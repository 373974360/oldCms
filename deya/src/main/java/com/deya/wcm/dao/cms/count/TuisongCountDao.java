package com.deya.wcm.dao.cms.count;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.count.TuisongCountBean;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.db.DBManager;

public class TuisongCountDao{
	/**
	 * 根据栏目分类取得统计信息列表
	 * (枝节点的统计为空,因为枝节点下没有直接的数据)
	 * @param mp	统计参数
	 * @param lt 	子栏目cat_ids映射
	 * @return	统计信息map
	 */
	@SuppressWarnings("unchecked")
	public static TuisongCountBean getCountListByCat(Map<String, String> mp)
	{
		int Tuisong = 0;
		int not_Tuisong = 0;
		String site_id = mp.get("site_id");
		
		List<TuisongCountBean> InfoList = DBManager.queryFList("getInfoListsByCat_SiteId", mp);
		TuisongCountBean tsBean = new TuisongCountBean();
		
		for(int i=0;i<InfoList.size();i++)
		{
			InfoBean  infob  = null;
			tsBean = InfoList.get(i);
			int from_id = InfoList.get(i).getFrom_id();
			/*
			CategoryBean  cat = CategoryManager.getCategoryBeanCatID(from_id,site_id);//株洲
			if(cat == null){
				Tuisong ++;
			}else{
				not_Tuisong ++;
			}
			*/
			infob = InfoBaseManager.getInfoById(from_id+"",site_id);//2012-09-14
			if(infob == null){
				Tuisong ++;
			}else{
				not_Tuisong ++;
			}
			tsBean.setIs_host(not_Tuisong);
			tsBean.setIsNot_host(Tuisong);
			tsBean.setApp_id(InfoList.get(i).getApp_id());
			tsBean.setInfo_id(InfoList.get(i).getInfo_id());
			tsBean.setSite_id(InfoList.get(i).getSite_id());
		}
		return tsBean;
	}
}