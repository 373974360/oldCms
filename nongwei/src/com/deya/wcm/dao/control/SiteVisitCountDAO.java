package com.deya.wcm.dao.control;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.control.SiteVisitCountBean;
import com.deya.wcm.db.DBManager;

public class SiteVisitCountDAO {
	@SuppressWarnings("unchecked")
	public static List<SiteVisitCountBean>	getAllSiteVisitCount()
	{
		return DBManager.queryFList("getAllSiteVisitCount", "");
	}
	
	public static boolean insertSiteHits(Map<String,String> m)
	{
		return DBManager.insert("insert_site_hits", m);
	}
	
	public static boolean updateSiteHits(Map<String,String> m)
	{
		return DBManager.update("update_site_hits", m);
	}
	
	public static boolean clearSiteHits(Map<String,String> m)
	{
		return DBManager.update("clear_site_hits", m);
	}
}
