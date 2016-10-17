package com.deya.wcm.dao.zwgk.count;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.zwgk.count.GKCountBean;
import com.deya.wcm.db.DBManager;

/**
 * 信息公开的节点统计类
 * @author liqi
 *
 */
public class GKCountDAO {

	/**
	 * 统计后取得信息公开节点的信息条数
	 * @param mp
	 * @return	从信息主表和信息公开表中取得的统计数据
	 */
	@SuppressWarnings("unchecked")
	public static List getCateInfoCount(Map<String, String> mp)
	{
		return DBManager.queryFList("getCatInfoCount", mp);
	}
	
	/**
	 * 取得栏目工作量统计
	 * 可以取得单条
	 * 条件为开始时间,截至时间,cat_id,site_id等
	 * @param mp	取得条件，包括开始时间,截至时间,cat_id,site_id等
	 * @return
	 */
	public static List getGKCount(Map<String, String> mp)
	{
		return DBManager.queryFList("getGK_countList", mp);
	}
	
	/**
	 * 取得所有站点的栏目工作量统计
	 * 条件为开始时间,截至时间
	 * @param mp	取得条件开始时间,截至时间
	 * @return
	 */
	public static List getAllSiteGKCount(Map<String, String> mp)
	{
		return DBManager.queryFList("getAllSiteGKCount", mp);
	}
	
	/**
	 * 添加栏目统计信息
	 * @param cb	栏目统计信息对象
	 * @return
	 */
	public static boolean insertGKCount(GKCountBean cb)
	{
		return DBManager.insert("insertGK_count", cb);
	}
	
	/**
	 * 修改栏目统计信息
	 * @param cb	栏目统计信息对象
	 * @return
	 */
	public static boolean updateGKCount(GKCountBean cb)
	{
		return DBManager.update("updateGK_count", cb);
	}
	
	/**
	 * 删除栏目统计对象
	 * @param mp	删除条件,包括site_id,update_time,和cat_id
	 * cat_id 可以是多个,之间使用,分隔
	 * @return
	 */
	public static boolean deleteGKCount(Map<String, String> mp)
	{
		return DBManager.delete("deleteGK_count", mp);
	}
	
	
	/**
	 * 通过i.info_status字段标示 得到不同方式信息公开的数量
	 * @param mp
	 * @return  List<GKCountBean>
	 */
	public static List<GKCountBean> getGkInfoCountByStatus(Map mp)
	{   
		return DBManager.queryFList("gk_count.getGkInfoCountByStatus", mp);
	}
	
	/**
	 * 通过i.info_status字段标示 按某个公开节点下面的栏目得到不同方式信息公开的数量
	 * @param mp 
	 * @return  List<GKCountBean>
	 */
	public static List<GKCountBean> getGkInfoCountByStatusANDNode(Map mp)
	{   
		return DBManager.queryFList("gk_count.getGkInfoCountByStatusANDNode", mp);
	}
	
	
	public static void main(String[] arg)
	{

	}
}

