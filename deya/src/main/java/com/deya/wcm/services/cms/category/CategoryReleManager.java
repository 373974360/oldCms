package com.deya.wcm.services.cms.category;

import java.util.ArrayList;
import java.util.List;

import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.category.CategoryReleBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.cms.category.CategoryReleDAO;
import com.deya.wcm.services.org.group.GroupManager;

/**
 *  目录与人员关联逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 目录与人员关联逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author liqi
 * @version 1.0
 * * 
 */

public class CategoryReleManager implements ISyncCatch{
	private static List<CategoryReleBean> cr_list = new ArrayList<CategoryReleBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		cr_list.clear();
		cr_list = CategoryReleDAO.getCategoryReleUserList();
		CategoryTreeUtil.reloadMap();
	}
	
	public static void reloadCategoryRele()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.cms.category.CategoryReleManager");
	}
	
	/**
	 * 根据目录ID得到关联列表
	 * @param int cat_id
	 * @return List
	 */
	public static List<CategoryReleBean> getCategoryReleUserListByCatID(int cat_id,String site_id)
	{
		List<CategoryReleBean> list = new ArrayList<CategoryReleBean>();
		if(cr_list != null && cr_list.size() > 0)
		{
			for(int i=0;i<cr_list.size();i++)
			{
				if(cr_list.get(i).getCat_id() == cat_id && site_id.equals(cr_list.get(i).getSite_id()))
					list.add(cr_list.get(i));
			}
		}
		return list;
	}
	
	/**
	 * 根据用户，或用户组得到它所能管理的栏目ID
	 * @param int cat_id
	 * @return List
	 */
	public static String getCategoryIDSByUser(int priv_type,int prv_id,String site_id)
	{
		String ids = "";
		if(cr_list != null && cr_list.size() > 0)
		{
			for(int i=0;i<cr_list.size();i++)
			{
				if(cr_list.get(i).getPriv_type() == priv_type && cr_list.get(i).getPrv_id() == prv_id && site_id.equals(cr_list.get(i).getSite_id()))
					ids += ","+cr_list.get(i).getCat_id();
			}
			if(ids != null && !"".equals(ids))
				ids = ids.substring(1);
		}
		return ids;
	}
	
	/**
	 * 插入目录与人员的关联(用于在站点用户管理中插入关联)
	 * @param String cat_ids
	 * @param int prv_id 类型0为用户　1为组
	 * @param int user_id
	 * @param String site_id
	 * @return boolean
	 */
	public static boolean insertCategoryReleUser(String cat_ids,int priv_type,int prv_id,String site_id)
	{
		CategoryReleDAO.deleteCategoryReleUserByCatID(priv_type,prv_id+"",site_id);
		if(cat_ids != null && !"".equals(cat_ids))
		{
			try{
				String[] tempA = cat_ids.split(",");
				CategoryReleBean crb = new CategoryReleBean();
				crb.setPriv_type(priv_type);
				crb.setPrv_id(prv_id);
				crb.setSite_id(site_id);
				for(int i=0;i<tempA.length;i++)
				{
					crb.setCat_id(Integer.parseInt(tempA[i]));				
					CategoryReleDAO.insertCategoryReleUser(crb);
				}
				
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}	
		reloadCategoryRele();
		return true;
	}
		
	/**
	 * 根据类型，ID，站点ID删除用户或用户组同栏目的关联
	 * @param int priv_type
	 * @param String prv_id
	 * @param String site_id
	 * @return boolean
	 */
	public static boolean deleteCateReleByPrv(int priv_type,String prv_id,String site_id)
	{
		if(CategoryReleDAO.deleteCategoryReleUserByCatID(priv_type,prv_id,site_id))
		{
			reloadCategoryRele();
			return true;
		}else
			return false;
	}
	
	/**
	 * 插入目录与人员的关联
	 * @param List<CategoryReleBean> list
	 * @param int cat_id
	 * @param String site_id
	 * @return boolean
	 */
	public static boolean insertCategoryReleUser(List<CategoryReleBean> list,int cat_id,String site_id)
	{
		deleteCategoryReleUserByCatID(cat_id+"",site_id);
		if(list != null && list.size() > 0)
		{
			try{
				for(int i=0;i<list.size();i++)
				{
					CategoryReleDAO.insertCategoryReleUser(list.get(i));
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		reloadCategoryRele();
		return true;
	}
	
	/**
	 * 根据目录ID删除关联
	 * @param String cat_id
	 * @param String prv_id
	 * @return boolean		
	 */
	public static boolean deleteCategoryReleUserByCatID(String cat_id,String site_id)
	{
		return CategoryReleDAO.deleteCategoryReleUserByCatID(cat_id,site_id);
	}
	
	/**
	 * 根据站点ID删除关联
	 * @param Sting site_id
	 * @return boolean		
	 */
	public static boolean deleteCategoryReleUserBySiteID(String site_id)
	{
		if(CategoryReleDAO.deleteCategoryReleUserBySiteID(site_id))
		{
			reloadCategoryRele();
			return true;
		}else
			return false;
	}
	
	/**
	 * 根据人员ID，站点ID，栏目ID判断它是否是这个栏目的管理员
	 * @param int user_id
	 * @param String site_id
	 * @param int cat_id
	 * @return boolean		
	 */
	public static boolean isCategoryManagerByUser(int user_id,String site_id,int cat_id)
	{
		//得到该用户所在的用户组
		String user_group_ids = ","+GroupManager.getGroupIDSByUserID(user_id+"")+",";
		CategoryBean cgb = CategoryManager.getCategoryBean(cat_id);
		if(cgb != null)
		{
			CategoryBean cgb2 = null;
			if(cr_list != null && cr_list.size() > 0)
			{
				for(int i=0;i<cr_list.size();i++)
				{//0表示用户  1表示用户组
					if(site_id.equals(cr_list.get(i).getSite_id()))
					{
						cgb2 = CategoryManager.getCategoryBean(cr_list.get(i).getCat_id());
						if(cr_list.get(i).getCat_id() == cat_id || cgb.getCat_position().indexOf(cr_list.get(i).getCat_id()+"") > 0 || cgb2.getCat_position().indexOf(cat_id+"") > 0)
						{
							boolean tmp1 = cr_list.get(i).getPriv_type() == 0 && cr_list.get(i).getPrv_id() == user_id;
							boolean tmp2 = cr_list.get(i).getPriv_type() == 1 && user_group_ids.contains(","+cr_list.get(i).getPrv_id()+",");
							if(tmp1 || tmp2 || user_id == 1)
								return true;		
						}
					}
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		System.out.println(getCategoryIDSByUser(1,116,"11111ddd"));
	}
}
