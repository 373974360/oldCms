package com.deya.wcm.dao.cms.count;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.cms.count.InfoAccessBean;
import com.deya.wcm.bean.cms.info.HitsCountBean;
import com.deya.wcm.db.DBManager;

public class AccessCountDao {
    public static List<InfoAccessBean> getAccessCountsBySite(Map m){
    	return (List<InfoAccessBean>)DBManager.queryFList("getAccessCountsBySite_id", m);
    }
    
    public static List<InfoAccessBean> getSiteCateAccessList(Map m){
    	return (List<InfoAccessBean>)DBManager.queryFList("getSiteCateAccessList", m);
    }
    
    public static List<InfoAccessBean> getAccessInfoLists(Map m){
    	return (List<InfoAccessBean>)DBManager.queryFList("getAccessInfoLists", m);
    }
    
    public static InfoAccessBean getBeanByID(String info_id){
        return (InfoAccessBean)DBManager.queryFObj("getBeanByID", info_id);
    } 
    
    public static List<InfoAccessBean> getAccessInfoListsByCat_SiteId(Map m)
    {
    	return (List<InfoAccessBean>)DBManager.queryFList("getcounts_ByCatSiteId", m);
    }
    
    public static boolean deleteAccessCountInfo(Map mp){
		return  DBManager.delete("delete_AccessCountInfos", mp);
	}
    
      //统计每个栏目的信息访问量
      public static List<InfoAccessBean>  getCatOrderListByCat_id(Map mp){
      		return DBManager.queryFList("getCatOrderListByCatid", mp);
      }
      
      //统计每条信息的访问量
      public static List<InfoAccessBean>  getInfoOrderListByInfo_id(Map mp){
      		return DBManager.queryFList("getInfoOrderListByInfoid", mp);
      }

      //昨日，今日，riju访问量
    public static Map<String,String> getDayAccessCountList(String site_id,String constant) throws ParseException
  	{
    	if("".equals(constant))
		{
    		constant = "0";
		}
    	Map<String, String> rs_mp = new HashMap<String, String>();
    	
  		String current_day = DateUtil.getCurrentDate();
  		String yesterday = DateUtil.getDateBefore(current_day,1);
  
  		String current_day_d = DateUtil.formatToString(current_day,"dd");
  		String current_day_m = DateUtil.formatToString(current_day,"MM");
  		String current_day_y = DateUtil.formatToString(current_day,"yyyy");
  		
  		String yesterday_d = DateUtil.formatToString(yesterday,"dd");
  		String yesterday_m = DateUtil.formatToString(yesterday,"MM");
  		String yesterday_y = DateUtil.formatToString(yesterday,"yyyy");
  		
  		//当天时间
  		Map<String, String> t_m = new HashMap<String, String>();
  		t_m.put("site_id",site_id);
  		t_m.put("access_day",current_day_d);
  		t_m.put("access_month",current_day_m);
  		t_m.put("access_year",current_day_y);
  		String today_num =(String)DBManager.getString("getDayAccessCount",t_m);
  		if(today_num =="" || today_num == null){
  			today_num ="0";
  		}
  		
  	    //昨天时间
  		Map<String, String> y_map = new HashMap<String, String>();
  		y_map.put("site_id",site_id);
  		y_map.put("access_day",yesterday_d);
  		y_map.put("access_month",yesterday_m);
  		y_map.put("access_year",yesterday_y);
  		String yesterday_num =(String)DBManager.getString("getDayAccessCount",y_map);
  		if(yesterday_num =="" || yesterday_num == null){
  			yesterday_num="0";
  		}
  		
  		String year_month =  DateUtil.formatToString(current_day,"yyyy-MM");
		String start_time = year_month +"-01 00:00:00";
		String end_time = year_month +"-31 23:59:59";
		
		Map<String, String> m_map = new HashMap<String, String>();
		m_map.put("site_id",site_id);
  		m_map.put("start_day",start_time);
  		m_map.put("end_day",end_time);
  		String monthnum =(String)DBManager.getString("getMonthAccessCount",m_map);
		if(monthnum =="" || monthnum == null){
			monthnum ="0";		
		}
		
  		int day_of_month  =  DateUtil.getDayOfMonth(DateUtil.getDate(current_day,"yyyy-MM-dd"));
		double average  = Integer.parseInt(monthnum)/day_of_month;
		String averageNum = Integer.parseInt(new java.text.DecimalFormat("0").format(average))+"";
		
		rs_mp.put("todayNum",Integer.parseInt(today_num)+Integer.parseInt(constant)+"");
		rs_mp.put("yesterDayNum",Integer.parseInt(yesterday_num)+Integer.parseInt(constant)+"");
		rs_mp.put("averageDayNum",Integer.parseInt(averageNum)+Integer.parseInt(constant)+"");
		
  		return rs_mp;
  	}

    //统计每个栏目的信息访问量
    public static List<HitsCountBean>  getHitsByCat(Map mp){
        return DBManager.queryFList("getHitsByCat", mp);
    }

    //统计每条信息的访问量
    public static List<HitsCountBean>  getHitsByInfo(Map mp){
        return DBManager.queryFList("getHitsByInfo", mp);
    }
}