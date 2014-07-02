package pharaohTests;

import junit.framework.Assert;

import org.junit.Test;

import pharaohLayoutModel.Map;
import pharaohLayoutModel.Map.MapCoordException;
import pharaohLayoutModel.analyser.Analyser;
import pharaohLayoutModel.buildings.Bazar;
import pharaohLayoutModel.buildings.Courthouse;
import pharaohLayoutModel.buildings.House;
import pharaohLayoutModel.buildings.PoliceStation;
import pharaohLayoutModel.buildings.Temple;
import pharaohLayoutModel.buildings.WaterCarrier;
import pharaohLayoutModel.buildings.House.HouseLevel;
import pharaohLayoutModel.renderer.MapRenderer;
import pharaohLayoutModel.renderer.WindowRenderMap;

public class AnalyiserTest {
	@Test
	public void test() throws Exception {
		Map map = new Map(10, 10);
		map.addBuilding(1, 1, new Temple());
		map.addBuilding(5, 1, new Temple());
		map.addBuilding(4, 4, new House());
		map.addBuilding(6, 4, new House());
		map.addBuilding(7, 6, new House());
		map.addBuilding(4, 7, new WaterCarrier());
		map.addBuilding(2, 6, new Bazar());
		map.addBuilding(0, 0, new PoliceStation());
		map.addBuilding(8, 8, new Courthouse());

		Analyser.analyse(map);

		WindowRenderMap wnd = new WindowRenderMap(map);
		MapRenderer renderer = wnd.getRenderer();
		renderer.shouldPaintBuildingLabels = true;
		renderer.shouldFakeIsomorphic = false;

		Assert.assertEquals(8, map.getCell(1, 1).properties.templeInfluence);
		Assert.assertEquals(5, map.getCell(4, 4).properties.templeInfluence);
		Assert.assertEquals(0, map.getCell(7, 7).properties.templeInfluence);
		Assert.assertEquals(0, map.getCell(9, 9).properties.templeInfluence);

		Assert.assertEquals(House.class, map.getCell(4, 4).building.getClass());

		Assert.assertEquals(HouseLevel.SHANTY, House.calculateLevel(map.getCell(4, 4).properties));
	}

	@Test
	public void test2() throws MapCoordException {
		Map map = new Map(10, 10);
		map.addBuilding(1, 1, new House());
		map.addBuilding(2, 2, new Bazar());

		Analyser.analyse(map);

		Assert.assertEquals(HouseLevel.SHANTY, House.calculateLevel(map.getCell(1, 1).properties));
	}
}
