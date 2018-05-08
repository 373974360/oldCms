package com.deya.wcm.services.cms.digg;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.digg.InfoDiggBean;

public class DiggRPC {
	
	/**
	 * 取得信息Digg列表
	 * @param mp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<InfoDiggBean> getInfoDiggList(Map mp)
	{
		return DiggManager.getInfoDiggList(mp);
	}
	
	/**
	 * 取得信息Digg列表条数
	 * @param mp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getInfoDiggCnt(Map mp)
	{
		return DiggManager.getInfoDiggListCnt(mp);
	}

}
