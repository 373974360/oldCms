package com.deya.wcm.template.velocity.impl;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.services.appeal.model.ModelManager;
import com.deya.wcm.services.control.site.SiteAppRele;
import com.deya.wcm.template.velocity.VelocityContextAbstract;

public class VelocityCommentContextImp extends VelocityContextAbstract{
	public VelocityCommentContextImp(HttpServletRequest request)
	{
		super(request);
	}
	
	/**
     * 根据app_id,site_id,业务ID，得到模板ID
     * @param String app_id
     * @param String site_id
     * @param String model_id 业务ID
     * @param temp_type 模板类型　form,list,content
     * @return boolean
     * */
	public void setTemplateID(String app_ids,String site_ids,String model_id)
	{
		if(model_id != null && !"".equals(model_id))
		{	
			String app_id = "appeal";
			try{				
				if("appeal".equals(app_ids))	
				{					
					site_id = SiteAppRele.getSiteIDByAppID(app_id);					
					template_id = ModelManager.getModelBean(Integer.parseInt(model_id)).getTemplate_comment()+"";					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("setTemplateID -- template_id is null app_id:"+app_id+" site_id:"+site_id+" id:"+model_id);
			}			
		}
	}
	
	public static void main(String[] args) {
		/*
		VelocityCommentContextImp vc = new VelocityCommentContextImp();
		vc.setTemplateID("appeal", "", "30");
		System.out.println("111111111111"+vc.getTemplateFilePath());
		*/		
	}
}
