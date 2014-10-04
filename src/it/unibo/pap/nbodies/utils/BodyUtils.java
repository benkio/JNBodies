package it.unibo.pap.nbodies.utils;

import it.unibo.pap.nbodies.model.interfaces.StaticModelData;

/**
 * Implementation of the bodies's utilities functions used in the system
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public class BodyUtils {

	/**
	 * Method to calculate the radius value of a body
	 * @param bodyMass : the body's mass
	 * @return the radius value of the body
	 */
	public static double calculateBodyRadius(double bodyMass) {
		return (Math.pow(bodyMass * StaticModelData.BODY_DENSITY, (1.0 / 3.0)));
	}
	
}
