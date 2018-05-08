package com.deya.wcm.dao.system.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.template.TemplateBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 * 模板数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 模板数据处理类,sql在template.xml 中</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author 符江波
 * @version 1.0
 * @date   2011-3-24 下午02:04:44
 */
public class TemplateDAO {
	
	/**
    * 得到所有模板信息
    * @param 
    * @return List
    * */	
	@SuppressWarnings("unchecked")
	public static List<TemplateBean> getAllTemplateList()
	{
		return DBManager.queryFList("getAllTemplateList", "");
	}
	
	/**
    * 根据ID得到模板信息
    * @param int t_id
    * @return TemplateBean
    * */
	public static TemplateBean getTemplateBean(int t_id)
	{
		return (TemplateBean)DBManager.queryFObj("getTemplateBean", t_id);
	}
	
	/**
    * 得到模板总数
    * @param 
    * @return String
    * */
	public static String getTemplateCount(Map<String,String> con_map)
	{
		return DBManager.getString("getTemplateCount", con_map);
	}
	
	/**
    * 根据条件查询模板信息
    * @param Map<String,String> con_map
    * @return List<TemplateBean>
    * */
	@SuppressWarnings("unchecked")
	public static List<TemplateBean> getTemplateListForDB(Map<String,String> con_map)
	{
		return DBManager.queryFList("getTemplateListForDB", con_map);
	}
	
	/**
	 * 修改模板
	 * @param tb
	 * @param stl
	 * @return boolean
	 */
	public static boolean updateTemplate(TemplateBean tb, SettingLogsBean stl){
		if(DBManager.update("updateTemplate", tb))
		{
			PublicTableDAO.insertSettingLogs("修改","模板",tb.getT_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 克隆模板
	 * @param hw
	 * @return boolean
	 */
	public static boolean cloneTemplate(TemplateBean tb){
		int tbId = PublicTableDAO.getIDByTableName(PublicTableDAO.TEMPLATE_TABLE_NAME);	
		tb.setId(tbId);
		return DBManager.insert("insertTemplate", tb);
	}
	
	/**
	 * 添加模板
	 * @param hw
	 * @return boolean
	 */
	public static boolean addTemplate(TemplateBean tb, SettingLogsBean stl){
		int tbId = PublicTableDAO.getIDByTableName(PublicTableDAO.TEMPLATE_TABLE_NAME);
		tb.setT_id(tbId);
		tb.setId(tbId);
		if(DBManager.insert("insertTemplate", tb))
		{
			PublicTableDAO.insertSettingLogs("添加","模板",tbId+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 删除模板
	 * @param t_ids
	 * @param stl
	 * @return boolean
	 */
	public static boolean delTemplate(String t_ids, String site_id, SettingLogsBean stl){
		Map<String,String> map = new HashMap<String,String>();
		map.put("t_ids", t_ids);
		map.put("site_id", site_id);
		if(DBManager.delete("deleteTemplate", map))
		{
			PublicTableDAO.insertSettingLogs("删除","模板",t_ids,stl);
			return true;
		}else
			return false;
	}
	
	public static void main(String[] args) {
		TemplateBean tb = new TemplateBean();
		tb.setApp_id("bbbbbbbbbbbb");
		tb.setSite_id("gx");
		tb.setT_id(1);
		tb.setT_ver(1);
		//DBManager.insert("insertTemplate", tb);
		//DBManager.update("updateTemplate", tb);
		Map<String,String> map = new HashMap<String,String>();
		map.put("t_ids", "1");
		if(DBManager.delete("deleteTemplate", map))
		System.out.println(getAllTemplateList());
	}
}
