public class Microscopes
{
	private boolean[] isUsed =
	{ false, false, false };

	public synchronized void getMicro(int index)
	{
		int i;
		if (index % 2 == 0)
			i = index / 2; // Microbiologists need which pen
							// according to their index
		else
			i = index / 2 == 0 ? 2 : index / 2 - 1;
		while (isUsed[i]) // If pen is used, try
		{
			try
			{
				wait();
				System.out.println("Microbiologist " + index
						+ " is looking through microscope");
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		isUsed[i] = true;
		System.out
				.println("Microbiologist " + index + " is making observation");
	}

	public synchronized void release(int index)
	{
		int i;
		if (index % 2 == 0)
			i = index / 2; // Microbiologists need which pen
							// according to their index
		else
			i = index / 2 == 0 ? 2 : index / 2 - 1;
		isUsed[i] = false;
		System.out.println("Microbiologist " + index
				+ " has finished with microscope");
		notify(); // alert a thread that's blocking on this semaphore
	}
}
