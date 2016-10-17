package com.deya.wcm.services.appeal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.appeal.AreaBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.operate.OperateBean;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.org.operate.OperateManager;

 

public class AreaRPC {
  
	/**
     * 根据权限ID得到此节点下的权限节点列表deep+1
     * @param String area_id
     * @return List
     * */
	public static List<AreaBean> getChildAreaList(int area_id)
	{
		return AreaManager.getChildAreaList(area_id);
	}
	/**
     * 得到所有权限,并组织为json数据
     * @param
     * @return String
     * */
	public static String getAreaTreeJsonStr(){
		return AreaManager.getAreaTreeJsonStr();
	}
	/**
     * 根据ID得到此节点下的节点个数
     * @param String area_id
     * @return String
     * */
	public static String getChildAreaCount(int area_id)
	{
		return AreaManager.getChildAreaCount(area_id);
	}
	/**
     * 得到地区ID,用于添加页面
     * @param
     * @return String
     * */
	public static int getAreaID()
	{
		return AreaManager.getAreaID();
	} 
	/**
     * 插入地区信息
     * @param AreaBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertArea(AreaBean ob,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return AreaManager.insertArea(ob,stl);
		}else
			return false;
	}
	/**
	 * 根据地区ID返回对象
	 * @param area_id
	 * @return Bean
	 */
	public static AreaBean getAreaBean(int area_id)
	{ 
		return AreaManager.getAreaBean(area_id);
	}
	
	/**
     * 修改地区信息
     * @param AreaBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateArea(AreaBean ob,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return AreaManager.updateArea(ob,stl);
		}else
			return false;
	}
	/**
	 * 删除地区信息
	 * @param area_id
	 * @param request
	 * @return
	 */
	public static boolean deleteArea(String area_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(null != stl)
		{
		    return AreaManager.deleteArea(area_id,stl);
		}else{
		    return false;
		}
	}
}
