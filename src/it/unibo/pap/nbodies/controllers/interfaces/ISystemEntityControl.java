package it.unibo.pap.nbodies.controllers.interfaces;

/**
 * Interface of controlled system entity
 * (the system entity is controlled by the controller)
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public interface ISystemEntityControl {

	/**
	 * Method to signal to the system that it has to start
	 */
	public abstract void signalStart();

	/**
	 * Method to signal to the system that it has to
	 * do the next computational step
	 */
	public abstract void signalStep();

	/**
	 * Method to signal to the system that it has to stop
	 */
	public abstract void signalStop();

	/**
	 * Method to signal to the system that it has to reset
	 * itself to the initial configuration
	 */
	public abstract void signalReset();
}
