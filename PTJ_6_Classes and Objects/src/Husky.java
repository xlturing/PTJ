import java.awt.Color;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * @description: This Class make husky get the highest score
 */
public class Husky extends Critter
{
	private Random r = new Random((new Date()).getTime());

	public Husky()
	{
	}

	// Husky is yellow
	@Override
	public Color getColor()
	{
		return Color.yellow;
	}

	// Always eat for high score
	@Override
	public boolean eat()
	{
		return true;
	}

	// Husky will have appropriate attacking accord to opponet,
	// so they will always win
	@Override
	public Attack fight(String opponent)
	{
		Pattern pattern = Pattern.compile("[0-9]*"); // Regular expression
		if (opponent.equals("S"))
			return Attack.POUNCE;
		else if (opponent.equals("%"))
			return Attack.ROAR;
		else if (opponent.equals("^") || opponent.equals("V")
				|| opponent.equals("<") || opponent.equals(">"))
			return Attack.SCRATCH;
		else if (pattern.matcher(opponent).matches())
		{
			return Attack.SCRATCH; // Because hippo's fight may be SCRATCH or
									// POUNCE, so we return SCRATCH will get win
									// or random
		}
		else
		// It's a husky
		{
			switch (r.nextInt(3))
			{
				case 0:
					return Attack.POUNCE;
				case 1:
					return Attack.ROAR;
				default:
					return Attack.SCRATCH;
			}
		}
	}

	// Husky move to food as soon as possible and they will avoid meeting other
	// huskies as soon as possible
	@Override
	public Direction getMove()
	{
		String oppE = getNeighbor(Direction.EAST), oppW = getNeighbor(Direction.WEST);
		String oppN = getNeighbor(Direction.NORTH), oppS = getNeighbor(Direction.SOUTH);
		if (oppE.equals("."))
			return Direction.EAST;
		else if (oppN.equals("."))
			return Direction.NORTH;
		else if (oppW.equals("."))
			return Direction.WEST;
		else if (oppS.equals("."))
			return Direction.SOUTH;
		else
		{
			if (isHusky(oppE) == 0)
				return Direction.EAST;
			else if (isHusky(oppW) == 0)
				return Direction.WEST;
			else if (isHusky(oppN) == 0)
				return Direction.NORTH;
			else if(isHusky(oppS) == 0)
				return Direction.SOUTH;
			else
				return randDir();
		}
	}

	// Return a strange string for others can't find us
	@Override
	public String toString()
	{
		return "~";
	}

	// Decide whether it's husky, 2 is husky and 1 is space
	private int isHusky(String opp)
	{
		Pattern pattern = Pattern.compile("[0-9]*"); // Regular expression
		if (opp.equals("S"))
			return 0;
		else if (opp.equals("%"))
			return 0;
		else if (opp.equals("^") || opp.equals("V") || opp.equals("<")
				|| opp.equals(">"))
			return 0;
		else if (pattern.matcher(opp).matches())
			return 0; // Because hippo's fight may be SCRATCH or
						// POUNCE, so we return SCRATCH will get win
						// or random
		else if (opp.equals(" "))
			return 1;
		else
			// It's a husky
			return 2;
	}

	// Produce a random direction
	private Direction randDir()
	{
		switch (r.nextInt(4))
		{
			case 0:
				return Direction.EAST;
			case 1:
				return Direction.WEST;
			case 2:
				return Direction.SOUTH;
			default:
				return Direction.NORTH;
		}
	}

}
