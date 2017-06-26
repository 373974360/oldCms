package com.deya.wcm.services.zwgk.info;

import com.deya.wcm.bean.cms.info.GKInfoBean;
import com.deya.wcm.bean.cms.info.GKResFileBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GKInfoRPC
{
	public static List<GKInfoBean> getGKInfoList(Map<String, String> m)
	{
		return GKInfoManager.getGKInfoList(m);
	}

	public static String getGKInfoCount(Map<String, String> m)
	{
		return GKInfoManager.getGKInfoCount(m);
	}

	public static String getInfoIdForGKIndex(String gk_index,String site_id)
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("gk_index",gk_index);
		map.put("site_id",site_id);
		return GKInfoManager.getInfoIdForGKIndex(map);
	}

	public static List<GKResFileBean> getGKResFileList(String info_id)
	{
		return GKInfoManager.getGKResFileList(info_id);
	}

	public static boolean reloadGKIndex(Map<String, String> m)
	{
		return GKInfoManager.reloadGKIndex(m);
	}
}
