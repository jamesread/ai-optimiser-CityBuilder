package cityBuilderModel.renderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import cityBuilderModel.Map;
import cityBuilderModel.Map.Cell;

public class MapRenderer extends JComponent {
	private Map map;
	private double scaledWidth;
	private double scaledHeight;

	private int cellWidth;
	private int cellHeight;

	public boolean shouldPaintBuildingLabels = true;
	public boolean shouldFakeIsomorphic = true;

	private final int OFFSET_LABEL_X = 10;

	public MapRenderer(Map map) {
		this.map = map;
	}

	private boolean isDark(Color c) {
		return Math.sqrt((c.getRed() * c.getRed() * .241) + (c.getGreen() * c.getGreen() * .691) + (c.getBlue() * c.getBlue() * .068)) > 130;
	}

	private void paintBorder(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, (int) this.scaledWidth, (int) this.scaledHeight);
	}

	private void paintCellContents(Graphics2D g, final double w, final double h) {
		Color foreground;

		for (Cell c : this.map.getCells()) {
			foreground = Color.BLACK;

			if (c.hasBuilding()) {
				if (!this.isDark(c.building.getColor())) {
					foreground = Color.WHITE;
				}

				g.setColor(c.building.getColor());
				g.fillRect(c.x * this.cellWidth, c.y * this.cellHeight, this.cellWidth, this.cellHeight);

				g.setColor(foreground);

				if (this.shouldPaintBuildingLabels) {
					g.drawString("" + c.building.toString(c), (c.x * this.cellWidth) + this.OFFSET_LABEL_X, (c.y * this.cellHeight) + 45);
				}
			}

			g.setColor(foreground);
			g.drawString(c.x + ":" + c.y, (c.x * this.cellWidth) + this.OFFSET_LABEL_X, (c.y * this.cellHeight) + 10);
			g.drawString(c.properties.toString(), (c.x * this.cellWidth) + this.OFFSET_LABEL_X, (c.y * this.cellHeight) + 30);
		}

	}

	@Override
	public void paintComponent(Graphics gOriginal) {
		Graphics2D g = (Graphics2D) gOriginal;
		g.setFont(g.getFont().deriveFont(10f));

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		if (this.shouldFakeIsomorphic) {
			this.scaledWidth = this.getWidth() / 2;
			this.scaledHeight = this.getHeight() / 2;

			g.translate(this.getWidth() / 4, this.getHeight() / 4);
			g.rotate(Math.toRadians(45), this.scaledWidth / 2, this.scaledHeight / 2);
		} else {
			this.scaledWidth = this.getWidth();
			this.scaledHeight = this.getHeight();
		}

		this.cellWidth = Math.round(((float) this.scaledWidth / this.map.getWidth()));
		this.cellHeight = Math.round(((float) this.scaledHeight / this.map.getHeight()));

		this.paintCellContents(g, this.scaledWidth, this.scaledHeight);
		this.paintGrid(g, this.scaledWidth, this.scaledHeight);
		this.paintFieldHalos(g);
		this.paintBorder(g);
	}

	private void paintFieldHalos(Graphics2D g) {
		for (Cell c : this.map.getCells()) {
			if (c.hasBuilding() && c.isBuildingCenter(this.map)) {
				final int fieldEffect = c.building.getFieldEffect();

				if (fieldEffect > 0) {
					g.setColor(c.building.getColor());
					g.drawOval(((c.x * this.cellWidth) + (this.cellWidth / 2)) - ((this.cellWidth * fieldEffect) / 2), ((c.y * this.cellHeight) + (this.cellWidth / 2)) - ((this.cellHeight * fieldEffect) / 2), this.cellWidth * fieldEffect, this.cellHeight * fieldEffect);
				}
			}
		}
	}

	private void paintGrid(Graphics2D g, final double w, final double h) {
		g.setColor(Color.LIGHT_GRAY);

		int rheight = Math.round((float) h / this.map.getHeight());

		for (int r = 0; r < this.map.getHeight(); r++) {
			g.drawLine(0, r * rheight, (int) w, r * rheight);
		}

		g.setColor(Color.GRAY);

		int cwidth = Math.round((float) w / this.map.getWidth());

		for (int c = 0; c < this.map.getWidth(); c++) {
			g.drawLine(c * cwidth, 0, c * cwidth, (int) h);
		}
	}

	public void setMap(Map map) {
		this.map = map;
	}
}
