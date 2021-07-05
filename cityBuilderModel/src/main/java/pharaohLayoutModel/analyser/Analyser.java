package cityBuilderModel.analyser;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map.Entry;

import cityBuilderModel.Map;
import cityBuilderModel.Map.Cell;

public class Analyser {

	public static class CellProperties {
		public int templeInfluence = 0;
		public int waterCarrier = 0;
		public int bazarAccess = 0;
		public int crimeLevel = 0;

		public CellProperties copy() {
			CellProperties props = new CellProperties();
			props.templeInfluence = this.templeInfluence;
			props.waterCarrier = this.waterCarrier;
			props.bazarAccess = this.bazarAccess;
			props.crimeLevel = this.crimeLevel;

			return props;
		}

		public void reset() {
			this.templeInfluence = 0;
			this.waterCarrier = 0;
			this.bazarAccess = 0;
			this.crimeLevel = 0;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();

			try {
				for (Field a : this.getClass().getFields()) {
					if (a.getType() == int.class) {
						int v = (Integer) a.get(this);

						if (v > 0) {
							sb.append(a.getName().substring(0, 1) + ":" + v + " ");
						}
					}
				}
			} catch (Exception e) {
				return "expn";
			}

			return sb.toString();
		}
	}

	public static void analyse(Map map) {
		for (Cell cell : map.getCells()) {
			cell.properties.reset();
		}

		for (Cell cell : map.getCells()) {
			if (cell.hasBuilding() && cell.building.hasFieldEffect() && cell.isBuildingCenter(map)) {
				Iterator<Entry<Cell, Double>> buildingCellList = map.getCellsWithinField(cell).entrySet().iterator();

				while (buildingCellList.hasNext()) {
					Entry<Cell, Double> current = buildingCellList.next();

					if (cell.hasBuilding() && (current.getValue() < cell.building.getFieldEffect())) {
						cell.building.applyProperties(current.getKey(), current.getValue());
					}
				}
			}
		}
	}

}
