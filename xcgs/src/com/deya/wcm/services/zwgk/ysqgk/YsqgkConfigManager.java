package com.deya.wcm.services.zwgk.ysqgk;

import java.util.List;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.ysqgk.YsqgkConfigBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.zwgk.ysqgk.YsqgkConfigDAO;
import com.deya.wcm.db.DBManager;

public class YsqgkConfigManager {
	private static YsqgkConfigBean ykcb = null;
	
	static{
		getYsqgkConfigBeanForDB();
	}
	
	/**
     * 插入依申请公开配置对象
     * @param 
     * @return boolean
     * */
	public static boolean insertYsqgkConfig(YsqgkConfigBean ysqgk,SettingLogsBean stl)
	{
		if(deleteYsqgkConfig(stl)){
			int ysq_id = PublicTableDAO.getIDByTableName(PublicTableDAO.YSQGK_CONFIG_TABLE_NAME );
			ysqgk.setId(ysq_id);
			
			if(YsqgkConfigDAO.insertYsqgkConfig(ysqgk,stl))
			{
				ykcb = ysqgk;
				return true;
			}else
				return false;
		}
		return true;
	}
	
	/**
     *  删除依申请公开配置对象
     * @param 
     * @return boolean
     * */
	public static boolean deleteYsqgkConfig(SettingLogsBean stl)
	{
		if(YsqgkConfigDAO.deleteYsqgkConfig(stl))
		{
			ykcb = null;
			return true;
		}else
			return false;
	}
	/**
     * 得到依申请公开配置对象
     * @param 
     * @return YsqgkConfigBean
     * */
	public static YsqgkConfigBean getYsqgkConfigBean()
	{
		return ykcb;
	}
	
	public static void getYsqgkConfigBeanForDB()
	{
		ykcb = YsqgkConfigDAO.getYsqgkConfigBean();
	}
}
