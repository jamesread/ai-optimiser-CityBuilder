package pharaohLayoutModel;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import pharaohLayoutModel.analyser.Analyser.CellProperties;
import pharaohLayoutModel.buildings.Bazar;
import pharaohLayoutModel.buildings.Building;
import pharaohLayoutModel.buildings.House;
import pharaohLayoutModel.buildings.PoliceStation;
import pharaohLayoutModel.buildings.Temple;
import pharaohLayoutModel.buildings.WaterCarrier;

public class Map {
	public static class BuildingOverlapException extends MapCoordException {
		public BuildingOverlapException(String message) {
			super(message);
		}
	}

	public static class Cell {
		public final int x;
		public final int y;
		public Building building;
		public CellProperties properties = new CellProperties();

		public Cell(final int x, final int y) {
			this.x = x;
			this.y = y;
		}

		public Cell copy() {
			Cell copy = new Cell(this.x, this.y);

			copy.building = this.building;
			copy.properties = this.properties.copy();

			return copy;
		}

		public boolean hasBuilding() {
			return this.building != null;
		}

		public boolean isBuildingCenter(Map map) {
			return this.hasBuilding();
		}

		@Override
		public String toString() {
			return "{x: " + this.x + " y: " + this.y + " b: " + this.building + "}";
		}

	}

	public static class MapCoordException extends Exception {
		public MapCoordException(String message) {
			super(message);
		}

		public MapCoordException(String message, Point point) {
			super(message + " Given: " + point.toString());
		}
	}

	public static Building getRandomBuilding() {
		Vector<Class<? extends Building>> buildings = new Vector<>();
		buildings.add(House.class);
		buildings.add(House.class);
		buildings.add(House.class);
		buildings.add(House.class);
		buildings.add(Temple.class);
		buildings.add(WaterCarrier.class);
		buildings.add(PoliceStation.class);
		buildings.add(Bazar.class);

		int index = (new Random()).nextInt(buildings.size());

		try {
			return buildings.get(index).newInstance();
		} catch (Exception e) {
			return new House();
		}
	}

	private final Cell[][] map;

	private final int w, h;

	public Map(int w, int h) {
		this.w = w;
		this.h = h;

		this.map = new Cell[w][h];

		for (int ix = 0; ix < this.getWidth(); ix++) {
			for (int iy = 0; iy < this.getHeight(); iy++) {
				this.map[ix][iy] = new Cell(ix, iy);
			}
		}
	}

	public void addBuilding(final int x, final int y, Building building) throws MapCoordException {
		this.checkMapCoords(x, y);

		if ((x + building.getWidth()) > this.getWidth()) {
			throw new BuildingOverlapException("edge of width of map");
		}

		if ((y + building.getHeight()) > this.getHeight()) {
			throw new BuildingOverlapException("edge of height of map");
		}

		building.firstCell = this.map[x][y];
		for (int ix = x; ix < (x + building.getWidth()); ix++) {
			for (int iy = y; iy < (y + building.getHeight()); iy++) {
				this.map[ix][iy].building = building;
			}
		}

	}

	private void checkMapCoords(int x, int y) throws MapCoordException {
		Point point = new Point(x, y);
		if (x < 0) {
			throw new MapCoordException("X must be positive.", point);
		}

		if (y < 0) {
			throw new MapCoordException("Y must be positive.", point);
		}

		if (x > this.getWidth()) {
			throw new MapCoordException("X extends off width of map", point);
		}

		if (y > this.getHeight()) {
			throw new MapCoordException("Y extends off height of map", point);
		}
	}

	public Map copy() {
		Map copy = new Map(this.w, this.h);

		for (Cell c : this.getCells()) {
			copy.map[c.x][c.y] = c.copy();
		}

		return copy;
	}

	public void fillRandom(int max) {
		for (int buildingCount = 0; buildingCount < max;) {
			Building b = Map.getRandomBuilding();

			Cell cell = this.getRandomCell();

			if (!cell.hasBuilding()) {
				cell.building = b;
				buildingCount++;
			}
		}
	}

	public void fillWith(Class<? extends Building> class1) throws Exception {
		for (Cell cell : this.getCells()) {
			cell.building = class1.newInstance();
		}
	}

	public <T extends Building> Vector<Cell> getBuildings(Class<T> types) {
		Vector<Cell> ret = new Vector<Cell>();

		for (Cell cell : this.getCells()) {
			if (cell.hasBuilding()) {
				if (cell.building.getClass() == types) {
					ret.add(cell);
				}
			}
		}

		return ret;
	}

	public Cell getCell(int i, int j) {
		return this.map[i][j];
	}

	public List<Cell> getCells() {
		ArrayList<Cell> cells = new ArrayList<Cell>();

		for (int ix = 0; ix < this.getWidth(); ix++) {
			for (int iy = 0; iy < this.getHeight(); iy++) {
				cells.add(this.map[ix][iy]);
			}
		}

		return cells;
	}

	public HashMap<Cell, Double> getCellsWithinField(Cell origin) {
		Building building = origin.building;
		Cell middleCell = building.getMiddleCell(this, origin);

		HashMap<Cell, Double> list = new HashMap<>();

		for (Cell cell : this.getCells()) {
			double d = this.planeDistance(cell.x, cell.y, middleCell.x, middleCell.y);

			list.put(cell, d);
		}

		return list;
	}

	public int getHeight() {
		return this.map[0].length;
	}

	public Cell getRandomCell() {
		Random random = new Random();
		int x = random.nextInt(this.w);
		int y = random.nextInt(this.h);

		return this.map[x][y];
	}

	public MapRatings getRatings() {
		MapRatings ratings = new MapRatings();

		for (Cell cell : this.getBuildings(House.class)) {
			ratings.prosperity += House.calculateLevel(cell.properties).value;
			ratings.population += House.calculateLevel(cell.properties).populationCapacity;
		}

		return ratings;
	}

	public int getWidth() {
		return this.map.length;
	}

	public boolean hasBuilding(Building buildingSearch, int x, int y) {
		Building b = this.map[x][y].building;

		if (b == null) {
			return false;
		} else if (b == buildingSearch) {
			return false;
		} else {
			return true;
		}
	}

	public double planeDistance(int xa, int ya, int xb, int yb) {
		return new Point(xa, ya).distance(new Point(xb, yb));
	}

	public void setCell(int x, int y, Cell cell2) {
		this.map[x][y] = cell2;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\n");

		for (int ix = 0; ix < this.getWidth(); ix++) {
			for (int iy = 0; iy < this.getHeight(); iy++) {
				Building b = this.map[ix][iy].building;

				if (b == null) {
					sb.append("-");
					// sb.append(this.map[ix][iy].properties.bazarAccess);
				} else {
					sb.append(b.toChar());
				}
			}

			sb.append("\n");
		}

		return sb.toString();
	}
}
