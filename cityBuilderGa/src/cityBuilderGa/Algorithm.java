package cityBuilderGa;

import java.util.Random;

import cityBuilderModel.Map;
import cityBuilderModel.Map.Cell;
import cityBuilderModel.MapRatings;
import cityBuilderModel.analyser.Analyser;

public class Algorithm {
	private static final Random random = new Random();

	public static Map crossover(Map p1, Map p2) {
		Map o = new Map(p1.getWidth(), p1.getHeight());

		// crossoverRandom(p1, p2, o);
		crossoverSequence(p1, p2, o);

		return o;
	}

	private static void crossoverRandom(Map p1, Map p2, Map o) {
		Cell c;

		for (int x = 0; x < p1.getWidth(); x++) {
			for (int y = 0; y < p1.getHeight(); y++) {
				if (random.nextBoolean()) {
					c = p1.getCell(x, y);
				} else {
					c = p2.getCell(x, y);
				}

				o.setCell(x, y, c);
			}
		}
	}

	private static void crossoverSequence(Map p1, Map p2, Map o) {
		int s = Math.round((float) Math.random() * o.getCells().size());
		int f = Math.min(s + Math.round(((float) Math.random() * 10)), o.getCells().size());

		for (Cell c : p1.getCells()) {
			o.setCell(c.x, c.y, c);
		}

		for (int i = s; i < f; i++) {
			Cell c = p2.getCells().get(i);
			o.setCell(c.x, c.y, c);
		}
	}

	public static Population evolve(Population pop) {
		Population next = new Population();
		next.add(pop.getFittest());

		for (int i = 0; i < pop.size(); i++) {
			Map a = tournamentSelection(pop);
			Map b = tournamentSelection(pop);

			Map c = crossover(a, b);
			c = mutate(c);

			// System.out.println(getFitnessRating(a) + " + " +
			// getFitnessRating(b) + " = " + getFitnessRating(c));

			next.add(c);
		}

		return next;
	}

	public static int getFitnessRating(Map map) {
		Analyser.analyse(map);

		MapRatings ratings = map.getRatings();

		return ratings.prosperity;// + ratings.population;
	}

	public static Map mutate(Map map) {
		for (int i = 0; i < 3; i++) {
			Cell cell = map.getRandomCell();

			if (random.nextDouble() > .7) {
				cell.building = null;
			} else {
				cell.building = Map.getRandomBuilding();
			}
		}

		return map;
	}

	private static Map tournamentSelection(Population source) {
		Population competition = new Population(Args.tournamentSize);

		for (int i = 0; i < Args.tournamentSize; i++) {
			Map rnd = source.getRandom();

			competition.add(rnd);
		}

		return competition.getFittest();
	}
}
