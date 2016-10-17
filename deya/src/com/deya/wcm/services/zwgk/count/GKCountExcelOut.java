package com.deya.wcm.services.zwgk.count;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.FormatUtil;
import com.deya.util.OutExcel;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.zwgk.count.GKCountBean;
import com.deya.wcm.bean.zwgk.count.GKysqCountBean;
import com.deya.wcm.services.appeal.count.CountUtil;

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
public class GKCountExcelOut {

	//删除今天以前的文件夹  并 创建今天的文件夹和xls文件
	public static String[] getFileUrl(){
		//删除今天以前的文件夹
		String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path");
		String path = FormatUtil.formatPath(root_path + "/zwgk/count/file/");  
		File filePath = new File(path);
		if(!filePath.exists()){
			filePath.mkdir();
		}
		//System.out.println("path===" + path);
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
		String urlFile = "/sys/zwgk/count/file/"+nowDate+File.separator+xls;
		//System.out.println("xlsFile===" + xlsFile);
		String[] str = {xlsFile,urlFile};
		
		return str;
	}
	 
	
	/**
	 * 政务公开信息量统计 -- 按节点统计
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String gkInfoOutExcel(List listBean,List headerList){
		try{
			
			String[] fileStr = getFileUrl();
			
			//得到header
			final int size =  headerList.size();
			String[] head = (String[])headerList.toArray(new String[size]);
				
			//填充数据
			String[][] data = new String[listBean.size()][6];
			for(int i=0;i<listBean.size();i++){
				   GKCountBean countBean = (GKCountBean)listBean.get(i);
				   data[i][0] = countBean.getSite_name();
				   data[i][1] = String.valueOf(countBean.getInfo_count());
				   data[i][2] = String.valueOf(countBean.getZ_count());
				   data[i][3] = String.valueOf(countBean.getY_count());
				   data[i][4] = String.valueOf(countBean.getB_count());
				   data[i][5] = String.valueOf(countBean.getPic_count());
			 }	 
			OutExcel oe=new OutExcel("政务公开信息量统计");
			oe.doOut(fileStr[0],head,data);
			
			return fileStr[1];
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	/**
	 * 政务公开信息量统计 -- 按节点录入人统计
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String gkWorkInfoOutExcel(List listBean,List headerList){
		try{
			
			String[] fileStr = getFileUrl();
			
			//得到header
			final int size =  headerList.size();
			String[] head = (String[])headerList.toArray(new String[size]);
				
			//填充数据
			String[][] data = new String[listBean.size()][5];
			for(int i=0;i<listBean.size();i++){
				   List list = (List)listBean.get(i);
				   data[i][0] = String.valueOf(list.get(0));
				   data[i][1] = String.valueOf(list.get(1));
				   data[i][2] = String.valueOf(list.get(2));
				   data[i][3] = String.valueOf(list.get(3));
				   data[i][4] = String.valueOf(list.get(4)+"%");
			 }
			  
			OutExcel oe=new OutExcel("政务公开信息量统计");
			oe.doOut(fileStr[0],head,data);
			
			return fileStr[1];
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	/**
	 * 依申请公开信息量统计 -- 按节点统计
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String ysqgkInfoOutExcel(List listBean,List headerList){
		try{
			
			String[] fileStr = getFileUrl();
			
			//得到header
			final int size =  headerList.size();
			String[] head = (String[])headerList.toArray(new String[size]);
				
			//填充数据
			String[][] data = new String[listBean.size()][9];
			for(int i=0;i<listBean.size();i++){
				   GKysqCountBean countBean = (GKysqCountBean)listBean.get(i);
				   if(headerList.contains("统计年月")){
					   data[i][0] = countBean.getPut_dtime();
				   }else{
					   data[i][0] = countBean.getNode_name();
				   }
				   data[i][1] = String.valueOf(countBean.getTotalCnt());
				   data[i][2] = String.valueOf(countBean.getUnAcceptCnt());
				   data[i][3] = String.valueOf(countBean.getAcceptedCnt());
				   data[i][4] = String.valueOf(countBean.getReplyCnt());
				   data[i][5] = String.valueOf(countBean.getInvalidCnt());
				   data[i][6] = String.valueOf(countBean.getThirdCnt());
				   data[i][7] = String.valueOf(countBean.getDelayCnt());
				   data[i][8] = String.valueOf(countBean.getTimeoutCnt());
			 }
			  
			OutExcel oe=new OutExcel("依申请公开信息量统计");
			oe.doOut(fileStr[0],head,data);
			
			return fileStr[1];
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	
	/**
	 * 依申请公开信息量统计 -- 按类型统计
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String ysqgkTypeInfoOutExcel(List listBean,List headerList){
		try{
			
			String[] fileStr = getFileUrl();
			
			//得到header
			final int size =  headerList.size();
			String[] head = (String[])headerList.toArray(new String[size]);
				
			//填充数据
			String[][] data = new String[listBean.size()][7];
			for(int i=0;i<listBean.size();i++){
				   GKysqCountBean countBean = (GKysqCountBean)listBean.get(i);
				   data[i][0] = countBean.getNode_name();
				   data[i][1] = String.valueOf(countBean.getType_total());
				   data[i][2] = String.valueOf(countBean.getQbgk_cnt());
				   data[i][3] = String.valueOf(countBean.getBfgk_cnt());
				   data[i][4] = String.valueOf(countBean.getBygk_cnt());
				   data[i][5] = String.valueOf(countBean.getFbdwxx_cnt());
				   data[i][6] = String.valueOf(countBean.getXxbcz_cnt());
			 }
			  
			OutExcel oe=new OutExcel("依申请公开信息量统计");
			oe.doOut(fileStr[0],head,data);
			
			return fileStr[1];
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	
	/**
	 * 信息公开信息量统计 -- 按某个节点中所有栏目统计
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String gkTreeInfoOutExcel(List headerList,String site_id,String startDate,String endDate){
		try{
			int num =0;
			String[] fileStr = getFileUrl();
			
			List listBean = GKCountManager.getGKCountList(site_id, startDate, endDate);
			
			Map map = new HashMap();
	        List list = new ArrayList();
	        map.put("numcount", 1);
	        for(int i=0;i<listBean.size();i++){
	        	GKCountBean countBean = (GKCountBean)listBean.get(i);
	        	int mun = 1;
	        	doOutTreeBean(countBean,mun,map,list);
	        }
	        
	        List listResult = new ArrayList();
	    	int numcount = Integer.valueOf(map.get("numcount").toString());
	    	for(int i=0;i<list.size();i++){
	    		Map mapO = (Map)list.get(i);
	    		int numO = Integer.valueOf(mapO.get("num").toString());
	            String cat_name = mapO.get("cat_name").toString();
	    		List listCur = new ArrayList();
	    		for(int j=1;j<=numcount;j++){
	    			if(j==numO){
	    				listCur.add(cat_name);
	    			}else{
	    				listCur.add("");
	    			}
	    		}
	    		int num1 = listCur.size();
	    		listCur.add(String.valueOf(mapO.get("info_count")));
	    		listCur.add(String.valueOf(mapO.get("z_count")));
	    		listCur.add(String.valueOf(mapO.get("y_count")));
	    		listCur.add(String.valueOf(mapO.get("b_count")));
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
			  
			OutExcel oe=new OutExcel("信息公开统计");
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
    public static void doOutTreeBean(GKCountBean countBean,int num,Map map,List list){
    	boolean is_leaf = countBean.isIs_leaf();
    	if(is_leaf){
    		//System.out.print(String.valueOf(num)+countBean.getCat_name());
    		Map map1 = new HashMap();
    		map1.put("num", num);
    		map1.put("cat_name",countBean.getCat_name());
    		map1.put("info_count",countBean.getInfo_count());
    		map1.put("z_count",countBean.getZ_count());
    		map1.put("y_count",countBean.getY_count());
    		map1.put("b_count",countBean.getB_count());
    		list.add(map1);
    	}else{
    		//System.out.print(String.valueOf(num)+countBean.getCat_name());
    		Map map1 = new HashMap();
    		map1.put("num", num);
    		map1.put("cat_name",countBean.getCat_name());
    		map1.put("info_count",countBean.getInfo_count());
    		map1.put("z_count",countBean.getZ_count());
    		map1.put("y_count",countBean.getY_count());
    		map1.put("b_count",countBean.getB_count());
    		list.add(map1);
    		List listChild = countBean.getChild_list(); 
    		//List listChild = null;
    		if(listChild!=null && !"".equals(listChild) && listChild.size()>0){
    			num++; 
    			setNumCount(map,num);
    			 for(int i=0;i<listChild.size();i++){
    		        GKCountBean gkCountBean = (GKCountBean)listChild.get(i);  
    	        	if(gkCountBean!=null){
    	        		doOutTreeBean(gkCountBean,num,map,list);
    	        	}
    	        }
    		}
    	}
    }
	

}
