package it.unibo.pap.nbodies.model.interfaces;

import java.util.List;

/**
 * Interface of the n-bodies system model
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public interface INBodiesSystemModel {

	/**
	 * Method to get the number of bodies involved in the system 
	 * @return the number of bodies involved in the system
	 */
	public int getNumBodies();

	/**
	 * Method to set the initial system's bodies number
	 * @param scale : the new system's visualization scale value
	 */
	public void setNumBodies(int bodiesNumber);
	
	/**
	 * Method to get the list of bodies involved in the system
	 * @return the list of bodies involved in the system
	 */
	public List<IBody> getBodies();

	/**
	 * Method to set the system's bodies list
	 * @param bodies : the system's bodies list
	 */
	public void setBodies(List<IBody> bodies);
	
	/**
	 * Method to reset the system configuration to the initial one
	 */
	public void reset();
	
	/**
	 * Method to do the next computational step of system
	 * (calculating forces on each body and update its position)
	 */
	public void doNextStep();

	/**
	 * Method to get the default system's visualization scale
	 * @return the default system's visualization scale
	 */
	public long getScale();

	/**
	 * Method to set the default system's visualization scale
	 * @param scale : the new system's visualization scale value
	 */
	public void setScale(long scale);
	
}
