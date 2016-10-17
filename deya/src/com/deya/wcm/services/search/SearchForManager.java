package com.deya.wcm.services.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.deya.util.io.FileOperation;
import com.deya.util.xml.XmlManager;
import com.deya.wcm.bean.search.SiteInfo;
import com.deya.wcm.services.control.domain.SiteDomainRPC;
import com.deya.wcm.services.search.index.IndexLuceneManagerResource;
import com.deya.wcm.services.search.index.IndexManager;
import com.deya.wcm.services.search.index.util.IndexUtil;

/**
 * <p>搜索所需要的管理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 */
public class SearchForManager {

	
	private static List<Map> listIndex = new ArrayList<Map>();
	
	static{
		SearchForManager.setSiteList();
	}
	
	public static void clearList(){
		listIndex.clear();
	}
	
	//创建所有的信息索引
	public static boolean initAndCreateIndex(){
		boolean result = IndexManager.initAndCreateIndex(true);
		IndexUtil.writeSiteToFile();
		return result;
	}
	
	//删除所有信息的索引 -- 也就是删掉索引文件夹
	public static boolean deleteIndexDir(){
		IndexUtil.clearList();
		return IndexManager.deleteIndexDir();
	}
	
	//创建该站点的信息索引 
	public static boolean createIndexBySite(String site_id){
		boolean result = IndexManager.initAndCreateIndex(false);
		if(result){ 
			deleteIndexBySite(site_id);
			IndexUtil.addSiteToFile(site_id);
			return IndexManager.readToIndexBySite(site_id);
		}else{
			return false;
		}
	}  
	
	
	/**
     * 通过site_id删除该类信息的全部索引
     * @param String site_id
     * @return boolean
     */
	public static boolean deleteIndexBySite(String site_id) {
		IndexUtil.delelteSiteToFile(site_id);
		return IndexManager.deleteIndexBySite(site_id);
	}
	
	/**
     * 从search.xml得到所有站点信息列表
     * @return List
     * */
	public static List<Map> getSiteList(){
		try{
			if(listIndex.size()==0){
				String searchFile = SearchForInterface.getSearchXmlPath();
				Document document = XmlManager.createDOM(searchFile);
				NodeList nodeList = XmlManager.queryNodeList(document, "search/sites/site");
				for(int i=0;i<nodeList.getLength();i++){
					Node node = nodeList.item(i);
				    String site_id = (String)XmlManager.queryNodeValue(node,"site_id");
				    String site_name = (String)XmlManager.queryNodeValue(node,"site_name");
				    String state = (String)XmlManager.queryNodeValue(node,"state");
//				    if(IndexManager.isIndexDirExists()){
//				    	state = "1";
//				    }else{    
//				    	state = "0";
//				    }
				    Map<String, String> map = new HashMap<String, String>();
				    map.put("site_id", site_id);
				    map.put("site_name", site_name);
				    map.put("state", state);
				    listIndex.add(map);
				}
			}
			return listIndex;
		}catch (Exception e) {
			e.printStackTrace();
			return listIndex;
		}
	} 
	
	/**
     * 从search.xml得到所有站点信息列表
     * @param Map map  start_num,page_size
     * @return List
     * */
	public static List<SiteInfo> getSiteListByMap(Map mapParam){
      	List<SiteInfo> listIndexResult = new ArrayList<SiteInfo>();
		try{
			
			List siteList = SearchForInterface.getSiteList();
			if(siteList.size()!=listIndex.size()){//站点有更新
				setSiteList();
			}
			clearList();
			if(listIndex.size()==0){
				String searchFile = SearchForInterface.getSearchXmlPath();
				Document document = XmlManager.createDOM(searchFile);
				NodeList nodeList = XmlManager.queryNodeList(document, "search/sites/site");
				for(int i=0;i<nodeList.getLength();i++){
					Node node = nodeList.item(i);
				    String site_id = (String)XmlManager.queryNodeValue(node,"site_id");
				    String site_name = (String)XmlManager.queryNodeValue(node,"site_name");
				    String state = (String)XmlManager.queryNodeValue(node,"state");
//				    if(IndexManager.isIndexDirExists()){
//				    	state = "1";
//				    }else{    
//				    	state = "0";
//				    }
				    state = "0";
				    Map<String, String> map = new HashMap<String, String>();
				    map.put("site_id", site_id);
				    map.put("site_name", site_name);
				    map.put("state", state);
				    listIndex.add(map);
				}
			}
			
			int start_num = mapParam.get("start_num")==null?0:Integer.valueOf(String.valueOf((Object)mapParam.get("start_num")));
			int page_size = mapParam.get("page_size")==null?15:Integer.valueOf(String.valueOf((Object)mapParam.get("page_size")));
//			System.out.println("start_num===========" + start_num);
//			System.out.println("page_size===========" + page_size);
//			System.out.println("listIndex.size()===========" + listIndex.size());
			for(int i=start_num;i<start_num+page_size&&i<listIndex.size();i++){
				Map map = listIndex.get(i);
				//System.out.println("map===" + map);
				SiteInfo siteInfo = new SiteInfo();
				siteInfo.setSite_id((String)map.get("site_id"));
				siteInfo.setSite_name((String)map.get("site_name"));
				if(IndexUtil.isHasIndex(siteInfo.getSite_id())){
					siteInfo.setState("1");     
				}else{
					siteInfo.setState("0"); 
				}
				
				siteInfo.setSite_domain(SiteDomainRPC.getSiteDomainBySiteID((String)map.get("site_id")));
				
				//System.out.println("state===========" + siteInfo.getState());
				//System.out.println("Site_name===========" + siteInfo.getSite_name());
				//System.out.println("Site_id===========" + siteInfo.getSite_id());
				//System.out.println("Site_domain===========" + siteInfo.getSite_domain());
				
				listIndexResult.add(siteInfo); 
			}
//			System.out.println("listIndexResult===========" + listIndexResult);
			return listIndexResult;
		}catch (Exception e) {
			e.printStackTrace();
			return listIndexResult;
		}
	} 
	
