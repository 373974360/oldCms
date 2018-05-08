package com.deya.wcm.rmi.file;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import com.deya.util.FormatUtil;
import com.deya.util.io.FileOperation;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.page.PageBean;
import com.deya.wcm.bean.system.template.TemplateEditBean;
import com.deya.wcm.bean.system.template.TemplateResourcesBean;
import com.deya.wcm.bean.system.ware.WareBean;
import com.deya.wcm.rmi.IFileRmi;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.cms.info.InfoPublishManager;
import com.deya.wcm.services.cms.info.ModelUtil;
import com.deya.wcm.services.page.PageManager;
import com.deya.wcm.services.system.template.TemplateResourcesManager;
import com.deya.wcm.services.system.ware.WareManager;
import com.deya.wcm.template.TemplateBase;

public class FileRmiImpl extends UnicastRemoteObject implements IFileRmi{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8265188463990287682L;

	public FileRmiImpl() throws RemoteException {
		super();
	}
	
	public boolean saveFile(String savePath,String content)throws RemoteException
	{
		try {
			return FileOperation.writeStringToFile(savePath,content,false,"utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
	}
	
	public boolean saveFile(String savePath,String file_name,String content)throws RemoteException
	{
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
	}
	
	//生面页面
	public boolean createPage(PageBean pb)throws RemoteException{
		try {
			return PageManager.createPageHandl(pb);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	//删除文件目录
	public boolean delFile(String savePath)throws RemoteException{
		try {
			if((savePath).endsWith("ROOT/"))
			{
				return true;
			}else
			{
				File f = new File(savePath);
				if(f.exists()){
					f.delete();
				}
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//删除文件目录
	public boolean delDir(String savePath)throws RemoteException{
		//再判断一下吧，root下面不能删除
		if((savePath).endsWith("ROOT/"))
		{
			return true;
		}else
			return FileOperation.deleteDir(savePath);
	}
	
	//生成索引文件（发布信息时用）
	public void saveSearchIndex(String info_ids)throws RemoteException{
		com.deya.wcm.services.search.SearchInnerManager.infoSetAdd(info_ids);
	}
	
	//删除索引文件（删除信息时用）
	public void delSearchIndex(String info_ids)throws RemoteException{
		com.deya.wcm.services.search.SearchInnerManager.infoSetDel(info_ids);
	}
	
	//根据站点ＩＤ得到资源目录，包括images.styles.js　目录
	public String getFolderJSONStr(String site_id)throws RemoteException
	{
		return TemplateResourcesManager.getFolderJSONStr(site_id);
	}
	
	//模板管理中，获取本服务器上图片列表
	public List<TemplateResourcesBean> getResImageListBySiteID(String site_id)throws RemoteException
	{
		return TemplateResourcesManager.getResImageListBySiteID(site_id);
	}
	
	//模板管理中，获取本服务器上资源文件列表
	public List<TemplateResourcesBean> getResourcesListBySiteID(String site_id)throws RemoteException
	{
		return TemplateResourcesManager.getResourcesListBySiteID(site_id);
	}
	
	//添加模板资源目录
	public boolean addTemplateResourcesFolder(String file_path)throws RemoteException
	{
		return TemplateResourcesManager.addTemplateResourcesFolder(file_path);
	}
	
	//模板管理中，删除文件
	public boolean deleteTemplateResources(String file_path)
	{
		return TemplateResourcesManager.deleteTemplateResources(file_path);
	}
	
	//模板管理中，修改文件
	public boolean updateResourcesFile(String file_path,String file_content) throws IOException
	{
		return TemplateResourcesManager.updateResourcesFile(file_path,file_content);
	}
	
	//模板管理中，得到文件内容
	public String getResourcesFileContent(String file_path) throws IOException
	{
		return TemplateResourcesManager.getResourcesFileContent(file_path);
	}
	
	//生成模板文件
	public boolean saveTemplateFile(TemplateEditBean teb)throws RemoteException
	{
		return TemplateBase.saveTemplateFileHandl(teb);
	}
	
	//生成标签文件
	public void createWarePage(WareBean wb) throws RemoteException
	{
		try {
			WareManager.createWarePageHandl(wb);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加模型信息
	 * @param ob	模型信息对象
	 * @param model_name	模型信息名称
	 * @param request
	 * @return
	 */
	public boolean insertInfo(Object ob, String model_name, SettingLogsBean stl)throws RemoteException
	{
		return ModelUtil.insert(ob, model_name, stl);
	}
	
	/**
	 * 修改模型信息
	 * @param ob	模型信息对象
	 * @param model_name	模型信息名称
	 * @param request
	 * @return
	 */
	public boolean updateInfo(Object ob, String model_name, SettingLogsBean stl)throws RemoteException
	{
		return ModelUtil.update(ob, model_name, stl);
	}
	
	//获取信息操作
	public boolean infoGet(List<InfoBean> l,String s_site_id,String s_app_id,int cat_id,int get_type,boolean is_publish,int user_id)throws RemoteException
	{
		return InfoBaseManager.infoGet(l, s_site_id, s_app_id, cat_id, get_type, is_publish, user_id);		
	}
	
	/**
	 * 批量发布静态内容页,根据栏目,站点和发布时间得到信息
	 * @param map
	 * @return boolean
	 */
	public boolean batchPublishContentHtml(Map<String, String> map)throws RemoteException
	{
		return InfoBaseManager.batchPublishContentHtml(map);
	}
	
	/**
	 * 转移信息
	 * @param List<InfoBean> l
	 * @param int to_cat_id 要转移的目标栏目ID
	 * @param String app_id
	 * @param String site_id
	 * @return boolean
	 */
	public boolean MoveInfo(List<InfoBean> l,int to_cat_id,String app_id,String site_id,SettingLogsBean stl)throws RemoteException
	{
		return InfoBaseManager.MoveInfo(l, to_cat_id, app_id, site_id, stl);
	}
	
	/**
	 * 发布，撤销信息
	 * @param List<InfoBean> l
	 * @param String status
	 * @return boolean
	 */
	public boolean updateInfoStatus(List<InfoBean> l, String status,SettingLogsBean stl)throws RemoteException
	{
		return InfoBaseManager.updateInfoStatus(l, status, stl);
	}
	
	/**
	 * 纯修改信息（用于修改引用信息的主表内容）
	 * @param info
	 * @param stl
	 * @return boolean
	 */
	public boolean updateInfoEvent(InfoBean info, SettingLogsBean stl)throws RemoteException
	{
		return InfoBaseManager.updateInfoEvent(info, stl);
	}
	
	/**
	 * 审核信息通过
	 * @param List<InfoBean> info_list
	 * @param stl
	 * @return boolean
	 */
	public boolean passInfoStatus(List<InfoBean> info_list,String user_id, SettingLogsBean stl)throws RemoteException
	{
		return InfoBaseManager.passInfoStatus(info_list, user_id, stl);
	}
	
	/**
	 * 生成内容页
	 * @param List<InfoBean> l
	 * @throws IOException 
	 */
	public boolean createContentHTML(List<InfoBean> l)throws RemoteException
	{
		try {
			return InfoPublishManager.createContentHTML(l);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除信息
	 * @param List<InfoBean> l
	 * @throws IOException 
	 */
	public boolean deleteInfo(List<InfoBean> l, SettingLogsBean stl)throws RemoteException
	{
		return InfoBaseManager.deleteInfo(l,stl);
	}

	/**
	 * 归档信息
	 * @param List<InfoBean> l
	 * @throws IOException
	 */
	@Override
	public boolean backInfo(String info_ids, SettingLogsBean stl) throws RemoteException {
		return InfoBaseManager.backInfo(info_ids,stl);
	}
}
