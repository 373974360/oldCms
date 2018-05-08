package com.deya.wcm.services.cms.count;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.count.CmsCountBean;
import com.deya.wcm.bean.cms.count.SiteCountBean;

public class SiteCountRPC {
	
	//得到部门添加信息统计
	public static List<SiteCountBean> getSiteCountListByInput(Map map){
		return SiteCountManager.getSiteCountListByInput(map);
	}
	
	//得到人员信息统计
	public static List<SiteCountBean> getSiteCountListByInputUser(Map mp){
		return SiteCountManager.getSiteCountListByInputUser(mp);
	}
	
	//按站点统计
	public static List<SiteCountBean> getSiteCountListBySite(Map mp){
		return SiteCountManager.getSiteCountListBySite(mp);
	}
	
	//按某一个站点下面的所有栏目统计
	public static List<SiteCountBean> getSiteCountListByCate(Map mp){
		return SiteCountManager.getSiteCountListByCate(mp);
	}
	
	/**
	 * 统计导出Excel文件 -- 按栏人员统计
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String siteInfoOutExcelByUser(List listBean,List headerList){
		return SiteCountExcelOut.siteInfoOutExcelByUser(listBean, headerList);
	} 
	
	/**
	 * 按部门统计
	 * @param List listBean
	 * @param Map map
	 * @return	String 文件路径名字
	 */
	public static String orgTreeInfoOutExcel(List headerList,Map mp){
		return SiteCountExcelOut.orgTreeInfoOutExcel(headerList, mp);
	}
	
    /**
	 * 按栏目统计
	 * @param List listBean
	 * @param Map map
	 * @return	String 文件路径名字
	 */
	public static String getInputCountListByCateOutExcel(List headerList,Map mp){
		return SiteCountExcelOut.getInputCountListByCateOutExcel(headerList, mp);
	}
	
}
