package com.deya.wcm.dao.zwgk.node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.node.GKNodeBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  公开节点数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 公开节点数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class GKNodeDAO {
	/**
	 * 得到所有节点列表
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<GKNodeBean> getAllGKNodeList()
	{
		return DBManager.queryFList("getAllGKNodeList", "");
	}
	
	/**
	 * 根据ID得到节点对象
	 * @param int id 
	 * @return GKNodeCategory
	 */
	public static GKNodeBean getGKNodeBeanByID(int id)
	{
		return (GKNodeBean)DBManager.queryFObj("getGKNodeBeanByID", id);
	}
	
	/**
	 * 根据node_id得到节点对象
	 * @param String node_id
	 * @return GKNodeCategory
	 */
	public static GKNodeBean getGKNodeBeanByNodeID(String node_id)
	{
		return (GKNodeBean)DBManager.queryFObj("getGKNodeBeanByNodeID", node_id);
	}
	
	/**
	 * 插入节点
	 * @param GKNodeBean gbn
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertGKNode(GKNodeBean gbn,SettingLogsBean stl)
	{
		if(DBManager.insert("insert_gk_node", gbn))
		{
			PublicTableDAO.insertSettingLogs("添加", "公开节点", gbn.getNode_id()+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改节点
	 * @param GKNodeBean gbn
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateGKNode(GKNodeBean gbn,SettingLogsBean stl)
	{
		if(DBManager.update("update_gk_node", gbn))
		{
			PublicTableDAO.insertSettingLogs("修改", "公开节点", gbn.getNode_id()+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改节点(用于公开节点中的资料信息修改)
	 * @param GKNodeBean gbn
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateGKNodeSimple(GKNodeBean gbn,SettingLogsBean stl)
	{
		if(DBManager.update("update_gk_node_simple", gbn))
		{
			PublicTableDAO.insertSettingLogs("修改", "公开节点", gbn.getNode_id()+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改节点状态
	 * @param String ids
	 * @param int node_status
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateGKNodeStatus(String ids,int node_status,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("node_status", node_status+"");
		if(DBManager.update("update_gk_node_status", m))
		{
			PublicTableDAO.insertSettingLogs("修改", "公开节点状态", ids+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 移动节点
	 * @param String ids
	 * @param int nodcat_id
	 * @return boolean
	 */
	public static boolean moveGKNode(String ids,int nodcat_id,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("nodcat_id", nodcat_id+"");
		if(DBManager.update("move_gk_node", m))
		{
			PublicTableDAO.insertSettingLogs("移动", "公开节点", ids+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 保存节点排序
	 * @param String ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean sortGKNode(String ids,SettingLogsBean stl)
	{
		if(ids != null && !"".equals(ids))
		{
			try{
				Map<String,String> m = new HashMap<String,String>();
				String[] tempA = ids.split(",");
				for(int i=0;i<tempA.length;i++)
				{
					m.put("sort_id", (i+1)+"");
					m.put("id", tempA[i]);
					DBManager.update("sort_gk_node", m);
				}
				PublicTableDAO.insertSettingLogs("保存排序", "公开节点", ids+"", stl);
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
	 * 删除节点
	 * @param String ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean deleteGKNode(String ids,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("node_status", "-1");
		if(DBManager.update("update_gk_node_status", m))
		{
			PublicTableDAO.insertSettingLogs("删除", "公开节点", ids+"", stl);
			return true;
		}else
			return false;
	}
}
