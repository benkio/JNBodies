package it.unibo.pap.nbodies.controllers.listeners;

import it.unibo.pap.nbodies.controllers.implementation.NBodiesViewController;
import it.unibo.pap.nbodies.model.implementation.executor.NBodiesSystemModel;
import it.unibo.pap.nbodies.model.interfaces.INBodiesSystemModel;
import it.unibo.pap.nbodies.system.configurator.views.NBodiesConfigurationView;
import it.unibo.pap.nbodies.views.implementation.NBodiesSystemView;
import it.unibo.pap.nbodies.views.interfaces.INBodiesSystemView;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartButtonConfigurationListener implements ActionListener {
    /**
     * Configuration view for the NBodies System.
     */
    NBodiesConfigurationView configurationView;

    /**
     * Constructor of the Start Button Listener in configuration view
     *
     * @param configurationView
     *            Configuration view for the NBodies System.
     */
    public StartButtonConfigurationListener(
	    NBodiesConfigurationView configurationView) {
	this.configurationView = configurationView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	try {
	    // check the bodies number and time values that must be greather
	    // than zero
	    if (Integer.parseInt(configurationView.getTimeTextField().getText()
		    .trim()) > 0
		    && Integer.parseInt(configurationView.getNBodiesTextField()
			    .getText().trim()) > 0) {
		// start the system view
		configurationView.getCommunicationLabel().setForeground(
			Color.BLUE);
		configurationView.getCommunicationLabel().setText(
			"  Starting n-bodies system...");

		INBodiesSystemModel systemModel = new NBodiesSystemModel(
			Integer.parseInt(configurationView
				.getNBodiesTextField().getText()),
				Integer.parseInt(configurationView.getTimeTextField()
					.getText()));
		INBodiesSystemView systemView = new NBodiesSystemView(
			systemModel);
		new NBodiesViewController(systemModel, systemView);
		configurationView.closeView();

	    } else {
		// reset text fields and communicate the error
		configurationView.getNBodiesTextField().setText("0");
		configurationView.getTimeTextField().setText("0");
		configurationView.getCommunicationLabel().setForeground(
			Color.RED);
		configurationView.getCommunicationLabel().setText(
			"  ERROR: the values must be greather than zero.");
	    }
	} catch (Exception ex) {
	    ex.printStackTrace();
	    System.out.println("[Configurator] Error: " + ex.getMessage());
	    // reset text fields and communicate the error
	    configurationView.getNBodiesTextField().setText("0");
	    configurationView.getTimeTextField().setText("0");
	    configurationView.getCommunicationLabel().setForeground(Color.RED);
	    configurationView.getCommunicationLabel().setText(
		    "  ERROR: the values must be greather than zero.");
	}
    }
}
