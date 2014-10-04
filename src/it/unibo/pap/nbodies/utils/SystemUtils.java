package it.unibo.pap.nbodies.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import it.unibo.pap.nbodies.model.implementation.Position;
import it.unibo.pap.nbodies.model.implementation.Vector2D;
import it.unibo.pap.nbodies.model.implementation.executor.Body;
import it.unibo.pap.nbodies.model.interfaces.INBodiesSystemModel;
import it.unibo.pap.nbodies.model.interfaces.IPosition;
import it.unibo.pap.nbodies.model.interfaces.IVector2D;

/**
 * Implementation of the system's general utilities functions
 * 
 * @author Foschini Federico
 * @author Farneti Thomas
 * @author Benini Enrico
 * 
 */
public class SystemUtils {

	public void loadBodiesFromFile(String fileName, INBodiesSystemModel model) {
		try {
			String line;
			int i = 0, nBodies = 0;
			IPosition pos;
			IVector2D speed;
			double mass;
			BufferedReader buffReader = new BufferedReader(new FileReader(fileName));
			String[] planetsLine = new String[5];
			
			// read and set the bodies's number
			do {
				line = buffReader.readLine();
			} while (line.isEmpty());
			line = line.trim();
			nBodies = Integer.parseInt(line);
			model.setNumBodies(nBodies);

			// read and set the system's visualization scale
			do {
				line = buffReader.readLine();
			} while (line.isEmpty());
			line = line.trim();
			model.setScale((long) Double.parseDouble(line));

			// read and set each body in the model
			while (((line = buffReader.readLine()) != null) && i < nBodies) {
				line = line.replaceAll("\\s+", " ");
				line = line.trim();
				if (!line.isEmpty()) {
					planetsLine = line.split(" ");

					mass = Double.parseDouble(planetsLine[4]);
					pos = new Position(Double.parseDouble(planetsLine[0]),
							Double.parseDouble(planetsLine[1]));
					speed = new Vector2D(Double.parseDouble(planetsLine[2]),
							Double.parseDouble(planetsLine[3]));
					model.getBodies().add(new Body(nBodies, mass, pos, speed));
					i++;

				}
			}
			buffReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
