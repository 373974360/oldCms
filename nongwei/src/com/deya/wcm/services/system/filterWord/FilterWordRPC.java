/**
 * 过滤词的RPC类
 */
package com.deya.wcm.services.system.filterWord;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.system.filterWord.FilterWordBean;
import com.deya.wcm.dao.PublicTableDAO;


public class FilterWordRPC {
	
	/**
	 * 得到指定ID的 过滤词对象
	 * @param filterword_id
	 * @return
	 */
	public static FilterWordBean getFilterWordBean(int filterword_id){
		return FilterWordManager.getFilterWordBean(filterword_id);
	}

	/**
	 * 新增过滤词
	 * @param fwd
	 * @return
	 */
	public static boolean insertFilterWord(FilterWordBean fwd){
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.FILTERWORD_TABLE_NAME);
System.out.println("filterWord_id>>>>>>>>>>>>>"+id);
		fwd.setFilterword_id(id);
		
		return FilterWordManager.insertFilterWord(fwd);
	}
	
	/**
	 * 修改关键词
	 * @param fwd
	 * @return
	 */
	public static boolean updateFilterWord(FilterWordBean fwd){
		return FilterWordManager.updateFilterWord(fwd);
	}
	
	/**
	 * 删除关键词
	 * @param filterword_ids
	 * @return
	 */
	public static boolean deleteFilterWord(String filterword_ids)
	{
		return FilterWordManager.deleteFilterWord(filterword_ids);
	}
	
	/**
	 * 分页  展示所有过滤词
	 * @param m  包含条件、排序、分页等信息的map
	 * @return
	 */
	public static List<FilterWordBean> getAllFilterWord(Map<String,String> m)
	{
		return FilterWordManager.getAllFilterWord(m);
	}
	

	/**
	 * 得到所有过滤词总数
	 * @param map
	 * @return
	 */
	public static String getFilterWordCount(Map<String,String> map){
		return FilterWordManager.getFilterWordCount(map);
	}
}
