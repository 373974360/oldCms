package com.deya.wcm.services.zwgk.appcatalog;

import java.util.List;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.appcatalog.RegulationBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.zwgk.appcatalog.AppCatalogDAO;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.zwgk.node.GKNodeManager;

/**
 *  公开应用目录汇聚规则逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 公开应用目录汇聚规则逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class RegulationManager {
	public static int getNewRegulationID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.GK_APPREGU_TABLE_NAME);
	}
	
	/**
	 * 根据目录节点ID得到所有规则
	 * @param int cata_id
	 * @return List
	 */
	public static List<RegulationBean> getAppCataReguList(int cata_id)
	{
		List<RegulationBean> l = AppCatalogDAO.getAppCataReguList(cata_id);
		if(l != null && l.size() > 0)
		{
			for(RegulationBean rgb : l)
			{
				if(rgb.getRegu_type() == 0)
				{
					rgb.setCat_id_names(CategoryManager.getMutilCategoryNames(rgb.getCat_ids(),""));
					if(rgb.getSite_ids() != null && !"".equals(rgb.getSite_ids()))
						rgb.setSite_id_names(GKNodeManager.getMutilNodeNames(rgb.getSite_ids()));
				}else
				{
					//节点的
					//rgb.setSite_id_names(GKNodeManager.getNodeNameByNodeID(rgb.getSite_ids()));
					rgb.setSite_id_names(SiteManager.getSiteBeanBySiteID(rgb.getSite_ids()).getSite_name());
					rgb.setCat_id_names(CategoryManager.getMutilCategoryNames(rgb.getCat_ids(),rgb.getSite_ids()));
					
				}
			}
		}
		return l;
	}
		
	/**
	 * 插入公开应用目录节点同步规则
	 * @param List<RegulationBean> l
	 * @param int cata_id
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertAppCateRegu(List<RegulationBean> l,String cata_id,SettingLogsBean stl)
	{
		String sql_node = "";
		String sql_cat = "";
		String sql = "";
		//先删除了
		if(AppCatalogDAO.deleteAppCateRegu(cata_id))
		{
			try{
				if(l != null && l.size() > 0)
				{
					for(RegulationBean rgb : l)
					{
						rgb.setId(getNewRegulationID());
						
						if(AppCatalogDAO.insertAppCateRegu(rgb))
						{
							if(rgb.getRegu_type() == 1)
							{
								sql_node += "or "+spellConSql(rgb.getCat_ids(),rgb.getSite_ids(),rgb.getRegu_type());
							}else
							{
								sql_cat += "or "+spellConSql(rgb.getCat_ids(),rgb.getSite_ids(),rgb.getRegu_type());
							}
						}
					}
					if(sql_node != null && !"".equals(sql_node))
						sql_node = sql_node.substring(2);
					if(sql_cat != null && !"".equals(sql_cat))
					{
						sql_cat = " ca.cat_id in (select cat_id from cs_info_category where "+sql_cat.substring(2)+")";						
					}
					sql = sql_node +" or "+sql_cat;
					if(sql.startsWith(" or"))
					{
						sql = sql.substring(3);
					}
					if(sql.endsWith("or "))
					{
						sql = sql.substring(0,sql.length()-3);
					}
					sql = "("+sql+")";
				}
				
				AppCatalogManager.updateGKAppCatelogSQL(sql,cata_id);
				PublicTableDAO.insertSettingLogs("移动","公开应用目录同步规则",cata_id,stl);
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}else
			return false;
	}
	
	/**
	 * 拼查询sql
	 * @param String cat_ids
	 * @param String site_ids
	 * @return String
	 */
	public static String spellConSql(String cat_ids,String site_ids,int regu_type)
	{
		String sql = "";
		
		if(regu_type == 1)
		{		
			site_ids = "'"+site_ids.replaceAll(",", "','")+"'";
			//以节点为主
			sql = "( ci.site_id in ("+site_ids+") and ci.cat_id in ("+cat_ids+") )";
		}
		else
		{
			//共享目录
			sql = "( cat_class_id in ("+cat_ids+")";
			if(site_ids != null && !"".equals(site_ids))
			{
				site_ids = "'"+site_ids.replaceAll(",", "','")+"'";
				sql += " and site_id in ("+site_ids+")";
			}	
			sql += " )";
		}
		return sql;
	}
}
