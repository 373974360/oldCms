package com.deya.wcm.services.search.index;

//svn上面最新的文件
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.io.FileOperation;
import com.deya.wcm.dao.search.impl.InfoServiceUtil;
import com.deya.wcm.services.search.SearchForInterface;
import com.deya.wcm.services.search.SearchForManager;
import com.deya.wcm.services.search.index.impl.IndexBsznServiceImpl;
import com.deya.wcm.services.search.index.impl.IndexFileServiceImpl;
import com.deya.wcm.services.search.index.impl.IndexFwServiceImpl;
import com.deya.wcm.services.search.index.impl.IndexInfoServiceImpl;
import com.deya.wcm.services.search.index.impl.IndexLawServiceImpl;
import com.deya.wcm.services.search.index.impl.IndexLeaderServiceImpl;
import com.deya.wcm.services.search.index.impl.IndexOrgDServiceImpl;
import com.deya.wcm.services.search.index.impl.IndexOrgServiceImpl;
import com.deya.wcm.services.search.index.impl.IndexPicServiceImpl;
import com.deya.wcm.services.search.index.impl.IndexVideoServiceImpl;
import com.deya.wcm.services.search.index.impl.IndexXxgkServiceImpl;

/**
 * <p>搜索所需要的管理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 */  
public class IndexManager_nxszs {
    
	// 保存索引文件的地方
	static String indexDir = SearchForInterface.getIndexPathRoot();
	
	public static IndexService indexInfoService = new IndexInfoServiceImpl(); //一般信息类型的实现类
	public static IndexService indexPicService = new IndexPicServiceImpl(); //一般图文信息类型的实现类
	public static IndexService indexVideoService = new IndexVideoServiceImpl(); //一般视频信息类型的实现类
	public static IndexService indexXxgkService = new IndexXxgkServiceImpl(); //信息公开 一般信息类型的实现类
	public static IndexService indexFileService = new IndexFileServiceImpl(); //信息公开 法律法规文件类型的实现类
	public static IndexService indexLeaderService = new IndexLeaderServiceImpl(); //信息公开 领导成员类型的实现类
	public static IndexService indexOrgService = new IndexOrgServiceImpl(); //信息公开 下属机构类型的实现类
	public static IndexService indexOrgDService = new IndexOrgDServiceImpl(); //信息公 开机构概况 类型的实现类
	public static IndexService indexLawService = new IndexLawServiceImpl(); //信息公 开机构概况 类型的实现类
	public static IndexService indexBsznService = new IndexBsznServiceImpl(); //办事指南类型的实现类
	public static IndexService indexFwService = new IndexFwServiceImpl(); //服务的实现类
	
//	public static IndexService indexCustomService = new IndexCustomServiceImpl(); //自定义表单信息 -- 一般信息
//	public static IndexService indexCustomGkService = new IndexCustomGkServiceImpl(); 
	
	//IndexFwServiceImpl.java 要调用
	public static void createAllIndexByAppId(Map map){
		indexInfoService.appendALlDocument(map); 
		indexPicService.appendALlDocument(map); 
		indexVideoService.appendALlDocument(map);
		indexXxgkService.appendALlDocument(map);  
		indexFileService.appendALlDocument(map);
		indexLeaderService.appendALlDocument(map); 
		indexOrgService.appendALlDocument(map);
		indexOrgDService.appendALlDocument(map);
		indexLawService.appendALlDocument(map);
		indexBsznService.appendALlDocument(map);
//		indexCustomService.appendALlDocument(map);
//		indexCustomGkService.appendALlDocument(map);
	}
	
	//IndexFwServiceImpl.java 要调用 
	public static void appendSingleDocumentAppId(Map map){
		appendSingleDocumentAppIdInfo(map);
		appendSingleDocumentAppIdZwgk(map);
	}
	
	//一般信息展示类 
	public static void appendSingleDocumentAppIdInfo(Map map){
		if(indexInfoService.appendSingleDocument(map)){
			return;
		}
		if(indexPicService.appendSingleDocument(map)){
			return;
		}
		if(indexVideoService.appendSingleDocument(map)){
			return;
		}
//		if(indexCustomService.appendSingleDocument(map)){
//			return;
//		}
	}
	
