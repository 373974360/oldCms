package com.deya.wcm.dao.system.formodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.formodel.ModelBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  内容模型管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 内容模型管理数据处理类,sql在 formodel.xml 中</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class ModelDAO {
	/**
     * 得到所有内容模型信息
     * @param 
     * @return List
     * */	
	@SuppressWarnings("unchecked")
	public static List<ModelBean> getAllModelList()
	{
		return DBManager.queryFList("getAllFormModelList", "");
	}
	
	/**
	 * 得到内容模型对象
	 * 
	 * @param int model_id
	 * @return ModelBean 
	 */
	public static ModelBean getModelBean(int model_id)
	{
		return (ModelBean)DBManager.queryFObj("getModelBean", model_id+"");
	}
	
	/**
     * 插入内容模型信息
     * @param ModelBean mb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertModel(ModelBean mb,SettingLogsBean stl){
		int model_id = PublicTableDAO.getIDByTableName(PublicTableDAO.FORMODEL_TABLE_NAME);
		mb.setModel_id(model_id);
		if(DBManager.insert("insert_formModel", mb))
		{
			PublicTableDAO.insertSettingLogs("添加","内容模型",model_id+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 修改内容模型信息
     * @param ModelBean mb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateModel(ModelBean mb,SettingLogsBean stl){
		if(DBManager.update("update_formModel", mb))
		{
			PublicTableDAO.insertSettingLogs("修改","内容模型",mb.getModel_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 删除内容模型信息
     * @param String model_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteModel(String model_ids,SettingLogsBean stl){
		Map<String,String> m = new HashMap<String,String>();
		m.put("model_ids", model_ids);
		if(DBManager.delete("delete_formModel", m))
		{
			PublicTableDAO.insertSettingLogs("删除","内容模型",model_ids,stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 修改内容模型状态
     * @param String model_ids
     * @param String disabled
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateModelDisabled(String model_ids,String disabled,SettingLogsBean stl){
		Map<String,String> m = new HashMap<String,String>();
		m.put("model_ids", model_ids);
		m.put("disabled", disabled);
		if(DBManager.delete("update_model_disabled", m))
		{
			PublicTableDAO.insertSettingLogs("修改","内容模型状态",model_ids,stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 保存排序
     * @param String model_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public static boolean saveModelSort(String model_ids,SettingLogsBean stl)
	{
		if(model_ids != null && !"".equals(model_ids))
		{
			try{
				Map<String, Comparable> m = new HashMap<String, Comparable>();
				String[] tempA = model_ids.split(",");
				for(int i=0;i<tempA.length;i++)
				{
					m.put("model_sort", (i+1));
					m.put("model_id", tempA[i]);
					DBManager.update("sort_model", m);
				}
				PublicTableDAO.insertSettingLogs("保存排序","内容模型",model_ids,stl);
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else
			return true;
	}
}
