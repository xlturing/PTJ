// Write your compliance statement here:
// What are your 4 extra features?
// How is your new alien different from the one described by the Alien class?
//
import uwcse.graphics.*;

import java.awt.Button;
import java.awt.Color;
import java.util.*;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * A SpaceInvader displays a fleet of alien ships and a space ship. The player
 * directs the moves of the spaceship and can shoot at the aliens.
 */

public class SpaceInvader extends GWindowEventAdapter
{
	// Possible actions from the keyboard
	/** No action */
	public static final int DO_NOTHING = 0;

	/** Steer the space ship */
	public static final int SET_SPACESHIP_DIRECTION = 1;

	/** To shoot at the aliens */
	public static final int SHOOT = 2;

	/** Move space ship up */
	public static final int UP = 3;

	/** Move space ship down */
	public static final int DWON = 4;

	// Period of the animation (in ms)
	// (the smaller the value, the faster the animation)
	private int animationPeriod = 100;

	// Current action from the keyboard
	private int action;

	// Game window
	private GWindow window;

	// The space ship
	private SpaceShip spaceShip;

	// Direction of motion given by the player
	private int dirFromKeyboard = MovingObject.LEFT;

	// The aliens
	private ArrayList<Alien> aliens;

	// Is the current game over?
	private String messageGameOver;

	// The count of aliens
	private int countAliens;

	// Background
	private Rectangle background;

	// Hit Background. When some aliens arrive bottom, screen will become red
	private Rectangle hitBackground;

	/**
	 * Construct a space invader game
	 */
	public SpaceInvader()
	{
		this.window = new GWindow("Space invaders", 600, 500);
		this.window.setExitOnClose();
		this.window.addEventHandler(this); // this SpaceInvader handles all of
		// the events fired by the graphics
		// window

		// Display the game rules
		String rulesOfTheGame = "Save the Earth! Destroy all of the aliens ships.\n"
				+ "White alien is simple alien and it will die when it's shot;\n"
				+ "Yellow alien is stronger alien and it will change to white when it's shot;\n"
				+ "Blue alien is crazy alien and it will change to yellow when it's shot;\n"
				+ "Red alien is the strongest alien and it will be down by a strange track, \n"
				+ "and also it will change to blue when it's shot.\n"
				+ "To move left, press 'J'.\n"
				+ "To move right, press 'L'.\n"
				+ "To move up, press 'I'.\n"
				+ "To move down, press 'k'.\n"
				+ "To shoot, press the space bar.\n"
				+ "To quit, press 'Q'."
				+ "To faster, press the '-'.\n" + "To slower, press the '+'.";
		JOptionPane.showMessageDialog(null, rulesOfTheGame, "Space invaders",
				JOptionPane.INFORMATION_MESSAGE);
		this.initializeGame();
	}

	/**
	 * Initialize the game (draw the background, aliens, and space ship)
	 */
	private void initializeGame()
	{
		// Clear the window
		this.window.erase();

		// Background (starry universe)
		background = new Rectangle(0, 0, this.window.getWindowWidth(),
				this.window.getWindowHeight(), Color.black, true);

		// Red Background
		hitBackground = new Rectangle(0, 0, this.window.getWindowWidth(),
				this.window.getWindowHeight(), Color.pink, true);

		this.window.add(background);
		// Add 50 stars here and there (as small circles)
		Random rnd = new Random(new Date().getTime());
		for (int i = 0; i < 50; i++)
		{
			// Random radius between 1 and 3
			int radius = rnd.nextInt(3) + 1;
			// Random location (within the window)
			// Make sure that the full circle is visible in the window
			int x = rnd.nextInt(this.window.getWindowWidth() - 2 * radius);
			int y = rnd.nextInt(this.window.getWindowHeight() - 2 * radius);
			this.window.add(new Oval(x, y, 2 * radius, 2 * radius, Color.white,
					true));
		}

		// Random count of aliens
		this.countAliens = rnd.nextInt(10) + 10;
		// ArrayList of aliens
		this.aliens = new ArrayList<Alien>();
		remainAliens();

		int x = 5 * SpaceShip.WIDTH;
		int y = 10 * Alien.RADIUS;
		// Create the space ship at the bottom of the window
		x = this.window.getWindowWidth() / 2;
		y = this.window.getWindowHeight() - SpaceShip.HEIGHT / 2;
		this.spaceShip = new SpaceShip(this.window, new Point(x, y));

		// start timer events
		this.window.startTimerEvents(this.animationPeriod);
	}

	// The aliens are still alive, show them
	private void remainAliens()
	{
		// Create 10 aliens every time till all aliens dead
		int x = 2 * SpaceShip.WIDTH;
		for (int i = 0; i < 10 && i < this.countAliens; i++)
		{
			if (i == Alien.r.nextInt(10) || i == 5)
			{
				int y = 10 * StrongAlien.RADIUS
						+ Alien.r.nextInt(this.window.getWindowHeight() / 3);
				this.aliens.add(new StrongAlien(this.window, new Point(x, y)));
				x += 8 * StrongAlien.RADIUS;
			}
			else
			{
				int y = 10 * Alien.RADIUS
						+ Alien.r.nextInt(this.window.getWindowHeight() / 3);
				this.aliens.add(new Alien(this.window, new Point(x, y)));
				x += 10 * Alien.RADIUS;
			}
		}
	}

