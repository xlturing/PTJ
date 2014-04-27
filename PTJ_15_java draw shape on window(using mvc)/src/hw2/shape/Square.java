package hw2.shape;

import java.awt.*;

public class Square extends AbstractShape {

	public Square(Color color, int size, int x, int y) {
		this.color = color;
		this.size = size;
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, size, size);
	}

	@Override
	public Shape copy() {
		Shape s = new Square(color, size, x, y);
		return s;
	}

}
