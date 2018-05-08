package com.deya.wcm.services.model.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class CmsIresourceCategoryUtil {

	public static Properties p = new Properties(); 
	static {
		reloadProperties();
	}
	
	public static void reloadProperties(){
		try{
			InputStream inputStream = new CmsIresourceCategoryUtil().getClass().getClassLoader().getResourceAsStream("cms_iresource_cat.properties");    
			p.load(inputStream);		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}  
	
	public static String getCmscatidByResourceid(String treeId){
		String result = "";
		try{
			
			result = p.getProperty(treeId);
			if(result==null){
				result = p.getProperty("all_category_id");
				if(result==null){
					result = "";
				}
			}
			
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}
	
	
	//通过 dept_potion 得到该信息要插入的站点列表 以,分割
	public static List<String> getSiteIdByDeptTreePosition(String dept_position){
		 List<String> list = new ArrayList<String>();
		try{
			//调用朱亮的接口
			String str = "";
			//str = SiteReleHandl.getSiteIDSForDeptPosition(dept_position);
			if(str==null || "".equals(str)){
				return list;
			}
			//System.out.println("getSiteIdByDeptTreePosition-----str::"+str);
			List<String> list2 = Arrays.asList(str.split(","));
			for(String s : list2){
				if(s!=null && !"".equals(s)){
					list.add(s);
				}
			}
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

           String str = getCmscatidByResourceid("ss");
           System.out.println(str);
          
	}

}
