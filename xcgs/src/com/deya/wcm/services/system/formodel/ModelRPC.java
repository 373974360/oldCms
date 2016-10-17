package com.deya.wcm.services.system.formodel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.formodel.ModelBean;
import com.deya.wcm.bean.system.formodel.ModelFieldBean;
import com.deya.wcm.services.Log.LogManager;

public class ModelRPC {
	public static Map<Integer,ModelBean> getModelMap()
	{
		return ModelManager.getModelMap();
	}
	
	/**
	 * 得到所有的 内容模型列表
	 * 
	 * @param 
	 * @return List 
	 */
	public static List<ModelBean> getModelList()
	{
		return ModelManager.getModelList();
	}
	
	/**
	 * 根据模型ID返回英文名称
	 * 
	 * @param int model_id
	 * @return String 
	 */
	public static String getModelEName(int model_id)
	{
		return ModelManager.getModelEName(model_id);
	}
	/**
	 * 根据英文名称得到内容模型对象
	 * 
	 * @param String model_ename
	 * @return ModelBean 
	 */
	public static ModelBean getModelBeanByEName(String model_ename){
		return ModelManager.getModelBeanByEName(model_ename);
	}
	
	/**
	 * 得到可用的内容模型列表
	 * 
	 * @param 
	 * @return List 
	 */
	public static List<ModelBean> getCANModelList(){
		return ModelManager.getCANModelList();
	}
	
	/**
	 * 根据模型ID返回模型添加页
	 * 
	 * @param int model_id
	 * @return String 
	 */
	public static String getModelAddPage(int model_id)
	{
		return ModelManager.getModelAddPage(model_id);
	}
	
	/**
	 * 根据模型ID返回模型添加页
	 * 
	 * @param int model_id
	 * @return String 
	 */
	public static String getModelViewPage(int model_id)
	{
		return ModelManager.getModelViewPage(model_id);
	}
	
	/**
	 * 得到可用的内容模型列表
	 * 
	 * @param app_id
	 * @return List 
	 */
	public static List<ModelBean> getCANModelListByAppID(String app_id)
	{
		return ModelManager.getCANModelListByAppID(app_id);
	}
	
	/**
	 * 得到内容模型对象
	 * 
	 * @param int model_id
	 * @return ModelBean 
	 */
	public static ModelBean getModelBean(int model_id){
		return ModelManager.getModelBean(model_id);
	}
	
	/**
     * 插入内容模型信息
     * @param ModelBean mb
     * @return boolean
     * */
	public static boolean insertModel(ModelBean mb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return ModelManager.insertModel(mb,stl);
		}else
			return false;		
	}
	
	/**
     * 修改内容模型信息
     * @param ModelBean mb
     * @return boolean
     * */
	public static boolean updateModel(ModelBean mb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return ModelManager.updateModel(mb,stl);
		}else
			return false;		
	}
	
	/**
     * 修改内容模型状态
     * @param String model_ids
     * @param String disabled
     * @return boolean
     * */
	public static boolean updateModelDisabled(String model_ids,String disabled,HttpServletRequest request)
 	{
 		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
 		if(stl != null)
 		{
 			return ModelManager.updateModelDisabled(model_ids,disabled,stl);
 		}else
 			return false;		
 	}
	
	/**
     * 删除内容模型信息
     * @param String model_ids
     * @return boolean
     * */
	public static boolean deleteModel(String model_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return ModelManager.deleteModel(model_ids,stl);
		}else
			return false;		
	}
	
	/**
     * 保存排序
     * @param String model_ids
     * @return boolean
     * */
	public static boolean saveModelSort(String model_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return ModelManager.saveModelSort(model_ids,stl);
		}else
			return false;		
	}
	
	/*********************** 模型字段管理 开始  ************************************/
	/**
     * 根据内容模型ID得到它下面的字段列表
     * @param int model_id
     * @return List
     * */
	public static List<ModelFieldBean> getFieldListByModelID(int model_id)
	{
		return ModelFieldManager.getFieldListByModelID(model_id);
	}
	
	/**
     * 根据内容模型ID得到里面自定义(扩展字段)对象列表
     * @param int model_id
     * @return List
     * */
	public static List<ModelFieldBean> getUDefinedFieldsByModelID(int model_id){
		return ModelFieldManager.getUDefinedFieldsByModelID(model_id);
	}
	
	/**
	 * 得到内容模型字段对象
	 * 
	 * @param int modelField_id
	 * @return ModelFieldBean 
	 */
	public static ModelFieldBean getModelFieldBean(int modelField_id)
	{
		return ModelFieldManager.getModelFieldBean(modelField_id);
	}
	
	/**
     * 插入内容模型字段信息
     * @param ModelFieldBean mfb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertModelField(ModelFieldBean mfb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return ModelFieldManager.insertModelField(mfb,stl);
		}else
			return false;		
	}
	
	/**
     * 修改内容模型字段信息
     * @param ModelFieldBean mfb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateModelField(ModelFieldBean mfb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return ModelFieldManager.updateModelField(mfb,stl);
		}else
			return false;		
	}
	
	/**
     * 删除内容模型字段信息
     * @param String field_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteModelField(String field_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return ModelFieldManager.deleteModelField(field_ids,stl);
		}else
			return false;		
	}
	/*********************** 模型字段管理 结束  ************************************/
}
