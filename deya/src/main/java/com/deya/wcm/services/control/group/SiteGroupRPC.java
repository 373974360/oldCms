package com.deya.wcm.services.control.group;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.control.SiteGroupBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.control.SiteGroupDAO;
import com.deya.wcm.services.Log.LogManager;

/**
 *  网站群管理逻辑处理类  js调用.
 * <p>Title: CicroDate</p>
 * <p>Description: 网站群管理逻辑处理类 js调用</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class SiteGroupRPC {

	/**
	 * 得到本站群的站群配置信息
	 * @param String id
	 * @return List
	 */
	public static SiteGroupBean getSGroupRoot(String sgroup_id)
	{
		return SiteGroupManager.getSGroupChildListByID(sgroup_id).get(0);
	}
	
	/**
     * 修改网站群信息
     * @param SiteGroupBean sgb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateSiteGroup(SiteGroupBean sgb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{ 
			if(SiteGroupManager.updateSiteGroup(sgb, stl))
			{
				return true;
			}else
				return false;
		}else{
			return false;
		}
	}
	
	/**
     * 插入网站群信息
     * @param SiteGroupBean sgb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertSiteGroup(SiteGroupBean sgb,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{ 
			if(SiteGroupManager.insertSiteGroup(sgb, stl))
			{
				return true;
			}else
				return false;
		}else{
			return false;
		}
	}
	
	/**
     * 得到所有站群信息,并组织为json数据
     * @param
     * @return String
     * */
	public static String getGroupTreeJsonStr(){
		return SiteGroupManager.getGroupTreeJsonStr();
	}
	
	/**
	 * 根据节点ID得到该节点下的站群列表信息(得到子节点列表)
	 * @param String id
	 * @return List
	 */
	public static List<SiteGroupBean> getSGroupChildListByID(String sgroup_id)
	{
		return SiteGroupManager.getSGroupChildListByID(sgroup_id);
	}
	
	/**
     * 删除网站群信息
     * @param String sgroup_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteSiteGroup(String sgroup_ids,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{ 
			if(SiteGroupManager.deleteSiteGroup(sgroup_ids, stl))
			{
				return true;
			}else
				return false;
		}else{
			return false;
		}
	}
	
	/**
	 * 根据节点ID得到该节点下排序的值
	 * @param String id
	 * @return int
	 */
	public static int getSGroupSortByID(String sgroup_id){
		return SiteGroupManager.getSGroupSortByID(sgroup_id);
	}
	
	
	/**
     * 保存网站群排序
     * @param String sgroup_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean saveSGroupSort(String sgroup_ids,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{ 
			if(SiteGroupManager.saveSGroupSort(sgroup_ids, stl))
			{
				return true;
			}else
				return false;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 根据ID得到站群信息
	 * 
	 * @param String 
	 *            id
	 * @return SiteGroupBean
	 */
	public static SiteGroupBean getSGroupBeanByID(String sgroup_id)
	{
		return SiteGroupManager.getSGroupBeanByID(sgroup_id);
	}
	
	
}
