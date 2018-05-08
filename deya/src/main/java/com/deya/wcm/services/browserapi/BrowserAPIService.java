package com.deya.wcm.services.browserapi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.cms.count.CmsCountBean;
import com.deya.wcm.bean.cms.count.SiteCountBean;
import com.deya.wcm.services.cms.count.SiteCountManager;
import com.deya.wcm.services.cms.count.SiteCountRPC;
import com.deya.wcm.services.cms.countsource.CmsCountSourceRPC;


//模板中要调用到的方法
public class BrowserAPIService {
       
	//通过条件得到各个站点的信息发布量 -- 按发布信息数降序排序
	//条件：
	/*
	 * Map中的参数:  site_ids 站点id（多个站点id可以用逗号分隔）   若没有该参数则表示所有站点
	 *              start_day 开始时间  （时间格式 ：2012-10-01 10:10:10）
	 *              end_day 开始时间  （时间格式 ：2012-10-01 10:10:10）
	 *              atype 统计类型    如果为空 或 ""就是全部的   此时上面的时间才会生效
	 *                             如果为"lastmonth"就是上个月的
	 *                             如果为"currmonth"就是本月的
	 *              num 得到的站点个数 
	 * 返回的bean中的属性：
	 *              site_name      站点名称
	 *              releasedCount  发布信息条数
	 *              
	 */
	public static List<SiteCountBean> getSiteCountListByMap(Map map){
		List<SiteCountBean> listResult = new ArrayList<SiteCountBean>();
		try{
			
			Map mapt = map;
			String start_day = (String)map.get("start_day");
			if(start_day!=null && !"".equals(start_day)){
				if(!start_day.contains(":")){
					start_day =  start_day+" 00:00:01";
				}
				mapt.put("start_day",start_day);
			}else{
				mapt.put("start_day", "2000-01-01 00:00:01");
			}
			
			String end_day = (String)map.get("end_day");
			if(end_day!=null && !"".equals(end_day)){
				if(!end_day.contains(":")){
					end_day =  start_day+" 23:59:59";
				}
				mapt.put("end_day",end_day);
			}else{
				mapt.put("end_day", DateUtil.getCurrentDateTime());
			}
			String atype = (String)map.get("atype");
			if(atype==null){
				atype = "";
			}
			if("lastmonth".equals(atype)){//全部的统计
				//得到上个月的时间 开始时间
				mapt.put("start_day",getFirstDayByLastMonth());
				mapt.put("end_day",getLastDayByLastMonth());
			}else if("currmonth".equals(atype)){
				mapt.put("start_day",getFirstDayByCurrMonth());
				mapt.put("end_day",getLastDayByCurrMonth());
			}
			
			
			List<SiteCountBean> list = SiteCountManager.getSiteCountListBySite(map);
			String numString = (String)map.get("num");
			if(numString==null){
				numString = "0";
			}
			int num = Integer.parseInt(numString);
			
			//按数据量排序 -- 降序排序
			SiteCountComparator countComparator = new SiteCountComparator();
			Collections.sort(list, countComparator);
			
			for(int i=0;i<list.size();i++){
				if(num!=0 && i<num){
					listResult.add(list.get(i));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return listResult;
		}
	}
	
	
	//通过条件得到各个部门的信息发布量  只针对新闻类  只返回组织机构中的末级节点  -- 按发布信息数降序排序
	//条件：
	/*
	 * Map中的参数:  site_ids 站点id（多个站点id可以用逗号分隔）   若没有该参数则表示所有站点
	 *              start_day 开始时间  （时间格式 ：2012-10-01） 可以为空
	 *              end_day 开始时间  （时间格式 ：2012-10-01）      可以为空
	 *              num 得到的部门个数 
	 *              atype 统计类型    如果为空 或 ""就是全部的   此时上面的时间才会生效
	 *                             如果为"lastmonth"就是上个月的
	 *                             如果为"currmonth"就是本月的
	 * 返回的bean中的属性：
	 *              dept_name      部门名称
	 *              inputCount  发布信息条数
	 *              
	 */
	public static List<SiteCountBean> getDeptNameListByMap(Map map){
		List<SiteCountBean> listResult = new ArrayList<SiteCountBean>();
		try{ 
			Map mapt = new HashMap();
			String site_ids = (String)map.get("site_ids");
			if(site_ids!=null && !"".equals(site_ids)){
				mapt.put("site_ids", site_ids);
			}
			String cate_ids = (String)map.get("cate_ids");
			if(cate_ids!=null && !"".equals(cate_ids)){
				mapt.put("cate_ids", cate_ids);
			}
			String start_day = (String)map.get("start_day");
			if(start_day!=null && !"".equals(start_day)){
				if(!start_day.contains(":")){
					start_day =  start_day+" 00:00:01";
				}
				mapt.put("start_day",start_day);
			}else{
				mapt.put("start_day", "2000-01-01 00:00:01");
			}
			
			String end_day = (String)map.get("end_day");
			if(end_day!=null && !"".equals(end_day)){
				if(!end_day.contains(":")){
					end_day =  start_day+" 23:59:59";
				}
				mapt.put("end_day",end_day);
			}else{
				mapt.put("end_day", DateUtil.getCurrentDateTime());
			}
			String atype = (String)map.get("atype");
			if(atype==null){
				atype = "";
			}
			if("lastmonth".equals(atype)){//全部的统计
				//得到上个月的时间 开始时间
				mapt.put("start_day",getFirstDayByLastMonth());
				mapt.put("end_day",getLastDayByLastMonth());
			}else if("currmonth".equals(atype)){
				mapt.put("start_day",getFirstDayByCurrMonth());
				mapt.put("end_day",getLastDayByCurrMonth());
			}
			
			//得到所有的
			List<SiteCountBean> list = SiteCountRPC.getSiteCountListByInput(mapt);
			//System.out.println(" list --- > " + list.size());
			//要返回的结果
			List<SiteCountBean> listTemp = new ArrayList<SiteCountBean>();
			if(list.size() != 0) {
				for(int i=0;i<list.size();i++){
					setTreeNode(list.get(i), "",listTemp);
				}
			}
			//System.out.println(" listTemp --- > " + listTemp.size());
			    
			String numString = (String)map.get("num");
			if(numString==null){
				numString = "0";
			}
			int num = Integer.parseInt(numString);
			
			//按数据量排序 -- 降序排序
			com.deya.wcm.services.cms.count.SiteCountComparator countComparator = new com.deya.wcm.services.cms.count.SiteCountComparator();
			Collections.sort(listTemp, countComparator);
			
			for(int i=0;i<listTemp.size();i++){
				if(num!=0 && i<num){
					listResult.add(listTemp.get(i));
				}
			}  
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return listResult;
		}
	}
	
	//递归调用
	public static void setTreeNode(SiteCountBean bean,String parent_id,List<SiteCountBean> list){
		try{
			if(bean.isIs_leaf()){//末级节点		
				//System.out.println(bean.getDept_name() + "----" + bean.getInputCount());
				list.add(bean);
			}
			else 
			{
				  List<SiteCountBean> child_list = bean.getChild_list();
				  if(child_list.size() > 0)
				  { 
					 for(int i=0;i<child_list.size();i++)
					 {						
						setTreeNode(child_list.get(i), bean.getDept_id()+"",list);
					 }
				  }
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//通过条件得到按照栏目统计站点的发布信息  -  按信息来源分类  -- 按发布信息数降序排序
	//条件：
	/*
	 * Map中的参数:  site_ids 站点id（多个站点id可以用逗号分隔）   若没有该参数则表示所有站点
	 *              start_day 开始时间  （时间格式 ：2012-10-01） 可以为空
	 *              end_day 结束时间  （时间格式 ：2012-10-01）      可以为空
	 *              num 得到的部门个数 
	 *              atype 统计类型    如果为空 或 ""就是全部的   此时上面的时间才会生效
	 *                             如果为"lastmonth"就是上个月的
	 *                             如果为"currmonth"就是本月的
	 * 返回的bean中的属性：
	 *              cat_name      部门名称
	 *              count  发布信息条数
	 *              
	 */
	public static List<CmsCountBean> getSourceNameListByMap(Map map){
		List<CmsCountBean> listResult = new ArrayList<CmsCountBean>();
		try{ 
			Map mapt = new HashMap();
			String site_ids = (String)map.get("site_ids");
			if(site_ids!=null && !"".equals(site_ids)){
				mapt.put("site_ids", site_ids);
			}
			String cate_ids = (String)map.get("cate_ids");
			if(cate_ids!=null && !"".equals(cate_ids)){
				mapt.put("cate_ids", cate_ids);
			}
			String start_day = (String)map.get("start_day");
			if(start_day!=null && !"".equals(start_day)){
				if(!start_day.contains(":")){
					start_day =  start_day+" 00:00:01";
				}
				mapt.put("start_day",start_day);
			}else{
				mapt.put("start_day", "2000-01-01 00:00:01");
			}
			
			String end_day = (String)map.get("end_day");
			if(end_day!=null && !"".equals(end_day)){
				if(!end_day.contains(":")){
					end_day =  start_day+" 23:59:59";
				}
				mapt.put("end_day",end_day);
			}else{
				mapt.put("end_day", DateUtil.getCurrentDateTime());
			}
			String atype = (String)map.get("atype");
			if(atype==null){
				atype = "";
			}
			if("lastmonth".equals(atype)){//全部的统计
				//得到上个月的时间 开始时间
				mapt.put("start_day",getFirstDayByLastMonth());
				mapt.put("end_day",getLastDayByLastMonth());
			}else if("currmonth".equals(atype)){
				mapt.put("start_day",getFirstDayByCurrMonth());
				mapt.put("end_day",getLastDayByCurrMonth());
			}
			
			//得到所有的
			List<CmsCountBean> listTemp = CmsCountSourceRPC.getInfoCountListBySource(mapt);
			//System.out.println(" list --- > " + listTemp.size());
			
			String numString = (String)map.get("num");
			if(numString==null){
				numString = "0";
			}
			int num = Integer.parseInt(numString);
			
			//按数据量排序 -- 降序排序
//			com.deya.wcm.services.cms.count.SiteCountComparator countComparator = new com.deya.wcm.services.cms.count.SiteCountComparator();
//			Collections.sort(listTemp, countComparator);
			
			for(int i=0;i<listTemp.size();i++){
				if(num!=0 && i<num){
					listResult.add(listTemp.get(i));
				}
			}  
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return listResult;
		}
	}
	
	
	//得到本月的第一天
	public static String getFirstDayByCurrMonth(){
		return (String)findLastMonth(0).get("prevMonthFD");
	}
	
	//得到本月的最后一天
	public static String getLastDayByCurrMonth(){
		return (String)findLastMonth(0).get("prevMonthPD");
	}
	
	
	//得到上个月的第一天
	public static String getFirstDayByLastMonth(){
		return (String)findLastMonth(-1).get("prevMonthFD");
	}
	
	//得到上个月的最后一天
	public static String getLastDayByLastMonth(){
		return (String)findLastMonth(-1).get("prevMonthPD");
	}

	/**
	 * 得到上个月的第一和最后一天带时间
	 * 
	 * @return MAP{prevMonthFD:当前日期上个月的第一天带时间}{prevMonthPD:当前日期上个月的最后一天带时间}
	 */
	private static Map<String, String> findLastMonth(int num) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		calendar.add(Calendar.MONTH, num);
		Date theDate = calendar.getTime();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first_prevM = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first_prevM).append(
				" 00:00:00");
		day_first_prevM = str.toString();

		calendar.add(cal.MONTH, 1);
		calendar.set(cal.DATE, 1);
		calendar.add(cal.DATE, -1);
		String day_end_prevM = df.format(calendar.getTime());
		StringBuffer endStr = new StringBuffer().append(day_end_prevM).append(
				" 23:59:59");
		day_end_prevM = endStr.toString();

		Map<String, String> map = new HashMap<String, String>();
		map.put("prevMonthFD", day_first_prevM);
		map.put("prevMonthPD", day_end_prevM);
		return map;
	}
}
