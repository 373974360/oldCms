package com.deya.wcm.services.zwgk.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.deya.wcm.bean.zwgk.export.ExportDept;
import com.deya.wcm.bean.zwgk.export.ExportInfo;
import com.deya.wcm.dao.zwgk.export.ExportInfoDao;
import com.deya.wcm.services.cms.category.CategoryRPC;

/**
 * 政务公开目录导出处理类
 * <p>Title: ExportInfoService</p>
 * <p>Description: 政务公开目录导出处理类</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class ExportInfoService {
    
	
	//导出政务公开目录   条件：一个公开节点下面的某些栏目
	public static List<ExportDept> exportZwgkInfoByNodeCate(Map map){
		List<ExportDept> listResult = new ArrayList<ExportDept>();
		try{
			//System.out.println(map);
			String node_id = (String)map.get("node_id");
			String cat_ids = (String)map.get("cat_ids");
			List<String> fileSuffixList = Arrays.asList(cat_ids.split(","));
			String start_day = (String)map.get("start_day");
			String end_day = (String)map.get("end_day");
			String titlename = (String)map.get("titlename");
			String extype = (String)map.get("extype");
			
			//得到该节点下面的公开栏目
			String treeStr = "";
			String infoType = (String)map.get("infoType");
			if(infoType.equals("zwgk")){//政务公开信息
				treeStr = CategoryRPC.getCategoryTreeBySiteID(node_id);
			}else{//共享信息
				treeStr = CategoryRPC.getCategoryTreeByClassID(Integer.valueOf(infoType));
			}	
			//String treeStr = (String)map.get("extype");
			//System.out.println("treeStr==" + treeStr);
			JSONArray jsonArrayAll = JSONArray.fromObject(treeStr);  
	        Iterator itAll = jsonArrayAll.iterator();
	        JSONObject jsonObjectAll = (JSONObject)itAll.next();
			JSONArray jsonArray = JSONArray.fromObject(jsonObjectAll.get("children"));
			Iterator it = jsonArray.iterator();
//			Iterator it = jsonArrayAll.iterator();
	        while(it.hasNext()){
	        	JSONObject jsonObject = (JSONObject)it.next();
	        	ExportDept exportDept = doOutTreeBean(jsonObject,listResult,fileSuffixList,map);
	        }
	        //把栏目的排序调整正确
	        Map<String,ExportDept> mapResult = new HashMap<String, ExportDept>();
			List<ExportDept> listResult2 = new ArrayList<ExportDept>();
			for(ExportDept exportDept : listResult){
	    		mapResult.put(exportDept.getCateId(), exportDept);
	    	}
	        
	        Iterator it2 = jsonArray.iterator();
//			Iterator it2 = jsonArrayAll.iterator();
	        while(it2.hasNext()){
	        	JSONObject jsonObject = (JSONObject)it2.next();
	        	ExportDept exportDept = doOutTreeBean2(jsonObject,listResult2,mapResult);
	        }
	        
			return listResult2;

		}catch (Exception e) {
			e.printStackTrace();
			return listResult;
		}
	}
	
	
	//exportZwgkInfoByNodeCate  -- 调用的递归方法用
	public static ExportDept doOutTreeBean(JSONObject jsonObject,List<ExportDept> listResult,List<String> fileSuffixList,Map map){
		ExportDept exportDept = new ExportDept();
		try{
			int cat_id = Integer.valueOf(String.valueOf(jsonObject.get("id")));
			exportDept.setCateId(String.valueOf(cat_id));
			if(fileSuffixList.contains(String.valueOf(cat_id))){
				JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("children"));
				if(!jsonArray.toString().equals("[null]") && jsonArray!=null && !"".equals(jsonArray) && jsonArray.size()>0){//有子节点
					exportDept.setCatName(String.valueOf(jsonObject.get("text")));
					int inputCount = 0;
					Iterator it = jsonArray.iterator();
					 while(it.hasNext()){  
			        	Object object = it.next();
			        	if(object!=null){ 
			        		//System.out.println("object====" + object);
				        	JSONObject jsonObject2 = (JSONObject)object;
				        	if(jsonObject2!=null){
				        		ExportDept ex = doOutTreeBean(jsonObject2,listResult,fileSuffixList,map);
				        		inputCount += ex.getCountInfo();
				        	}
			        	}
					 }
					 exportDept.setCountInfo(inputCount);		

				}else{//没有子节点
					exportDept.setCatName(String.valueOf(jsonObject.get("text")));
					map.put("cat_id", cat_id);
					
					//得到该节点下面的信息列表
					String extype = (String)map.get("extype");
					List<ExportInfo> exportInfo = new ArrayList<ExportInfo>();
					String infoType = (String)map.get("infoType");
					//System.out.println("infoType===" + infoType);
					//System.out.println("extype===" + extype);
					if(infoType.equals("zwgk")){//政务公开信息
						if(extype.equals("1")){//列表形式
							exportInfo = getGkinfoByNodeAndCat(map);
						}else{//卡片形式
							exportInfo = getGkinfoCardByNodeAndCat(map);
						}
					}else{//共享信息
						if(extype.equals("1")){//列表形式
							//System.out.println("getGkGXinfoByNodeAndCat()--------");
							exportInfo = getGkGXinfoByNodeAndCat(map);
						}else{//卡片形式
							exportInfo = getGkGXinfoByNodeAndCat(map);
						}
					}
					
					exportDept.setCountInfo(exportInfo.size());
					exportDept.setExportInfo(exportInfo);
					
				}
				listResult.add(exportDept);
				
			}
			return exportDept;
		}catch (Exception e) {
			e.printStackTrace();
			return exportDept;
		}
	}
	
	//exportZwgkInfoByNodeCate 把栏目的排序调整正确 用  -- 调用的递归方法用 
	public static ExportDept doOutTreeBean2(JSONObject jsonObject,List<ExportDept> listResult2,Map<String,ExportDept> mapResult){
		ExportDept exportDept = new ExportDept();
		try{
			String cat_id = String.valueOf(jsonObject.get("id"));
			if(mapResult.containsKey(cat_id)){
				JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("children"));
				if(!jsonArray.toString().equals("[null]") && jsonArray!=null && !"".equals(jsonArray) && jsonArray.size()>0){//有子节点
					listResult2.add(mapResult.get(cat_id));
					Iterator it = jsonArray.iterator();
					 while(it.hasNext()){  
			        	Object object = it.next();
			        	if(object!=null){ 
				        	JSONObject jsonObject2 = (JSONObject)object;
				        	if(jsonObject2!=null){
				        		ExportDept ex = doOutTreeBean2(jsonObject2,listResult2,mapResult);
				        	}
			        	}
					 }
				}else{//没有子节点
					listResult2.add(mapResult.get(cat_id));
				}
            }
			return exportDept;
		}catch (Exception e) {
			e.printStackTrace();
			return exportDept;
		}
	}
	
	//通过公开节点和该节点下的栏目得到信息列表
	public static List<ExportInfo> getGkinfoByNodeAndCat(Map map){
		return ExportInfoDao.getGkinfoByNodeAndCat(map);
	}
	
	//通过公开节点和该节点下的栏目得到信息列表（有文号和简介）
	public static List<ExportInfo> getGkinfoCardByNodeAndCat(Map map){
		return ExportInfoDao.getGkinfoCardByNodeAndCat(map);
	}
	
	// 通过共享栏目ID和公开节点得到所共享的信息列表
	public static List<ExportInfo> getGkGXinfoByNodeAndCat(Map map){
		String node_id = (String)map.get("node_id");
		String cat_id = String.valueOf(map.get("cat_id"));
		String sql = com.deya.wcm.services.zwgk.appcatalog.RegulationManager.spellConSql(cat_id,node_id,0);
		map.put("catSql",sql);
		//System.out.println("map=======" + map);
		return ExportInfoDao.getGkGXinfoByNodeAndCat(map);
	}
	
	
	public static void main(String arr[]){
		

		
		String treeStr = "[{\"id\":0,\"text\":\"人民政府\",\"attributes\":{\"url\":\"\",\"handl\":\"\"},\"children\":[{\"id\":1834,\"text\":\"概况信息\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?app_id=zwgk&cat_id=1834\",\"handl\":\"\"},\"state\":'closed',\"children\":[{\"id\":1835,\"text\":\"机构职能\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?app_id=zwgk&cat_id=1835\",\"handl\":\"\"}},{\"id\":1836,\"text\":\"领导信息\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?app_id=zwgk&cat_id=1836\",\"handl\":\"\"}},{\"id\":1837,\"text\":\"内设科室\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?app_id=zwgk&cat_id=1837\",\"handl\":\"\"}},{\"id\":1838,\"text\":\"地区概况\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?app_id=zwgk&cat_id=1838\",\"handl\":\"\"}}]},{\"id\":1839,\"text\":\"法规公文\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?app_id=zwgk&cat_id=1839\",\"handl\":\"\"},\"state\":'closed',\"children\":[{\"id\":1840,\"text\":\"法规\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?app_id=zwgk&cat_id=1840\",\"handl\":\"\"}},{\"id\":1841,\"text\":\"法规\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?app_id=zwgk&cat_id=1841\",\"handl\":\"\"}},{\"id\":1842,\"text\":\"公文\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?app_id=zwgk&cat_id=1842\",\"handl\":\"\"}}]},{\"id\":2039,\"text\":\"推送测试\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?app_id=zwgk&cat_id=2039\",\"handl\":\"\"}}]}]";
		String cat_ids = "1834,1835,1836,1837,1839,1841";
		
		Map map = new HashMap();
		map.put("extype", treeStr);
		map.put("cat_ids", cat_ids);
		exportZwgkInfoByNodeCate(map);
	
		/*
		List<String> fileSuffixList = Arrays.asList(cat_ids.split(","));
		List<ExportDept> listResult = new ArrayList<ExportDept>();
		Map<String,ExportDept> mapResult = new HashMap<String, ExportDept>();
		List<ExportDept> listResult2 = new ArrayList<ExportDept>();
		
		JSONArray jsonArrayAll = JSONArray.fromObject(treeStr);  
        Iterator itAll = jsonArrayAll.iterator();
        JSONObject jsonObjectAll = (JSONObject)itAll.next();
		JSONArray jsonArray = JSONArray.fromObject(jsonObjectAll.get("children"));
		Iterator it = jsonArray.iterator();
        while(it.hasNext()){
        	JSONObject jsonObject = (JSONObject)it.next();
        	ExportDept exportDept = doOutTreeBean(jsonObject,listResult,fileSuffixList);
        }
        
        for(ExportDept exportDept : listResult){
    		System.out.println(exportDept.getCateId()+":"+exportDept.getCatName()+"("+exportDept.getCountInfo()+")");
    		mapResult.put(exportDept.getCateId(), exportDept);
    		List<ExportInfo> exportInfo = exportDept.getExportInfo();
    		if(exportInfo.size()>0){
    			for(ExportInfo info : exportInfo){
    				System.out.println(info.getIndexCode()+"--"+info.getTitle());
    			}
    		}
    	}
        
        
        Iterator it2 = jsonArray.iterator();
        while(it2.hasNext()){
        	JSONObject jsonObject = (JSONObject)it2.next();
        	ExportDept exportDept = doOutTreeBean2(jsonObject,listResult2,mapResult);
        }
        
        System.out.println("-----------------------------");
        
        for(ExportDept exportDept : listResult2){
    		System.out.println(exportDept.getCateId()+":"+exportDept.getCatName()+"("+exportDept.getCountInfo()+")");
    		mapResult.put(exportDept.getCateId(), exportDept);
    		List<ExportInfo> exportInfo = exportDept.getExportInfo();
    		if(exportInfo.size()>0){
    			for(ExportInfo info : exportInfo){
    				System.out.println(info.getIndexCode()+"--"+info.getTitle());
    			}
    		}
    	}
    	
    	*/
	}
	
	
	
	
}
