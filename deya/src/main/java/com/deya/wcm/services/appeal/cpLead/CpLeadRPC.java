/**
 * 
 */
package com.deya.wcm.services.appeal.cpLead;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.cpLead.CpLeadBean;

/**
 * @author Administrator
 *
 */
public class CpLeadRPC {
	
	/**
	 * 得到所有领导
	 * @return
	 */
	public static List<CpLeadBean> getAllCpLeadList(){
		return CpLeadManager.getAllCpLeadList(); 
	}
	
	/**
	 * 新增注册领导
	 * @param lead
	 * @return
	 */
	public static boolean insertCpLead(CpLeadBean lead){
	
		return CpLeadManager.insertCpLead(lead); 
	}
	
	/**
	 * 修改注册领导
	 * @param lead
	 * @return
	 */
	public static boolean updateCpLead(CpLeadBean lead){
		return CpLeadManager.updateCpLead(lead); 
	}
	
	
	/**
	 * 删除 注册领导
	 * @param leads
	 * @return
	 */
	public static boolean deleteCpLead(String leads){
		return CpLeadManager.deleteCpLead(leads); 
	}
	
	/**
	 * 得到指定ID的领导
	 * @param lead_id
	 * @return
	 */
	public static CpLeadBean getCpLeadById(String lead_id){
		return CpLeadManager.getCpLeadById(lead_id); 
	}
	
	/**
	 * 保存领导用户排序
	 * @param m
	 * @return
	 */
	public static boolean savesortCpLead(int start,String lead_ids){
		return CpLeadManager.savesortCpLead(start,lead_ids);
	}
	
	/**
	 * 根据条件、分页展示注册领导
	 * @param m
	 * @return
	 */
	public static List<CpLeadBean> getCpLeadList(Map<String,String> m){
		return CpLeadManager.getCpLeadList(m); 
	}
	
	/**
	 * 根据条件得到注册领导总数
	 * @param m
	 * @return
	 */
	public static String getCpLeadCount(Map<String,String> m){
		return CpLeadManager.getCpLeadCount(m); 
	}
	
	
	
	
}
