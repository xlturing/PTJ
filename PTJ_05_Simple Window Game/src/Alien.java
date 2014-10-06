import uwcse.graphics.*;

import java.awt.Color;
import java.awt.Point;
import java.util.*;

/**
 * The representation and display of an Alien
 */

public class Alien extends MovingObject
{
	// Size of an Alien
	public static final int RADIUS = 5;

	// Random speed
	public static Random r = new Random((new Date()).getTime());

	// Color lives
	protected Color[] c =
	{ Color.white, Color.yellow, Color.blue, Color.red };

	// Number of lives in this Alien
	// When 0, this Alien is dead
	protected int lives;

	// Alien move speed: the smaller the value, the slower the alien
	protected int speed;

	// Alien arrived the bottom?
	protected boolean isArrived = false;

	/**
	 * Create an alien in the graphics window
	 * 
	 * @param window
	 *            the GWindow this Alien belongs to
	 * @param center
	 *            the center Point of this Alien
	 */
	public Alien(GWindow window, Point center)
	{
		super(window, center);
		this.lives = r.nextInt(3) + 1;
		this.speed = 3;

		// Display this Alien
		this.draw();
	}

	/**
	 * The alien is being shot Decrement its number of lives and erase it from
	 * the graphics window if it is dead.
	 */
	public void isShot()
	{
		this.lives -= 1;
	}

	/**
	 * Is this Alien dead?
	 */
	public boolean isDead()
	{
		return this.lives == 0;
	}

	/**
	 * Return the location of this Alien
	 */
	public Point getLocation()
	{
		return this.center;
	}

	public boolean getIsArrived()
	{
		return this.isArrived;
	}

	/**
	 * Move this Alien As a start make all of the aliens move downward. If an
	 * alien reaches the bottom of the screen, it reappears at the top.
	 */
	public void move()
	{
		// move this Alien
		// Random r = new Random((new Date()).getTime());
		// int step = this.boundingBox.getHeight() / (r.nextInt(10) + 1);
		int y = this.center.y;
		y += speed;

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
			this.center.y = y;
			this.erase();
			this.draw();
		}
	}

	/**
	 * Display this Alien in the graphics window
	 */
	protected void draw()
	{
		// pick the color (according to the number of lives left)
		Color color = this.c[this.lives - 1]; // color by lives

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