	//政务公开类信息
	public static void appendSingleDocumentAppIdZwgk(Map map){
		if(indexXxgkService.appendSingleDocument(map)){
			return;
		}
		if(indexFileService.appendSingleDocument(map)){
			return;
		}
		if(indexLeaderService.appendSingleDocument(map)){
			return;
		}
		if(indexOrgService.appendSingleDocument(map)){
			return;
		}
		if(indexOrgDService.appendSingleDocument(map)){
			return;
		}
		if(indexLawService.appendSingleDocument(map)){
			return;
		}
		if(indexBsznService.appendSingleDocument(map)){
			return;
		}
//		if(indexCustomGkService.appendSingleDocument(map)){
//			return;
//		}
	}
	  
	//通过站点ID读该所有类型的信息到Index中
	public static void createAllIndexBySite(Map mapSite){
		indexInfoService.appendALlDocument(mapSite); 
		indexPicService.appendALlDocument(mapSite); 
		indexVideoService.appendALlDocument(mapSite);
//		indexCustomService.appendALlDocument(mapSite);
		//创建 公务公开 类类的信息索引  
		String site_id1 = (String)mapSite.get("site_id"); 
		
		if(site_id1!=null && !"".equals(site_id1)){
			//政务公开类信息
			String site_id2 = com.deya.wcm.services.control.site.SiteAppRele.getSiteIDByAppID("zwgk");
			if(site_id1.equals(site_id2)){
				Map map = new HashMap();  
				indexXxgkService.appendALlDocument(map);   // 政务公开通用格式    
				indexFileService.appendALlDocument(map);
				indexLeaderService.appendALlDocument(map); 
				indexOrgService.appendALlDocument(map);
				indexOrgDService.appendALlDocument(map);  
				indexLawService.appendALlDocument(map);
//				indexCustomGkService.appendALlDocument(map);
				indexBsznService.appendALlDocument(mapSite);
			} 
			
			//服务类信息
			String site_idFw = com.deya.wcm.services.control.site.SiteAppRele.getSiteIDByAppID("ggfw");
			if(site_id1.equals(site_idFw)){
				indexFwService.appendALlDocument(mapSite);
			}
		}
	}
	
	//通过信息Id删除索引
	public static void deleteSingleDocument(Map map){
		indexInfoService.deleteSingleDocument(map);
	}
	
	//通过信息Id添加索引
	public static void appendSingleDocument(Map map){
		IndexManager_nxszs.deleteSingleDocument(map);
		String type = com.deya.util.FormatUtil.formatNullString(InfoServiceUtil.getInfoTypeById(map));
		if(type.equals("cms")){
			appendSingleDocumentAppIdInfo(map);
		}else if(type.equals("zwgk")){ 
			appendSingleDocumentAppIdZwgk(map);
		}else if(type.equals("ggfw")){    
			indexFwService.appendSingleDocument(map);
		}
	}
	
	
	//创建所有的信息索引
	public static boolean initAndCreateIndex(boolean isInit){
		try{
			File file = new File(indexDir);
			if(!file.exists()){
				initIndexSearch(isInit); 
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	 //初始化IndexWriter 如果服务器上没有索引文件夹 调用该方法
	 public static void initIndexSearch(boolean isInit){
		 IndexLuceneManager.initIndex();
		 if(isInit){
			 readALLToIndex();
		 }
	 }
	
	 /**
     * 读所有的信息到Index中
     * @return void 
     * */ 
	 public static void readALLToIndex(){
			 List<Map> listSite = SearchForManager.getSiteList();
			 for(Map mapSite : listSite){
				 createAllIndexBySite(mapSite);
			 }
	 }
	 
	 
	 /**
     * 通过站点ID读该所有的信息到Index中
     * @param String site_id
     * @return boolean 
     * */ 
	 public static boolean readToIndexBySite(String site_id){
		 try{
	         Map mapSite = new HashMap();
	         mapSite.put("site_id", site_id);
	         createAllIndexBySite(mapSite);
	         return true;
		 }catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	 }
	 
	 
	 //判断索引文件夹是不是存在
	 public static boolean isIndexDirExists(){
		    File file = new File(indexDir);
			if(!file.exists()){
				return false;
			}
			return true;
	 }
	 
	//删除所有信息的索引 -- 也就是删掉索引文件夹
	public static boolean deleteIndexDir(){
		try{
			File file = new File(indexDir);
			if(!file.exists()){
				return true;
			}
			FileOperation.deleteDir(indexDir);
			return true;
		}catch (Exception e) { 
			e.printStackTrace();
			return false;
		}
	}     
	

	/**
     * 通过site_id删除该类信息的全部索引
     * @param String site_id
     * @return boolean
     */
	public static boolean deleteIndexBySite(String site_id) {
		Map map = new HashMap();
		map.put("site_id", site_id);
		return indexInfoService.deleteALlDocument(map);
	}
	
}
