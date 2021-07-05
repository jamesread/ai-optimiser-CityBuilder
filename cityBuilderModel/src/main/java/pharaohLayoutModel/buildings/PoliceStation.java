package cityBuilderModel.buildings;

import java.awt.Color;

import cityBuilderModel.Map.Cell;

public class PoliceStation extends Building {
	public PoliceStation() {
		super(1, 1, 'P');
	}

	@Override
	public void applyProperties(Cell cell, Double distance) {
		cell.properties.crimeLevel -= (this.getFieldEffect() - distance.intValue());
	}

	@Override
	public Color getColor() {
		return new Color(75, 120, 255);
	}

	@Override
	public int getFieldEffect() {
		return 4;
	}
}
