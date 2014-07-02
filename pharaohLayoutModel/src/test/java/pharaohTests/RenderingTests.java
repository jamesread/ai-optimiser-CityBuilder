package pharaohTests;

import org.junit.Test;

import pharaohLayoutModel.Map;
import pharaohLayoutModel.buildings.House;
import pharaohLayoutModel.buildings.Temple;
import pharaohLayoutModel.renderer.WindowRenderMap;

public class RenderingTests {
	@Test
	public void test() throws Exception {
		Map map = new Map(10, 10);
		map.addBuilding(0, 0, new Temple());
		map.addBuilding(5, 5, new Temple());
		map.addBuilding(2, 6, new House());

		new WindowRenderMap(map);

		System.out.println(map.toString());

		Thread.currentThread().join();
	}
}
