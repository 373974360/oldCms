package com.deya.wcm.services.system.config;

import java.util.List;

import com.deya.wcm.bean.system.config.ConfigBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.system.config.ConfigDAO;
import com.deya.wcm.services.control.site.ICloneSite;

public class ConfigCloneImpl implements ICloneSite{
	public boolean cloneSite(String site_id,String s_site_id)
	{
		try{
			List<ConfigBean> l = ConfigDAO.getSystemConfigListBySiteID(s_site_id);
			if(l != null && l.size() > 0)
			{
				for(ConfigBean cb : l)
				{
					String insert_sql = PublicTableDAO.getIDByTableName(PublicTableDAO.SYS_CONFIG_TABLE_NAME)+",'attachment','"+cb.getKey()+"','"+cb.getValue()+"','"+site_id+"','cms'";
					
					ConfigDAO.addConfig(insert_sql, null);
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args)
	{
		
	}
}
