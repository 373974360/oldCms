package com.deya.wcm.services.cms.countsource;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.count.CmsCountBean;

public class CmsCountSourceRPC {

	//按照栏目统计站点的发布信息  -  按信息来源分类
	public static List<CmsCountBean> getInfoCountListBySource(Map<String, String> mp){
		return CmsCountSourceService.getInfoCountListBySource(mp);
	}

	
	/**
	 * 统计导出Excel文件 -- 按照栏目统计站点的发布信息  -  按信息来源分类
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String cmsInfoOutExcel(List listBean,List headerList){
		return CmsCountSourceService.cmsInfoOutExcel(listBean, headerList);
	}
	
}
