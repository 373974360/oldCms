package com.deya.wcm.services.outinfoinput;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableInfoOut {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String url = "jdbc:mysql://192.168.12.110:3306/cicrop"; 
	    String user = "root"; 
	    String pwd = "mysql";
	    
	    String urlin = "jdbc:oracle:thin:@192.168.12.18:1521:orcl"; 
	    String userin = "cicrop"; 
	    String pwdin = "cicrop";
		
//		String url = "jdbc:oracle:thin:@192.168.12.18:1521:orcl"; 
//	    String user = "cicrop";
//	    String pwd = "cicrop";
		
//	    String url = "jdbc:mysql://127.0.0.1:3306/cicro"; 
//	    String user = "root"; 
//	    String pwd = "mysql";
	    
//		String urlin = "jdbc:mysql://192.168.12.110:3306/cicrop"; 
//	    String userin = "root"; 
//	    String pwdin = "mysql";
 
	    inputAllInfo(url, user, pwd, urlin, userin, pwdin);
		
	    //inputAllInfoByTableNames("cs_org_role_opt", url, user, pwd, urlin, userin, pwdin);
	}
	 
	
	//导入所有的表数据
	public static void inputAllInfo(String url,String user,String pwd,String urlin,String userin,String pwdin){
		try{
			
			TableInfoHelper tableInfoHelper = new TableInfoHelper();
		    tableInfoHelper.initCon(url, user, pwd);
		    String str = tableInfoHelper.getTables();
		    List<String> list = Arrays.asList(str.split(","));
		    for(String name : list){
		    	   if(!name.startsWith("cp_") && !name.startsWith("cs_") ){
		    		   continue;
		    	   }
		    	   System.out.println("--------------------------"+name+"-----------------------------");
		    	   StringBuffer sbFieldNames = new StringBuffer(""); 
		    	   List<Map> fieldMap = tableInfoHelper.getFieldsToListMap(name); 
		    	   if(fieldMap.size()<=0){
		    		   continue;
		    	   }
		    	   Map mapCol = new HashMap();
		    	   //System.out.println(fieldMap.size());
		    	   for(Map map : fieldMap){
		    		   String column = (String)map.get("column");
		    		   String columnType = (String)map.get("columnType");
		    		   sbFieldNames.append(column + ",");
		    		   mapCol.put(column, columnType);
		    		   //System.out.println(map);
		    	   }
		    	   
		    	   String fieldNames = sbFieldNames.toString().trim();
		    	   fieldNames = fieldNames.substring(0, fieldNames.length()-1);
		    	   //得到该表中的所有信息
		    	   String sql = "select " + fieldNames + " from " + name ;
		    	   
		    	   DBase dBase = new DBase(url, user, pwd);
		    	   List<Map> listResult = dBase.getListAll(mapCol, sql);
		    	   
		    	   //插入新表
		    	   DBase dBase2 = new DBase(urlin, userin, pwdin);
		    	   dBase2.getConnectionOneTime();
		    	   dBase2.deleteDataByTableNameOneTime(name);
		    	   for(Map map : listResult){
		    		   dBase2.addInfoOneTime(mapCol, map, name);
		    	   }
		    	   dBase2.closeConnectionOneTime();
		    }
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	//导入某几张表
	public static void inputAllInfoByTableNames(String tableNames,String url,String user,String pwd,String urlin,String userin,String pwdin){
		try{
			TableInfoHelper tableInfoHelper = new TableInfoHelper();
			tableInfoHelper.initCon(url, user, pwd);
			List<String> listNames = Arrays.asList(tableNames.split(","));
			for(String name : listNames){
				if(name!=null && !"".equals(name)){
					System.out.println("--------------------------"+name+"-----------------------------");
			    	   StringBuffer sbFieldNames = new StringBuffer(""); 
			    	   List<Map> fieldMap = tableInfoHelper.getFieldsToListMap(name); 
			    	   Map mapCol = new HashMap();
			    	   //System.out.println(fieldMap.size());
			    	   for(Map map : fieldMap){
			    		   String column = (String)map.get("column");
			    		   String columnType = (String)map.get("columnType");
			    		   sbFieldNames.append(column + ",");
			    		   mapCol.put(column, columnType);
			    		   //System.out.println(map);
			    	   }
			    	   
			    	   String fieldNames = sbFieldNames.toString().trim();
			    	   fieldNames = fieldNames.substring(0, fieldNames.length()-1);
			    	   //得到该表中的所有信息
			    	   String sql = "select " + fieldNames + " from " + name ;
			    	   
			    	   DBase dBase = new DBase(url, user, pwd);
			    	   List<Map> listResult = dBase.getListAll(mapCol, sql);
			    	   
			    	   //插入新表
			    	   DBase dBase2 = new DBase(urlin, userin, pwdin);
			    	   dBase2.getConnectionOneTime();
			    	   dBase2.deleteDataByTableNameOneTime(name);
			    	   for(Map map : listResult){
			    		   dBase2.addInfoOneTime(mapCol, map, name);
			    	   }
			    	   dBase2.closeConnectionOneTime();
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
