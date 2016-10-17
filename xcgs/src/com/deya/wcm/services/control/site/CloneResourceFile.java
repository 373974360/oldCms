package com.deya.wcm.services.control.site;

import java.io.File;

import com.deya.util.FormatUtil;
import com.deya.util.JarManager;
import com.deya.util.io.FileOperation;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.rmi.ISiteRmi;

/**
 *  克隆站点资源文件,克隆站点ROOT目录下的image,js,style目录
 * <p>Title: CicroDate</p>
 * <p>Description: 站点操作管理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class CloneResourceFile implements ICloneSite{
	public boolean cloneSite(String site_id,String s_site_id)
	{
		try{
			SiteBean sb = SiteManager.getSiteBeanBySiteID(site_id);
			SiteBean s_sb = SiteManager.getSiteBeanBySiteID(s_site_id);
			//判断两个站点是否在同一台服务器上
			if(sb.getServer_id() == s_sb.getServer_id())
			{
				//如果是，直接打包附件
				compressResourceFile(sb,s_sb);
			}
			else
			{
				//如果不在同一台服务器上，需要使用rmi来传递附件
				//得到克隆目录站点的RMI,打包
				ISiteRmi siteRmi=SiteOperationFactory.getSiteRmigetSiteRMIForServerID(s_sb.getServer_id()+"");
				byte[] b = siteRmi.copySiteResource(s_site_id);
				if(b != null)
				{
					FileOperation.writeBytesToFile(FormatUtil.formatPath(sb.getSite_path()+"/resource.jar"), b, true);
					decompressResourceFile(sb.getSite_path());
				}
			}
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 资源文件打包并解压
	 * @param SiteBean sb
	 * @param SiteBean s_sb
	 * @return 
	 */
	public static void compressResourceFile(SiteBean sb,SiteBean s_sb)
	{
		String copy_dir = FormatUtil.formatPath(s_sb.getSite_path()+"/images")+","+FormatUtil.formatPath(s_sb.getSite_path()+"/js")+","+FormatUtil.formatPath(s_sb.getSite_path()+"/styles");
		JarManager.compress("resource.jar",copy_dir,FormatUtil.formatPath(s_sb.getSite_path()),sb.getSite_path());		
		decompressResourceFile(sb.getSite_path());
	}
	
	/**
	 * 资源文件解压
	 * @param SiteBean sb
	 * @param SiteBean s_sb
	 * @return 
	 */
	public static void decompressResourceFile(String site_path)
	{
		JarManager.decompress(FormatUtil.formatPath(site_path+"/resource.jar"),site_path);
		File f = new File(FormatUtil.formatPath(site_path+"/resource.jar"));
		f.delete();
	}
}
