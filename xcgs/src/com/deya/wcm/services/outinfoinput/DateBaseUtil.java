package com.deya.wcm.services.outinfoinput;

public class DateBaseUtil {

	 //根据不同的数据库来得到相对应的driverClass
	public static String getDriverClassByType(String dbType){
		String result = "";
		try{

			if (dbType.equals("mysql"))
				result = "com.mysql.jdbc.Driver";
	        else if (dbType.equals("oracle")) {
	        	result = "oracle.jdbc.driver.OracleDriver";
	        }
			
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}
	
	  //根据不同的数据库连接地址来得到相对应的 数据库名字
	  public static String getDBtypeByUrl(String url)
	  {
	    try
	    {
	      if (url.contains("jdbc:mysql:"))
	        return "mysql";
	      if (url.contains("jdbc:oracle:thin:")) {
	        return "oracle";
	      }
	      return "";
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace(); 
	    }
	    return "";
	  }
	  
	  
	  //根据不同的数据库连接地址来得到相对应的driverClass
	  public static String getDriverClassByUrl(String url)
	  {
		  return getDriverClassByType(getDBtypeByUrl(url));
	  }
	  
	  //根据不同的数据库连接地址来得到相对应的 数据库名称
	  public static String getDatebaseNameByUrl(String url)
	  {
	    try
	    {
	      int lastInt;
	      if (url.contains("jdbc:mysql:")) {
	        lastInt = url.lastIndexOf("/");
	        return url.substring(lastInt + 1); }
	      if (url.contains("jdbc:oracle:thin:")) {
	        lastInt = url.lastIndexOf(":");
	        return url.substring(lastInt + 1);
	      }
	      return "";
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace(); }
	    return "";
	  }
	  
	  
	  public static void main(String arr[]){
		  String url = "jdbc:mysql://127.0.0.1:3306/myorg";
		  System.out.println(getDatebaseNameByUrl(url));
	  }
	
}
