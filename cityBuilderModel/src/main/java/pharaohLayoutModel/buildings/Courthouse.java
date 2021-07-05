package cityBuilderModel.buildings;

import cityBuilderModel.Map.Cell;

public class Courthouse extends Building {
	public Courthouse() {
		super(2, 2);
	}

	@Override
	public void applyProperties(Cell cell, Double distance) {
		cell.properties.crimeLevel -= (this.getFieldEffect() - distance.intValue());
	}

	@Override
	public int getFieldEffect() {
		return 5;
	}
}
