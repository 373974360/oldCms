package com.deya.wcm.services.appeal.count;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.deya.util.io.FileOperation;

/**
 * 诉求统计工具类. 
 * @version 1.0 10/05/08
 * @author lisupei
 */
public class CountUtil {
   
	/**
     * 得到昨天的日期  
     * @return String yyyy_MM_dd 
     */
	public static String getYesterDayDate(){
		String str = "";
		try{
			Calendar   rightNow   =   Calendar.getInstance(); 
			GregorianCalendar   gc1   =   new   GregorianCalendar(rightNow.get(Calendar.YEAR),   rightNow.get(Calendar.MONTH), 
			rightNow.get(Calendar.DAY_OF_MONTH)); 
			gc1.add(Calendar.DATE,   -1); 
			Date   d1   =   (Date)gc1.getTime();
			SimpleDateFormat   df   =   new   SimpleDateFormat("yyyy_MM_dd"); 
			String   dd   =   df.format(d1);
			str = dd;
			return str;
		}catch (Exception e) {
			e.printStackTrace();
			return str;
		}
	}
	
	
	/**
     * 得到今天的日期  
     * @return String yyyy_MM_dd 
     */
	public static String getNowDayDate(){
		String str = "";
		try{
			Calendar   rightNow   =   Calendar.getInstance(); 
			GregorianCalendar   gc1   =   new   GregorianCalendar(rightNow.get(Calendar.YEAR),   rightNow.get(Calendar.MONTH), 
			rightNow.get(Calendar.DAY_OF_MONTH)); 
			Date   d1   =   (Date)gc1.getTime();
			SimpleDateFormat   df   =   new   SimpleDateFormat("yyyy_MM_dd"); 
			String   dd   =   df.format(d1);
			str = dd;
			return str;
		}catch (Exception e) {
			e.printStackTrace();
			return str;
		}
	}
	
	/**
     * 得到现在的日期  
     * @return String yyyyMMddHHmmss 
     */
	public static String getNowDayDateTime(){
		String str = "";
		try{ 
			Date   d1   =   new Date();
			SimpleDateFormat   df   =   new   SimpleDateFormat("yyyyMMddHHmmss"); 
			String   dd   =   df.format(d1);
			str = dd; 
			return str;
		}catch (Exception e) {
			e.printStackTrace();
			return str;
		}
	}
	
	/**
     * 得到随机英文字母  
     * @param int count 个数
     * @return String 
     */
	public static String getEnglish(int count){
		StringBuffer sb = new StringBuffer();
		try{
			
			 // 先设置26个英文字母
			  char[] allChar = new char[26];
			  for (int i = 97; i < 123; i++) {
			   allChar[i - 97] = (char) i;
			  }
			  // 存放已经取到的字符。为了方便使用list以及查找已经获取过的字符，因此使用list，目标是使用它的contains方法。
			  List<String> contentList = new ArrayList<String>();
			  while (count > 0) {
			   // 取随机数，用以乘以26，得到[0,26)（大于等于零，小于26）的数字。
			   double numDouble = Math.random() * 26;
			   // 用得到的结果除以1，可得到0-25一共26个数字中的任何一个。
			   int position = (int) numDouble / 1;
			   // 判断对应位置上面的字符是否已经被取到，如果取到则直接进入下一个循环，不做保存处理。
			   if (contentList.contains(String.valueOf(allChar[position])))
			    continue;
			   // 如果之前没有被取到，则保存到结果集当中，并将计数数字减一。
			   contentList.add(String.valueOf(allChar[position]));
			   count--;
			  }
			  // 获取到了指定数目的不同字符，循环输出
			  for (int i = 0; i < contentList.size(); i++){
				   //System.out.println(contentList.get(i));
				  sb.append(contentList.get(i)); 
			  }
			  
			  return sb.toString();
			  
		}catch (Exception e) {
			e.printStackTrace();
			return sb.toString();
		}
	}
	
	
	/**
     * 删除今天以前的文件夹 
     * @param String path 文件夹存放路径
     * @return void 
     */
	public static void deleteFile(String path){
		try{
			File fileRoot = new File(path);
			if(fileRoot.exists()){
				File fileAll[] = fileRoot.listFiles();
				for(File file : fileAll){  
					if(file.isDirectory()){
						if(file.getName().startsWith("2")){
							String nowDate = CountUtil.getNowDayDate();
							if(!file.getName().equals(nowDate)){ 
								FileOperation.deleteDir(file.getPath());
							}
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//得到开始时间的完整格式
	public static String getTimeS(String s){
		String time = "";
		try{
			time = s.trim() + " 00:00:01";
			return time;
		}catch (Exception e) {
			e.printStackTrace();
			return time;
		}
	}
	
	//得到开始时间的完整格式
	public static String getTimeE(String s){
		String time = "";
		try{
			time = s.trim() + " 59:59:59";
			return time;
		}catch (Exception e) {
			e.printStackTrace();//
			return time;
		}
	}
	
	public static void main(String str[]){
		System.out.println(getNowDayDate());
		//deleteFile("G:\\test");
	}
	
	
	
}
