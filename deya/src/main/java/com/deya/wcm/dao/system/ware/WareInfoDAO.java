package com.deya.wcm.dao.system.ware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.ware.WareBean;
import com.deya.wcm.bean.system.ware.WareInfoBean;
import com.deya.wcm.bean.system.ware.WareInfoRef;
import com.deya.wcm.bean.system.ware.WareInfos;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  标签与信息关联数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 标签与信息关联数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class WareInfoDAO {
	/*********************** 信息推荐处理　开始 ********************************/
	/**
	 * 根据标签ID，应用ID，站点ID得到它下面的推荐信息总数
	 * @param Map m
	 * @return	List
	 */
	public static String getWareInfoRefCount(Map<String,String> m)
	{
		return DBManager.getString("getWareInfoRefCount", m);
	}
	
	/**
	 * 根据标签ID，应用ID，站点ID得到它下面的推荐信息
	 * @param Map m
	 * @return	List
	 */
	@SuppressWarnings("unchecked")
	public static List<InfoBean> getWareInfoRefList(Map<String,String> m)
	{
		return DBManager.queryFList("getWareInfoRefList", m);
	}
	
	/**
	 * 根据信息ID，站点，应用ID得到标签列表
	 * @param Map m
	 * @return	List
	 */
	@SuppressWarnings("unchecked")
	public static List<WareBean> getWareListByRefInfo(Map<String,String> m)
	{
		return DBManager.queryFList("getWareListByRefInfo", m);
	}
	
	/**
	 * 添加推荐信息
	 * @param WareInfoRef wir	
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean insertWareInfoRef(WareInfoRef wir)
	{		
		wir.setRef_id(PublicTableDAO.getIDByTableName(PublicTableDAO.WARE_INFO_REF_TABLE_NAME));
		return DBManager.insert("insert_ware_info_ref", wir);		
	}
	
	/**
	 * 删除推荐信息
	 * @param Map m
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean deleteWareInfoRef(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_ware_info_ref", m))
		{
			if(m.containsKey("info_ids"))
				PublicTableDAO.insertSettingLogs("删除", "推荐信息 信息", m.get("info_ids")+"", stl);
			else
				PublicTableDAO.insertSettingLogs("删除", "推荐信息 标签", m.get("ware_ids")+"", stl);
			return true;
		}else
			return false;
	}
	/*********************** 信息推荐处理　 结束 ********************************/
	
	/*********************** 手动标签信息处理　 开始 ********************************/
	/**
	 * 根据标签ID得到手动标签列表
	 * @param Map<String,String> m
	 * @return	boolean
	 */
	@SuppressWarnings("unchecked")
	public static List<WareInfoBean> getWareInfoList(Map<String,String> m)
	{
		return DBManager.queryFList("getWareInfoList", m);
	}
	
	/**
	 * 根据信息ID得到标签关联的信息内容
	 * @param int id
	 * @return	WareInfos
	 */
	public static WareInfos getWareInfosBean(int id)
	{
		return (WareInfos)DBManager.queryFObj("getWareInfosBean", id);
	}
	
	
	
	/**
	 * 添加手动标签行数
	 * @param WareInfoBean wib
	 * @return	boolean
	 */
	public static boolean insertWareInfo(WareInfoBean wib)
	{
		
		return DBManager.insert("insert_ware_info", wib);
	}
	
	/**
	 * 保存标签行排序
	 * @param String winfo_ids
	 * @return	boolean
	 */
	public static boolean sortWareInfo(String winfo_ids)
	{
		if(winfo_ids != null && !"".equals(winfo_ids))
		{
			String[] tempA = winfo_ids.split(",");
			Map<String,String> m = new HashMap<String,String>();
			for(int i=0;i<tempA.length;i++)
			{
				m.put("winfo_id", tempA[i]);
				m.put("sort_id", (i+1)+"");
				DBManager.update("sort_ware_info", m);
			}
		}
		return true;
	}
	
	/**
	 * 根据行号ID删除标签关联
	 * @param String winfo_id
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean deleteWareInfoByWInfoID(String winfo_id,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_ware_info_byWinfoId", winfo_id))
		{
			DBManager.delete("delete_ware_infos_byWinfoId", winfo_id);
			return true;
		}
		else 
			return false;
	}
	
	/**
	 * 根据标签ID删除标签关联
	 * @param Map<String,String> m
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean deleteWareInfoByWareID(Map<String,String> m,SettingLogsBean stl)
	{
		return DBManager.delete("delete_ware_info_byWareId", m);
	}
	
	/**
	 * 得到手动标签行ID	
	 * @return	int
	 */
	public static int getNewWareInfosID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.WARE_INFOS_TABLE_NAME);
	}
	
	/**
	 * 插入标签内容
	 * @param String winfo_id
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean insertWareInfos(WareInfos wif,SettingLogsBean stl)
	{	
		return DBManager.insert("insert_ware_infos", wif);
	}
		
	/**
	 * 修改标签内容
	 * @param String winfo_id
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean updateWareInfos(WareInfos wif,SettingLogsBean stl)
	{
		return DBManager.update("update_ware_infos", wif);
	}
	
	/**
	 * 保存标签信息排序
	 * @param String ids
	 * @return	boolean
	 */
	public static boolean sortWInfos(String ids)
	{
		if(ids != null && !"".equals(ids))
		{
			String[] tempA = ids.split(",");
			Map<String,String> m = new HashMap<String,String>();
			for(int i=0;i<tempA.length;i++)
			{
				m.put("id", tempA[i]);
				m.put("sort_id", (i+1)+"");
				DBManager.update("sort_w_infos", m);
			}
		}
		return true;
	}
	
	/**
	 * 根据ID删除标签关联信息
	 * @param int id
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean deleteWareInfosByID(int id,SettingLogsBean stl)
	{
		return DBManager.delete("delete_ware_infos_byId", id);
	}
	
	/**
	 * 根据标签ID删除标签关联
	 * @param Map<String,String> m
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean deleteWareInfosByWareID(Map<String,String> m,SettingLogsBean stl)
	{
		return DBManager.delete("delete_ware_infos_byWareId", m);
	}
	/*********************** 手动标签信息处理　 结束 ********************************/
}
