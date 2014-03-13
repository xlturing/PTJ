import java.awt.Color;

public class Vulture extends Bird
{
	// Returns true if vulture is hungry.
	// A vulture is initially hungry, and he remains
	// hungry until he eats once.
	// After eating once he will become non-hungry until he
	// gets into a fight. After one or more fights, he will be hungry again.
	private boolean isHungry = true;

	public Vulture()
	{
	}

	// Vulture color is black
	@Override
	public Color getColor()
	{
		return Color.black;
	}

	@Override
	public boolean eat()
	{
		if (isHungry)
		{
			isHungry = false;
			return true;
		}
		else
			return false;
	}

	// roars if the opponent looks like an Ant ("%"); otherwise pounces
	@Override
	public Attack fight(String opponent)
	{
		isHungry = false;
		if (opponent.equals("%"))
			return Attack.ROAR;
		else
			return Attack.POUNCE;
	}
}
