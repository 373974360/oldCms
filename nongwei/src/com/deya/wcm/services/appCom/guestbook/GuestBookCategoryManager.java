package com.deya.wcm.services.appCom.guestbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.deya.wcm.bean.appCom.guestbook.GuestBookCategory;
import com.deya.wcm.bean.appCom.guestbook.GuestBookSub;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.appCom.guestbook.GuestBookDAO;

public class GuestBookCategoryManager implements ISyncCatch{
	private static TreeMap<Integer,GuestBookCategory> cat_map = new TreeMap<Integer,GuestBookCategory>();
	
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
			List<GuestBookCategory> l = GuestBookDAO.getAllGuestBookCategoryList();
			if(l != null && l.size() > 0)
			{
				for(GuestBookCategory cat : l)
				{
					cat_map.put(cat.getCat_id(), cat);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void reloadGuestBook()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.appCom.guestbook.GuestBookCategoryManager");
	}
	
	public static List<GuestBookCategory> getGuestBookCategoryList(String site_id)
	{
		List<GuestBookCategory> l = new ArrayList<GuestBookCategory>();
		Set<Integer> s = cat_map.keySet();
		if(s != null && s.size() > 0)
		{
			for(Integer i:s)
			{
				GuestBookCategory cat = cat_map.get(i).clone();
				System.out.println(cat);
				if(site_id.equals(cat.getSite_id()))
				{					
					cat.setDescription("");
					l.add(cat);
				}
			}
		}
		if(l != null && l.size() > 0)
			Collections.sort(l,new GuestBookCategoryComparator());
		return l;
	}
	
	public static List<GuestBookCategory> getGuestBookCategoryListForIDS(String site_id,String cat_ids)
	{
		List<GuestBookCategory> l = new ArrayList<GuestBookCategory>(); 
		if(cat_ids != null && !"".equals(cat_ids))
		{
			String[] tempA = cat_ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				int c_id = Integer.parseInt(tempA[i]);
				if(cat_map.containsKey(c_id))
				{
					l.add(cat_map.get(c_id));
				}
			}
		}
		return l;
	}
	
	public static GuestBookCategory getGuestBookCategoryBean(int cat_id)
	{
		if(cat_map.containsKey(cat_id))
			return cat_map.get(cat_id);
		else
			return null;
	}
		
	public static boolean insertGuestBookCategory(GuestBookCategory cat,SettingLogsBean stl)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.GUESTBOOK_CATEGORY_TABLE_NAME);
		cat.setCat_id(id);
		cat.setId(id);
		if(GuestBookDAO.insertGuestBookCategory(cat, stl))
		{
			reloadGuestBook();
			return true;
		}else
			return false;
	}
	
	public static boolean updateGuestBookCategory(GuestBookCategory cat,SettingLogsBean stl)
	{
		if(GuestBookDAO.updateGuestBookCategory(cat, stl))
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
	public static boolean publishGuestBookCategory(Map<String,String> m,SettingLogsBean stl)
	{
		if(GuestBookDAO.publishGuestBookCategory(m, stl))
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
	public static boolean sortGuestBookCategory(String cat_ids,SettingLogsBean stl)
	{
		if(GuestBookDAO.sortGuestBookCategory(cat_ids, stl))
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
	public static boolean deleteGuestBookCategory(Map<String,String> m,SettingLogsBean stl)
	{
		if(GuestBookDAO.deleteGuestBookCategory(m, stl))
		{
			reloadGuestBook();
			return true;
		}else
			return false;
	}
	
	static class GuestBookCategoryComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			GuestBookCategory gbc1 = (GuestBookCategory) o1;
			GuestBookCategory gbc2 = (GuestBookCategory) o2;
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
	
	public static void main(String args[])
	{
		System.out.println(getGuestBookCategoryList("HIWCMdemo"));
	}
}
