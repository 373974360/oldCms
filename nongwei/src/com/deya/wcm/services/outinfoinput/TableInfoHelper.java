package com.deya.wcm.services.outinfoinput;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableInfoHelper
{
  Connection con;
  String url;

  public void initCon(String url, String user, String password)
  {
    try
    {
        this.url = url;
        String driverClass = DateBaseUtil.getDriverClassByUrl(url);
        Class.forName(driverClass);
        this.con = DriverManager.getConnection(url, user, password);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Connection create Error");
    }
  }

  public String getTables()
  {
    try {
      StringBuilder sb = new StringBuilder();
      DatabaseMetaData dmd = this.con.getMetaData();
      ResultSet rs = dmd.getTables(DateBaseUtil.getDatebaseNameByUrl(this.url), null, null, 
        new String[] { "TABLE"});
      while (rs.next()) {
        sb.append(rs.getString("TABLE_NAME") + ",");
      }
      rs.close();

      return sb.toString().toLowerCase();
    }
    catch (Exception e) {
      e.printStackTrace(); }
    return "";
  }




  public String getFields(String table)
  {
    try
    {
      StringBuilder sb = new StringBuilder();
      //StringBuilder sbtype = new StringBuilder();
      Statement ps = null;
      try {
        ps = this.con.createStatement();
        ResultSet rs = ps.executeQuery("select * from " + table);
        ResultSetMetaData rsm = rs.getMetaData();
        int a = rsm.getColumnCount();
        //System.out.println(a);

        for (int i = 1; i <= a; ++i) {
          String column = rsm.getColumnName(i);
          String columnType = rsm.getColumnTypeName(i);
          sb.append(column);
          //sbtype.append(columnType);
          if(i!=a){
        	  sb.append(",");
        	  //sbtype.append(",");
          }
          
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (ps != null)
          try {
            ps.close();
          }
          catch (Exception localException2) {
          }
      }
      return sb.toString().toLowerCase();
    } catch (Exception e) {
      e.printStackTrace(); }
    return "";
  }
  
  
  public List<Map> getFieldsToListMap(String table)
  {
	List<Map> list = new ArrayList<Map>();
    try
    {
      Statement ps = null;
      try {
        ps = this.con.createStatement();
        ResultSet rs = ps.executeQuery("select * from " + table);
        ResultSetMetaData rsm = rs.getMetaData();
        int a = rsm.getColumnCount();
        //System.out.println(a);

        for (int i = 1; i <= a; ++i) {
          Map map = new HashMap();
          String column = rsm.getColumnName(i);
          String columnType = rsm.getColumnTypeName(i);
          map.put("column", column);
          map.put("columnType", columnType);
          list.add(map);
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (ps != null)
          try {
            ps.close();
          }
          catch (Exception localException2) {
          }
      }
      return list;
    } catch (Exception e) {
        e.printStackTrace(); }
    	return list;
  }

  public static void main(String[] args)
  {
    TableInfoHelper tableInfoHelper = new TableInfoHelper();
    tableInfoHelper.initCon("jdbc:oracle:thin:@192.168.12.18:1521:orcl", "cicrop", "cicrop");
    String str = tableInfoHelper.getTables();
    List<String> list = Arrays.asList(str.split(","));
    for(String name : list){
    	   if(name.startsWith("cp_") || name.startsWith("cs_") ){
    		   System.out.println("--------------------------"+name+"-----------------------------");
    	   }
//    	   String fieldNames = tableInfoHelper.getFields(name); 
//    	   List<String> listFields = Arrays.asList(fieldNames.split(","));
//    	   for(String fieldName : listFields){
//    		   System.out.println(fieldName);
//    	   }
    }
  }
}