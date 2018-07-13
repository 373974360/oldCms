package com.deya.wcm.services.cms.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.dao.cms.info.InfoDAO;
import com.deya.wcm.dao.zwgk.info.GKInfoDAO;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.cms.category.CategoryUtil;
import com.deya.wcm.services.cms.workflow.WorkFlowManager;
import com.deya.wcm.services.zwgk.info.GKInfoRPC;

/**
 *  信息桌面处理类
 * <p>Title: CicroDate</p>
 * <p>Description: 信息桌面处理类，用于提供给用户桌面提供待审信息的计算方法.</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Cicro</p>
 * @author liqi
 * @version 1.0
 * * 
 */

public class InfoDesktop {
		
	/**
     * 根据用户ID，站点ID得到该用户能看到的待审信息
     * @param int user_id
     * @param String site_id
     * @param page_size
     * @return Map 这里返回Map 把总数和列表放在一起，以名分开取的时候浪费性能,取条件时耗性能
     * */
	public static Map<String,Object> getWaitVerifyInfoList(Map<String,String> m)
	{
		int user_id = Integer.parseInt(m.get("user_id"));
		String site_id=m.get("site_id");
		Map<String,Object> return_map = new HashMap<String,Object>();
		
		//第一步，根据站点和用户ID得到它所能管理的所有栏目ID
		String cat_ids = CategoryUtil.getCategoryIDSByUser(0,site_id , user_id);
		//第二步，得到需要审核栏目的sql
		String sql = getWaitVerifyInfoSql(user_id+"",cat_ids,m.get("app_id"),site_id);
		
		if(sql == null || "".equals(sql))
		{
			List<InfoBean> l = new ArrayList<InfoBean>();
			return_map.put("info_count", "0");
			return_map.put("info_list", l);
		}
		else
		{
			m.put("searchString2", sql);
			if("cms".equals(m.get("app_id")))
			{
				return_map.put("info_count", InfoDAO.getInfoCount(m));
				return_map.put("info_List", InfoDAO.getInfoBeanList(m));
			}else
			{
				return_map.put("info_count", GKInfoDAO.getGKInfoCount(m));
				return_map.put("info_List", GKInfoDAO.getGKInfoList(m));
			}
		}
		return return_map;		
	}
	
	/**
     * 根据用户ID和多个栏目ID拼出这个用户所能审核的信息sql(只列出有审核流程的)
     * @param String user_id
     * @param String category_ids
     * @param String app_id
     * @param site_id
     * @return String
     * */
	public static String getWaitVerifyInfoSql(String user_id,String category_ids,String app_id,String site_id)
	{
		String conn = "";		
		String[] tempA = category_ids.split(",");
		if(tempA != null && tempA.length > 0)
		{
			for(int i=0;i<tempA.length;i++)
			{	
				if(tempA[i] != null && !"".equals(tempA[i]))
				{
					CategoryBean cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(tempA[i]),site_id);
					if(cb != null)
					{
						if(cb.getWf_id() != 0)
						{
							//如果该栏目有流程，根据用户得到步骤ID
							int step_id = WorkFlowManager.getMaxStepIDByUserID(cb.getWf_id(),user_id,app_id,site_id);
							System.out.println("step_id=======>"+step_id);
							if(step_id==100){
								conn += "or (ci.cat_id = "+tempA[i]+" and ci.step_id < "+step_id+") ";
							}else{
								conn += "or (ci.cat_id = "+tempA[i]+" and ci.step_id = "+(step_id-1)+") ";
							}

						}
					}
				}
			}
			if(conn != null && !"".equals(conn))
			{
				conn = conn.substring(2);
				conn = " and ("+conn+")";
			}						
		}
		//System.out.println(conn);
		return conn;
	}
	
	public static void main(String args[])
	{
		Map<String,String> con_map = new HashMap<String,String>();
		con_map.put("user_id", "199");
		con_map.put("info_status", "2");
		con_map.put("final_status", "0");
		con_map.put("start_num", "0");
		con_map.put("page_size", "15");
		con_map.put("sort_name", "ci.info_id");
		con_map.put("sort_type", "desc");
		con_map.put("site_id", "GKxfj");
		con_map.put("app_id", "zwgk");
		System.out.println(getWaitVerifyInfoList(con_map));
		//System.out.println(CategoryUtil.getCategoryIDSByUser(0,"HIWCMdemo" , 199));
		//System.out.println(getWaitVerifyInfoSql("199","1020,1021,1023,1024,1026,2049,1113,1118,1130,1148,1172,2000,2001,2002,1999,1997,2036,2045,2046,2047,2042,2043,2044","cms","HIWCMdemo"));
	}
}
