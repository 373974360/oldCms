package com.deya.wcm.services.cms.rss;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.cms.info.InfoBean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import sun.security.util.Debug;
import sun.util.logging.resources.logging;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
 
public class RssReaderManager {
  
	public static void main(String[] args) throws Exception {		
		getRssInfoList("http://news.163.com/special/00011K6L/rss_newstop.xml",1,20);
		
	}
	
	
	public static List<InfoBean> getRssInfoList(String url,int currentPage,int pageSize) {
		
		List<InfoBean> infos = new ArrayList<InfoBean>();
		try{
			URLConnection feedUrl = new URL(url).openConnection();
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(feedUrl));
			List entryList = feed.getEntries();
			int start = (currentPage - 1) * pageSize;
			int end = currentPage * pageSize > entryList.size() ? entryList.size() : currentPage * pageSize;
			
			for (int i = start; i < end; i++) {
				SyndEntry entry = (SyndEntry) entryList.get(i);
				String released_dtime = "";
				if(entry.getPublishedDate() != null)
					released_dtime = DateUtil.getDateTimeString(entry.getPublishedDate());
				String title = entry.getTitle();
				String content_url = entry.getLink();
				InfoBean info = new InfoBean();
				info.setReleased_dtime(released_dtime);
				info.setTitle(title);
				info.setContent_url(content_url);
				infos.add(info);
			}
			return infos;
		} catch (Exception e) {
			e.printStackTrace();
			return infos;
		}
	}
	
public static int getRssInfoCount(String url) {
		
		List<InfoBean> infos = new ArrayList<InfoBean>();
		try{
			URLConnection feedUrl = new URL(url).openConnection();
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(feedUrl));
			List entryList = feed.getEntries();
			if(entryList != null)
			{
				return entryList.size();
			}
			else
			{
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}