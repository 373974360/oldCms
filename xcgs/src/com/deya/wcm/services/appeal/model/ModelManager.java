package com.deya.wcm.services.appeal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.deya.wcm.bean.appeal.cpDept.CpDeptBean;
import com.deya.wcm.bean.appeal.cpLead.CpLeadBean;
import com.deya.wcm.bean.appeal.cpUser.CPUserReleInfo;
import com.deya.wcm.bean.appeal.model.ModelBean;
import com.deya.wcm.bean.appeal.model.ModelReleDept;
import com.deya.wcm.bean.appeal.model.ModelReleUser;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.appeal.model.ModelDAO;
import com.deya.wcm.dao.appeal.sq.SQDAO;
import com.deya.wcm.services.appeal.cpDept.CpDeptManager;
import com.deya.wcm.services.appeal.cpDept.CpDeptManager.CpDeptComparator;
import com.deya.wcm.services.appeal.cpLead.CpLeadManager;
import com.deya.wcm.services.appeal.cpUser.CpUserManager;
import com.deya.wcm.services.appeal.sq.SQManager;
import com.deya.wcm.services.cms.workflow.WorkFlowManager;
import com.deya.wcm.services.org.user.UserManager;
/**
 *  诉求业务逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 诉求业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author yuduochao
 * @version 1.0
 * * 
 */
public class ModelManager implements ISyncCatch{
	
	private static TreeMap<Integer,ModelBean> model_map = new TreeMap<Integer, ModelBean>();
	private static List<ModelReleDept> m_dept_list = new ArrayList<ModelReleDept>();
	private static List<ModelReleUser> m_user_list = new ArrayList<ModelReleUser>();
		
	static{
		reloadCatchHandl();
		
		//reloadModel();
		//reloadModelDeptList();
		//reloadModelUserList();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		List<ModelBean> model_list = ModelDAO.getAllModelList(); 
		model_map.clear();
		if (model_list != null && model_list.size() > 0) {
			for (int i = 0; i < model_list.size(); i++) {				
				model_map.put(model_list.get(i).getModel_id(), model_list.get(i));  
			}  
		}
		
		m_dept_list.clear();
    	m_dept_list = ModelDAO.getModelReleDeptList();
    	
    	m_user_list.clear();
    	m_user_list = ModelDAO.getModelReleUserList();
	}
	/**
	 * 初始化
	 * @param
	 * @return
	 */
	public static void reloadModel() {
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.appeal.model.ModelManager");
	}
	
	/**
     * 根据业务ID，人员ID得到它所以看到的最大流程步骤ID
     * @param String model_id
     * @param String user_id
     * @return int
     * */
	public static int getWFIDByMIDUserID(String model_id,String user_id)
	{
		try{
			ModelBean mb = getModelBean(Integer.parseInt(model_id));
			if(mb != null)
				return WorkFlowManager.getMaxStepIDByUserID(mb.getWf_id(),user_id,"appeal","");
			else
				return 0;
		}catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
		
	/**
     * 得到所有业务列表
     * @param 
     * @return List
     * */
	public static List<ModelBean> getAllSQModelList()
	{
		List<ModelBean> model_list = new ArrayList<ModelBean>();; 
		Set<Integer> set = model_map.keySet();
		
		for(int i : set){
			model_list.add(model_map.get(i));				
		}
		return model_list;
	}
	
	/**
     * 根据条件得到总数
     * @param Map<String,String> m
     * @return String
     * */
	public static String getSQModelCount(Map<String,String> m)
	{
		return ModelDAO.getSQModelCount(m);
	}
	
	/**
     * 根据条件得到列表
     * @param Map<String,String> m
     * @return List
     * */
	public static List<ModelBean> getSQModelListForDB(Map<String,String> m)
	{
		return ModelDAO.getSQModelListForDB(m);
	}
	
	/**
	 * 根据多个业务ID得到业务名称
	 * @param model_id
	 * @return String
	 */
	public static String getModelNamebyIDS(String model_ids)
	{
		String names = "";
		if(model_ids != null && !"".equals(model_ids))
		{	
			String[] tempA = model_ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				ModelBean mb = getModelBean(Integer.parseInt(tempA[i]));
				if(mb != null)
				{
					names += ","+mb.getModel_cname();
				}
			}
			if(names != null && !"".equals(names.trim()))
				names = names.substring(1);
		}
		return names;
	}
	
