package com.deya.wcm.services.control.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.control.SiteDomainBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.rmi.ISiteRmi;
import com.deya.wcm.services.control.site.SiteOperation;
import com.deya.wcm.services.control.site.SiteVisitCountManager;
/**
*
* <p>Title: 站点相关方法rmi接口实现类</p>
* <p>Description: 站点相关方法rmi接口类实现类，处理站点添加，修改，删除及列表获取的方法</p>
* <p>Copyright: Copyright (c) 2010</p>
* <p>Company: Cicro</p>
* @author zhuliang
* @version 1.0
*/
public class SiteRmiImpl extends UnicastRemoteObject implements ISiteRmi{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8865285105158846507L;

	public SiteRmiImpl() throws RemoteException {
		super();
	}
	
	public boolean addSite(SiteBean sb,SettingLogsBean stl)throws RemoteException{		
		return SiteOperation.addSite(sb, stl);
	}
	
	public boolean updateSite(SiteBean sb,SettingLogsBean stl)throws RemoteException{
		return SiteOperation.updateSite(sb, stl);
	}
	
	public boolean updateSiteStatus(String site_id,int site_status,SettingLogsBean stl)throws RemoteException{
		return SiteOperation.updateSiteStatus(site_id,site_status, stl);
	}
	
	public boolean deleteSite(String site_id,SettingLogsBean stl)throws RemoteException{
		return SiteOperation.deleteSite(site_id, stl);
	}
	
	public boolean addAlias(SiteDomainBean sdb,SettingLogsBean stl)throws RemoteException{
		return SiteOperation.addAlias(sdb, stl);
	}
	
	public boolean updateAlias(SiteDomainBean sdb,SettingLogsBean stl)throws RemoteException{
		return SiteOperation.updateAlias(sdb, stl);
	}
	
	public boolean deleteAlias(String domain_ids,SettingLogsBean stl)throws RemoteException{
		return SiteOperation.deleteAlias(domain_ids, stl);
	}
	
	public boolean addSiteInResourceServer(String site_id)throws RemoteException{
		return SiteOperation.addSiteInResourceServer(site_id);
	}
	//拷贝目录站点资源文件
	public byte[] copySiteResource(String site_id)throws RemoteException{
		return SiteOperation.copySiteResource(site_id);
	}
	
	/**
	 * 根据站点修改总点击次数
	 * @param String hit_type
	 * @param HttpServletRequest request
	 * @return int
	 */
	public boolean updateHitForSite(String site_id,int count)
	{
		return SiteVisitCountManager.updateHitForSite(site_id, count);
	}
}
