package com.deya.wcm.services.search.util.autokeys;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BaiduResult {

	
	public static List<String> getBaiduResult(String str_2){
		
		List<String> list = new ArrayList<String>();
		try{
			
			str_2 = java.net.URLEncoder.encode(str_2,"utf-8"); 
			String url_baidu = "http://suggestion.baidu.com/su?wd=="+str_2;
			//System.out.println("str_2 --- " + str_2);
			String result_baidu = FileDown.getBaiduResult(url_baidu);
			result_baidu = result_baidu.replaceAll("window.baidu.sug\\(","").replaceAll("\\);", "");
			System.out.println("result_baidu::"+result_baidu);
			JSONObject jo = JSONObject.fromObject(result_baidu);
	        String oStr = jo.get("s").toString().replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"", "");
	        //System.out.println("oStr---" + oStr);
	        String[] oarry = oStr.split(",");
	        for(String s : oarry){
	        	if(!s.trim().equals("")){
	        		list.add(s.trim());
	        	}
	        }
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		//DownFileUtil.readBaiduString(result_baidu);
		String str_2 = "";
		//str_2 = "zho";
		
		List<String> list = getBaiduResult(str_2);
		for(String s : list){
			System.out.println(s);
		}
	}

}
