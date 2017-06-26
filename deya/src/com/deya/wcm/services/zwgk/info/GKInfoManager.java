package com.deya.wcm.services.zwgk.info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.cms.info.GKFbsznBean;
import com.deya.wcm.bean.cms.info.GKInfoBean;
import com.deya.wcm.bean.cms.info.GKResFileBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.dao.cms.info.InfoDAO;
import com.deya.wcm.dao.zwgk.info.GKInfoDAO;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.zwgk.index.IndexManager;

public class GKInfoManager {
	/**
	 * 得到公开信息列表（前台使用）
	 * @param Map<String, String> m
	 * @return List
	 */
	public static List<GKInfoBean> getBroGKInfoList(Map<String, String> map)
	{
		int start_page = Integer.parseInt(map.get("current_page"));
		int page_size = Integer.parseInt(map.get("page_size"));
		map.put("start_num", ((start_page-1)*page_size)+"");
		map.put("page_size", page_size+"");
		return GKInfoDAO.getBroGKInfoList(map);
	}

	/**
	 * 得到公开办事指南表单信息列表（前台使用）
	 * @param Map<String, String> m
	 * @return List
	 */
	public static List<GKFbsznBean> getBroGKBSZNInfoList(Map<String, String> map)
	{
		int start_page = Integer.parseInt(map.get("current_page"));
		int page_size = Integer.parseInt(map.get("page_size"));
		map.put("start_num", ((start_page-1)*page_size)+"");
		map.put("page_size", page_size+"");
		return GKInfoDAO.getBroGKBSZNInfoList(map);
	}

	/**
	 * 根据共享目录得到公开信息总数（前台使用）
	 * @param Map<String, String> m
	 * @return String
	 */
	public static String getBroGKInfoCountForSharedCategory(Map<String, String> m)
	{
		return GKInfoDAO.getBroGKInfoCountForSharedCategory(m);
	}

	/**
	 * 根据共享目录得到公开信息列表（前台使用）
	 * @param Map<String, String> m
	 * @return List
	 */
	public static List<GKInfoBean> getBroGKInfoListForSharedCategory(Map<String, String> map)
	{
		int start_page = Integer.parseInt(map.get("current_page"));
		int page_size = Integer.parseInt(map.get("page_size"));
		map.put("start_num", ((start_page-1)*page_size)+"");
		map.put("page_size", page_size+"");
		return GKInfoDAO.getBroGKInfoListForSharedCategory(map);
	}

	/**
	 * 得到公开信息总数（前台使用）
	 * @return String
	 */
	public static String getBroGKInfoCount(Map<String, String> m)
	{
		return GKInfoDAO.getBroGKInfoCount(m);
	}

	public static GKInfoBean getGKInfoBean(String info_id)
	{
		return GKInfoDAO.getGKInfoBean(info_id);
	}

	/**
	 * 得到所有公开信息列表
	 * @return List
	 */
	public static List<GKInfoBean> getGKInfoList(Map<String, String> m)
	{
		InfoBaseManager.getSearchSQL(m);
		return GKInfoDAO.getGKInfoList(m);
	}

	/**
	 * 得到公开附件公用表
	 * @param String info_id
	 * @return boolean
	 */
	public static List<GKResFileBean> getGKResFileList(String info_id)
	{
		return GKInfoDAO.getGKResFileList(info_id);
	}

	/**
	 * 判断索引号是否已存在，返回info_id
	 * @param String
	 * @return String
	 */
	public static String getInfoIdForGKIndex(Map<String,String> map)
	{
		return GKInfoDAO.getInfoIdForGKIndex(map);
	}

	/**
	 * 得到所有公开信息总数
	 * @return String
	 */
	public static String getGKInfoCount(Map<String, String> m)
	{
		InfoBaseManager.getSearchSQL(m);
		return GKInfoDAO.getGKInfoCount(m);
	}

	/**
	 * 重新生成索引号
	 * @return String node_id
	 */
	public static boolean reloadGKIndex(Map<String, String> map)
	{
		String node_id = "";
		if(map.containsKey("site_id"))
			node_id = map.get("site_id");
		if(!GKInfoDAO.deleteGKIndexSequence(node_id))
		{
			return false;
		}
		List<GKInfoBean> l = GKInfoDAO.getAllGKInfoList(map);
		if(l != null && l.size() > 0)
		{
			for(GKInfoBean gkb : l)
			{
				try{
				int info_id = gkb.getInfo_id();
				int cat_id = gkb.getCat_id();
				String released_dtime = gkb.getReleased_dtime();
				String input_time = gkb.getInput_dtime();
				String site_id = gkb.getSite_id();
				String ymd = "";
				if(released_dtime != null && !"".equals(released_dtime))
				{
					ymd = released_dtime;
				}else
				{
					ymd = input_time;
				}

				Map<String, String> m = IndexManager.getIndex(site_id, cat_id+"", ymd, "");
				if(GKInfoDAO.updateGKIndex(info_id+"", m.get("gk_index"), m.get("gk_num")))
				{//得到该信息被引用 的
					List<InfoBean> il =  InfoDAO.getFromInfoListByIDS(info_id+"");
					if(il != null && il.size() > 0)
					{
						for(InfoBean ib : il)
						{
							GKInfoDAO.updateGKIndex(ib.getInfo_id()+"", m.get("gk_index"), m.get("gk_num"));
						}
					}
				}
				}catch(Exception e)
				{
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 得到gk_year内容，用于移动时重新生成索引号用
	 * @param String info_id
	 * @return boolean
	 */
	public static String getGKYearStr(String info_id)
	{
		return GKInfoDAO.getGKYearStr(info_id);
	}

	/**
	 * 按年度或季度统计公开的发布量
	 * @param String type  year,quarter
	 * @return String
	 */
	public static String getGKPublishStatistics(String type)
	{
		Map<String,String> m = new HashMap<String,String>();
		if("year".equals(type))
		{
			String y = DateUtil.getCurrentDateTime("yyyy");
			m.put("start_time", y+"-01-01 00:00:00");
			m.put("end_time", y+"-12-31 23:59:59");
		}
		if("quarter".equals(type))
		{
			String y = DateUtil.getCurrentDateTime("yyyy");
			String M = DateUtil.getCurrentDateTime("MM");
			//System.out.println(M);
			if("01,02,03".contains(M))
			{
				m.put("start_time", y+"-01-01 00:00:00");
				m.put("end_time", y+"-03-31 23:59:59");
			}
			if("04,05,06".contains(M))
			{//System.out.println("----------"+M);
				m.put("start_time", y+"-04-01 00:00:00");
				m.put("end_time", y+"-06-30 23:59:59");
			}
			if("07,08,09".contains(M))
			{
				m.put("start_time", y+"-07-01 00:00:00");
				m.put("end_time", y+"-09-30 23:59:59");
			}
			if("10,11,12".contains(M))
			{
				m.put("start_time", y+"-10-01 00:00:00");
				m.put("end_time", y+"-12-31 23:59:59");
			}
		}//System.out.println(m);
		return GKInfoDAO.getGKPublishStatistics(m);
	}

	public static void main(String[] args)
	{
		System.out.println(getGKPublishStatistics("quarter"));
	}
}
