package com.deya.wcm.template.velocity.impl;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.minlu.MingLuBean;
import com.deya.wcm.services.minlu.MingLuManager;
import com.deya.wcm.template.velocity.VelocityContextAbstract;

public class VelocityMingLuContextImp extends VelocityContextAbstract{
	public VelocityMingLuContextImp(HttpServletRequest request)
	{
		super(request);
	}
	
	public void vcontextPut(String key,Object o){
		vcontext.put(key, o);	
	}	
	
	/**
     * 手动设置模板ID     
     * @param String site_ids
     * @param String template_ids
     * @return boolean
     * */
	public void setTemplateID(String site_ids,String template_ids)
	{
		site_id = site_ids;
		template_id = template_ids;
	}
	
	public void setMLTemplateID(String t_site_id,String temp_type)
	{
		site_id = t_site_id;
		vcontext.put("site_id", site_id);
		MingLuBean mlb = MingLuManager.getMingLuBean(site_id);
		if(mlb != null)
		{
			if("index".equals(temp_type))
				template_id = mlb.getTemplate_index()+"";
			if("list".equals(temp_type))
				template_id = mlb.getTemplate_list()+"";
			if("content".equals(temp_type))
				template_id = mlb.getTemplate_content()+"";
			if("infolist".equals(temp_type))
				template_id = mlb.getReinfo_temp_list()+"";
			if("infocontent".equals(temp_type))
				template_id = mlb.getReinfo_temp_content()+"";
			if("piclist".equals(temp_type))
				template_id = mlb.getReinfo_temp_pic_list()+"";
			if("piccontent".equals(temp_type))
				template_id = mlb.getReinfo_temp_pic_content()+"";
		}
		else
		{
			System.out.println("setMLTemplateID -- MingLuBean is null id:"+t_site_id);			
		}
	}
}
