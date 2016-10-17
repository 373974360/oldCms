package com.deya.wcm.services.cms.category;

import java.util.ArrayList;
import java.util.List;

import com.deya.wcm.bean.cms.category.CateCurPositionBean;
import com.deya.wcm.bean.cms.category.CategoryBean;

/**
 *  目录管理工具类.
 * <p>Title: CicroDate</p>
 * <p>Description: 目录管理工具类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class CategoryUtil {
	private final static String ZWGK_FOLDER = "/gk";
	
	public static String[] getFoldeArrByCatIDS(String old_cat_ids,String site_id,String app_id)
	{
		String[] tempA = old_cat_ids.split(",");		
		String[] arr = new String[tempA.length];
		for(int i=0;i<tempA.length;i++)
		{				
			arr[i] = CategoryUtil.getFoldePathByCategoryID(Integer.parseInt(tempA[i]),app_id, site_id);
			
		}	
		return arr;
	}
	
	/**
     * 根据cat_ID得到该级目录的保存路径(路径是由父节点的英文名称组合起来的)
     * @param String cat_ids
     * @param String app_id
     * @return String
     * */
	public static String getFoldePathByCategoryID(int cat_id,String app_id,String site_id)
	{//政务公开的目录地址都放在gk下面
		String path = "/";
		if("zwgk".equals(app_id))
				path = ZWGK_FOLDER+path;
		CategoryBean cb = CategoryManager.getCategoryBeanCatID(cat_id,site_id);
	
		if(cb != null)
		{
			String position = cb.getCat_position();
			
			String[] tempA = position.split("\\$");
			for(int i=0;i<tempA.length;i++)
			{
				if(tempA[i] != null && !"".equals(tempA[i]) && !"0".equals(tempA[i]))
				{
					CategoryBean temp_cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(tempA[i]),site_id);
					if(temp_cb != null)
					{
						path += temp_cb.getCat_ename()+"/";
					}
				}
			}
		}
		return path;
	}	
	
	/**
	 * 根据栏目ID，页面类型返回栏目的当前位置列表
	 * @param String cat_id
	 * @param String page_type
	 * @return List<CateogryBean>
	 */
	public static List<CateCurPositionBean> getCategoryPosition(String cat_id,String site_id,String page_type)
	{
		List<CateCurPositionBean> ccpb_list = new ArrayList<CateCurPositionBean>();
		
		CateCurPositionBean first_b = new CateCurPositionBean();
		first_b.setCat_cname("首页");
		first_b.setCat_id(0);
		first_b.setUrl("/");
		ccpb_list.add(first_b);
		if(cat_id != null && !"0".equals(cat_id))
		{
			CategoryBean cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(cat_id), site_id);			
			if(cb != null)
			{				
				String[] tempA = cb.getCat_position().split("\\$");
				
				if(tempA != null && tempA.length > 0)
				{
					for(int i=2;i<tempA.length;i++)
					{
						if(tempA[i] != null && !"".equals(tempA[i]))
						{
							CategoryBean cb2 = CategoryManager.getCategoryBeanCatID(Integer.parseInt(tempA[i]), site_id);						
							if(cb2 != null)
							{
								CateCurPositionBean ccpb = new CateCurPositionBean();
								ccpb.setJump_url(cb2.getJump_url());
								ccpb.setCat_cname(cb2.getCat_cname());
								ccpb.setCat_id(cb2.getCat_id());
								if(cb2.getJump_url() != null && !"".equals(cb2.getJump_url()))
								{
									ccpb.setUrl(cb2.getJump_url());									
								}
								else
								{									
									if("index".equals(page_type) || CategoryManager.isHasChildNode(Integer.parseInt(tempA[i]), site_id))
									{
										//ccpb.setUrl(getFoldePathByCategoryID(cb2.getCat_id(),cb2.getApp_id(),cb2.getSite_id())+"index.htm");
										ccpb.setUrl("/info/iIndex.jsp?cat_id="+cb2.getCat_id());
									}
									else									
										ccpb.setUrl("/info/iList.jsp?cat_id="+cb2.getCat_id());
								}
								ccpb_list.add(ccpb);
							}
						}
					}
				}
			}
		}
		return ccpb_list;
	}
	
	/**
     * 根据cat_ID返回目录名称
     * @param int cat_id
     * @param String site_id
     * @return String
     * */
	public static String getCategoryCName(int cat_id,String site_id)
	{
		CategoryBean cgb = CategoryManager.getCategoryBeanCatID(cat_id,site_id);
		if(cgb != null)
		{
			return cgb.getCat_cname();
		}else
			return "";
	}
	
	/**
     * 根据cat_id，site_id和user_id得到它能管理的目录节点ID
     * @param int cat_id
     * @param String site_id
     * @param int user_id
     * @return CategoryBean
     * */
	public static String getCategoryIDSByUser(int cat_id,String site_id,int user_id)
	{		
		//首先判断该人员是否是站点管理员或该级目录的管理权限,如果是，给出所有的节点	
		if(CategoryManager.haveCategoryManagementAuthority(cat_id,site_id,user_id)){
			return CategoryManager.getAllChildCategoryIDS(cat_id, site_id);
		}else
		{
			List<CategoryBean> l = CategoryManager.getChildCategoryList(cat_id,site_id);
			
			return getCategoryIDSByUserHandl(l,site_id,user_id);
		}
	}
	
	public static String getCategoryIDSByUserHandl(List<CategoryBean> l,String site_id,int user_id)
	{
		String cat_ids = "";
		if(l != null && l.size() > 0)
		{//循环列表，判断该人员是否有节点管理权限
			for(CategoryBean cb : l)
			{
				if(cb.getIs_archive() == 0 && CategoryReleManager.isCategoryManagerByUser(user_id,site_id,cb.getCat_id()))
				{					
					String all_child_ids = CategoryManager.getAllChildCategoryIDS(cb.getCat_id(),site_id);
					//判断是否有子节点，没有就记录本身
					if(all_child_ids != null && !"".equals(all_child_ids))
						cat_ids += ","+	all_child_ids;
					else
						cat_ids += ","+	cb.getCat_id();
				}else
				{
					//些节点没有权限，再判断它的子节点
					List<CategoryBean> child_list = CategoryManager.getChildCategoryList(cb.getCat_id(),site_id);
					String child_ids = getCategoryIDSByUserHandl(child_list,site_id,user_id);
					if(child_ids != null && !"".equals(child_ids))
						cat_ids += ","+	child_ids;
				}
			}
			if(cat_ids != null && !"".equals(cat_ids))
				cat_ids = cat_ids.substring(1);
		}
		return cat_ids;
	}
	
	public static void main(String[] args)
	{
		System.out.println(getCategoryIDSByUser(0,"HIWCMdemo",1));
	}
}
