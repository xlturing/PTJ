import java.awt.Color;

public class Bird extends Critter
{
	// Bird Move record
	private int birdMove = 0;

	// Bird last move
	private Direction lastMove = Direction.NORTH;

	public Bird()
	{

	}

	// Bird color is blue
	@Override
	public Color getColor()
	{
		return Color.blue;
	}

	// Bird never eat
	@Override
	public boolean eat()
	{
		return false;
	}

	// Bird roars if the opponent looks like an Ant ("%"); otherwise pounces
	@Override
	public Attack fight(String opponent)
	{
		if (opponent.equals("%"))
			return Attack.ROAR;
		else
			return Attack.POUNCE;
	}

	// Bird a clockwise square: first goes north 3 times, then east 3 times,
	// then south 3 times, then west 3 times, then repeats
	@Override
	public Direction getMove()
	{
		if (birdMove < 3)
		{
			++birdMove;
			lastMove = Direction.NORTH;
			return Direction.NORTH;
		}
		else if (birdMove < 6)
		{
			++birdMove;
			lastMove = Direction.EAST;
			return Direction.EAST;
		}
		else if (birdMove < 9)
		{
			++birdMove;
			lastMove = Direction.SOUTH;
			return Direction.SOUTH;
		}
		else
		{
			++birdMove;
			if (birdMove == 11)
				birdMove = 0;
			lastMove = Direction.WEST;
			return Direction.WEST;
		}
	}

	// "^" (caret) if the bird's last move was north or it has not moved;
	// ">" (greater-than) if the bird's last move was east;
	// "V" (uppercase letter v) if the bird's last move was south;
	// "<" (less-than) if the bird's last move was west
	@Override
	public String toString()
	{
		switch(lastMove)
		{
			case NORTH:
				return "^";
			case EAST:
				return ">";
			case SOUTH:
				return "V";
			default:
				return "<";
		}
	}
}
