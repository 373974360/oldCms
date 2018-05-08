package com.deya.wcm.services.zwgk.ser;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.dict.DataDictBean;
import com.deya.wcm.bean.zwgk.ser.SerCategoryBean;
import com.deya.wcm.bean.zwgk.ser.SerResouceBean;
import com.deya.wcm.services.Log.LogManager;

public class SerRPC {
	/********************* 场景式服务主题（导航）管理 开始 **********************************/
	/**
	 * 得到已发布的根节点分类树
	 * @return String
	 */
	public static String getSerCategoryRootJSONTree()
	{
		return SerCategoryManager.getSerCategoryRootJSONTree();
	}
	
	/**
	 * 根据ID资源分类列表,对应数据字典中的
	 * @param ser_id
	 * @return List
	 */
	public static List<DataDictBean> getDataDictList(int ser_id)
	{
		return SerCategoryManager.getDataDictList(ser_id);
	}
	
	/**
	 * 根据主题ID得到节点树
	 * @return String
	 */
	public static String getSerCateJSONTree(int ser_id)
	{
		return SerCategoryManager.getSerCateJSONTree(ser_id);
	}
	
	/**
	 * 根据节点ID得到子节点列表
	 * @return List
	 */
	public static List<SerCategoryBean> getChildSerCategoryList(int ser_id)
	{
		return SerCategoryManager.getChildSerCategoryList(ser_id);
	}
	
	/**
	 * 得到根节点分类列表
	 * @return List
	 */
	public static List<SerCategoryBean> getSerCategoryRootList()
	{
		return SerCategoryManager.getSerCategoryRootList();
	}
	
	/**
	 * 根据ID得到资源信息对象
	 * @param res_id
	 * @return SerResouceBean
	 */
	public static SerCategoryBean getSerCategoryBean(int ser_id)
	{
		return SerCategoryManager.getSerCategoryBean(ser_id);
	}
	
	/**
	 * 插入分类节点
	 * @param SerCategoryBean scb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertSerCategory(SerCategoryBean scb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SerCategoryManager.insertSerCategory(scb,stl);
		}else
			return false;
	}
	
	/**
	 * 修改分类节点
	 * @param SerCategoryBean scb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSerCategory(SerCategoryBean scb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SerCategoryManager.updateSerCategory(scb,stl);
		}else
			return false;
	}
	
	/**
     * 移动目录
     * @param String parent_id
     * @param String cat_ids
     * @return boolean
     * */
	public static boolean moveSerCategory(int parent_id,String ser_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SerCategoryManager.moveSerCategory(parent_id,ser_ids,stl);
		}else
			return false;
	}
	
	/**
	 * 修改分类状态
	 * @param String ser_ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSerCategoryStatus(String ser_ids,String publish_status,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SerCategoryManager.updateSerCategoryStatus(ser_ids,publish_status,stl);
		}else
			return false;
	}
	
	/**
     * 保存排序
     * @param String ser_ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean sortSerCategory(String ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SerCategoryManager.sortSerCategory(ids,stl);
		}else
			return false;
	}
	
	/**
     * 删除节点信息
     * @param String ser_ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean deleteSerCategory(String ser_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SerCategoryManager.deleteSerCategory(ser_ids,stl);
		}else
			return false;
	}
	
	/**
	 * 得到新的分类节点ID
	 * @return int
	 */		
	public static int getNewID()
	{
		return SerCategoryManager.getNewID();
	}
	
	/**
	 * 修改场景式服务内容页模板关联
	 * @param String template_content_id
	 * @return boolean
	 */
	public static boolean updateSerTemplateContent(String template_content_id)
	{
		return SerCategoryManager.updateSerTemplateContent(template_content_id);
	}
	/********************* 场景式服务主题（导航）管理 结束 **********************************/
	
	/********************* 场景式服务资源信息管理 开始 **********************************/
	/**
	 * 根据主题（导航）ID得到资源信息列表
	 * @param String ser_id
	 * @return List
	 */
	public static List<SerResouceBean> getSerResouceList(String ser_id)
	{
		return SerResouceManager.getSerResouceList(ser_id);
	}
	
	/**
	 * 根据ID得到资源信息对象
	 * @param res_id
	 * @return SerResouceBean
	 */
	public static SerResouceBean getSerResouceBean(String res_id)
	{
		return SerResouceManager.getSerResouceBean(res_id);
	}
	
	/**
	 * 插入资源信息
	 * @param SerResouceBean srb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertSerResouce(SerResouceBean srb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SerResouceManager.insertSerResouce(srb,stl);
		}else
			return false;
	}
	
	/**
	 * 修改资源信息
	 * @param SerResouceBean srb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSerResouce(SerResouceBean srb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SerResouceManager.updateSerResouce(srb,stl);
		}else
			return false;
	}
	
	/**
	 * 修改分类状态
	 * @param String res_ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSerResouceStatus(String res_ids,String publish_status,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SerResouceManager.updateSerResouceStatus(res_ids,publish_status,stl);
		}else
			return false;
	}
	
	/**
     * 保存排序
     * @param String ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean sortSerResouce(String ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SerResouceManager.sortSerResouce(ids,stl);
		}else
			return false;
	}
	
	/**
     * 删除资源信息
     * @param String res_ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean deleteSerResouce(String res_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SerResouceManager.deleteSerResouce(res_ids,stl);
		}else
			return false;
	}
	/********************* 场景式服务资源信息管理 结束 **********************************/
	
	
	public static List<SerResouceBean> getSerResouceListByPublish(String ser_id)
	{
		return SerResouceManager.getSerResouceListByPublish(ser_id);
	}
}
