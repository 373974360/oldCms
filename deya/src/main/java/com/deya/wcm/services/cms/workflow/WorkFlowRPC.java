package com.deya.wcm.services.cms.workflow;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.cms.workflow.WorkFlowBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

public class WorkFlowRPC {
	/**
     * 根据流程ID，用户ID，应用ID，站点ID得到它下一步的步骤ID
     * @param int wf_id
     * @param String user_id
     * @param String app_id
     * @param String site_id
     * @return String
     * */
	public static int getNextStepIDByUserID(int wf_id,String user_id,String app_id,String site_id)
	{
		return WorkFlowManager.getNextStepIDByUserID(wf_id, user_id, app_id, site_id);
	}
	
	/**
     * 根据流程ID，用户ID，应用ID，站点ID得到它最大的步骤ID
     * @param int wf_id
     * @param String user_id
     * @param String app_id
     * @param String site_id
     * @return String
     * */
	public static int getMaxStepIDByUserID(int wf_id,String user_id,String app_id,String site_id)
	{
		return WorkFlowManager.getMaxStepIDByUserID(wf_id, user_id, app_id, site_id);
	}
	
	
	/**
     * 获取工作流列表信息
     * @param 
     * @return List
     * */	
	public static List<WorkFlowBean> getWorkFlowList()
	{
		return WorkFlowManager.getWorkFlowList();
	}
	
	/**
     * 根据ID返回工作流对象
     * @param int wf_id
     * @return WorkFlowBean
     * */
	public static WorkFlowBean getWorkFlowBean(int wf_id)
	{
		return WorkFlowManager.getWorkFlowBean(wf_id);
	}
	
	/**
     * 插入工作流信息
     * @param WorkFlowBean wfb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertWorkFlow(WorkFlowBean wfb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WorkFlowManager.insertWorkFlow(wfb,stl);
		}else
			return false;		
	}
	
	/**
    * 修改工作流信息
    * @param WorkFlowBean wfb
    * @param SettingLogsBean stl
    * @return boolean
    * */
	public static boolean updateWorkFlow(WorkFlowBean wfb,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WorkFlowManager.updateWorkFlow(wfb,stl);
		}else
			return false;		
	}
	
	/**
     * 删除工作流信息
     * @param String wf_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteWorkFlow(String wf_ids,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return WorkFlowManager.deleteWorkFlow(wf_ids,stl);
		}else
			return false;		
	}
}
