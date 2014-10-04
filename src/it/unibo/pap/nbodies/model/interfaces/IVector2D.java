package it.unibo.pap.nbodies.model.interfaces;

/**
 * Interface of the two dimensions vector
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public interface IVector2D {

	/**
	 * Method the sets the x coordinate of the vector
	 * @param value : the value that will set to the x coordinate of the vector
	 */
	void setX(double value);

	/**
	 * Method the sets the y coordinate of the vector
	 * @param value : the value that will set to the y coordinate of the vector
	 */
	void setY(double value);

	/**
	 * Method that returns the value of the x coordinate of the vector
	 * @return the value of the x coordinate of the vector
	 */
	double getX();

	/**
	 * Method that returns the value of the y coordinate of the vector
	 * @return the value of the y coordinate of the vector
	 */
	double getY();

	/**
	 * Method that sets the value of the x and the y coordinates of the vector
	 * @param x : the value that will set to the x coordinate of the vector
	 * @param y : the value that will set to the y coordinate of the vector
	 */
	void setComponents(double x, double y);

	/**
	 * Method that returns the intensity of this vector
	 * @return the intensity of this vector
	 */
	double getIntensity();
	
}
