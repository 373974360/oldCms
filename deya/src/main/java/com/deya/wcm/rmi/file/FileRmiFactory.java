package com.deya.wcm.rmi.file;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.deya.util.FormatUtil;
import com.deya.util.io.FileOperation;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.page.PageBean;
import com.deya.wcm.bean.system.template.TemplateEditBean;
import com.deya.wcm.bean.system.template.TemplateResourcesBean;
import com.deya.wcm.bean.system.ware.WareBean;
import com.deya.wcm.rmi.IFileRmi;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.cms.info.InfoPublishManager;
import com.deya.wcm.services.cms.info.ModelUtil;
import com.deya.wcm.services.control.server.SiteServerManager;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.page.PageManager;
import com.deya.wcm.services.system.template.TemplateResourcesManager;
import com.deya.wcm.services.system.ware.WareManager;
import com.deya.wcm.template.TemplateBase;

public class FileRmiFactory {
	public static IFileRmi getFileRmiObj(String site_id)
	{
		SiteBean si = SiteManager.getSiteBeanBySiteID(site_id);
		if(si == null)
			return null;
		String server_ip = SiteServerManager.getServerBeanByID(si.getServer_id()+"").getServer_ip();	
		
		try{			
			Context namingContext=new InitialContext();
			String path = "rmi://"+server_ip+":"+JconfigUtilContainer.bashConfig().getProperty("port", "", "rmi_config")+"/fileRmi";			
			return (IFileRmi)namingContext.lookup(path);
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean saveFile(String site_id,String savePath,String file_name,String content)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(site_id))
		{
			//如果不是多台服务器,或者就是在本服务器上操作的，直接执行了
			File f = new File(savePath);
			if(!f.exists())
			{
				f.mkdirs();
			}
			savePath = FormatUtil.formatPath(savePath+"/"+file_name);
			try {
				return FileOperation.writeStringToFile(savePath,content,false,"utf-8");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}else
		{
			IFileRmi ifr = getFileRmiObj(site_id);
			try {
				return ifr.saveFile(savePath,file_name, content);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public static boolean saveFile(String site_id,String savePath,String content)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(site_id))
		{
			//如果不是多台服务器，直接执行了
			try {
				return FileOperation.writeStringToFile(savePath,content,false,"utf-8");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}	
		}else
		{
			IFileRmi ifr = getFileRmiObj(site_id);
			try {
				return ifr.saveFile(savePath, content);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public static boolean delFile(String site_id,String savePath)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(site_id))
		{
			//如果不是多台服务器，直接执行了
			try {
				File f = new File(savePath);
				if(f.exists()){
					f.delete();
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}else
		{
			IFileRmi ifr = getFileRmiObj(site_id);
			try {
				return ifr.delFile(savePath);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public static boolean delDir(String site_id,String savePath)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(site_id))
		{
			//如果不是多台服务器，直接执行了
			return FileOperation.deleteDir(savePath);
		}else
		{
			IFileRmi ifr = getFileRmiObj(site_id);
			try {
				return ifr.delDir(savePath);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public static boolean createPage(PageBean pb)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(pb.getSite_id()))
		{
			//如果不是多台服务器，直接执行了
			try {
				return PageManager.createPageHandl(pb);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		else
		{
			IFileRmi ifr = getFileRmiObj(pb.getSite_id());
			try {
				return ifr.createPage(pb);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public static void delSearchIndex(String site_id,String info_ids)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(site_id))
		{
			//如果不是多台服务器，直接执行了
			com.deya.wcm.services.search.SearchInnerManager.infoSetDel(info_ids);
		}else
		{
			IFileRmi ifr = getFileRmiObj(site_id);
			try {
				ifr.delSearchIndex(info_ids);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();			
			}
		}
	}
	
	//模板管理中，获取本服务器上图片列表
	public static List<TemplateResourcesBean> getResImageListBySiteID(String site_id)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(site_id))
		{
			return TemplateResourcesManager.getResImageListBySiteID(site_id);
		}else
		{
			IFileRmi ifr = getFileRmiObj(site_id);
			try {
				return ifr.getResImageListBySiteID(site_id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}
	
	//添加模板资源目录
	public static boolean addTemplateResourcesFolder(String file_path)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(file_path))
		{
			return TemplateResourcesManager.addTemplateResourcesFolder(file_path);
		}else
		{
			IFileRmi ifr = getFileRmiObj(file_path);
			try {
				return ifr.addTemplateResourcesFolder(file_path);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	//模板管理中，获取本服务器上资源文件列表
	public static List<TemplateResourcesBean> getResourcesListBySiteID(String site_id)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(site_id))
		{
			return TemplateResourcesManager.getResourcesListBySiteID(site_id);
		}else
		{
			IFileRmi ifr = getFileRmiObj(site_id);
			try {
				return ifr.getResourcesListBySiteID(site_id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}
	
	//根据站点ＩＤ得到资源目录，包括images.styles.js　目录
	public static String getFolderJSONStr(String site_id)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(site_id))
		{
			return TemplateResourcesManager.getFolderJSONStr(site_id);
		}else
		{
			IFileRmi ifr = getFileRmiObj(site_id);
			try {
				return ifr.getFolderJSONStr(site_id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}
	
	//模板管理中，删除文件
	public static boolean deleteTemplateResources(String file_path,String site_id)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(site_id))
		{
			return TemplateResourcesManager.deleteTemplateResources(file_path);
		}else
		{
			IFileRmi ifr = getFileRmiObj(site_id);
			try {
				return ifr.deleteTemplateResources(file_path);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	//模板管理中，修改文件
	public static boolean updateResourcesFile(String file_path,String file_content,String site_id) throws IOException
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(site_id))
		{
			return TemplateResourcesManager.updateResourcesFile(file_path,file_content);
		}else
		{
			IFileRmi ifr = getFileRmiObj(site_id);
			try {
				return ifr.updateResourcesFile(file_path,file_content);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	//模板管理中，得到文件内容
	public static String getResourcesFileContent(String file_path,String site_id) throws IOException
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(site_id))
		{
			return TemplateResourcesManager.getResourcesFileContent(file_path);
		}else
		{
			IFileRmi ifr = getFileRmiObj(site_id);
			try {
				return ifr.getResourcesFileContent(file_path);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
		}
	}
	
	//生成模板文件
	public static boolean saveTemplateFile(String site_id,TemplateEditBean teb)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(site_id))
		{
			//如果不是多台服务器，直接执行了
			return TemplateBase.saveTemplateFileHandl(teb);
		}else
		{
			IFileRmi ifr = getFileRmiObj(site_id);
			try {
				return ifr.saveTemplateFile(teb);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	//生成标签文件
	public static void createWarePage(WareBean wb)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(wb.getSite_id()))
		{
			//如果不是多台服务器，直接执行了
			try {
				WareManager.createWarePageHandl(wb);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			IFileRmi ifr = getFileRmiObj(wb.getSite_id());
			try {
				ifr.createWarePage(wb);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 添加模型信息
	 * @param ob	模型信息对象
	 * @param model_name	模型信息名称
	 * @param request
	 * @return
	 */
	public static boolean insertInfo(String rmi_site_id,Object ob, String model_name, SettingLogsBean stl)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(rmi_site_id))
		{
			//如果不是多台服务器，直接执行了
			return ModelUtil.insert(ob, model_name, stl);			
		}else
		{
			IFileRmi ifr = getFileRmiObj(rmi_site_id);
			try {
				return ifr.insertInfo(ob, model_name, stl);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	/**
	 * 修改模型信息
	 * @param ob	模型信息对象
	 * @param model_name	模型信息名称
	 * @param request
	 * @return
	 */
	public static boolean updateInfo(String rmi_site_id,Object ob, String model_name, SettingLogsBean stl)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(rmi_site_id))
		{
			//如果不是多台服务器，直接执行了
			return ModelUtil.update(ob, model_name, stl);			
		}else
		{
			IFileRmi ifr = getFileRmiObj(rmi_site_id);
			try {
				return ifr.updateInfo(ob, model_name, stl);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	//获取信息操作
	public static boolean infoGet(String rmi_site_id,List<InfoBean> l,String s_site_id,String s_app_id,int cat_id,int get_type,boolean is_publish,int user_id)throws RemoteException
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(rmi_site_id))
		{
			//如果不是多台服务器，直接执行了
			return InfoBaseManager.infoGet(l, s_site_id, s_app_id, cat_id, get_type, is_publish, user_id);
			
		}else
		{
			IFileRmi ifr = getFileRmiObj(rmi_site_id);
			try {
				return ifr.infoGet(l, s_site_id, s_app_id, cat_id, get_type, is_publish, user_id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	/**
	 * 批量发布静态内容页,根据栏目,站点和发布时间得到信息
	 * @param map
	 * @return boolean
	 */
	public static boolean batchPublishContentHtml(String site_id,Map<String, String> map)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(site_id))
		{
			return InfoBaseManager.batchPublishContentHtml(map);
		}else
		{
			IFileRmi ifr = getFileRmiObj(site_id);
			try {
				return ifr.batchPublishContentHtml(map);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	/**
	 * 转移信息
	 * @param List<InfoBean> l
	 * @param int to_cat_id 要转移的目标栏目ID
	 * @param String app_id
	 * @param String site_id
	 * @return boolean
	 */
	public static boolean MoveInfo(String rmi_site_id,List<InfoBean> l,int to_cat_id,String app_id,String site_id,SettingLogsBean stl)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(site_id))
		{
			return InfoBaseManager.MoveInfo(l, to_cat_id, app_id, site_id, stl);
		}else
		{
			IFileRmi ifr = getFileRmiObj(rmi_site_id);
			try {
				return ifr.MoveInfo(l, to_cat_id, app_id, site_id, stl);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	/**
	 * 发布，撤销信息
	 * @param List<InfoBean> l
	 * @param String status
	 * @return boolean
	 */
	public static boolean updateInfoStatus(String rmi_site_id,List<InfoBean> l, String status,SettingLogsBean stl)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(rmi_site_id))
		{
			return InfoBaseManager.updateInfoStatus(l, status, stl);
		}else
		{
			IFileRmi ifr = getFileRmiObj(rmi_site_id);
			try {
				return ifr.updateInfoStatus(l, status, stl);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * 审核中信息撤回
	 * @param List<InfoBean> l
	 * @param String status
	 * @return boolean
	 */
	public static boolean backPassInfoStatus(String rmi_site_id,String info_id, String status,SettingLogsBean stl)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(rmi_site_id))
		{
			return InfoBaseManager.backPassInfoStatus(info_id, status, stl);
		}else
		{
			IFileRmi ifr = getFileRmiObj(rmi_site_id);
			try {
				return ifr.backPassInfoStatus(info_id, status, stl);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	/**
	 * 纯修改信息（用于修改引用信息的主表内容）
	 * @param info
	 * @param stl
	 * @return boolean
	 */
	public static boolean updateInfoEvent(String rmi_site_id,InfoBean info, SettingLogsBean stl)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(rmi_site_id))
		{
			return InfoBaseManager.updateInfoEvent(info, stl);
		}else
		{
			IFileRmi ifr = getFileRmiObj(rmi_site_id);
			try {
				return ifr.updateInfoEvent(info, stl);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	/**
	 * 审核信息通过
	 * @param List<InfoBean> info_list
	 * @param stl
	 * @return boolean
	 */
	public static boolean passInfoStatus(String rmi_site_id,List<InfoBean> info_list,String auto_desc,String user_id,SettingLogsBean stl)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(rmi_site_id))
		{
			return InfoBaseManager.passInfoStatus(info_list,auto_desc,user_id, stl);
		}else
		{
			IFileRmi ifr = getFileRmiObj(rmi_site_id);
			try {
				return ifr.passInfoStatus(info_list,auto_desc,user_id, stl);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	/**
	 * 生成内容页
	 * @param List<InfoBean> l
	 * @throws IOException 
	 */
	public static boolean createContentHTML(String rmi_site_id,List<InfoBean> l)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(rmi_site_id))
		{
			try {
				return InfoPublishManager.createContentHTML(l);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}else
		{
			IFileRmi ifr = getFileRmiObj(rmi_site_id);
			try {
				return ifr.createContentHTML(l);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	/**
	 * 删除信息
	 * @param List<InfoBean> l
	 * @throws IOException 
	 */
	public static boolean deleteInfo(String rmi_site_id,List<InfoBean> l, SettingLogsBean stl)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(rmi_site_id))
		{
			return InfoBaseManager.deleteInfo(l,stl);
		}else
		{
			IFileRmi ifr = getFileRmiObj(rmi_site_id);
			try {
				return ifr.deleteInfo(l,stl);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * 删除信息
	 * @param List<InfoBean> l
	 * @throws IOException
	 */
	public static boolean backInfo(String rmi_site_id,String info_ids, SettingLogsBean stl)
	{
		if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(rmi_site_id))
		{
			return InfoBaseManager.backInfo(info_ids,stl);
		}else
		{
			IFileRmi ifr = getFileRmiObj(rmi_site_id);
			try {
				return ifr.backInfo(info_ids,stl);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
}
