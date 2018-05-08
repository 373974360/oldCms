package com.deya.wcm.services.query;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.query.QueryDicBean;
import com.deya.wcm.bean.query.QueryItemBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.query.queryItemDao;
import com.deya.wcm.template.velocity.data.QueryData;

public class QueryItemManager {
	
	/**
     * 根据条件得到总数
     * @param Map<String,String> m
     * @return String
     * */
	public static String getQueryItemCount(Map<String,String> m)
	{
		String count = queryItemDao.getQueryItemCount(m);
		String conf_id = "";
		int num = 0;
		int n =0;
		if(m != null){
			conf_id = m.get("conf_id");
			num  = Integer.parseInt(queryItemDao.getQueryCellCount(conf_id));
		}
		if(num !=0){
			n = Integer.parseInt(count)/num;
		}
		return String.valueOf(n);
	}
	
	/**
	 * 根据查询ID返回对象
	 * @param conf_id
	 * @return QueryItemBean
	*/
    public static List<QueryItemBean> getQueryItemBeans(int item_id,int conf_id)
    {       Map m =new HashMap();
    		m.put("item_id",item_id);
    		m.put("conf_id",conf_id);
    		return queryItemDao.getQueryItemBeans(m);
    }  

	/**
     * 根据条件得到列表
     * @param Map<String,String> m
     * @return List
     * */
	public static List<QueryItemBean> getQueryItemLists(Map m)
	{
		String conf_id = (String)m.get("conf_id");
		String table_name = " from cs_dz_cx_"+conf_id+" ";
		m.put("table_name",table_name);
		return queryItemDao.getQueryItemLists(m);
	}
	
	public static String getQueryItemCounts(Map m)
	{
		String conf_id = (String)m.get("conf_id");
		String table_name = " from cs_dz_cx_"+conf_id+" ";
		m.put("table_name",table_name);
		return  queryItemDao.getQueryItemCounts(m);
	}
	
