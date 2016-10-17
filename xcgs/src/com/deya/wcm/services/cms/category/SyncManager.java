package com.deya.wcm.services.cms.category;

import java.util.ArrayList;
import java.util.List;
import com.deya.wcm.bean.cms.category.SyncBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.cms.category.SyncDAO;

/**
 *  目录同步逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 目录同步数据处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author liqi
 * @version 1.0
 * * 
 */
public class SyncManager implements ISyncCatch{
	
	private static List<SyncBean> sync_list = new ArrayList<SyncBean>();
	
	static 
	{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		sync_list.clear();
		sync_list = SyncDAO.getAllSyncBeanList();
	}
	
	/**
	 *  初始化加载目录同步信息
	 */
	public static void reloadSync()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.cms.category.SyncManager");
	}
	
	/**
	 * 根据站点ID,cat_id得到推送列表
	 * @param String s_site_id
	 * @param int cat_id
	 * @return	List
	 */
	public static List<SyncBean> getToInfoCategoryList(String s_site_id,int cat_id)
	{
		List<SyncBean> s_list = new ArrayList<SyncBean>();
		if(sync_list != null && sync_list.size() > 0)
		{
			for(SyncBean sb : sync_list)
			{
				if(s_site_id.equals(sb.getS_site_id()) && sb.getS_cat_id() == cat_id && sb.getOrientation() == 1)
					s_list.add(sb);
			}
		}
		return s_list;
	}
	
	/**
	 * 根据站点ID,cat_id得到同步列表
	 * @param String s_site_id
	 * @param int cat_id
	 * @return	List
	 */
	public static List<SyncBean> getSyncListBySiteCatID(String s_site_id,int cat_id)
	{
		List<SyncBean> s_list = new ArrayList<SyncBean>();
		if(sync_list != null && sync_list.size() > 0)
		{
			for(SyncBean sb : sync_list)
			{
				if(s_site_id.equals(sb.getS_site_id()) && sb.getS_cat_id() == cat_id && sb.getOrientation() == 0)
					s_list.add(sb);
			}
		}
		return s_list;
	}
	
	/**
	 * 根据站点t_site_id,目标t_cat_id得到需要同步此栏目信息的栏目列表
	 * @param String t_site_id
	 * @param int t_cat_id
	 * @return	List
	 */
	public static List<SyncBean> getSyncCatListBySiteCatID(String t_site_id,int t_cat_id)
	{
		List<SyncBean> s_list = new ArrayList<SyncBean>();
		if(sync_list != null && sync_list.size() > 0)
		{
			for(SyncBean sb : sync_list)
			{
				if(t_site_id.equals(sb.getT_site_id()) && sb.getT_cat_id() == t_cat_id && sb.getOrientation() == 0)
					s_list.add(sb);
			}
		}
		return s_list;
	}
	
	/**
	 * 插入目录同步信息
	 * @param List<SyncBean> sList
	 * @param String s_cat_id
	 * @param String s_site_id
	 * @return		true 成功| false 失败
	 */
	public static boolean insertSync(List<SyncBean> sList,String s_cat_id,String s_site_id,String orientation)
	{		
		//首先删除信息
		SyncDAO.deleteSync(s_site_id,s_cat_id,orientation);
		if(sList != null && sList.size() > 0)
		{
			try{
				for(SyncBean sb : sList)
				{
					//sb.setOrientation(Integer.parseInt(orientation));
					SyncDAO.insertSync(sb);
				}
			}catch(Exception e)
			{
				return false;
			}
		}
		reloadSync();
		return true;		
	}
	
	
	/**
	 * 删除目录时删除同步信息
	 * @param String site_id
	 * @param String cat_ids
	 * @return		true 成功| false 失败	 
	 */
	public static boolean deleteSyncForCatID(String site_id,String cat_ids)
	{
		if(SyncDAO.deleteSyncForCatID(site_id, cat_ids))
		{
			reloadSync();
			return true;
		}else
			return false;
	}
	
	public static void main(String[] args)
	{
		System.out.println(getSyncCatListBySiteCatID("HIWCMkjj",340).get(0).getT_site_id());
	}
}
