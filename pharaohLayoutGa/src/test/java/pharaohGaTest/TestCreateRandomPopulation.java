package test.java.pharaohGaTest;

import org.junit.Test;

import pharaohLayoutGa.Population;

public class TestCreateRandomPopulation {
	@Test
	public void test() {
		Population pop = new Population();
		pop.fillRandom(5);

		System.out.println(pop);
	}
}
