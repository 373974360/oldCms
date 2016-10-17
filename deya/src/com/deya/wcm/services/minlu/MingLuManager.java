package com.deya.wcm.services.minlu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.minlu.MingLuBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.minlu.MingLuDAO;

public class MingLuManager implements ISyncCatch{
	public static Map<String,MingLuBean> ml_map = new HashMap<String,MingLuBean>();
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		ml_map.clear();
		List<MingLuBean> l = MingLuDAO.getMingLuReleTemplateList();
		if(l != null && l.size() > 0)
		{
			for(MingLuBean mlb : l)
			{
				ml_map.put(mlb.getSite_id(), mlb);
			}
		}
	}
	
	public static void reloadMingLu()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.minlu.MingLuManager");		
	}
	
	public static int getNewMingLuID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.MINGLU_TEMPLATE_TABLE_NAME);
	}
	
	/**
     * 得到名录配置对象
     * @param 
     * @return MingLuBean
     * */
	public static MingLuBean getMingLuBean(String site_id)
	{
		if(ml_map.containsKey(site_id))
		{
			return ml_map.get(site_id);
		}else
		{
			MingLuBean mlb = MingLuDAO.getMingLuBean(site_id);
			if(mlb != null)
			{
				ml_map.put(mlb.getSite_id(), mlb);
				return mlb;
			}else
				return null;
		} 
	}
	
	/**
     * 添加名录与模板关联
     * @param MingLuBean
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertMingLuTemplate(MingLuBean ml,SettingLogsBean stl)
	{
		ml.setId(getNewMingLuID());
		if(MingLuDAO.insertMingLuTemplate(ml, stl))
		{
			reloadMingLu();
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
		if(MingLuDAO.updateMingLuTemplate(ml, stl))
		{
			reloadMingLu();
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
		if(MingLuDAO.deleteMingLuTemplate(site_id))
		{
			reloadMingLu();
			return true;
		}else
			return false;
	}
}