	/**
	 * Move the objects within the graphics window every time the timer fires an
	 * event
	 */
	public void timerExpired(GWindowEvent we)
	{
		// Perform the action requested by the user?
		switch (this.action)
		{
			case SpaceInvader.SET_SPACESHIP_DIRECTION:
				this.spaceShip.setDirection(this.dirFromKeyboard);
				break;
			case SpaceInvader.SHOOT:
				this.spaceShip.shoot(this.aliens);
				break;
			case SpaceInvader.UP:
				this.spaceShip.moveUpDown(SpaceInvader.UP);
				break;
			case SpaceInvader.DWON:
				this.spaceShip.moveUpDown(SpaceInvader.DWON);
				break;
		}

		this.action = SpaceInvader.DO_NOTHING; // Don't do the same action
		// twice

		// Show the new locations of the objects
		this.updateGame();
	}

	/**
	 * Select the action requested by the pressed key
	 */
	public void keyPressed(GWindowEvent e)
	{
		// Don't perform the actions (such as shoot) directly in this method.
		// Do the actions in timerExpired, so that the alien ArrayList can't be
		// modified at the same time by two methods (keyPressed and timerExpired
		// run in different threads).

		switch (Character.toLowerCase(e.getKey()))
		// not case sensitive
		{
		// Put here the code to move the space ship with the < and > keys

			case ' ': // shoot at the aliens
				this.action = SpaceInvader.SHOOT;
				break;

			case 'q': // quit the game (BlueJ might not like that one)
				System.exit(0);
				break;

			case 'j': // press 'j' spaceShip move left
				this.spaceShip.direction = MovingObject.LEFT;
				break;

			case 'l': // press 'l' spaceShip move right
				this.spaceShip.direction = MovingObject.RIGHT;
				break;

			case 'i': // press 'i' spaceShip move up
				this.action = SpaceInvader.UP;
				break;

			case 'k': // press 'k' spaceShip move down
				this.action = SpaceInvader.DWON;
				break;

			case '-': // press '-' slower
				this.animationPeriod += 10;
				this.window.startTimerEvents(this.animationPeriod);
				break;

			case '=': // press '+' faster
				this.animationPeriod = this.animationPeriod - 10 <= 0 ? 10
						: (this.animationPeriod -= 10);
				this.window.startTimerEvents(this.animationPeriod);
				break;

			default: // no new action
				this.action = SpaceInvader.DO_NOTHING;
				break;
		}
	}

	/**
	 * Update the game (Move aliens + space ship)
	 */
	private void updateGame()
	{
		// Is the game won (or lost)?
		// Put here code to end the game (= no more aliens)

		this.window.suspendRepaints(); // to speed up the drawing

		// Move the aliens
		Iterator<Alien> it = this.aliens.iterator();
		ArrayList<Alien> deadA = new ArrayList<Alien>(), arrivedA = new ArrayList<Alien>();
		while (it.hasNext())
		{
			Alien a = (Alien) it.next();
			if (a.getBoundingBox().intersects(spaceShip.getBoundingBox()))
			{
				if (anotherGame("Game Over!"))
				{
					this.initializeGame();
					return;
				}
				else
					System.exit(0);
			}
			if (a.isDead())
			{
				a.erase();
				deadA.add(a);
				this.countAliens--;
			}
			else
			{
				a.move();
				if (a.getIsArrived())
					arrivedA.add(a);
			}
		}

		// Remove the dead alien
		if (deadA.size() != 0)
			this.aliens.removeAll(deadA);
		// Remove the arrived alien
		if (arrivedA.size() != 0)
		{
			this.aliens.removeAll(arrivedA);
			this.window.add(hitBackground);
		}
		// If all aliens in window have arrived the bottom, then repaint the
		// remain aliens
		if (aliens.size() == 0)
		{
			remainAliens();
			this.window.remove(hitBackground);
		}

		// If all aliens dead, then victory
		// Ask player whether play again?
		if (this.countAliens == 0)
			if (anotherGame("Congratulations, you saved the Earth!"))
			{
				this.initializeGame();
				return;
			}
			else
				System.exit(0);

		// Move the space ship
		this.spaceShip.move();

		// Display it all
		this.window.resumeRepaints();
	}

	/**
	 * Does the player want to play again?
	 */
	private boolean anotherGame(String s)
	{
		// this method is useful at the end of a game if you want to prompt the
		// user
		// for another game (s would be a String describing the outcome of the
		// game
		// that just ended, e.g. "Congratulations, you saved the Earth!")
		// Yes 0 No 1
		int choice = JOptionPane.showConfirmDialog(null, s
				+ "\nDo you want to play again?", "Game over",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		return (choice == JOptionPane.YES_OPTION);
	}

	/**
	 * Starts the application
	 */
	public static void main(String[] args)
	{
		new SpaceInvader();
	}
}
