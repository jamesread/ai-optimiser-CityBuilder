package test.java.pharaohGaTest;

import org.junit.Test;

import pharaohLayoutGa.Algorithm;
import pharaohLayoutModel.Map;

public class TestFillBuildings {
	@Test
	public void foo() {
		Map map = new Map(10, 10);

		for (int i = 0; i < 1000; i++) {
			map = Algorithm.mutate(map);
			System.out.println(map);
		}
	}
}
