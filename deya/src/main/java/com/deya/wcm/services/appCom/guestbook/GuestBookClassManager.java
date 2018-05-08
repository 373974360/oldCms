package com.deya.wcm.services.appCom.guestbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.deya.wcm.bean.appCom.guestbook.GuestBookClass;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.appCom.guestbook.GuestBookDAO;

public class GuestBookClassManager implements ISyncCatch{
	private static TreeMap<Integer,GuestBookClass> cat_map = new TreeMap<Integer,GuestBookClass>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		cat_map.clear();
		try{
			List<GuestBookClass> l = GuestBookDAO.getAllGuestBookClassList();
			if(l != null && l.size() > 0)
			{
				for(GuestBookClass cat : l)
				{
					cat_map.put(cat.getClass_id(), cat);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void reloadGuestBook()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.appCom.guestbook.GuestBookClassManager");
	}
	
	public static List<GuestBookClass> getGuestBookClassList(String site_id,int cat_id)
	{
		List<GuestBookClass> l = new ArrayList<GuestBookClass>();
		Set<Integer> s = cat_map.keySet();
		if(s != null && s.size() > 0)
		{
			for(Integer i:s)
			{
				GuestBookClass cat = cat_map.get(i);
				if(cat.getCat_id() == cat_id && site_id.equals(cat.getSite_id()))
				{					
					l.add(cat);
				}
			}
		}
		if(l != null && l.size() > 0)
			Collections.sort(l,new GuestBookClassComparator());
		return l;
	}
	
	public static GuestBookClass getGuestBookClassBean(int class_id)
	{
		if(cat_map.containsKey(class_id))
			return cat_map.get(class_id);
		else
			return null;
	}
		
	public static boolean insertGuestBookClass(GuestBookClass cat,SettingLogsBean stl)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.GUESTBOOK_CLASS_TABLE_NAME);
		cat.setClass_id(id);
		cat.setId(id);
		if(GuestBookDAO.insertGuestBookClass(cat, stl))
		{
			reloadGuestBook();
			return true;
		}else
			return false;
	}
	
	public static boolean updateGuestBookClass(GuestBookClass cat,SettingLogsBean stl)
	{
		if(GuestBookDAO.updateGuestBookClass(cat, stl))
		{
			reloadGuestBook();
			return true;
		}else
			return false;
	}
	
	/**
     * 修改留言分类发布状态
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean publishGuestBookClass(Map<String,String> m,SettingLogsBean stl)
	{
		if(GuestBookDAO.publishGuestBookClass(m, stl))
		{
			reloadGuestBook();
			return true;
		}else
			return false;
	}
	
	/**
     * 留言分类排序
     * @param String cat_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean sortGuestBookClass(String class_ids,SettingLogsBean stl)
	{
		if(GuestBookDAO.sortGuestBookClass(class_ids, stl))
		{
			reloadGuestBook();
			return true;
		}else
			return false;		
	}
	
	/**
     * 删除留言分类
     * @param Map m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteGuestBookClass(Map<String,String> m,SettingLogsBean stl)
	{
		if(GuestBookDAO.deleteGuestBookClass(m, stl))
		{
			reloadGuestBook();
			return true;
		}else
			return false;
	}
	
	static class GuestBookClassComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			GuestBookClass gbc1 = (GuestBookClass) o1;
			GuestBookClass gbc2 = (GuestBookClass) o2;
		    if (gbc1.getSort_id() > gbc2.getSort_id()) {
		     return 1;
		    } else {
		     if (gbc1.getSort_id() == gbc2.getSort_id()) {
		      return 0;
		     } else {
		      return -1;
		     }
		    }
		}
	}
}
