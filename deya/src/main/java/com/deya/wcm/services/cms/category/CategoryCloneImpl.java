package com.deya.wcm.services.cms.category;

import java.util.List;

import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.category.CategoryModel;
import com.deya.wcm.dao.cms.category.CategoryDAO;
import com.deya.wcm.dao.cms.category.CategoryModelDAO;
import com.deya.wcm.services.control.site.ICloneSite;

public class CategoryCloneImpl implements ICloneSite{
	public boolean cloneSite(String site_id,String s_site_id)
	{
		try{
			List<CategoryBean> l = CategoryDAO.getCategoryListBySiteID(s_site_id);
			if(l != null && l.size() > 0)
			{
				for(CategoryBean cgb : l)
				{
					//List<CategoryModel> cmb_list = CategoryModelManager.getCategoryReleModelList(cgb.getCat_id()+"",s_site_id);
					cgb.setSite_id(site_id);
					cgb.setHj_sql("");
					CategoryDAO.cloneCategory(cgb);	
					cloneCategoryModel(CategoryModelManager.getCategoryReleModelList(cgb.getCat_id()+"",s_site_id),site_id);
				}
				CategoryManager.reloadCategory();
				CategoryModelManager.reloadCategoryModel();
			}
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}		
	}	
	
	public static boolean cloneCategoryModel(List<CategoryModel> cmb_list,String site_id)
	{
		try{
			if(cmb_list != null && cmb_list.size() > 0)
			{
				for(CategoryModel cmb : cmb_list)
				{					
					cmb.setSite_id(site_id);
				}
				CategoryModelDAO.insertCategoryModel(cmb_list);
			}
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args)
	{
		
	}
}
