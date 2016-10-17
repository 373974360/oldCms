package com.deya.wcm.services.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.query.QueryDicBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.query.queryConfDao;
import com.deya.wcm.dao.query.queryDicDao;

/**
 *  自定义查询字段配置逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description:  自定义查询字段配置逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author zhangqiang
 * @version 1.0
 * * 
 */

public class QueryDicManager implements ISyncCatch{
	
	private static List<QueryDicBean> QueryDic_list = new ArrayList<QueryDicBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		QueryDic_list.clear();
		QueryDic_list = queryDicDao.getAllQueryDicList();	
	}
	/**
	 * 初始化
	 * @param
	 * @return
	 */
	public static void reloadQueryDic() {
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.query.QueryDicManager");
	}
	
	
	/**
     * 得到一个查询业务数据字段
     * @param 
     * @return List
     * */
	public static List<QueryDicBean> getDicListByConf_id(int conf_id)
	{
		List<QueryDicBean> l = new ArrayList<QueryDicBean>();
		if(QueryDic_list != null && QueryDic_list.size() > 0)
		{
			for(int i=0;i<QueryDic_list.size();i++)
			{
				if(QueryDic_list.get(i).getConf_id() == conf_id)
				{
					l.add(QueryDic_list.get(i));
				}
			}
		}
		return l;
	}
	
	public static QueryDicBean getDicByConf_Dic_Id(int dic_id,int conf_id)
	{
		QueryDicBean qdb  = null;
		List<QueryDicBean> l = getDicListByConf_id(conf_id);
		if(l.size() >0)
		{
			for(int i=0;i<l.size();i++)
			{
				if(l.get(i).getDic_id() == dic_id)
				{
					 qdb = l.get(i);
				}
			}	
		}	
		return qdb;
	}
	
	/**
     * 得到一个查询业务字段类型
     * @param 
     * @return List
     * */
	public static List<QueryDicBean> getOneTypeDicList(int conf_id,String type)
	{
		List<QueryDicBean> l = new ArrayList<QueryDicBean>();
		if(QueryDic_list != null && QueryDic_list.size() > 0)
		{
			for(int i=0;i<QueryDic_list.size();i++)
			{
				if(type.equals(QueryDic_list.get(i).getIs_query()) && QueryDic_list.get(i).getConf_id() == conf_id){
					l.add(QueryDic_list.get(i));
				}else if(type.equals(QueryDic_list.get(i).getIs_result()) && QueryDic_list.get(i).getConf_id() == conf_id){
					l.add(QueryDic_list.get(i));
				}else if(type.equals(QueryDic_list.get(i).getIs_selected()) && QueryDic_list.get(i).getConf_id() == conf_id){
					l.add(QueryDic_list.get(i));
				}
			}
		}
		return l;
	}
	
	/**
	 * 新增自定义字段
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
	
    public static boolean insertQueryDic(int conf_id,List<QueryDicBean> l,SettingLogsBean stl)
    {
    	if(queryDicDao.insertQueryDic(conf_id,l, stl))
		{
			reloadQueryDic();
			return true;
		}else{
			return false;
		}
    }
    
    
    /**
	 * 删除自定义字段
	 * @param dic_ids
	 * @param request
	 * @return
	 */
    public static boolean deleteQueryDic(String conf_id)
    {
    	if(queryDicDao.deleteQueryDic(conf_id))
		{
			reloadQueryDic();
			return true;
		}else{
			return false;
		}
    }  
    
	/**
	 * 得到数据字典前台使用
	 * @param ob
	 * @param con
	 * @return List
	 */
    public static List<QueryDicBean> getTypeDicList(String con){
    	
		return queryDicDao.getTypeDicList(con);
	}
	
    
    public static void main(String[] args){
    	//List<QueryDicBean> l = getDicListByConf_id(24);
    	//System.out.println(l);
    	System.out.println(getDicByConf_Dic_Id(3,24));
	}
    
}