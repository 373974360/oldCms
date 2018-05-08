package com.deya.wcm.dao.org;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.dao.org.dept.DeptDAODBImpl;
import com.deya.wcm.dao.org.dept.DeptDAORMIImpl;
import com.deya.wcm.dao.org.dept.IDeptDAO;
import com.deya.wcm.dao.org.user.IUserDAO;
import com.deya.wcm.dao.org.user.UserDAODBImpl;
import com.deya.wcm.dao.org.user.UserDAORMIImpl;

public class OrgDAOFactory {
	
	private static String org_save_type = JconfigUtilContainer.bashConfig().getProperty("type", "db", "org_save_type");
			
	public static IDeptDAO getDeptDao()
	{
		if("db".equals(org_save_type))
		{
			return new DeptDAODBImpl();
		}else
			return new DeptDAORMIImpl();
	}
	
	public static IUserDAO getUserDao()
	{
		if("db".equals(org_save_type))
		{
			return new UserDAODBImpl();
		}else
			return new UserDAORMIImpl();
	}
	
	public static void main(String[] args)
	{
		System.out.println("org_save_type-------"+org_save_type);
	}
}
