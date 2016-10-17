package com.deya.wcm.services.search.util.tongyinci;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class InitCiku {

	public static List<String> list = new ArrayList<String>();
	public static HashMap<String, String> hashMap = new HashMap<String, String>();
	static String classpath = InitCiku.class.getResource("").getPath();
	
	static {
		InitCiku.initCiku("");
	}
	
	public static void initCiku(String root) {
		try {
			//String pathFile = root+"\\WEB-INF\\classes\\com\\search\\compass";
			//String pathFile = root+"WEB-INF/classes/com/search/compass";String pathFile
//			FileReader fr_1 = new FileReader(classpath + File.separator + "dict.txt");
//			BufferedReader bf_1 = new BufferedReader(fr_1);
//			String str_1;
//			while ((str_1 = bf_1.readLine()) != null) {
//				if (!str_1.startsWith("n")) {
//					list.add(str_1);
//				}
//			}
//			bf_1.close();
//			fr_1.close();

			// String search_1 = "我";
			// StringBuffer sb_1 = new StringBuffer();
			// for(String forValue:list){
			// if(forValue.startsWith(search_1)){
			// sb_1.append(forValue+",");
			// }
			// }
			// System.out.println(sb_1.toString());

			if(hashMap.size()<1){//还没有进行初始化
				FileReader fr = new FileReader(classpath + File.separator + "dict2.txt");
				BufferedReader bf = new BufferedReader(fr);
				String str;
				while ((str = bf.readLine()) != null) {
					String strR[] = str.split("=");
					// System.out.println(strR[1]);
					hashMap.put(strR[0], strR[1]);
				}
				bf.close();
				fr.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public static String getTongyinci(String search){
//		/String search = "句章";
        String pinyin = Pinyin4j.makeStringByStringSet(Pinyin4j.getPinyin(search));
  		//System.out.println(pinyin);
        String py[] = pinyin.split(",");
  		StringBuffer sb = new StringBuffer();
	    for(String py_1 : py){
	    	     //System.out.println(hashMap);
	    	     String value = hashMap.get(py_1);
	    	     if(value!=null){
					 sb.append(value+",");
	    	     }
	    }
	    HashSet<String> strSet = new HashSet<String>();
	    CollectionUtils.addAll(strSet, sb.toString().replaceAll(",,",",").split(","));
	    Iterator<String> it = strSet.iterator();
	    sb.delete(0, sb.length());
	    while(it.hasNext()){
	    	sb.append(it.next()+" ");
	    }
        //System.out.println(sb.toString().replaceAll(",,",","));
        return sb.toString().replaceAll(",,",",");
	}
	
	
	public static void main(String arr[]){
		System.out.println(InitCiku.getTongyinci("宁下"));
	}
	
}
