package com.deya.wcm.services.zwgk.count;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.bean.zwgk.count.GKCountBean;
import com.deya.wcm.bean.zwgk.node.GKNodeBean;
import com.deya.wcm.dao.zwgk.count.GKRankDAO;
import com.deya.wcm.services.org.user.UserManager;
import com.deya.wcm.services.zwgk.node.GKNodeManager;

/**
 * ZWGK 栏目排行
 * @author liqi
 *
 */
public class GKRankManager {
	/**
	 * 信息填报工作量排行
	 * @param mp 取得条件,开始时间(start_day),结束时间(end_day),
	 * 取得条数(num),节点IDS(site_id显示部分站点的排名)
	 * @return 返回的列表中的列表包含了统计信息 .
	 * 顺序为:节点名称,录入人名称, 录入信息总数,已发信息条数,百分比(5个)
	 */
	public static List<List> GKWorkLoadRank(Map<String, String> mp) {
		List<List> retLt = new ArrayList<List>();
		List<Map> ranklt =GKRankDAO.GKWorkLoadRank(mp);
		try{
			// 控制取得的条数
			String num = mp.get("num");
			int i_num = 10;
			if(num != null && !"".equals(num)) {
				i_num = Integer.valueOf(num);
			  if ("0".equals(num)) {
                i_num = ranklt.size() + 1;
               }
			}
			
			for(int i=0; i<ranklt.size()&&i<i_num; i++){
				Map totalMap = ranklt.get(i);
				
				List<String> subList = new ArrayList<String>();
				// 添加节点名称
				String site_id = (String)totalMap.get("SITE_ID");
				GKNodeBean nodebean = GKNodeManager.getGKNodeBeanByNodeID(site_id);
				String node_name = nodebean==null ? site_id : nodebean.getNode_name();// 如果取不到节点去节点的ID
				subList.add(node_name);
				// 添加录入人名称
				//int userr_id = ((BigDecimal)totalMap.get("INPUT_USER")).intValue();
				int userr_id = Integer.valueOf(String.valueOf(totalMap.get("INPUT_USER")));
				UserBean ub = UserManager.getUserBeanByID(userr_id+"");
				if(ub != null) {
					subList.add(ub.getUser_realname());
				} else {
					subList.add(userr_id+"");
				}
				// subList.add(userr_id+"--test");
				// 录入信息总数
				//int record_count = ((BigDecimal)totalMap.get("RECORD_COUNT")).intValue();
				int record_count = Integer.valueOf(String.valueOf(totalMap.get("RECORD_COUNT")));
				subList.add(record_count+"");
				
				// 发布信息数目
				//int pub_count = ((BigDecimal)totalMap.get("PUB_COUNT")).intValue();
				int pub_count = Integer.valueOf(String.valueOf(totalMap.get("PUB_COUNT")));
				subList.add(pub_count+"");
				  
				// 百分比,要用百分比的形式表示Double
				//double rate = ((BigDecimal)totalMap.get("RATE")).doubleValue() * 100;
				double rate = (Double.valueOf(String.valueOf(totalMap.get("RATE")))).doubleValue() * 100;
				  
				NumberFormat nf = new DecimalFormat();
				nf.setMaximumFractionDigits(2);				
				nf.setRoundingMode(RoundingMode.HALF_UP);
				subList.add(nf.format(rate));
				
				retLt.add(subList);
			}
			return retLt;
		}catch (Exception e) {
			e.printStackTrace();
			return retLt;
		}
	}
	
	/**
	 * 节点信息量排行
	 * @param mp	mp 取得条件,开始时间(start_day),结束时间(end_day),
	 * 取得条数(num),节点IDS(site_id显示部分站点的排名)
	 * @return
	 */
	public static List<GKCountBean> GKInfoCountRank(Map<String, String> mp)
	{
		//List<GKCountBean> ranklt = GKRankDAO.GKInfoCountRank(mp);
		
		String startDate = mp.get("start_day");
		String endDate = mp.get("end_day");
		String node_ids = mp.get("site_ids"); 
		List<GKCountBean> ranklt = GKCountManager.getAllSiteGKCountList(startDate, endDate, node_ids);
		
		// 控制取得的条数
		String num = mp.get("num");
		int i_num = 10;
		if(num != null && !"".equals(num)) {
			i_num = Integer.valueOf(num);
			if ("0".equals(num)){
             i_num = ranklt.size() + 1;
            }
		}
		int index = ranklt.size()>i_num ? i_num : ranklt.size();
		ranklt = ranklt.subList(0, index);
		
		for(GKCountBean bean : ranklt) {
			GKNodeBean nodebean = GKNodeManager.getGKNodeBeanByNodeID(bean.getSite_id());
			String node_name = nodebean==null ? bean.getSite_id() : nodebean.getNode_name();// 如果取不到节点去节点的ID
			bean.setSite_name(node_name);
		}
		
		return ranklt;
	}
	
	public static void main(String arg[]){
		double rate = 0.9876; 
		double rates = (BigDecimal.valueOf(rate)).doubleValue() * 100;
		System.out.println("rates = " + rates);
	}
}
