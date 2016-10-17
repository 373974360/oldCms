package com.deya.wcm.services.outinfoinput;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleResultSet;
import oracle.sql.CLOB;

public class DBase {
	  
//	       static String driverClass = "com.mysql.jdbc.Driver"; 
//		   static String url = "jdbc:mysql://127.0.0.1:3306/myorg"; 
//		   static String user = "root"; 
//		   static String pwd = "pwd";
	
//		   static String url = "jdbc:mysql://192.168.12.125:3306/cicro?useUnicode=true&characterEncoding=utf8"; 
//		   static String user = "root"; 
//		   static String pwd = "mysql";
	      
		   String url = "jdbc:mysql://192.168.12.125:3306/cicro"; 
		   String user = "root"; 
		   String pwd = "mysql";
		   String driverClass = "com.mysql.jdbc.Driver"; 
		   
		   public DBase(String url,String user,String pwd){
			   this.url = url;
			   this.user = user;
			   this.pwd = pwd;
			   this.driverClass = DateBaseUtil.getDriverClassByUrl(url);
		   }
	
	       public Connection getConnection(){
			   Connection conn = null;
		       try {  
		           //装载驱动类  
		           Class.forName(driverClass);  
		       } catch (ClassNotFoundException e) {  
		           System.out.println("装载驱动异常!");   
		           e.printStackTrace();  
		       }  
		       try {  
		           //建立jdbc连接  
		           conn = DriverManager.getConnection(url,user,pwd);  
		           return conn;
		       } catch (Exception e) {   
		           System.out.println("链接数据库异常!");  
		           e.printStackTrace();  
		           return conn;
		       }  
		   }  
		   
