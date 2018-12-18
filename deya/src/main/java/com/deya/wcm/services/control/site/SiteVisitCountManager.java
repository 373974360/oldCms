package com.deya.wcm.services.control.site;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.control.SiteConfigBean;
import com.deya.wcm.bean.control.SiteVisitCountBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.control.SiteVisitCountDAO;
import com.deya.wcm.services.control.config.SiteConfigManager;
import com.deya.wcm.services.control.domain.SiteDomainManager;

/**
 *  站点访问量统计.
 * <p>Title: CicroDate</p>
 * <p>Description: 站点访问量统计</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class SiteVisitCountManager {
	private static Map<String,SiteVisitCountBean> count_map = new HashMap<String,SiteVisitCountBean>();
	private static int DEFAULT_FREQUENCY = Integer.parseInt(JconfigUtilContainer.bashConfig().getProperty("num", "30", "site_count_freq"));//写库频率，如刷新30次以后就入一次库
	
	static{
		reloadSiteVisitCount();
	}
	
	public static void reloadSiteVisitCount()
	{
		count_map.clear();
		List<SiteVisitCountBean> l = SiteVisitCountDAO.getAllSiteVisitCount();
		if(l != null && l.size() > 0)
		{
			for(SiteVisitCountBean svcb : l)
			{
				SiteConfigBean scb = SiteConfigManager.getConfigValues(svcb.getSite_id(), "click_step");
				if(scb != null && scb.getConfig_value() != null && !"".equals(scb.getConfig_value()))
					svcb.setClick_step(Integer.parseInt(scb.getConfig_value()));
				svcb.setIs_exist(true);
				count_map.put(svcb.getSite_id(), svcb);
			}
		}
	}
	
	/**
	 * 根据站点和类型得到点击次数
	 * @param String hit_type
	 * @param HttpServletRequest request
	 * @return int
	 */
	public static int getHit(String hit_type,HttpServletRequest request)
	{
		reloadSiteVisitCount();
		String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
		if(hit_type.equals("day_hits"))
		{
			return count_map.get(site_id).getDay_hits();
		}
		if(hit_type.equals("week_hits"))
		{
			return count_map.get(site_id).getWeek_hits();
		}
		if(hit_type.equals("month_hits"))
		{
			return count_map.get(site_id).getMonth_hits();
		}
		return count_map.get(site_id).getHits();
	}
	
	/**
	 * 根据站点修改总点击次数
	 * @param String hit_type
	 * @param HttpServletRequest request
	 * @return int
	 */
	public static boolean updateHitForSite(String site_id,int count)
	{
		try{
			if(count_map.containsKey(site_id))
			{
				count_map.get(site_id).setHits(count);
			}else
			{
				SiteVisitCountBean svcb = new SiteVisitCountBean();
				svcb.setHits(count);
				svcb.setSite_id(site_id);	
				count_map.put(site_id, svcb);
			}
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 根据站点修改步长
	 * @param String site_id
	 * @param int step
	 * @return 
	 */
	public static boolean updateStep(String site_id,int step)
	{
		try{
			if(count_map.containsKey(site_id))
			{
				count_map.get(site_id).setClick_step(step);
			}else
			{			
				SiteVisitCountBean svcb = new SiteVisitCountBean();
				svcb.setSite_id(site_id);
				svcb.setClick_step(step);
				count_map.put(site_id, svcb);			
			}
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
			
	/**
	 * 添加点击次数
	 * @param HttpServletRequest request
	 * @return List
	 */
	public static void addSiteHits(HttpServletRequest request)
	{
		String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());		
		if(count_map.containsKey(site_id))
		{
			addSiteHits(site_id);
		}else
		{					
			SiteVisitCountBean svcb = new SiteVisitCountBean();
			svcb.setSite_id(site_id);
			svcb.setDay_hits(svcb.getClick_step());
			svcb.setHits(svcb.getHits()+svcb.getClick_step());
			svcb.setMonth_hits(svcb.getClick_step());
			svcb.setWeek_hits(svcb.getClick_step());
			count_map.put(site_id, svcb);
		}
	}
	
	/**
	 * 添加点击次数,入库
	 * @param 
	 * @return List
	 */
	public static void addSiteHits(String site_id)
	{		
		SiteVisitCountBean svcb = count_map.get(site_id);
		svcb.setTemp_count(svcb.getTemp_count()+1);
		svcb.setDay_hits(svcb.getDay_hits()+svcb.getClick_step());
		svcb.setHits(svcb.getHits()+svcb.getClick_step());
		svcb.setMonth_hits(svcb.getMonth_hits()+svcb.getClick_step());
		svcb.setWeek_hits(svcb.getWeek_hits()+svcb.getClick_step());
		
		//if(svcb.getTemp_count() == DEFAULT_FREQUENCY)
		//{
			//达到频率了，入库
			int count = svcb.getTemp_count() * 1;
			Map<String,String> m = new HashMap<String,String>();
			m.put("site_id", site_id);
			m.put("app_id", "cms");
			m.put("hits", svcb.getHits()+"");
			m.put("h_count", count+"");
			if(svcb.getIs_exist() == true)
			{
				if(SiteVisitCountDAO.updateSiteHits(m));
				{
					svcb.setTemp_count(0);
				}
			}else
			{
				m.put("id", PublicTableDAO.getIDByTableName(PublicTableDAO.SITECOUNT_TABLE_NAME)+"");
				if(SiteVisitCountDAO.insertSiteHits(m));
				{
					svcb.setTemp_count(0);
					svcb.setIs_exist(true);
				}
			}
		//}
	}
	
	/**
	 * 根据类型清空对应的数值，用于每晚的定时任务
	 * @param 
	 * @return 
	 */
	public static void clearHits(String hit_type)
	{
		Map<String,String> m = new HashMap<String,String>();
		if(hit_type.equals("week"))
		{
			m.put("week", "true");
		}
		if(hit_type.equals("month"))
		{
			m.put("month", "true");
		}
		SiteVisitCountDAO.clearSiteHits(m);
	}
}
