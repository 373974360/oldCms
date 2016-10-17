package com.deya.wcm.dao.minlu;

import java.util.List;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.minlu.MingLuBean;
import com.deya.wcm.dao.LogUtil;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

public class MingLuDAO {
	/**
     * 得到所有与模板的关联信息
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<MingLuBean> getMingLuReleTemplateList()
	{
		return DBManager.queryFList("getMingLuReleTemplateList","");
	}
	
	/**
     * 得到名录配置对象
     * @param 
     * @return MingLuBean
     * */
	public static MingLuBean getMingLuBean(String site_id)
	{
		return (MingLuBean)DBManager.queryFObj("getMingLuBean",site_id);
	}
	
	/**
     * 添加名录与模板关联
     * @param MingLuBean
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertMingLuTemplate(MingLuBean ml,SettingLogsBean stl)
	{
		if(DBManager.insert("insert_minlu_template", ml))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.ADD,"公务员名录配置信息 站点ID",ml.getSite_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	
	/**
     * 修改名录与模板关联
     * @param MingLuBean
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateMingLuTemplate(MingLuBean ml,SettingLogsBean stl)
	{
		if(DBManager.update("update_minlu_template", ml))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.UPDATE,"公务员名录配置信息 站点ID",ml.getSite_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 删除站点时删除名录与模板关联
     * @param String site_id
     * @return boolean
     * */
	public static boolean deleteMingLuTemplate(String site_id)
	{
		return DBManager.delete("delete_minlu_template", site_id);
	}
}
