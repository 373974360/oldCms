package com.deya.wcm.dao.org.operate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.operate.MenuBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  菜单管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 菜单管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class MenuDAO {
	/**
     * 得到所有菜单列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getAllMenuList()
	{
		return DBManager.queryFList("getAllMenuList", "");
	}
	
	/**
     * 根据菜单ID得到对象
     * @param String menu_id
     * @return MenuBean
     * */
	public static MenuBean getMenuBean(int menu_id)
	{
		return (MenuBean)DBManager.queryFObj("getMenuBean", menu_id+"");
	}
	
	/**
     * 插入菜单信息
     * @param MenuBean mb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertMenu(MenuBean mb,SettingLogsBean stl)
	{		
		mb.setMenu_position(mb.getMenu_position() + mb.getMenu_id() + "$");
		if(DBManager.insert("insert_menu", mb))
		{
			PublicTableDAO.insertSettingLogs("添加","菜单",mb.getMenu_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 修改菜单信息
     * @param MenuBean mb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateMenu(MenuBean mb,SettingLogsBean stl)
	{
		if(DBManager.update("update_menu", mb))
		{
			PublicTableDAO.insertSettingLogs("修改","菜单",mb.getMenu_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 保存菜单排序
     * @param String menu_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public static boolean saveMenuSort(String menu_id,SettingLogsBean stl)
	{
		if(menu_id != null && !"".equals(menu_id))
		{
			try{
				Map<String, Comparable> m = new HashMap<String, Comparable>();
				String[] tempA = menu_id.split(",");
				for(int i=0;i<tempA.length;i++)
				{
					m.put("menu_sort", (i+1));
					m.put("menu_id", tempA[i]);
					DBManager.update("update_menu_sort", m);
				}
				PublicTableDAO.insertSettingLogs("保存排序","菜单",menu_id,stl);
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else
			return true;
	}
	
	/**
     * 移动菜单
     * @param String parent_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean moveMenu(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("move_menu", m))
		{			
			PublicTableDAO.insertSettingLogs("移动","菜单",m.get("menu_id"),stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 删除菜单信息
     * @param String menu_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteMenu(String menu_id,SettingLogsBean stl)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("menu_id", menu_id);
		if(DBManager.delete("delete_menu", m))
		{
			PublicTableDAO.insertSettingLogs("删除","菜单",menu_id+"",stl);
			return true;
		}else
			return false;
	}
}
