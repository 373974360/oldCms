package com.deya.wcm.template.velocity.impl;

import com.deya.wcm.bean.page.PageBean;
import com.deya.wcm.template.velocity.VelocityContextAbstract;

public class VelocityPageContextImp extends VelocityContextAbstract{
	public String getHtmlContent(PageBean pb)
	{
		site_id = pb.getSite_id();
		template_id = pb.getTemplate_id()+"";		
		return super.parseTemplate();
	}	
}
