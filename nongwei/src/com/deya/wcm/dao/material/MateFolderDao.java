package com.deya.wcm.dao.material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.material.MateFolderBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;


public class MateFolderDao {
	
	/**
     * 得到文件夹列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<MateFolderBean> getMateFolderBeanList()
	{
//		Map<String, String> m = new HashMap<String, String>();
//		m.put("site_id", site_id);
//		m.put("user_id", user_id);	
		return DBManager.queryFList("getMateFolderList","");
	}
	/**
     * 根据素材库文件夹ID得到文件夹对象
     * @param int f_id
     * @return MateFolderBean
     * */
	public static MateFolderBean getMateFolderBean(String f_id)
	{
		return (MateFolderBean)DBManager.queryFObj("getMateFolderBean",f_id);
	}
	/**
     * 插入素材库文件夹信息
     * @param MateFolderBean mf
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertMateFolder(MateFolderBean mf,SettingLogsBean stl)
	{	
		if(DBManager.insert("insert_MateFolder", mf))
		{
			PublicTableDAO.insertSettingLogs("添加","文件夹",mf.getClass()+"",stl);
			return true;
		}else
			return false;
	}
	/**
     * 修改素材库文件夹信息
     * @param MateFolderBean mf
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateMateFolder(MateFolderBean mf,SettingLogsBean stl)
	{
		mf.setApp_id("cms");
		if(DBManager.update("update_MateFolder", mf))
		{
			PublicTableDAO.insertSettingLogs("添加","修改",mf.getF_id()+"",stl);
			return true;
		}else
			return false;
	}
	/**
     * 移动素材库文件夹
     * @param String parent_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean moveMateFolder(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("move_MateFolder", m))
		{			
			PublicTableDAO.insertSettingLogs("移动","文件夹",m.get("f_id"),stl);
			return true;
		}
		else
			return false;
	}
	/**
     * 删除素材库文件夹
     * @param String f_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteMateFolder(String  f_id,SettingLogsBean stl)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("f_id", f_id);
		if(DBManager.delete("delete_MateFolder", m))
		{
			//删除权限角色关联
			//RoleOptDAO.deleteOptReleRoleByOptID(opt_id);
			PublicTableDAO.insertSettingLogs("删除","文件夹",f_id,stl);
			return true;
		}else
			return false;
	}
}