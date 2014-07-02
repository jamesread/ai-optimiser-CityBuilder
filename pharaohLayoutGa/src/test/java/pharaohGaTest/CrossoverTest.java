package test.java.pharaohGaTest;

import org.junit.Test;

import pharaohLayoutGa.Algorithm;
import pharaohLayoutModel.Map;
import pharaohLayoutModel.analyser.Analyser;

public class CrossoverTest {
	@Test
	public void testCrossover() throws Exception {
		new Analyser();

		Map map1 = new Map(10, 10);
		map1.fillRandom(100);

		Map map2 = new Map(10, 10);
		map2.fillRandom(100);

		Map result = Algorithm.crossover(map1, map2);

		System.out.println(result);

		Analyser.analyse(map1);
		System.out.println(Algorithm.getFitnessRating(map1));

		Analyser.analyse(map2);
		System.out.println(Algorithm.getFitnessRating(map2));

		Analyser.analyse(result);
		System.out.println(Algorithm.getFitnessRating(result));
	}
}
