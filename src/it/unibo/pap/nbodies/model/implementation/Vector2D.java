package it.unibo.pap.nbodies.model.implementation;

import it.unibo.pap.nbodies.model.interfaces.IVector2D;

/**
 * Implementation of the two dimensions vector
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public class Vector2D implements IVector2D {

	/**
	 * x coordinate of the vector
	 */
	protected double x;
	/**
	 * y coordinate of the vector
	 */
	protected double y;
	
	/***
	 * Constructor without input parameters,
	 * this constructor will set the coordinates
	 * x and y to generated random values
	 */
	public Vector2D() {
		double signx = Math.random();
		double signy = Math.random();
		x = Math.random();
		y = Math.random();
		if (signx < 0.5) {
			x = -x;
		}
		if (signy < 0.5) {
			y = -y;
		}
	}
	
	/***
	 * Constructor with the value of the two positions
	 * passed by input parameters,
	 * this constructor will set the initial and the
	 * final position to values passed as parameter
	 * @param x : the value that will set to the x coordinate of the vector
	 * @param y : the value that will set to the y coordinate of the vector
	 */
	public Vector2D(double xCoordinate, double yCoordinate) {
		this.setComponents(xCoordinate, yCoordinate);
	}

	@Override
	public void setX(double value) {
		x = value;
	}

	@Override
	public void setY(double value) {
		y = value;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void setComponents(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public double getIntensity() {
		return (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
	}
	
}
