package com.deya.wcm.services.zwgk.info;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.info.GKInfoBean;
import com.deya.wcm.bean.cms.info.GKResFileBean;

public class GKInfoRPC {
	/**
	 * 得到所有公开信息列表
	 * @return List
	 */
	public static List<GKInfoBean> getGKInfoList(Map<String, String> m)
	{
		return GKInfoManager.getGKInfoList(m);
	}
	
	/**
	 * 得到所有公开信息总数
	 * @return String
	 */
	public static String getGKInfoCount(Map<String, String> m)
	{
		return GKInfoManager.getGKInfoCount(m); 
	}
	
	/**
	 * 判断索引号是否已存在，返回info_id
	 * @param String
	 * @return String
	 */
	public static String getInfoIdForGKIndex(String gk_index)
	{
		return GKInfoManager.getInfoIdForGKIndex(gk_index); 
	}
	
	/**
	 * 得到公开附件公用表
	 * @param String info_id
	 * @return boolean
	 */	
	public static List<GKResFileBean> getGKResFileList(String info_id)
	{
		return GKInfoManager.getGKResFileList(info_id);
	}
	
	/**
	 * 重新生成索引号
	 * @return String node_id
	 */
	public static boolean reloadGKIndex(Map<String, String> m)
	{
		return GKInfoManager.reloadGKIndex(m);
	}
}
