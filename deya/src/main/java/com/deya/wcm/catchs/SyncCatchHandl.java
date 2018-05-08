package com.deya.wcm.catchs;

import java.rmi.RemoteException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.control.SiteServerBean;
import com.deya.wcm.rmi.ISyncCatchRmi;
import com.deya.wcm.rmi.conf.RmiServerConfBean;
import com.deya.wcm.rmi.conf.RmiServerConfManager;
import com.deya.wcm.services.control.server.SiteServerManager;

public class SyncCatchHandl {

    /*
	private static String port = JconfigUtilContainer.bashConfig().getProperty("port", "", "rmi_config");

	public static void reladCatchForRMI(String class_name)
	{		
		System.out.println("SyncCatchHandl-----------"+class_name);
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false)
		{
			//如果不是多台服务器，直接执行了
			ISyncCatch ic;
			try {
				ic = (ISyncCatch) Class.forName(class_name).newInstance();
				ic.reloadCatch();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		else
		{
			//得到发布服务器列表
			List<SiteServerBean> sl = SiteServerManager.getServerListBySType(2);
			if(sl != null && sl.size() > 0)
			{
				for(SiteServerBean ssb : sl)
				{
					Context namingContext;
					String ip = ssb.getServer_ip();
					try {
						namingContext = new InitialContext();
						String path = "rmi://"+ip+":"+port+"/catchRmi";	
						ISyncCatchRmi iscr = (ISyncCatchRmi)namingContext.lookup(path);
						iscr.reloadCateh(class_name);					
					} catch (NamingException e) {
						// TODO Auto-generated catch block
						System.out.println("rmi "+ip+" error");
						e.printStackTrace();
						
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						System.out.println("rmi "+ip+" error");
						e.printStackTrace();
					}
					
				}
			}
		}
	}
	*/

    //private static String ip = JconfigUtilContainer.bashConfig().getProperty("ip", "", "rmi_config");
    //private static String port = JconfigUtilContainer.bashConfig().getProperty("port", "", "rmi_config");

    public static void reladCatchForRMI(String class_name)
    {
        if(SiteServerManager.IS_MUTILPUBLISHSERVER == false)
        {
            //如果不是多台服务器，直接执行了
            ISyncCatch ic;
            try {
                ic = (ISyncCatch) Class.forName(class_name).newInstance();
                ic.reloadCatch();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            List<SiteServerBean> rsc = SiteServerManager.getServerListBySType(2);
            if ((rsc != null) && (rsc.size() > 0)) {
                for (SiteServerBean rscb : rsc) {
                    String rscb_ip = rscb.getServer_ip();
                    String rscb_port = rscb.getServer_port();
                    try {
                        Context namingContext = new InitialContext();
                        String path = "rmi://" + rscb_ip + ":" + rscb_port + "/catchRmi";
                        System.out.println("***********RMIpath***************" + path);
                        ISyncCatchRmi iscr = (ISyncCatchRmi) namingContext.lookup(path);
                        iscr.reloadCateh(class_name);
                        //System.out.println(path + "------------" + class_name);
                    } catch (NamingException e) {
                        //System.out.println("rmi " + rscb_ip + " error");
                        e.printStackTrace();
                    } catch (RemoteException e) {
                        //System.out.println("rmi " + rscb_ip + " error");
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
