package pharaohTests;

import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

import pharaohLayoutModel.Map;
import pharaohLayoutModel.Map.Cell;
import pharaohLayoutModel.Map.MapCoordException;
import pharaohLayoutModel.analyser.Analyser;
import pharaohLayoutModel.buildings.Bazar;
import pharaohLayoutModel.renderer.WindowRenderMap;

public class TestFields {
	@Test
	public void testField() throws MapCoordException, InterruptedException {
		Map map = new Map(10, 10);
		map.addBuilding(5, 5, new Bazar());

		Vector<Cell> buildings = map.getBuildings(Bazar.class);
		Assert.assertEquals(1, buildings.size());

		Cell theBazar = buildings.firstElement();

		Assert.assertEquals(5, theBazar.x);
		Assert.assertEquals(5, theBazar.y);
		Assert.assertTrue(theBazar.isBuildingCenter(map));

		Analyser.analyse(map);

		System.out.println(map);

		Assert.assertEquals(5, map.getCell(5, 5).properties.bazarAccess);

		Assert.assertEquals(0, map.getCell(1, 1).properties.bazarAccess);

		// away left
		Assert.assertEquals(4, map.getCell(4, 5).properties.bazarAccess);
		Assert.assertEquals(3, map.getCell(3, 5).properties.bazarAccess);
		Assert.assertEquals(2, map.getCell(2, 5).properties.bazarAccess);
		Assert.assertEquals(1, map.getCell(1, 5).properties.bazarAccess);

		// away right
		Assert.assertEquals(4, map.getCell(6, 5).properties.bazarAccess);
		Assert.assertEquals(3, map.getCell(7, 5).properties.bazarAccess);
		Assert.assertEquals(2, map.getCell(8, 5).properties.bazarAccess);

		WindowRenderMap wrm = new WindowRenderMap(map);
		wrm.join();
	}
}
