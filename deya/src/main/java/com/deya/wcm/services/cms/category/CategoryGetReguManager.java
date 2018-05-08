package com.deya.wcm.services.cms.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.category.CategoryGetRegu;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.cms.category.CategoryGetReguDAO;
import com.deya.wcm.services.zwgk.appcatalog.RegulationManager;
import com.deya.wcm.services.zwgk.node.GKNodeManager;

/**
 *  目录获取规则逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 目录获取规则逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class CategoryGetReguManager {
	public static int getNewRegulationID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_CATEGORY_REGU_TABLE_NAME);
	}
	
	/**
	 * 根据目录节点ID得到所有规则
	 * @param int cata_id
	 * @return List
	 */
	public static List<CategoryGetRegu> getCatagoryReguList(int cat_id,String site_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("cat_id", cat_id+"");
		m.put("site_id", site_id+"");
		List<CategoryGetRegu> l = CategoryGetReguDAO.getCategoryReguList(m);
		if(l != null && l.size() > 0)
		{
			for(CategoryGetRegu cgr : l)
			{
				if(cgr.getRegu_type() == 0)
				{
					cgr.setCat_id_names(CategoryManager.getMutilCategoryNames(cgr.getCat_ids(),""));
					if(cgr.getSite_ids() != null && !"".equals(cgr.getSite_ids()))
						cgr.setSite_id_names(GKNodeManager.getMutilNodeNames(cgr.getSite_ids()));
				}else
				{
					//节点的
					cgr.setSite_id_names(GKNodeManager.getNodeNameByNodeID(cgr.getSite_ids()));
					cgr.setCat_id_names(CategoryManager.getMutilCategoryNames(cgr.getCat_ids(),cgr.getSite_ids()));
				}
			}
		}
		return l;
	}
	
	/**
	 * 插入目录节点获取规则
	 * @param List<CategoryGetRegu> l
	 * @param int cat_id
	 * @param String site_id
	 * @return boolean
	 */
	public static String insertCategoryRegu(List<CategoryGetRegu> l,int cat_id,String site_id,String app_id)
	{
		String sql = "";
		//先删除了		
		if(CategoryGetReguDAO.deleteCategoryRegu(cat_id+"",site_id))
		{
			try{
				sql = insertReguHandl(l,cat_id,site_id,app_id);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return sql;
	}
	
	public static String insertReguHandl(List<CategoryGetRegu> l,int cat_id,String site_id,String app_id)
	{
		String sql_node = "";
		String sql_cat = "";
		String sql = "";
		try{			
			if(l != null && l.size() > 0)
			{
				for(CategoryGetRegu rgb : l)
				{
					rgb.setId(getNewRegulationID());
					rgb.setCat_id(cat_id);
					rgb.setSite_id(site_id);
					rgb.setApp_id(app_id);					
					
					if(CategoryGetReguDAO.insertCategoryRegu(rgb))
					{
						if(rgb.getRegu_type() == 1)
						{
							sql_node += "or "+RegulationManager.spellConSql(rgb.getCat_ids(),rgb.getSite_ids(),rgb.getRegu_type());
						}else
						{
							sql_cat += "or "+RegulationManager.spellConSql(rgb.getCat_ids(),rgb.getSite_ids(),rgb.getRegu_type());
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
			return sql;
		}catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}	
	}	
	
	public static void main(String args[])
	{
		System.out.println(" or cat_d".startsWith(" or"));
	}
}
