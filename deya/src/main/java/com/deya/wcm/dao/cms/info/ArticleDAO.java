package com.deya.wcm.dao.cms.info;

import java.util.HashMap;
import java.util.Map;

import com.deya.wcm.bean.cms.info.ArticleBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 * @author 符江波
 * @version 1.0
 * @date   2011-6-14 下午04:29:47
 */
public class ArticleDAO {

	public static boolean addArticleBean(ArticleBean article, SettingLogsBean stl){
		if(InfoDAO.addInfo(article,stl))
			if(DBManager.insert("addArticle", article)){
				PublicTableDAO.insertSettingLogs("添加","主信息内容",article.getInfo_id()+"",stl);
				return true;
			}else
				return false;
		else
			return false;
	}
	
	public static boolean updateArticle(ArticleBean article, SettingLogsBean stl){
		if(InfoDAO.updateInfo(article,stl))
			if(DBManager.update("updateArticle", article)){
				PublicTableDAO.insertSettingLogs("修改","主信息内容",article.getInfo_id()+"",stl);
				return true;
			}else
				return false;
		else
			return false;
	}
	
	public static ArticleBean getArticle(String infoId, String siteId){
		Map<String, String> map = new HashMap<String, String>();
		map.put("site_id", siteId);
		map.put("info_id", infoId);
		return (ArticleBean)DBManager.queryFObj("getInfoArticleBean", map);
	}
	
	/**
	 * 根据栏目ID和站点ID得到文章对象，主要用于公开指南的数据获取
	 * @param String cat_id
	 * @param String site_id
	 * @return ArticleBean
	 */
	public static ArticleBean getArticleBeanForCatSiteID(String cat_id, String site_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("site_id", site_id);
		map.put("cat_id", cat_id);
		return (ArticleBean)DBManager.queryFObj("getArticleBeanForCatSiteID", map);
	}
	
	public static boolean deleteArticle(Map<String, String> map, SettingLogsBean stl){
		if(InfoDAO.deleteInfo(map,stl))
			if(DBManager.update("deleteArticle", map)){
				PublicTableDAO.insertSettingLogs("删除","彻底删除信息",map.get("info_id")+"",stl);
				return true;
			}else
				return false;
		else
			return false;
	}
	
	public static void main(String[] args) {
		System.out.println(getArticleBeanForCatSiteID("10","GKmzj"));
	}
}
