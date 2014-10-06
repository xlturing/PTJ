public class Pens
{
	private boolean[] isUsed =
	{ false, false, false };

	public synchronized void getPen(int index)
	{
		int i = index / 2; // Microbiologists need which pen
							// according to their index
		while (isUsed[i]) // If pen is used, try
		{
			try
			{
				wait();
				System.out.println("Microbiologist " + index
						+ " is waiting for pen to write notes");
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		isUsed[i] = true;
		System.out.println("Microbiologist " + index + " is writing with pen");
	}

	public synchronized void release(int index)
	{
		int i = index / 2;
		isUsed[i] = false;
		System.out.println("Microbiologist " + index + " has finished writing");
		notify(); // alert a thread that's blocking on this semaphore
	}
}
