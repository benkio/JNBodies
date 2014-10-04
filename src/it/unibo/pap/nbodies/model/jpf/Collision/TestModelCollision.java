package it.unibo.pap.nbodies.model.jpf.Collision;

import it.unibo.pap.nbodies.model.implementation.executor.Body;
import it.unibo.pap.nbodies.model.interfaces.IBody;
import it.unibo.pap.nbodies.model.interfaces.INBodiesSystemModel;

import java.util.ArrayList;
import java.util.List;

public class TestModelCollision {

	public static void main(String[] args) {
		List<IBody> b = new ArrayList<IBody>();
		b.add(new Body(2));
		b.add(new Body(2));

		INBodiesSystemModel model = new NBodiesSystemModelOnlyCollision(2, 7000);

		model.doNextStep();
	}
}
