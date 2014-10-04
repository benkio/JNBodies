package it.unibo.pap.nbodies.model.implementation;

import it.unibo.pap.nbodies.model.interfaces.IPosition;
import it.unibo.pap.nbodies.model.interfaces.StaticModelData;

/**
 * Implementation of the position two dimension vector
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public class Position extends Vector2D implements IPosition {

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
	 * x and y to generated random values basing 
	 * to the distance between the EARTHh and the Sun
	 */
	public Position() {
		this.setComponents(
				(Math.random() * StaticModelData.DISTANCE_EARTH_SUN * 2 - StaticModelData.DISTANCE_EARTH_SUN),
				(Math.random() * StaticModelData.DISTANCE_EARTH_SUN * 2 - StaticModelData.DISTANCE_EARTH_SUN));
	}
	
	/***
	 * Constructor with the value of the two coordinates
	 * passed by input parameters,
	 * this constructor will set the coordinates
	 * x and y to values passed as parameter
	 * @param x : the value that will set to the x coordinate of the position
	 * @param y : the value that will set to the y coordinate of the position
	 */
	public Position(double x, double y) {
		this.setComponents(x, y);
	}
	
}
