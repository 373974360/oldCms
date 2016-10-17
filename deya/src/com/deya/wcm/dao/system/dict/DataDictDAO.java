package com.deya.wcm.dao.system.dict;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.dict.DataDictBean;
import com.deya.wcm.bean.system.dict.DataDictCategoryBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 * @author 符江波
 * @version 1.0
 * @date   2011-5-31 下午04:44:15
 */
public class DataDictDAO {

	//字典分类
	@SuppressWarnings("unchecked")
	public static List<DataDictCategoryBean> getDataDictionaryCategoryList(Map<String, String> map){
		return (List<DataDictCategoryBean>)DBManager.queryFList("getDataDictionaryCategoryList", map);
	}
	
	public static boolean addDataDictionaryCategory(DataDictCategoryBean dc, SettingLogsBean stl){
		if(DBManager.insert("addDataDictionaryCategory", dc))
		{
			PublicTableDAO.insertSettingLogs("添加","字典分类",dc.getDict_cat_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	public static boolean delDataDictionaryCategory(String ids, SettingLogsBean stl){
		Map<String, String> map = new HashMap<String, String>();
		map.put("ids", ids);
		if(DBManager.delete("delDataDictionaryCategory", map))
		{
			PublicTableDAO.insertSettingLogs("删除","字典分类",ids+"",stl);
			return true;
		}else
			return false;
	}
	
	public static boolean updateDataDictionaryCategory(DataDictCategoryBean dc, SettingLogsBean stl){
		if(DBManager.update("updateDataDictionaryCategory", dc))
		{
			PublicTableDAO.insertSettingLogs("修改","字典分类",dc.getDict_cat_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	public static DataDictCategoryBean selectOneDataDictionaryCategoryBean(String id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("dict_cat_id", id);
		return (DataDictCategoryBean)DBManager.queryFObj("selectOneDataDictionaryCategoryBean", map);
	}
	
	//字典项
	@SuppressWarnings("unchecked")
	public static List<DataDictBean> getDataDictionaryList(Map<String, String> map){
		return (List<DataDictBean>)DBManager.queryFList("getDataDictionaryList", map);
	}
	
	public static boolean addDataDictionary(DataDictBean dd, SettingLogsBean stl){
		if(DBManager.insert("addDataDictionary", dd))
		{
			PublicTableDAO.insertSettingLogs("添加","字典项",dd.getDict_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	public static boolean delDataDictionary(String ids, SettingLogsBean stl){
		Map<String, String> map = new HashMap<String, String>();
		map.put("ids", ids);
		if(DBManager.delete("delDataDictionary", map))
		{
			PublicTableDAO.insertSettingLogs("删除","字典项",ids+"",stl);
			return true;
		}else
			return false;
	}
	
	public static boolean delDataDictionaryOfCategory(String id, SettingLogsBean stl){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		if(DBManager.delete("delDataDictionaryOfCategory", map))
		{
			PublicTableDAO.insertSettingLogs("删除","字典分类"+id+"下所有数据项",id+"",stl);
			return true;
		}else
			return false;
	}
	
	public static boolean updateDataDictionary(DataDictBean dd, SettingLogsBean stl){
		if(DBManager.update("updateDataDictionary", dd))
		{
			PublicTableDAO.insertSettingLogs("修改","字典项",dd.getDict_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	public static DataDictBean selectOneDataDictionaryBean(String id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("dict_id", id);
		return (DataDictBean)DBManager.queryFObj("selectOneDataDictionaryBean", map);
	}
	
	public static boolean updateDictSort(String id, String sortNum, SettingLogsBean stl){
		Map<String, String> map = new HashMap<String, String>();
		map.put("dict_sort", sortNum);
		map.put("dict_id", id);
		if(DBManager.update("updateDictSort", map))
		{
			PublicTableDAO.insertSettingLogs("修改","字典项排序",id+"",stl);
			return true;
		}else
			return false;
	}
}
