package litaoxiao;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DNA
{
	private ArrayList<String> rn = new ArrayList<String>(); // Region name
	private ArrayList<String> sequence = new ArrayList<String>(); // DNA
	private ArrayList<Result> rList = new ArrayList<Result>();

	private double[] masses =
	{ 135.128, 111.103, 151.128, 125.107, 100.000 }; // A C G T -

	/* Get data from file */
	public void getData(String filename)
	{
		File f = new File(filename);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(f));
			String tempString = null;
			// Read data from file one by one line
			for (int i = 1; (tempString = reader.readLine()) != null; i++)
			{
				if (i % 2 == 1) // If it's odd line then it's region name
					rn.add(tempString);
				else
					// If it's even line then it's DNA sequence
					sequence.add(tempString);
			}
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}

	/* Calculate the result */
	public void calculate()
	{
		// Calculate per count, per mass, all mass and split the string to get
		// codons
		for (String s : sequence)
		{
			Result r = new Result();
			s = s.toUpperCase();
			String codon = "";
			for (char c : s.toCharArray())
			{
				switch (c)
				{
					case 'A':
					{
						r.setCount(0);
						r.setMassAll(masses[0]);
						r.setMassPerAdd(0, masses[0]);
						break;
					}
					case 'C':
					{
						r.setCount(1);
						r.setMassAll(masses[1]);
						r.setMassPerAdd(1, masses[1]);
						break;
					}
					case 'G':
					{
						r.setCount(2);
						r.setMassAll(masses[2]);
						r.setMassPerAdd(2, masses[2]);
						break;
					}
					case 'T':
					{
						r.setCount(3);
						r.setMassAll(masses[3]);
						r.setMassPerAdd(3, masses[3]);
						break;
					}
					default:
					{
						r.setMassAll(masses[4]);
					}
				}
				// If the length of codon is 3, then get a new codon
				if (c != '-')
					codon += Character.toString(c);
				if (codon.length() == 3)
				{
					r.setCodonList(codon);
					r.setCountCodons();
					codon = "";
				}
			}
			rList.add(r);
		}

		// Calculate the Total Mass% and decide sequence whether is Protein
		for (Result r : rList)
		{
			// Save C and G mass
			r.setMassCG(r.getMassPerOne(1) + r.getMassPerOne(2));
			// Calculate Total Mass%
			for (int i = 0; i < 4; i++)
			{
				r.setMassPer(
						i,
						Math.round(r.getMassPerOne(i) / r.getMassAll() * 100.0
								* 10.0) / 10.0);
			}

			// Is Protein?//
			if (r.getCodonList().get(0).equals("ATG")
					&& r.getCountCodons() >= 5
					&& (r.getMassCG() / r.getMassAll()) >= 0.3
					&& (r.getCodonList().get(r.getCodonList().size() - 1)
							.equals("TAA")
							|| r.getCodonList()
									.get(r.getCodonList().size() - 1)
									.equals("TAG") || r.getCodonList()
							.get(r.getCodonList().size() - 1).equals("TGA")))
			{
				r.setIsProtein("YES");
			}
		}
	}

	public void printAll(String fileOut)
	{
		// Create a new file
		File f = new File(fileOut);
		if (f.exists())
			f.delete();

		PrintWriter out = null;
		try
		{
			out = new PrintWriter(f);
			for (int i = 0; i < rn.size(); i++)
			{
				out.println("Region Name: " + rn.get(i));
				out.println("Nucleotides: " + sequence.get(i));
				out.println("Nuc. Counts: "
						+ Arrays.toString(rList.get(i).getCount()));
				out.println("Total Mass%: "
						+ Arrays.toString(rList.get(i).getMassPer()) + " of "
						+ Math.round(rList.get(i).getMassAll() * 10.0) / 10.0);
				out.println("Codons List: "
						+ rList.get(i).getCodonList().toString());
				out.println("Is Protein?: " + rList.get(i).getIsProtein()
						+ "\r\n");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			out.close();
		}
	}

	public static void main(String[] args)
	{
		// Get input and output file names

		Scanner input = new Scanner(System.in);
		System.out.println("This program reports information about DNA"
				+ "\nnucleotide sequences that may encode proteins.");
		System.out.print("Input file name?");
		String fileIn = input.next();
		System.out.print("Output file name?");
		String fileOut = input.next();

		DNA d = new DNA();
		d.getData(fileIn);
		d.calculate();
		d.printAll(fileOut);
	}
}