	       //得到所有的信息列表
	       public List<Map> getListAll(Map map,String sql){
	    	      System.out.println(map);
	    	   	  List<Map> listResult = new ArrayList<Map>();
	    	   	  Connection conn = null;
				   Statement stmt = null;
				   ResultSet rs = null;
	    	   	   try{
	    	   		   //创建一个jdbc声明  
			    	   conn = getConnection();
			           stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); 
			           System.out.println(sql);
			           rs = stmt.executeQuery(sql);  
			           while (rs.next()) {
			        	   Map map1 = new HashMap();
			        	   Iterator it = map.keySet().iterator();
			        	   while(it.hasNext()){
			        		   String column = (String)it.next();
			        		   String columnType = (String)map.get(column);
			        		   //System.out.println("columnType = " + columnType);
			        		   String val = "";
			        		   if(columnType.toLowerCase().contains("clob")){
			        			   CLOB clob = ((OracleResultSet)rs).getCLOB(column);
					               if(clob!=null){
						               Reader is=clob.getCharacterStream();
						               BufferedReader br=new BufferedReader(is);
						               String s=br.readLine();
						               while(s!=null){
						            	   val+=s;
							               s=br.readLine();
						               }
					               }
			        		   }else{
			        			   val = rs.getString(column);
			        		   }
			        		   map1.put(column, val);
			        	   }
			        	   listResult.add(map1);
			           }
	    	    	  return listResult;
	    	      }catch (Exception e) {
					e.printStackTrace();
					return listResult;
				}finally {  
			           //预防性关闭连接（避免异常发生时在try语句块关闭连接没有执行）  
			           try {  
			        	   if (rs != null) rs.close();
			        	   if (stmt != null) stmt.close();
			               if (conn != null) conn.close(); 
			           } catch (Exception e) {  
			        	   e.printStackTrace(); 
			           }  
			       }
	       }
	       
	       public void addInfo(Map mapFields,Map mapInfo,String tableName){
	    	   Connection conn = null;
			   PreparedStatement ps = null;
			   ResultSet rs = null;
	    	   try{
	    		   
	    		    StringBuffer sbFields = new StringBuffer();
	    		    StringBuffer sbValues = new StringBuffer();
	    		    StringBuffer sbValues2 = new StringBuffer();
	    		    Iterator it = mapFields.keySet().iterator();
	        	    while(it.hasNext()){
	        		   String column = (String)it.next();
	        		   String columnType = (String)mapFields.get(column);
	        		   String value = "";
	        		   sbFields.append(column+",");
	        		   sbValues2.append("?,");
	        		   if(columnType.toLowerCase().contains("varchar")){
	        			   value = (String)mapInfo.get(column);
	        			   sbValues.append("'"+value+"',");
	        		   }else if(columnType.toLowerCase().contains("int")){
	        			   value = (String)mapInfo.get(column);
	        			   sbValues.append(""+value+",");
	        		   }
	        	    }
	        	    String sbFieldsStr = sbFields.toString().trim();
	        	    sbFieldsStr = sbFieldsStr.substring(0, sbFieldsStr.length()-1);
	        	    String sbValuesStr = sbValues.toString().trim();
	        	    sbValuesStr = sbValuesStr.substring(0, sbValuesStr.length()-1);
	        	    String sbValuesStr2 = sbValues2.toString().trim();
	        	    sbValuesStr2 = sbValuesStr2.substring(0, sbValuesStr2.length()-1);
	        	    
	    		    String sql = "insert into " + tableName +"( "+sbFieldsStr+" ) values( " ;
	    		    sql += sbValuesStr2+")";
	    		    //sql += " )";

	    		    conn = getConnection();
	    		    System.out.println(sql);
				    ps = conn.prepareStatement(sql);
				    
				    Iterator it2 = mapFields.keySet().iterator();
				    int mun = 1;
	        	    while(it2.hasNext()){
	        		   String column = (String)it2.next();
	        		   String columnType = (String)mapFields.get(column);
	        		   String value = "";
	        		   if(columnType.toLowerCase().contains("varchar")){
	        			   value = (String)mapInfo.get(column);
	        			   if(value==null){
	        				   value = "";
	        			   }
	        			   ps.setString(mun,value);
	        			   //System.out.println("varchar="+value);
	        		   }else if(columnType.toLowerCase().contains("int")){
	        			   value = (String)mapInfo.get(column);
	        			   if(value==null){
	        				   value = "0";
	        			   }
	        			   ps.setInt(mun,Integer.parseInt(value));
	        			   //System.out.println("int="+value);
	        		   }else{
	        			   value = (String)mapInfo.get(column);
	        			   if(value==null){
	        				   value = "";
	        			   }
	        			   ps.setString(mun,value);
	        			   //System.out.println("varchar="+value);
	        		   }
	        		   mun++;
	        	    }
				    ps.executeUpdate();
	    		    
				    
	    	   }catch (Exception e) {
				   e.printStackTrace();
			   }finally {  
		           //预防性关闭连接（避免异常发生时在try语句块关闭连接没有执行）  
		           try {  
		        	   if (rs != null) rs.close();  
		        	   if (ps != null) ps.close();  
		               if (conn != null) conn.close();  
		           } catch (Exception e) {  
		        	   e.printStackTrace(); 
		           }  
		       }
	       }
	       
	       
	       public void deleteDataByTableName(String tableName){
	    	   Connection conn = null;
			   PreparedStatement ps = null;
			   Statement stmt = null;
			   ResultSet rs = null;
	    	   try{
	    		    String sql = "delete from " + tableName;
	    		    conn = getConnection();
	    		    System.out.println(sql);
	    		    
	    		    ///////////////////////////
//				    ps = conn.prepareStatement(sql);
//				    rs = ps.executeQuery();
	    		    /////////////////////
	    		    stmt = conn.createStatement();
	    		    stmt.executeUpdate(sql);
	    		    /////////////////////////

				    
	    	   }catch (Exception e) {
	    		   e.printStackTrace();
			   }finally {  
		           //预防性关闭连接（避免异常发生时在try语句块关闭连接没有执行）  
		           try {  
		        	   if (rs != null) rs.close();  
		        	   if (ps != null) ps.close();  
		               if (conn != null) conn.close();  
		           } catch (Exception e) {  
		        	   e.printStackTrace(); 
		           }  
		       }
	       }
	       
	       
	       
	       //删除表中数据或添加数据的时候一张表只连接一次数据库
	       Connection connall = null;
     	   public void getConnectionOneTime(){
		       try {  
		           //装载驱动类  
		           Class.forName(driverClass);  
		       } catch (ClassNotFoundException e) {  
		           System.out.println("装载驱动异常!");   
		           e.printStackTrace();  
		       }  
		       try {  
		           //建立jdbc连接  
		    	   connall = DriverManager.getConnection(url,user,pwd);  
		       } catch (Exception e) {   
		           System.out.println("链接数据库异常!");  
		           e.printStackTrace();  
		       }  
		   }
     	   
     	   public void closeConnectionOneTime(){
     		      try {  
     	              if (connall != null) connall.close();  
     	          } catch (Exception e) {  
     	       	   e.printStackTrace(); 
     	          } 
     	   }
     	  
	       //删除表中数据或添加数据的时候一张表只连接一次数据库
	       public void addInfoOneTime(Map mapFields,Map mapInfo,String tableName){
	    	   PreparedStatement ps = null;
			   ResultSet rs = null;
	    	   try{
	    		   
	    		    StringBuffer sbFields = new StringBuffer();
	    		    StringBuffer sbValues = new StringBuffer();
	    		    StringBuffer sbValues2 = new StringBuffer();
	    		    Iterator it = mapFields.keySet().iterator();
	        	    while(it.hasNext()){
	        		   String column = (String)it.next();
	        		   String columnType = (String)mapFields.get(column);
	        		   String value = "";
	        		   sbFields.append(column+",");
	        		   sbValues2.append("?,");
	        		   if(columnType.toLowerCase().contains("varchar")){
	        			   value = (String)mapInfo.get(column);
	        			   sbValues.append("'"+value+"',");
	        		   }else if(columnType.toLowerCase().contains("int") || columnType.toLowerCase().contains("number")){
	        			   value = (String)mapInfo.get(column);
	        			   sbValues.append(""+value+",");
	        		   }
	        	    }
	        	    String sbFieldsStr = sbFields.toString().trim();
	        	    sbFieldsStr = sbFieldsStr.substring(0, sbFieldsStr.length()-1);
	        	    String sbValuesStr = sbValues.toString().trim();
	        	    sbValuesStr = sbValuesStr.substring(0, sbValuesStr.length()-1);
	        	    String sbValuesStr2 = sbValues2.toString().trim();
	        	    sbValuesStr2 = sbValuesStr2.substring(0, sbValuesStr2.length()-1);
	        	    
	    		    String sql = "insert into " + tableName +"( "+sbFieldsStr+" ) values( " ;
	    		    sql += sbValuesStr2+")";
	    		    //sql += " )";

	    		    //conn = getConnection();
	    		    //System.out.println(sql);
				    ps = connall.prepareStatement(sql);
				    
				    Iterator it2 = mapFields.keySet().iterator();
				    int mun = 1;
	        	    while(it2.hasNext()){
	        		   String column = (String)it2.next();
	        		   String columnType = (String)mapFields.get(column);
	        		   String value = "";
	        		   if(columnType.toLowerCase().contains("varchar")){
	        			   value = (String)mapInfo.get(column);
	        			   if(value==null){
	        				   value = "";
	        			   }
	        			   ps.setString(mun,value);
	        			   //System.out.println("varchar="+value);
	        		   }else if(columnType.toLowerCase().contains("int")){
	        			   value = (String)mapInfo.get(column);
	        			   if(value==null){
	        				   value = "0";
	        			   }
	        			   ps.setInt(mun,Integer.parseInt(value));
	        			   //System.out.println("int="+value);
	        		   }else{
	        			   value = (String)mapInfo.get(column);
	        			   if(value==null){
	        				   value = "";
	        			   }
	        			   ps.setString(mun,value);
	        			   //System.out.println("varchar="+value);
	        		   }
	        		   mun++;
	        	    }
				    ps.executeUpdate();
	    		    
				    
	    	   }catch (Exception e) {
				   e.printStackTrace();
			   }finally {  
		           //预防性关闭连接（避免异常发生时在try语句块关闭连接没有执行）  
		           try {  
		        	   if (rs != null) rs.close();  
		        	   if (ps != null) ps.close();  
		           } catch (Exception e) {  
		        	   e.printStackTrace(); 
		           }  
		       }
	       }
	       
	    
	       public void deleteDataByTableNameOneTime(String tableName){
			   PreparedStatement ps = null;
			   Statement stmt = null;
			   ResultSet rs = null;
	    	   try{
	    		    String sql = "delete from " + tableName;
	    		    connall = getConnection();
	    		    System.out.println(sql);
	    		    
	    		    ///////////////////////////
//				    ps = conn.prepareStatement(sql);
//				    rs = ps.executeQuery();
	    		    /////////////////////
	    		    stmt = connall.createStatement();
	    		    stmt.executeUpdate(sql);
	    		    /////////////////////////

				    
	    	   }catch (Exception e) {
	    		   e.printStackTrace();
			   }finally {  
		           //预防性关闭连接（避免异常发生时在try语句块关闭连接没有执行）  
		           try {
		        	   if (rs != null) rs.close();  
		        	   if (ps != null) ps.close();  
		           } catch (Exception e) {  
		        	   e.printStackTrace(); 
		           }  
		       }
	       }
	      
}
