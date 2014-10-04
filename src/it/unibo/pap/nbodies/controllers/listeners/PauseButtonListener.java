package it.unibo.pap.nbodies.controllers.listeners;
import it.unibo.pap.nbodies.controllers.implementation.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Implementation of the listener to catch the mouse click event on the button
 * to put in pause the n-bodies system's window
 *
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 *
 */
public class PauseButtonListener implements ActionListener {

    /**
     * Thread to manage the system's view
     */
    protected SystemViewerThread SystemViewerThread;
    /**
     * Thread to manage the system's model
     */
    protected SystemModelThread SystemModelThread;

    /**
     * Contructor that takes as arguments the Thread to control the model and
     * the viewer
     *
     * @param modelThread
     *            : the Thread to control the model
     * @param viewThread
     *            : the Thread to control the viewer
     */
    public PauseButtonListener(SystemModelThread modelThread,
	    SystemViewerThread viewThread) {
	SystemModelThread = modelThread;
	SystemViewerThread = viewThread;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	SystemModelThread.signalStop();
	SystemViewerThread.signalStop();
    }

}
