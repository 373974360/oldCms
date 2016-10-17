package com.deya.wcm.dao.system.ware;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.ware.WareCategoryBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

public class WareCategoryDAO {
	
	/**
	 * 根据站点ID得到标签分类，用于克隆站点
	 * @param String site_id
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<WareCategoryBean> getWareCataListBySiteID(String site_id)
	{
		return DBManager.queryFList("getWareCataListBySiteID", site_id);
	}
	
	/**
	 * 取得信息标签分类列表
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<WareCategoryBean> getWareCategoryList()
	{
		return DBManager.queryFList("getWareCataList", "");
	}
	
	/**
	 * 克隆信息标签分类
	 * @param wcb	信息标签分类对象
	 * @return	true 成功| false 失败
	 */
	public static boolean cloneWareCate(WareCategoryBean wcb)
	{
		String id = PublicTableDAO.getIDByTableName(PublicTableDAO.WARE_CATEGORY_TABLE_NAME)+"";
		wcb.setId(id);
		return DBManager.insert("insertWareCategory", wcb);
	}
	
	/**
	 * 插入信息标签分类
	 * @param wcb	信息标签分类对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean insertWareCate(WareCategoryBean wcb, SettingLogsBean stl)
	{
		String id = PublicTableDAO.getIDByTableName(PublicTableDAO.WARE_CATEGORY_TABLE_NAME)+"";
		wcb.setId(id);
		wcb.setWcat_id(id);
		wcb.setWcat_position(wcb.getWcat_position()+"$"+id+"$");
		if(DBManager.insert("insertWareCategory", wcb))
		{
			PublicTableDAO.insertSettingLogs("添加", "信息标签分类", id, stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改信息标签分类
	 * @param wcb	信息标签分类对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateWareCate(WareCategoryBean wcb, SettingLogsBean stl)
	{
		if(DBManager.update("updateWareCategory", wcb))
		{
			PublicTableDAO.insertSettingLogs("修改", "信息标签分类", wcb.getId(), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 保存排序
	 * @param wcb	信息标签分类对象
	 * @param stl	
	 * @return	true 成功| false 失败
	 */
	public static boolean saveWareCateSort(WareCategoryBean wcb, SettingLogsBean stl)
	{
		if(DBManager.update("saveWareCateSort", wcb))
		{
			PublicTableDAO.insertSettingLogs("修改", "信息标签分类排序", wcb.getId(), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 删除信息标签分类
	 * @param mp	
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteWareCate(Map<String, String> mp, SettingLogsBean stl)
	{
		if(DBManager.update("deleteWareCategory", mp))
		{
			PublicTableDAO.insertSettingLogs("删除", "信息标签分类", mp.get("id"), stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void main(String aeg[])
	{
		List<WareCategoryBean> lt = getWareCategoryList();
		System.out.println(lt.size());
	}

}
