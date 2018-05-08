package com.deya.wcm.services.material;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.util.UploadManager;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.material.MateFolderBean;
import com.deya.wcm.bean.org.app.AppBean;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.org.app.AppManager;
import com.deya.wcm.services.system.config.ConfigManager;

public class MateFolderRPC {
	/**
	 * 得到附件的访问路径
	 *
	 * @param return
	 *            访问路径
	 */
	public static String getImgDomain(String site_id)
	{
		return UploadManager.getImgBrowserUrl(site_id);
	}
	
	/**
     * 得到上传文件的配置信息
     * @param
     * @return String
     * */
	public static Map<String, String> getValues(Map<String, String> map){
		return ConfigManager.getConfigMap(map);
	}
	/**
     * 得到文件夹Map
     * @param
     * @return String
     * */
	public static Map<String,MateFolderBean> getMateFolderMap()
	{
		return MateFolderManager.getMateFolderMap();
	}
	/**
	 * 得到应用系统列表（用于后台页面管理，列出所有的应用系统）
	 * 
	 * @param
	 * @return List
	 */	
	public static List<AppBean> getAppList(){
		return AppManager.getAppList();
	}
	/**
     * 得到文件夹ID,用于添加页面
     * @param
     * @return String
     * */
	public static int getMateFolderID()
	{
		return MateFolderManager.getMateFolderID();
	}
	/**
     * 得到所有文件夹,并组织为json数据
     * @param
     * @return String
     * */
	public static String getMateFolderTreeJsonStr(String f_id,String site_id,String user_id){
		return MateFolderManager.getMateFolderTreeJsonStr(f_id,site_id,user_id);
	}
	
	/**
     * 得到用户自定义文件夹 ,并组织为json数据
     * @param
     * @return String
     * */
	public static String getMFTreeJsonStrForCustom(String site_id,String user_id){
		return MateFolderManager.getMFTreeJsonStrForCustom(site_id,user_id);
	}
	/**
     * 根据文件夹ID得到对象
     * @param String opt_id
     * @return MateFolderBean
     * */
	public static MateFolderBean getMateFolderBean(String opt_id)
	{
		return MateFolderManager.getMateFolderBean(opt_id);
	}
	/**
     * 根据文件夹ID得到此节点下的文件夹节点个数
     * @param String opt_id
     * @return String
     * */
	public static String getChildMateFolderCount(String opt_id,String user_id)
	{
		return MateFolderManager.getChildMateFolderCount(opt_id,user_id);
	}
	
	/**
     * 根据文件夹ID得到根节点下的文件夹
     * @param String opt_id
     * @return List
     * 
	public static List<MateFolderBean> getMateFolderList(String f_id)
	{
		return MateFolderManager.getMateFolderList(f_id);
	}*/
	
	/**
     * 根据文件夹ID得到此节点下的文件夹节点列表deep+1
     * @param String opt_id
     * @return List
     * */
	public static List<MateFolderBean> getMateFolderList(String f_id,String site_id,String user_id)
	{
		return MateFolderManager.getMateFolderList(f_id,site_id,user_id);
	}
	/**
     * 插入文件夹信息
     * @param MateFolderBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertMateFolder(MateFolderBean ob,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return MateFolderManager.insertMateFolder(ob,stl);
		}else
			return false;
	}	
	/**
     * 修改文件夹信息
     * @param MateFolderBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateMateFolder(MateFolderBean ob,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return MateFolderManager.updateMateFolder(ob,stl);
		}else
			return false;
	}
	/**
     * 移动文件夹
     * @param String parent_id
     * @param String menu_ids
     * @return boolean
     * */
	public static boolean moveMateFolder(String parent_id,String opt_id,HttpServletRequest request,String site_id,String user_id) {
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return MateFolderManager.moveMateFolder(parent_id,opt_id,stl,site_id,user_id);
		}else
			return false;
	}
	/**
     * 删除文件夹信息
     * @param String opt_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteMateFolder(String opt_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return MateFolderManager.deleteMateFolder(opt_id,stl);
		}else
			return false;
	}
	
	/**
     * 根据权限ID得到此节点下的权限节点个数
     * @param String opt_id
     * @return String
     * */
	public static String getChildOptCount(String opt_id,String user_id)
	{
		return MateFolderManager.getChildMateFolderCount(opt_id,user_id);
	}
	public static void main(String[] agrs){
		//System.out.println(getMateFolderID());
	}
}