package com.deya.wcm.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.control.SiteDomainBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.control.site.SiteOperation;

/**
*
* <p>Title: 站点相关方法rmi接口类</p>
* <p>Description: 站点相关方法rmi接口类，处理站点添加，修改，删除及列表获取的方法</p>
* <p>Copyright: Copyright (c) 2010</p>
* <p>Company: Cicro</p>
* @author zhuliang
* @version 1.0
*/
public interface ISiteRmi extends Remote{
	
	public boolean addSite(SiteBean sb,SettingLogsBean stl)throws RemoteException;
	
	public boolean updateSite(SiteBean sb,SettingLogsBean stl)throws RemoteException;
	
	public boolean updateSiteStatus(String site_id,int site_status,SettingLogsBean stl)throws RemoteException;
	
	public boolean deleteSite(String site_id,SettingLogsBean stl)throws RemoteException;
	
	public boolean addAlias(SiteDomainBean sdb,SettingLogsBean stl)throws RemoteException;
	
	public boolean updateAlias(SiteDomainBean sdb,SettingLogsBean stl)throws RemoteException;
	
	public boolean deleteAlias(String domain_ids,SettingLogsBean stl)throws RemoteException;
	
	public boolean addSiteInResourceServer(String site_id)throws RemoteException;
	
	//拷贝目录站点资源文件
	public byte[] copySiteResource(String site_id)throws RemoteException;
	
	public boolean updateHitForSite(String site_id,int count)throws RemoteException;
}
