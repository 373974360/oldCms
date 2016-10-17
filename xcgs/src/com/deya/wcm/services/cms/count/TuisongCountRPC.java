package com.deya.wcm.services.cms.count;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.count.SiteInfoTuisongBean;
import com.deya.wcm.bean.cms.count.TuisongCountBean;
import com.deya.wcm.services.cms.count.TuisongCountManager;

public class TuisongCountRPC{
	
	/*
	 * 按站点栏目统计的推送信息
	 * @param mp
	 * @return List
	 * */
	public static List<TuisongCountBean> getTuisongInfoCountByCat(Map<String, String> mp){
		return TuisongCountManager.getTuisongInfoCountByCat(mp);
	}
	
	/*
	 * excle 导出
	 * @param list
	 * @return String
	 * */
	public static String tuiSongInfoOutExcel(List listBean,List headerList){
		return TuisongCountManager.tuiSongInfoOutExcel(listBean, headerList);
	}
	
	/*
	 * 统计其它站点推送到指定站点信息总数
	 * @param mp
	 * @return List
	 * */
	public static List<SiteInfoTuisongBean> getOneSiteTuisCounts(Map<String, String> mp){
		return TuisongCountManager.getOneSiteTuisCounts(mp);
	}
}