package com.deya.wcm.dao.zwgk.ser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.ser.SerResouceBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  场景式服务资源信息数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 场景式服务资源信息数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SerResouceDAO {
	/**
	 * 根据主题（导航）ID得到资源信息列表
	 * @param String ser_id
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<SerResouceBean> getSerResouceList(String ser_id)
	{		
		return DBManager.queryFList("getSerResouceList",ser_id);
	}
	
	/**
	 * 根据主题（导航）ID得到已发布的资源信息列表
	 * @param String ser_id
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<SerResouceBean> getSerResouceListByPublish(String ser_id)
	{
		return DBManager.queryFList("getSerResouceListByPublish",ser_id);
	}
	
	/**
	 * 根据ID得到资源信息对象
	 * @param res_id
	 * @return SerResouceBean
	 */
	public static SerResouceBean getSerResouceBean(String res_id)
	{
		return (SerResouceBean)DBManager.queryFObj("getSerResouceBean", res_id);
	}
	
	/**
	 * 插入资源信息
	 * @param SerResouceBean srb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertSerResouce(SerResouceBean srb,SettingLogsBean stl)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.SER_RESOUCE_TABLE_NAME);
		srb.setId(id);
		srb.setRes_id(id);
		if(DBManager.insert("insert_ser_resouce", srb))
		{
			PublicTableDAO.insertSettingLogs("添加", "场景式服务资源信息", srb.getRes_id()+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改资源信息
	 * @param SerResouceBean srb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSerResouce(SerResouceBean srb,SettingLogsBean stl)
	{
		if(DBManager.update("update_ser_resouce", srb))
		{
			PublicTableDAO.insertSettingLogs("添加", "场景式服务资源信息", srb.getRes_id()+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改分类状态
	 * @param String res_ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSerResouceStatus(String res_ids,String publish_status,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("res_ids", res_ids);
		m.put("publish_status", publish_status);
		if("0".equals(publish_status))
			m.put("publish_time", "");
		else
			m.put("publish_time", DateUtil.getCurrentDateTime());
		if(DBManager.update("update_ser_resouce_status", m))
		{
			PublicTableDAO.insertSettingLogs("修改", "场景式服务资源信息", res_ids, stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 保存排序
     * @param String ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean sortSerResouce(String ids,SettingLogsBean stl)
	{
		try{
			Map<String,Object> m =new HashMap<String,Object>();
			String[] tempA = ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				m.put("sort_id", i+1);
				m.put("res_id", tempA[i]);
				DBManager.update("sort_ser_resouce", m);
			}
			PublicTableDAO.insertSettingLogs("保存排序","场景式服务资源信息",ids,stl);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}	
	}
	
	/**
     * 删除资源信息
     * @param String res_ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean deleteSerResouce(String res_ids,SettingLogsBean stl)
	{
		Map<String,String> m =new HashMap<String,String>();
		m.put("res_ids", res_ids);
		if(DBManager.delete("delete_ser_resouce", m))
		{
			PublicTableDAO.insertSettingLogs("删除","场景式服务资源信息",res_ids,stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 根据栏目ID删除资源信息
     * @param String ser_ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean deleteSerResouceByCategory(String ser_ids,SettingLogsBean stl)
	{
		Map<String,String> m =new HashMap<String,String>();
		m.put("ser_ids", ser_ids);
		if(DBManager.delete("delete_ser_resouce", m))
		{
			PublicTableDAO.insertSettingLogs("删除","场景式服务资源信息 分类为",ser_ids,stl);
			return true;
		}
		else
			return false;
	}
}
