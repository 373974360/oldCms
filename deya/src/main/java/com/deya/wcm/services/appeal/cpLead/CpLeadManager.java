/**
 * 注册用户的业务层
 */
package com.deya.wcm.services.appeal.cpLead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.cpDept.CpDeptBean;
import com.deya.wcm.bean.appeal.cpLead.CpLeadBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.appeal.cpLead.CpLeadDAO;
import com.deya.wcm.services.appeal.cpDept.CpDeptManager;

/**
 * @author 王磊
 *
 */
public class CpLeadManager implements ISyncCatch{
	
private static List<CpLeadBean> cpLeadList = new ArrayList<CpLeadBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		cpLeadList.clear();
		try{
			cpLeadList = CpLeadDAO.getAllCpLeadList();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 刷新领导人集合
	 */
	public static void reloadCpLeadList(){
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.appeal.cpLead.CpLeadManager");
	}

	/**
	 * 得到所有注册领导
	 * @return
	 */
	public static List<CpLeadBean> getAllCpLeadList() {
		return cpLeadList;
	}
	
	/**
	 * 新增注册领导
	 * @param lead
	 * @return
	 */
	public static boolean insertCpLead(CpLeadBean lead){
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.APPEAL_Lead_TABLE_NAME);
		lead.setLead_id(id);
		if(CpLeadDAO.insertCpLead(lead)){
			reloadCpLeadList();
			return true;
		} else{
			return false;
		}
	}
	
	/**
	 * 修改注册领导
	 * @param lead
	 * @return
	 */
	public static boolean updateCpLead(CpLeadBean lead){
		if(CpLeadDAO.updateCpLead(lead)){
			reloadCpLeadList();
			return true;
		} else{
			return false;
		}
	}
	
	
	
	/**
	 * 删除 注册领导
	 * @param leads
	 * @return
	 */
	public static boolean deleteCpLead(String leads){
		Map<String,String> m = new HashMap<String,String>();
		m.put("lead_ids", leads);
		
		if(CpLeadDAO.deleteCpLead(m)){
			reloadCpLeadList();
			return true;
		} else{
			return false;
		}
	}
	
	/**
	 * 得到指定ID的领导
	 * @param lead_id
	 * @return
	 */
	public static CpLeadBean getCpLeadById(String lead_id){
		
		if(cpLeadList != null && cpLeadList.size() > 0)
		{
			for(int i=0;i<cpLeadList.size();i++)
			{
				if(lead_id.equals(cpLeadList.get(i).getLead_id()+""))
				{
					CpLeadBean cpLead = cpLeadList.get(i);
					CpDeptBean dept = CpDeptManager.getCpDeptBean(cpLead.getDept_id());
					if(dept != null){
						cpLead.setDept_name(dept.getDept_name());
					}
					return cpLead;
				}
			}
		}
		CpLeadBean cpLead = CpLeadDAO.getCpLeadById(lead_id); 
		if(cpLead != null)
		{
			CpDeptBean dept = CpDeptManager.getCpDeptBean(cpLead.getDept_id());
			if(dept != null){
				cpLead.setDept_name(dept.getDept_name());
			}
			return cpLead;
		}else
			return null;
	}
	
	/**
	 * 保存领导用户排序
	 * @param m
	 * @return
	 */
	public static boolean savesortCpLead(int start,String lead_ids){
		if(CpLeadDAO.savesortCpLead(start,lead_ids)){
			reloadCpLeadList();
			return true;
		} else{
			return false;
		}
	}
	
	/**
	 * 根据条件、分页展示注册领导
	 * @param m
	 * @return
	 */
	public static List<CpLeadBean> getCpLeadList(Map<String,String> m){
		List<CpLeadBean> leadList= CpLeadDAO.getCpLeadList(m);
		if(leadList != null && leadList.size() > 0){
			for(int i=0;i<leadList.size();i++){
				CpDeptBean dept = CpDeptManager.getCpDeptBean(leadList.get(i).getDept_id());
				if(dept != null){
					leadList.get(i).setDept_name(dept.getDept_name());
				}
						
			}
		}
		return leadList;
	}
	
	/**
	 * 根据条件得到注册领导总数
	 * @param m
	 * @return
	 */
	public static String getCpLeadCount(Map<String,String> m){
		return CpLeadDAO.getCpLeadCount(m); 
	}
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map m=new HashMap();
		m.put("start_num",0);
		m.put("page_size",15);
		m.put("sort_name","sort_id");
		m.put("sort_type","asc");
		System.out.println(getCpLeadList(m));
	}

	

}
