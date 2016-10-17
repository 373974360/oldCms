package com.deya.wcm.dao.org.desktop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.org.desktop.DeskTopBean;
import com.deya.wcm.db.DBManager;

/**
 *  用户桌面设置表数据处理类. sql在siteUser.xml中
 * <p>Title: CicroDate</p>
 * <p>Description: 用户桌面设置表数据处理类</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class DesktopDAO {
	/**
     * 得到所有配置信息
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<DeskTopBean> getUserDesktopList()
	{
		return DBManager.queryFList("getUserDesktopList", "");
	}
	
	/**
     * 插入配置信息
     * @param  DeskTopBean dtb
     * @return boolean
     * */
	public static boolean insertUserDesktop(DeskTopBean dtb)
	{
		return DBManager.insert("insert_user_desktop", dtb);
	}
	
	/**
     * 删除配置信息
     * @param  user_ids
     * @return boolean
     * */
	public static boolean deleteUserDesktop(String user_ids)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("user_ids", user_ids);
		return DBManager.delete("delete_user_desktop", m);
	}
}
