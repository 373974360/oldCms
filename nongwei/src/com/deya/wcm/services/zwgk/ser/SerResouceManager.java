package com.deya.wcm.services.zwgk.ser;

import java.util.List;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.ser.SerResouceBean;
import com.deya.wcm.dao.zwgk.ser.SerResouceDAO;

/**
 *  场景式服务资源信息逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 场景式服务资源信息逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SerResouceManager {
	/**
	 * 根据主题（导航）ID得到资源信息列表
	 * @param String ser_id
	 * @return List
	 */
	public static List<SerResouceBean> getSerResouceList(String ser_id)
	{
		return SerResouceDAO.getSerResouceList(ser_id);
	}
	
	/**
	 * 根据主题（导航）ID得到已发布的资源信息列表
	 * @param String ser_id
	 * @return List
	 */
	public static List<SerResouceBean> getSerResouceListByPublish(String ser_id)
	{
		return SerResouceDAO.getSerResouceListByPublish(ser_id);
	}
	
	/**
	 * 根据ID得到资源信息对象
	 * @param res_id
	 * @return SerResouceBean
	 */
	public static SerResouceBean getSerResouceBean(String res_id)
	{
		return SerResouceDAO.getSerResouceBean(res_id);
	}
	
	/**
	 * 插入资源信息
	 * @param SerResouceBean srb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertSerResouce(SerResouceBean srb,SettingLogsBean stl)
	{
		if(srb.getPublish_status() == 1)
		{
			srb.setPublish_time(DateUtil.getCurrentDateTime());
		}
		return SerResouceDAO.insertSerResouce(srb,stl);
	}
	
	/**
	 * 修改资源信息
	 * @param SerResouceBean srb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSerResouce(SerResouceBean srb,SettingLogsBean stl)
	{
		if(srb.getPublish_status() == 1 && "".equals(srb.getPublish_time()))
		{
			srb.setPublish_time(DateUtil.getCurrentDateTime());
		}
		if(srb.getPublish_status() == 0)
			srb.setPublish_time("");
		return SerResouceDAO.updateSerResouce(srb,stl);
	}
	
	/**
	 * 修改分类状态
	 * @param String res_ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSerResouceStatus(String res_ids,String publish_status,SettingLogsBean stl)
	{
		return SerResouceDAO.updateSerResouceStatus(res_ids,publish_status,stl);
	}
	
	/**
     * 保存排序
     * @param String ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean sortSerResouce(String ids,SettingLogsBean stl)
	{
		return SerResouceDAO.sortSerResouce(ids,stl);
	}
	
	/**
     * 删除资源信息
     * @param String res_ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean deleteSerResouce(String res_ids,SettingLogsBean stl)
	{
		return SerResouceDAO.deleteSerResouce(res_ids,stl);
	}
}
