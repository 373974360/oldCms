package com.deya.wcm.services.zwgk.count;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.zwgk.count.GKCountBean;
import com.deya.wcm.bean.zwgk.count.GKysqCountBean;

public class GKCountRPC {
	
	/**
	 * 取得指定站点，指定应用的栏目昨天的栏目工作统计
	 * @param site_id	站点ID
	 * @param app_id	应用ID
	 * @param date	指定日期，可以为空,或者是yyyy-MM-dd或者yyyy-MM-DD hh:mm:ss
	 * @return
	 */
	public static boolean CateInfoCounting(String site_id, String app_id ,String date)
	{
		return GKCountManager.CateInfoCounting(site_id, app_id, date);
	}
	
	/**
	 * 根据站点ID取得栏目工作统计
	 * 默认取得昨天一天的工作统计情况
	 * @param site_id	站点ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List getGKCountList(String site_id)
	{
		return GKCountManager.getGKCountList(site_id);
	}
	
	/**
	 * 根据站点ID和日期取得栏目工作统计情况
	 * @param site_id	站点ID
	 * @param startDate	开始时间,时间格式yyyy-MM-dd
	 * @param endDate	截至时间,时间格式yyyy-MM-dd
	 * @return
	 */
	public static List<GKCountBean> getGKCountListByDate(String site_id, String startDate, String endDate)
	{
		return GKCountManager.getGKCountList(site_id, startDate, endDate);
	}
	
	/**
	 * 取得指定站点下的工作量统计
	 * @param startDate	统计开始日期
	 * @param endDate	统计截至日期
	 * @param node_ids	指定的节点
	 * @return
	 */
	public static List<GKCountBean> getAllSiteGKCountList(String startDate, 
			String endDate, String node_ids) {
		return GKCountManager.getAllSiteGKCountList(startDate, endDate, node_ids);
	}
	
	/**
	 * 不一定能用得到的方法
	 * 根据排序取得所有或者前多少位的排行
	 * @param mp
	 * @return
	 */
	public static List<GKCountBean> getAllSiteGKCountList(Map<String, String> mp) {
		return GKCountManager.getAllSiteGKCountList(mp);
	}
	
	// ***********************排行************************ //
	
	/**
	 * 信息填报工作量排行
	 * @param mp 取得条件,开始时间(start_day),结束时间(end_day),
	 * 取得条数(num),节点IDS(site_id显示部分站点的排名)
	 * @return 返回的列表中的列表包含了统计信息 .
	 * 顺序为:节点名称,录入人名称, 录入信息总数,已发信息条数,百分比(5个)
	 */
	@SuppressWarnings("unchecked")
	public static List<List> GKWorkLoadRank(Map<String, String> mp){
		return GKRankManager.GKWorkLoadRank(mp);
	}
	
	/**
	 * 节点信息量排行
	 * @param mp	mp 取得条件,开始时间(start_day),结束时间(end_day),
	 * 取得条数(num),节点IDS(site_id显示部分站点的排名)
	 * @return
	 */
	public static List<GKCountBean> GKInfoCountRank(Map<String, String> mp)
	{
		return GKRankManager.GKInfoCountRank(mp);
	}
	
	// ******************依申请公开统计********************* //
	
	/**
	 * 取得依申请公开申请单状态统计
	 * @param mp
	 * @return
	 */
	public static List<GKysqCountBean> getYSQStateCnt(Map<String, String> mp) {
		return GKysqCountManager.getYSQStateCnt(mp);
	}
	
	/**
	 * 取得节点下依申请公开申请单状态统计
	 * @param mp
	 * @return
	 */
	public static List<GKysqCountBean> getYSQStateCntByNode(
			Map<String, String> mp) {
		return GKysqCountManager.getYSQStateCntByNode(mp);
	}
	
	/**
	 * 取得申请单处理方式统计
	 * @param mp
	 * @return
	 */
	public static List<GKysqCountBean> getYSQTypeCount(Map<String, String> mp) {
		return GKysqCountManager.getYSQTypeCount(mp);
	}
	
	/**
	 * 政务公开信息量统计 -- 按节点统计
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String gkInfoOutExcel(List listBean,List headerList){
		return GKCountExcelOut.gkInfoOutExcel(listBean, headerList);
	}
	
	/**
	 * 政务公开信息量统计 -- 按节点录入人统计
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String gkWorkInfoOutExcel(List listBean,List headerList){
		return GKCountExcelOut.gkWorkInfoOutExcel(listBean, headerList);
	}
	
	/**
	 * 依申请公开信息量统计 -- 按节点统计
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String ysqgkInfoOutExcel(List listBean,List headerList){
		return GKCountExcelOut.ysqgkInfoOutExcel(listBean, headerList);
	}
	
	/**
	 * 依申请公开信息量统计 -- 按类型统计
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String ysqgkTypeInfoOutExcel(List listBean,List headerList){
		return GKCountExcelOut.ysqgkTypeInfoOutExcel(listBean, headerList);
	}
	
	/**
	 * 信息公开信息量统计 -- 按某个节点中所有栏目统计
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String gkTreeInfoOutExcel(List headerList,String site_id,String startDate,String endDate){
		return GKCountExcelOut.gkTreeInfoOutExcel(headerList, site_id, startDate, endDate);
	}
}
