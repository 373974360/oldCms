package com.deya.wcm.services.org.operate;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.app.AppBean;
import com.deya.wcm.bean.org.operate.OperateBean;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.org.app.AppManager;

public class OperateRPC {
	/**
     * 得到权限Map
     * @param
     * @return String
     * */
	public static Map<String,OperateBean> getOptMap()
	{
		return OperateManager.getOptMap();
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
     * 得到权限ID,用于添加页面
     * @param
     * @return String
     * */
	public static int getOperateID()
	{
		return OperateManager.getOperateID();
	}
	
	/**
     * 得到所有权限,并组织为json数据
     * @param String app_id
     * @return String
     * */
	public static String getOperateTreeJsonStr(String app_id){
		return OperateManager.getOperateTreeJsonStr(app_id);
	}
	
	/**
     * 得到所有权限,并组织为json数据(用于在我的权限中展示权限树)
     * @param String app_ids 可多个，用逗号分隔
     * @return String
     * */
	public static String getOperateTreeJsonStr2(String app_ids)
	{
		return OperateManager.getOperateTreeJsonStr2(app_ids);
	}
	
	/**
     * 根据权限ID得到对象
     * @param String opt_id
     * @return OperateBean
     * */
	public static OperateBean getOperateBean(String opt_id)
	{
		return OperateManager.getOperateBean(opt_id);
	}
	
	/**
     * 根据权限ID得到此节点下的权限节点个数
     * @param String opt_id
     * @return String
     * */
	public static String getChildOptCount(String opt_id)
	{
		return OperateManager.getChildOptCount(opt_id);
	}
	
	/**
     * 根据权限ID得到此节点下的权限节点列表deep+1
     * @param String opt_id
     * @return List
     * */
	public static List<OperateBean> getChildOptList(String opt_id)
	{
		return OperateManager.getChildOptList(opt_id);
	}
	
	/**
     * 插入权限信息
     * @param OperateBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertOperate(OperateBean ob,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return OperateManager.insertOperate(ob,stl);
		}else
			return false;
	}	
	
	/**
     * 修改权限信息
     * @param OperateBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateOperate(OperateBean ob,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return OperateManager.updateOperate(ob,stl);
		}else
			return false;
	}
	
	/**
     * 移动权限
     * @param String parent_id
     * @param String menu_ids
     * @return boolean
     * */
	public static boolean moveOperate(String parent_id,String opt_id,HttpServletRequest request) {
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return OperateManager.moveOperate(parent_id,opt_id,stl);
		}else
			return false;
	}
	
	/**
     * 删除权限信息
     * @param String opt_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteOperate(String opt_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return OperateManager.deleteOperate(opt_id,stl);
		}else
			return false;
	}
}
