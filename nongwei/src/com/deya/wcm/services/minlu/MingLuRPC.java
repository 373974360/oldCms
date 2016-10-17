package com.deya.wcm.services.minlu;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.minlu.MingLuBean;
import com.deya.wcm.services.Log.LogManager;

public class MingLuRPC {
	public static int getNewMingLuID()
	{
		return MingLuManager.getNewMingLuID();
	}
	
	/**
     * 得到名录配置对象
     * @param 
     * @return MingLuBean
     * */
	public static MingLuBean getMingLuBean(String site_id)
	{
		return MingLuManager.getMingLuBean(site_id);
	}
	
	/**
     * 添加名录与模板关联
     * @param MingLuBean
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertMingLuTemplate(MingLuBean ml,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return MingLuManager.insertMingLuTemplate(ml, stl);
		}else
			return false;		
	}
	
	/**
     * 修改名录与模板关联
     * @param MingLuBean
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateMingLuTemplate(MingLuBean ml,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return MingLuManager.updateMingLuTemplate(ml, stl);
		}else
			return false;		
	}
}
