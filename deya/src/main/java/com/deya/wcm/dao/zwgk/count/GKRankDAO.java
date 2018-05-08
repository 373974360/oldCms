package com.deya.wcm.dao.zwgk.count;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.zwgk.count.GKCountBean;
import com.deya.wcm.db.DBManager;

public class GKRankDAO {
	
	/**
	 * 取得信息录入工作量排行
	 * @param mp	取得条件,开始时间,结束时间,节点IDS(显示部分站点的排名)
	 * 排序方式,等
	 * @return	返回取得的录入信息列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Map> GKWorkLoadRank(Map<String, String> mp)
	{
		return (List<Map>)DBManager.queryFList("gk_xxtb_rank", mp);
	}
	
	/** 
	 * 取得节点信息量排行
	 * @param mp	取得条件,开始时间,结束时间,节点IDS(显示部分站点的排名)
	 * @return	节点信息量排行信息
	 */
	@SuppressWarnings("unchecked")
	public static List<GKCountBean> GKInfoCountRank(Map<String, String> mp)
	{
		return (List<GKCountBean>)DBManager.queryFList("gk_jdxx_rank", mp);
	}
	
	
}
