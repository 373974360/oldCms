package com.deya.wcm.services.page;

import java.util.List;

import com.deya.wcm.bean.page.PageBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.page.PageDAO;
import com.deya.wcm.services.control.site.ICloneSite;

public class PageCloneImpl implements ICloneSite{
	public boolean cloneSite(String site_id,String s_site_id)
	{
		try{
			List<PageBean> l = PageDAO.getPageListBySiteID(s_site_id);
			if(l != null && l.size() > 0)
			{
				for(PageBean pb : l)
				{
					pb.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.PAGE_TABLE_NAME));
					pb.setSite_id(site_id);
					if(PageDAO.clonePage(pb))
					{
						PageManager.createHtmlPage(pb);
					}
				}
				PageManager.reloadPage();
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
		
	}
}
