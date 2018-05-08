package com.deya.wcm.template.velocity.impl;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.zwgk.ser.SerCategoryBean;
import com.deya.wcm.services.control.site.SiteAppRele;
import com.deya.wcm.services.zwgk.ser.SerCategoryManager;
import com.deya.wcm.template.velocity.VelocityContextAbstract;

public class VelocitySerContextImp extends VelocityContextAbstract{
	public VelocitySerContextImp(HttpServletRequest request)
	{
		super(request);
	}
	
	public VelocitySerContextImp()
	{
		
	}
	
	public void vcontextPut(String key,Object o){
		vcontext.put(key, o);	
	}
	
	public void setSerTopicTemplateID(String ser_id,String site_id,String temp_type)
	{
		super.site_id = site_id;
		if(ser_id != null && !"".equals(ser_id))
		{
			vcontext.put("ser_id", ser_id);
			//System.out.println("setSerTopicTemplateID--------"+vcontext.get("ser_id"));
			try{
				SerCategoryBean scb = SerCategoryManager.getRootSerCategoryBean(Integer.parseInt(ser_id));
				if("index".equals(temp_type))
				{
					template_id = scb.getTemplate_index()+"";
				}
				if("list".equals(temp_type))
				{
					template_id = scb.getTemplate_list()+"";
				}
				if("content".equals(temp_type))
				{
					template_id = scb.getTemplate_content()+"";
				}	
			}catch(Exception e)
			{
				e.printStackTrace();
				//System.out.println("setSerTopicTemplateID -- SerBean is null id:"+ser_id);
			}
		}
	}
	
	public void setSerInfoListTemplateID(String ser_id,String site_id)
	{
		super.site_id = site_id;
		if(ser_id != null && !"".equals(ser_id))
		{
			vcontext.put("ser_id", ser_id);
			template_id = SerCategoryManager.getSerTemplateID("list")+"";
		}
	}
	
	public static void main(String args[])
	{
		VelocitySerContextImp s = new VelocitySerContextImp();
		s.template_id = "1122";
		s.site_id = "11111ddd";		
		System.out.println(s.parseTemplate("#set($serObject=$SerData.getSerObject(\"25\"))<div>$serObject.cat_name</div>222"));
	}
}
