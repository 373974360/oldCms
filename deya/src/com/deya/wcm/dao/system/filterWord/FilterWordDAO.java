/**
 * 
 */
package com.deya.wcm.dao.system.filterWord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.system.filterWord.FilterWordBean;
import com.deya.wcm.db.DBManager;

/**
 * @author 王磊
 *
 */
public class FilterWordDAO {
	
	/**
	 * 得到所有过滤词总数
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<FilterWordBean> getFilterWordList()
	{
		return DBManager.queryFList("getFilterWordList", "");
	}
	
	/**
	 * 分页  展示所有过滤词
	 * @param m  包含条件、排序、分页等信息的map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<FilterWordBean> getAllFilterWord(Map<String,String> m)
	{
		return DBManager.queryFList("getAllFilterWord", m);
	}
	
	/**
	 * 得到所有过滤词总数
	 * @param map
	 * @return
	 */
	public static String getFilterWordCount(Map<String,String> map){
		return DBManager.getString("getFilterWordCount", map);
	}
	
	/**
	 * 得到指定ID的 过滤词对象
	 * @param filterword_id
	 * @return
	 */
	public static FilterWordBean getFilterWordBean(int filterword_id){
		return (FilterWordBean)DBManager.queryFObj("getFilterWordBean", filterword_id);
	}
	
	/**
	 * 新增 关键词
	 * @param fwd
	 * @return
	 */
	public static boolean insertFilterWord(FilterWordBean fwd){
		return DBManager.insert("insert_FilterWord",fwd);
	}
	
	/**
	 * 修改  关键词
	 * @param fwd
	 * @return
	 */
	public static boolean updateFilterWord(FilterWordBean fwd){
		return DBManager.update("update_FilterWord", fwd);
	}
	
	/**
	 * 删除 关键词
	 * @param filterword_ids
	 * @return
	 */
	public static boolean deleteFilterWord(String filterword_ids){
		Map<String,String> m = new HashMap<String,String>();
		m.put("filterword_ids", filterword_ids);
		return DBManager.delete("delete_FilterWord", m);
	}
}
