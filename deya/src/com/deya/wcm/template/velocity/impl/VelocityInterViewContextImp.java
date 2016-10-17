package com.deya.wcm.template.velocity.impl;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.services.control.site.SiteAppRele;
import com.deya.wcm.services.interview.SubjectCategoryServices;
import com.deya.wcm.template.velocity.VelocityContextAbstract;

public class VelocityInterViewContextImp extends VelocityContextAbstract{
	public VelocityInterViewContextImp(HttpServletRequest request)
	{
		super(request);
	}	
	
	public void vcontextPut(String key,Object o){
		vcontext.put(key, o);	
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
		String app_id = "interview";
		if(site_id == null || "".equals(site_id))
			site_id = vcontext.get("site_id").toString();
		if(cat_id != null && !"".equals(cat_id))
		{				
			vcontext.put("cat_id", cat_id);				
			try{
			//System.out.println("setTemplateID------cat_id------"+cat_id+"  ---temp_type------ "+temp_type);
				if("list".equals(temp_type))
					template_id = SubjectCategoryServices.getSubjectCategoryBean(cat_id).getM_hlist_path();
				if("live".equals(temp_type))
					template_id = SubjectCategoryServices.getSubjectCategoryBean(cat_id).getM_on_path();
				//System.out.println("setTemplateID------template_id------ "+template_id);
				if("forecastList".equals(temp_type))
					template_id = SubjectCategoryServices.getSubjectCategoryBean(cat_id).getM_forecast_path();
				if("historyContent".equals(temp_type))
					template_id = SubjectCategoryServices.getSubjectCategoryBean(cat_id).getM_h_path();				
				if("infoList".equals(temp_type))
					template_id = SubjectCategoryServices.getSubjectCategoryBean(cat_id).getM_rlist_path();
				if("infoContent".equals(temp_type))
					template_id = SubjectCategoryServices.getSubjectCategoryBean(cat_id).getM_rcontent_list();
				
			}catch(Exception e)
			{
				e.printStackTrace();
				//System.out.println("setTemplateID -- template_id is null app_id:"+app_id+" site_id:"+site_id+" id:"+cat_id);
			}			
		}else
		{
			
			template_id = SubjectCategoryServices.getInterViewTemplate(temp_type,site_id);
			//System.out.println("VelocityInterViewContextImp----"+site_id+"-----"+template_id);
		}
	}
		
}
