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
public class FormRPC {

	/**
     * 得到列表
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public static List<Fields> getFormFieldsListByModelId(Map map) {
		return FormService.getFormFieldsListByModelId(map);
	}
	
	/**
     * 得到列表 第三方资源库调用
     * @param Map map sql所需要的参数 
     * @return List
     * */ 
	public static List<Fields> getFormFieldsListByModelIdN3(Map map) {
		return FormService.getFormFieldsListByModelIdN3(map);
	}
	
	/**
     * 得到列表
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public static List<Fields> getFormFieldsListByModelIdN(Map map) {
		return FormService.getFormFieldsListByModelIdN(map);
	}
	
	
	/**
     * 得到列表 数量
     * @param Map map sql所需要的参数 
     * @return int
     * */
	public static int getFormFieldsListByModelIdCount(Map map) {
		return FormService.getFormFieldsListByModelIdCount(map);
	}
	
	//更新表结构
	public static boolean updateForm(Map map){
		return FormService.updateForm(map);
	}
	
	//保存排序
	public static boolean saveFormSort(String ids)
	{
		return FormService.saveFormSort(ids);
	}
	
	//查看信息
	public static Fields getFormFieldById(String id)
	{   
		return FormService.getFormFieldById(id);
	}
	
	/**
     * 修改信息
     * @param Fields fields
     * @return boolean
     * */
	public static boolean updateFormFieldsById(Fields fields) {
		return FormService.updateFormFieldsById(fields);
	}
	
	//删除信息
	public static boolean deleteFormFields(String ids,String model_id){
		return FormService.deleteFormFields(ids,model_id);
	}
	
	
	//设置信息列表header
	public static boolean updateFormFieldFlag(String field_flag,String action,String id){
		return FormService.updateFormFieldFlag(field_flag, action, id);
	}
	
	
}

