package com.deya.wcm.services.cms.count;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.FormatUtil;
import com.deya.util.OutExcel;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.cms.count.SiteCountBean;
import com.deya.wcm.bean.zwgk.count.GKCountBean;
import com.deya.wcm.services.appeal.count.CountUtil;
import com.deya.wcm.services.zwgk.count.GKCountManager;

/**
 * 统计导出Excel文件处理类
 * <p>Title: CmsCountExcelOut</p>
 * <p>Description: 统计导出Excel文件处理类</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class SiteCountExcelOut {

	//删除今天以前的文件夹  并 创建今天的文件夹和xls文件
	public static String[] getFileUrl(){
		//删除今天以前的文件夹
		String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path");
		String path = FormatUtil.formatPath(root_path + "/control/count/file/");  
		//System.out.println("path===" + path);
		File filePath = new File(path);
		if(!filePath.exists()){
			filePath.mkdir();
		}
		CountUtil.deleteFile(path);
		
		//创建今天的文件夹和xls文件
		String nowDate = CountUtil.getNowDayDate();
		String fileTemp2 = FormatUtil.formatPath(path+File.separator+nowDate);
		//System.out.println("fileTemp2===" + fileTemp2);
		File file2 = new File(fileTemp2);
		if(!file2.exists()){
			file2.mkdir();
		}
		String nowTime = CountUtil.getNowDayDateTime();
		String xls = nowTime + CountUtil.getEnglish(1)+".xls";
		String xlsFile = fileTemp2+File.separator+xls;
		String urlFile = "/sys/control/count/file/"+nowDate+File.separator+xls;
		//System.out.println("xlsFile===" + xlsFile);
		String[] str = {xlsFile,urlFile};
		
		return str;
	}
	 
	
	/**
	 * 统计导出Excel文件 -- 按栏人员统计
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String siteInfoOutExcelByUser(List listBean,List headerList){
		try{
			
			String[] fileStr = getFileUrl();
			
			//得到header
			final int size =  headerList.size();
			String[] head = (String[])headerList.toArray(new String[size]);
			
			
			//填充数据
			String[][] data = new String[listBean.size()][8];
			for(int i=0;i<listBean.size();i++){
				   SiteCountBean countBean = (SiteCountBean)listBean.get(i);
				   data[i][0] = countBean.getUser_name();
				   data[i][1] = String.valueOf(countBean.getDept_name());
				   data[i][2] = String.valueOf(countBean.getInputCount());
				   data[i][3] = String.valueOf(countBean.getHostInfoCount());
				   data[i][4] = String.valueOf(countBean.getRefInfoCount());
				   data[i][5] = String.valueOf(countBean.getLinkInfoCount());
				   data[i][6] = String.valueOf(countBean.getReleasedCount());
				   countBean.setReleaseRate(); 
				   data[i][7] = String.valueOf(countBean.getReleaseRate());   
			 }
			
			OutExcel oe=new OutExcel("站点信息统计表");
			oe.doOut(fileStr[0],head,data);
			
			return fileStr[1];
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	/**
	 * 按部门统计
	 * @param List listBean
	 * @param Map map
	 * @return	String 文件路径名字
	 */
	public static String orgTreeInfoOutExcel(List headerList,Map mp){
		try{
			int num = 0;
			String[] fileStr = getFileUrl();
			
			//里面数据是递归格式
			List listBean = SiteCountManager.getSiteCountListByInput(mp);
			
			Map map = new HashMap();
	        List list = new ArrayList();
	        map.put("numcount", 1);
	        for(int i=0;i<listBean.size();i++){
	        	SiteCountBean countBean = (SiteCountBean)listBean.get(i);
	        	int mun = 1;
	        	doOutTreeBean(countBean,mun,map,list);
	        }
	        
	        List listResult = new ArrayList();
	    	int numcount = Integer.valueOf(map.get("numcount").toString());
	    	for(int i=0;i<list.size();i++){
	    		Map mapO = (Map)list.get(i);
	    		int numO = Integer.valueOf(mapO.get("num").toString());
	            String dept_name = mapO.get("dept_name").toString();
	            //System.out.println("dept_name====" + dept_name);
	    		List listCur = new ArrayList();
	    		for(int j=1;j<=numcount;j++){
	    			if(j==numO){
	    				listCur.add(dept_name);
	    			}else{
	    				listCur.add("");
	    			}
	    		}
	    		int num1 = listCur.size();
	    		listCur.add(String.valueOf(mapO.get("inputCount")));
	    		listCur.add(String.valueOf(mapO.get("hostInfoCount")));
	    		listCur.add(String.valueOf(mapO.get("refInfoCount")));
	    		listCur.add(String.valueOf(mapO.get("linkInfoCount")));
	    		listCur.add(String.valueOf(mapO.get("releasedCount")));
	    		listCur.add(String.valueOf(mapO.get("releaseRate")));
	    		int num2 = listCur.size();
	    		num = num2 - num1;
	    		listResult.add(listCur);
	    	}
			
			//得到header
			final int size =  headerList.size();
			String[] head = (String[])headerList.toArray(new String[size]);
			
			//填充数据
			String[][] data = new String[listResult.size()][numcount+num];
	    	for(int i=0;i<listResult.size();i++){
	    		List listCur = (List)listResult.get(i);
	    		for(int j=0;j<numcount+num;j++){
	    			data[i][j] = (String)listCur.get(j);
	    		}
	    		
	    	}
			  
			OutExcel oe=new OutExcel("按部门统计");
	    	oe.doOutTree(fileStr[0],head,data,numcount);      
			
			return fileStr[1];
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//gkTreeInfoOutExcel要用到的方法
    public static void setNumCount(Map map,int num){
    	if(num>Integer.valueOf(map.get("numcount").toString())){
    		map.put("numcount", num);
    	}
    }
    
    //gkTreeInfoOutExcel要用到的方法
    public static void doOutTreeBean(SiteCountBean countBean,int num,Map map,List list){
    	boolean is_leaf = countBean.isIs_leaf();
    	if(is_leaf){
    		//System.out.print(String.valueOf(num)+countBean.getCat_name());
    		Map map1 = new HashMap();
    		map1.put("num", num);
    		map1.put("dept_name",countBean.getDept_name());
    		map1.put("inputCount",countBean.getInputCount());
    		map1.put("hostInfoCount",countBean.getHostInfoCount());
    		map1.put("refInfoCount",countBean.getRefInfoCount());
    		map1.put("linkInfoCount",countBean.getLinkInfoCount());
    		map1.put("releasedCount",countBean.getReleasedCount());
    		map1.put("releaseRate",countBean.getReleaseRate());
    		list.add(map1);
    	}else{
    		//System.out.print(String.valueOf(num)+countBean.getCat_name());
    		Map map1 = new HashMap();
    		map1.put("num", num);
    		map1.put("dept_name",countBean.getDept_name());
    		map1.put("inputCount",countBean.getInputCount());
    		map1.put("hostInfoCount",countBean.getHostInfoCount());
    		map1.put("refInfoCount",countBean.getRefInfoCount());
    		map1.put("linkInfoCount",countBean.getLinkInfoCount());
    		map1.put("releasedCount",countBean.getReleasedCount());
    		map1.put("releaseRate",countBean.getReleaseRate());
    		list.add(map1);
    		List listChild = countBean.getChild_list(); 
    		//List listChild = null;
    		if(listChild!=null && !"".equals(listChild) && listChild.size()>0){
    			num++; 
    			setNumCount(map,num);
    			 for(int i=0;i<listChild.size();i++){
    				 SiteCountBean siteCountBean = (SiteCountBean)listChild.get(i);  
    	        	if(siteCountBean!=null){
    	        		doOutTreeBean(siteCountBean,num,map,list);
    	        	}
    	        }
    		}
    	}
    }
    
    /**
	 * 按栏目统计
	 * @param List listBean
	 * @param Map map
	 * @return	String 文件路径名字
	 */
	public static String getInputCountListByCateOutExcel(List headerList,Map mp){
		try{
			int num = 0;
			String[] fileStr = getFileUrl();
			
			//里面数据是递归格式
			List listBean = CmsCountManager.getInputCountListByCate(mp);
			
			Map map = new HashMap();
	        List list = new ArrayList();
	        map.put("numcount", 1);
	        for(int i=0;i<listBean.size();i++){
	        	SiteCountBean countBean = (SiteCountBean)listBean.get(i);
	        	int mun = 1;
	        	doOutTreeBeanByCate(countBean,mun,map,list);
	        }
	        
	        List listResult = new ArrayList();
	    	int numcount = Integer.valueOf(map.get("numcount").toString());
	    	for(int i=0;i<list.size();i++){
	    		Map mapO = (Map)list.get(i);
	    		int numO = Integer.valueOf(mapO.get("num").toString());
	            String dept_name = mapO.get("cat_name").toString();
	            //System.out.println("dept_name====" + dept_name);
	    		List listCur = new ArrayList();
	    		for(int j=1;j<=numcount;j++){
	    			if(j==numO){
	    				listCur.add(dept_name);
	    			}else{
	    				listCur.add("");
	    			}
	    		}
	    		int num1 = listCur.size();
	    		listCur.add(String.valueOf(mapO.get("inputCount")));
	    		listCur.add(String.valueOf(mapO.get("releasedCount")));
	    		listCur.add(String.valueOf(mapO.get("picReleasedCount")));
	    		listCur.add(String.valueOf(mapO.get("releaseRate")));
	    		
	    		int num2 = listCur.size();
	    		num = num2 - num1;
	    		listResult.add(listCur);
	    	}
			 
			//得到header
			final int size =  headerList.size();
			String[] head = (String[])headerList.toArray(new String[size]);
			
			//填充数据
			String[][] data = new String[listResult.size()][numcount+num];
	    	for(int i=0;i<listResult.size();i++){
	    		List listCur = (List)listResult.get(i);
	    		for(int j=0;j<numcount+num;j++){
	    			data[i][j] = (String)listCur.get(j);
	    		}
	    		
	    	}
			  
			OutExcel oe=new OutExcel("按栏目统计");
	    	oe.doOutTree(fileStr[0],head,data,numcount);      
			
			return fileStr[1];
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
    
    //getInputCountListByCateOutExcel要用到的方法
    public static void doOutTreeBeanByCate(SiteCountBean countBean,int num,Map map,List list){
    	boolean is_leaf = countBean.isIs_leaf();
    	if(is_leaf){
    		//System.out.print(String.valueOf(num)+countBean.getCat_name());
    		Map map1 = new HashMap();
    		map1.put("num", num);
    		map1.put("cat_name",countBean.getCat_name());
    		map1.put("inputCount",countBean.getInputCount());
    		map1.put("releasedCount",countBean.getReleasedCount());
    		map1.put("picReleasedCount",countBean.getPicReleasedCount());
    		map1.put("releaseRate",countBean.getReleaseRate());
    		list.add(map1);
    	}else{
    		//System.out.print(String.valueOf(num)+countBean.getCat_name());
    		Map map1 = new HashMap();
    		map1.put("num", num);
    		map1.put("cat_name",countBean.getCat_name());
    		map1.put("inputCount",countBean.getInputCount());
    		map1.put("releasedCount",countBean.getReleasedCount());
    		map1.put("picReleasedCount",countBean.getPicReleasedCount());
    		map1.put("releaseRate",countBean.getReleaseRate());
    		list.add(map1);
    		List listChild = countBean.getChild_list(); 
    		//List listChild = null;
    		if(listChild!=null && !"".equals(listChild) && listChild.size()>0){
    			num++; 
    			setNumCount(map,num);
    			 for(int i=0;i<listChild.size();i++){
    				 SiteCountBean siteCountBean = (SiteCountBean)listChild.get(i);  
    	        	if(siteCountBean!=null){
    	        		doOutTreeBeanByCate(siteCountBean,num,map,list);
    	        	}
    	        }
    		}
    	}
    }
	
	
}
