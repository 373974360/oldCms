package com.deya.wcm.rmi;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.page.PageBean;
import com.deya.wcm.bean.system.template.TemplateEditBean;
import com.deya.wcm.bean.system.template.TemplateResourcesBean;
import com.deya.wcm.bean.system.ware.WareBean;

/**
 * @title 文件操作类接口，用于在不同服务器上的文件操作
 * @author 
 * @version 1.0
 * @date   2011-6-15 上午10:18:04
 */

public interface IFileRmi extends Remote{
	//保存文件
	public boolean saveFile(String savePath,String content)throws RemoteException;
	
	//保存文件 需要判断目录是否存在
	public boolean saveFile(String savePath,String file_name,String content)throws RemoteException;
	
	//删除文件
	public boolean delFile(String savePath)throws RemoteException;
	
	//删除文件目录
	public boolean delDir(String savePath)throws RemoteException;
	
	//生面页面
	public boolean createPage(PageBean pb)throws RemoteException;
		
	//根据站点ＩＤ得到资源目录，包括images.styles.js　目录
	public String getFolderJSONStr(String site_id)throws RemoteException;	
	
	//删除索引文件（删除信息时用）
	public void delSearchIndex(String info_ids)throws RemoteException;
	
	//模板管理中，获取本服务器上图片列表
	public List<TemplateResourcesBean> getResImageListBySiteID(String site_id)throws RemoteException;
	
	//模板管理中，获取本服务器上资源文件列表
	public List<TemplateResourcesBean> getResourcesListBySiteID(String site_id)throws RemoteException;
	
	//添加模板资源目录
	public boolean addTemplateResourcesFolder(String file_path)throws RemoteException;
	
	//模板管理中，删除文件
	public boolean deleteTemplateResources(String file_path)throws RemoteException;
	
	//模板管理中，修改文件
	public boolean updateResourcesFile(String file_path,String file_content) throws RemoteException, IOException;
	
	//模板管理中，得到文件内容
	public String getResourcesFileContent(String file_path)throws RemoteException, IOException;
	
	//生成模板文件
	public boolean saveTemplateFile(TemplateEditBean teb)throws RemoteException;
	
	//生成标签文件
	public void createWarePage(WareBean wb) throws RemoteException;
	
	//以下为信息操作的方法，会比较多，因为会有多循环，或多次调用，考虑性能问题，在入口处只调用一次，避免不停的调用接口
	
	/**
	 * 添加模型信息
	 * @param ob	模型信息对象
	 * @param model_name	模型信息名称
	 * @param request
	 * @return
	 */
	public boolean insertInfo(Object ob, String model_name, SettingLogsBean stl)throws RemoteException;
	
	/**
	 * 修改模型信息
	 * @param ob	模型信息对象
	 * @param model_name	模型信息名称
	 * @param request
	 * @return
	 */
	public boolean updateInfo(Object ob, String model_name, SettingLogsBean stl)throws RemoteException;
	
	//获取信息操作
	public boolean infoGet(List<InfoBean> l,String s_site_id,String s_app_id,int cat_id,int get_type,boolean is_publish,int user_id)throws RemoteException;

	/**
	 * 批量发布静态内容页,根据栏目,站点和发布时间得到信息
	 * @param map
	 * @return boolean
	 */
	public boolean batchPublishContentHtml(Map<String, String> map)throws RemoteException;
	
	/**
	 * 转移信息
	 * @param List<InfoBean> l
	 * @param int to_cat_id 要转移的目标栏目ID
	 * @param String app_id
	 * @param String site_id
	 * @return boolean
	 */
	public boolean MoveInfo(List<InfoBean> l,int to_cat_id,String app_id,String site_id,SettingLogsBean stl)throws RemoteException;
	
	/**
	 * 发布，撤销信息
	 * @param List<InfoBean> l
	 * @param String status
	 * @return boolean
	 */
	public boolean updateInfoStatus(List<InfoBean> l, String status,SettingLogsBean stl)throws RemoteException;

	/**
	 * 审核中信息撤回
	 * @param List<InfoBean> l
	 * @param String status
	 * @return boolean
	 */
	public boolean backPassInfoStatus(String info_id, String status,SettingLogsBean stl)throws RemoteException;
	
	/**
	 * 纯修改信息（用于修改引用信息的主表内容）
	 * @param info
	 * @param stl
	 * @return boolean
	 */
	public boolean updateInfoEvent(InfoBean info, SettingLogsBean stl)throws RemoteException;
	
	/**
	 * 审核信息通过
	 * @param List<InfoBean> info_list
	 * @param stl
	 * @return boolean
	 */
	public boolean passInfoStatus(List<InfoBean> info_list,String auto_desc,String user_id, SettingLogsBean stl)throws RemoteException;
	
	/**
	 * 生成内容页
	 * @param List<InfoBean> l
	 * @throws IOException 
	 */
	public boolean createContentHTML(List<InfoBean> l)throws RemoteException;
	
	/**
	 * 删除信息
	 * @param List<InfoBean> l
	 * @throws IOException 
	 */
	public boolean deleteInfo(List<InfoBean> l, SettingLogsBean stl)throws RemoteException;

	/**
	 * 归档信息
	 * @param List<InfoBean> l
	 * @throws IOException
	 */
	public boolean backInfo(String info_ids, SettingLogsBean stl)throws RemoteException;
	
}
