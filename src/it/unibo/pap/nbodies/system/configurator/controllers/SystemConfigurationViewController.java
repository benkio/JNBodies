package it.unibo.pap.nbodies.system.configurator.controllers;

import it.unibo.pap.nbodies.controllers.listeners.ChooseConfigFileConfigurationButtonListener;
import it.unibo.pap.nbodies.controllers.listeners.StartButtonConfigurationListener;
import it.unibo.pap.nbodies.system.configurator.views.NBodiesConfigurationView;

import java.awt.event.ActionListener;

/**
 * N-bodies's system start button configuration view controller
 *
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 *
 */
public class SystemConfigurationViewController {

	/**
	 * View to control
	 */
	NBodiesConfigurationView targetView;

	/**
	 * Constructor that take the text fields of the system's view
	 *
	 * @param view
	 *            : the target view to control
	 */
	public SystemConfigurationViewController(NBodiesConfigurationView view) {
		targetView = view;
	}

	public ActionListener getChooseConfigurationFileListener() {
		return new ChooseConfigFileConfigurationButtonListener(targetView);
	}

	public ActionListener getStartListener() {
		return new StartButtonConfigurationListener(targetView);
	}
}
