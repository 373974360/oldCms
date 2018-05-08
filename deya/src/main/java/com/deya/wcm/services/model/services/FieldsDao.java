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
public class FieldsDao {
	
	private static String FIELD_TABLE_NAME = "cs_field_data";
	
	/**
     * 得到列表
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public static List<Fields> getFieldsList(Map map) {
		return DBManager.queryFList("fields.getFieldsList", map);
	} 
	
	/**
     * 得到列表 数量
     * @param Map map sql所需要的参数 
     * @return int
     * */
	public static int getFieldsListCount(Map map) {
		return Integer.valueOf((Integer)DBManager.queryFObj("fields.getFieldsListCount", map));
	}
	
	/**
     * 插入信息
     * @param Fields fields
     * @return boolean
     * */
	public static boolean addFields(Fields fields) {
		fields.setId(PublicTableDAO.getIDByTableName(FIELD_TABLE_NAME));
		//System.out.println(fields.toString());
		return DBManager.insert("fields.addFields", fields);
	} 
	
	
	//删除信息
	public static boolean deleteFields(String ids){
		Map map = new HashMap();
		map.put("ids", ids); 
		return DBManager.delete("fields.deleteFields", map);
	} 
	
	//查看信息
	public static Fields getFieldById(String id)
	{   
		Map map = new HashMap();
		map.put("id", id);
		return (Fields)DBManager.queryFObj("fields.getFieldById",map);
	}
	
	
	/**
     * 修改信息
     * @param Fields fields
     * @return boolean
     * */
	public static boolean updateFieldsById(Fields fields) {
		return DBManager.update("fields.updateFieldsById", fields);
	} 
	
}
