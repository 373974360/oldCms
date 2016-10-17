package com.deya.wcm.dao.zwgk.ser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.ser.SerCategoryBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  场景式服务主题（导航）数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 场景式服务主题（导航）数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SerCategoryDAO {
	/**
	 * 得到所有节点分类列表
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<SerCategoryBean> getAllSerCategoryList()
	{
		return DBManager.queryFList("getAllSerCategoryList", "");
	}
	
	/**
	 * 根据分类ID得到分类对象
	 * @param id 分类ID
	 * @return SerCategoryBean
	 */
	public static SerCategoryBean getSerCategoryBean(int ser_id)
	{
		return (SerCategoryBean)DBManager.queryFObj("getSerCategoryBean", ser_id);
	}
	
	/**
	 * 插入分类节点
	 * @param SerCategoryBean scb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertSerCategory(SerCategoryBean scb,SettingLogsBean stl)
	{
		if(DBManager.insert("insert_ser_category", scb))
		{
			PublicTableDAO.insertSettingLogs("添加", "场景式服务主题节点", scb.getSer_id()+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改分类节点
	 * @param SerCategoryBean scb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSerCategory(SerCategoryBean scb,SettingLogsBean stl)
	{
		if(DBManager.update("update_ser_category", scb))
		{
			PublicTableDAO.insertSettingLogs("修改", "场景式服务主题节点", scb.getSer_id()+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改分类状态
	 * @param String ser_ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSerCategoryStatus(String ser_ids,String publish_status,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ser_ids", ser_ids);
		m.put("publish_status", publish_status);
		if("0".equals(publish_status))
			m.put("publish_time", "");
		else
			m.put("publish_time", DateUtil.getCurrentDateTime());
		if(DBManager.update("update_ser_category_status", m))
		{
			PublicTableDAO.insertSettingLogs("修改", "场景式服务主题节点发布状态", ser_ids, stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 保存排序
     * @param String ser_ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean sortSerCategory(String ids,SettingLogsBean stl)
	{
		try{
			Map<String,Object> m =new HashMap<String,Object>();
			String[] tempA = ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				m.put("sort_id", i+1);
				m.put("ser_id", tempA[i]);
				DBManager.update("sort_ser_category", m);
			}
			PublicTableDAO.insertSettingLogs("保存排序","场景式服务主题节点",ids,stl);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}	
	}
	
	/**
     * 移动目录
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean moveSerCategory(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("move_ser_category", m))
		{			
			PublicTableDAO.insertSettingLogs("移动","场景导航目录",m.get("ser_id"),stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 删除节点信息
     * @param String ser_ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean deleteSerCategory(String ser_ids,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ser_ids", ser_ids);
		
		if(DBManager.delete("delete_ser_category", m))
		{
			//根据主题ID删除信息栏目表中的相关信息,常见问题栏目
			DBManager.delete("delete_info_category_forSer", m);
			DBManager.delete("delete_info_category_model_forSer", m);
			PublicTableDAO.insertSettingLogs("删除","场景式服务主题节点",ser_ids,stl);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * 修改场景式服务内容页模板关联
	 * @param String template_content_id
	 * @return boolean
	 */
	public static boolean updateSerTemplateContent(String template_content_id)
	{
		return DBManager.delete("update_category_model_forSer", template_content_id);
	}
	
}
