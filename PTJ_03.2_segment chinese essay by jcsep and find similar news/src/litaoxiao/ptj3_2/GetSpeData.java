package litaoxiao.ptj3_2;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.text.DateFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @description: Get the data from web with specific date
 * @author: XLT
 * @date: date:2013年12月24日 time:上午11:49:24
 * @version 1.0
 */
public class GetSpeData
{
	public GetSpeData(String url)
	{
		this.url = url;

		this.getData();
	}

	// search and url
	private String url;

	// search result key:date value:href
	private HashMap<String, String> searchRes = new HashMap<String, String>();

	// result information
	private List<ResultInfo> lri = new ArrayList<ResultInfo>();

	// get data from url and store into a hash map which key is date and value
	// is href
	private void getData()
	{
		try
		{
			Document document = Jsoup.connect(url).get();
			Elements es = document.select("a[href]");

			for (Element e : es)
			{
				String date = e.text();
				String href = e.attr("href");
				searchRes.put(date, href);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// get all pages data according to specific date
	public List<ResultInfo> searchByDate(String date1, String date2)
	{
		try
		{
			// traverse throw date1 to date2
			DateFormat df = new SimpleDateFormat("yyyy年MM月");
			Date d1 = df.parse(date1);
			Date d2 = df.parse(date2);
			Calendar start = Calendar.getInstance();
			Calendar end = Calendar.getInstance();
			start.setTime(d1);
			end.setTime(d2);

			while (!start.after(end))
			{
				Date targetDate = start.getTime();

				// get url
				DateFormat yearMonth = new SimpleDateFormat("yyyy年MM月");
				String date = yearMonth.format(targetDate);
				String target = url.substring(0, 31) + searchRes.get(date);

				// get first page
				Document firDocument = Jsoup.connect(target).get();

				// get url the prefix of every page
				String prefix = firDocument
						.select("td[align=left] > div.pages > a").get(0)
						.attr("href");
				prefix = target + prefix.substring(0, prefix.length() - 1);

				// get pages count
				String pages = firDocument.select("td[align=left] > div.pages")
						.get(0).text();
				String tmp[] = pages.split("/");
				pages = tmp[1];
				pages = pages.replaceAll("\\D", "");
				int pagesCount = Integer.parseInt(pages);

				int i = 1;
				while (i <= pagesCount)
				{
					// get one of the pages
					Document document = Jsoup.connect(prefix + i).get();
					++i;

					// process this page
					for (Element table : document.select("table#ajaxtable"))
					{
						ResultInfo ri;
						for (Element tr : table.select("tr:gt(1)"))
						{
							Elements tds = tr.select("td:not([colspan])");

							// for eliminating the last row
							if (tds.size() == 0)
								continue;

							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd");

							ri = new ResultInfo();
							ri.setTitle(tds.get(1).text());
							ri.setWriter(tds.get(2).text());
							ri.setReply(tds.get(3).text());
							ri.setPopularity(tds.get(4).text());
							ri.setTime(sdf.parse(tds.get(5).text()));
							String href = url.substring(0, 31)
									+ tds.get(1).getElementsByTag("a")
											.attr("href").toString();
							ri.setHref(href);

							lri.add(ri);
						}
					}
				}
				start.add(Calendar.MONTH, 1);
			}
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return lri;
	}
}
