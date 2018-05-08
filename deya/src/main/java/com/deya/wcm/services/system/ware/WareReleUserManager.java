package com.deya.wcm.services.system.ware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.ware.WareCategoryBean;
import com.deya.wcm.bean.system.ware.WareReleUser;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.system.ware.WareReleUserDAO;
import com.deya.wcm.services.org.group.GroupManager;

/**
 *  标签分类与人员关联逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 标签分类与人员关联逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class WareReleUserManager implements ISyncCatch{
	private static Map<Integer,WareReleUser> wru_map = new HashMap<Integer,WareReleUser>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		wru_map.clear();
		List<WareReleUser> wr_list = WareReleUserDAO.getWareReleUserList();
		if(wr_list != null && wr_list.size() > 0)
		{
			for(int i=0;i<wr_list.size();i++)
			{
				wru_map.put(wr_list.get(i).getId(), wr_list.get(i));
			}
		}
	}
	
	public static void reloadWareReleUser()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.system.ware.WareReleUserManager");
	}
	
	/**
	 * 根据标签分类ID得到关联的用户和用户组列表
	 * 
	 * @param int wcat_id 
	 * @return list
	 */	
	public static List<WareReleUser> getWareReleUserListByCat(int wcat_id,String site_id)
	{
		List<WareReleUser> r_list = new ArrayList<WareReleUser>();
		Set<Integer> rSet = wru_map.keySet();
		for(int i : rSet)
		{
			WareReleUser wru = wru_map.get(i);
			if(wru.getWcat_id() == wcat_id && site_id.equals(wru.getSite_id()))
			{
				r_list.add(wru);
			}
		}
		return r_list;
	}
	
	/**
	 * 根据用户ID，站点ID，应用ID得以它所能管理的标签分类ID集合
	 * 
	 * @param String user_id 
	 * @param String site_id 
	 * @param String app_id 
	 * @return Set
	 */	
	public static Set<WareCategoryBean> getWCatIDByUser(int user_id,String site_id)
	{
		String grup_ids = GroupManager.getGroupIDSByUserID(user_id+"");//该用户所在的用户组
		if(grup_ids != null && !"".equals(grup_ids))
			grup_ids = ","+grup_ids+",";
		Set<WareCategoryBean> c_set = new HashSet<WareCategoryBean>();
		Set<Integer> rSet = wru_map.keySet();
		
		for(int i : rSet)
		{
			WareReleUser wru = wru_map.get(i);
			
			if(wru.getPriv_type() == 0 && user_id == wru.getPrv_id() && site_id.equals(wru.getSite_id()))
			{
				c_set.add(WareCategoryManager.getWareCteBeanByWID(wru.getWcat_id()+"",site_id));
			}
			if(wru.getPriv_type() == 1 && grup_ids.contains(wru.getPrv_id()+"") && site_id.equals(wru.getSite_id()))
			{
				c_set.add(WareCategoryManager.getWareCteBeanByWID(wru.getWcat_id()+"",site_id));
			}
		}
		return c_set;
	}
	
	/**
	 * 插入标签分类与人员的关联(以分类为主)
	 * 
	 * @param int wcat_id
	 * @param String usre_ids
	 * @param String group_ids
	 * @param String app_id
	 * @param String site_id
	 * @param SettingLogsBean stl
	 * @return boolean
	 */	
	public static boolean insertWareReleUserByCat(int wcat_id,String usre_ids,String group_ids,String app_id,String site_id,SettingLogsBean stl)
	{
		if(WareReleUserDAO.insertWareReleUserByCat(wcat_id, usre_ids, group_ids, app_id, site_id, stl))
		{
			reloadWareReleUser();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * 根据标签分类ID删除关联信息
	 * 
	 * @param String wcat_id 
	 * @return boolean
	 */	
	public static boolean deleteWRUByCat(String wcat_id,String site_id)
	{
		if(WareReleUserDAO.deleteWRUByCat(wcat_id,site_id))
		{
			reloadWareReleUser();
			return true;
		}
		else
			return false;
	}
	
	public static void main(String[] args)
	{}
}
