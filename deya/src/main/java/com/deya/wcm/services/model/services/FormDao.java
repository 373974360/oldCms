package com.deya.wcm.services.model.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.services.model.Fields;

/**
 *  元数据集数据库操作处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 元数据集数据库操作处理类</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class FormDao {
	
	private static String FIELD_TABLE_NAME = "cs_form_data";
	
	/**
     * 得到列表
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public static List<Fields> getFormFieldsListByModelId(Map map) {
		return DBManager.queryFList("form.getFormFieldsListByModelId", map);
	}
	
	/**
     * 得到列表
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public static List<Fields> getFormFieldsListByModelIdN(Map map) {
		return DBManager.queryFList("form.getFormFieldsListByModelIdN", map);
	}
	
	/**
     * 得到列表 数量
     * @param Map map sql所需要的参数 
     * @return int
     * */
	public static int getFormFieldsListByModelIdCount(Map map) {
		return Integer.valueOf((Integer)DBManager.queryFObj("form.getFormFieldsListByModelIdCount", map));
	}
	
	/**
     * 得到列表 数量
     * @param Map map sql所需要的参数 
     * @return int
     * */
	public static int getFormFieldsListByFromIdsCount(String from_ids) {
		Map map = new HashMap();
		map.put("from_ids", from_ids);  
		return Integer.valueOf((Integer)DBManager.queryFObj("form.getFormFieldsListByFromIdsCount", map));
	}
	
	
	//删除信息
	public static boolean deleteFormFieldsByModeIdAndFromId(String ids,String model_ids){
		Map map = new HashMap();
		map.put("from_ids", ids); 
		map.put("model_id", model_ids);
		//System.out.println("deleteFormFieldsByModeIdAndFromId map :: " + map);
		return DBManager.delete("form.deleteFormFieldsByModeIdAndFromId", map);
	} 
	
	/**
     * 插入信息
     * @param Fields fields
     * @return boolean
     * */
	public static boolean addFields(Fields fields) {
		fields.setId(PublicTableDAO.getIDByTableName(FIELD_TABLE_NAME));
		//System.out.println(fields.toString());
		return DBManager.insert("form.addFormFields", fields);
	} 
	
	
	//保存排序
	public static boolean saveFormSort(String ids)
	{
		if(ids != null && !"".equals(ids))
		{
			try{
				Map<String, Comparable> m = new HashMap<String, Comparable>();
				String[] tempA = ids.split(",");
				for(int i=0;i<tempA.length;i++)
				{
					m.put("field_sort", (i+1));
					m.put("id", tempA[i]);
					DBManager.update("form.sortFormFields", m);
				}
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else
			return true;
	}
	
	//查看信息
	public static Fields getFormFieldById(String id)
	{   
		Map map = new HashMap();
		map.put("id", id);
		return (Fields)DBManager.queryFObj("form.getFormFieldById",map);
	}
	
	
	/**
     * 修改信息
     * @param Fields fields
     * @return boolean
     * */
	public static boolean updateFormFieldsById(Fields fields) {
		return DBManager.update("form.updateFormFieldsById", fields);
	}
	
	//删除信息
	public static boolean deleteFormFields(String ids){
		Map map = new HashMap();
		map.put("ids", ids); 
		return DBManager.delete("form.deleteFormFields", map);
	} 
	
	//设置信息列表header
	public static boolean updateFormFieldFlag(String field_flag,String action,String id){
		Map map = new HashMap();
		map.put("id", id); 
		map.put("field_flag", field_flag); 
		map.put("action", action); 
		return DBManager.delete("form.updateFormFieldFlag", map);
	} 
}
