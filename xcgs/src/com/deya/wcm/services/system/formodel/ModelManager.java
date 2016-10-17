package com.deya.wcm.services.system.formodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.formodel.ModelBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.system.formodel.ModelDAO;
import com.deya.wcm.services.model.services.FormUtil;

/**
 *  内容模型管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 内容模型管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class ModelManager implements ISyncCatch{
	private static TreeMap<Integer,ModelBean> md_map = new TreeMap<Integer, ModelBean>();	//内容模型缓存
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		List<ModelBean> model_list = ModelDAO.getAllModelList();
		md_map.clear();
		if(model_list != null && model_list.size() > 0)
		{
			for(int i=0;i<model_list.size();i++)
			{
				md_map.put(model_list.get(i).getModel_id(), model_list.get(i));
			}
		}
	}
	
	/**
	 * 初始加载内容模型
	 * 
	 * @param
	 * @return
	 */
	public static void reloadModel()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.system.formodel.ModelManager");
	}
	
	public static Map<Integer,ModelBean> getModelMap()
	{
		return md_map;
	}
	
	/**
	 * 得到所有的内容模型列表
	 * 
	 * @param 
	 * @return List 
	 */
	@SuppressWarnings("unchecked")
	public static List<ModelBean> getModelList()
	{
		List<ModelBean> m_list = new ArrayList<ModelBean>();
		Iterator iter = md_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			m_list.add((ModelBean)entry.getValue());
		}
		Collections.sort(m_list,new ModelComparator());
		return m_list;
	}
	
	/**
	 * 得到可用的内容模型列表
	 * 
	 * @param 
	 * @return List 
	 */
	@SuppressWarnings("unchecked")
	public static List<ModelBean> getCANModelList()
	{
		List<ModelBean> m_list = new ArrayList<ModelBean>();
		Iterator iter = md_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			ModelBean mb = (ModelBean)entry.getValue();
			if(mb.getDisabled() == 0)
				m_list.add(mb);
		}
		Collections.sort(m_list,new ModelComparator());
		return m_list;
	}
	
	/**
	 * 得到可用的内容模型列表
	 * 
	 * @param app_id
	 * @return List 
	 */
	@SuppressWarnings("unchecked")
	public static List<ModelBean> getCANModelListByAppID(String app_id)
	{
		List<ModelBean> m_list = new ArrayList<ModelBean>();
		Iterator iter = md_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			ModelBean mb = (ModelBean)entry.getValue();
			if(mb.getDisabled() == 0 && app_id.trim().equals(mb.getApp_id().trim()))
				m_list.add(mb);
		}
		Collections.sort(m_list,new ModelComparator());
		return m_list;
	}
	
	/**
	 * 得到内容模型对象
	 * 
	 * @param int model_id
	 * @return ModelBean 
	 */
	public static ModelBean getModelBean(int model_id){
		if(md_map.containsKey(model_id))
			return md_map.get(model_id);
		else
		{
			ModelBean mb = ModelDAO.getModelBean(model_id);
			if(mb != null)
			{
				md_map.put(model_id, mb);
				return mb;
			}else
				return null;
		}
	}
	
	/**
	 * 根据英文名称得到内容模型对象
	 * 
	 * @param String model_ename
	 * @return ModelBean 
	 */
	public static ModelBean getModelBeanByEName(String model_ename){
		Set<Integer> s = md_map.keySet();
		for(int i : s)
		{
			ModelBean mb = md_map.get(i);
			System.out.println(mb.getModel_ename());
			if(mb.getModel_ename().equals(model_ename))
			{
				return mb;
			}
		}
		return null;
	}
	
	
	/**
	 * 根据模型ID返回模型添加页
	 * 
	 * @param int model_id
	 * @return String 
	 */
	public static String getModelAddPage(int model_id)
	{
		ModelBean m = getModelBean(model_id);
		if(m != null)
			return m.getAdd_page();
		else
			return "";
	}
	
	/**
	 * 根据模型ID返回英文名称
	 * 
	 * @param int model_id
	 * @return String 
	 */
	public static String getModelEName(int model_id)
	{
		ModelBean m = getModelBean(model_id);
		if(m != null)
			return m.getModel_ename();
		else
			return "";
	}
	
	/**
	 * 根据模型ID返回模型添加页
	 * 
	 * @param int model_id
	 * @return String 
	 */
	public static String getModelViewPage(int model_id)
	{
		ModelBean m = getModelBean(model_id);
		if(m != null)
			return m.getView_page();
		else
			return "";
	}
	
	
	/**
     * 插入内容模型信息
     * @param ModelBean mb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertModel(ModelBean mb,SettingLogsBean stl){		
		if(ModelDAO.insertModel(mb, stl))
		{	
			
			//创建表 -- 李苏培
			FormUtil.createTable(mb.getTable_name().toLowerCase());
			
			reloadCatchHandl();
			reloadModel();
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
		if(ModelDAO.updateModel(mb, stl))
		{
			reloadModel();
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
		
		String id[]= model_ids.split(",");
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<id.length;i++){
        	String s_id = id[i];
        	if(s_id!=null && !"".equals(s_id)){
        		ModelBean mb = getModelBean(Integer.valueOf(s_id));
        		if(mb!=null){
        			ModelDAO.deleteModel(s_id, stl); 
        			
        			//删除表
        			FormUtil.dropTable(mb.getTable_name().toLowerCase());
        		}
        	}
        }
        reloadCatchHandl();
        reloadModel();
        /*
		if(ModelDAO.deleteModel(model_ids, stl))
		{
			reloadModel();
			return true;
		}else
			return false;
		*/
        return true;
	}
	
	/**
     * 保存排序
     * @param String model_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean saveModelSort(String model_ids,SettingLogsBean stl)
	{
		if(ModelDAO.saveModelSort(model_ids, stl))
		{
			reloadModel();
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
		if(ModelDAO.updateModelDisabled(model_ids,disabled, stl))
		{
			reloadModel();
			return true;
		}else
			return false;
	}
	
	public static void sortModelList(List<ModelBean> m_list)
	{
		Collections.sort(m_list,new ModelComparator());
	}
	
	static class ModelComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			ModelBean mb1 = (ModelBean) o1;
			ModelBean mb2 = (ModelBean) o2;
		    if (mb1.getModel_sort() > mb2.getModel_sort()) {
		     return 1;
		    } else {
		     if (mb1.getModel_sort() == mb2.getModel_sort()) {
		      return 0;
		     } else {
		      return -1;
		     }
		    }
		}
	}
	
	public static void main(String args[])
	{
		System.out.println(getModelBeanByEName("infoLink"));
	}
}
