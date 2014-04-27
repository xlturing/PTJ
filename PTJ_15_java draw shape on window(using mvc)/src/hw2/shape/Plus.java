package hw2.shape;

import java.awt.*;

public class Plus extends AbstractShape {

	public Plus(Color color, int size, int x, int y) {
		this.color = color;
		this.size = size;
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawLine(x, y, x + size, y);
		g.drawLine(x + size / 2, y - size / 2, x + size / 2, y + size / 2);
	}

	@Override
	public Shape copy() {
		Shape s = new Plus(color, size, x, y);
		return s;
	}

}
