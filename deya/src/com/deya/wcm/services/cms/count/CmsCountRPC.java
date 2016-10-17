package com.deya.wcm.services.cms.count;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.count.CmsCountBean;
import com.deya.wcm.bean.cms.count.SiteCountBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.dao.cms.count.CmsCountDAO;

public class CmsCountRPC {
	/**
	 * 按照栏目分类,或者日期取得站点统计信息
	 * @param mp
	 * @return	取得的统计信息列表
	 */
	public static List<CmsCountBean> getInfoCount(Map<String, String> mp){
		return CmsCountManager.getInfoCount(mp);
	}
	
	/**
	 * 按照用户分类,取得站点统计信息
	 * @param mp
	 * @return	取得的统计信息列表
	 */
	public static List<CmsCountBean> getInputCountList(Map<String, String> mp){
		return CmsCountManager.getInputCountList(mp);
	}
	
	/**
	 * 根据用户ID取得站点下的统计信息
	 * @param mp
	 * @return	取得的统计信息列表
	 */
	public static List<CmsCountBean> getInputCountListByUserID(Map<String, String> mp){
		return CmsCountManager.getInputCountListByUserID(mp);
	}
	
	/**
	 * 统计导出Excel文件处理类 -- 按栏目统计
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String cmsInfoOutExcel(List listBean,List headerList){
		return CmsCountExcelOut.cmsInfoOutExcel(listBean, headerList);
	}
	
	/**
	 * 统计导出Excel文件 -- 按人员统计
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String cmsWorkInfoOutExcel(List listBean,List headerList){
		return CmsCountExcelOut.cmsWorkInfoOutExcel(listBean, headerList);
	}
	
	/**
	 * 取得单个用户各个栏目下的工作量统计信息
	 * @param mp
	 * @return	用户工作量统计信息列表
	 */
	public static List<SiteCountBean> getInputCountListByUserIDCate(Map<String, String> mp){
		return CmsCountManager.getInputCountListByUserIDCate(mp);
	}  
	
	
	/**
	 * 取得所选栏目下的工作量统计信息
	 * @param mp
	 * @return	取得所选栏目下的工作量统计信息列表
	 */
	public static List<SiteCountBean> getInputCountListByCate(Map<String, String> mp){
		return CmsCountManager.getInputCountListByCate(mp);
	}
	
	/**
	 * 取得所选栏目下的信息更新情况
	 * @param mp
	 * @return	取得所选栏目下的信息更新列表
	 */
	public static List<SiteCountBean> getInfoUpdateListByCate(Map<String, String> mp){
		return CmsCountManager.getInfoUpdateListByCate(mp);
	}

	/**
	 * 统计导出Excel文件 -- 取得所选栏目下的信息更新情况
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String cmsUpdateInfoOutExcel(List listBean,List headerList){
		return CmsCountExcelOut.cmsUpdateInfoOutExcel(listBean, headerList);
	}
	
	/**
	 * 取用户工作量统计信息详细列表
	 * @param mp
	 * @return	用户工作量统计信息详细列表
	 */
	public static List<InfoBean> getInfoListByUserIDTimeType(Map<String, String> map)
	{
		return CmsCountManager.getInfoListByUserIDTimeType(map);
	}
	
	/**
	 * 按照作者分类,取得站点统计信息
	 * @param mp
	 * @return	取得的统计信息列表
	 */
	public static List<CmsCountBean> getAuthorCountList(Map<String, String> mp){
		return CmsCountManager.getAuthorCountList(mp);
	}
}
