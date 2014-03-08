package litaoxiao.ptj3_2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.lionsoul.jcseg.ASegment;
import org.lionsoul.jcseg.core.ADictionary;
import org.lionsoul.jcseg.core.DictionaryFactory;
import org.lionsoul.jcseg.core.IWord;
import org.lionsoul.jcseg.core.JcsegException;
import org.lionsoul.jcseg.core.JcsegTaskConfig;
import org.lionsoul.jcseg.core.SegmentFactory;

public class Similar
{

	private List<ResultInfo> lri;
	private List<ResultInfo> similar;
	private String keyword;

	public Similar(List<ResultInfo> lri, String keyword)
	{
		this.lri = lri;
		this.keyword = keyword;
		this.similar = new ArrayList<ResultInfo>();
	}

	public List<ResultInfo> getSimilar()
	{
		return similar;
	}

	public void setSimilar(List<ResultInfo> similar)
	{
		this.similar = similar;
	}

	public List<ResultInfo> process()
	{
		try
		{
			JcsegTaskConfig config = new JcsegTaskConfig();
			ADictionary dic = DictionaryFactory.createDefaultDictionary(config,
					false);
			ASegment seg = (ASegment) SegmentFactory.createJcseg(
					JcsegTaskConfig.COMPLEX_MODE, new Object[]
					{ config, dic });
			IWord word = null;
			List<String> ls;
			List<String> lsKey = new ArrayList<String>();

			for (int i = 0; i < lri.size(); i++)
			{
				ResultInfo ri = lri.get(i);
				seg.reset(new StringReader(ri.getTitle()));
				ls = new ArrayList<String>();
				while ((word = seg.next()) != null)
				{
					ls.add(word.getValue());
				}
				ri.setSegResult(ls);
			}

			seg.reset(new StringReader(keyword));
			while ((word = seg.next()) != null)
			{
				lsKey.add(word.getValue());
			}

			sortInfo[] dis = distance(lri, lsKey);

			// sort
			Arrays.sort(dis, new Comparator<sortInfo>()
			{
				public int compare(sortInfo si1, sortInfo si2)
				{
					return si2.distance - si1.distance;
				}
			});// anonymous class use

			for (int i = 0; i < 5; ++i)
			{
				similar.add(lri.get(dis[i].index));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return this.similar;
	}

	private class sortInfo
	{
		int distance;
		int index;
	}

	// distance between keyword and every news
	public sortInfo[] distance(List<ResultInfo> lri, List<String> lsKey)
	{
		int len = lri.size();
		// index distance
		sortInfo[] lsi = new sortInfo[lri.size()];

		for (int i = 0; i < len; ++i)
		{
			lsi[i] = new sortInfo();
			lsi[i].distance = 0;
			lsi[i].index = i;
			ResultInfo ri1 = lri.get(i);

			for (String a : ri1.getSegResult())
			{
				for (String b : lsKey)
				{
					if (a.equals(b))
					{
						lsi[i].index = i;
						lsi[i].distance++;
					}
				}
			}
		}

		return lsi;
	}

}
