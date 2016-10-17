package com.deya.wcm.services.search;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.control.site.SiteManager;


/**
 * <p>搜索所需要的外部接口类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 */  
public class SearchForInterface {

	
	private static String searchPath = JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path") + File.separator + "search";
	  
	/**
     * 得到所有站点信息列表
     * @param Map map sql所需要的参数
     * @return List
     * */
	public static String getSearchXmlPath(){
		
		//调用  得到search.xml配置文件路径  的方法
		String searchfile = new SearchForInterface().getClass().getClassLoader().getResource("search.xml").getFile();
		System.out.println(searchfile);
		return searchfile;
	}
	
	/**
     * 得到所有站点信息列表
     * @param 
     * @return List
     * */
	public static List<Map> getSiteList(){
		
		//调用  得到所有站点信息列表  的方法
		List list = new ArrayList<Map>();
		
		List<SiteBean> beans = SiteManager.getSiteList();
		for(SiteBean bean : beans){
		   // System.out.println("sssssssssssssss========" + bean.getParent_id());
			if(!bean.getParent_id().equals("0")){
				Map map = new HashMap();
				map.put("site_id",bean.getSite_id());
				map.put("site_name",bean.getSite_name());
				list.add(map);
			}
		}		
		return list; 
	}  
	
	
	/**
     * 得到索引存放目录
     * @param 
     * @return List
     * */
	public static String getIndexPathRoot(){
		return searchPath;
	}
	
	/**
     * 通过栏目id得到栏目名称
     * @param String cat_id
     * @return String
     * */
	public static String getCategoryNameById(String cat_id){
		CategoryBean categoryBean = CategoryManager.getCategoryBean(Integer.valueOf(cat_id));
		if(categoryBean==null){
			return "";
		}
		return categoryBean.getCat_cname();
	}
	
	
	/**
     * 通过站点id得域名
     * @param String site_id
     * @return String
     * */ 
	public static String getDomainBySiteId(String site_id){
		//return SiteManager.getSiteBeanBySiteID(site_id).getSite_domain();
		String url = com.deya.wcm.services.control.site.SiteRPC.getDefaultSiteDomainBySiteID(site_id);
		if(url.startsWith("http://")){
			url = url.substring(7);
		}
		return url;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = getSearchXmlPath();
		System.out.println(path);
	}

}
