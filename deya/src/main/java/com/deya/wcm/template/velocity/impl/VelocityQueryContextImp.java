package com.deya.wcm.template.velocity.impl;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.services.appeal.model.ModelManager;
import com.deya.wcm.services.control.site.SiteAppRele;
import com.deya.wcm.services.query.QueryConfManager;
import com.deya.wcm.template.velocity.VelocityContextAbstract;

public class VelocityQueryContextImp extends VelocityContextAbstract{
	
	public VelocityQueryContextImp(HttpServletRequest request)
	{
		super(request);
	}
	public void vcontextPut(String key,Object o){
		vcontext.put(key, o);	
	}
	
	/**
     * 设置业务属性
     * @param String conf_id 业务ID
     * @param temp_type 模板类型　list,content
     * @return boolean
     * */

	public void setTemplatID(String conf_id,String temp_type)
	{
		if(conf_id != null && !"".equals(conf_id))
		{
			vcontext.put("conf_id", conf_id);			
			try{
				if("list".equals(temp_type))
					template_id = QueryConfManager.getQueryConfBean(Integer.parseInt(conf_id)).getT_list_id() +"";
				if("content".equals(temp_type))
					template_id = QueryConfManager.getQueryConfBean(Integer.parseInt(conf_id)).getT_content_id()+"";
			}catch(Exception e)
			{
				e.printStackTrace();
				//System.out.println("setTemplatID -- ConfBean is null id:"+conf_id);
			}			
		}
		/*else
		{
			template_id = QueryConfManager.getModelTemplate(temp_type);
		}*/
	}
}
