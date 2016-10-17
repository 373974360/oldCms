package com.deya.wcm.services.search.index.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.deya.util.io.FileOperation;
import com.deya.wcm.services.search.SearchForInterface;
import com.deya.wcm.services.search.index.IndexManager;

public class IndexUtil {

	// 保存站点信息的文件site.txt
	static String indexFile = SearchForInterface.getIndexPathRoot()+File.separator+"site.txt";
    static List listSite = new ArrayList();
    
    static{
    	reloadAll();
    }
    
    //得到所有已经创建索引的信息
    public static void reloadAll(){
    	try {
    		if(new File(indexFile).exists()){
    			String strSite = FileOperation.readFileToString(new File(indexFile));
    			String site[] = strSite.split(",");
    			List arrayList = Arrays.asList(site);
    			listSite = new ArrayList(arrayList);
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    //清空list
    public static void clearList(){
    	listSite.clear();
    }
	
    //把所有的站点写进配置文件
    public synchronized static void writeSiteToFile(){
    	try {
    		StringBuffer sb = new StringBuffer();
    		List<Map> siteList = SearchForInterface.getSiteList();
    		for(int i=0;i<siteList.size();i++){
    			Map map = siteList.get(i);
    			String site_id = (String)map.get("site_id");
    			if(i==siteList.size()-1){
    				sb.append(site_id);
    			}else{ 
    				sb.append(site_id+",");
    			}
    		}
    		//System.out.println("indexFile==="+indexFile);
    		System.out.println("sb.toString()==="+sb.toString());
    		if(IndexManager.isIndexDirExists()){
    			FileOperation.writeStringToFile(new File(indexFile), sb.toString(), false);
    		}
    		reloadAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    //在文件中删除一个站点id
    public synchronized static void delelteSiteToFile(String site_id){
    	try{
    		if(listSite.contains(site_id)){
    			listSite.remove(site_id); 
        		StringBuffer sb = new StringBuffer();
        		String str = readListString(); 
        		sb.append(str);
        		if(IndexManager.isIndexDirExists()){
        			FileOperation.writeStringToFile(new File(indexFile), sb.toString(), false);
        		}
        	}
    		reloadAll();
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    //添加一个站点id到文件中
    public synchronized static void addSiteToFile(String site_id){
    	try{
    		if(!listSite.contains(site_id)){
        		StringBuffer sb = new StringBuffer();
        		String str = readListString();
        		sb.append(str);
        		if(str.equals("")){
        			sb.append(site_id);
        		}else{
        			sb.append(","+site_id);
        		}
        		if(IndexManager.isIndexDirExists()){
        			FileOperation.writeStringToFile(new File(indexFile), sb.toString(), false);
        		}
        	}
    		reloadAll();
    	}catch (Exception e) {
			e.printStackTrace();
		}

    } 
    
	//判断该站点是否已经创建索引
	public static boolean isHasIndex(String site_id){
		if(!IndexManager.isIndexDirExists()){
			return false;
		}else{
			if(listSite.contains(site_id)){
				return true;
			}else{
				return false;
			}
		}
	}
	
	
	//读list为string
	public static String readListString(){
		StringBuffer sb = new StringBuffer();
		if(listSite.size()==0){
			return "";
		}
		for(int i=0;i<listSite.size();i++){
			if(i==listSite.size()-1){
				sb.append((String)listSite.get(i));
			}else{
				sb.append((String)listSite.get(i)+",");
			}
		}
		return sb.toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] strArray = new String[6];   
        for(int i = 0; i < 4; ){   
            strArray[i] = String.valueOf(i++);   
        }   
        List<String> testList = Arrays.asList(strArray);   
        testList.add(String.valueOf(10));   
        testList.add(String.valueOf(11));   
        System.out.println(testList); 
	}

}
