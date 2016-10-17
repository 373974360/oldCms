package com.deya.wcm.services.sendInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.util.CalculateNumber;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.sendInfo.ReceiveInfoBean;
import com.deya.wcm.bean.sendInfo.SendRecordCount;
import com.deya.wcm.dao.sendInfo.SendCountDAO;
import com.deya.wcm.services.cms.category.CategoryManager;

public class ReceiveCountManager {
	/**
     * 得到报送站点信息量统计列表
     * @param Map<String,String> m
     * @return List
     * */
	public static List<SendRecordCount> getSendSiteCountForReceive(Map<String,String> m)
	{
		List<SendRecordCount> count_list = SendCountDAO.getSendSiteCountForReceive(m);
		if(count_list != null && count_list.size() > 0)
		{
			//得到已发布的统计列表
			m.put("adopt_status", "1");
			List<SendRecordCount> publish_list = SendCountDAO.getSendSiteCountForReceive(m);
			for(SendRecordCount src : count_list)
			{
				try{
					for(SendRecordCount pub : publish_list)
					{
						if(src.getUser_id() == pub.getUser_id())
						{
							src.setAdopt_count(pub.getSend_count());
							break;
						}
					}
					src.setNot_count(src.getSend_count()-src.getAdopt_count());
					src.setAdopt_rate(CalculateNumber.getRate(src.getAdopt_count()+"", src.getSend_count()+""));
				}catch(Exception e)
				{
					e.printStackTrace();
					continue;
				}
			}
		}
		return count_list;
	}
	
	/**
     * 根站点得到所有报送到此站点的列表
     * @param Map<String,String> m
     * @return List
     * */
	public static List<ReceiveInfoBean> getSendSiteList(String site_id)
	{
		return SendCountDAO.getSendSiteList(site_id);
	}
	
	/**
     * 根据条件得到栏目接收统计
     * @param Map<String,String> m
     * @return List
     * */
	public static List<SendRecordCount> getReceiveCateListForRecord(Map<String,String> m)
	{
		String site_id = m.get("site_id");
		Set<CategoryBean> cat_set = new HashSet<CategoryBean>();//所有父级的集合
		Map<Integer,SendRecordCount> count_m = new HashMap<Integer,SendRecordCount>();
		List<SendRecordCount> count_list = SendCountDAO.getReceiveCateListForRecord(m);
		if(count_list != null && count_list.size() > 0)
		{
			//得到已发布的统计列表
			m.put("adopt_status", "1");
			List<SendRecordCount> publish_list = SendCountDAO.getReceiveCateListForRecord(m);
			for(SendRecordCount src : count_list)
			{
				try{
					for(SendRecordCount pub : publish_list)
					{
						if(src.getCat_id() == pub.getCat_id())
						{
							src.setAdopt_count(pub.getSend_count());
							break;
						}
					}
					src.setNot_count(src.getSend_count()-src.getAdopt_count());
					src.setAdopt_rate(CalculateNumber.getRate(src.getAdopt_count()+"", src.getSend_count()+""));
					CategoryBean cb = CategoryManager.getCategoryBeanCatID(src.getCat_id(), site_id);
					String position = cb.getCat_position();
					CategoryManager.setCategoryByPosition(cat_set,position,site_id);//把所有父节点入进集合
					cat_set.add(cb);//自身也放进去
					src.setCat_cname(cb.getCat_cname());
					src.setCat_parent_id(cb.getParent_id());
					src.setCat_sort(cb.getCat_sort());
					count_m.put(src.getCat_id(), src);
				}catch(Exception e)
				{
					e.printStackTrace();
					continue;
				}
			}
			//将栏目列表组织成树型
			
		}
		return SendCountManager.rankCountListForCate(count_m,cat_set,0);
	}
}
