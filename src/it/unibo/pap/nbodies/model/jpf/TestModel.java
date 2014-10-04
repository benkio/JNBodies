package it.unibo.pap.nbodies.model.jpf;

import it.unibo.pap.nbodies.model.implementation.executor.NBodiesSystemModel;
import it.unibo.pap.nbodies.model.interfaces.INBodiesSystemModel;

public class TestModel {

	public static void main(String[] args) {
		INBodiesSystemModel model = new NBodiesSystemModel(2, 7000);

		model.doNextStep();
	}
}
