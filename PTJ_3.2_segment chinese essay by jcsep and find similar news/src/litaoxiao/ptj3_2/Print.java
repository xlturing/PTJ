package litaoxiao.ptj3_2;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Print<E>
{
	public Print()
	{

	}

	private List<E> lp;
	private int threshold = 50;

	public List<E> getLp()
	{
		return lp;
	}

	public void setLp(List<E> lp)
	{
		this.lp = lp;
	}

	public int getThreshhold()
	{
		return threshold;
	}

	public void setThreshhold(int threshold)
	{
		this.threshold = threshold;
	}

	public void printToTxt(String s, String keyword)
	{
		// Create a new file and a file name
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
		String now = dateformat.format(date);
		File f = new File(now + "_similar5_" + s + ".txt");
		if (f.exists())
			f.delete();

		// Print similar 5 news to a text file
		try
		{
			PrintWriter out = new PrintWriter(f);
			out.println(s + " Most 5 Similar News By Keyword [" + keyword
					+ "] :\n");
			// similarity definition: the most similar popularity
			// because if the popularity is similar, the news is popular to
			// people
			int standard = Integer.parseInt(((ResultInfo) lp.get(1))
					.getPopularity());
			int count = 0;
			for (int i = 0; i < lp.size(); ++i)
			{
				int t = Integer.parseInt(((ResultInfo) lp.get(i))
						.getPopularity());
				if (Math.abs(standard - t) < threshold)
				{
					out.println("TITLE:" + ((ResultInfo) lp.get(i)).getTitle());
					out.println("POPULARITY:"
							+ ((ResultInfo) lp.get(i)).getPopularity());
					out.println("WRITER:"
							+ ((ResultInfo) lp.get(i)).getWriter());
					out.println("REPLY:" + ((ResultInfo) lp.get(i)).getReply());
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					out.println("TIME:"
							+ df.format(((ResultInfo) lp.get(i)).getTime()));
					out.println("URL:" + ((ResultInfo) lp.get(i)).getHref()
							+ "\r\n");
					++count;
				}
			}
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
