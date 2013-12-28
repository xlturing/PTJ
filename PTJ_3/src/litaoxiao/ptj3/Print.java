package litaoxiao.ptj3;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Print<E>
{
	public Print()
	{
		
	}

	private List<E> lp;

	public List<E> getLp()
	{
		return lp;
	}

	public void setLp(List<E> lp)
	{
		this.lp = lp;
	}

	public void printToTxt(String s)
	{
		// Create a new file and a file name
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
		String now = dateformat.format(date);
		File f = new File(now + "_" + s + ".txt");
		if (f.exists())
			f.delete();

		// Print all news to a text file
		try
		{
			PrintWriter out = new PrintWriter(f);
			out.println(s + " All News:\n");
			for (int i = 0; i < lp.size(); ++i)
			{
				out.println("TITLE:" + ((ResultInfo)lp.get(i)).getTitle());
				out.println("HEADLINE:" + ((ResultInfo)lp.get(i)).getHeadLine());
				out.println("WRITER:" + ((ResultInfo)lp.get(i)).getWriter());
				out.println("REPLY:" + ((ResultInfo)lp.get(i)).getReply());
				out.println("POPULARITY:" + ((ResultInfo)lp.get(i)).getPopularity());
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				out.println("TIME:" + df.format(((ResultInfo)lp.get(i)).getTime()));
				out.println("URL:" + ((ResultInfo)lp.get(i)).getHref() + "\r\n");
			}
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
