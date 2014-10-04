package it.unibo.pap.nbodies.views.implementation;

import it.unibo.pap.nbodies.controllers.listeners.MouseClickedListener;
import it.unibo.pap.nbodies.model.interfaces.INBodiesSystemModel;
import it.unibo.pap.nbodies.views.interfaces.INBodiesSystemView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Implementation of the view to show the n-bodies system's window
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public class NBodiesSystemView implements INBodiesSystemView {

	/**
	 * The system's model
	 */
	protected INBodiesSystemModel model;
	/**
	 * Semaphore to synchronize the display
	 */
	private Semaphore paintSynchSemaphore;
	/**
	 * View's windows dimensions
	 */
	private Dimension windowsDimension;
	/**
	 * Master panel with all the GUI elements
	 */
	private JPanel masterPanel;
	/**
	 * Frame to show the view of the bodies in the system
	 */
	private JFrame frameSystem;
	/**
	 * Label to show the number of bodies involved in the system
	 */
	private JLabel lblNBodies;
	/**
	 * Console panel that contains the system view's buttons
	 */
	private JPanel controlConsole;
	/**
	 * Buttons to start the system
	 */
	private JButton btnStart;
	/**
	 * Buttons to proceed to the next system's runtime step
	 */
	private JButton btnNextStep;
	/**
	 * Buttons to put in pause the system's runtime
	 */
	private JButton btnPause;
	/**
	 * Buttons to reset the system's status to the initial state
	 */
	private JButton btnReset;
	/**
	 * Display of the system's bodies
	 */
	private BodiesSystemDisplay bodiesDisplay;
	/**
	 * Button used for the restart of the application
	 */
	private AbstractButton btnRestart;

	/**
	 * Constructor of the system view that take as input the bodies's system
	 * model
	 * 
	 * @param bodiesModel
	 *            : the nbodies's system model
	 */
	public NBodiesSystemView(INBodiesSystemModel bodiesModel) {
		model = bodiesModel;
		paintSynchSemaphore = new Semaphore(0);
		initializeGUI();
	}

	/**
	 * Method to initialize the GUI of the system's view
	 */
	private void initializeGUI() {

		// system's view windows dimensions
		windowsDimension = new Dimension(900, 700);

		// system's main frame and its properties
		frameSystem = new JFrame("NBodies System Viewer");
		frameSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameSystem.setResizable(false);
		frameSystem.setBackground(Color.white);

		// buttons and their properties
		btnNextStep = new JButton("Next Step");
		btnStart = new JButton("Start");
		btnStart.setSize(btnNextStep.getSize());
		btnPause = new JButton("Pause");
		btnPause.setSize(btnNextStep.getSize());
		btnReset = new JButton("Reset");
		btnReset.setSize(btnNextStep.getSize());
		btnRestart = new JButton("Restart Application");
		btnRestart.setSize(btnNextStep.getSize());

		// control console and its properties and internal buttons
		controlConsole = new JPanel();
		controlConsole.setBackground(new Color(180, 180, 180));
		controlConsole.add(btnStart);
		controlConsole.add(btnNextStep);
		controlConsole.add(btnPause);
		controlConsole.add(btnReset);
		controlConsole.add(btnRestart);

		// label related to the number of bodies involved in the system and its
		// properties
		lblNBodies = new JLabel("- Number of bodies: 0", JLabel.RIGHT);
		controlConsole.add(lblNBodies);

		// bodies's display and its properties (background color black to
		// simulate the space)
		bodiesDisplay = new BodiesSystemDisplay(model);
		bodiesDisplay.setBackground(new Color(0, 0, 0));

		// master panel and its layout's properties
		masterPanel = new JPanel();
		LayoutManager layout = new BorderLayout();
		masterPanel.setLayout(layout);
		masterPanel.add(BorderLayout.NORTH, controlConsole);
		masterPanel.add(BorderLayout.CENTER, bodiesDisplay);

		// view's main frame and its properties
		frameSystem.setContentPane(masterPanel);
		frameSystem.setSize(windowsDimension);
		frameSystem.setLocationRelativeTo(null);
		frameSystem.setVisible(true);
		
		bodiesDisplay.setUpBuffer();
	}

	@Override
	public void addNextStepButtonListener(ActionListener listener) {
		btnNextStep.addActionListener(listener);
	}

	@Override
	public void addPauseButtonListener(ActionListener listener) {
		btnPause.addActionListener(listener);
	}

	@Override
	public void addStartButtonListener(ActionListener listener) {
		btnStart.addActionListener(listener);
	}

	@Override
	public void addResetButtonListener(ActionListener listener) {
		btnReset.addActionListener(listener);
	}

	@Override
	public void addMouseClickedListener(
			MouseClickedListener mouseClickedListener) {
		bodiesDisplay.addMouseListener(mouseClickedListener);
	}

	@Override
	public void addMouseWheelListener(
			it.unibo.pap.nbodies.controllers.listeners.MouseWheelListener mouseWheelListener) {
		bodiesDisplay.addMouseWheelListener(mouseWheelListener);

	}

	@Override
	public void addMouseMotionListener(
			it.unibo.pap.nbodies.controllers.listeners.MouseMotionListener mouseMotionListener) {
		bodiesDisplay.addMouseMotionListener(mouseMotionListener);

	}

	@Override
	public Semaphore getSynchronizationPaintSemaphore() {
		return paintSynchSemaphore;
	}

	@Override
	public void reset() {
		bodiesDisplay.reset();
		bodiesDisplay.updateDisplayImage();
	}

	@Override
	public void updateViewImage() {
		bodiesDisplay.updateDisplayImage();
		lblNBodies.setText("- Number of bodies: " + model.getNumBodies());
	}

	@Override
	public void updateViewScale(int factor) {
		bodiesDisplay.setSizeScale(bodiesDisplay.getSizeScale()
				+ (factor * (bodiesDisplay.getSizeScale() / 10)));
	}

	@Override
	public void updateViewCenter(int x, int y) {
		bodiesDisplay.moveCenter(x, y);
	}

	@Override
	public void closeView() {
		frameSystem.dispose();
	}

	@Override
	public void addRestartButtonListener(ActionListener listener) {
		btnRestart.addActionListener(listener);
	}

}
