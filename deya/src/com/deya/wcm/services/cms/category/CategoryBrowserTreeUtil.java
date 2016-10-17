package com.deya.wcm.services.cms.category;

import java.util.List;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.cms.category.CategoryBean;

public class CategoryBrowserTreeUtil {
	/**
     * 根据目录ID得目录树节字符串
     * @param int class_id
     * @param String site_id
     * @return String
     * */
	public static String getBroCategoryTreeByCategoryID(int cat_id,String site_id)
	{
		CategoryBean cbg = CategoryManager.getCategoryBeanCatID(cat_id,site_id);
		//对象不为空且，归档状态为0
		if(cbg != null && cbg.getIs_archive() == 0)
		{
			return getBroCategoryTreeByCategory(cbg);
		}else
			return "[]";			
	}
	
	/**
     * 根据目录对象得目录树节字符串
     * @param CategoryBean cbg
     * @return String
     * */
	public static String getBroCategoryTreeByCategory(CategoryBean cbg)
	{
		String json_str = "";
		try{
			
			List<CategoryBean> child_list = CategoryManager.getChildCategoryList(cbg.getCat_id(),cbg.getSite_id());	
			json_str = "[{\"id\":"+cbg.getCat_id()+",\"text\":\""+FormatUtil.formatJsonStr(cbg.getCat_cname())+"\",\"attributes\":{\"url\":\""
			+cbg.getJump_url()+"\",\"is_mutilpage\":\""+cbg.getIs_mutilpage()+"\"}";
			if(child_list != null && child_list.size() > 0)
			{				
				json_str += ",\"children\":["+getBroCategoryTreeJsonStrHandl(child_list)+"]";
			}
			json_str += "}]";
		}catch(Exception e)
		{
			e.printStackTrace();			
		}
		return json_str;
	}
	
	/**
     * 根据目录列表得目录树节字符串
     * @param CategoryBean cbg
     * @return String
     * */
	public static String getBroCategoryTreeJsonStrHandl(List<CategoryBean> child_list)
	{
		String json_str = "";
		if(child_list != null && child_list.size() > 0)
		{			
			for(int i=0;i<child_list.size();i++)
			{	
				if(child_list.get(i).getIs_show_tree() == 1)
				{
					json_str += ",{";
					List<CategoryBean> child_m_list = CategoryManager.getChildCategoryList(child_list.get(i).getCat_id(),child_list.get(i).getSite_id());
					if(child_m_list != null && child_m_list.size() > 0)
					{
						json_str += "\"id\":"+child_list.get(i).getCat_id()+",\"text\":\""+FormatUtil.formatJsonStr(child_list.get(i).getCat_cname())+"\",\"attributes\":{\"url\":\""
				+child_list.get(i).getJump_url()+"\",\"is_mutilpage\":\""+child_list.get(i).getIs_mutilpage()+"\"}";
						json_str += ",\"state\":'closed'";
						json_str += ",\"children\":["+getBroCategoryTreeJsonStrHandl(child_m_list)+"]";
					}else
						json_str += "\"id\":"+child_list.get(i).getCat_id()+",\"text\":\""+FormatUtil.formatJsonStr(child_list.get(i).getCat_cname())+"\",\"attributes\":{\"url\":\""
				+child_list.get(i).getJump_url()+"\",\"is_mutilpage\":\""+child_list.get(i).getIs_mutilpage()+"\"}";
					json_str += "}";
				}
			}
			if(json_str != null && !"".equals(json_str))
				json_str = json_str.substring(1);
		}
		return json_str;
	}
	
}
