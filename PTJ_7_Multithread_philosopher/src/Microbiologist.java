import java.util.Date;
import java.util.Random;

public class Microbiologist extends Thread
{
	private int index; // identifying index number
	private boolean pen; // microscope available
	private boolean miscroscope; // pen available
	private int minThk; // minimum and maximum time thinking observations
	private int maxThk;
	private int minObs;
	private int maxObs;
	private int count = 1; // How many times has mircrobiologist observed

	private Pens p;
	private Microscopes m;

	public static Random r = new Random(new Date().getTime());

	public Microbiologist(Pens p, Microscopes m)
	{
		this.p = p;
		this.m = m;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public boolean isPen()
	{
		return pen;
	}

	public void setPen(boolean pen)
	{
		this.pen = pen;
	}

	public boolean isMiscroscope()
	{
		return miscroscope;
	}

	public void setMiscroscope(boolean miscroscope)
	{
		this.miscroscope = miscroscope;
	}

	public int getMinThk()
	{
		return minThk;
	}

	public void setMinThk(int minThk)
	{
		this.minThk = minThk;
	}

	public int getMaxThk()
	{
		return maxThk;
	}

	public void setMaxThk(int maxThk)
	{
		this.maxThk = maxThk;
	}

	public int getMinObs()
	{
		return minObs;
	}

	public void setMinObs(int minObs)
	{
		this.minObs = minObs;
	}

	public int getMaxObs()
	{
		return maxObs;
	}

	public void setMaxObs(int maxObs)
	{
		this.maxObs = maxObs;
	}

	@Override
	public synchronized void run()
	{
		while (count <= 5)
		{
			this.observation();
			System.out
					.println("Microbiologist " + index
							+ " has finished observation " + count
							+ " and is thinking");
			++count;
			this.thinking();
		}
		System.out.println("Microbiologist " + index
				+ " has finished work and gone to pub");
	}

	private synchronized void observation()
	{
		this.p.getPen(index);
		this.pen = true;
		this.m.getMicro(index);
		this.miscroscope = true;
		try
		{
			int obsTime = r.nextInt(maxObs - minObs) + minObs;
			sleep(r.nextInt(obsTime * 1000)); // second to milisecond
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		this.m.release(index);
		this.miscroscope = false;
		this.p.release(index);
		this.pen = false;
	}

	private synchronized void thinking()
	{
		try
		{
			int obsTime = r.nextInt(maxThk - minThk) + minThk;
			sleep(r.nextInt(obsTime * 1000)); // second to milisecond
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		Pens p = new Pens();
		Microscopes m = new Microscopes();
		for (int i = 0; i < 6; i++)
		{
			Microbiologist mb = new Microbiologist(p, m);
			mb.setIndex(i);
			if (i % 2 == 0)
			{
				mb.setMinObs(5);
				mb.setMaxObs(30);
				mb.setMinThk(10);
				mb.setMaxThk(40);
			}
			else
			{
				mb.setMinObs(10);
				mb.setMaxObs(20);
				mb.setMinThk(15);
				mb.setMaxThk(30);
			}
			mb.start();
		}
	}
}
