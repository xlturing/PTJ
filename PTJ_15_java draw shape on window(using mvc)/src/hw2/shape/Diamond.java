package hw2.shape;

import java.awt.*;

public class Diamond extends AbstractShape {

	public Diamond(Color color, int size, int x, int y) {
		this.color = color;
		this.size = size;
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Graphics g) {
		// caculate the diamond
		int[] xpoints = new int[4];
		int[] ypoints = new int[4];
		xpoints[0] = x - size / 2;
		ypoints[0] = y;
		xpoints[1] = x;
		ypoints[1] = y - size / 2;
		xpoints[2] = x + size / 2;
		ypoints[2] = y;
		xpoints[3] = x;
		ypoints[3] = y + size / 2;
		Polygon p = new Polygon(xpoints, ypoints, xpoints.length);
		g.setColor(color);

		g.fillPolygon(p);
	}

	@Override
	public Shape copy() {
		Shape s = new Diamond(color, size, x, y);
		return s;
	}

}
