package com.deya.wcm.services.appCom.guestbook;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.deya.wcm.bean.appCom.guestbook.GBookReleUser;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.appCom.guestbook.GuestBookDAO;

public class GBookReleUserManager implements ISyncCatch{
	private static TreeMap<Integer,GBookReleUser> rele_user_map = new TreeMap<Integer,GBookReleUser>();
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		rele_user_map.clear();
		try{
			List<GBookReleUser> l = GuestBookDAO.getGuestBookReleUserList();
			if(l != null && l.size() > 0)
			{
				for(GBookReleUser rele_user : l)
				{
					rele_user_map.put(rele_user.getId(), rele_user);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void reloadGuestBookReleUser()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.appCom.guestbook.GBookReleUserManager");
	}
	
	/**
     * 根据用户ID和站点ID得到他能管理的留言分类ID
     * @param int user_id
     * @param String site_id
     * @return String
     * */
	public static String getGBCatIDForUser(int user_id,String site_id)
	{
		String cat_ids = "";
		Set<Integer> ids = rele_user_map.keySet();
		for(Integer i : ids)
		{
			GBookReleUser gb_user = rele_user_map.get(i);
			if(gb_user.getUser_id() == user_id && site_id.equals(gb_user.getSite_id()))
				cat_ids += ","+gb_user.getCat_id();
		}
		if(cat_ids.startsWith(","))
			cat_ids = cat_ids.substring(1);
		
		return cat_ids;
	}
	
	/**
     * 根据分类ID和站点ID得到管理人员ID
     * @param int cat_id
     * @param String site_id
     * @return String
     * */
	public static String getUserIDForGBCat(int cat_id,String site_id)
	{
		String user_ids = "";
		Set<Integer> ids = rele_user_map.keySet();
		for(Integer i : ids)
		{
			GBookReleUser gb_user = rele_user_map.get(i);
			if(gb_user.getCat_id() == cat_id && site_id.equals(gb_user.getSite_id()))
				user_ids += ","+gb_user.getUser_id();
		}
		if(user_ids.startsWith(","))
			user_ids = user_ids.substring(1);
		
		return user_ids;
	}
	
	public static boolean insertGuestBookReleUser(int cat_id,String user_ids,String site_id,SettingLogsBean stl)
	{
		if(GuestBookDAO.insertGuestBookReleUser(cat_id,user_ids,site_id,stl))
		{
			reloadGuestBookReleUser();
			return true;
		}else
			return false;
	}
}
