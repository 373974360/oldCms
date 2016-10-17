package com.deya.wcm.services.cms.count;

import java.util.List;
import java.util.Map;
import com.deya.wcm.bean.cms.count.InfoAccessBean;
import com.deya.wcm.bean.cms.info.HitsCountBean;
import com.deya.wcm.dao.cms.count.AccessCountDao;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/*
 * 访问统计类
 * */

public class AccessCountRPC {
	
	//按站点统计
	public static List<InfoAccessBean> getAccessCountsBySite(Map mp){
		return AccessCountManager.getAccessCountsBySite(mp);
	}
	
	/**
	 * 统计导出Excel 按站点
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String AccessCountsBySiteOutExcel(List listBean,List headerList){
		return CmsCountExcelOut.AccessCountsBySiteOutExcel(listBean, headerList);
	}
	
	
	//按某一个站点下面的所有栏目统计
	public static List<InfoAccessBean> getSiteCountListByCate(Map mp){
		return AccessCountManager.getSiteCountListByCate(mp);
	}
	
	//统计单个站点下面的栏目信息访问量
	public static List<InfoAccessBean> getSiteCateAccessList(Map mp){
		return AccessCountManager.getSiteCateAccessList(mp);
	}
	//导出EXCLE
	public static String CateAccessCountsOutExcel(List listBean,List headerList){
		return CmsCountExcelOut.CateAccessCountsOutExcel(listBean, headerList);
	}
	
	//导出栏目访问量EXCLE
	public static String CateAccessOrderCountsOutExcel(List listBean,List headerList){
		return CmsCountExcelOut.CateAccessOrderCountsOutExcel(listBean, headerList);
	}
	
	//导出信息访问量EXCLE
	public static String InfoAccessOrderCountsOutExcel(List listBean,List headerList){
		return CmsCountExcelOut.InfoAccessOrderCountsOutExcel(listBean, headerList);
	}
	
	
	//统计单个站点下面的栏目信息访问量
	public static List<InfoAccessBean> getAccessInfoLists(Map mp){
		return AccessCountManager.getAccessInfoListsByCats(mp);
	}
	
	public static  InfoAccessBean getBeanByID(String info_id){
		return  AccessCountManager.getBeanByID(info_id);
	}
	
	
	
	//统计每个子栏目的信息访问量
	public static List<InfoAccessBean> getCatOrderListByCat_id(Map mp){
		return AccessCountManager.getCatOrderListByCat_id(mp);
	}
	
	//统计每条信息的访问量
	public static List<InfoAccessBean> getInfoOrderListByInfo_id(Map mp){
		return AccessCountManager.getInfoOrderListByInfo_id(mp);
	}
	public static boolean deleteAccessCountInfos(Map m){
		if(AccessCountManager.deleteAccessCountInfos(m))
		{
			return  true;
		}else{
			return false;
		}
	}
	
	 //昨日，今日，日均访问量
    public static Map<String,String> getDayAccessCountList(String site_id,String constant) 
  	{
    	return  AccessCountManager.getDayAccessCountList(site_id,constant);
  	}

    //统计每个栏目的信息访问量
    public static List<HitsCountBean>  getHitsByCat(Map mp){
        return AccessCountManager.getHitsByCat(mp);
    }

    //统计每条信息的访问量
    public static List<HitsCountBean>  getHitsByInfo(Map mp){
        return AccessCountManager.getHitsByInfo(mp);
    }
}