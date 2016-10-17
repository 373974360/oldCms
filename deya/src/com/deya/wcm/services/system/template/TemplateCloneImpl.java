package com.deya.wcm.services.system.template;

import java.util.List;

import com.deya.wcm.bean.system.template.TemplateBean;
import com.deya.wcm.bean.system.template.TemplateCategoryBean;
import com.deya.wcm.bean.system.template.TemplateEditBean;
import com.deya.wcm.bean.system.template.TemplateVerBean;
import com.deya.wcm.dao.system.template.TemplateCategoryDAO;
import com.deya.wcm.dao.system.template.TemplateDAO;
import com.deya.wcm.dao.system.template.TemplateEditDAO;
import com.deya.wcm.dao.system.template.TemplateVerDAO;
import com.deya.wcm.services.control.site.ICloneSite;
import com.deya.wcm.template.TemplateBase;

public class TemplateCloneImpl implements ICloneSite{
	public boolean cloneSite(String site_id,String s_site_id)
	{
		try{
			templateCategoryClone(site_id,s_site_id);
			templateClone(site_id,s_site_id);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean templateCategoryClone(String site_id,String s_site_id)
	{
		try{
			List<TemplateCategoryBean> l = TemplateCategoryDAO.getTemplateCategoryListBySiteID(s_site_id);
			if(l != null && l.size() > 0)
			{
				for(TemplateCategoryBean tcb : l)
				{
					tcb.setSite_id(site_id);
					TemplateCategoryDAO.cloneTemplateCategory(tcb);
				}
				TemplateCategoryManager.reloadTemplateCategory();
			}
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean templateClone(String site_id,String s_site_id)
	{
		try{
			List<TemplateEditBean> l = TemplateEditDAO.getAllTemplateEditList("",s_site_id,"");
			if(l != null && l.size() > 0)
			{
				for(TemplateEditBean teb : l)
				{
					teb.setSite_id(site_id);
					teb.setT_ver(1);					
					if(TemplateEditDAO.cloneTemplateEdit(teb))
					{
						//添加版本表
						//TemplateVerBean tvb = TemplateEditManager.TEBtoTVB(teb);
						//tvb.setT_status(1);
						//TemplateVerDAO.cloneTemplateVer(tvb);						
						//在正式表中写入发布信息
						TemplateBean tb = new TemplateBean();
						tb.setT_id(teb.getT_id());
						tb.setT_ver(teb.getT_ver());
						tb.setSite_id(site_id);
						tb.setApp_id(teb.getApp_id());
						TemplateDAO.cloneTemplate(tb);
						
						TemplateUtils.setTemplatePath(teb);
						
						//把模板保存成.vm文件
						TemplateBase.saveTemplateFile(teb);
					}
				}
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
		System.out.println(TemplateUtils.getTemplatePath("190_HIWCM9999"));
	}
}
