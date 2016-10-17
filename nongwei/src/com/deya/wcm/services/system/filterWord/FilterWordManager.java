/**
 * 
 */
package com.deya.wcm.services.system.filterWord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.system.filterWord.FilterWordBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.system.filterWord.FilterWordDAO;
import com.deya.wcm.db.DBManager;

/**
 * @author Administrator
 *
 */
public class FilterWordManager implements ISyncCatch{
	private static List<FilterWordBean> fl = new ArrayList<FilterWordBean>();
		
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		fl.clear();
		fl = FilterWordDAO.getFilterWordList();
	}
	
	public static void reloadFilterWord()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.system.filterWord.FilterWordManager");
	}
	
	public static List<FilterWordBean> getFilterWordList()
	{
		return fl;
	}
	
	/**
	 * 得到指定ID的 过滤词对象
	 * @param filterword_id
	 * @return
	 */
	public static FilterWordBean getFilterWordBean(int filterword_id){
		if(fl != null && fl.size() > 0)
		{
			for(FilterWordBean fwb : fl)
			{
				if(fwb.getFilterword_id() == filterword_id)
					return fwb;
			}
		}
		return FilterWordDAO.getFilterWordBean(filterword_id);
	}

	/**
	 * 新增过滤词
	 * @param fwd
	 * @return
	 */
	public static boolean insertFilterWord(FilterWordBean fwd){
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.FILTERWORD_TABLE_NAME);
		fwd.setFilterword_id(id);
		
		return FilterWordDAO.insertFilterWord(fwd);
	}
	
	/**
	 * 修改关键词
	 * @param fwd
	 * @return
	 */
	public static boolean updateFilterWord(FilterWordBean fwd){
		return FilterWordDAO.updateFilterWord(fwd);
	}
	
	/**
	 * 删除关键词
	 * @param filterword_ids
	 * @return
	 */
	public static boolean deleteFilterWord(String filterword_ids)
	{
		return FilterWordDAO.deleteFilterWord(filterword_ids);
	}
	
	/**
	 * 分页  展示所有过滤词
	 * @param m  包含条件、排序、分页等信息的map
	 * @return
	 */
	public static List<FilterWordBean> getAllFilterWord(Map<String,String> m)
	{
		return FilterWordDAO.getAllFilterWord(m);
	}
	

	/**
	 * 得到所有过滤词总数
	 * @param map
	 * @return
	 */
	public static String getFilterWordCount(Map<String,String> map){
		return FilterWordDAO.getFilterWordCount(map);
	}
	
	public static void main(String[] args){
	
		System.out.println(getFilterWordBean(2));
	}
}
