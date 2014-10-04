package it.unibo.pap.nbodies.controllers.implementation;

import it.unibo.pap.nbodies.controllers.interfaces.ISystemEntityControl;
import it.unibo.pap.nbodies.model.enumerators.ModelStatusEnum;
import it.unibo.pap.nbodies.model.implementation.Position;
import it.unibo.pap.nbodies.model.interfaces.IPosition;
import it.unibo.pap.nbodies.views.interfaces.INBodiesSystemView;

import java.util.concurrent.Semaphore;

/**
 * Implementation of the n-bodies system viewer's controller
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public class SystemViewerThread implements ISystemEntityControl, Runnable {

	/**
	 * The position related to the mouse's click event
	 */
	protected IPosition clickPosition;
	/**
	 * The enumerator that represents the current system's status
	 */
	protected ModelStatusEnum systemStatus;
	/**
	 * The nbodies's system view to manage
	 */
	protected INBodiesSystemView systemView;
	/**
	 * The semaphore to signal the done execution step
	 */
	protected Semaphore signalStepSemaphore;
	/**
	 * The semaphore to synchronize the system's display view
	 */
	protected Semaphore paintGraphicSemaphore;
	/**
	 * The semaphore to manage the update of the view
	 */
	protected Semaphore viewSemaphore;

	/**
	 * Constructor that takes as argument the system model and the semaphore to
	 * signal the done of the step execution
	 * 
	 * @param bodiesView
	 *            : the bodies system's view
	 * @param stepSemaphore
	 *            : the semaphore to signal the done of the step execution
	 */
	public SystemViewerThread(INBodiesSystemView bodiesView,
			Semaphore stepSemaphore) {
		systemView = bodiesView;
		signalStepSemaphore = stepSemaphore;
		systemStatus = ModelStatusEnum.Stop;
		viewSemaphore = new Semaphore(0);
		paintGraphicSemaphore = systemView.getSynchronizationPaintSemaphore();
		clickPosition = new Position();
	}

	@Override
	public void signalStart() {
		if (systemStatus != ModelStatusEnum.Go) {
			systemStatus = ModelStatusEnum.Go;
			viewSemaphore.release();
		}
	}

	@Override
	public void signalStep() {
		if (systemStatus != ModelStatusEnum.Go) {
			viewSemaphore.release();
		}
		systemStatus = ModelStatusEnum.SingleStep;
	}

	@Override
	public void signalStop() {
		systemStatus = ModelStatusEnum.Stop;
	}

	@Override
	public void signalReset() {
		systemStatus = ModelStatusEnum.Stop;
		systemView.reset();
	}

	/**
	 * Method to notify to the viewer thread the mouse wheel event
	 * 
	 * @param wheelFactor
	 *            : factor of the mouse wheel
	 */
	public void notifyMouseWheel(int wheelFactor) {
		systemView.updateViewScale(wheelFactor);
		systemView.updateViewImage();
	}

	/**
	 * Method to notify to the viewer thread the mouse click event
	 * 
	 * @param x
	 *            : the x coordinate of the click
	 * @param y
	 *            : the y coordinate of the click
	 */
	public void notifyMouseClick(int x, int y) {
		clickPosition.setComponents(x, y);
	}

	/**
	 * Method to notify to the viewer thread the mouse drag event
	 * 
	 * @param x
	 *            : the x coordinate of the drag
	 * @param y
	 *            : the y coordinate of the drag
	 */
	public void notifyMouseDragged(int x, int y) {
		int distanceXAxe = (int) (clickPosition.getX() - x);
		int distanceYAxe = (int) (clickPosition.getY() - y);
		systemView.updateViewCenter(distanceXAxe, distanceYAxe);
		systemView.updateViewImage();
		clickPosition.setComponents(x, y);
	}

	@Override
	public void run() {
		while (true) {
			systemView.updateViewImage();
			// System.out.println("[Viewer Thread] Running");
			try {
				if (systemStatus.equals(ModelStatusEnum.Go)) {
					Thread.sleep(10);
					// System.out.println("[Viewer Thread] Running - Go");
					systemView.updateViewImage();
					// paintGraphicSemaphore.acquire();
				} else if (systemStatus.equals(ModelStatusEnum.SingleStep)) {
					// System.out.println("[Viewer Thread] Running - Single Step");
					signalStepSemaphore.acquire();
					systemView.updateViewImage();
					viewSemaphore.acquire();
				} else if (systemStatus.equals(ModelStatusEnum.Stop)) {
					// System.out.println("[Viewer Thread] Running - Stop");
					systemView.updateViewImage();
					viewSemaphore.acquire();
				}
			} catch (InterruptedException ex) {
				System.out
						.println("[Viewer Thread - Exception] Run Procedure: "
								+ ex.getMessage());
			}
		}
	}
}
