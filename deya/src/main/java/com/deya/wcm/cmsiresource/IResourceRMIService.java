package com.deya.wcm.cmsiresource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cicro.iresource.service.resourceService.dto.PropertyFilterExtDTO;
import com.cicro.iresource.service.resourceService.dto.SearchInfoDTO;
import com.cicro.iresource.service.resourceService.dto.TreeDTO;
import com.cicro.iresource.service.resourceService.rmi.ResourceRmiI;
import com.deya.util.jconfig.JconfigUtilContainer;

public class IResourceRMIService {
 
	//public static final String urlRmi = "rmi://192.168.12.26:5000/ResourceService";
	//public static final String urlFile = "http://192.168.12.26:8080/iresource-center/sys/file/download/";
	public static final String urlRmi = JconfigUtilContainer.bashConfig().getProperty("urlRmi", "", "wcm_zyk");
	public static final String urlFile = JconfigUtilContainer.bashConfig().getProperty("urlFile", "", "wcm_zyk");
		
	//得到信息列表
	//String pageNo 当前页数
	//String pageSize 页面条数
	//String orderBy= ""; //排序字段
	//String orderByDir= ""; //排序方式 升序降序
	//String desFields="";
	//String treeId="";//栏目id
	//String decEnName = ""; //元数据集  
	//要搜索的字段 String fields = "";
	//要搜索的关键字 String keys = ""; 
	
