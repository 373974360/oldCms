package com.deya.wcm.template.velocity.impl;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.appCom.guestbook.GuestBookCategory;
import com.deya.wcm.bean.appCom.guestbook.GuestBookSub;
import com.deya.wcm.services.appCom.guestbook.GuestBookCategoryManager;
import com.deya.wcm.services.appCom.guestbook.GuestBookSubManager;
import com.deya.wcm.template.velocity.VelocityContextAbstract;

public class VelocityGuestBookContextImp extends VelocityContextAbstract{
	public VelocityGuestBookContextImp(HttpServletRequest request)
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
	
	/**
     * 根据gbs_id,temp_type,业务ID，得到模板ID
     * @param String app_id
     * @param String site_id    
     * @return boolean
     * */
	public void setGuestbookTemplateID(String gbs_id,String temp_type)
	{
		if(gbs_id != null && !"".equals(gbs_id))
		{
			vcontext.put("sub_id", gbs_id);
			try{
				GuestBookSub gbs = GuestBookSubManager.getGuestBookSub(Integer.parseInt(gbs_id));
				if("index".equals(temp_type))
				{
					GuestBookCategory cat = GuestBookCategoryManager.getGuestBookCategoryBean(gbs.getCat_id());
					if(cat != null)
						template_id = cat.getTemplate_index()+"";
				}
				if("list".equals(temp_type))
					template_id = gbs.getTemplate_list()+"";
				if("form".equals(temp_type))
					template_id = gbs.getTemplate_form()+"";
				if("content".equals(temp_type))
					template_id = gbs.getTemplate_content()+"";
			}
			catch(Exception e)
			{
				//System.out.println("setGuestbookTemplateID----gbs_id is null");
				e.printStackTrace();				
			}
		}else
			System.out.println("setGuestbookTemplateID----gbs_id is null");
	}
	
	/**
     * 根据gbs_id,temp_type,业务ID，得到模板ID
     * @param String app_id
     * @param String site_id    
     * @return boolean
     * */
	public void setGuestbookIndexTemplateID(String cat_id,String site_id)
	{
		if(cat_id != null && !"".equals(cat_id))
		{
			vcontext.put("gbcat_id", cat_id);
			try{
				GuestBookCategory cat = GuestBookCategoryManager.getGuestBookCategoryBean(Integer.parseInt(cat_id));
				if(cat != null)
					template_id = cat.getTemplate_index()+"";				
			}
			catch(Exception e)
			{
				//System.out.println("setGuestbookIndexTemplateID----gbs_id is null");
				e.printStackTrace();				
			}
		}else
			System.out.println("setGuestbookIndexTemplateID----gbs_id is null");
	}
}
