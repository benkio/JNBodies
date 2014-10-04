package it.unibo.pap.nbodies.views.interfaces;

import it.unibo.pap.nbodies.controllers.listeners.*;

import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

/**
 * Interface of the view to show the n-bodies system's window
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public interface INBodiesSystemView {

	/**
	 * Method to reset the system's properties and execution
	 */
	public void reset();
	
	/**
	 * Method to get the synchronization semaphore for the display
	 * @return the synchronization semaphore for the display
	 */
	public Semaphore getSynchronizationPaintSemaphore();

	/**
	 * Method to refresh the displayed image
	 */
	public void updateViewImage();
	
	/**
	 * Makes the product between the input factor and the visualization scale
	 * 
	 * @param factor : factor to update the view scale of the display
	 */
	public void updateViewScale(int factor);
	
	/**
	 * Method to move the relative center of the displayed image
	 * 
	 * @param x : movement on the x-axe
	 * @param y : movement on the y-axe
	 */
	public void updateViewCenter(int x, int y);
	
	/**
	 * Method that add the listener to the "Next Step" button
	 * @param listener : the listener to add
	 */
	public void addNextStepButtonListener(ActionListener listener);
	
	/**
	 * Method that add the listener to the "Pause" button
	 * @param listener : the listener to add
	 */
	public void addPauseButtonListener(ActionListener listener);
	
	/**
	 * Method that add the listener to the "Start" button
	 * @param listener : the listener to add
	 */
	public void addStartButtonListener(ActionListener listener);
	
	/**
	 * Method that add the listener to the "Reset" button
	 * @param listener : the listener to add
	 */
	public void addResetButtonListener(ActionListener listener);
	
	/**
	 * Method that add the listener to the click event of the mouse
	 * @param mouseClickedListener : the listener to add
	 */
	public void addMouseClickedListener(MouseClickedListener mouseClickedListener);
	
	/**
	 * Method that add the listener to the wheeling movement of the mouse
	 * @param mouseWheelListener : the listener to add
	 */
	public void addMouseWheelListener(MouseWheelListener mouseWheelListener);
	
	/**
	 * Method that add the listener to the movement of the mouse
	 * @param mouseMotionListener : the listener to add
	 */
	public void addMouseMotionListener(MouseMotionListener mouseMotionListener);

	/**
	 * Method for close the view.
	 */
	void closeView();

	/**
	 * Method for the add of the reset button listener.
	 * @param listener
	 */
	void addRestartButtonListener(ActionListener listener);
	
}
