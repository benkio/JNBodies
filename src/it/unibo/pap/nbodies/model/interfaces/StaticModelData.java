package it.unibo.pap.nbodies.model.interfaces;

/**
 * Interface that contains all static data about the system
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public interface StaticModelData {

	/**
	 * The default size scale of the system's display
	 */
	public static final long DEFAULT_SIZE_SCALE = 149600000000L * 2;
	/**
	 * Distance between the Earth and the Sun
	 */
	public static final long DISTANCE_EARTH_SUN = 149600000000L;
	/**
	 * Speed of the Earth
	 */
	public static final int EARTH_SPEED = 100000;
	/**
	 * Density of a single body
	 */
	public static final double BODY_DENSITY = 4.328548123;
	/**
	 * Universal gravity constant (used to calculate the gravitation force)
	 */
	public static final double UNIVERSAL_GRAVITY_CONSTANT = 6.67428 * (Math.pow(10, -11));
}
