package com.deya.wcm.services.cms.info.article;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.cms.info.ArticleBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

/**
 * @author 符江波
 * @version 1.0
 * @date   2011-6-15 上午10:46:40
 */
public class ArticleRPC {

	public static boolean addArticle(ArticleBean article, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return ArticleManager.addArticle(article, stl);
		}else
			return false;		
	}
	
	public static boolean updateArticle(ArticleBean article, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return ArticleManager.updateArticle(article, stl);
		}else
			return false;		
	}
	
	public static ArticleBean getArticle(String infoId, String siteId){
		return ArticleManager.getArticle(infoId, siteId);
	}
	
	/**
	 * 根据栏目ID和站点ID得到文章对象，主要用于公开指南的数据获取
	 * @param String cat_id
	 * @param String site_id
	 * @return ArticleBean
	 */
	public static ArticleBean getArticleBeanForCatSiteID(String cat_id, String site_id){
		return ArticleManager.getArticleBeanForCatSiteID(cat_id, site_id);
	}
	
	public static boolean deleteArticle(Map<String, String> map, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return ArticleManager.deleteArticle(map, stl);
		}else
			return false;
	}
}