	/**
     * 根据业务ID得到部门信息 
     * @param int model_id
     * @return Map
     * */
    public static Map<String,String> getModelDeptMapByMID(int model_id)
    {
    	Map<String,String> m = new HashMap<String,String>();
    	if(m_dept_list != null && m_dept_list.size() > 0)
    	{
    		for(int i=0;i<m_dept_list.size();i++)
    		{
    			if(m_dept_list.get(i).getModel_id() == model_id)
    			{
    				CpDeptBean cdb = CpDeptManager.getCpDeptBean(m_dept_list.get(i).getDept_id());   
    				if(cdb != null) 
    					m.put(cdb.getDept_id()+"", cdb.getDept_name());
    			}    				
    		}    		
    	}
    	return m;
    }
	
	/**
	 * 根据业务ID返回对象
	 * @param model_id
	 * @return ModelBean
	 */
    public static ModelBean getModelBean(int model_id)
    {
    	if(model_map.containsKey(model_id))
    	{
    		return model_map.get(model_id);
    	}else{
    		ModelBean ob = ModelDAO.getModelBean(model_id);
    		if(ob != null)
    			model_map.put(model_id, ob);
    		return ob;
    	}
    }
	/**
    * 得到业务ID,用于添加页面
    * @param
    * @return String
    * */
	public static int getModelID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.APPEAL_MODEL_TABLE_NAME);
	}
	/**
	 * 新增业务配置
	 * @param ob
	 * @param stl
	 * @param String dept_ids
	 * @return boolean
	 */
    public static boolean insertModel(ModelBean ob,String dept_ids,SettingLogsBean stl)
    {
    	if(ModelDAO.insertModel(ob, stl))
		{
			reloadModel();
			insertModelReleDept(ob.getModel_id(),dept_ids);
			return true;
		}else{
			return false;
		}
    }
    /**
     * 修改业务信息
     * @param ModelBean ob
     * @param String dept_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
    public static boolean updateModel(ModelBean ob,String dept_ids,SettingLogsBean stl)
	{
		if(ModelDAO.updateModel(ob, stl))
		{
			reloadModel();
			insertModelReleDept(ob.getModel_id(),dept_ids);
			return true;
		}else{
			return false;
		}
	}
    
    /**
	 * 删除业务信息
	 * @param Model_id
	 * @param request
	 * @return
	 */
    public static boolean deleteModel(String model_ids,SettingLogsBean stl)
    {
    	if(ModelDAO.deleteModel(model_ids, stl))
		{
			reloadModel();
			SQDAO.deleteSQByModel(model_ids);
			CPFromManager.deleteCPFrom(model_ids);
			deleteModelReleDept(model_ids);
			deleteModelReleUserByModel(model_ids);
			return true;
		}else{
			return false;
		}
    }
    
    /*********************** 业务与部门关联 开始 *****************************/
    /**
     * 初始加载业务与部门关联列表
     * @param 
     * @return 
     * */
    public static void reloadModelDeptList()
    {
    	SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.appeal.model.ModelManager");
    }
    
    /**
     * 根据业务ID得到部门信息 
     * @param int model_id
     * @return List
     * */
    public static List<CpDeptBean> getModelDeptListByMID(int model_id)
    {
    	ModelBean mb = getModelBean(model_id);
    	if(mb != null)
    	{
	    	List<CpDeptBean> cdList = new ArrayList<CpDeptBean>();
	    	if(m_dept_list != null && m_dept_list.size() > 0)
	    	{
	    		for(int i=0;i<m_dept_list.size();i++)
	    		{
	    			if(m_dept_list.get(i).getModel_id() == model_id)
	    			{
	    				if(mb.getRelevance_type() == 0)
	    				{//如果是部门信箱用此方法
		    				CpDeptBean cdb = CpDeptManager.getCpDeptBean(m_dept_list.get(i).getDept_id());   
		    				if(cdb != null) 
		    					cdList.add(cdb);
	    				}else
	    				{//如果是领导信箱用此方法，先得到领导人的信息，再存入部门对象中
	    					CpLeadBean lead = CpLeadManager.getCpLeadById(m_dept_list.get(i).getDept_id()+"");
	        				if(lead != null) 
	        				{
	        					//为保证信息的前台对象的统一，将领导人的信息存入部门对象中
	        					CpDeptBean cdb = new CpDeptBean();
	        					cdb.setDept_id(lead.getDept_id());
	        					cdb.setDept_name(lead.getLead_name());
	        					cdb.setSort_id(lead.getSort_id());
	        					cdb.setDept_memo(lead.getLead_memo());
	        					cdList.add(cdb);
	        				}    
	    				}
	    			}    				
	    		}  
	    		Collections.sort(cdList,new CpDeptComparator());
	    	}
	    	return cdList;
    	}else
    		return null;
    } 
    
    /**
     * 根据业务ID得到领导人列表 
     * @param int model_id
     * @return List
     * */
    public static List<CpLeadBean> getModelLeadListByMID(int model_id)
    {
    	List<CpLeadBean> l = new ArrayList<CpLeadBean>();
    	if(m_dept_list != null && m_dept_list.size() > 0)
    	{
    		for(int i=0;i<m_dept_list.size();i++)
    		{
    			if(m_dept_list.get(i).getModel_id() == model_id)
    			{
    				CpLeadBean lead = CpLeadManager.getCpLeadById(m_dept_list.get(i).getDept_id()+"");
    				if(lead != null) 
    					l.add(lead);
    			}
    		}
    	}
    	return l;
    }
    
    /**
     * 根据业务ID得到部门ID
     * @param int model_id
     * @return String
     * */
    public static String getModelDeptIDSByMID(int model_id)
    {
    	String dept_ids = "";
    	if(m_dept_list != null && m_dept_list.size() > 0)
    	{
    		for(int i=0;i<m_dept_list.size();i++)
    		{
    			if(m_dept_list.get(i).getModel_id() == model_id)
    				dept_ids += ","+m_dept_list.get(i).getDept_id();
    		}
    		if(dept_ids.length() > 0)
    			dept_ids = dept_ids.substring(1);
    	}
    	return dept_ids;
    }
    
    /**
     * 插入业务与部门关联列表
     * @param ModelReleDept mrd
     * @return boolean
     * */
	public static boolean insertModelReleDept(int model_id,String dept_ids)
	{
		//先删除
		Map<String,String> m = new HashMap<String,String>();
		m.put("model_ids", model_id+"");
		if(ModelDAO.deleteModelReleDept(m))
		{
			if(dept_ids != null && !"".equals(dept_ids))
			{
				try{
					String[] tempA = dept_ids.split(",");
					ModelReleDept drd = new ModelReleDept();
					drd.setModel_id(model_id);
					for(int i=0;i<tempA.length;i++)
					{
						drd.setDept_id(Integer.parseInt(tempA[i]));
						ModelDAO.insertModelReleDept(drd);
					}					
				}catch(Exception e)
				{
					e.printStackTrace();
					return false;
				}
			}
			reloadModelDeptList();
			return true;
		}else
			return false;
	}
	
	public static boolean deleteModelReleDept(String model_ids)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("model_ids", model_ids);
		if(ModelDAO.deleteModelReleDept(m))
		{
			reloadModelDeptList();
			return true;
		}else
			return false;
	}
    /*********************** 业务与部门关联 结束 *****************************/
    
    /*********************** 业务与人员关联 开始 *****************************/
	/**
     * 初始加载业务与部门关联列表
     * @param 
     * @return 
     * */
    public static void reloadModelUserList()
    {
    	SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.appeal.model.ModelManager");
    }
    
    /**
     * 根据业务ID得到部门信息 
     * @param int model_id
     * @return Map
     * */
    public static Map<String,String> getModelUserMapByMID(int model_id)
    {
    	Map<String,String> m = new HashMap<String,String>();
    	if(m_dept_list != null && m_dept_list.size() > 0)
    	{
    		for(int i=0;i<m_dept_list.size();i++)
    		{
    			if(m_dept_list.get(i).getModel_id() == model_id)
    			{
    				UserBean ub = UserManager.getUserBeanByID(m_dept_list.get(i).getDept_id()+"");
    				if(ub != null)    								
    					m.put(ub.getUser_id()+"", ub.getUser_realname());
    			}    				
    		}    		
    	}
    	return m;
    }
    
    /**
     * 根据业务ID得到领导人信息 
     * @param int model_id
     * @return Map
     * */
    public static Map<String,String> getModelLeadMapByMID(int model_id)
    {
    	Map<String,String> m = new HashMap<String,String>();
    	if(m_dept_list != null && m_dept_list.size() > 0)
    	{
    		for(int i=0;i<m_dept_list.size();i++)
    		{
    			if(m_dept_list.get(i).getModel_id() == model_id)
    			{
    				CpLeadBean lead = CpLeadManager.getCpLeadById(m_dept_list.get(i).getDept_id()+"");
    				if(lead != null) 
    					m.put(lead.getLead_id()+"", lead.getLead_name());
    			}    				
    		}    		
    	}
    	return m;
    }
    
    /**
     * 根据业务ID得到信件管理员用户列表
     * @param int model_id
     * @return String
     * */
    public static List<UserBean> getAdminUserByMID(int model_id)
    {
    	List<UserBean> uList = new ArrayList<UserBean>(); 
    	if(m_user_list != null && m_user_list.size() > 0)
    	{
    		for(int i=0;i<m_user_list.size();i++)
    		{
    			if(m_user_list.get(i).getModel_id() == model_id && SQManager.isAppealManager(m_user_list.get(i).getUser_id()))
    				uList.add(UserManager.getUserBeanByID(m_user_list.get(i).getUser_id()+""));
    		}
    	}
    	return uList;
    }
    
    /**
     * 根据业务ID得到人员ID
     * @param int model_id
     * @return String
     * */
    public static String getModelUserIDSByMID(int model_id)
    {
    	String user_ids = "";
    	if(m_user_list != null && m_user_list.size() > 0)
    	{
    		for(int i=0;i<m_user_list.size();i++)
    		{
    			if(m_user_list.get(i).getModel_id() == model_id)
    				user_ids += ","+m_user_list.get(i).getUser_id();
    		}
    		if(user_ids.length() > 0)
    			user_ids = user_ids.substring(1);
    	}
    	return user_ids;
    }
    
    /**
     * 根据人员ID得到业务ID
     * @param int model_id
     * @return String
     * */
    public static String getModelIDSByUserID(int user_id)
    {
    	String model_ids = "";
    	if(m_user_list != null && m_user_list.size() > 0)
    	{
    		for(int i=0;i<m_user_list.size();i++)
    		{
    			if(m_user_list.get(i).getUser_id() == user_id)
    				model_ids += ","+m_user_list.get(i).getModel_id();
    		}
    		if(model_ids.length() > 0)
    			model_ids = model_ids.substring(1);
    	}
    	if("".equals(model_ids))
    		model_ids = "0";
    	return model_ids;
    }
    
    /**
     * 根据人员ID得到业务列表
     * @param int model_id
     * @return List
     * */
    public static List<ModelBean> getModelListSByUserID(int user_id)
    {
    	List<ModelBean> m_list = new ArrayList<ModelBean>();
    	if(m_user_list != null && m_user_list.size() > 0)
    	{
    		for(int i=0;i<m_user_list.size();i++)
    		{
    			if(m_user_list.get(i).getUser_id() == user_id)
    			{
    				m_list.add(model_map.get(m_user_list.get(i).getModel_id()));
    			}    				
    		}
    	}
    	
    	return m_list;
    }
    
    /**
     * 插入业务与人员关联列表(以人员为主)
     * @param ModelReleUser mrd
     * @return boolean
     * */
	public static boolean insertModelReleUser(String model_ids,int user_id,SettingLogsBean stl)
	{
		if(ModelDAO.insertModelReleUser(model_ids, user_id, stl))
		{
			reloadModelUserList();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 插入业务与用户关联列表(以业务为主)
     * @param ModelReleUser mrd
     * @return boolean
     * */
	public static boolean insertModelReleUserByModel(int model_id,String user_ids,SettingLogsBean stl)
	{
		if(ModelDAO.insertModelReleUserByModel(model_id, user_ids, stl))
		{
			reloadModelUserList();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 删除业务与用户关联列表
     * @param 
     * @return boolean
     * */
	public static boolean deleteModelReleUser(String user_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("user_ids", user_id+"");
		if(ModelDAO.deleteModelReleUser(m))
		{
			reloadModelUserList();
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean deleteModelReleUserByModel(String model_ids)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("model_ids", model_ids);
		if(ModelDAO.deleteModelReleUser(m))
		{
			reloadModelUserList();
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
     * 根据业务ID得到该业务信件管理员的手机号码,用于短信发送
     * @param int model_id
     * @return String
     * */
	public static String getUserPhoneByModelID(int model_id)
	{
		String phones = "";
		List<UserBean> ul = ModelManager.getAdminUserByMID(model_id);//得到该业务下的信件管理员
		if(ul != null && ul.size() > 0)
		{
			for(UserBean ub : ul)
			{
				if(ub.getPhone() != null && !"".equals(ub.getPhone()))
					phones = ";"+ub.getPhone();
			}
			
			if(phones != null && !"".equals(phones))
			{
				phones = phones.substring(1);
			}
		}
		return phones;
	}
	
	/**
     * 根据部门ID得到该部门处理员的手机号码,用于短信发送
     * @param int dept_id
     * @param int model_id
     * @return String
     * */
	public static String getUserPhoneByDeptID(int dept_id,int model_id)
	{
		String phones = "";
		//首先根据部门找到所有的用户
		List<CPUserReleInfo> cul = CpUserManager.getUserListByDept(dept_id);
		if(cul != null && cul.size() > 0)
		{
			String model_user = ","+ModelManager.getModelUserIDSByMID(model_id)+",";//根据业务得到所有人员
			for(CPUserReleInfo cub : cul)
			{
				if(model_user.contains(","+cub.getUser_id()+","))
				{
					String phone = UserManager.getUserBeanByID(cub.getUser_id()+"").getPhone();
					if(phone != null && !"".equals(phone))
						phones = ";"+phone;
				}
			}
			if(phones != null && !"".equals(phones))
			{
				phones = phones.substring(1);
			}
		}
		return phones;
	}
	
    /*********************** 业务与人员关联 结束 *****************************/
    
	
	/**
     * 查找所有分类中任意的模板ID,为应对传入的分类ID为空而找不到对应的模板
     * @param String template_type
     * @return String
     * */
	public static String getModelTemplate(String template_type)
	{
		Set<Integer> s = model_map.keySet();
		for(int i : s)
		{
			ModelBean mb =  model_map.get(i);
			if("form".equals(template_type))
			{
				if(mb.getTemplate_form() != 0)
					return mb.getTemplate_form()+"";
			}
			
			if("list".equals(template_type))
			{
				if(mb.getTemplate_list() != 0)
					return mb.getTemplate_list()+"";
			}
			
			if("content".equals(template_type))
			{
				if(mb.getTemplate_content() != 0)
					return mb.getTemplate_content()+"";
			}
			
			if("print".equals(template_type))
			{
				if(mb.getTemplate_print() != 0)
					return mb.getTemplate_print()+"";
			}
			
			if("search".equals(template_type))
			{
				if(mb.getTemplate_search_list() != 0)
					return mb.getTemplate_search_list()+"";
			}
		}
		return "";
	}
    public static void main(String[] args)
    {
    	System.out.println(ModelManager.getAdminUserByMID(25));
    }
}
