package it.unibo.pap.nbodies.model.jpf.Collision;

import it.unibo.pap.nbodies.model.implementation.executor.Body;
import it.unibo.pap.nbodies.model.implementation.executor.tasks.ManageCollisionTask;
import it.unibo.pap.nbodies.model.interfaces.IBody;
import it.unibo.pap.nbodies.model.interfaces.INBodiesSystemModel;
import it.unibo.pap.nbodies.model.interfaces.StaticModelData;
import it.unibo.pap.nbodies.utils.SystemUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Implementation of the n-bodies system model
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public class NBodiesSystemModelOnlyCollision implements INBodiesSystemModel {

	/**
	 * Configuration file path
	 */
	protected String configFilePath;
	/**
	 * List with all bodies
	 */
	protected List<IBody> systemBodies;
	/**
	 * Initial number of bodies
	 */
	protected int initBodiesNumber;
	/**
	 * Time between two runtime steps
	 */
	protected int systemTimeStep;
	/**
	 * Display image scale
	 */
	protected long displayScale;
	/**
	 * Semaphore to signal the finishing of each task
	 */
	protected Semaphore taskDoneSemaphore;
	/**
	 * Semaphore to signal the step done
	 */
	protected Semaphore signalStepSemaphore;
	/**
	 * Flag related to the file load of the initial configuration
	 */
	protected boolean fileConfigLoaded;
	/**
	 * ExecutorService
	 */
	protected ExecutorService exec;
	/**
	 * Thread Array for collision Task
	 */
	protected ManageCollisionTask[] collisionThreads;
	/**
	 * Active Object Number
	 */
	private final int nThread = 4;

	/**
	 * Constructor of the system's model
	 * 
	 * @param bodiesNumber
	 *            : the initial number of bodies
	 * @param timeStep
	 *            : the runtime time step
	 */
	public NBodiesSystemModelOnlyCollision(int bodiesNumber, int timeStep) {
		systemBodies = new CopyOnWriteArrayList<IBody>();
		initBodiesNumber = bodiesNumber;
		systemTimeStep = timeStep;

		fileConfigLoaded = false;
		setUpModel(fileConfigLoaded);
	}

	/**
	 * Constructor of the system model from file
	 * 
	 * @param configFile
	 *            : Configuration File's path
	 * @param timeStep
	 *            : the runtime time step
	 */
	public NBodiesSystemModelOnlyCollision(String configFile, int timeStep) {
		systemTimeStep = timeStep;

		configFilePath = configFile;

		fileConfigLoaded = true;
		setUpModel(fileConfigLoaded);
	}

	/**
	 * Method to generate the bodies
	 * 
	 * @param numberOfBodies
	 *            : the number of bodies to generate
	 */
	private void generateBodies(int numberOfBodies) {
		for (int index = 0; index < numberOfBodies; index++) {
			systemBodies.add(new Body(numberOfBodies));
		}
	}

	/**
	 * Method to set up all the parameters of the system's model
	 */
	protected void setUpModel(boolean loadedFromFile) {
		systemBodies = new CopyOnWriteArrayList<IBody>();

		if (!loadedFromFile) {
			displayScale = StaticModelData.DEFAULT_SIZE_SCALE;
			generateBodies(initBodiesNumber);
		} else {
			new SystemUtils().loadBodiesFromFile(configFilePath, this);
		}
		taskDoneSemaphore = new Semaphore(0);

		signalStepSemaphore = new Semaphore(1);

		exec = Executors.newFixedThreadPool(nThread);

		collisionThreads = new ManageCollisionTask[nThread];
	}

	@Override
	public int getNumBodies() {
		return systemBodies.size();
	}

	@Override
	public void setNumBodies(int bodiesNumber) {
		initBodiesNumber = bodiesNumber;
	}

	@Override
	public List<IBody> getBodies() {
		return systemBodies;
	}

	@Override
	public void setBodies(List<IBody> bodies) {
		systemBodies = bodies;
	}

	@Override
	public void reset() {
		setUpModel(fileConfigLoaded);
	}

	@Override
	public long getScale() {
		return displayScale;
	}

	@Override
	public void setScale(long scale) {
		displayScale = scale;
	}

	@Override
	public void doNextStep() {
		try {
			// // System.out.println("[N Bodies Model] Next Step Procedure");
			// long runtimeStepTime = System.nanoTime();
			// signalStepSemaphore.acquire();
			//
			// for (int index = 0; index < systemBodies.size() - 1; index++) {
			// exec.execute(new UpdateBodiesForcesTask(systemBodies, index,
			// taskDoneSemaphore));
			// }
			//
			// for (int index = 0; index < systemBodies.size() - 1; index++) {
			// taskDoneSemaphore.acquire();
			// }
			//
			// exec.shutdown();
			// exec = Executors.newFixedThreadPool(nThread);
			//
			// for (int index = 0; index < systemBodies.size(); index++) {
			// exec.execute(new UpdateBodyPositionTask(
			// systemBodies.get(index), systemTimeStep,
			// taskDoneSemaphore));
			// }
			//
			// for (int index = 0; index < systemBodies.size(); index++) {
			// taskDoneSemaphore.acquire();
			// }
			//
			// exec.shutdown();
			// exec = Executors.newFixedThreadPool(nThread);

			int bodyIndex = systemBodies.size() - 1;
			taskDoneSemaphore = new Semaphore(nThread);
			while (bodyIndex > 0) {

				for (int i = 0; i < nThread; i++)
					taskDoneSemaphore.acquire();

				if (bodyIndex - nThread >= -1) {

					for (int threadIndex = 0; threadIndex < collisionThreads.length; threadIndex++) {
						if (collisionThreads[threadIndex] == null
								|| !collisionThreads[threadIndex].isAlive()) {
							collisionThreads[threadIndex] = new ManageCollisionTask(
									systemBodies, taskDoneSemaphore);

							collisionThreads[threadIndex].start();
						}
						collisionThreads[threadIndex]
								.setBodyIndex((bodyIndex - threadIndex));

						collisionThreads[threadIndex].notifyStart();
					}
					bodyIndex -= nThread;
				} else {
					for (int threadIndex = bodyIndex; threadIndex >= 0; threadIndex--) {
						if (collisionThreads[threadIndex] == null
								|| !collisionThreads[threadIndex].isAlive()) {
							collisionThreads[threadIndex] = new ManageCollisionTask(
									systemBodies, taskDoneSemaphore);
							collisionThreads[threadIndex].start();
						}
						collisionThreads[threadIndex]
								.setBodyIndex((bodyIndex - threadIndex));

						collisionThreads[threadIndex].notifyStart();
					}
					bodyIndex = 0;
				}

				if (bodyIndex <= 0) {
					taskDoneSemaphore = new Semaphore(0);
					for (ManageCollisionTask t : collisionThreads) {
						// System.out.println("Shutdown thread");
						if (t != null)
							t.Shutdown();
					}
					break;
				}
			}
			// remove collided bodies & check if there's some collision
			for (IBody body : systemBodies) {
				if (body.isCollided()) {
					systemBodies.remove(body);
				}
			}
			// update bodies's number
			for (IBody body : systemBodies) {
				body.setBodiesNumber(systemBodies.size());
			}
			signalStepSemaphore.release();
			// System.out.println("Time: " + runtimeStepTime);

		} catch (InterruptedException ex) {
			System.out
					.println("[N Bodies Model - Exception] Next Step Procedure: "
							+ ex.getMessage());
		}
	}

}
