package com.deya.wcm.dao.zwgk.count;

import java.util.List;
import java.util.Map;

import com.deya.wcm.db.DBManager;

public class GKysqCountDAO {
	
	/**
	 * 依申请公开,申请单状态统计
	 * @param mp	统计条件
	 * @return	取得的Map列表
	 */
	public static List getStateCount(Map<String, String> mp)
	{
		return DBManager.queryFList("ysq_state_count", mp);
	}
	
	/**
	 * 依申请公开,处理方式统计
	 * @param mp
	 * @return
	 */
	public static List getTypeCount(Map<String, String> mp)
	{
		return DBManager.queryFList("ysq_type_count", mp);
	}
	
	/**
	 * 依申请公开,节点下申请单状态统计,
	 * 在指定的时间段按月统计
	 * @param mp
	 * @return
	 */
	public static List getStateCountByNode(Map<String, String> mp)
	{
		return DBManager.queryFList("ysq_state_count_node", mp);
	}
}
