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

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ChooseConfigFileConfigurationButtonListener implements
ActionListener {
    /**
     * Configuration view for the NBodies System.
     */
    NBodiesConfigurationView configurationView;

    /**
     * Constructor fo teh fileConfigurationButtonListener
     *
     * @param configurationView
     *            Configuration view for the NBodies System.
     */
    public ChooseConfigFileConfigurationButtonListener(
	    NBodiesConfigurationView configurationView) {
	this.configurationView = configurationView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	configurationView.getNBodiesTextField().setFocusable(false);

	if (Integer.parseInt(configurationView.getTimeTextField().getText()
		.trim()) > 0) {
	    JFileChooser chooser = new JFileChooser(getClass().getClassLoader()
		    .getResource(".").getPath());
	    chooser.setFileFilter(new FileNameExtensionFilter("TEXT FILES",
		    "txt", "text"));
	    chooser.setDialogTitle("Choose the configurator file");

	    int returnVal = chooser.showOpenDialog(null);

	    if (returnVal == JFileChooser.APPROVE_OPTION) {
		try {
		    // String configFile = new String(Files.readAllBytes(
		    // Paths.get(chooser.getSelectedFile().getPath())),
		    // StandardCharsets.UTF_8);

		    String configFile = chooser.getSelectedFile().getPath();
		    INBodiesSystemModel systemModel = new NBodiesSystemModel(
			    configFile, Integer.parseInt(configurationView
				    .getTimeTextField().getText()));
		    INBodiesSystemView systemView = new NBodiesSystemView(
			    systemModel);
		    new NBodiesViewController(systemModel, systemView);
		    configurationView.closeView();

		} catch (Exception ex) {
		    ex.printStackTrace();
		    configurationView.getCommunicationLabel().setText(
			    "  ERROR: cannot Load Configuration File");
		    System.out.println("[Configurator] Error: "
			    + ex.getMessage());
		}
	    }
	} else {
	    configurationView.getCommunicationLabel().setForeground(Color.RED);
	    configurationView
		    .getCommunicationLabel()
		    .setText(
			    "  ERROR: first insert an evolution time value for the system.");
	}
    }
}
