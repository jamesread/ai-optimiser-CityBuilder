package pharaohLayoutModel.buildings;

import java.awt.Color;

import pharaohLayoutModel.Map.Cell;
import pharaohLayoutModel.analyser.Analyser.CellProperties;

public class House extends Building {

	public enum HouseLevel {
		HUT(1, 4), SHANTY(2, 6), COTTAGE(4, 8), HOMESTEAD(8, 12), APARTMENT(16, 24), RESIDENCE(32, 60), MANOR(64, 128), ESTATE(128, 256);

		public int value;
		public int populationCapacity = 0;

		HouseLevel(int value, int populationCapacity) {
			this.value = value;
			this.populationCapacity = populationCapacity;
		}
	}

	public static HouseLevel calculateLevel(CellProperties props) {
		if ((props.bazarAccess > 2) && (props.waterCarrier > 1) && (props.templeInfluence > 2) && (props.crimeLevel < 0)) {
			return HouseLevel.ESTATE;
		} else if ((props.bazarAccess > 1) && (props.waterCarrier > 1) && (props.templeInfluence > 1)) {
			return HouseLevel.MANOR;
		} else if ((props.bazarAccess > 1) && (props.waterCarrier > 1)) {
			return HouseLevel.COTTAGE;
		} else if (props.bazarAccess >= 1) {
			return HouseLevel.SHANTY;
		} else {
			return HouseLevel.HUT;
		}
	}

	public House() {
		super(1, 1, 'H');
	}

	@Override
	public Color getColor() {
		return new Color(193, 242, 63);
	}

	@Override
	public String toString(Cell c) {
		return "" + calculateLevel(c.properties);
	}

}
