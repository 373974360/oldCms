package com.deya.wcm.dao.system.formodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.formodel.ModelFieldBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  内容模型字段管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 内容模型字段管理数据处理类,sql在 formodel.xml 中</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class ModelFieldDAO {
	/**
     * 得到所有内容模型字段信息列表
     * @param 
     * @return List
     * */	
	@SuppressWarnings("unchecked")
	public static List<ModelFieldBean> getAllModelFieldList()
	{
		return DBManager.queryFList("getAllModelFieldList", "");
	}
	
	/**
     * 得到所有内容模型字段信息
     * @param 
     * @return List
     * */
	public static ModelFieldBean getModelFieldBean(int modelField_id)
	{
		return (ModelFieldBean)DBManager.queryFObj("getModelFieldBean", modelField_id);
	}
	
	/**
     * 插入内容模型字段信息
     * @param ModelFieldBean mfb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertModelField(ModelFieldBean mfb,SettingLogsBean stl){
		int modelField_id = PublicTableDAO.getIDByTableName(PublicTableDAO.FORMODELFIELD_TABLE_NAME);
		mfb.setField_id(modelField_id);
		if(DBManager.insert("insert_modelField", mfb))
		{
			PublicTableDAO.insertSettingLogs("添加","内容模型字段",modelField_id+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 修改内容模型字段信息
     * @param ModelFieldBean mfb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateModelField(ModelFieldBean mfb,SettingLogsBean stl){
		if(DBManager.update("update_modelField", mfb))
		{
			PublicTableDAO.insertSettingLogs("修改","内容模型字段",mfb.getField_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 删除内容模型字段信息
     * @param String field_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteModelField(String field_ids,SettingLogsBean stl){
		Map<String,String> m = new HashMap<String,String>();
		m.put("field_ids", field_ids);
		m.put("table_name", PublicTableDAO.INFO_UDEFINED_TABLE_NAME);//只允许删除cs_info_udefined这张表的字段
		if(DBManager.delete("delete_modelField", m))
		{
			PublicTableDAO.insertSettingLogs("删除","内容模型字段",field_ids,stl);
			return true;
		}else
			return false;
	}
}
