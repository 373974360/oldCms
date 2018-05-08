package com.deya.wcm.services.model.services;

import java.util.List;
import java.util.Map;

import com.deya.wcm.services.model.Fields;

/**
 *  元数据集数据库操作处理类.js调用
 * <p>Title: CicroDate</p>
 * <p>Description: 元数据集数据库操作处理类.js调用</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class FieldsRPC {

	
	/**
     * 得到列表
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public static List<Fields> getFieldsList(Map map) {
		return FieldsService.getFieldsList(map);
	}
	
	
	/**
     * 得到列表 数量
     * @param Map map sql所需要的参数 
     * @return int
     * */
	public static int getFieldsListCount(Map map) {
		return FieldsService.getFieldsListCount(map);
	}
	
	/**
     * 插入信息
     * @param Fields fields
     * @return boolean
     * */
	public static boolean addFields(Fields fields) {
		return FieldsService.addFields(fields);
	}
	
	//删除信息
	public static boolean deleteFields(String ids){
		return FieldsService.deleteFields(ids);
	}
	
	//查看信息
	public static Fields getFieldById(String id)
	{   
		return FieldsService.getFieldById(id);
	}
	
	/**
     * 修改信息
     * @param Fields fields
     * @return boolean
     * */
	public static boolean updateFieldsById(Fields fields) {
		return FieldsService.updateFieldsById(fields);
	}
}
