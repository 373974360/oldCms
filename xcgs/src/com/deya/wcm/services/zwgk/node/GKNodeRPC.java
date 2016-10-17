package com.deya.wcm.services.zwgk.node;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.node.GKNodeBean;
import com.deya.wcm.bean.zwgk.node.GKNodeCategory;
import com.deya.wcm.services.Log.LogManager;

public class GKNodeRPC {
	/********************* 分类管理 开始 **********************************/
	/**
	 * 得到节点分类ID
	 * @return int
	 */
	public static int getNewNodCatID()
	{
		return GKNodeCateManager.getNewNodCatID();
	}
	
	/**
     * 根据nodcat_id得公开节点的树
     * @param int nodcat_id
     * @return String
     * */
	public static String getGKNodeTreebyCateID(int nodcat_id)
	{
		return GKNodeCateManager.getGKNodeTreebyCateID(nodcat_id);
	}
	
	/**
     * 得到公开分类树字符串,从根节点开始
     * @return String
     * */
	public static String getGKNodeCategroyJSONROOTTreeStr()
	{
		return GKNodeCateManager.getGKNodeCategroyJSONROOTTreeStr();
	}
	
	/**
     * 得到公开分类树字符串
     * @return String
     * */
	public static String getGKNodeCategroyJSONTreeStr()
	{
		return GKNodeCateManager.getGKNodeCategroyJSONTreeStr();
	}
	
	/**
     * 根据ID得到它的子级列表(deep+1)
     * @param int nodcat_id
     * @return List
     * */
	public static List<GKNodeCategory> getChildCategoryList(int nodcat_id)
	{
		return GKNodeCateManager.getChildCategoryList(nodcat_id);
	}
	
	/**
	 * 根据分类ID得到分类对象
	 * @param id 分类ID
	 * @return GKNodeCategory
	 */
	public static GKNodeCategory getNodeCategoryBean(int id)
	{
		return GKNodeCateManager.getNodeCategoryBean(id);
	}
	
	/**
	 * 插入分类节点
	 * @param GKNodeCategory gnc
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertGKNodeCategory(GKNodeCategory gnc,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GKNodeCateManager.insertGKNodeCategory(gnc,stl);
		}else
			return false;
	}
	
	/**
	 * 修改分类节点
	 * @param GKNodeCategory gnc
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateGKNodeCategory(GKNodeCategory gnc,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GKNodeCateManager.updateGKNodeCategory(gnc,stl);
		}else
			return false;
	}
	
	/**
	 * 移动节点分类
	 * @param String nodcat_ids
	 * @param int parent_id
	 * @return boolean
	 */
	public static boolean moveGKNodeCategory(String nodcat_ids,int parent_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GKNodeCateManager.moveGKNodeCategory(nodcat_ids,parent_id,stl);
		}else
			return false;
	}
	
	/**
	 * 保存分类节点排序
	 * @param String nodcat_ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean sortGKNodeCategory(String nodcat_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GKNodeCateManager.sortGKNodeCategory(nodcat_ids,stl);
		}else
			return false;
	}
	
	/**
     * 根据ID判断分类是否有叶节点,用于删除时判断
     * @param int nodcat_id
     * @return boolean
     * */
	public static boolean hasChildNodeByCategory(int nodcat_id)
	{
		return GKNodeCateManager.hasChildNodeByCategory(nodcat_id);
	}
	
	/**
	 * 删除分类节点
	 * @param String nodcat_ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean deleteGKNodeCategory(String nodcat_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GKNodeCateManager.deleteGKNodeCategory(nodcat_ids,stl);
		}else
			return false;
	}
	
	/********************* 分类管理 结束 **********************************/
	/********************* 节点管理 开始 **********************************/
	/**
	 * 返回所有节点列表
	 * @param 
	 * @return List
	 */
	public static List<GKNodeBean> getAllGKNodeList()
	{
		return GKNodeManager.getAllGKNodeList();
	}
	/**
	 * 根据分类ID得到节点列表
	 * @param int nodcat_id
	 * @return List
	 */
	public static List<GKNodeBean> getGKNodeListByCateID(int nodcat_id)
	{
		return GKNodeManager.getGKNodeListByCateID(nodcat_id);
	}
	
	/**
	 * 根据node_id得到节点名称
	 * @param String node_id
	 * @return String
	 */
	public static String getNodeNameByNodeID(String node_id)
	{
		return GKNodeManager.getNodeNameByNodeID(node_id);
	}
	
	/**
	 * 根据分类ID判断此分类下是否有节点，用于分类删除判断
	 * @param String nodcat_id
	 * @return boolean
	 */
	public static boolean hasNodeByCatID(int nodcat_id)
	{
		return GKNodeManager.hasNodeByCatID(nodcat_id);
	}
	
	/**
	 * 根据node_id判断是否已存在
	 * @return int
	 */
	public static boolean nodeIdIsExist(String node_id)
	{
		return GKNodeManager.nodeIdIsExist(node_id);
	}
	
	/**
	 * 根据ID得到节点对象
	 * @param int id 
	 * @return GKNodeCategory
	 */
	public static GKNodeBean getGKNodeBeanByID(int id)
	{
		return GKNodeManager.getGKNodeBeanByID(id);
	}
	
	/**
	 * 根据node_id得到节点对象
	 * @param String node_id
	 * @return GKNodeCategory
	 */
	public static GKNodeBean getGKNodeBeanByNodeID(String node_id)
	{
		return GKNodeManager.getGKNodeBeanByNodeID(node_id);
	}
	
	/**
	 * 插入节点
	 * @param GKNodeBean gbn
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertGKNode(GKNodeBean gbn,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GKNodeManager.insertGKNode(gbn,stl);
		}else
			return false;
	}
	
	/**
	 * 修改节点
	 * @param GKNodeBean gbn
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateGKNode(GKNodeBean gbn,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GKNodeManager.updateGKNode(gbn,stl);
		}else
			return false;
	}
	
	/**
	 * 修改节点(用于公开节点中的资料信息修改)
	 * @param GKNodeBean gbn
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateGKNodeSimple(GKNodeBean gbn,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GKNodeManager.updateGKNodeSimple(gbn,stl);
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
	public static boolean updateGKNodeStatus(String ids,int node_status,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GKNodeManager.updateGKNodeStatus(ids,node_status,stl);
		}else
			return false;
	}
	
	/**
	 * 移动节点
	 * @param String ids
	 * @param int nodcat_id
	 * @return boolean
	 */
	public static boolean moveGKNode(String ids,int nodcat_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GKNodeManager.moveGKNode(ids,nodcat_id,stl);
		}else
			return false;
	}
	
	/**
	 * 保存节点排序
	 * @param String ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean sortGKNode(String ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GKNodeManager.sortGKNode(ids,stl);
		}else
			return false;
	}
	
	/**
	 * 删除节点
	 * @param String ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean deleteGKNode(String ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GKNodeManager.deleteGKNode(ids,stl);
		}else
			return false;
	}
	/********************* 节点管理 结束 **********************************/
	public static void main(String[] args)
	{
		System.out.println(getGKNodeCategroyJSONTreeStr());
	}
}
