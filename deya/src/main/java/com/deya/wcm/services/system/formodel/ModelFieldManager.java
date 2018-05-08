package com.deya.wcm.services.system.formodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.formodel.ModelFieldBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.system.formodel.ModelFieldDAO;

/**
 *  内容模型字段管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 内容模型字段管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class ModelFieldManager implements ISyncCatch{
	private static TreeMap<Integer,ModelFieldBean> mf_map = new TreeMap<Integer, ModelFieldBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		List<ModelFieldBean> mf_list = ModelFieldDAO.getAllModelFieldList();
		mf_map.clear();
		if(mf_list != null && mf_list.size() > 0){
			for(int i=0;i<mf_list.size();i++)
			{
				mf_map.put(mf_list.get(i).getField_id(),mf_list.get(i));
			}
		}
	}
	
	/**
     * 得到所有内容模型字段信息
     * @param 初始加载模板字段信息
     * @return 
     * */
	public static void reloadModelField()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.system.formodel.ModelFieldManager");
	}
	
	/**
     * 根据内容模型ID得到它下面的字段列表
     * @param int model_id
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<ModelFieldBean> getFieldListByModelID(int model_id)
	{
		List<ModelFieldBean> mf_list = new ArrayList<ModelFieldBean>();
		Iterator iter = mf_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			ModelFieldBean mfb = (ModelFieldBean)entry.getValue(); 
			if(mfb.getModel_id() == model_id)
				mf_list.add(mfb);
		}
		return mf_list;
	}
	
	/**
     * 根据内容模型ID得到里面自定义(扩展字段)对象列表
     * @param int model_id
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<ModelFieldBean> getUDefinedFieldsByModelID(int model_id)
	{
		List<ModelFieldBean> mf_list = new ArrayList<ModelFieldBean>();
		Iterator iter = mf_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			ModelFieldBean mfb = (ModelFieldBean)entry.getValue(); 
			//判断它的表是否是扩展表
			if(mfb.getModel_id() == model_id && mfb.getTable_name().equals(PublicTableDAO.INFO_UDEFINED_TABLE_NAME))
				mf_list.add(mfb);
		}
		return mf_list;
	}
	
	/**
	 * 得到内容模型字段对象
	 * 
	 * @param int modelField_id
	 * @return ModelFieldBean 
	 */
	public static ModelFieldBean getModelFieldBean(int modelField_id)
	{
		if(mf_map.containsKey(modelField_id))
			return mf_map.get(modelField_id);
		else{
			ModelFieldBean mfb = ModelFieldDAO.getModelFieldBean(modelField_id);
			if(mfb != null)
			{
				mf_map.put(modelField_id, mfb);
				return mfb;
			}else
				return null;
		}
	}
	
	/**
     * 插入内容模型字段信息
     * @param ModelFieldBean mfb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertModelField(ModelFieldBean mfb,SettingLogsBean stl){
		if(ModelFieldDAO.insertModelField(mfb, stl))
		{
			reloadModelField();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改内容模型字段信息
     * @param ModelFieldBean mfb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateModelField(ModelFieldBean mfb,SettingLogsBean stl){
		if(ModelFieldDAO.updateModelField(mfb, stl))
		{
			reloadModelField();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 删除内容模型字段信息
     * @param String field_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteModelField(String field_ids,SettingLogsBean stl){
		if(ModelFieldDAO.deleteModelField(field_ids, stl))
		{
			reloadModelField();
			return true;
		}
		else
			return false;
	}
}