	/**
	 * 新增信息
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
    public static boolean insertQueryItem(int cont_id,QueryItemBean ob,SettingLogsBean stl)
    {
    	if(queryItemDao.insertQueryItem(cont_id,ob,stl))
		{
			return true;
		}else{
			return false;
		}
    }
    
    /**
	 * 新增信息
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
    public static boolean insertQueryItemByConf_id(int conf_id,List<QueryItemBean> l,SettingLogsBean stl)
    {
    	if(l != null){
    		for(int i=0;i<l.size();i++)
    		{
    			QueryItemBean item = new QueryItemBean();
//    			System.out.println("getItem_id======="+l.get(i).getItem_id());
//    			System.out.println("getItem_key======"+l.get(i).getItem_key());
//    			System.out.println("getItem_value===="+l.get(i).getItem_value());
    			item.setItem_id(l.get(i).getItem_id());
	            item.setSite_id(l.get(i).getSite_id());
	            item.setConf_id(conf_id);
	            item.setItem_key(l.get(i).getItem_key());
	            item.setItem_value(l.get(i).getItem_value());
	            	
	            queryItemDao.insertQueryItem(conf_id,item,stl);
    		}
    		return true;
    	}else{
    		return false;
    	}
    }
    /**
     * 修改查询信息
     * @param QueryItemBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
    public static boolean updateQueryItem(List<QueryItemBean> l,SettingLogsBean stl)
	{
    	if(l != null)
    	{
    		for(int i=0;i<l.size();i++){
    			QueryItemBean  item = new QueryItemBean();
    			item.setItem_id(l.get(i).getItem_id());
            	item.setConf_id(l.get(i).getConf_id());
            	item.setItem_key(l.get(i).getItem_key());
            	item.setItem_value(l.get(i).getItem_value());
            	
            	queryItemDao.updateQueryItem(item,stl);
    		}
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
    public static boolean deleteQueryItem(Map m,SettingLogsBean stl)
    {
    	//System.out.println("conf_id    ============"+m.get("conf_id"));
    	//System.out.println("item_id    ============"+m.get("item_id"));
    	if(queryItemDao.deleteQueryItemByItem(m,stl))
		{
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
    public static boolean deleteQueryItemByConf_id(int conf_id,SettingLogsBean stl)
    {
    	if(queryItemDao.deleteQueryItemByConf_id(conf_id,stl))
		{
			return true;
		}else{
			return false;
		}
    }
	
	public static boolean writeExcel(String file_name,int conf_id,String site_id,SettingLogsBean stb)
	{
		List<QueryDicBean> QueryDicList = new ArrayList<QueryDicBean>();
		try{
			Workbook book = Workbook.getWorkbook(new File(file_name));
			deleteQueryItemByConf_id(conf_id,stb);
			if(book != null)
			{
	            //获得第一个工作表对象
	            Sheet sheet  =  book.getSheet(0);
            	QueryDicList = QueryDicManager.getDicListByConf_id(conf_id);
            	int n=0;
            	if(QueryDicList != null)
            	{
        			for(QueryDicBean qdb : QueryDicList)
        			{
        				for(int i=1;i<sheet.getRows();i++)
        		        {   
        					QueryItemBean item = new QueryItemBean();
        					String temp="";
        					try{
	        		            if(sheet.getRow(i)[n].getContents() != null)
	        		            {    
	        		            	temp = sheet.getRow(i)[n].getContents();
	        		            }
        					}catch(Exception e)
        					{
        						e.printStackTrace();
        					}   
        		            	item.setItem_id(i);
            	            	item.setSite_id(site_id);
            	            	item.setConf_id(conf_id);
            	            	item.setItem_key(qdb.getDic_id());
            	            	item.setItem_value(temp);
        		        	insertQueryItem(conf_id,item,stb);
        		        }
        				n++;
        			}
        		}
	            return true;
			}
			else
				return false;
        }   
		catch  (Exception e)  {
            //System.out.println(e);
            e.printStackTrace();
            return false;
        }
	}
	
	public static List<List<QueryItemBean>> getQueryItemValueList(String conf_id,int page_size)
	{
		int count = Integer.parseInt(queryItemDao.getQueryCellCount(conf_id));
		List<QueryItemBean> l = new ArrayList<QueryItemBean>();
		List<QueryItemBean> l_f_db = null;
		Map<String,String> m = new HashMap<String,String>();
		m.put("conf_id", conf_id);
		m.put("cell_count", count+"");
		m.put("page_size", page_size+"");
		m.put("start_num", "0");
		l_f_db = queryItemDao.getQueryItemList(m);
		
		List<List<QueryItemBean>> ml = new ArrayList<List<QueryItemBean>>();
		if(l_f_db != null)
		{
			int i=1;
			for(QueryItemBean qib : l_f_db)
			{
				l.add(qib);
				if(i%count == 0)
				{
					List<QueryItemBean> l_2 = new ArrayList<QueryItemBean>();
					l_2.addAll(l);
					ml.add(l_2);
					l.clear();
				}
				i++;
			}
		}
		return ml;
	}
	
	/*		前台使用		*/
	public static List<QueryItemBean> getQueryListBrowser(String con,String conf_id,int counts,int page_sizes)
	{
		List<QueryItemBean> l = null;
		int count = Integer.parseInt(queryItemDao.getQueryCellCount(conf_id));
		Map m = new HashMap();
		//System.out.println("QueryItemManager	getQueryListBrowser====="+con);
	    m.put("con",con);
		m.put("page_size", page_sizes+"");
		m.put("start_num", counts+"");
		
		return l = queryItemDao.getQueryListBrowser(m);
	}
	
	public static String getQueryListCountBrowser(String con){
		Map m = new HashMap();
		//System.out.println("getQueryListCountBrowser==============="+con);
	    m.put("con",con);
		return queryItemDao.getQueryListCountBrowser(m);
	}
	
	public static void main(String[] args){
		//SettingLogsBean stl2 = new SettingLogsBean();
		//writeExcel("D:/data.xls",24,"HIWCMdemo",stl2);
		//System.out.println(getQueryItemValueList("24",5).get(0).size());
	}	
}