package it.unibo.pap.nbodies.utils;

import it.unibo.pap.nbodies.model.interfaces.IPosition;

/**
 * Implementation of the positions's utilities functions used in the system
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public class PositionUtils {

	/**
	 * Method that returns the distance between the positions passed by parameter
	 * @param firstPosition : first position against which to calculate the distance
	 * @param secondPosition : second position against which to calculate the distance
	 * @return the distance between the current position and the position passed by parameter
	 */
	public static double calculateDistance(IPosition firstPosition, IPosition secondPosition) {
		return Math.sqrt(calculateSqrDistance(firstPosition, secondPosition));
	}
	
	/**
	 * Method that returns the square distance between the positions passed by parameter
	 * @param firstPosition : first position against which to calculate the square distance
	 * @param secondPosition : second position against which to calculate the square distance
	 * @return the square distance between the current position and the position passed by parameter
	 */
	public static double calculateSqrDistance(IPosition firstPosition, IPosition secondPosition) {
		return Math.pow(secondPosition.getX() - firstPosition.getX(), 2)
				+ Math.pow(secondPosition.getY() - firstPosition.getY(), 2);
	}
	
}
