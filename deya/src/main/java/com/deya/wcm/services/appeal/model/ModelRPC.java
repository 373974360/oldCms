package com.deya.wcm.services.appeal.model;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.appeal.cpDept.CpDeptBean;
import com.deya.wcm.bean.appeal.cpLead.CpLeadBean;
import com.deya.wcm.bean.appeal.model.CPFrom;
import com.deya.wcm.bean.appeal.model.ModelBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;
 

 

public class ModelRPC {
	/**
     * 得到所有业务列表
     * @param 
     * @return List
     * */
	public static List<ModelBean> getAllSQModelList(){
		return ModelManager.getAllSQModelList();
	}
	
	
	/**
     * 根据业务ID获取部门，收信人列表
     * @param String model_id
     * @return List
     * */
	public static List<CpDeptBean> getAppealDeptList(String model_id)
	{
		return ModelManager.getModelDeptListByMID(Integer.parseInt(model_id));
	}
	
	/**
     * 根据条件得到总数
     * @param Map<String,String> m
     * @return String
     * */
	public static String getSQModelCount(Map<String,String> m)
	{
		return ModelManager.getSQModelCount(m);
	}
	
	/**
     * 根据条件得到列表
     * @param Map<String,String> m
     * @return List
     * */
	public static List<ModelBean> getSQModelListForDB(Map<String,String> m)
	{
		return ModelManager.getSQModelListForDB(m);
	}
  
	/**
	 * 根据业务ID返回对象
	 * @param Model_id
	 * @return Bean
	 */
	public static ModelBean getModelBean(int Model_id)
	{ 
		return ModelManager.getModelBean(Model_id);
	}
	 
	/**
     * 得到业务ID,用于添加页面
     * @param
     * @return String
     * */
	public static int getModelID()
	{
		return ModelManager.getModelID();
	} 
	/**
     * 新增业务信息
     * @param ModelBean ob
     * @param String dept_ids
     * @param HttpServletRequest request
     * @return boolean
     * */
	public static boolean insertModel(ModelBean ob,String dept_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(null !=stl)
		{
			return ModelManager.insertModel(ob,dept_ids,stl);
		}else
			return false;
	}
	
	
	/**
     * 修改业务信息
     * @param ModelBean ob
     * @param String dept_ids
     * @param HttpServletRequest request
     * @return boolean
     * */
	public static boolean updateModel(ModelBean ob,String dept_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(null !=stl)
		{
			return ModelManager.updateModel(ob,dept_ids,stl);
		}else
			return false;
	}
	/**
	 * 删除业务信息
	 * @param String model_ids
	 * @param request
	 * @return
	 */
	public static boolean deleteModel(String model_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(null !=stl)
		{
		    return ModelManager.deleteModel(model_ids,stl);
		}else{
		    return false;
		}
	}	
	

	/**
     * 根据业务ID得到部门信息 
     * @param int model_id
     * @return Map
     * */
	public static Map<String,String> getModelDeptMapByMID(int model_id)
    {
    	return ModelManager.getModelDeptMapByMID(model_id);
    }
	
	/**
     * 根据业务ID得到领导人信息 
     * @param int model_id
     * @return Map
     * */
    public static Map<String,String> getModelLeadMapByMID(int model_id)
    {
    	return ModelManager.getModelLeadMapByMID(model_id);
    }
	/*********************** 业务与部门关联 开始 *****************************/
	 /**
     * 根据业务ID得到领导人列表 
     * @param int model_id
     * @return List
     * */
    public static List<CpLeadBean> getModelLeadListByMID(int model_id)
    {
    	return ModelManager.getModelLeadListByMID(model_id);
    }
	
	
    
    /**
     * 根据业务ID得到部门ID
     * @param int model_id
     * @return String
     * */
    public static String getModelDeptIDSByMID(int model_id)
    {
    	return ModelManager.getModelDeptIDSByMID(model_id);
    }
    /*********************** 业务与部门关联 结束 *****************************/
    
    /*********************** 业务与人员关联 开始 *****************************/
    /**
     * 根据业务ID得到人员ID
     * @param int model_id
     * @return String
     * */
    public static String getModelUserIDSByMID(int model_id)
    {
    	return ModelManager.getModelUserIDSByMID(model_id);
    }
    
    /**
     * 根据业务ID人员信息 
     * @param int model_id
     * @return Map
     * */
    public static Map<String,String> getModelUserMapByMID(int model_id)
    {
    	return ModelManager.getModelUserMapByMID(model_id);
    }
    
    /**
     * 插入业务与人员关联列表(以人员为主)
     * @param ModelReleUser mrd
     * @return boolean
     * */
	public static boolean insertModelReleUser(String model_ids,int user_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(null !=stl)
		{
			return ModelManager.insertModelReleUser(model_ids,user_id,stl);
		}else{
		    return false;
		}
	}
	
	/**
     * 根据人员ID得到业务列表
     * @param int model_id
     * @return List
     * */
    public static List<ModelBean> getModelListSByUserID(int user_id)
    {
    	return ModelManager.getModelListSByUserID(user_id);
    }
	
	/**
     * 插入业务与用户关联列表(以业务为主)
     * @param ModelReleUser mrd
     * @return boolean
     * */
	public static boolean insertModelReleUserByModel(int model_id,String user_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(null !=stl)
		{
			return ModelManager.insertModelReleUserByModel(model_id,user_ids,stl);
		}else{
		    return false;
		}
	}
    /*********************** 业务与人员关联 结束 *****************************/
	
	public static List<CPFrom> getCPFormListByModel(int model_id)
	{
		return CPFromManager.getCPFormListByModel(model_id);
	}
	
	public static boolean insertCPFrom(int model_id,List<CPFrom> l,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(null !=stl)
		{
			return CPFromManager.insertCPFrom(model_id,l,stl);
		}else{
		    return false;
		}
	}
}
