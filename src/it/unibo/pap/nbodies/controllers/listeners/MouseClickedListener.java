package it.unibo.pap.nbodies.controllers.listeners;
import it.unibo.pap.nbodies.controllers.implementation.*;

import java.awt.event.MouseAdapter;

/**
 * Implementation of the listener to catch the mouse click event on the display
 * view of n-bodies system's window
 *
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 *
 */
public class MouseClickedListener extends MouseAdapter {

    /**
     * Thread to manage the system's view
     */
    protected SystemViewerThread SystemViewerThread;

    /**
     * Constructor that takes as argument the viewer thread to manage the view
     *
     * @param viewThread
     *            : the viewer Thread to manage the view
     */
    public MouseClickedListener(SystemViewerThread viewThread) {
	SystemViewerThread = viewThread;
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
	SystemViewerThread.notifyMouseClick(e.getX(), e.getY());
    }

}
