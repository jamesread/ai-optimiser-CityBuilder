package test.java.cityBuilderGaTest;

import org.junit.Test;

import cityBuilderGa.Algorithm;
import cityBuilderModel.Map;

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
