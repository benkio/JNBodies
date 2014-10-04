package it.unibo.pap.nbodies.model.interfaces;

/**
 * Interface of the single body
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public interface IBody {

	/**
	 * Method to set the number of the bodies
	 * 
	 * @param numberOfBodies
	 *            : number of bodies
	 */
	void setBodiesNumber(int numberOfBodies);

	/**
	 * Method to get the body's mass value
	 * 
	 * @return the body's mass value
	 */
	double getMass();

	/**
	 * Method to set the body's mass
	 * 
	 * @param mass
	 *            : the new value to set the body's mass
	 */
	void setMass(double mass);

	/**
	 * Method to get the value of the body's radius
	 * 
	 * @return the value of the body's radius
	 */
	double getRadius();

	/**
	 * Method to set the value of the body's radius
	 * 
	 * @param radius
	 *            : the value of the body's radius
	 */
	void setRadius(double radius);

	/**
	 * Method to get the current body's position
	 * 
	 * @return the current body's position
	 */
	IPosition getPosition();

	/**
	 * Set the current position of this body
	 * 
	 * @param position
	 *            : the position to set to this body
	 */
	void setPosition(IPosition position);

	/**
	 * Method to get the intensity of the current body's speed
	 * 
	 * @return the intensity of the current body's speed
	 */
	double getSpeedIntensity();

	/**
	 * Method to get the current body's speed
	 * 
	 * @return the current body's speed
	 */
	IVector2D getSpeed();

	/**
	 * Method to set the current body's speed
	 * 
	 * @param speed
	 *            : the value body's speed
	 */
	void setSpeed(IVector2D speed);

	/**
	 * Check if the body is collided with another one
	 * 
	 * @return True if the body is collided with another one False otherwise
	 */
	boolean isCollided();

	/**
	 * Set to True the collided boolean because the body collided with another
	 * one
	 */
	void setCollided();

	/**
	 * Add a force to the global force of this body
	 * 
	 * @param force
	 *            : the force to add
	 * @param id
	 *            : identificator of the force to add
	 */
	void addForce(IVector2D force, int id);

	/**
	 * Method to update the body's position
	 * 
	 * @param timeStep
	 *            : the time step to update the body's position with reference
	 *            to all forces working on the body
	 */
	void updatePosition(int timeStep);

}
