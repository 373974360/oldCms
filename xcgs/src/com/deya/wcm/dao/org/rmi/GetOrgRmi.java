package com.deya.wcm.dao.org.rmi;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.rmi.IOrgRmi;

public class GetOrgRmi {
	private static String path = "rmi://"+JconfigUtilContainer.bashConfig().getProperty("rmi_ip", "", "org_save_type")+":"+JconfigUtilContainer.bashConfig().getProperty("rmi_port", "", "org_save_type")+"/orgRmi";
	
	public static IOrgRmi getorgRmi()
	{
		try{						
			Context namingContext=new InitialContext();
			return (IOrgRmi)namingContext.lookup(path);			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
