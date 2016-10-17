package com.deya.wcm.dao.cms.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.workflow.WorkFlowBean;
import com.deya.wcm.bean.cms.workflow.WorkFlowLogBean;
import com.deya.wcm.bean.cms.workflow.WorkFlowStatusBean;
import com.deya.wcm.bean.cms.workflow.WorkFlowStepBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  工作流管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 工作流角色管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class WorkFlowDAO {
	/**
     * 得到所有工作流列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getAllWorkFlowList()
	{
		return DBManager.queryFList("getAllWorkFlowList", "");
	}
	
	/**
     * 根据ID返回工作流对象
     * @param int wf_id
     * @return WorkFlowBean
     * */
	public static WorkFlowBean getWorkFlowBean(int wf_id)
	{
		return (WorkFlowBean)DBManager.queryFObj("getWorkFlowBean", wf_id+"");
	}
	
	/**
     * 插入工作流信息
     * @param WorkFlowBean wfb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertWorkFlow(WorkFlowBean wfb,SettingLogsBean stl){
		
		if(DBManager.insert("insert_workflow", wfb))
		{				
			PublicTableDAO.insertSettingLogs("添加","工作流",wfb.getWf_id()+"",stl);
			return true;
		}else
			return false; 
	}
	
	/**
     * 修改工作流信息
     * @param WorkFlowBean wfb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateWorkFlow(WorkFlowBean wfb,SettingLogsBean stl){
		if(DBManager.update("update_workflow", wfb))
		{				
			PublicTableDAO.insertSettingLogs("修改","工作流",wfb.getWf_id()+"",stl);
			return true;
		}else
			return false; 
	}
	
	/**
     * 删除工作流信息
     * @param String wf_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteWorkFlow(String wf_ids,SettingLogsBean stl){
		Map<String, String> m = new HashMap<String, String>();
		m.put("wf_ids", wf_ids);
		if(DBManager.insert("delete_workflow", m))
		{				
			PublicTableDAO.insertSettingLogs("删除","工作流",wf_ids+"",stl);
			return true;
		}else
			return false; 
	}
	
	/**
     * 插入工作流步骤信息
     * @param WorkFlowStepBean wfsb
     * @return boolean
     * */
	public static boolean insertWorkFlowStep(WorkFlowStepBean wfsb)
	{
		return DBManager.insert("insert_workFlowStep", wfsb);
	}
	
	/**
     * 删除工作流步骤信息
     * @param String wf_id
     * @return boolean
     * */
	public static boolean deleteWorkFlowStep(String wf_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("wf_ids", wf_id);
		return DBManager.insert("delete_workFlowStep", m);
	}
	
	/**
     * 得到所有工作日志列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getAllWorkFlowLogList()
	{
		return DBManager.queryFList("getAllWorkFlowLogList", "");
	}
	
	/**
     * 根据ID返回工作日志对象
     * @param int wf_id
     * @return WorkFlowBean
     * */
	public static WorkFlowLogBean getWorkFlowLogBean(int log_id)
	{
		return (WorkFlowLogBean)DBManager.queryFObj("getWorkFlowLogBean", log_id+"");
	}
	
	/**
     * 插入工作流日志
     * @param WorkFlowLogBean wflb  日志对象
     * @return boolean              是否成功
     * */
	public static boolean insertWorkFlowLog(WorkFlowLogBean wflb){
		
		if(DBManager.insert("insert_workflowlog", wflb))
		{				
			return true;
		}else
			return false; 
		
	}
	
	/**
     * 修改工作流日志
     * @param WorkFlowLogBean wflb  日志对象
     * @return boolean              是否成功
     * */
	public static boolean updateWorkFlowLog(WorkFlowLogBean wflb){
		
		if(DBManager.update("update_workFlowLog", wflb))
		{				
			return true;
		}else
			return false; 
		
	}
	
	/**
     * 删除工作流日志
     * @param WorkFlowLogBean wflb  日志对象
     * @return boolean              是否成功
     * */
	public static boolean deleteWorkFlowLog(String log_ids){
		Map<String, String> m = new HashMap<String, String>();
		m.put("log_ids", log_ids);
		if(DBManager.insert("delete_workFlowLog", m))
		{				
			return true;
		}else
			return false;
		
	}
	
	/**
     * 得到所有信息状态
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getAllInfoStatus()
	{
		return DBManager.queryFList("getAllInfoStatus", "");
	}
	
	/**
     * 根据ID取得指定的信息状态
     * @param int wf_id
     * @return WorkFlowStatusBean
     * */
	public static WorkFlowStatusBean getInfoStatusBean(int status_id)
	{
		return (WorkFlowStatusBean)DBManager.queryFObj("getInfoStatusBean", status_id+"");
	}
	
	/**
     * 插入信息状态
     * @param WorkFlowStatusBean wfsb  状态对象
     * @return boolean              是否成功
     * */
	public static boolean insert_infoStatus(WorkFlowStatusBean wfsb){
		
		if(DBManager.insert("insert_infoStatus", wfsb))
		{				
			return true;
		}else
			return false; 
		
	}
	
	/**
     * 修改信息状态
     * @param WorkFlowStatusBean wfsb  状态对象
     * @return boolean                 是否成功
     * */
	public static boolean update_infoStatus(WorkFlowStatusBean wfsb){
		
		if(DBManager.update("update_infoStatus", wfsb))
		{				
			return true;
		}else
			return false; 
		
	}
	
	/**
     * 删除信息状态
     * @param String status_ids     状态编号
     * @return boolean              是否成功
     * */
	public static boolean delete_infoStatus(String status_ids){
		Map<String, String> m = new HashMap<String, String>();
		m.put("status_ids", status_ids);
		if(DBManager.insert("delete_infoStatus", m))
		{				
			return true;
		}else
			return false;
		
	}
}
