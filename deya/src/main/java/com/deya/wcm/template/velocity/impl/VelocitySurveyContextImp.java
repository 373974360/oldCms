package com.deya.wcm.template.velocity.impl;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.services.control.site.SiteAppRele;
import com.deya.wcm.services.survey.SurveyCategoryService;
import com.deya.wcm.template.velocity.VelocityContextAbstract;

public class VelocitySurveyContextImp extends VelocityContextAbstract{
	public VelocitySurveyContextImp(HttpServletRequest request)
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
	public void setTemplateID(String cat_id,String temp_type)
	{
		String app_id = "survey";
		
		if(cat_id != null && !"".equals(cat_id))
		{				
			vcontext.put("cat_id", cat_id);				
			try{
				if("list".equals(temp_type))
					template_id = SurveyCategoryService.getSurveyCategoryBean(cat_id).getTemplate_list_path();
				if("content".equals(temp_type))
					template_id = SurveyCategoryService.getSurveyCategoryBean(cat_id).getTemplate_content_path();
				if("result".equals(temp_type))
					template_id = SurveyCategoryService.getSurveyCategoryBean(cat_id).getTemplate_result_path();
				
			}catch(Exception e)
			{
				e.printStackTrace();
				//System.out.println("setTemplateID -- template_id is null app_id:"+app_id+" site_id:"+site_id+" id:"+cat_id);
			}			
		}else
		{
			template_id = SurveyCategoryService.getSurveyTemplate(temp_type,site_id);
			//System.out.println("VelocitySurveyContextImp----"+site_id+"-----"+template_id);
		}
	}
}
