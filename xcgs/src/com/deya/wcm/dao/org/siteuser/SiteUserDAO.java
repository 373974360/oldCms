package com.deya.wcm.dao.org.siteuser;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.siteuser.SiteUserBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  站点-用户表数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 站点-用户表数据处理类.</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author liqi
 * @version 1.0
 * * 
 */
public class SiteUserDAO {

	private static final String INSERT_OPERATE = "添加";
	private static final String UPDATE_OPERATE = "修改";
	private static final String DELETE_OPERATE = "删除";
	
	private static final String TABLE_NAME = "站点用户表";
	

	/**
	 * 得到所有站点用户信息列表
	 * @return	站点用户信息列表
	 */
	public static List<SiteUserBean> getAllSiteUserList()
	{
		return DBManager.queryFList("getAllSiteUserList", "");
	}
	
	/**
	 * 根据站点和应用ID得到人员列表
	 * @return	站点用户信息列表
	 */
	public static List<SiteUserBean> getSiteUserListForDB(Map<String,String> m )
	{
		return DBManager.queryFList("getSiteUserListForDB", m);
	}
	
	/**
	 * 根据站点和应用ID得到人员总数
	 * @return	站点用户总数
	 */
	public static String getSiteUserListCount(Map<String,String> m )
	{
		return DBManager.getString("getSiteUserListCount", m);
	}
	
	/**
	 * 插入站点用户信息
	 * @param sub	站点用户信息对象
	 * @param stl
	 * @return	true 成功|false  失败
	 */
	public static boolean insertSiteUser(SiteUserBean sub, SettingLogsBean stl)
	{
		if(DBManager.insert("insertSiteUser", sub))
		{
			PublicTableDAO.insertSettingLogs(INSERT_OPERATE, TABLE_NAME, sub.getUser_id(), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 删除站点用户信息
	 * @param sub	站点用户对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteSiteUser(Map<String, String> map, SettingLogsBean stl)
	{
		if(DBManager.delete("deleteSiteUser", map))
		{
			PublicTableDAO.insertSettingLogs(DELETE_OPERATE, TABLE_NAME, map.get("site_id"), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
}
