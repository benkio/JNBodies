/**
 *
 */
package it.unibo.pap.nbodies.controllers.listeners;

import it.unibo.pap.nbodies.system.configurator.views.NBodiesConfigurationView;
import it.unibo.pap.nbodies.views.interfaces.INBodiesSystemView;
import it.unibo.pap.nbodies.controllers.implementation.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 *
 */
public class RestartButtonListener implements ActionListener {

    /**
     * NBodies System View
     */
    private INBodiesSystemView view;
    /**
     * Thread to manage the system's view
     */
    protected SystemViewerThread SystemViewerThread;
    /**
     * Thread to manage the system's model
     */
    protected SystemModelThread SystemModelThread;

    /**
     * Constructor of the Restart Button in NBodies system view
     *
     * @param view
     */
    public RestartButtonListener(INBodiesSystemView view,
	    SystemModelThread modelThread, SystemViewerThread viewThread) {
	this.view = view;
	SystemModelThread = modelThread;
	SystemViewerThread = viewThread;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	SystemModelThread.signalStop();
	SystemViewerThread.signalStop();
	view.closeView();
	new NBodiesConfigurationView();
    }
}
