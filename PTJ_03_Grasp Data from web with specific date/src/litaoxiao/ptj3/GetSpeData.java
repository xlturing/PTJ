package litaoxiao.ptj3;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	public List<ResultInfo> searchByDate(String date)
	{
		String yearMonth = date.substring(0, date.length() - 3);
		String target = url.substring(0, 31) + searchRes.get(yearMonth);

		try
		{
			// convert string to date
			DateFormat df = new SimpleDateFormat("yyyy年MM月");
			Date dateYearMonth = df.parse(yearMonth);

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

						// if it's not the specific date continue
						String t = tds.get(5).text();
						t = t.substring(0, t.length() - 4);
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						SimpleDateFormat sdf2 = new SimpleDateFormat(
								"yyyy年MM月dd日");
						if (sdf.parse(t).compareTo(sdf2.parse(date)) != 0)
							continue;

						ri = new ResultInfo();
						ri.setTitle(tds.get(1).text());
						ri.setWriter(tds.get(2).text());
						ri.setReply(tds.get(3).text());
						ri.setPopularity(tds.get(4).text());
						ri.setTime(sdf.parse(t));
						String href = url.substring(0, 31)
								+ tds.get(1).getElementsByTag("a").attr("href")
										.toString();
						ri.setHref(href);

						// get headline
						Document essay = Jsoup.connect(href).get();
						Element es = essay.select("div.tpc_content").get(0);
						String[] section = es.text().split("\\s");
						String headline = "";
						for (int j = 2; j < 5 && j < section.length; ++j)
							headline += section[j];
						ri.setHeadLine(headline);

						lri.add(ri);
					}
				}
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
