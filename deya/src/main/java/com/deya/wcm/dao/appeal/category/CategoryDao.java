package com.deya.wcm.dao.appeal.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.category.CategoryBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.org.role.RoleOptDAO;
import com.deya.wcm.db.DBManager;


public class CategoryDao {
	
	/**
     * 得到所有诉求分类列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<CategoryBean> getAllApp_categroyList()
	{
		return DBManager.queryFList("getAllApp_categroyList", "");
	}
	
	/**
     * 根据诉求分类ID得到对象
     * @param String cat_id
     * @return app_categoryBean
     * */
	public static CategoryBean getapp_categoryBean(String cat_id)
	{
		return (CategoryBean)DBManager.queryFObj("getApp_categoryBean", cat_id);
	}
	
	/**
     * 添加诉求分类信息
     * @param CategoryBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertApp_categroy(CategoryBean ob,SettingLogsBean stl)
	{	
		ob.setCat_position(ob.getCat_position()+ ob.getCat_id()+ "$");
		ob.setCat_id(ob.getCat_id());
		
		if(DBManager.insert("insert_app_categroy", ob))
		{
			PublicTableDAO.insertSettingLogs("添加","诉求分类",ob.getCat_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 修改诉求分类信息
     * @param CategoryBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateApp_categroy(CategoryBean ob,SettingLogsBean stl)
	{
		if(DBManager.update("update_app_categroy", ob))
		{
			PublicTableDAO.insertSettingLogs("添加","修改",ob.getCat_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 保存诉求分类排序
     * @param String cat_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public static boolean saveApp_categroySort(String cat_id,SettingLogsBean stl)
	{
		if(cat_id != null && !"".equals(cat_id))
		{
			try{
				Map<String, Comparable> m = new HashMap<String, Comparable>();
				String[] tempA = cat_id.split(",");
				for(int i=0;i<tempA.length;i++)
				{
					m.put("sort_id", (i+1));
					m.put("cat_id", tempA[i]);
					DBManager.update("update_cat_id_sort", m);
				}
				PublicTableDAO.insertSettingLogs("保存排序","菜单",cat_id,stl);
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
     * 移动诉求分类
     * @param String parent_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean moveApp_categroy(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("move_app_categroy", m))
		{			
			PublicTableDAO.insertSettingLogs("移动","诉求分类",m.get("cat_id"),stl);
			return true;
		}
		else
			return false;
	}
	
	
     /* 删除诉求分类信息
     * @param String cat_id
     * @param SettingLogsBean stl
     * @return boolean
     */
	public static boolean deleteApp_categroy(String cat_id,SettingLogsBean stl)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("cat_id", cat_id);
		if(DBManager.delete("delete_app_categroy", m))
		{
			//删除诉求分类角色关联
			//RoleOptDAO.deleteOptReleRoleByOptID(cat_id);
			PublicTableDAO.insertSettingLogs("删除","诉求分类",cat_id,stl);
			return true;
		}else
			return false;
	}
	public static void main(String args[]){
		
		getAllApp_categroyList();
	}
}