import java.awt.Color;
import java.util.Date;
import java.util.Random;

public class Hippo extends Critter
{
	// the number of times that a call to eat
	private int hunger;

	private Random r = new Random((new Date()).getTime());

	public Hippo(int hunger)
	{
		this.hunger = hunger;
	}

	//
	@Override
	public boolean eat()
	{
		if (hunger == 0)
			return false;
		else
		{
			--hunger;
			return true;
		}
	}

	@Override
	public Color getColor()
	{
		if (hunger == 0)
			return Color.white;
		else
			return Color.gray;
	}

	// if this Hippo is hungry (if eat would return true),
	// then scratches; else pounces
	@Override
	public Attack fight(String opponent)
	{
		if (hunger == 0)
			return Attack.SCRATCH;
		else
			return Attack.POUNCE;
	}

	// moves 5 steps in a random direction (north, south, east, or west),
	// then chooses a new random direction and repeats
	@Override
	public Direction getMove()
	{
		int d = r.nextInt(5) + 1;
		switch (d)
		{
			case 1:
				return Direction.NORTH;
			case 2:
				return Direction.SOUTH;
			case 3:
				return Direction.EAST;
			case 4:
				return Direction.WEST;
			default:
				return Direction.CENTER;
		}
	}

	// the number of pieces of food this Hippo still wants to eat, as a String
	@Override
	public String toString()
	{
		return Integer.toString(hunger);
	}
}
