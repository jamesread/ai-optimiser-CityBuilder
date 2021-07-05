package cityBuilderModel.buildings;

import java.awt.Color;

import cityBuilderModel.Map.Cell;

public class WaterCarrier extends Building {
	public WaterCarrier() {
		super(1, 1, 'W');
	}

	@Override
	public void applyProperties(Cell cell, Double distance) {
		cell.properties.waterCarrier += (this.getFieldEffect() - distance.intValue());
	}

	@Override
	public Color getColor() {
		return Color.BLUE;
	}

	@Override
	public int getFieldEffect() {
		return 3;
	}
}
