package it.unibo.pap.nbodies.model.implementation.executor.tasks;

import it.unibo.pap.nbodies.model.interfaces.IBody;
import it.unibo.pap.nbodies.utils.PositionUtils;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Implementation of the task to manage the bodies's collisions
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public class ManageCollisionTask extends Thread {

	/**
	 * List that contains all bodies on which managing collisions
	 */
	List<IBody> bodies;
	/**
	 * The index of the current body to check collisions
	 */
	int bodyIndex;

	/**
	 * Internal semaphore used to sync the start of calculation
	 */
	Semaphore start;
	/**
	 * External semaphore used to signal the end of the task
	 */
	Semaphore taskDone;

	/**
	 * Used to shutdown the thread
	 */
	boolean end;

	/**
	 * Constructor that takes all bodies's list to manage the current body's
	 * collisions, the current body's index in the bodies's list and the
	 * semaphore to signal the manage of the current body's collisions
	 * 
	 * @param bodiesList
	 *            : the list that contains all bodies
	 * @param currentBodyIndex
	 *            : the index of the current body to check collisions
	 */
	public ManageCollisionTask(List<IBody> bodiesList, Semaphore taskDone) {
		bodies = bodiesList;
		start = new Semaphore(0);
		this.taskDone = taskDone;
		end = false;
	}

	@Override
	public void run() {
		while (true) {
			try {
				start.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (end)
				break;

			synchronized (bodies.get(bodyIndex)) {
				if (!bodies.get(bodyIndex).isCollided()) {
					for (int index = bodies.size() - 1; index > bodyIndex; index--) {
						synchronized (bodies.get(index)) {
							if (!bodies.get(index).isCollided()) {
								if (checkCollision(bodies.get(bodyIndex),
										bodies.get(index))) {
									manageCollision(bodies.get(bodyIndex),
											bodies.get(index));
								}
							}
						}
					}
				}
			}
			taskDone.release();
		}
	}

	/**
	 * set the new body index to check
	 * 
	 * @param bodyIndex
	 */
	public void setBodyIndex(int bodyIndex) {
		this.bodyIndex = bodyIndex;
	}

	/**
	 * start of computation
	 */
	public void notifyStart() {
		start.release();
	}

	/**
	 * shutdown the thread
	 */
	public void Shutdown() {
		end = true;

		start.release();
	}

	/**
	 * Method to check if two bodies are collided
	 * 
	 * @param firstBody
	 *            : the first body involved in the check
	 * @param secondBody
	 *            : the second body involved in the check
	 * @return True if there's collision, False otherwise
	 */
	protected boolean checkCollision(IBody firstBody, IBody secondBody) {
		double distance = PositionUtils.calculateDistance(
				firstBody.getPosition(), secondBody.getPosition());
		return (distance < (firstBody.getRadius() + secondBody.getRadius()));
	}

	/**
	 * Method to manage the collision situation between two bodies
	 * 
	 * @param firstBody
	 *            : the first body involved in the collision
	 * @param secondBody
	 *            : the second body involved in the collision
	 */
	protected void manageCollision(IBody firstBody, IBody secondBody) {
		double totalMass = firstBody.getMass() + secondBody.getMass();
		double firstMass = firstBody.getMass();
		double secondMass = secondBody.getMass();

		// calculate the bigger body's new speed components
		double newSpeedX = ((firstMass * firstBody.getSpeed().getX()) + ((secondMass * secondBody
				.getSpeed().getX()))) / totalMass;
		double newSpeedY = ((firstMass * firstBody.getSpeed().getY()) + ((secondMass * secondBody
				.getSpeed().getY()))) / totalMass;

		// calculate the bigger body's new position components
		double newPositionX = (firstBody.getPosition().getX() * (firstMass / totalMass))
				+ (secondBody.getPosition().getX() * (secondMass / totalMass));
		double newPositionY = (firstBody.getPosition().getY() * (firstMass / totalMass))
				+ (secondBody.getPosition().getY() * (secondMass / totalMass));

		// the new values about position and speed will be settet to the bigger
		// body involved in the collision
		if (firstBody.getRadius() > secondBody.getRadius()) {
			secondBody.setCollided();
			firstBody.setMass(totalMass);
			firstBody.getPosition().setComponents(newPositionX, newPositionY);
			firstBody.getSpeed().setComponents(newSpeedX, newSpeedY);
		} else {
			firstBody.setCollided();
			secondBody.setMass(totalMass);
			secondBody.getPosition().setComponents(newPositionX, newPositionY);
			secondBody.getSpeed().setComponents(newSpeedX, newSpeedY);
		}
	}

}
