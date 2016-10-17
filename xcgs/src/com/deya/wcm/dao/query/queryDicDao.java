package com.deya.wcm.dao.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.query.QueryDicBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.BoneDataSourceFactory;
import com.deya.wcm.db.DBManager;

public class queryDicDao {

	
	/**
     * 得到所有数据字典列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<QueryDicBean> getAllQueryDicList()
	{
		return DBManager.queryFList("getAllQueryDicList","");
	}
	
	/**
	 * 新增数据字典
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
	public static boolean insertQueryDic(int conf_id,List<QueryDicBean> l,SettingLogsBean stl)
	{
		if(deleteQueryDic(conf_id+""))
		{
			Map  m =  new HashMap();
			String temp ="";
			if(l != null && l.size() > 0)
			{
				try{
					for(int i=0;i<l.size();i++)
					{
						int n=i+1;
						l.get(i).setDic_id(n);
						l.get(i).setConf_id(conf_id);
						if("mssql".equals(BoneDataSourceFactory.getDataTypeName()))
						{
							temp +=",isnull(max(case item_key when "+n+" then item_value end),'') as item_"+n+"";
						}else
							temp +=",nvl(max(case item_key when "+n+" then item_value end),'') as item_"+n+"";
						DBManager.insert("insert_QueryDic", l.get(i));
					}
					if("mssql".equals(BoneDataSourceFactory.getDataTypeName()))
					{
						m.put("sql", "create view cs_dz_cx_"+conf_id+" as select top 100000 item_id "+temp+" from cs_dz_chaxun_item where conf_id="+conf_id+" group by item_id order by item_id");
					}
					else
					{
						m.put("sql", "create or replace view cs_dz_cx_"+conf_id+" as " +
							"select item_id "+temp+" from  cs_dz_chaxun_item where conf_id="+conf_id+" group by item_id order by item_id");
					}
					//System.out.println("sql========================="+m.get("sql"));
					com.deya.wcm.dao.PublicTableDAO.createSql(m);		   
				}catch(Exception e)
				{
					e.printStackTrace();
					return false;
				}
			}
			PublicTableDAO.insertSettingLogs("新增","字段 业务",conf_id+"",stl);
			return true;
		}else
			return false;
	}
		
	/**
	 * 删除数据字典
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
	public static boolean deleteQueryDic(String conf_id)
	{
		Map m = new HashMap();
		m.put("conf_id", conf_id);
		
		Map m2 = new HashMap();
		m2.put("sql", "drop view cs_dz_cx_"+conf_id+"");
		com.deya.wcm.dao.PublicTableDAO.createSql(m2);	
		
		if(DBManager.delete("delete_QueryDic",m))
		{
			return true;
		}else
			return false;
	}
	
	/**
	 * 数据字典前台使用
	 * @param ob
	 * @param con
	 * @return List
	 */
    public static List<QueryDicBean> getTypeDicList(String con){
	    Map m = new HashMap();
	    m.put("con",con);
    	return DBManager.queryFList("getTypeDicList_browser",m);
	}
}
