import java.awt.Color;

public class Ant extends Critter
{
	private boolean walkSouth;
	
	// Record zigzag
	private boolean se = true;	// Alternates between south and east (S, E, S, E, ...)
	private boolean ne = true;	// Alternates between north and east (N, E, N, E, ...)
	
	public Ant(boolean walkSouth)
	{
		this.walkSouth = walkSouth;
	}
	
	// Ant color is red
	@Override
	public Color getColor()
	{
		return Color.red;
	}
	
	// Ants look like "%"
	@Override
	public String toString()
	{
		return "%";
	}
	
	// Ant always eat
	@Override
	public boolean eat()
	{
		return true;
	}
	
	// Ant move direction accord to walkSouth
	@Override
	public Direction getMove()
	{
		if(walkSouth)
		{
			if(se)
			{
				se = false;
				return Direction.SOUTH;
			}
			else
			{
				se = true;
				return Direction.EAST;
			}
		}
		else
		{
			if(ne)
			{
				ne = false;
				return Direction.NORTH;
			}
			else
			{
				ne = true;
				return Direction.EAST;
			}
		}
	}
	
	// Ant always scratch
	@Override
	public Attack fight(String opponent)
	{
		return Attack.SCRATCH;
	}
}
