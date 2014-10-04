package it.unibo.pap.nbodies.model.implementation.executor;

import it.unibo.pap.nbodies.model.implementation.Position;
import it.unibo.pap.nbodies.model.implementation.Vector2D;
import it.unibo.pap.nbodies.model.interfaces.IBody;
import it.unibo.pap.nbodies.model.interfaces.IPosition;
import it.unibo.pap.nbodies.model.interfaces.IVector2D;
import it.unibo.pap.nbodies.utils.BodyUtils;

/**
 * Implementation of the single body
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public class Body implements IBody {

	/**
	 * The number of body in the system
	 */
	protected int nBodies;
	/**
	 * The mass of the body
	 */
	protected double bodyMass;
	/**
	 * The radius measure of the body
	 */
	protected double bodyRadius;
	/**
	 * The current position of the body
	 */
	protected IPosition bodyPosition;
	/**
	 * The current speed of the body
	 */
	protected IVector2D bodySpeed;
	/**
	 * The boolean variable referred to the collided situation with another body
	 */
	protected boolean isCollided;
	/**
	 * The forces on this body
	 */
	protected IVector2D[] bodyForces;

	/**
	 * Constructor that takes the initial number of body in the system as
	 * parameter
	 * 
	 * @param initialNumberOfBodies
	 *            : the initial number of bodies in the system
	 */
	public Body(int initialNumberOfBodies) {
		nBodies = initialNumberOfBodies;
		bodyMass = Math.random() * Math.pow(10, Math.random() * 28);
		calculateRadius();
		bodyPosition = new Position();
		int directionX = Math.random() < 0.5 ? -1 : 1;
		int directionY = Math.random() < 0.5 ? -1 : 1;
		bodySpeed = new Vector2D(Math.random()
				* Math.pow(10, Math.random() * 5) * directionX, Math.random()
				* Math.pow(10, Math.random() * 5) * directionY);
		bodyForces = new IVector2D[initialNumberOfBodies];

	}

	/**
	 * Constructor with all the initial body's parameters
	 * 
	 * @param initialMass
	 *            : the initial mass of the body
	 * @param initialPosition
	 *            : the initial position of the body
	 * @param initialSpeed
	 *            : the initial speed of the body
	 * @param initialNumberOfBodies
	 *            : the initial number of bodies in the system
	 */
	public Body(int initialNumberOfBodies, double initialMass,
			IPosition initialPosition, IVector2D initialSpeed) {
		bodyForces = new IVector2D[initialNumberOfBodies];
		bodyMass = initialMass;
		calculateRadius();
		bodyPosition = initialPosition;
		bodySpeed = initialSpeed;
		nBodies = initialNumberOfBodies;
	}

	@Override
	public void setBodiesNumber(int numberOfBodies) {
		nBodies = numberOfBodies;
		bodyForces = new Vector2D[nBodies];
	}

	@Override
	public double getMass() {
		return bodyMass;
	}

	@Override
	public synchronized void setMass(double mass) {
		bodyMass = mass;
		this.calculateRadius();
	}

	@Override
	public double getRadius() {
		return bodyRadius;
	}

	@Override
	public void setRadius(double radius) {
		bodyRadius = radius;
	}

	@Override
	public IPosition getPosition() {
		return bodyPosition;
	}

	@Override
	public void setPosition(IPosition position) {
		bodyPosition = position;
	}

	@Override
	public double getSpeedIntensity() {
		return bodySpeed.getIntensity();
	}

	@Override
	public IVector2D getSpeed() {
		return bodySpeed;
	}

	@Override
	public void setSpeed(IVector2D speed) {
		bodySpeed = speed;
	}

	@Override
	public boolean isCollided() {
		return isCollided;
	}

	@Override
	public void setCollided() {
		isCollided = true;
	}

	@Override
	public void addForce(IVector2D force, int id) {
		bodyForces[id] = force;
	}

	@Override
	public void updatePosition(int timeStep) {
		// System.out.println("[Body] Updating body's position");
		double xForce = 0, yForce = 0, xSpeed = 0, ySpeed = 0;
		// calculate the total force on the body
		// xForce = x force on this body
		// yForce = y force on this body
		for (int i = 0; i < bodyForces.length; i++) {
			if (bodyForces[i] != null) {
				xForce += bodyForces[i].getX();
				yForce += bodyForces[i].getY();
			}
		}
		// calculate the body's speed
		xSpeed = (xForce / bodyMass) * timeStep;
		ySpeed = (yForce / bodyMass) * timeStep;
		// calculate the new body's position
		bodyPosition.setX(bodyPosition.getX() + (bodySpeed.getX() + xSpeed / 2)
				* timeStep);
		bodyPosition.setY(bodyPosition.getY() + (bodySpeed.getY() + ySpeed / 2)
				* timeStep);
		// calculate the new body's speed
		bodySpeed.setX(bodySpeed.getX() + xSpeed);
		bodySpeed.setY(bodySpeed.getY() + ySpeed);
	}

	/**
	 * Method to calculate the body's radius
	 */
	protected void calculateRadius() {
		bodyRadius = BodyUtils.calculateBodyRadius(bodyMass);
	}
}
