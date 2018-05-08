package com.deya.wcm.services.control.site;

import java.util.List;

import com.deya.util.io.FileOperation;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.control.SiteDomainBean;
import com.deya.wcm.services.control.domain.SiteDomainManager;

/**
 *  创建站点ROOT目录下的crossdomain.xml 文件
 * <p>Title: CicroDate</p>
 * <p>Description: 创建站点ROOT目录下的crossdomain.xml 文件.</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */


public class CreateFlashConfigFile {
	private static String img_site_path = JconfigUtilContainer.bashConfig().getProperty("save_path", "", "resource_server");
	public static boolean CreateFlashFile()
	{
		String xml = getCrossdomainXML();
		List<SiteBean> siteList = SiteManager.getSiteList();
		for(SiteBean site : siteList)
		{
			String site_path = site.getSite_path()+"/crossdomain.xml";
			try{
				FileOperation.writeStringToFile(site_path, xml, false);
			}catch(Exception e){e.printStackTrace();}
		}
		try{//写图片域名下的那个站点文件 
			FileOperation.writeStringToFile(img_site_path+"/crossdomain.xml", xml, false);
		}catch(Exception e){e.printStackTrace();}
		return true;
	}
	
	public static String getCrossdomainXML()
	{
		String xml = "<?xml version=\"1.0\"?>\n\r";
		xml += "<cross-domain-policy>\n\r";
		xml += "<site-control permitted-cross-domain-policies=\"master-only\"/>\n\r";
		List<SiteBean> siteList = SiteManager.getSiteList();
		if(siteList != null && siteList.size() > 0)
		{
			for(SiteBean site : siteList)
			{
				List<SiteDomainBean> domainList = SiteDomainManager.getDomainListBySiteID(site.getSite_id());
				if(domainList != null && domainList.size() > 0)
				{
					for(SiteDomainBean domain : domainList)
						xml += "<allow-access-from domain=\""+domain.getSite_domain()+"\" />\n\r";
				}
			}
		}
		xml += "</cross-domain-policy>";
		return xml;
	}
}
