package cityBuilderModelTests;

import junit.framework.Assert;

import org.junit.Test;

import cityBuilderModel.Map;
import cityBuilderModel.Map.MapCoordException;
import cityBuilderModel.buildings.Building;

public class MapTests {

	@Test
	public void testEdgeBuildings() throws Exception {
		Building building = new Building(2, 2);

		Map map = new Map(5, 5);
		map.addBuilding(0, 0, building);

		map.hasBuilding(building, 1, 1);
	}

	@Test(expected = Map.MapCoordException.class)
	public void testAddBuildingIntoVoid() throws Exception {
		Map map = new Map(5, 5);
		map.addBuilding(-1, -1, new Building());
	}

	@Test
	public void testToString() throws Exception {
		Map map = new Map(5, 5);
		map.addBuilding(2, 2, new Building(2, 2, 'A'));
		map.addBuilding(4, 4, new Building(1, 1, 'B'));

		System.out.println(map.toString());
	}

	@Test
	public void testDistances() throws MapCoordException {
		Map map = new Map(10, 10);

		Assert.assertEquals(1.0, map.planeDistance(1, 1, 1, 2));
		Assert.assertEquals(0.0, map.planeDistance(1, 1, 1, 1));
		Assert.assertEquals(2.23606797749979, map.planeDistance(3, 3, 1, 2));
	}
}
