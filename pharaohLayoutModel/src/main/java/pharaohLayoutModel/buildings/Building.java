package pharaohLayoutModel.buildings;

import java.awt.Color;

import pharaohLayoutModel.Map;
import pharaohLayoutModel.Map.Cell;

public class Building {
	protected final int width;
	protected final int height;
	protected char representationChar;

	public Cell firstCell;

	public Building() {
		this(1, 1);
	}

	public Building(int w, int h) {
		this(w, h, '?');
	}

	public Building(int w, int h, char representationChar) {
		this.width = w;
		this.height = h;
		this.representationChar = representationChar;
	}

	public void applyProperties(Cell cell, Double value) {
	}

	public Color getColor() {
		return Color.ORANGE;
	}

	public int getFieldEffect() {
		return 0;
	}

	public final int getHeight() {
		return this.height;
	}

	public Cell getMiddleCell(Map map, Cell origin) {
		int centerX = origin.x;
		int centerY = origin.y;
		return map.getCell(centerX, centerY);
	}

	public final int getWidth() {
		return this.width;
	}

	public boolean hasFieldEffect() {
		return this.getFieldEffect() > 0;
	}

	public char toChar() {
		return this.representationChar;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	public String toString(Cell c) {
		return this.toString();
	}
}
