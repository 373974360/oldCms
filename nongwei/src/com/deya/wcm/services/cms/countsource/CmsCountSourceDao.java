package com.deya.wcm.services.cms.countsource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deya.util.MapManager;
import com.deya.wcm.bean.cms.count.CmsCountBean;
import com.deya.wcm.db.DBManager;

public class CmsCountSourceDao {

	//按照栏目统计站点的发布信息  -  按信息来源分类
	public static List<CmsCountBean> getInfoCountListBySource(Map<String, String> mp){
		List<CmsCountBean> list = new ArrayList<CmsCountBean>();
		try{
			System.out.println("CmsCountSourceDao---getInfoCountListBySource---map--" + mp);
			List<Map> result = DBManager.queryFList("sqlmap.cms.infocount_source.getInfoCountListBySource", mp);	
			// 站点统计信息的map
			for(Map bean : result){
				CmsCountBean cntBean = new CmsCountBean();
				bean = MapManager.mapKeyValueToLow(bean);
				// 从SQL结果中提取取得的字段
				String cat_name = (String)bean.get("source");
				if(cat_name==null || "".equals(cat_name)){
					cat_name = "未知来源";
				}
				String count = bean.get("count").toString();
				
				cntBean.setCat_name(cat_name);
				cntBean.setCount(Integer.valueOf(count));
				
				list.add(cntBean);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
