package it.unibo.pap.nbodies.system.configurator.views.implementation;

import it.unibo.pap.nbodies.model.implementation.Position;
import it.unibo.pap.nbodies.model.interfaces.IBody;
import it.unibo.pap.nbodies.model.interfaces.INBodiesSystemModel;
import it.unibo.pap.nbodies.model.interfaces.IPosition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.Iterator;
import java.util.concurrent.Semaphore;

import javax.swing.JPanel;

/**
 * Implementation of the view to show the n-bodies
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
@SuppressWarnings("serial")
public class BodiesSystemDisplay extends JPanel {

	/**
	 * The system's model
	 */
	protected INBodiesSystemModel model;
	/**
	 * Value of the size scale of the system displayed
	 */
	private long sizeScale;
	/**
	 * Dimension of the bodies system's window
	 */
	private Dimension windowDimension;
	/**
	 * Insets of the bodies system's display
	 */
	private Insets displayInsets;
	/**
	 * Dimension of the bodies system's display
	 */
	private Dimension displayDimension;
	/**
	 * Position of the image's center
	 */
	private IPosition imageCenter;
	/**
	 * Boolean to signal the update of the image's center
	 */
	private boolean centerUpdated;
	/**
	 * Semaphore to synchronize the display
	 */
	private Semaphore paintSynchSemaphore;

	/**
	 * Constructor of the display to show the bodies that takes as parameters the nbodies's model,
	 * that contains all data about the bodies and the scale factor to drawing all the bodies in the
	 * display, and the semaphore to synchronize the updating of the bodies painting
	 * 
	 * @param bodiesModel : the nbodies's model
	 * @param paintViewSemaphore : the semaphore to synchronize the updating of the bodies painting
	 */
	public BodiesSystemDisplay(INBodiesSystemModel bodiesModel, Semaphore paintViewSemaphore) {
		model = bodiesModel;
		paintSynchSemaphore = paintViewSemaphore;
		sizeScale = model.getScale();
		imageCenter = new Position();
		centerUpdated = false;
	}
	
	/**
	 * Method to paint all the bodies in the display
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// graphic component to paint the components in the display
		Graphics2D g2d = (Graphics2D) g;
		
		// setting the dimension of the display by get the panel dimension and the insets of the display
		windowDimension = getSize();
		displayInsets = getInsets();
		displayDimension = new Dimension((windowDimension.width - displayInsets.left - displayInsets.right), 
											(windowDimension.height - displayInsets.top - displayInsets.bottom));
		
		// setting of the image's center
		if(!centerUpdated){
			imageCenter.setComponents(displayDimension.getWidth() / 2,
					displayDimension.getHeight() / 2);
			centerUpdated = true;
		}
		
		IBody currBody;
		// check if the bodies's list is not null
		if(model.getBodies() != null){
			Iterator<IBody> bodyIterator = model.getBodies().iterator();
			// iteration on each body in the collection
			while(bodyIterator.hasNext()){
				currBody = bodyIterator.next();
				g2d.setColor(Color.RED);
				
				/* get the current body's radius, calculate the radius against the display
				* scale and do the product with the display's width to get the correct
				* visualization of the body
				*/
				double bodyRadius = Math.round((currBody.getRadius() / sizeScale) * displayDimension.width);
				int newRadius = (bodyRadius < 1) ? (int)(bodyRadius + 1) : (int)bodyRadius;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				// calculate the coordinates of the body's visualization position with the same scale factor
				int xCoordinate = (int)((((currBody.getPosition().getX() / sizeScale) * displayDimension.width)) +
									(imageCenter.getX() - (newRadius / 2)));
				int yCoordinate = displayDimension.height - ((int)((
									((currBody.getPosition().getY() / sizeScale) * displayDimension.height)) + 
									(imageCenter.getY() - (newRadius / 2))));
				
				//System.out.println("[Bodies Display] x: " + xCoordinate + " - y: " + yCoordinate);
				
				//Toolkit toolkit = Toolkit.getDefaultToolkit();
				//Image img = toolkit.getImage("/ConfigurationFiles/earth.gif");
				//System.out.println("x: " + xCoordinate + " - y: " + yCoordinate);
				//g2d.drawImage(img, xCoordinate, yCoordinate, null);
				
				g2d.fillOval(xCoordinate, yCoordinate, newRadius, newRadius);
			}
		}
		// paint semaphore release to signal the done painting
		paintSynchSemaphore.release();
	}

	/**
	 * Method to get the display's size scale
	 * @return the display's size scale
	 */
	public long getSizeScale() {
		return sizeScale;
	}
	
	/**
	 * Method to set the display's size scale
	 * @param newSizeScale : the new size scale's value
	 */
	public void setSizeScale(long newSizeScale) {
		sizeScale = newSizeScale;
	}

	/**
	 * Method to refresh and repaint the displayed image
	 */
	public void updateDisplayImage() {
		this.repaint();
	}

	/**
	 * Method to move the image's center in the display
	 * 
	 * @param x : movement on the x-axe
	 * @param y : movement on the y-axe
	 */
	public void moveCenter(int x, int y) {
		imageCenter.setComponents(imageCenter.getX() - x,
				imageCenter.getY() + y);
		centerUpdated = true;
	}

	/**
	 * Method to reset the image and the visualization scale to the initial status
	 */
	public void reset() {
		centerUpdated = false;
		sizeScale = model.getScale();
		this.repaint();
	}
	
}
