package it.unibo.pap.nbodies.controllers.implementation;

import java.util.concurrent.Semaphore;

import it.unibo.pap.nbodies.controllers.interfaces.ISystemEntityControl;
import it.unibo.pap.nbodies.model.enumerators.ModelStatusEnum;
import it.unibo.pap.nbodies.model.interfaces.INBodiesSystemModel;

/**
 * Implementation of the n-bodies system model's controller
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public class SystemModelThread implements ISystemEntityControl, Runnable {

	/**
	 * The system's model
	 */
	protected INBodiesSystemModel model;
	/**
	 * The semaphore to signal the done step
	 */
	protected Semaphore signalStepSemaphore;
	/**
	 * The semaphore to manage the model execution
	 */
	protected Semaphore modelSemaphore;
	/**
	 * The enumerator that represents the current system's status
	 */
	protected ModelStatusEnum systemStatus;
	
	/**
	 * Constructor that takes as argument the system model and the semaphore to signal
	 * the done of the step execution
	 * @param bodiesModel : the bodies system's model
	 * @param stepSemaphore : the semaphore to signal the done of the step execution
	 */
	public SystemModelThread(INBodiesSystemModel bodiesModel, Semaphore stepSemaphore) {
		model = bodiesModel;
		signalStepSemaphore = stepSemaphore;
		modelSemaphore = new Semaphore(0);
		systemStatus = ModelStatusEnum.Stop;
	}
	
	@Override
	public void signalStart() {
		if(systemStatus.equals(ModelStatusEnum.SingleStep) || 
				systemStatus.equals(ModelStatusEnum.Stop)){
			modelSemaphore.release();
		}
		systemStatus = ModelStatusEnum.Go;
	}

	@Override
	public void signalStep() {
		if(systemStatus.equals(ModelStatusEnum.SingleStep) || 
				systemStatus.equals(ModelStatusEnum.Stop)){
			modelSemaphore.release();
		}
		systemStatus = ModelStatusEnum.SingleStep;
	}

	@Override
	public void signalStop() {
		try {
			if(systemStatus.equals(ModelStatusEnum.Go)){
				modelSemaphore.acquire();
			}
			systemStatus = ModelStatusEnum.Stop;
		} catch(InterruptedException ex) {
			System.out.println("[Model Thread - Exception] Signal Stop Procedure: " + ex.getMessage());
		}
	}

	@Override
	public void signalReset() {
		try {
			if(systemStatus.equals(ModelStatusEnum.Go)){
				modelSemaphore.acquire();
			}
			systemStatus = ModelStatusEnum.Stop;
			model.reset();
		} catch(InterruptedException ex) {
			System.out.println("[Model Thread - Exception] Signal Reset Procedure: " + ex.getMessage());
		}
	}

	@Override
	public void run() {
		while(true){
			try {
				modelSemaphore.acquire();
				//System.out.println("[Model Thread] Running");
				model.doNextStep();
				if(systemStatus.equals(ModelStatusEnum.Go)){
					modelSemaphore.release();
				} else if(systemStatus.equals(ModelStatusEnum.SingleStep)){
					signalStepSemaphore.release();
				}
				Thread.sleep(0);
			} catch(InterruptedException ex) {
				System.out.println("[Model Thread - Exception] Run Procedure: " + ex.getMessage());
			}
		}
	}

}
