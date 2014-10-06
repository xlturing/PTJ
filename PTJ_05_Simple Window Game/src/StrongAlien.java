import uwcse.graphics.*;

import java.awt.Color;
import java.awt.Point;
import java.util.*;

/**
 * @description: This is an alien which is stronger than normal aliens and this
 *               alien will be down by a stranger track
 * @author:
 */
public class StrongAlien extends Alien
{
	// Size of an Strong Alien. bigger
	public static final int RADIUS = 10;

	// X step
	private int step = 20;

	public StrongAlien(GWindow window, Point center)
	{
		super(window, center);
		// It have stronger lives
		this.lives = 4;
		// And also faster speed
		this.speed = 4;
	}

	@Override
	public void move()
	{
		// move this Alien by stranger track
		int x = this.center.x;
		int y = this.center.y;
		y += speed;

		// Random move right or left
		int flag = Alien.r.nextInt(2);
		// If flag is 1, then move right
		if (flag == 1)
		{
			if (x + step < this.window.getWindowWidth())
				x += step;
		}
		// else move left
		else
		{
			if (x - step > 0)
				x -= step;
		}

		// Is the new position in the window?
		if (y + this.boundingBox.getHeight() / 3 >= this.window
				.getWindowHeight())
		{
			this.erase();
			this.isArrived = true;
		}
		else
		// it is in the window
		{
			this.center.x = x;
			this.center.y = y;
			this.erase();
			this.draw();
		}
	}

	@Override
	protected void draw()
	{
		// pick the color (according to the number of lives left)
		Color color = this.c[this.lives - 1]; // Stronger alien's color is white

		// Graphics elements for the display of this Alien
		// A circle on top of an X
		this.shapes = new Shape[3];
		this.shapes[0] = new Line(this.center.x - 2 * RADIUS, this.center.y - 2
				* RADIUS, this.center.x + 2 * RADIUS, this.center.y + 2
				* RADIUS, color);
		this.shapes[1] = new Line(this.center.x + 2 * RADIUS, this.center.y - 2
				* RADIUS, this.center.x - 2 * RADIUS, this.center.y + 2
				* RADIUS, color);
		this.shapes[2] = new Oval(this.center.x - RADIUS, this.center.y
				- RADIUS, 2 * RADIUS, 2 * RADIUS, color, true);

		for (int i = 0; i < this.shapes.length; i++)
			this.window.add(this.shapes[i]);

		// Bounding box of this Alien
		this.boundingBox = new Rectangle(this.center.x - 2 * RADIUS,
				this.center.y - 2 * RADIUS, 4 * RADIUS, 4 * RADIUS);

		this.window.doRepaint();
	}
}
