package com.deya.wcm.dao.cms.count;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.count.SiteCountBean;
import com.deya.wcm.db.DBManager;

public class SiteCountDAO {
	
	//得到人员添加信息统计
	public static List<SiteCountBean> getSiteCountListByInput(Map map){ 
		return (List<SiteCountBean>)DBManager.queryFList("site_count.getSiteCountListByInput", map);
	}
	
	//得到站点统计
	public static List<SiteCountBean> getSiteCountListBySite(Map map){ 
		return (List<SiteCountBean>)DBManager.queryFList("site_count.getSiteCountListBySite", map);
	}
	
	//按某一个站点下面的所有栏目统计
	public static List<SiteCountBean> getSiteCountListBySiteCate(Map map){ 
		return (List<SiteCountBean>)DBManager.queryFList("site_count.getSiteCountListBySiteCate", map);
	}
	
}
