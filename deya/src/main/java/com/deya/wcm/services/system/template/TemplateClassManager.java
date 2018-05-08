package com.deya.wcm.services.system.template;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.template.TemplateClassBean;
import com.deya.wcm.dao.system.template.TemplateClassDAO;

/**
 * @author 符江波
 * @date   2011-3-24 上午10:19:00
 */
public class TemplateClassManager {

	/**
	 * 根据ID得到模板信息
	 * @param int t_id
	 * @return TemplateClassBean
	 * */
	public static TemplateClassBean getTemplateClassBean(int t_id)
	{
		return TemplateClassDAO.getTemplateClassBean(t_id);
	}

	/**
	 * 得到模板总数
	 * @param 
	 * @return String
	 * */
	public static String getTemplateClassCount(Map<String,String> con_map)
	{
		return TemplateClassDAO.getTemplateClassCount(con_map);
	}

	/**
	 * 修改模板
	 * @param author
	 * @return boolean
	 */
	public static boolean updateTemplateClass(TemplateClassBean template, SettingLogsBean stl){
		return TemplateClassDAO.updateTemplateClass(template, stl);
	}

	/**
	 * 添加模板
	 * @param template
	 * @return boolean
	 */
	public static boolean addTemplateClass(TemplateClassBean template, SettingLogsBean stl){
		if(template == null){
			return false;
		}
		return TemplateClassDAO.addTemplateClass(template,stl);
	}

	/**
	 * 所有模板列表
	 * @return List<TemplateClassBean>
	 */
	public static List<TemplateClassBean> getAllTemplateClassList()
	{
		return TemplateClassDAO.getAllTemplateClassList();
	}

	/**
	 * 得到模板列表
	 * @param con_map
	 * @return List<TemplateClassBean>
	 */
	public static List<TemplateClassBean> getTemplateClassListForDB(Map<String,String> con_map)
	{
		return TemplateClassDAO.getTemplateClassListForDB(con_map);
	}

	/**
	 * 删除模板
	 * @param t_id
	 * @return boolean
	 */
	public static boolean delTemplateClassById(String t_ids, SettingLogsBean stl){
		if(t_ids == null || t_ids.equals("")){
			return false;
		}
		return TemplateClassDAO.delTemplateClass(t_ids, stl);
	}
	
}
