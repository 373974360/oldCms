package com.deya.wcm.services.member;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.member.MemberCategoryBean;
import com.deya.wcm.bean.member.MemberReleUser;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.member.MemberReleUserDAO;
import com.deya.wcm.services.org.group.GroupManager;

import java.util.*;

/**
 *  会员分类与人员关联逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 会员分类与人员关联逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class MemberReleUserManager implements ISyncCatch{
	private static Map<Integer,MemberReleUser> mru_map = new HashMap<Integer,MemberReleUser>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		mru_map.clear();
		List<MemberReleUser> mr_list = MemberReleUserDAO.getMemberReleUserList();
		if(mr_list != null && mr_list.size() > 0)
		{
			for(int i=0;i<mr_list.size();i++)
			{
				mru_map.put(mr_list.get(i).getId(), mr_list.get(i));
			}
		}
	}
	
	public static void reloadMemberReleUser()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.member.MemberReleUserManager");
	}
	
	/**
	 * 根据会员分类ID得到关联的用户和用户组列表
	 * 
	 * @param int mcat_id 
	 * @return list
	 */	
	public static List<MemberReleUser> getMemberReleUserListByCat(int mcat_id,String site_id)
	{
		List<MemberReleUser> r_list = new ArrayList<MemberReleUser>();
		Set<Integer> rSet = mru_map.keySet();
		for(int i : rSet)
		{
			MemberReleUser mru = mru_map.get(i);
			if(mru.getMcat_id() == mcat_id && site_id.equals(mru.getSite_id()))
			{
				r_list.add(mru);
			}
		}
		return r_list;
	}
	
	/**
	 * 根据用户ID，站点ID，应用ID得以它所能管理的会员分类ID集合
	 * 
	 * @param String user_id 
	 * @param String site_id 
	 * @param String app_id 
	 * @return Set
	 */	
	public static String getMCatIDByUser(int user_id, String site_id)
	{
		String grup_ids = GroupManager.getGroupIDSByUserID(user_id+"");//该用户所在的用户组
		if(grup_ids != null && !"".equals(grup_ids))
			grup_ids = ","+grup_ids+",";
		String cat_ids = "";   
		Set<Integer> rSet = mru_map.keySet();
		
		for(int i : rSet)
		{
			MemberReleUser mru = mru_map.get(i);
			
			if(mru.getPriv_type() == 0 && user_id == mru.getPrv_id() && site_id.equals(mru.getSite_id()))
			{
                cat_ids += MemberCategoryManager.getMemberCategoryByID(mru.getMcat_id()+"").getMcat_id() + ",";
			}
			if(mru.getPriv_type() == 1 && grup_ids.contains(mru.getPrv_id()+"") && site_id.equals(mru.getSite_id()))
			{
                cat_ids += MemberCategoryManager.getMemberCategoryByID(mru.getMcat_id()+"").getMcat_id() + ",";
			}
		}
        if(cat_ids.length() > 1)
        {
            cat_ids = cat_ids.substring(0,cat_ids.length() - 1);
        }
		return cat_ids;
	}
	
	/**
	 * 插入会员分类与人员的关联(以分类为主)
	 * 
	 * @param int mcat_id
	 * @param String usre_ids
	 * @param String group_ids
	 * @param String app_id
	 * @param String site_id
	 * @param SettingLogsBean stl
	 * @return boolean
	 */	
	public static boolean insertMemberReleUserByCat(int mcat_id,String usre_ids,String group_ids,String app_id,String site_id,SettingLogsBean stl)
	{
		if(MemberReleUserDAO.insertMemberReleUserByCat(mcat_id, usre_ids, group_ids, app_id, site_id, stl))
		{
			reloadMemberReleUser();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * 根据会员分类ID删除关联信息
	 * 
	 * @param String mcat_id 
	 * @return boolean
	 */	
	public static boolean deleteMRUByCat(String mcat_id,String site_id)
	{
		if(MemberReleUserDAO.deleteMRUByCat(mcat_id,site_id))
		{
			reloadMemberReleUser();
			return true;
		}
		else
			return false;
	}
	
	public static void main(String[] args)
	{

    }
}
