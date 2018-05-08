package com.deya.wcm.dao.zwgk.node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.node.GKNodeCategory;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  公开节点分类数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 公开节点分类数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class GKNodeCategoryDAO {
	/**
	 * 得到所有节点分类列表
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<GKNodeCategory> getAllNodeCategoryList()
	{
		return DBManager.queryFList("getAllNodeCategoryList", "");
	}
	
	/**
	 * 根据分类ID得到分类对象
	 * @param id 分类ID
	 * @return GKNodeCategory
	 */
	public static GKNodeCategory getNodeCategoryBean(int id)
	{
		return (GKNodeCategory)DBManager.queryFObj("getNodeCategoryBean", id);
	}
	
	/**
	 * 插入分类节点
	 * @param GKNodeCategory gnc
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertGKNodeCategory(GKNodeCategory gnc,SettingLogsBean stl)
	{
		if(DBManager.insert("insert_node_category", gnc))
		{
			PublicTableDAO.insertSettingLogs("添加", "公开节点分类", gnc.getNodcat_id()+"", stl);
			return true;
		}else
			return false;
	}	
	
	/**
	 * 修改分类节点
	 * @param GKNodeCategory gnc
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateGKNodeCategory(GKNodeCategory gnc,SettingLogsBean stl)
	{
		if(DBManager.update("update_node_category", gnc))
		{
			PublicTableDAO.insertSettingLogs("修改", "公开节点分类", gnc.getNodcat_id()+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 移动节点分类
	 * @param Map<String,String> m
	 * @return boolean
	 */
	public static boolean moveGKNodeCategory(Map<String,String> m)
	{		
		if(DBManager.update("move_node_category", m))
			return true;
		else
			return false;
	}
	
	/**
	 * 保存分类节点排序
	 * @param String nodcat_ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean sortGKNodeCategory(String nodcat_ids,SettingLogsBean stl)
	{
		if(nodcat_ids != null && !"".equals(nodcat_ids))
		{
			try{
				Map<String,String> m = new HashMap<String,String>();
				String[] tempA = nodcat_ids.split(",");
				for(int i=0;i<tempA.length;i++)
				{
					m.put("sort_id", (i+1)+"");
					m.put("nodcat_id", tempA[i]);
					DBManager.update("sort_node_category", m);
				}
				PublicTableDAO.insertSettingLogs("保存排序", "公开节点分类", nodcat_ids+"", stl);
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return true;		
	}
	
	/**
	 * 删除分类节点
	 * @param String nodcat_ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean deleteGKNodeCategory(String nodcat_ids,SettingLogsBean stl)
	{
		Map<String,String> m  = new HashMap<String,String>();
		m.put("nodcat_ids", nodcat_ids);
		if(DBManager.delete("delete_node_category", m))
		{
			PublicTableDAO.insertSettingLogs("删除", "公开节点分类", nodcat_ids+"", stl);
			return true;
		}else
			return false;
	}
}
