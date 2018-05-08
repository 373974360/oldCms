package com.deya.wcm.dao.zwgk.ysqgk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.ysqgk.YsqgkBean;
import com.deya.wcm.bean.zwgk.ysqgk.YsqgkListBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;


/**
 *  依申请公开信息类
 * <p>Title: CicroDate</p>
 * <p>Description: 依申请公开类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhangqiang
 * @version 1.0
 * * 
 */

public class YsqgkInfoDAO {
	
	/**
	 * 得到依申请公开信息列表
	 * @param Map m 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<YsqgkListBean> getYsqgkLists(Map<String,String> m)
	{
		return DBManager.queryFList("getYsqgkLists", m);
	}
	
	/**
	 * 得到依申请公开信息总数
	 * @param Map m 
	 * @return List
	 */
	public static int getYsqgkListsCount(Map<String, String> map)
	{
		return Integer.parseInt(DBManager.getString("getYsqgkListsCount", map));
	}

	/**
	 * 得到依申请公开信息对象
	 * @param String  ysq_id
	 * @return YsqgkBean
	 */
	public static YsqgkBean getYsqgkBean(String ysq_id)
	{
		return (YsqgkBean)DBManager.queryFObj("getYsqgkBean",ysq_id);
	}
	
	/**
	 * 得到依申请公开信息对象
	 * @param String  ysq_id
	 * @return YsqgkBean
	 */
	public static YsqgkBean getYsqgkBeanForQuery(Map<String,String> m)
	{
		return (YsqgkBean)DBManager.queryFObj("getYsqgkBeanForQuery",m);
	}

	/**
	 * 插入依申请公开信息对象
	 * @param YsqgkBean ysqgk
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertYsqgkInfo(YsqgkBean ysqgk,SettingLogsBean stl)
	{
		if(DBManager.insert("insert_ysqgk_info", ysqgk))
		{
			PublicTableDAO.insertSettingLogs("添加", "依申请公开信息","", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 处理依申请公开信息
	 * @param YsqgkBean ysqgk
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateStatus(Map<String, String> map,SettingLogsBean stl)
	{
		if(DBManager.update("deal_ysqgk_info", map))
		{
			PublicTableDAO.insertSettingLogs("处理", "依申请公开信息",map.get("ysq_id"), stl);
			return true;
		}else
			return false;
	}
	/**
	 * 修改依申请公开信息对象
	 * @param YsqgkBean ysqgk
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateYsqgkInfo(YsqgkBean ysqgk,SettingLogsBean stl)
	{
		if(DBManager.update("update_ysqgk_info", ysqgk))
		{
			PublicTableDAO.insertSettingLogs("修改", "依申请公开信息",ysqgk.getYsq_id()+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改依申请公开信息为删除状态
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean setDeleteState(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.delete("setDeleteState",m))
		{
			PublicTableDAO.insertSettingLogs("修改", "依申请公开信息为删除状态",m.get("ysq_id")+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 还原依申请公开信息
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean reBackInfos(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.delete("reBackInfos",m))
		{
			PublicTableDAO.insertSettingLogs("还原", "依申请公开信息",m.get("ysq_id")+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 删除依申请公开信息
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean deleteYsqgkInfo(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_ysqgk_info",m))
		{
			PublicTableDAO.insertSettingLogs("彻底删除", "依申请公开信息",m.get("ysq_id")+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 删除依申请公开回收站信息
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean clearDeleteYsqgkInfos(SettingLogsBean stl)
	{
		if(DBManager.delete("clear_hasdeleted_infos",""))
		{
			PublicTableDAO.insertSettingLogs("删除", "依申请公开回收站信息","", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 得到依申请总数，用于前台统计显示
	 * @param Map<String,String> m
	 * @return String
	 */
	public static String getYsqStatistics(Map<String,String> m)
	{
		return DBManager.getString("getYsqStatistics", m);
	}
}