package com.deya.wcm.services.cms.category;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.category.CategorySharedBean;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.cms.category.CategorySharedDAO;
import com.deya.wcm.services.control.domain.SiteDomainManager;
import com.deya.wcm.services.control.site.SiteManager;

/**
 *  目录共享管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 目录共享角色管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class CategorySharedManager implements ISyncCatch{
	private static List<CategorySharedBean> cs_list = new ArrayList<CategorySharedBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		cs_list.clear();
		cs_list = CategorySharedDAO.getAllCategorySharedList();
	}
	
	/**
	 * 初始加载目录共享信息
	 * 
	 * @param
	 * @return
	 */
	public static void reloadCategoryShared()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.cms.category.CategorySharedManager");
	}
	
	/**
     * 根据站点ID，栏目ID，共享类型得到共享列表
     * @param String site_id
     * @param int cat_id
     * @param int shared_type
     * @return List
     * */
	public static List<CategorySharedBean> getCategorySharedListBySCS(String site_id,int cat_id,int shared_type)
	{
		List<CategorySharedBean> list = new ArrayList<CategorySharedBean>();
		if(cs_list != null && cs_list.size() > 0)
		{			
			for(int i=0;i<cs_list.size();i++)
			{
				if(site_id.equals(cs_list.get(i).getS_site_id()) && cat_id == cs_list.get(i).getCat_id() && shared_type == cs_list.get(i).getShared_type())
				{
					list.add(cs_list.get(i));
				}
			}			
		}
		return list;
	}
	
	/**
     * 根据站点ID得到所有可以接收站点信息的站点集合    
     * @param String s_site_id
     * @return List
     * */
	public static List<SiteBean> getAllowReceiveSite(String t_site_id)
	{
		return getAllowSiteByType(t_site_id,1);
	}
	
	/**
     * 根据站点ID得到所有允许共享给该站点信息的站点集合    
     * @param String t_site_id
     * @return List
     * */
	public static List<SiteBean> getAllowSharedSite(String t_site_id)
	{
		return getAllowSiteByType(t_site_id,0);
	}
	
	public static String getAllowSharedSiteJSONStr(String t_site_id)
	{
		String str = "";
		List<SiteBean> l = getAllowSiteByType(t_site_id,0);
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{
				if(l.get(i) != null)
					str += ",{\"id\":"+(1000+i)+",\"text\":\""+l.get(i).getSite_name()+"\",\"attributes\":{\"real_site_id\":\""+l.get(i).getSite_id()+"\"}}";
			}
			if(!"".equals(str))
				str = str.substring(1);
		}else
		{
			str = "{\"id\":1001,\"text\":\""+SiteManager.getSiteBeanBySiteID(t_site_id).getSite_name()+"\",\"attributes\":{\"real_site_id\":\""+t_site_id+"\"}}";
		}
		return "["+str+"]";
	}
	
	/**
     * 根据站点ID得到所有允许共享给该站点信息的站点集合    
     * @param String t_site_id
     * @param int shared_type 类型　0共享　1接收
     * @return List
     * */
	public static List<SiteBean> getAllowSiteByType(String t_site_id,int shared_type)
	{
		List<SiteBean> site_list = new ArrayList<SiteBean>();
		Set<SiteBean> site_set = new HashSet<SiteBean>();
		if(cs_list != null && cs_list.size() > 0)
		{
			for(int i=0;i<cs_list.size();i++)
			{	//是共享类型的
				if(cs_list.get(i).getShared_type() == shared_type)
				{//如果是全部共享或共享站点等于 本站点，插入
					if(cs_list.get(i).getRange_type() == 1 || t_site_id.equals(cs_list.get(i).getT_site_id()))
						site_set.add(SiteManager.getSiteBeanBySiteID(cs_list.get(i).getS_site_id()));				
				}
			}
			site_list.addAll(site_set);
		}
		return site_list;
	}
	
	/**
     * 根据站点ID,得到它能提供给目标站点的栏目树
     * @param String s_site_id 提供共享目标的站点
     * @param String t_site_id 目标站点,需要获取栏目的站点　
     * @return String
     * */
	public static String getReceiveCategoryTreeBySiteID(String s_site_id,String t_site_id)
	{
		String json_str = "";
		List<CategoryBean> cat_list = new ArrayList<CategoryBean>();
		getSharedCategoryListBySiteID(cat_list,s_site_id,t_site_id,1);
		json_str = "["+CategoryTreeUtil.getCategoryTreeJsonStrHandl(cat_list)+"]";
		return json_str;
	}
	
	/**
     * 根据站点ID,得到它能提供给目标站点的栏目树
     * @param String s_site_id 提供共享目标的站点
     * @param String t_site_id 目标站点,需要获取栏目的站点　
     * @return String
     * */
	public static String getSharedCategoryTreeBySiteID(String s_site_id,String t_site_id)
	{
		String json_str = "";
		List<CategoryBean> cat_list = new ArrayList<CategoryBean>();
		getSharedCategoryListBySiteID(cat_list,s_site_id,t_site_id,0);
		json_str = "["+CategoryTreeUtil.getCategoryTreeJsonStrHandl(cat_list)+"]";
		return json_str;
	}
	
	/**
     * 根据站点ID,得到它能提供给目标站点的栏目集合
     * @param List<CategoryBean> cat_list
     * @param String s_site_id 提供共享目标的站点
     * @param String t_site_id 目标站点
     * @param int shared_type 类型　0共享　1接收
     * @return Set
     * */
	public static void getSharedCategoryListBySiteID(List<CategoryBean> cat_list,String s_site_id,String t_site_id,int shared_type)
	{		
		Set<CategoryBean> cat_set = new HashSet<CategoryBean>();
		if(cs_list != null && cs_list.size() > 0)
		{
			for(int i=0;i<cs_list.size();i++)
			{//是共享类型的,and 共享站点等于提供站点
				if(cs_list.get(i).getShared_type() == shared_type && s_site_id.equals(cs_list.get(i).getS_site_id()))
				{//如果是全部共享或共享站点等于目标站点，插入
					if(cs_list.get(i).getRange_type() == 1 || t_site_id.equals(cs_list.get(i).getT_site_id()))
						cat_set.add(CategoryManager.getCategoryBean(cs_list.get(i).getCat_id()));
				}
			}
			cat_list.addAll(cat_set);			
		}		
	}
	
	/**
     * 插入目录共享信息
     * @param CategorySharedBean csb
     * @return boolean
     * */
	public static boolean insertCategoryShared(CategorySharedBean csb)
	{		
		//判断是否是限制共享
		if(csb.getRange_type() == 0)
		{	//所共享站点ID不为空			
			if(!"".equals(csb.getT_site_id().trim()))
			{
				try{
					String[] tempA = csb.getT_site_id().split(",");
					for(int i=0;i<tempA.length;i++)
					{
						CategorySharedBean new_csb = csb;
						new_csb.setT_site_id(tempA[i]);
						CategorySharedDAO.insertCategoryShared(new_csb);
					}				
				}catch(Exception e)
				{
					e.printStackTrace();
					return false;
				}
			}
			reloadCategoryShared();
			return true;
		}else
		{
			csb.setT_site_id("");
			if(CategorySharedDAO.insertCategoryShared(csb))
			{
				reloadCategoryShared();
				return true;
			}else
				return false;
		}
	}
	
	/**
     * 修改目录共享信息
     * @param CategorySharedBean csb
     * @return boolean
     * */
	public static boolean updateCategoryShared(CategorySharedBean csb)
	{
		//先删除数据
		CategorySharedDAO.deleteCategoryShared(csb.getS_site_id(), csb.getCat_id(), csb.getShared_type());
		return insertCategoryShared(csb);
	}
	
	/**
     * 根据栏目ID删除信息      
     * @param String cat_ids
     * @return boolean
     * */
	public static boolean deleteCategorySharedByCatID(String cat_ids,String site_id)
	{
		if(CategorySharedDAO.deleteCategorySharedByCatID(cat_ids,site_id))
		{
			reloadCategoryShared();
			return true;
		}else
			return false;
	}
	
	public static void main(String[] args)
	{
		System.out.println(getAllowSharedSiteJSONStr("11111ddd"));
		//System.out.println(getAllowReceiveSite("11111ddd"));
	}
}
