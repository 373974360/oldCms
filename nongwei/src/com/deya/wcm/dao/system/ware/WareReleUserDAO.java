package com.deya.wcm.dao.system.ware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.ware.WareReleUser;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  标签分类与人员关联数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 标签分类与人员关联数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class WareReleUserDAO {
	@SuppressWarnings("unchecked")
	public static List<WareReleUser> getWareReleUserList()
	{
		return DBManager.queryFList("getWareReleUserList", "");
	}
	
	/**
	 * 插入标签分类与人员的关联(以分类为主)
	 * 
	 * @param int wcat_id
	 * @param String usre_ids
	 * @param String group_ids
	 * @param String app_id
	 * @param String site_id
	 * @param SettingLogsBean stl
	 * @return boolean
	 */	
	public static boolean insertWareReleUserByCat(int wcat_id,String usre_ids,String group_ids,String app_id,String site_id,SettingLogsBean stl)
	{
		if(deleteWRUByCat(wcat_id+"",site_id))
		{
			try{System.out.println("dao--------usre_ids--------"+usre_ids);
				if(usre_ids != null && !"".equals(usre_ids))
				{
					String[] userArr = usre_ids.split(",");
					insertWRUByCatHandl(wcat_id,userArr,0,app_id,site_id);
				}
				if(group_ids != null && !"".equals(group_ids))
				{
					String[] groupArr = group_ids.split(",");
					insertWRUByCatHandl(wcat_id,groupArr,1,app_id,site_id);
				}
				PublicTableDAO.insertSettingLogs("添加","标签分类与用户关联，标签分类",wcat_id+"",stl);
				return true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 插入标签分类与人员的关联入库方法(以分类为主)
	 * 
	 * @param int wcat_id
	 * @param String[] tempA 用户或用户组ID数组
	 * @param int priv_type 0 用户 1用户组
	 * @param String app_id
	 * @param String site_id
	 * @return boolean
	 */	
	public static boolean insertWRUByCatHandl(int wcat_id,String[] tempA,int priv_type,String app_id,String site_id)
	{
		try{
			for(int i=0;i<tempA.length;i++)
			{
				WareReleUser wru = new WareReleUser();
				wru.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.WARE_RELE_USER_TABLE_NAME));
				wru.setWcat_id(wcat_id);
				wru.setPriv_type(priv_type);
				wru.setSite_id(site_id);
				wru.setApp_id(app_id);
				wru.setPrv_id(Integer.parseInt(tempA[i]));
				DBManager.insert("insert_ware_user", wru);
			}
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 根据标签分类ID删除关联信息
	 * 
	 * @param String wcat_id 
	 * @return boolean
	 */	
	public static boolean deleteWRUByCat(String wcat_id,String site_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("wcat_id",wcat_id);
		m.put("site_id",site_id);
		return DBManager.delete("delete_ware_user_byCat", m);
	}
}
