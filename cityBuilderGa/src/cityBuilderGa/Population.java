package cityBuilderGa;

import java.util.Random;
import java.util.Vector;

import cityBuilderModel.Map;

public class Population extends Vector<Map> {
	public static int mapWidth = 10;
	public static int mapHeight = 10;

	public Population() {
		this(Args.populationSize, false);
	}

	public Population(int size) {
		this(size, false);
	}

	public Population(int size, boolean fillRandom) {
		super(size);

		if (fillRandom) {
			this.fillRandom(size);
		}
	}

	public void fillRandom(int count) {
		for (int i = 0; i < count; i++) {
			Map map = new Map(Population.mapWidth, Population.mapHeight);
			map.fillRandom(5);

			this.add(map);
		}
	}

	public Map getFittest() {
		int localMax = -1;
		Map fittist = null;

		for (Map map : this) {
			int mapFitnessRating = Algorithm.getFitnessRating(map);

			if (mapFitnessRating > localMax) {
				localMax = mapFitnessRating;
				fittist = map;
			}
		}

		return fittist;
	}

	public Map getRandom() {
		Random rnd = new Random();
		int idx = rnd.nextInt(this.size());

		return this.get(idx);
	}
}
