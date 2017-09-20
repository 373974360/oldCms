package com.deya.wcm.template.velocity.impl;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.zwgk.ysqgk.YsqgkConfigBean;
import com.deya.wcm.services.zwgk.ysqgk.YsqgkConfigManager;
import com.deya.wcm.template.velocity.VelocityContextAbstract;

public class VelocityYSQGKContextImp extends VelocityContextAbstract {
	public VelocityYSQGKContextImp(HttpServletRequest request)
	{
		super(request);
	}
	
	public void setTemplateID(String site_ids,String template_ids)
	{
		site_id = site_ids;
		template_id = template_ids;	
		vcontext.put("site_id", site_ids);
	}
	
	public void setYSQGKTemplateID(String site_ids,String temp_type)
	{
		site_id = site_ids;
		vcontext.put("site_id", site_ids);
		YsqgkConfigBean ycb = YsqgkConfigManager.getYsqgkConfigBeanBySiteId(site_ids);
		if(ycb != null)
		{
			if("form".equals(temp_type))
				template_id = ycb.getTemplate_form()+"";
			if("list".equals(temp_type))
				template_id = ycb.getTemplate_list()+"";
			if("content".equals(temp_type))
				template_id = ycb.getTemplate_content()+"";
			if("over".equals(temp_type))
				template_id = ycb.getTemplate_over()+"";
			if("print".equals(temp_type))
				template_id = ycb.getTemplate_print()+"";
			if("query".equals(temp_type))
				template_id = ycb.getTemplate_query()+"";
		}
	}
}
