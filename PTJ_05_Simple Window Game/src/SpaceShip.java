import uwcse.graphics.*;

import java.awt.Point;
import java.util.*;
import java.awt.Color;

/**
 * The space ship
 */
public class SpaceShip extends MovingObject
{
	/** Height of a space ship */
	public static final int HEIGHT = 40;

	/** Width of a space ship */
	public static final int WIDTH = 20;

	/** Is the space ship shooting with its laser? */
	private boolean isShooting;

	/**
	 * Construct this SpaceShip
	 */
	public SpaceShip(GWindow window, Point center)
	{
		super(window, center);
		this.direction = MovingObject.LEFT;

		// Draw this SpaceShip
		this.draw();
	}

	/**
	 * Move this SpaceShip. The space ship should be constantly moving. Select a
	 * new direction if the space ship can't move in the current direction of
	 * motion.
	 */
	public void move()
	{
		// A space ship moves left or right
		if (this.direction != MovingObject.LEFT
				&& this.direction != MovingObject.RIGHT)
			throw new IllegalArgumentException("Invalid space ship direction");

		// move this SpaceShip
		boolean isMoveOK;
		// Distance covered by the space ship in one step
		int step = this.boundingBox.getWidth() / 2;

		do
		{
			int x = this.center.x;
			switch (this.direction)
			{
				case LEFT:
					x -= step;
					break;
				case RIGHT:
					x += step;
					break;
			}

			// Is the new position in the window?
			if (x + this.boundingBox.getWidth() / 2 >= this.window
					.getWindowWidth())
			// past the right edge
			{
				isMoveOK = false;
				this.direction = MovingObject.LEFT;
			}
			else if (x - this.boundingBox.getWidth() / 2 <= 0) // past the
			// left edge
			{
				isMoveOK = false;
				this.direction = MovingObject.RIGHT;
			}
			else
			// it is in the window
			{
				isMoveOK = true;
				this.center.x = x;
			}
		}
		while (!isMoveOK);

		// Show the new location of this SpaceShip
		this.erase();
		this.draw();
	}

	public void moveUpDown(int direction)
	{
		int y = this.center.y;
		if (direction == this.UP)
		{
			if (y - SpaceShip.HEIGHT > 10 * Alien.RADIUS)
			{
				y -= SpaceShip.HEIGHT;
			}
		}
		else
		{
			if (y + SpaceShip.HEIGHT < this.window.getWindowHeight())
			{
				y += SpaceShip.HEIGHT;
			}
		}
		this.center.y = y;
		this.erase();
		this.draw();
	}

	/**
	 * Shoot at the aliens If an alien is hit, decrease its number of lives or
	 * remove it from the array list if it is dead.
	 * 
	 * @param aliens
	 *            the ArrayList of aliens
	 */
	public void shoot(ArrayList<Alien> aliens)
	{
		for (Alien a : aliens)
		{
			if (a.center.x <= this.center.x + 10
					&& a.center.x >= this.center.x - 10)
			{
				a.isShot();
			}
		}
		this.isShooting = true;
	}

	/**
	 * Draw this SpaceShip in the graphics window
	 */
	protected void draw()
	{
		this.shapes = new Shape[5];

		// Body of the space ship
		Rectangle body = new Rectangle(this.center.x - SpaceShip.WIDTH / 2,
				this.center.y - SpaceShip.HEIGHT / 2, SpaceShip.WIDTH,
				SpaceShip.HEIGHT, Color.cyan, true);

		this.shapes[0] = body;

		// Cone on top
		int x1 = body.getX();
		int y1 = body.getY();
		int x2 = x1 + body.getWidth();
		int y2 = y1;
		int x3 = body.getCenterX();
		int y3 = y1 - body.getWidth();
		this.shapes[1] = new Triangle(x1, y1, x2, y2, x3, y3, Color.red, true);
		// Show the laser beam if the rocket is shooting
		if (this.isShooting)
		{
			this.shapes[4] = new Line(x3, y3, x3, 0, Color.yellow);
			this.isShooting = false;
		}

		// Wings on the sides
		x1 = body.getX();
		y1 = body.getY() + body.getHeight();
		x2 = body.getX() - body.getWidth() / 2;
		y2 = y1;
		x3 = x1;
		y3 = y1 - body.getWidth() / 2;
		this.shapes[2] = new Triangle(x1, y1, x2, y2, x3, y3, Color.red, true);
		x1 = body.getX() + body.getWidth();
		x2 = x1 + body.getWidth() / 2;
		x3 = x1;
		this.shapes[3] = new Triangle(x1, y1, x2, y2, x3, y3, Color.red, true);

		// The bounding box of this SpaceShip
		this.boundingBox = this.shapes[0].getBoundingBox();

		// Put everything in the window
		for (int i = 0; i < this.shapes.length; i++)
			if (this.shapes[i] != null)
				this.window.add(this.shapes[i]);

		this.window.doRepaint();
	}
}
