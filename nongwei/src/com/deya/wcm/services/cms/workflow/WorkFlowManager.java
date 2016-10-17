package com.deya.wcm.services.cms.workflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.deya.wcm.bean.cms.workflow.WorkFlowBean;
import com.deya.wcm.bean.cms.workflow.WorkFlowLogBean;
import com.deya.wcm.bean.cms.workflow.WorkFlowStatusBean;
import com.deya.wcm.bean.cms.workflow.WorkFlowStepBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.role.RoleBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.cms.workflow.WorkFlowDAO;
import com.deya.wcm.services.org.role.RoleUserManager;
import com.deya.wcm.services.org.user.UserLogin;


/**
 *  工作流管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 工作流角色管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class WorkFlowManager implements ISyncCatch{
	private static TreeMap<Integer,WorkFlowBean> wf_map = new TreeMap<Integer, WorkFlowBean>();	//工作流缓存
	
	private static int last_verify = 100;
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}

	@SuppressWarnings("unchecked")
	public static void reloadCatchHandl()
	{
		wf_map.clear();
		try{
			List<WorkFlowBean> wf_list = WorkFlowDAO.getAllWorkFlowList();
			if(wf_list != null && wf_list.size() > 0)
			{
				for(int i=0;i<wf_list.size();i++)
				{//System.out.println(wf_list.get(i).getWorkFlowStep_list().size()+""+wf_list.get(i).getWorkFlowStep_list().get(0).getStep_name());
					wf_map.put(wf_list.get(i).getWf_id(), wf_list.get(i));
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始加载工作流信息
	 * 
	 * @param
	 * @return
	 */
	public static void reloadWorkFlow()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.cms.workflow.WorkFlowManager");
	}
	
	/**
     * 获取工作流列表信息
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<WorkFlowBean> getWorkFlowList()
	{
		List<WorkFlowBean> wf_list = new ArrayList<WorkFlowBean>();
		Iterator iter = wf_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();	
			wf_list.add((WorkFlowBean)entry.getValue());		
		}
		return wf_list;
	}
	
	/**
     * 根据ID返回工作流对象
     * @param int wf_id
     * @return WorkFlowBean
     * */
	public static WorkFlowBean getWorkFlowBean(int wf_id)
	{
		if(wf_id == 0)
			return null;
		if(wf_map.containsKey(wf_id))	
		{
			return wf_map.get(wf_id);
		}else
		{
			WorkFlowBean wfb = WorkFlowDAO.getWorkFlowBean(wf_id);
			if(wfb != null){
				wf_map.put(wf_id, wfb);
				return wfb;
			}else
				return null;
		}
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
		//首先判断此人是否是超级管理员或站点,节点管理员
		if(UserLogin.isSiteManager(user_id,app_id,site_id))
		{
			//如果是,直接给出100
			return 100;
		}
		
		int step_id = 0;
		//首先得到角色列表
		List<RoleBean> role_list = RoleUserManager.getRoleListByUserAppSite(user_id,app_id,site_id);
		
		if(role_list != null && role_list.size() > 0)
		{
			String step_ids = "";
			for(int i=0;i<role_list.size();i++)
			{
				String s_ids = getStepIDSbyEFIDAndRoleID(wf_id,role_list.get(i).getRole_id()+"");
				if(s_ids != null && !"".equals(s_ids))
					step_ids += ","+s_ids;				
			}
			
			if(step_ids != null && !"".equals(step_ids))
			{//对数组排下序
				step_ids = step_ids.substring(1);
				if(!"".equals(step_ids))
				{
					String[] tempA = step_ids.split(",");
					Arrays.sort(tempA);
					step_id = Integer.parseInt(tempA[tempA.length-1]);
				}				
			}
			WorkFlowBean wfb = getWorkFlowBean(wf_id);
			if(wfb != null)
			{//判断步骤ID是否为最后一步，如果是返回终审状态值	
				if(step_id == wfb.getWf_steps())
				{
					return last_verify;
				}
			}				
		}
		return step_id;
	}
	
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
		//首先判断此人是否是超级管理员或站点,节点管理员
		if(UserLogin.isSiteManager(user_id,app_id,site_id))
		{
			//如果是,直接给出100
			return last_verify;
		}else
		{
			int step_id = getMaxStepIDByUserID(wf_id, user_id, app_id, site_id);
			if(step_id == last_verify)
				return last_verify;
			else
				return getNextStepID(wf_id,step_id);
		}
	}
	
	/**
     * 根据流程ID和步骤ID得到它下一步的步骤ID
     * @param int wf_id
     * @param int step_id
     * @return int
     * */
	public static int getNextStepID(int wf_id,int step_id)
	{
		WorkFlowBean wfb = getWorkFlowBean(wf_id);
		if(wfb != null)
		{//判断步骤ID是否为最后一步，如果是返回终审状态值，否则＋１			
			if(step_id == wfb.getWf_steps())
			{
				return last_verify;
			}else
				return step_id + 1;
		}else
			return last_verify;
	}
	
	/**
     * 根据流程ID和角色ID得到它所具有的步骤ID
     * @param int wf_id
     * @param String role_id
     * @return String
     * */
	public static String getStepIDSbyEFIDAndRoleID(int wf_id,String role_id)
	{
		String step_ids = "";
		role_id = ","+role_id+",";
		
		WorkFlowBean wfb = getWorkFlowBean(wf_id);
		if(wfb != null)
		{
			List<WorkFlowStepBean> wfs_list = wfb.getWorkFlowStep_list();
			if(wfs_list != null && wfs_list.size() > 0)
			{
				for(int i=0;i<wfs_list.size();i++)
				{
					if(wfs_list.get(i).getRole_id().contains(role_id))
					{
						step_ids += ","+wfs_list.get(i).getStep_id();
					}
				}				
			}
		}
		if(step_ids.length() > 0)
			step_ids = step_ids.substring(1);
		
		return step_ids;
	}
	
	/**
     * 根据角色ID得到它所关联的步骤ID总数
     * @param String role_id
     * @return int
     * */
	public static int getStepCountByRoleID(String role_id)
	{
		role_id = ","+role_id+",";
		Set<Integer> s = wf_map.keySet();
		for(int i:s)
		{
			List<WorkFlowStepBean> wfs_list = wf_map.get(i).getWorkFlowStep_list();
			if(wfs_list != null && wfs_list.size() > 0)
			{
				for(int j=0;j<wfs_list.size();j++)
				{
					if(wfs_list.get(j).getRole_id().contains(role_id))
					{
						return 1;
					}
				}
			}
		}
		return 0;
	}
	
	
	
	
	/**
     * 插入工作流信息
     * @param WorkFlowBean wfb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertWorkFlow(WorkFlowBean wfb,SettingLogsBean stl){
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.WORKFLOW_TABLE_NAME);
		wfb.setWf_id(id);
		if(WorkFlowDAO.insertWorkFlow(wfb, stl)){
			//插入步骤信息
			if(insertWorkFlowStep(id,wfb.getWorkFlowStep_list()) == false)
			{
				//操作失败，删除工作流主表和步骤表
				WorkFlowDAO.deleteWorkFlow(id+"", stl);
				WorkFlowDAO.deleteWorkFlowStep(id+"");
				return false;
			}else
			{
				reloadWorkFlow();			
				return true;
			}
			
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
		if(WorkFlowDAO.updateWorkFlow(wfb, stl)){
			//先删除工作流步骤表
			WorkFlowDAO.deleteWorkFlowStep(wfb.getWf_id()+"");
			//插入步骤信息
			if(insertWorkFlowStep(wfb.getWf_id(),wfb.getWorkFlowStep_list()) == false)
			{
				WorkFlowDAO.deleteWorkFlowStep(wfb.getWf_id()+"");
				return false;
			}else{
				reloadWorkFlow();
				return true;
			}
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
		if(WorkFlowDAO.deleteWorkFlow(wf_ids, stl)){
			WorkFlowDAO.deleteWorkFlowStep(wf_ids);
			reloadWorkFlow();
			return true;
		}else
			return false;
	}
	
	/**
     * 插入工作流步骤信息
     * @param int wf_id
     * @param List<WorkFlowStepBean> wfs_list
     * @return boolean
     * */
	public static boolean insertWorkFlowStep(int wf_id,List<WorkFlowStepBean> wfs_list)
	{
		if(wfs_list != null && wfs_list.size() > 0)
		{
			for(int i=0;i<wfs_list.size();i++)
			{
				wfs_list.get(i).setWf_id(wf_id);
				if(!wfs_list.get(i).getRole_id().startsWith(","))
					wfs_list.get(i).setRole_id(","+wfs_list.get(i).getRole_id());
				if(!wfs_list.get(i).getRole_id().endsWith(","))
					wfs_list.get(i).setRole_id(wfs_list.get(i).getRole_id()+",");
				if(WorkFlowDAO.insertWorkFlowStep(wfs_list.get(i)) == false)
					return false;
			}
		}
		return true;
	}
	
	/************************** 工作流日志记录 开始 ****************************************/
	/**
     * 获取所有工作流日志信息
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<WorkFlowLogBean> getWorkFlowLogList()
	{
		List<WorkFlowLogBean> wfl_list = WorkFlowDAO.getAllWorkFlowLogList();
		return wfl_list;
	}
	
	/**
     * 根据ID返回工作流日志对象
     * @param int wf_id
     * @return WorkFlowBean
     * */
	public static WorkFlowLogBean getWorkFlowLogBean(int log_id)
	{
		WorkFlowLogBean wflb = WorkFlowDAO.getWorkFlowLogBean(log_id);
		return wflb;
	}
	
	/**
     * 插入工作流日志
     * @param WorkFlowLogBean wflb   日志对象
     * @return boolean               是否成功
     * */
	public static boolean insertWorkFlowLog(WorkFlowLogBean wflb){
		//得到下一个日志ID
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.WORKFLOW_LOG_TABLE_NAME);
		wflb.setLog_id(id);
		if(WorkFlowDAO.insertWorkFlowLog(wflb)){
			return true;
		}else{
			return false;
		}
			
	}
	
	/**
     * 修改工作流日志
     * @param WorkFlowLogBean wflb  日志对象
     * @return boolean              是否成功
     * */
	public static boolean updateWorkFlowLog(WorkFlowLogBean wflb){
		if(WorkFlowDAO.updateWorkFlowLog(wflb)){
			return true;
		}else
			return false;
	}
	
	/**
     * 删除工作流日志
     * @param String log_ids        日志编号
     * @return boolean              是否成功
     * */
	public static boolean deleteWorkFlowLog(String log_ids){
		if(WorkFlowDAO.deleteWorkFlowLog(log_ids)){
			return true;
		}else{
			return false;
		}
	}
	
	
	/************************** 工作流日志记录 结束 ****************************************/
	/************************** 工作流信息状态 开始 ****************************************/
	/**
     * 获取所有工作流信息状态信息
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<WorkFlowStatusBean> getWorkFlowStatusList()
	{
		List<WorkFlowStatusBean> wfsl_list = WorkFlowDAO.getAllInfoStatus();
		return wfsl_list;
	}
	
	/**
     * 根据ID返回工作流信息状态对象
     * @param int wf_id
     * @return WorkFlowStatusBean
     * */
	public static WorkFlowStatusBean getWorkFlowStatusBean(int status_id)
	{
		WorkFlowStatusBean wflb = WorkFlowDAO.getInfoStatusBean(status_id);
		return wflb;
	}
	
	/**
     * 插入工作流信息状态
     * @param WorkFlowStatusBean wflb   信息状态对象
     * @return boolean               是否成功
     * */
	public static boolean insertWorkFlowStatus(WorkFlowStatusBean wfsb){
		
		if(WorkFlowDAO.insert_infoStatus(wfsb)){
			return true;
		}else{
			return false;
		}
			
	}
	
	/**
     * 修改工作流信息状态
     * @param WorkFlowStatusBean wfsb  信息状态对象
     * @return boolean              是否成功
     * */
	public static boolean updateWorkFlowStatus(WorkFlowStatusBean wfsb){
		if(WorkFlowDAO.update_infoStatus(wfsb)){
			return true;
		}else
			return false;
	}
	
	/**
     * 删除工作流信息状态
     * @param String log_ids        信息状态编号
     * @return boolean              是否成功
     * */
	public static boolean deleteWorkFlowStatus(String status_ids){
		if(WorkFlowDAO.delete_infoStatus(status_ids)){
			return true;
		}else
			return false;
	}
	/************************** 工作流信息状态 结束 ****************************************/
	public static void main(String[] args)
	{
		System.out.println(getMaxStepIDByUserID(33,"116","cms",""));
	}
	
}
