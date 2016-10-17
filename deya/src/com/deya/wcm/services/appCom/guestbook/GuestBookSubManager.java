package com.deya.wcm.services.appCom.guestbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.appCom.guestbook.GuestBookSub;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.appCom.guestbook.GuestBookDAO;

/**
 * @title 留言板主题逻辑处理类 
 * @author 
 * @version 1.0
 * @date   2011-6-14 下午04:29:47
 */

public class GuestBookSubManager implements ISyncCatch{
	
	public static Map<Integer,GuestBookSub> gbs_map = new HashMap<Integer,GuestBookSub>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		gbs_map.clear();
		try{
			List<GuestBookSub> l = GuestBookDAO.getAllGuestBookSubList();
			if(l != null && l.size() > 0)
			{
				for(GuestBookSub gbs : l)
				{
					gbs_map.put(gbs.getGbs_id(), gbs);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void reloadGuestBook()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.appCom.guestbook.GuestBookSubManager");
	}
		
	/**
     * 得到留言板主题列表 
     * @param 
     * @return List
     * */
	public static List<GuestBookSub> getGuestBookSubListForDB(Map<String,String> m)
	{
		if(m.containsKey("user_id"))
		{
			String cat_ids = GBookReleUserManager.getGBCatIDForUser(Integer.parseInt(m.get("user_id")), m.get("site_id"));
			if(cat_ids == "" || "".equals(cat_ids))
				return new ArrayList<GuestBookSub>();
			else
				m.put("cat_ids", cat_ids);
		}
		return GuestBookDAO.getGuestBookSubListForDB(m);
	}
	
	/**
     * 得到留言板主题列表 
     * @param String site_id
     * @param String gbs_ids
     * @return List
     * */
	public static List<GuestBookSub> getGuestBookSubListForIDS(String site_id,String gbs_ids)
	{
		List<GuestBookSub> l = new ArrayList<GuestBookSub>(); 
		if(gbs_ids != null && !"".equals(gbs_ids))
		{
			String[] tempA = gbs_ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				int g_id = Integer.parseInt(tempA[i]);
				if(gbs_map.containsKey(g_id))
				{
					l.add(gbs_map.get(g_id));
				}
			}
		}
		return l;
	}
	
	/**
     * 得到留言板主题总数 
     * @param 
     * @return List
     * */
	public static String getGuestBookSubCount(Map<String,String> m)
	{
		if(m.containsKey("user_id"))
		{
			String cat_ids = GBookReleUserManager.getGBCatIDForUser(Integer.parseInt(m.get("user_id")), m.get("site_id"));
			if(cat_ids == "" || "".equals(cat_ids))
				return "0";
			else
				m.put("cat_ids", cat_ids);
		}
		return GuestBookDAO.getGuestBookSubCount(m);
	}
	
	/**
     * 得到留言板主题对象 
     * @param 
     * @return List
     * */
	public static GuestBookSub getGuestBookSub(int gbs_id)
	{
		return gbs_map.get(gbs_id);
	}
	
	/**
     * 添加留言板主题
     * @param GuestBookSub gbs
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertGuestBookSub(GuestBookSub gbs,SettingLogsBean stl)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.GUESTBOOKSUB__TABLE_NAME);
		gbs.setId(id);
		gbs.setGbs_id(id);
		gbs.setAdd_time(DateUtil.getCurrentDateTime());
		if(gbs.getPublish_status() == 1)
			gbs.setPublish_time(DateUtil.getCurrentDateTime());
		if(GuestBookDAO.insertGuestBookSub(gbs, stl))
		{
			reloadGuestBook();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改留言板主题
     * @param GuestBookSub gbs
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateGuestBookSub(GuestBookSub gbs,SettingLogsBean stl)
	{
		if(gbs.getPublish_status() == 1)
			gbs.setPublish_time(DateUtil.getCurrentDateTime());
		else
			gbs.setPublish_time("");
		if(GuestBookDAO.updateGuestBookSub(gbs, stl))
		{
			reloadGuestBook();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 删除留言板主题
     * @param String gbs_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteGuestBookSub(String gbs_ids,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("gbs_ids", gbs_ids);
		if(GuestBookDAO.deleteGuestBookSub(m, stl))
		{
			reloadGuestBook();
			//删除内容
			GuestBookDAO.deleteGuestBook(m, null);
			return true;
		}return false;
	}
	
	/**
     * 修改留言板发布状态
     * @param String gbs_ids
     * @param String publish_status
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean publishGuestBookSub(String gbs_ids,String publish_status,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("gbs_ids", gbs_ids);
		m.put("publish_status", publish_status);
		if("1".equals(publish_status))
			m.put("publish_time", DateUtil.getCurrentDateTime());
		else
			m.put("publish_time", "");
		if(GuestBookDAO.publishGuestBookSub(m, stl))
		{
			reloadGuestBook();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 根据站点删除留言板主题
     * @param String site_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteGuestBookSubForSite(String site_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("site_id", site_id);
		if(GuestBookDAO.deleteGuestBookSub(m, null))
		{
			reloadGuestBook();
			//删除内容
			GuestBookDAO.deleteGuestBook(m, null);
			return true;
		}return false;
	}
	
	
	
	public static void main(String args[])
	{
		
	}
}
