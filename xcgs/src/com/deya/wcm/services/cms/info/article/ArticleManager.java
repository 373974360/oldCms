package com.deya.wcm.services.cms.info.article;

import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.cms.info.ArticleBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.cms.info.ArticleDAO;

/**
 * @author 符江波
 * @version 1.0
 * @date   2011-6-15 上午10:46:40
 */
public class ArticleManager {

	public static boolean addArticle(ArticleBean article, SettingLogsBean stl){
		int infid = article.getInfo_id();
		
		if(infid <= 0){
			int id = PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_TABLE_NAME);
			article.setId(id);
			article.setInfo_id(id);
		}
		if(article.getInfo_content() == null){
			article.setInfo_content("");
		}
		article.setInput_dtime(DateUtil.getCurrentDateTime());
		//article.setWord_count(article.getInfo_content().length());
		if(article.getInfo_status() == 8){
			article.setReleased_dtime(DateUtil.getCurrentDateTime());
		}
		return ArticleDAO.addArticleBean(article, stl);
	}
	
	public static boolean updateArticle(ArticleBean article, SettingLogsBean stl){
		article.setModify_dtime(DateUtil.getCurrentDateTime());
		article.setOpt_dtime(DateUtil.getCurrentDateTime());
		return ArticleDAO.updateArticle(article, stl);
	}
	
	public static ArticleBean getArticle(String infoId, String siteId){
		return ArticleDAO.getArticle(infoId, siteId);
	}
	
	/**
	 * 根据栏目ID和站点ID得到文章对象，主要用于公开指南的数据获取
	 * @param String cat_id
	 * @param String site_id
	 * @return ArticleBean
	 */
	public static ArticleBean getArticleBeanForCatSiteID(String cat_id, String site_id){
		return ArticleDAO.getArticleBeanForCatSiteID(cat_id, site_id);
	}
	
	public static boolean deleteArticle(Map<String, String> map, SettingLogsBean stl){
		return ArticleDAO.deleteArticle(map, stl);
	}
}
