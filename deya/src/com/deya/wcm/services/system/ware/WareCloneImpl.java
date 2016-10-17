package com.deya.wcm.services.system.ware;

import java.io.IOException;
import java.util.List;

import com.deya.wcm.bean.system.ware.WareBean;
import com.deya.wcm.bean.system.ware.WareCategoryBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.system.ware.WareCategoryDAO;
import com.deya.wcm.dao.system.ware.WareDAO;
import com.deya.wcm.services.control.site.ICloneSite;

public class WareCloneImpl implements ICloneSite{
	public boolean cloneSite(String site_id,String s_site_id)
	{
		try{
			wareCategoryClone(site_id,s_site_id);
			wareClone(site_id,s_site_id);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}		
	}
	
	/**
	 * 克隆标签分类	 
	 * @param String site_id 新站点ID
	 * @param String s_site_id　克隆的目标站点ID
	 * @return list
	 */
	public static boolean wareCategoryClone(String site_id,String s_site_id)
	{
		List<WareCategoryBean> l =  WareCategoryDAO.getWareCataListBySiteID(s_site_id);
		if(l != null && l.size() > 0)
		{
			for(WareCategoryBean wcb : l)
			{
				wcb.setSite_id(site_id);
				WareCategoryDAO.cloneWareCate(wcb);
			}
			WareCategoryManager.reloadMap();
		}
		return true;
	}
	
	/**
	 * 克隆标签	 
	 * @param String site_id 新站点ID
	 * @param String s_site_id　克隆的目标站点ID
	 * @return list
	 */
	public static boolean wareClone(String site_id,String s_site_id)
	{
		List<WareBean> l =  WareDAO.getWareListBySiteID(s_site_id);
		if(l != null && l.size() > 0)
		{
			for(WareBean wb : l)
			{
				wb.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.WARE_TABLE_NAME)+"");
				wb.setSite_id(site_id);
				if(WareDAO.cloneWare(wb))
				{
					if(wb.getWare_type() == 0 && !"".equals(wb.getWare_content().trim()))
						try {
							WareManager.createWarePage(wb);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}					
				}
			}
			WareManager.reloadMap();
		}
		return true;
	}
	
	public static void main(String args[])
	{
		
	}
}
