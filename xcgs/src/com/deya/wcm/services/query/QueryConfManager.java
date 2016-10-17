package com.deya.wcm.services.query;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import jxl.Sheet;
import jxl.Workbook;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.query.QueryConfBean;
import com.deya.wcm.bean.query.QueryItemBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.query.queryConfDao;

/**
 *  自定义查询业务管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description:  自定义查询业务管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author zhangqiang
 * @version 1.0
 * * 
 */

public class QueryConfManager implements ISyncCatch{
	
	private static TreeMap<Integer,QueryConfBean> QueryConf_map = new TreeMap<Integer, QueryConfBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		List<QueryConfBean> queryConf_list = queryConfDao.getAllQueryConfList(); 
		QueryConf_map.clear();
		if (queryConf_list != null && queryConf_list.size() > 0) {
			for (int i = 0; i < queryConf_list.size(); i++) {				
				QueryConf_map.put(queryConf_list.get(i).getConf_id(),queryConf_list.get(i));  
			}  
		}
	}
	
	
	/**
	 * 初始化
	 * @param
	 * @return
	 */
	public static void reloadQueryConf() {
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.query.QueryConfManager");
	}
	
	/**
     * 得到所有查询列表
     * @param 
     * @return List
     * */
	public static List<QueryConfBean> getAllQueryConfList()
	{
		List<QueryConfBean> QueryConf_list = new ArrayList<QueryConfBean>(); 
		Set<Integer> set = QueryConf_map.keySet();
		
		for(int i : set){
			QueryConf_list.add(QueryConf_map.get(i));				
		}
		return QueryConf_list;
	}
	
	/**
     * 根据条件得到总数
     * @param Map<String,String> m
     * @return String
     * */
	public static String getAllQueryConfCounts()
	{
		return queryConfDao.getAllQueryConfCounts();
	}
	
	/**
     * 根据条件得到总数
     * @param Map<String,String> m
     * @return String
     * */
	public static String getQueryConfCount(Map<String,String> m)
	{
		return queryConfDao.getQueryConfCount(m);
	}
	
	/**
     * 根据条件得到列表
     * @param Map<String,String> m
     * @return List
     * */
	public static List<QueryConfBean> getQueryConfLists(Map<String,String> m)
	{
		
		return queryConfDao.getQueryConfList(m);
	}
	
	
	/**
	 * 根据查询ID返回对象
	 * @param conf_id
	 * @return QueryConfBean
	 */
    public static QueryConfBean getQueryConfBean(int conf_id)
    {
    	if(QueryConf_map.containsKey(conf_id))
    	{
    		return QueryConf_map.get(conf_id);
    	}else{
    		QueryConfBean ob = queryConfDao.getQueryConfBeanById(conf_id);
    		if(ob != null)
    			QueryConf_map.put(conf_id,ob);
    		return ob;
    	}
    }
    
	/**
    * 得到查询ID,用于添加页面
    * @param
    * @return String
    * */
	public static int getQueryConfID()
	{
		return  PublicTableDAO.getIDByTableName(PublicTableDAO.DZ_CHAXUN_CONF_NAME);
	}
	
	
	/**
	 * 新增查询配置
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
    public static boolean insertQueryConf(QueryConfBean ob,SettingLogsBean stl)
    {
    	if(queryConfDao.insertQueryConf(ob, stl))
		{
			reloadQueryConf();
			return true;
		}else{
			return false;
		}
    }
    /**
     * 修改查询信息
     * @param QueryConfBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
    public static boolean updateQueryConf(QueryConfBean ob,SettingLogsBean stl)
	{
		if(queryConfDao.updateQueryConf(ob,stl))
		{
			reloadQueryConf();
			return true;
		}else{
			return false;
		}
	}
    
    /**
	 * 删除查询信息
	 * @param conf_ids
	 * @param request
	 * @return
	 */
    public static boolean deleteQueryConf(Map<String,String> m,SettingLogsBean stl)
    {
    	if(queryConfDao.deleteQueryConf(m, stl))
		{
			reloadQueryConf();
			return true;
		}else{
			return false;
		}
    }
    
    public static void main(String[] args){
    	
    	QueryConfBean ob = new QueryConfBean() ;
    	ob.setApp_id("cms");
    	ob.setConf_id(40);
    	ob.setConf_title("33333333333");
    	ob.setT_list_id(1252);
    	SettingLogsBean stl =new SettingLogsBean();
    	
    	insertQueryConf(ob,stl);
	}
    
}