    public static Map getIResourceInfoList(Map map){
    	List<Map> mapLsitSearch = new ArrayList<Map>();
    	Map resultMap = new HashMap();
    	List<Map> mapList = new ArrayList<Map>();
    	Context namingContext = null; 
    	try{
    		String pageNo = getMapString(map,"pageNo");
        	String pageSize = getMapString(map,"pageSize");
        	String orderBy = getMapString(map,"orderBy");
        	String orderByDir = getMapString(map,"orderByDir");
        	String desFields = getMapString(map,"desFields");
        	String treeId = getMapString(map,"treeId");
        	String decEnName = getMapString(map,"decEnName");
        	String fields = getMapString(map,"fields");
        	String keys = getMapString(map,"keys");
        	
        	String dept_id = getMapString(map,"dept_id");
        	String deptTreePath = getMapString(map,"deptTreePath");
        	
        	namingContext = new InitialContext();
        	//System.out.println("urlRmi : " + urlRmi);
			ResourceRmiI rmi = (ResourceRmiI) namingContext.lookup(urlRmi);
			
        	SearchInfoDTO searchInfoDTO = new SearchInfoDTO();
			if(!"".equals(treeId)){
				searchInfoDTO.setTreeId(treeId);  
			}
        	if(!"".equals(decEnName)){
        		searchInfoDTO.setDecEnName(decEnName); 
        	}
        	if(!"".equals(pageNo)){
        		searchInfoDTO.setPageNo(Integer.parseInt(pageNo));
        	}
        	if(!"".equals(pageSize)){
        		searchInfoDTO.setPageSize(Integer.parseInt(pageSize));
        	}
        	if(!"".equals(orderBy)){ 
        		searchInfoDTO.setOrderBy(orderBy);
        	}
        	if(!"".equals(orderByDir)){
        		searchInfoDTO.setOrderByDir(orderByDir);
        	}
        	List<PropertyFilterExtDTO> propertyFilterExts = new ArrayList<PropertyFilterExtDTO>();
        	if(!"".equals(fields) && !"".equals(keys)){
        		String[] strs = fields.split(",");
        		String str = "LIKES_";
        		for(String s : strs){
        			if(s!=null && !"".equals(s)){
        				str += "dec"+s + "_OR_";
        				//str += ""+s + "_OR_";
        			}
        		}
        		if(str.length()>5 && str.endsWith("_OR_")){
        			str = str.substring(0,str.length()-4);
        		}
        		//System.out.println(str);
        		//System.out.println(keys);
        		PropertyFilterExtDTO ext = new PropertyFilterExtDTO(str,keys);
        		propertyFilterExts.add(ext);
        		
        		Map<String,String> mapSearch = new HashMap<String,String>();
        		mapSearch.put("filterName", str);
        		mapSearch.put("value", keys);
        		mapLsitSearch.add(mapSearch);
        	}
        	
        	if(!"".equals(dept_id)){
        		String str = "EQS_deptId";
        		PropertyFilterExtDTO ext = new PropertyFilterExtDTO(str,dept_id);
        		propertyFilterExts.add(ext);
        		
        		Map<String,String> mapSearch = new HashMap<String,String>();
        		mapSearch.put("filterName", str);
        		mapSearch.put("value", dept_id);
        		mapLsitSearch.add(mapSearch);
        	}
        	if(!"".equals(deptTreePath)){
        		String str = "LIKES_deptTreePath";
        		PropertyFilterExtDTO ext = new PropertyFilterExtDTO(str,deptTreePath);
        		propertyFilterExts.add(ext);
        		
        		Map<String,String> mapSearch = new HashMap<String,String>();
        		mapSearch.put("filterName", str);
        		mapSearch.put("value", deptTreePath);
        		mapLsitSearch.add(mapSearch);
        	}
        	
			searchInfoDTO.setDeEnNames(desFields);
			
 //    		String info = rmi.findCustomformData(searchInfoDTO,propertyFilterExts);
			
			//为新疆兵团的项目改 为String的调用  --- 开始
			String searchInfo = JSONObject.fromObject(searchInfoDTO).toString();
			String sbE = JSONArray.fromObject(mapLsitSearch).toString();
			//System.out.println(searchInfo);
			//System.out.println(sbE);
			String info = rmi.findCustomformDataByJsonFilter(searchInfo,sbE); 
			//为新疆兵团的项目改 为String的调用  --- 结束
			
			JSONObject joAll = JSONObject.fromObject(info); 
	        Iterator itAll = joAll.keys();
	        Map mapAll = new HashMap();
	        while(itAll.hasNext()){
	       	 String key = itAll.next().toString();
	       	 String value = joAll.getString(key);
	       	 mapAll.put(key, value);
	        }
	        //totalItems是总条数
	        String totalItems = (String)mapAll.get("totalItems"); //totalItems是总条数
	        //System.out.println(mapAll);
	        String item = (String)mapAll.get("item");
			//System.out.println(item);
			JSONArray joitem = JSONArray.fromObject(item); 
	        Iterator ititem = joitem.iterator();
	        //信息dataList
//	        List<Map> listData = new ArrayList<Map>();           //信息dataList
	        while(ititem.hasNext()){
	        	JSONObject jsonObject = (JSONObject)ititem.next();
	        	Iterator itField = jsonObject.keys();
	            Map mapField  = new HashMap();
	            while(itField.hasNext()){
	          	    String key = itField.next().toString();
	          	    String value = jsonObject.getString(key);
	          	    if(key.equals("file")){
	          	    	//解析附件---------------------------------------开始
	          	    	String jsFile = value;
	          	    	JSONObject joFile = JSONObject.fromObject(jsFile); 
	        	        Iterator itFile = joFile.keys();
	        	        List<Map> listMapFiles = new ArrayList<Map>();
	        	        while(itFile.hasNext()){
	        		       	 String key2 = itFile.next().toString();
	        		       	 String jsonFile = joFile.getString(key2);
	        		       	 JSONArray jsonFileAy = JSONArray.fromObject(jsonFile);
	        		       	 Iterator itjsonFile = jsonFileAy.iterator();
	        		       	 while(itjsonFile.hasNext()){
	        		       		  JSONObject jsonObject2 = (JSONObject)itjsonFile.next();
	        		       		  Iterator itField2 = jsonObject2.keys();
	        		       		  Map mapField2  = new HashMap();
	        		       		  while(itField2.hasNext()){
	        	              	    String key_1 = itField2.next().toString();
	        	              	    String value_1 = jsonObject2.getString(key_1);
	        	                  	mapField2.put(key_1, value_1);
	        	                 }
	        		       		listMapFiles.add(mapField2);
	        		       		//System.out.println(mapField2); 
	        		       	 }
	        	        }
	        	        //解析附件---------------------------------------结束
	        	        for(Map mapField2 : listMapFiles){
	        	        	if(mapField2.containsKey("fileSufix")){
	        	        		String fileSufix = (String)mapField2.get("fileSufix");
	        	        		//System.out.println(fileSufix);
	        	        		String id = (String)mapField2.get("id");
	        	        		if(fileSufix.equals("epub")){
	        	        			mapField.put("epubFile", urlFile+id);
	        	        		}else if(fileSufix.equals("jpg") || fileSufix.equals("JPG") || fileSufix.equals("JPEG")){
	        	        			mapField.put("jpgFile", urlFile+id);
	        	        		}else if(fileSufix.equals("pdf") || fileSufix.equals("PDF")){
	        	        			mapField.put("pdfFile", urlFile+id);
	        	        		}else if(fileSufix.equals("flv") || fileSufix.equals("FLV")){
	        	        			mapField.put("flvFile", urlFile+id);
	        	        		}
	        	        	}else if(mapField2.containsKey("fileName")){
	        	        		String fileName = (String)mapField2.get("fileName");
	        	        		String id = (String)mapField2.get("id");
	        	        		if(fileName.endsWith(".epub")){
	        	        			mapField.put("epubFile", urlFile+id);
	        	        		}else if(fileName.endsWith(".jpg") || fileName.endsWith(".JPG") || fileName.endsWith(".JPEG")){
	        	        			mapField.put("jpgFile", urlFile+id);
	        	        		}else if(fileName.endsWith(".pdf") || fileName.endsWith(".PDF")){
	        	        			mapField.put("pdfFile", urlFile+id);
	        	        		}else if(fileName.endsWith(".flv") || fileName.endsWith(".FLV")){
	        	        			mapField.put("flvFile", urlFile+id);
	        	        		}else if(fileName.endsWith(".flv") || fileName.endsWith(".FLV")){
	        	        			mapField.put("flvFile", urlFile+id);
	        	        		}
	        	        	}
	        	        } 
	          	    }else{
	          	    	mapField.put(key, value);
	          	    }
	           }
	           //System.out.println(mapField);
	            mapList.add(mapField);
	        }
			//信息列表
	        if(mapList==null){
	        	mapList = new ArrayList<Map>();
	        }
	        resultMap.put("list", mapList);
	        
	        //总条数
	        if(totalItems==null){
	        	totalItems = "";
	        }
	        resultMap.put("count", ""+totalItems);
	        
	        //当前第几页
	        if(pageNo==null){
	        	pageNo = "0";
	        }
	        resultMap.put("cur_page", ""+pageNo);
	        
	        //共多少页
	        int page_size = Integer.parseInt(pageSize);//页面大小
	        int page_count = Integer.parseInt(totalItems)/page_size+1;
	        if(""+page_count=="null" || ""+page_count==null){
	        	page_count = 0;
	        }
	        if(Integer.parseInt(totalItems)==0){
	        	page_count = 0;
	        }
	        resultMap.put("page_count", ""+page_count);
	        
	        //上一页
	        int prev_num = 1;
	        if(Integer.parseInt(pageNo)>1){
	        	prev_num = Integer.parseInt(pageNo) - 1;
	        }
	        
	        resultMap.put("prev_num", ""+prev_num);
	        //下一页
	        int next_num = page_count;
	        //System.out.println(pageNo+" -- " + page_count);
	        if(Integer.parseInt(pageNo)<page_count){
	        	next_num = Integer.parseInt(pageNo) + 1;
	        	//System.out.println("-------------------------");
	        }
	        if(next_num==0){
	        	next_num = 1;
	        }
	        resultMap.put("next_num", ""+next_num);
	        
	        //末级页面
	        resultMap.put("page_count", ""+page_count);
        	return resultMap;
    	}catch (Exception e) {
			e.printStackTrace();
			return resultMap;
		}
    	
    }
	
    
    public static String getMapString(Map map,String key){
    	String value = "";
    	try{
    		value = (String)map.get(key);
    		if(value==null){
    			value = "";
    		}
    		return value;
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		return value;
		}
    }
    
    
    //通过父ID得到栏目 -- 农家书屋  栏目中书籍数量  统计用
    public static List<TreeDTO> findTreeByPIdAndType(String treeId){
    	List<TreeDTO> list = new ArrayList<TreeDTO>(); 
    	Context namingContext = null; 
    	try{
    		 
    		namingContext = new InitialContext();
        	//System.out.println("urlRmi : " + urlRmi);
			ResourceRmiI rmi = (ResourceRmiI) namingContext.lookup(urlRmi);
			list = rmi.findTreeByPIdAndType(treeId,1,null);
    		return list;
    	 }catch (Exception e) {
    		 e.printStackTrace();
    		 return list;
		}
    }
    
    //通过父ID得到 下级栏目统计 -- 农家书屋  栏目中书籍数量  统计用
    public static Map<String,String> totalByGroupTreeId(String treeId){
    	Map<String,String> map = new HashMap<String,String>(); 
    	Context namingContext = null; 
    	try{
    		SearchInfoDTO searchInfoDTO = new SearchInfoDTO();
    		List<PropertyFilterExtDTO> propertyFilterExts = new ArrayList<PropertyFilterExtDTO>();
    		searchInfoDTO.setFilters(propertyFilterExts);
    		namingContext = new InitialContext(); 
        	//System.out.println("urlRmi : " + urlRmi);
			ResourceRmiI rmi = (ResourceRmiI) namingContext.lookup(urlRmi);
			map = rmi.totalByGroupTreeId(treeId,searchInfoDTO);
			
    		return map;
    	 }catch (Exception e) {
    		 e.printStackTrace();
    		 return map;
		}
    }
    
    
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         
	}

}
