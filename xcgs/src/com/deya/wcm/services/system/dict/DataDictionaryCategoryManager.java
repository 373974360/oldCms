package com.deya.wcm.services.system.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.dict.DataDictCategoryBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.system.dict.DataDictDAO;

/**
 * @author 符江波
 * @version 1.0
 * @date   2011-6-1 上午09:58:07
 */
public class DataDictionaryCategoryManager {

	public static List<DataDictCategoryBean> getDataDictionaryCategoryList(Map<String, String> map){
		return DataDictDAO.getDataDictionaryCategoryList(map);
	}
	/**
	 * 根据参数得到系统或扩展的数据字典项 1系统 0扩展
	 * @param int is_sys
	 * @return List<DataDictBean>
	 */
	public static List<DataDictCategoryBean> getDictCategoryForSyS(int is_sys)
	{
		List<DataDictCategoryBean> c_list = new ArrayList<DataDictCategoryBean>();
		Map<String, String> map = new HashMap<String, String>();
		List<DataDictCategoryBean> l = getDataDictionaryCategoryList(map);
		if(l != null && l.size() > 0)
		{
			for(DataDictCategoryBean dc : l){
				if(dc != null){
					if(dc.getIs_sys() == is_sys){
						c_list.add(dc);
					}
				}
			}
		}
		return c_list;
	}
	
	public static String getDcTreeJson(Map<String, String> map){
		String defaultStr = "{\"id\":\"-2\",\"text\":\"系统数据字典\",\"attributes\":{\"nt\":\"df\"}, \"children\":[";//系统默认
		String jsonStr = "{\"id\":\"-1\",\"text\":\"扩展数据字典\",\"attributes\":{\"nt\":\"kz\"},\"children\":[";//系统默认
		boolean dflag = false , cflag = false;
		List<DataDictCategoryBean> list = getDataDictionaryCategoryList(map);
		for(DataDictCategoryBean dc : list){
			if(dc != null){
				if(dc.getIs_sys() == 1){
					defaultStr += "{\"id\":\""+dc.getDict_cat_id()+"\",\"text\":\""+dc.getDict_cat_name()+"\",\"attributes\":{\"site_id\":\""+dc.getSite_id()+"\",\"nt\":\"df\"}},";
					dflag = true;
				}else{
					jsonStr += "{\"id\":\""+dc.getDict_cat_id()+"\",\"text\":\""+dc.getDict_cat_name()+"\",\"attributes\":{\"site_id\":\""+dc.getSite_id()+"\",\"nt\":\"kz\"}},";
					cflag = true;
				}
			}
		}
		if(dflag){
			defaultStr = defaultStr.substring(0,defaultStr.length()-1);
		}
		if(cflag){
			jsonStr = jsonStr.substring(0,jsonStr.length()-1);
		}
		defaultStr += "]}";
		jsonStr += "]}";
		return "["+defaultStr+","+jsonStr+"]";
	}

	public static boolean addDataDictionaryCategory(DataDictCategoryBean dd, SettingLogsBean stl){
		if(dd == null){
			return false;
		}else{
			if(dd.getDict_cat_id() != null && !dd.getDict_cat_id().trim().equals("") && selectOneDataDictionaryCategoryBean(dd.getDict_cat_id()) != null){//如果是已经存在的节点，则更新
				return updateDataDictionaryCategory(dd, stl);
			}
		}
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.SYS_DICT_CATEGORY_TABLE_NAME);
		dd.setId(id);
		if(dd.getDict_cat_id() == null || dd.getDict_cat_id().trim().equals("")){
			dd.setDict_cat_id(id+"");
		}
		return DataDictDAO.addDataDictionaryCategory(dd, stl);
	}

	public static boolean delDataDictionaryCategory(String ids, SettingLogsBean stl){
		return DataDictDAO.delDataDictionaryCategory(ids, stl);
	}

	public static boolean updateDataDictionaryCategory(DataDictCategoryBean dd, SettingLogsBean stl){
		return DataDictDAO.updateDataDictionaryCategory(dd, stl);
	}

	public static DataDictCategoryBean selectOneDataDictionaryCategoryBean(String id){
		return DataDictDAO.selectOneDataDictionaryCategoryBean(id);
	}
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		System.out.println(getDcTreeJson(map));
	}
}
