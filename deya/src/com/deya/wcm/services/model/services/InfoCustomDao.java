package com.deya.wcm.services.model.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.deya.wcm.bean.cms.info.ArticleBean;
import com.deya.wcm.bean.system.formodel.ModelBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.db.IbatisSessionFactory;
import com.deya.wcm.services.model.Fields;
import com.deya.wcm.services.system.formodel.ModelManager;


/**
 * 自定义数据操作处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 自定义数据操作处理类</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class InfoCustomDao {

	//通过信息id得到cs_info主信息
	public static ArticleBean getArticle(String infoId){
		Map<String, String> map = new HashMap<String, String>();
		map.put("info_id", infoId);
		return (ArticleBean)DBManager.queryFObj("model.custoninfo.getInfoZBean", map);
	}
	
	//执行sql得到Map
	public static Map getMapBySql(String sql){
		Map map = new HashMap();
		try{
			SqlSession s = IbatisSessionFactory.getInstance().openSession();
			map.put("sql", sql);
			//List<Map> infoList = s.selectList(DBManager.getSqlNameByDataType("search.getInfoListBySiteIdPages"),mapSite);
			return MapManager.mapKeyToLowValueToString((Map)s.selectOne(DBManager.getSqlNameByDataType("model.custoninfo.getMapBySql"),map));
			//return MapManager.mapKeyToLowValueToString((Map)DBManager.queryFObj("model.custoninfo.getMapBySql",map));
		}catch (Exception e) {   
			e.printStackTrace();
			return map;
		}
		
	}
	
}
