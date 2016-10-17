package com.deya.wcm.services.org.role;

import java.util.List;

import com.deya.wcm.bean.org.role.RoleBean;
import com.deya.wcm.dao.org.role.RoleDAO;
import com.deya.wcm.dao.org.role.RoleOptDAO;
import com.deya.wcm.services.control.site.ICloneSite;

public class RoleCloneImpl implements ICloneSite{
	public boolean cloneSite(String site_id,String s_site_id)
	{
		try{
			roleClone(site_id,s_site_id);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean roleClone(String site_id,String s_site_id)
	{
		try{
			List<RoleBean> l = RoleDAO.getRoleListBySiteID(s_site_id);
			if(l != null && l.size() > 0)
			{
				for(RoleBean rb : l)
				{
					rb.setSite_id(site_id);					
					String opt_ids = RoleOptManager.getOptIDSByRoleID(rb.getRole_id()+"");					
					RoleDAO.insertRole(rb, null);
					//插入角色与权限关联
					RoleOptDAO.insertOptReleRole(rb.getRole_id()+"",opt_ids,null);
				}
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
		roleClone("11","11111ddd");
	}
}