	/**
     * 从search.xml得到所有站点信息总数
     * @return int
     * */
	public static int getSiteListByMapCount(){
		try{
			return listIndex.size();
		}catch (Exception e) {
			e.printStackTrace();
			return listIndex.size();
		}
	} 
	
	/**
     * 把站点信息写到search.xml文件中  只调用一次
     * @return List
     * */
	public synchronized static boolean setSiteList(){
		List<Map> indexList = new ArrayList<Map>();
		try{
			String searchFile = SearchForInterface.getSearchXmlPath();
			Document document = XmlManager.createDOM(searchFile);
			NodeList nodeList = XmlManager.queryNodeList(document, "search/sites/site");
			for(int i=0;i<nodeList.getLength();i++){
				Node node = nodeList.item(i);
			    String site_id = (String)XmlManager.queryNodeValue(node,"site_id");
			    String site_name = (String)XmlManager.queryNodeValue(node,"site_name");
			    String state = (String)XmlManager.queryNodeValue(node,"state");
			    Map<String, String> map = new HashMap<String, String>();
			    map.put("site_id", site_id);
			    map.put("site_name", site_name);
			    map.put("state", state);
			    indexList.add(map); 
			}
			
			List<Map> siteList = SearchForInterface.getSiteList();
			//System.out.println(siteList+" ==== "+ indexList);
			if(siteList.size()>indexList.size()){//站点有更新(有新站点)
				 String site_id = "";
				 boolean result = true;
				 for(Map mapSite : siteList){
					  site_id = (String)mapSite.get("site_id");
					  String site_idIndex = "";
					  result = true;
					  for(Map mapIndex : indexList){
						  site_idIndex = (String)mapIndex.get("site_id");
						  //System.out.println(site_id+" ==== "+ site_idIndex);
						  if(site_id.equals(site_idIndex)){
							  result = false;
						  }
					  }
					  //if result = true 就是新加的站点 写进文件
					  if(result){ 
						  String siteName = (String)mapSite.get("site_name");
						  Node parentnode = XmlManager.queryNode(document, "search/sites");
						  String siteXml = "<site><site_id>"+site_id+"</site_id><site_name>"+siteName+"</site_name><state>0</state></site>";
						  XmlManager.insertNode(parentnode, siteXml);
						  FileOperation.writeStringToFile(searchFile, XmlManager.node2String(document), false,"utf-8");
					  }
					  
				 }
			}
			
			if(siteList.size()<indexList.size()){//站点有更新(站点有删除)
				 String site_idIndex = "";
				 boolean result = true;
				 for(Map mapIndex : indexList){
					  site_idIndex = (String)mapIndex.get("site_id");
					  String site_idSite = "";
					  result = true;
					  for(Map mapSite : siteList){
						  site_idSite = (String)mapSite.get("site_id");
						  //System.out.println(site_id+" ==== "+ site_idIndex);
						  if(site_idIndex.equals(site_idSite)){
							  result = false;
						  }
					  }
					  //if result = true 就要把删除的站点 从文件中删除
					  if(result){ 
						  for(int i=0;i<nodeList.getLength();i++){
								Node node = nodeList.item(i);
							    String site_id = (String)XmlManager.queryNodeValue(node,"site_id");
							    if(site_idIndex.equals(site_id)){
							    	XmlManager.removeNode(node);
							    }
							}
						  FileOperation.writeStringToFile(searchFile, XmlManager.node2String(document), false,"utf-8");
					  }
					  
				 }
			}
			
			getSiteList();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	} 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//getSiteList();
		setSiteList();
	}

}
