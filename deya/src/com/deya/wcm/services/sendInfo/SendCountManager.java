package com.deya.wcm.services.sendInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.util.CalculateNumber;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.sendInfo.SendRecordBean;
import com.deya.wcm.bean.sendInfo.SendRecordCount;
import com.deya.wcm.dao.sendInfo.SendCountDAO;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.org.user.UserManager;

public class SendCountManager {
	/**
     * 按站点得到人员的报送信息工作量统计
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return String
     * */
	public static List<SendRecordCount> getSendRecordUserCount(Map<String,String> m)
	{
		List<SendRecordCount> count_list = SendCountDAO.getSendUserListForRecord(m);
		if(count_list != null && count_list.size() > 0)
		{
			//得到已发布的统计列表
			m.put("adopt_status", "1");
			List<SendRecordCount> publish_list = SendCountDAO.getSendUserListForRecord(m);
			for(SendRecordCount src : count_list)
			{
				try{
					src.setUser_realname(UserManager.getUserRealName(src.getUser_id()+""));
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
     * 按站点得到目录的报送信息量统计
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return String
     * */
	public static List<SendRecordCount> getSendCateListForRecord(Map<String,String> m)
	{
		String site_id = m.get("send_site_id");
		Set<CategoryBean> cat_set = new HashSet<CategoryBean>();//所有父级的集合
		Map<Integer,SendRecordCount> count_m = new HashMap<Integer,SendRecordCount>();
		List<SendRecordCount> count_list = SendCountDAO.getSendCateListForRecord(m);
		if(count_list != null && count_list.size() > 0)
		{
			//得到已发布的统计列表
			m.put("adopt_status", "1");
			List<SendRecordCount> publish_list = SendCountDAO.getSendCateListForRecord(m);
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
		return rankCountListForCate(count_m,cat_set,0);
	}
	
	
	
	public static List<SendRecordCount> rankCountListForCate(Map<Integer,SendRecordCount> count_m,Set<CategoryBean> cat_set,int parent_id)
	{
		List<SendRecordCount> list2 = new ArrayList<SendRecordCount>();
		//循环cat_set
		for(CategoryBean cb : cat_set)
		{
			if(cb.getParent_id() == parent_id)
			{
				if(count_m.containsKey(cb.getCat_id()))
				{//如果此节点有报送对象，直接加入list
					list2.add(count_m.get(cb.getCat_id()));
				}else
				{//否则放一个空的对象到list中，继续找它的子节点
					SendRecordCount src = new SendRecordCount();
					src.setCat_id(cb.getCat_id());
					src.setCat_cname(cb.getCat_cname());
					src.setChild_cate_list(rankCountListForCate(count_m,cat_set,cb.getCat_id()));
					src.setCat_parent_id(parent_id);
					src.setCat_sort(cb.getCat_sort());
					list2.add(src);
				}
			}
		}
		Collections.sort(list2,new sendCategoryComparator());
		return list2;
	}
	
	static class sendCategoryComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			SendRecordCount cgb1 = (SendRecordCount) o1;
			SendRecordCount cgb2 = (SendRecordCount) o2;
		    if (cgb1.getCat_sort() > cgb2.getCat_sort()) {
		     return 1;
		    } else {
		     if (cgb1.getCat_sort() == cgb2.getCat_sort()) {
		      return 0;
		     } else {
		      return -1;
		     }
		    }
		}
	}
	
	/**
     * 根据条件得到报送过的站点列表
     * @param Map<String,String> m
     * @return List
     * */
	public static List<SendRecordBean> getReceiveSiteListForRecord(String site_id)
	{
		return SendCountDAO.getReceiveSiteListForRecord(site_id);
	}
	
	public static void main(String[] args)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("send_site_id", "HIWCMdemo");
		System.out.println(SendCountDAO.getSendSiteList("HIWCMdemo").get(0).getS_site_name());
	}
}
