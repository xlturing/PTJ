package litaoxiao;

import java.util.ArrayList;

public class Result
{
	private int[] count = new int[4];	// Count of ACGT
	private int countCodons;			// Count of Codons
	private double[] massPer = new double[4];	// Total Mass%
	private double massAll;				// All Mass include junk '-'
	private ArrayList<String> codonList = new ArrayList<String>();	// All codons
	private String isProtein = "NO";
	private double massCG;				// C and G sum

	public int[] getCount()
	{
		return count;
	}

	public void setCount(int i)
	{
		this.count[i]++;
	}

	public double getMassPerOne(int i)
	{
		return massPer[i];
	}
	
	public double[] getMassPer()
	{
		return massPer;
	}
	
	public void setMassPer(double[] massPer)
	{
		this.massPer = massPer;
	}

	/* Add mass */
	public void setMassPerAdd(int i, double mass)
	{
		this.massPer[i] += mass;
	}
	
	/* Set mass percentages */
	public void setMassPer(int i, double massPer)
	{
		this.massPer[i] = massPer;
	}

	public double getMassAll()
	{
		return massAll;
	}

	public void setMassAll(double mass)
	{
		this.massAll += mass;
	}

	public ArrayList<String> getCodonList()
	{
		return codonList;
	}
	
	public String getCodonListOne(int i)
	{
		return codonList.get(i);
	}

	public void setCodonList(String codon)
	{
		this.codonList.add(codon);
	}

	public String getIsProtein()
	{
		return isProtein;
	}

	public void setIsProtein(String isProtein)
	{
		this.isProtein = isProtein;
	}

	public int getCountCodons()
	{
		return countCodons;
	}

	public void setCountCodons()
	{
		this.countCodons++;
	}

	public double getMassCG()
	{
		return massCG;
	}

	public void setMassCG(double massCG)
	{
		this.massCG = massCG;
	}

}
