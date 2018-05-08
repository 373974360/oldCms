package com.deya.wcm.dao.org.operate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.app.AppBean;
import com.deya.wcm.bean.org.operate.OperateBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.org.role.RoleOptDAO;
import com.deya.wcm.db.DBManager;

/**
 *  权限注册管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 权限注册管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class OperateDAO {
	/**
     * 得到应用列表
     * @param 
     * @return List
     * */	
	@SuppressWarnings("unchecked")
	public static List<AppBean> getAppList()
	{
		return DBManager.queryFList("getAppList", "");
	}
	
	/**
     * 得到所有权限列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<OperateBean> getAllOperateList()
	{
		return DBManager.queryFList("getAllOperateList", "");
	}
	
	/**
     * 根据权限ID得到对象
     * @param String opt_id
     * @return OperateBean
     * */
	public static OperateBean getOperateBean(String opt_id)
	{
		return (OperateBean)DBManager.queryFObj("getOperateBean", opt_id);
	}
	
	/**
     * 插入权限信息
     * @param OperateBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertOperate(OperateBean ob,SettingLogsBean stl)
	{	
		ob.setTree_position(ob.getTree_position()+ ob.getOpt_id() + "$");
		if(DBManager.insert("insert_operate", ob))
		{
			PublicTableDAO.insertSettingLogs("添加","权限",ob.getOpt_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 修改权限信息
     * @param OperateBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateOperate(OperateBean ob,SettingLogsBean stl)
	{
		if(DBManager.update("update_operate", ob))
		{
			PublicTableDAO.insertSettingLogs("添加","修改",ob.getOpt_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 移动权限
     * @param String parent_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean moveOperate(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("move_operate", m))
		{			
			PublicTableDAO.insertSettingLogs("移动","权限",m.get("opt_id"),stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 删除权限信息
     * @param String opt_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteOperate(String opt_id,SettingLogsBean stl)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("opt_id", opt_id);
		if(DBManager.delete("delete_operate", m))
		{
			//删除权限角色关联
			RoleOptDAO.deleteOptReleRoleByOptID(opt_id);
			PublicTableDAO.insertSettingLogs("删除","权限",opt_id,stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 根据角色ID得到关联的权限所对应的应用系统ID
     * @param String role_ids
     * @return String
     * */
	@SuppressWarnings("unchecked")
	public static List<AppBean> getOptAppListbyRoleID(String role_ids)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("role_ids", role_ids);
		return DBManager.queryFList("getOptAppListbyRoleID", m);
	}
	
	
}

