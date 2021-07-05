package cityBuilderModel.buildings;

import java.awt.Color;

import cityBuilderModel.Map.Cell;

public class Temple extends Building {
	public Temple() {
		super(1, 1, 'T');
	}

	@Override
	public void applyProperties(Cell cell, Double distance) {
		cell.properties.templeInfluence += (this.getFieldEffect() - distance.intValue());
	}

	@Override
	public Color getColor() {
		return new Color(86, 46, 120);
	}

	@Override
	public int getFieldEffect() {
		return 6;
	}
}
