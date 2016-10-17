package com.deya.wcm.services.system.dict;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.dict.DataDictBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.system.dict.DataDictDAO;

/**
 * @author 符江波
 * @version 1.0
 * @date   2011-6-1 上午09:58:07
 */
public class DataDictionaryManager implements ISyncCatch{
	private static Map<String, List<DataDictBean>> ddMap = new HashMap<String, List<DataDictBean>>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		ddMap.clear();
	}
	
	public static void clearMap()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.system.dict.DataDictionaryManager");
	}
	
	/**
	 * 根据字典分类得到其下的所有数据项
	 * @param dcid
	 * @return List<DataDictBean>
	 */
	public static List<DataDictBean> getDataDictList(String dcid){
		List<DataDictBean> list = ddMap.get(dcid);
		if(list == null){
			Map<String, String> map = new HashMap<String, String>();
			map.put("dict_cat_id", dcid);
			ddMap.put(dcid, getDataDictionaryList(map));
		}
		return ddMap.get(dcid);
	}

	public static List<DataDictBean> getDataDictionaryList(Map<String, String> map){
		return DataDictDAO.getDataDictionaryList(map);
	}
	/**
	 * 根据数据字典分类，得到此分类下边的所有数据项
	 * @param map
	 * @return List<DataDictBean>
	 */
	public static List<DataDictBean> getDataDictionaryListOfCategory(String dcid, String sortType){
		Map<String, String> map = new HashMap<String, String>();
		map.put("dict_cat_id", dcid);
		map.put("dict_sort", sortType);
		return DataDictDAO.getDataDictionaryList(map);
	}
	public static boolean addDataDictionary(DataDictBean dd, SettingLogsBean stl){
		if(dd == null){
			return false;
		}
		ddMap.remove(dd.getDict_cat_id());
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.SYS_DICT_TABLE_NAME);
		dd.setId(id);
		if(dd.getDict_id() == null || dd.getDict_id().trim().equals("")){
			dd.setDict_id(id+"");
		}
		return DataDictDAO.addDataDictionary(dd, stl);
	}

	public static boolean delDataDictionary(String ids, SettingLogsBean stl){
		ddMap.clear();
		return DataDictDAO.delDataDictionary(ids, stl);
	}

	public static boolean delDataDictionaryOfCategory(String id, SettingLogsBean stl){
		ddMap.clear();
		return DataDictDAO.delDataDictionaryOfCategory(id, stl);
	}
	
	public static boolean updateDataDictionary(DataDictBean dd, SettingLogsBean stl){
		ddMap.remove(dd.getDict_cat_id());
		return DataDictDAO.updateDataDictionary(dd, stl);
	}

	public static DataDictBean selectOneDataDictionaryBean(String id){
		return DataDictDAO.selectOneDataDictionaryBean(id);
	}

	public static boolean updateDictSort(String id, String sortNum, SettingLogsBean stl){
		return DataDictDAO.updateDictSort(id, sortNum, stl);
	}
}
