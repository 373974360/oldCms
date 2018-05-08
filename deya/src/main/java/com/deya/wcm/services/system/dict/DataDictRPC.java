package com.deya.wcm.services.system.dict;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.dict.DataDictBean;
import com.deya.wcm.bean.system.dict.DataDictCategoryBean;
import com.deya.wcm.services.Log.LogManager;

/**
 * @author 符江波
 * @version 1.0
 * @date   2011-6-1 上午10:24:40
 */
public class DataDictRPC {
	
	/**
	 * 根据参数得到系统或扩展的数据字典项 1系统 0扩展
	 * @param int is_sys
	 * @return List<DataDictBean>
	 */
	public static List<DataDictCategoryBean> getDictCategoryForSyS(int is_sys)
	{
		return DataDictionaryCategoryManager.getDictCategoryForSyS(is_sys);
	}
	
	/**
	 * 根据字典分类得到其下的所有数据项
	 * @param dcid
	 * @return List<DataDictBean>
	 */
	public static List<DataDictBean> getDataDictList(String dcid){
		return DataDictionaryManager.getDataDictList(dcid);
	}
	
	//分类
	public static List<DataDictCategoryBean> getDCList(Map<String, String> map){
		return DataDictionaryCategoryManager.getDataDictionaryCategoryList(map);
	}

	public static String getDcTreeJson(Map<String, String> map){
		return DataDictionaryCategoryManager.getDcTreeJson(map);
	}
	public static boolean addDC(DataDictCategoryBean dd,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return DataDictionaryCategoryManager.addDataDictionaryCategory(dd,stl);
		}else
			return false;	
	}

	public static boolean delDC(String ids,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null) {
			return DataDictionaryCategoryManager.delDataDictionaryCategory(ids,stl);
		}else
			return false;	
	}

	public static boolean updateDC(DataDictCategoryBean dd,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null) {
			return DataDictionaryCategoryManager.updateDataDictionaryCategory(dd,stl);
		}else
			return false;	
	}

	public static DataDictCategoryBean getOneDCBean(String id){
		return DataDictionaryCategoryManager.selectOneDataDictionaryCategoryBean(id);
	}

	//字典项
	public static List<DataDictBean> getDictList(Map<String, String> map){
		return DataDictionaryManager.getDataDictionaryList(map);
	}
	
	public static List<DataDictBean> getDictList(String dcid, String sortType){
		return DataDictionaryManager.getDataDictionaryListOfCategory(dcid, sortType);
	}

	public static boolean addDict(DataDictBean dd,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return DataDictionaryManager.addDataDictionary(dd,stl);
		}else
			return false;	
	}

	public static boolean delDict(String ids, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return DataDictionaryManager.delDataDictionary(ids,stl);
		}else
			return false;	
	}
	
	public static boolean delDicts(String id, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return DataDictionaryManager.delDataDictionaryOfCategory(id,stl);
		}else
			return false;	
	}

	public static boolean updateDict(DataDictBean dd, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return DataDictionaryManager.updateDataDictionary(dd, stl);
		}else
			return false;	
	}

	public static DataDictBean getOneDictBean(String id){
		return DataDictionaryManager.selectOneDataDictionaryBean(id);
	}

	public static boolean updateSort(String id, String sortNum, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null) {
			return DataDictionaryManager.updateDictSort(id, sortNum, stl);
		}else
			return false;	
	}
	
	public static void main(String args[])
	{
		System.out.println("getDataDictList----"+DataDictionaryCategoryManager.getDictCategoryForSyS(0));
	}
}
