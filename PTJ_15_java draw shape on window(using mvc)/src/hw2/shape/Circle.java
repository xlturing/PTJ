package hw2.shape;

import java.awt.*;

public class Circle extends AbstractShape {
	public Circle(Color color, int size, int x, int y) {
		this.color = color;
		this.size = size;
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, size, size);
	}

	@Override
	public Shape copy() {
		Shape s = new Circle(color, size, x, y);
		return s;
	}
}
