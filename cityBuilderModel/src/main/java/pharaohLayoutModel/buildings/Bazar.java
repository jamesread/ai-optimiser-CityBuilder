package cityBuilderModel.buildings;

import java.awt.Color;

import cityBuilderModel.Map.Cell;

public class Bazar extends Building {
	public Bazar() {
		super();
		this.representationChar = 'B';
	}

	@Override
	public void applyProperties(Cell cell, Double distance) {
		int d = distance.intValue();
		cell.properties.bazarAccess += (this.getFieldEffect() - d);
	}

	@Override
	public Color getColor() {
		return new Color(255, 52, 189);
	}

	@Override
	public int getFieldEffect() {
		return 5;
	}

}
