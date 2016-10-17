package com.deya.wcm.dao.appeal.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.model.CPFrom;
import com.deya.wcm.bean.appeal.model.ModelBean;
import com.deya.wcm.bean.appeal.model.ModelReleDept;
import com.deya.wcm.bean.appeal.model.ModelReleUser;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  诉求业务处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 诉求业务处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author yuduochao
 * @version 1.0
 * * 
 */

public class ModelDAO {
	/**
     * 得到所有业务列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<ModelBean> getAllModelList()
	{
		return DBManager.queryFList("getAllSQModelList", "");
	}
	
	/**
     * 根据条件得到总数
     * @param Map<String,String> m
     * @return String
     * */
	public static String getSQModelCount(Map<String,String> m)
	{
		return DBManager.getString("getSQModelCount", m);
	}
	
	/**
     * 根据条件得到列表
     * @param Map<String,String> m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<ModelBean> getSQModelListForDB(Map<String,String> m)
	{
		return DBManager.queryFList("getSQModelListForDB", m);
	}
	
	
	/**
	 * 根据业务ID得到对象
	 * @param model_id
	 * @return  ob
	 */
	public static ModelBean getModelBean(int model_id)
	{
		return (ModelBean)DBManager.queryFObj("getSQModelBean", model_id);
	}
	/**
	 * 新增业务
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
	public static boolean insertModel(ModelBean ob,SettingLogsBean stl)
	{
		if(DBManager.insert("insert_SQModel", ob)){
			PublicTableDAO.insertSettingLogs("添加","诉求业务",ob.getModel_id()+"",stl);
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 修改诉求业务
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
	public static boolean updateModel(ModelBean ob,SettingLogsBean stl)
	{
		if(DBManager.update("update_SQModel", ob))
		{
		  PublicTableDAO.insertSettingLogs("修改", "诉求业务", ob.getModel_id()+"", stl);
	      return true;
		}else{
		  return false;
		}
	}
	
	/**
	 * 删除诉求业务
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
	public static boolean deleteModel(String model_ids,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("model_ids", model_ids);
		if(DBManager.delete("delete_SQModel", m))
		{
			PublicTableDAO.insertSettingLogs("删除", "诉求业务", model_ids, stl);
			return true;
		}else
			return false;
	}
	
	/*********************** 业务与部门关联 开始 *****************************/
	/**
     * 取得业务与部门关联列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<ModelReleDept> getModelReleDeptList()
	{
		return DBManager.queryFList("getModelReleDeptList", "");
	}
	
	/**
     * 插入业务与部门关联列表
     * @param ModelReleDept mrd
     * @return boolean
     * */
	public static boolean insertModelReleDept(ModelReleDept mrd)
	{
		return DBManager.insert("insert_SQModel_dept", mrd);
	}
	
	/**
     * 删除业务与部门关联列表
     * @param 
     * @return boolean
     * */
	public static boolean deleteModelReleDept(Map<String,String> m)
	{
		return DBManager.delete("delete_SQModel_dept", m);
	}
	/*********************** 业务与部门关联 结束 *****************************/	
	
	
	/*********************** 业务与人员关联 开始 *****************************/
	/**
     * 取得业务与用户关联列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<ModelReleUser> getModelReleUserList()
	{
		return DBManager.queryFList("getModelReleUserList", "");
	}
	
	/**
     * 插入业务与用户关联列表(以人员为主)
     * @param ModelReleUser mrd
     * @return boolean
     * */
	public static boolean insertModelReleUser(String model_ids,int user_id,SettingLogsBean stl)
	{
		//先删除
		Map<String,String> m = new HashMap<String,String>();
		m.put("user_ids", user_id+"");//根据人员ID删除业务关联
		if(ModelDAO.deleteModelReleUser(m))
		{
			if(model_ids != null && !"".equals(model_ids))
			{
				try{
					String[] model_tempA = model_ids.split(",");					
					ModelReleUser mru = new ModelReleUser();
					mru.setUser_id(user_id);
					for(int i=0;i<model_tempA.length;i++)
					{
						mru.setModel_id(Integer.parseInt(model_tempA[i]));						
						DBManager.insert("insert_SQModel_user", mru);
					}	
					PublicTableDAO.insertSettingLogs("添加","诉求系统用户与业务关联用户",user_id+"",stl);
				}catch(Exception e)
				{
					e.printStackTrace();
					return false;
				}
			}			
			return true;
		}else
			return false;	
	}
	
	/**
     * 插入业务与用户关联列表(以业务为主)
     * @param ModelReleUser mrd
     * @return boolean
     * */
	public static boolean insertModelReleUserByModel(int model_id,String user_ids,SettingLogsBean stl)
	{
		//先删除
		Map<String,String> m = new HashMap<String,String>();
		m.put("model_ids", model_id+"");//根据人员ID删除业务关联
		if(ModelDAO.deleteModelReleUser(m))
		{
			if(user_ids != null && !"".equals(user_ids))
			{
				try{
					String[] user_tempA = user_ids.split(",");					
					ModelReleUser mru = new ModelReleUser();
					mru.setModel_id(model_id);
					for(int i=0;i<user_tempA.length;i++)
					{						
						mru.setUser_id(Integer.parseInt(user_tempA[i]));
						DBManager.insert("insert_SQModel_user", mru);
					}	
					PublicTableDAO.insertSettingLogs("添加","诉求系统用户与业务关联 业务",model_id+"",stl);
				}catch(Exception e)
				{
					e.printStackTrace();
					return false;
				}
			}			
			return true;
		}else
			return false;	
	}
	
	/**
     * 删除业务与用户关联列表
     * @param 
     * @return boolean
     * */
	public static boolean deleteModelReleUser(Map<String,String> m)
	{
		return DBManager.delete("delete_SQModel_user", m);
	}
	/*********************** 业务与人员关联 结束 *****************************/
	
	/*********************** 业务与扩展字段关联 开始 *****************************/
	@SuppressWarnings("unchecked")
	public static List<CPFrom> getAllCPFormList()
	{
		return DBManager.queryFList("getAllCPFormList", "");
	}
	@SuppressWarnings("unchecked")
	public static List<CPFrom> getCPFormListByModel(int model_id)
	{
		return DBManager.queryFList("getCPFormListByModel", model_id);
	}
	
	public static boolean insertCPFrom(int model_id,List<CPFrom> l,SettingLogsBean stl)
	{
		if(deleteCPFrom(model_id+""))
		{
			if(l != null && l.size() > 0)
			{
				try{
					for(int i=0;i<l.size();i++)
					{
						l.get(i).setField_id(i+1);
						l.get(i).setModel_id(model_id);
						DBManager.insert("insert_cp_from", l.get(i));
					}					
				}catch(Exception e)
				{
					e.printStackTrace();
					return false;
				}
			}
			PublicTableDAO.insertSettingLogs("修改","诉求系统扩展字段 业务",model_id+"",stl);
			return true;
		}else
			return false;
	}
	
	public static boolean deleteCPFrom(String model_ids)
	{
		Map m = new HashMap();
		m.put("model_id", model_ids);
		return DBManager.delete("delete_cp_from", m);
	}
	/*********************** 业务与扩展字段关联 结束 *****************************/
	
	public static void main(String[] args)
	{
		Map<String,String> m = new HashMap();
		m.put("model_ids", "1,2");
		m.put("dept_ids", "1,2");
		deleteModelReleDept(m);
	}
}
