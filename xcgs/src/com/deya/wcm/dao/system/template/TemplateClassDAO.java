package com.deya.wcm.dao.system.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.template.TemplateClassBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 * 模板分目录据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 模板目录数据处理类,sql在template.xml 中</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author 符江波
 * @version 1.0
 * @date   2011-3-24 下午02:04:44
 */
public class TemplateClassDAO {
	
	/**
    * 得到所有模板信息
    * @param 
    * @return List
    * */	
	@SuppressWarnings("unchecked")
	public static List<TemplateClassBean> getAllTemplateClassList()
	{
		return DBManager.queryFList("getAllTemplateClassList", "");
	}
	
	/**
    * 根据ID得到模板信息
    * @param int tclass_id
    * @return TemplateClassBean
    * */
	public static TemplateClassBean getTemplateClassBean(int tclass_id)
	{
		return (TemplateClassBean)DBManager.queryFObj("getTemplateClassBean", tclass_id);
	}
	
	/**
    * 得到模板总数
    * @param 
    * @return String
    * */
	public static String getTemplateClassCount(Map<String,String> con_map)
	{
		return DBManager.getString("getTemplateClassCount", con_map);
	}
	
	/**
    * 根据条件查询模板信息
    * @param Map<String,String> con_map
    * @return List<TemplateClassBean>
    * */
	@SuppressWarnings("unchecked")
	public static List<TemplateClassBean> getTemplateClassListForDB(Map<String,String> con_map)
	{
		return DBManager.queryFList("getTemplateClassListForDB", con_map);
	}
	
	/**
	 * 修改模板
	 * @param tb
	 * @param stl
	 * @return boolean
	 */
	public static boolean updateTemplateClass(TemplateClassBean tcb, SettingLogsBean stl){
		if(DBManager.update("updateTemplateClass", tcb))
		{
			PublicTableDAO.insertSettingLogs("修改","模板目录",tcb.getTclass_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 添加模板
	 * @param hw
	 * @return boolean
	 */
	public static boolean addTemplateClass(TemplateClassBean tb, SettingLogsBean stl){
		int tclassId = PublicTableDAO.getIDByTableName(PublicTableDAO.TEMPLATE_CLASS_TABLE_NAME);
		tb.setTclass_id(tclassId);
		if(DBManager.insert("insertTemplateClass", tb))
		{
			PublicTableDAO.insertSettingLogs("添加","模板目录",tclassId+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 删除模板
	 * @param tclass_ids
	 * @param stl
	 * @return boolean
	 */
	public static boolean delTemplateClass(String tclass_ids, SettingLogsBean stl){
		Map<String,String> map = new HashMap<String,String>();
		map.put("tclass_ids", tclass_ids);
		if(DBManager.delete("deleteTemplateClass", map))
		{
			PublicTableDAO.insertSettingLogs("删除","模板目录",tclass_ids,stl);
			return true;
		}else
			return false;
	}
	
	public static void main(String[] args) {
		TemplateClassBean tcb = new TemplateClassBean();
		tcb.setApp_id("11");
		tcb.setTclass_cname("bbbbbbbbbbbbbb");
		tcb.setTclass_ename("bbbbbbbbbbbbbb");
		tcb.setTclass_id(1);
		tcb.setTclass_memo("bbbbbbbbbbbbbbbbbbbbbbbb");
		
		//DBManager.insert("insertTemplateClass", tcb);
		//DBManager.update("updateTemplateClass", tcb);
		Map<String,String> map = new HashMap<String,String>();
		map.put("tclass_ids", "1");
		if(DBManager.delete("deleteTemplateClass", map))
		System.out.println(getAllTemplateClassList());
	}
}
