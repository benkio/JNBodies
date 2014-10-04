package it.unibo.pap.nbodies.model.implementation.executor.tasks;

import it.unibo.pap.nbodies.model.implementation.Vector2D;
import it.unibo.pap.nbodies.model.interfaces.IBody;
import it.unibo.pap.nbodies.model.interfaces.StaticModelData;
import it.unibo.pap.nbodies.utils.PositionUtils;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Implementation of the task to calculate the forces between the current body
 * and the others
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public class UpdateBodiesForcesTask implements Runnable {

	/**
	 * List that contains all bodies on which update the forces
	 */
	List<IBody> bodies;
	/**
	 * Semaphore to signal the update of the bodies's forces
	 */
	protected Semaphore updateForcesSemaphore;
	/**
	 * The index of the current updating body's forces
	 */
	int bodyIndex;

	/**
	 * Constructor that takes all bodies's list to calculate the current body's
	 * forces, the current body's index in the bodies's list and the semaphore
	 * to signal the update of the current body's forces
	 * 
	 * @param bodies
	 *            : the list that contains all bodies
	 * @param currentBodyIndex
	 *            : the index of the current body on which update forces in the
	 *            bodies's list
	 */
	public UpdateBodiesForcesTask(List<IBody> bodiesList, int currentBodyIndex,
			Semaphore semaphoreToSignalUpdateForces) {
		// public UpdateBodiesForcesTask(List<IBody> bodiesList, int
		// currentBodyIndex) {
		bodies = bodiesList;
		bodyIndex = currentBodyIndex;
		updateForcesSemaphore = semaphoreToSignalUpdateForces;
	}

	@Override
	public void run() {
		// calculate all forces of the current body
		for (int index = bodies.size() - 1; index > bodyIndex; index--) {
			// each body doesn't have to update the force between and itself
			calculateForce(bodies.get(bodyIndex), bodies.get(index), bodyIndex,
					index);

		}
		updateForcesSemaphore.release();
	}

	/**
	 * Method to calculate the forces between the input passed bodies
	 * 
	 * @param firstBody
	 *            : the first body involved
	 * @param secondBody
	 *            : the first body involved
	 * @param firstBodyIndex
	 *            : the list index of the first body
	 * @param secondBodyIndex
	 *            : the list index of the second body
	 */
	protected void calculateForce(IBody firstBody, IBody secondBody,
			int firstBodyIndex, int secondBodyIndex) {
		// calculate the square distance between the two bodies
		double sqrDistance = PositionUtils.calculateSqrDistance(
				firstBody.getPosition(), secondBody.getPosition());
		// calculate the force between the two bodies
		double force = ((StaticModelData.UNIVERSAL_GRAVITY_CONSTANT
				* firstBody.getMass() * secondBody.getMass()) / sqrDistance);
		// calculate the angle between the common point between the bodies's
		// positions and the horizontal flat
		double distance = Math.sqrt(sqrDistance);
		// calculate the cos and sen of the angle between the two bodies
		double cosAngle = (secondBody.getPosition().getX() - firstBody
				.getPosition().getX()) / distance;
		double sinAngle = (secondBody.getPosition().getY() - firstBody
				.getPosition().getY()) / distance;
		// calculate the x and y components of the force between the two
		// bodies
		double yForce = force * sinAngle;
		double xForce = force * cosAngle;
		// add the calculated force to the two bodies's forces
		firstBody.addForce(new Vector2D(xForce, yForce), secondBodyIndex);
		secondBody.addForce(new Vector2D(-xForce, -yForce), firstBodyIndex);
	}
}
