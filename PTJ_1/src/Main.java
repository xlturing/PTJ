import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args) throws FileNotFoundException
	{
		// input data a and b
		System.out.println("Please intput your data a and b:");
		Scanner input = new Scanner(System.in);
		double a = input.nextDouble();
		double b = input.nextDouble();

		// judge your data whether reasonable
		if (a > b)
		{
			System.out.println("Sorry, your a is bigger than b");
			return;
		}
		
		//Create a new file
		File f = new File("result.txt");
		if(f.exists())
			f.delete();
		
		//Calculate result and print to file named 'result.txt'
		PrintWriter out = new PrintWriter(f);
		for (double i = a; i < b; i += 0.1)
		{
			DecimalFormat    df   = new DecimalFormat("######0.0");   
			out.println(df.format(i) + ":\n");
			out.println("Square Root: " + Math.pow(i, 1.0 / 2.0));
			out.println("Cube Root: " + Math.pow(i, 1.0 / 3.0) + "\r\n");
		}
		out.close();
	}
}
