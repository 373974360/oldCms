package com.deya.wcm.dao.system.ware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.ware.WareBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.server.ServerManager;

public class WareDAO {
	/**
	 * 根据站点ID得到标签，用于克隆站点
	 * @param String site_id	
	 * @return	List
	 */
	@SuppressWarnings("unchecked")
	public static List<WareBean> getWareListBySiteID(String site_id)
	{
		return DBManager.queryFList("getWareListBySiteID", site_id);
	}
	
	/**
	 * 得到需要进行更新任务的列表
	 * @param String current_time	
	 * @return	List
	 */
	@SuppressWarnings("unchecked")
	public static List<WareBean> getTimerWareList(String current_time)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("current_time", current_time);
		String ip = ServerManager.LOCAL_IP;
		if(ip != null && !"".equals(ip) && !"127.0.0.1".equals(ip))
			m.put("server_ip", ServerManager.LOCAL_IP);
		return DBManager.queryFList("getTimerWareList", m);
	}
	
	/**
	 * 取得全部信息标签列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<WareBean> getAllWareList()
	{
		return DBManager.queryFList("getAllWareList", "");
	}
	
	public static WareBean getWareByID(String id)
	{
		return (WareBean)DBManager.queryFObj("getWareByID", id);
	}
	
	public static WareBean getWareBeanByWareID(Map<String,String> m)
	{
		return (WareBean)DBManager.queryFObj("getWareBeanByWareID", m);
	}
	
	/**
	 * 克隆信息标签
	 * @param wb	信息标签对象
	 * @param stl	
	 * @return	true 成功| false 失败
	 */
	public static boolean cloneWare(WareBean wb)
	{			
		return DBManager.insert("insertWare", wb);
	}
	
	/**
	 * 插入信息标签
	 * @param wb	信息标签对象
	 * @param stl	
	 * @return	true 成功| false 失败
	 */
	public static boolean insertWare(WareBean wb, SettingLogsBean stl)
	{		
		if(DBManager.insert("insertWare", wb))
		{
			PublicTableDAO.insertSettingLogs("添加", "信息标签", wb.getId(), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改信息标签对象
	 * @param wb	信息标签对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateWare(WareBean wb, SettingLogsBean stl)
	{
		if(DBManager.update("updateWare", wb))
		{
			PublicTableDAO.insertSettingLogs("修改", "信息标签", wb.getId(), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改信息标签内容
	 * @param wb	信息标签对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateWareContent(WareBean wb, SettingLogsBean stl)
	{
		if(DBManager.update("updateWareContent", wb))
		{
			PublicTableDAO.insertSettingLogs("修改", "信息标签内容", wb.getId(), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改手动标签信息行数
	 * @param Map m
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateWareInfoNum(Map<String,String> m)
	{
		return DBManager.update("update_ware_infoNum", m);
	}
	
	/**
	 * 修改信息标签页面更新时间
	 * @param wb	信息标签对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateWareTime(Map<String,String> m)
	{
		return DBManager.update("updateWareTime", m);
	}

	
	/**
	 * 保存信息标签排序
	 * @param wb	信息标签对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean savaWareSort(WareBean wb, SettingLogsBean stl)
	{
		if(DBManager.update("saveWareSort", wb))
		{
			PublicTableDAO.insertSettingLogs("修改", "信息标签排序", wb.getId(), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 通过ID删除信息标签
	 * @param mp	
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteWare(Map<String, String> mp, SettingLogsBean stl)
	{
		if(DBManager.delete("deleteWare", mp))
		{
			PublicTableDAO.insertSettingLogs("删除", "信息标签", mp.get("ids"), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 通过信息标签分类ID删除信息标签
	 * @param mp
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteWareByWcatIDS(Map<String, String> mp, SettingLogsBean stl)
	{
		if(DBManager.delete("deleteWareByWcatIDS", mp))
		{
			PublicTableDAO.insertSettingLogs("删除", "信息标签", mp.get("wcat_ids"), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean moveWareToOtherCategory(Map<String, String> mp, SettingLogsBean stl){
		if(DBManager.update("moveWareToOtherCategory", mp))
		{
			PublicTableDAO.insertSettingLogs("转移", "信息标签", mp.get("ware_ids"), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void main(String arg[])
	{
		//Map<String, String> mp = new HashMap<String, String>();
		//mp.put("", value)
//		WareBean wb = new WareBean();
//		wb.setId("1");
//		wb.setSort_id(3);
//		boolean flg = savaWareSort(wb, new SettingLogsBean());
		
		System.out.println(getTimerWareList("2011-05,21 00:00:00"));
	}

}
