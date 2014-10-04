package it.unibo.pap.nbodies.controllers.listeners;
import it.unibo.pap.nbodies.controllers.implementation.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

/**
 * Implementation of the listener to catch the mouse wheeling event on the
 * display view of n-bodies system's window
 *
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 *
 */
public class MouseWheelListener extends MouseAdapter {

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
    public MouseWheelListener(SystemViewerThread viewThread) {
	SystemViewerThread = viewThread;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
	SystemViewerThread.notifyMouseWheel(e.getWheelRotation());
    }

}
