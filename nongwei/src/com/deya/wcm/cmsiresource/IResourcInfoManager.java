package com.deya.wcm.cmsiresource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.control.domain.SiteDomainManager;
import com.deya.wcm.services.model.services.WcmZykInfoService;

public class IResourcInfoManager {

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
	
	/**
	 * 返回的map有一个是list 信息列表list里面是map（里面是字段名字和值对应）
	 * 还有 文件的路径 
	 * 其中
	 * jgpFile是jpg的url  
	 * epubFile是epub的url
	 * flvFile是 视频 的url
	 * pdfFile是 视频 的url
	 * 
	 * count是总条数 
	 * cur_page当前第几页
	 * page_count共多少页
	 * prev_num上一页
	 * next_num下一页
	 * page_count 末页
	 */
    public static Map getIResourceInfoList(Map map){
    	    Map mapResult = new HashMap();
    	    try{
    	    	mapResult = IResourceRMIService.getIResourceInfoList(map);
    	    	return mapResult;
    	    }catch (Exception e) {
				// TODO: handle exception
    	    	e.printStackTrace();
    	    	return mapResult;
			}
    }	
    
    
    /**
	 * 返回的map有一个是list 信息列表list里面是map（里面是字段名字和值对应）
	 * map 默认有 content_url
	 * 
	 * count是总条数 
	 * cur_page当前第几页
	 * page_count共多少页
	 * prev_num上一页
	 * next_num下一页
	 * page_count 末页
	 */
    //wcm要调用的资源目录的列表的接口 得到后取出wcm中对应信息的 内容页面url
    public static Map getIResourceInfoListAndUrl(Map map){
    	Map mapResult = new HashMap();
    	try{
    		String site_id = (String)map.get("site_id");
    		mapResult = IResourceRMIService.getIResourceInfoList(map);
    		List<Map> listMap = (List<Map>)map.get("list");
    		for(Map map2 : listMap){
    			String i_id = (String)map2.get("id");
    			System.out.println("i_id = " + i_id);
    			String info_id = WcmZykInfoService.getWcminfo_zykinfoById(i_id,site_id); //取出wcm中对应信息id
    			InfoBean infoBean = InfoBaseManager.getInfoById(info_id);
    			String url = infoBean.getContent_url();
    			map2.put("content_url",url); 
    		}
    		return mapResult;
    	}catch (Exception e) {
			e.printStackTrace();
			return mapResult;
		}
    }
    
    
    //通过dept_tree得到信息的条数
    public static int getCountByDeptTree(String dept_tree){
    	int count = 0;
    	try{
    		Map m = new HashMap();
        	m.put("pageNo","1");
        	m.put("pageSize","1");
        	//m.put("desFields","xmmc,optime,deptName"); //要查询的字段
        	//m.put("desFields","resTitle,optime,deptName"); //要查询的字段
        	//条件部门
        	//m.put("dept_id", "1234");
        	m.put("desFields","id");
        	m.put("deptTreePath",dept_tree);
        	Map map = IResourcInfoManager.getIResourceInfoList(m);
        	String countstr = (String)map.get("count");
        	if(countstr==null || "".equals(countstr)){
        		return 0;
        	} 
    		count = Integer.parseInt(countstr);
    	}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return count;
		}
    }
    
    
    
    
    
    public static void main(String arr[]){
    	
    	Map m = new HashMap();
    	m.put("pageNo","1");
    	m.put("pageSize","9");
    	//m.put("desFields","xmmc,optime,deptName"); //要查询的字段
    	m.put("desFields","mc"); //要查询的字段
    	//条件部门
    	//m.put("dept_id", "1234");
    	m.put("deptTreePath", "#1#");
//    	String site_id = SiteDomainManager.getSiteIDByDomain(request.getLocalName());
//    	m.put("site_id","site_id");
    	
    	m.put("decEnName","csfj");
    	
    	//m.put("fields","decbook_sm,decbook_jj"); 
    	//m.put("keys","父母");
    	//m.put("treeId","01");
    	m.put("treeId","40288c9a3a908ae9013a90909a8d002c");
    	System.out.println("m======" + m);
    	Map map = IResourcInfoManager.getIResourceInfoList(m);

    	//System.out.println("map======" + map);
    	
    	List<Map> listMap = (List<Map>)map.get("list");
    	System.out.println("listMap======" + listMap.size());
    	System.out.println("总条数" + map.get("count"));
    	System.out.println("当前第几页" + map.get("cur_page"));
    	System.out.println("共多少页" + map.get("page_count"));
    	System.out.println("上一页" + map.get("prev_num"));
    	System.out.println("下一页" + map.get("next_num"));
    	System.out.println("末页" + map.get("page_count"));
    	for(Map map3:listMap){
    		System.out.println(map3);
    		//System.out.println(map3.get("decbook_sm"));
    	}
    	
    	
    	
    }
    
}
