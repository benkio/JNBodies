package it.unibo.pap.nbodies.model.implementation.executor.tasks;

import java.util.concurrent.Semaphore;

import it.unibo.pap.nbodies.model.interfaces.IBody;

/**
 * Implementation of the task to update the body's position
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public class UpdateBodyPositionTask implements Runnable {

	/**
	 * Body on which execute the update position task
	 */
	protected IBody targetBody;
	/**
	 * Time step to update the body's position
	 */
	protected int timeStep;
	/**
	 * Semaphore to signal the update of the body's position
	 */
	protected Semaphore updatePositionSemaphore;

	/**
	 * Constructor that takes the body on which update the position, 
	 * the time step to update the body's position and the semaphore
	 * to signal the update of the body's position
	 * @param body : the target body on which update the position
	 * @param updatePositionTimeStep : time step to update the position
	 * @param semaphoreToUpdatePosition : semaphore to signal the update of the body's position
	 */
	public UpdateBodyPositionTask(IBody body, int updatePositionTimeStep,
									Semaphore semaphoreToSignalUpdatePosition) {
	//public UpdateBodyPositionTask(IBody body, int updatePositionTimeStep) {
		targetBody = body;
		timeStep = updatePositionTimeStep;
		updatePositionSemaphore = semaphoreToSignalUpdatePosition;
	}
	
	@Override
	public void run() {
		targetBody.updatePosition(timeStep);
		updatePositionSemaphore.release();
	}

}
