package test.java.cityBuilderGaTest;

import org.junit.Test;

import cityBuilderGa.Population;

public class TestCreateRandomPopulation {
	@Test
	public void test() {
		Population pop = new Population();
		pop.fillRandom(5);

		System.out.println(pop);
	}
}
