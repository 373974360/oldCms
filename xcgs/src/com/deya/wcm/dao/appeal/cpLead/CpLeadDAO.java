/**
 * 
 */
package com.deya.wcm.dao.appeal.cpLead;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.cpLead.CpLeadBean;
import com.deya.wcm.db.DBManager;

/**
 * @author Administrator
 *
 */
public class CpLeadDAO {

	/**
	 * 新增注册领导
	 * @param lead
	 * @return
	 */
	public static boolean insertCpLead(CpLeadBean lead){
		return DBManager.insert("insert_cpLead", lead); 
	}
	
	/**
	 * 修改注册领导
	 * @param lead
	 * @return
	 */
	public static boolean updateCpLead(CpLeadBean lead){
		return DBManager.update("update_cpLead", lead); 
	}
	
	
	/**
	 * 删除 注册领导
	 * @param leads
	 * @return
	 */
	public static boolean deleteCpLead(Map<String,String> m){
		return DBManager.delete("delete_cpLead", m); 
	}
	
	/**
	 * 得到指定ID的领导
	 * @param lead_id
	 * @return
	 */
	public static CpLeadBean getCpLeadById(String lead_id){
		return (CpLeadBean)DBManager.queryFObj("getCpLeadBean",lead_id); 
	}
	
	/**
	 * 保存领导用户排序
	 * @param start
	 * @param lead_ids
	 * @return
	 */
	public static boolean savesortCpLead(int start,String lead_ids){
System.out.println("lead_ids-------"+lead_ids);
		String[] tempA = lead_ids.split(",");
		Map m = new HashMap();
		
		try
		{
			for(int i=0;i<tempA.length;i++)
			{
				m.put("lead_id", tempA[i]);
				m.put("sort_id", start+i+1);
System.out.println("update cp_lead set sort_id = "+m.get("sort_id")+" where lead_id = "+m.get("lead_id"));
				DBManager.update("savesort_cpLead", m);
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
	 * 根据条件、分页展示注册领导
	 * @param m
	 * @return
	 */
	public static List<CpLeadBean> getCpLeadList(Map<String,String> m){
		return DBManager.queryFList("getCpLeadList", m); 
	}
	
	/**
	 * 根据条件得到注册领导总数
	 * @param m
	 * @return
	 */
	public static String getCpLeadCount(Map<String,String> m){
		return DBManager.getString("getCpLeadCount", m); 
	}

	/**
	 * 得到所有领导
	 * @return
	 */
	public static List<CpLeadBean> getAllCpLeadList() {
		return DBManager.queryFList("getAllCpLeadList", ""); 
	}
}
