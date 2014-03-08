package litaoxiao.ptj3_2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultInfo
{
	// result information 5W
	private String title;
	private String writer;
	private String reply;
	private String popularity;
	private Date time;
	private String href;
	private List<String> segResult = new ArrayList<String>();

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getWriter()
	{
		return writer;
	}

	public void setWriter(String writer)
	{
		this.writer = writer;
	}

	public String getReply()
	{
		return reply;
	}

	public void setReply(String reply)
	{
		this.reply = reply;
	}

	public String getPopularity()
	{
		return popularity;
	}

	public void setPopularity(String popularity)
	{
		this.popularity = popularity;
	}

	public Date getTime()
	{
		return time;
	}

	public void setTime(Date time)
	{
		this.time = time;
	}

	public String getHref()
	{
		return href;
	}

	public void setHref(String href)
	{
		this.href = href;
	}

	public List<String> getSegResult()
	{
		return segResult;
	}

	public void setSegResult(List<String> segResult)
	{
		this.segResult = segResult;
	}

}
