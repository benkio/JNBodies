package it.unibo.pap.nbodies.model.implementation;

import it.unibo.pap.nbodies.model.interfaces.ISpeed;
import it.unibo.pap.nbodies.model.interfaces.StaticModelData;

/**
 * Implementation of the speed two dimensions vector
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public class Speed extends Vector2D implements ISpeed {
	
	/**
	 * Constructor that initialize a two dimension vector that represents the speed with
	 * the intensity equals to the intensity of the Earth's speed vector and a random direction
	 */
	public Speed() {
		int sign = Math.round(Math.random()) == 0 ? 1 : -1;
		this.setComponents(Math.random() * StaticModelData.EARTH_SPEED * sign, 
				Math.random() * StaticModelData.EARTH_SPEED * sign);
	}

}
