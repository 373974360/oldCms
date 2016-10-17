package com.deya.wcm.dao.appeal.lang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.lang.CommonLangBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

public class CommonLangDAO {
	
	/**
	 * 取得全部的常用语信息
	 * @return 常用语信息列表	
	 */
	public static List<CommonLangBean> getAllCommonLangList()
	{
		return DBManager.queryFList("getAllCommonList", "");
	}
	
	/**
	 * 分页取得全部信息
	 * @return	常用语信息列表
	 */
	public static List<CommonLangBean> getAllCommonLangListByDB(Map<String, String> mp)
	{
		return DBManager.queryFList("getAllCommonLangListByDB", mp);
	}
	
	/**
	 * 通过ID取得常用语信息
	 * @param id	常用语ID
	 * @return	常用语信息
	 */
	public static CommonLangBean getCommonLangByID(String id)
	{
		return (CommonLangBean)DBManager.queryFObj("getCommonLangByID", id);
	}
	
	/**
	 * 通过类型取得常用语信息列表
	 * @param mp  ph_type = 类型
	 * @return	常用语信息列表
	 */
	public static List<CommonLangBean> getCommonLangList(Map<String, String> mp)
	{
		return DBManager.queryFList("getCommonLangList", mp);
	}
	
	/**
	 * 取得常用语信息条数
	 * @param mp	
	 * @return	得到的常用语条数
	 */
	public static String getCommonLangCnt(Map<String, String> mp)
	{
		return (String)DBManager.queryFObj("getCommonLangListCnt", mp);
	}
	
	/**
	 * 添加常用语信息
	 * @param clb	常用语信息
	 * @param stl	
	 * @return	true 成功| false 失败
	 */
	public static boolean insertCommonLang(CommonLangBean clb, SettingLogsBean stl)
	{
		String id = PublicTableDAO.getIDByTableName(PublicTableDAO.APPEAL_PHRASAL_TABLE_NAME)+"";
		clb.setPh_id(id);
		if(DBManager.insert("insertCommonLang", clb))
		{
			PublicTableDAO.insertSettingLogs("添加", "常用语", id, stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改常用语信息
	 * @param clb
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateCommonLang(CommonLangBean clb, SettingLogsBean stl)
	{
		if(DBManager.update("updateCommonLang", clb))
		{
			PublicTableDAO.insertSettingLogs("修改", "常用语", clb.getPh_id(), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改常用语排序
	 * @param clb
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateCommonLangSort(CommonLangBean clb, SettingLogsBean stl)
	{
		if(DBManager.update("updateCommonLangSort", clb))
		{
			PublicTableDAO.insertSettingLogs("修改", "常用语", clb.getPh_id(), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean deleteCommonLang(Map<String, String> mp, SettingLogsBean stl)
	{
		if(DBManager.delete("deleteCommonLang", mp))
		{
			PublicTableDAO.insertSettingLogs("删除", "常用语", mp.get("ph_id"), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void main(String arg[])
	{
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("ph_id", "1");
		mp.put("ph_type", "2");
		mp.put("sort_id", "888");
		
		//boolean flg = updateCommonLang(mp, new SettingLogsBean());
		System.out.println("111");
	}
}
