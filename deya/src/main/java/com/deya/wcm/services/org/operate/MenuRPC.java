package com.deya.wcm.services.org.operate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.operate.MenuBean;
import com.deya.wcm.services.Log.LogManager;

public class MenuRPC {
	/**
     * 得到所有菜单,并组织为json数据
     * @param
     * @return String
     * */
	public static String getMenuTreeJsonStr()
	{
		return MenuManager.getMenuTreeJsonStr();
	}
	
	/**
     * 得到菜单ID,用于添加页面
     * @param
     * @return String
     * */
	public static int getMenuID()
	{
		return MenuManager.getMenuID();
	}
	
	/**
     * 根据菜单ID得到对象
     * @param String menu_id
     * @return MenuBean
     * */
	public static MenuBean getMenuBean(int menu_id)
	{
		return MenuManager.getMenuBean(menu_id);
	}
	
	/**
	 * 根据菜单节点ID得到此节点下的子节点个数,用于页面管理
	 * String menu_id
	 * @param
	 * @return String
	 */
	public static String getChildMenuCount(String menu_id)
	{
		return MenuManager.getChildMenuCount(menu_id);
	}
	
	/**
	 * 根据菜单节点ID得到此节点下的子节点列表deep+1,
	 * String menu_id
	 * @param
	 * @return List
	 */
	public static List<MenuBean> getChildMenuList(String menu_id)
	{
		return MenuManager.getChildMenuList(menu_id);
	}
	
	/**
     * 插入菜单信息
     * @param MenuBean mb
     * @param HttpServletRequest request
     * @return boolean
     * */
	public static boolean insertMenu(MenuBean mb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return MenuManager.insertMenu(mb,stl);
		}else
			return false;
	}
	
	/**
     * 修改菜单信息
     * @param MenuBean mb
     * @param HttpServletRequest request
     * @return boolean
     * */
	public static boolean updateMenu(MenuBean mb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return MenuManager.updateMenu(mb,stl);
		}else
			return false;
	}
	
	/**
     * 保存菜单排序
     * @param String menu_id
     * @param HttpServletRequest request
     * @return boolean
     * */
	public static boolean saveMenuSort(String menu_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return MenuManager.saveMenuSort(menu_id,stl);
		}else
			return false;
	}
	
	/**
     * 删除菜单信息
     * @param String menu_id
     * @param HttpServletRequest request
     * @return boolean
     * */
	public static boolean deleteMenu(String menu_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return MenuManager.deleteMenu(menu_id,stl);
		}else
			return false;
	}
	
	/**
     * 移动菜单
     * @param String parent_id
     * @param String menu_ids
     * @return boolean
     * */
	public static boolean moveMenu(String parent_id,String menu_ids,HttpServletRequest request) {
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return MenuManager.moveMenu(parent_id,menu_ids,stl);
		}else
			return false;
	}
	
	public static void main(String arg[])
	{
		System.out.println(getMenuTreeJsonStr());
	}

}
