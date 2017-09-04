package com.deya.wcm.dao.zwgk.ysqgk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.node.GKNodeBean;
import com.deya.wcm.bean.zwgk.ysqgk.YsqgkConfigBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;


/**

 *  依申请公开配置类.
 * <p>Title: CicroDate</p>
 * <p>Description: 依申请公开配置类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhangqiang
 * @version 1.0
 * * 
 */

public class YsqgkConfigDAO {

	/**
	 * 得到依申请公开配置对象
	 * @param int id 
	 * @return GKNodeCategory
	 */

	public static YsqgkConfigBean getYsqgkConfigBean()
	{
		return (YsqgkConfigBean)DBManager.queryFObj("getYsqgkConfigBean","");
	}
	public static YsqgkConfigBean getYsqgkConfigBean(String site_id)
	{
		Map<String,String> map = new HashMap<String,String>();
		map.put("site_id",site_id);
		return (YsqgkConfigBean)DBManager.queryFObj("getYsqgkConfigBean",map);
	}

	/**
	 * 插入依申请公开配置对象
	 * @param YsqgkConfigBean ysqgk
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertYsqgkConfig(YsqgkConfigBean ysqgk,SettingLogsBean stl)
	{
		if(DBManager.insert("insert_ysqgk_config", ysqgk))
		{
			PublicTableDAO.insertSettingLogs("添加", "依申请公开配置","", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 删除依申请公开配置
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean deleteYsqgkConfig(SettingLogsBean stl)
	{
		if(DBManager.delete("delete_ysqgk_config", ""))
		{
			PublicTableDAO.insertSettingLogs("删除", "依申请公开配置","", stl);
			return true;
		}else
			return false;
	}
	public static boolean deleteYsqgkConfig(String site_id)
	{
		Map<String,String> map = new HashMap<String,String>();
		map.put("site_id",site_id);
		if(DBManager.delete("delete_ysqgk_config", map))
		{
			return true;
		}else
			return false;
	}
}