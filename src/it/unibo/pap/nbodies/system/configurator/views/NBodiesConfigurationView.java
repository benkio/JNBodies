package it.unibo.pap.nbodies.system.configurator.views;

import it.unibo.pap.nbodies.system.configurator.controllers.SystemConfigurationViewController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NBodiesConfigurationView {

	/**
	 * Master panel with all the GUI elements
	 */
	private JPanel masterPanel;
	/**
	 * Frame to show the view of the n-bodies system configuration view
	 */
	private JFrame frameSystem;
	/**
	 * Label and text field to insert the number of bodies involved in the
	 * system
	 */
	private JLabel lblNBodies;
	private JTextField txtNBodies;
	/**
	 * Label and text field to set the time tick of the runtime evolution of the
	 * system
	 */
	private JLabel lblTime;
	private JTextField txtTime;
	/**
	 * Console panel that contains the number of bodies view's controls
	 */
	private JPanel numberBodiesControlConsole;
	/**
	 * Console panel that contains the time tick of the system view's controls
	 */
	private JPanel timeControlConsole;
	/**
	 * Button to start the n-bodies system
	 */
	private JButton btnStartSystem;
	/**
	 * Console panel that contains the start button and the information (and
	 * errors) communication controls
	 */
	private JPanel startAndCommunicationConsole;
	/**
	 * Label to show input errors
	 */
	private JLabel lblCommunication;
	/**
	 * Button to choose a configuration file
	 */
	private JButton btnOpenFileConfig;

	/**
	 * Constructor of the system view
	 */
	public NBodiesConfigurationView() {
		initializeGUI();
	}

	/**
	 * Method to initialize the GUI of the system's configuration view
	 */
	private void initializeGUI() {

		// system's main frame and its properties
		frameSystem = new JFrame("NBodies System Configuration");
		frameSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameSystem.setResizable(false);
		frameSystem.setBackground(Color.white);

		// number of bodies controls and their properties
		lblNBodies = new JLabel("   Insert the system bodies's number: ");
		lblNBodies.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		txtNBodies = new JTextField("0");
		txtNBodies.setAlignmentX(JTextField.CENTER_ALIGNMENT);

		// time controls and their properties
		lblTime = new JLabel("   Insert the system's evolution time:  ");
		lblTime.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		txtTime = new JTextField("0");
		txtTime.setAlignmentX(JTextField.CENTER_ALIGNMENT);

		// number of bodies console and its properties and internal controls
		numberBodiesControlConsole = new JPanel();
		numberBodiesControlConsole.setBounds(0, 0, 550, 28);
		numberBodiesControlConsole.setBackground(Color.white);
		LayoutManager numberLayout = new BorderLayout();
		numberBodiesControlConsole.setLayout(numberLayout);
		numberBodiesControlConsole.add(lblNBodies, BorderLayout.WEST);
		numberBodiesControlConsole.add(txtNBodies, BorderLayout.CENTER);

		// time console and its properties and internal controls
		timeControlConsole = new JPanel();
		timeControlConsole.setBounds(0, 28, 550, 30);
		timeControlConsole.setBackground(Color.white);
		LayoutManager timeLayout = new BorderLayout();
		timeControlConsole.setLayout(timeLayout);
		timeControlConsole.add(lblTime, BorderLayout.WEST);
		timeControlConsole.add(txtTime, BorderLayout.CENTER);

		// start and communication console and its properties and internal
		// controls
		startAndCommunicationConsole = new JPanel();
		startAndCommunicationConsole.setBounds(0, 56, 550, 58);
		startAndCommunicationConsole.setBackground(Color.white);
		LayoutManager startLayout = new BorderLayout();
		startAndCommunicationConsole.setLayout(startLayout);

		// master panel and its layout's properties
		masterPanel = new JPanel();
		masterPanel.setLayout(null);
		masterPanel.add(numberBodiesControlConsole);
		masterPanel.add(timeControlConsole);
		masterPanel.add(startAndCommunicationConsole);

		// button to start the n-bodies system and its properties
		btnStartSystem = new JButton("Start the n-bodies system");
		startAndCommunicationConsole.add(btnStartSystem, BorderLayout.CENTER);

		btnOpenFileConfig = new JButton(
				"Start System from a Configuration File");
		SystemConfigurationViewController controller = new SystemConfigurationViewController(
				this);
		startAndCommunicationConsole.add(btnOpenFileConfig, BorderLayout.NORTH);
		btnOpenFileConfig.addActionListener(controller
				.getChooseConfigurationFileListener());
		btnStartSystem.addActionListener(controller.getStartListener());

		// view's main frame and its properties
		frameSystem.setContentPane(masterPanel);

		// label to show errors and its properties
		lblCommunication = new JLabel(
				"  Please insert the n-bodies system's configuration values.");
		lblCommunication.setBounds(0, 114, 550, 36);
		masterPanel.add(lblCommunication);
		lblCommunication.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		lblCommunication.setForeground(Color.BLUE);
		frameSystem.setSize(new Dimension(550, 181));
		frameSystem.setLocationRelativeTo(null);
		frameSystem.setVisible(true);
	}

	/**
	 * Method to get the configuration view's n-bodies text field
	 */
	public JTextField getNBodiesTextField() {
		return this.txtNBodies;
	}

	/**
	 * Method to get the configuration view's time text field
	 */
	public JTextField getTimeTextField() {
		return this.txtTime;
	}

	/**
	 * Method to get the start system button
	 */
	public JButton getChooseConfigFileButton() {
		return this.btnOpenFileConfig;
	}

	/**
	 * Method to get the choose file configuration system button
	 */
	public JButton getStartButton() {
		return this.btnStartSystem;
	}

	/**
	 * Method to get the configuration view's errors and informations text field
	 */
	public JLabel getCommunicationLabel() {
		return this.lblCommunication;
	}

	/**
	 * method used for closing the view
	 */
	public void closeView() {
		frameSystem.dispose();
	}

}
