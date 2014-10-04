package it.unibo.pap.nbodies.controllers.implementation;

import java.util.concurrent.Semaphore;

import it.unibo.pap.nbodies.controllers.interfaces.INBodiesViewController;
import it.unibo.pap.nbodies.controllers.listeners.MouseClickedListener;
import it.unibo.pap.nbodies.controllers.listeners.MouseMotionListener;
import it.unibo.pap.nbodies.controllers.listeners.MouseWheelListener;
import it.unibo.pap.nbodies.controllers.listeners.NextStepButtonListener;
import it.unibo.pap.nbodies.controllers.listeners.ResetButtonListener;
import it.unibo.pap.nbodies.controllers.listeners.RestartButtonListener;
import it.unibo.pap.nbodies.controllers.listeners.StartButtonListener;
import it.unibo.pap.nbodies.controllers.listeners.PauseButtonListener;
import it.unibo.pap.nbodies.model.interfaces.INBodiesSystemModel;
import it.unibo.pap.nbodies.views.interfaces.INBodiesSystemView;

/**
 * Implementation of the n-bodies view's controller
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public class NBodiesViewController implements INBodiesViewController {

	/**
	 * The system's model
	 */
	protected INBodiesSystemModel model;
	/**
	 * The semaphore to signal the done step
	 */
	protected Semaphore signalStepSemaphore;
	/** 
	 * Thread to manage the system's view
	 */
	protected SystemViewerThread systemViewerThread;
	/** 
	 * Thread to manage the system's model
	 */
	protected SystemModelThread systemModelThread;
	
	/**
	 * Constructor that takes the bodies's model and the system view 
	 * to control as input parameter and it creates all the view 
	 * controls's controllers and add each controller to the view
	 * 
	 * @param bodiesModel : the bodies system's model
	 * @param view : the target view to control
	 */
	public NBodiesViewController(INBodiesSystemModel bodiesModel, INBodiesSystemView view) {
		Semaphore signalStepSemaphore = new Semaphore(0);
		model = bodiesModel;
		systemModelThread = new SystemModelThread(model, signalStepSemaphore);
		systemViewerThread = new SystemViewerThread(view, signalStepSemaphore);
		
		view.addStartButtonListener(new StartButtonListener(systemModelThread, systemViewerThread));
		view.addNextStepButtonListener(new NextStepButtonListener(systemModelThread, systemViewerThread));
		view.addPauseButtonListener(new PauseButtonListener(systemModelThread, systemViewerThread));
		view.addResetButtonListener(new ResetButtonListener(systemModelThread, systemViewerThread));
		view.addMouseClickedListener(new MouseClickedListener(systemViewerThread));
		view.addMouseMotionListener(new MouseMotionListener(systemViewerThread));
		view.addMouseWheelListener(new MouseWheelListener(systemViewerThread));
		view.addRestartButtonListener(new RestartButtonListener(view, systemModelThread, systemViewerThread));
		
		new Thread(systemModelThread).start();
		new Thread(systemViewerThread).start();
	}
}
