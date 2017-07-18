<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@ page import="java.io.File" %>

<%
String rootpath = request.getSession().getServletContext().getRealPath("");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>文件转换</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
  </head>
<body>
<%
    VsbToFile(rootpath);
%>
<%!
	public void VsbToFile(String rootpath){
		try{
			String driverClassName ="org.postgresql.Driver";
			String userName = "vsb_pgsql";
			String userPasswd = "webber";
			String dbName="vsb9";
			String url = "jdbc:postgresql://192.168.4.13:5432/" + dbName;
			Class.forName(driverClassName);
			Connection conn = DriverManager.getConnection(url, userName, userPasswd);
			System.out.println("conn");
			System.out.println("db connecting");
			Statement stmt = conn.createStatement();


			int totle = 0;
			int index = 1;
			Statement stmtCount = conn.createStatement();

			String sql = "select replace(wbfilepath,'${ownername}','lwqxxb') as filepath,wbfileext from wbfileinfo where wbfilepath like '%.vsb%' and owner = 986853575" ;

			String sqlCount = "select count(wbfilepath) as totle from wbfileinfo where wbfilepath like '%.vsb%' and owner = 986853575";

			ResultSet rsCount = stmtCount.executeQuery(sqlCount);
			while(rsCount.next()) {
				String totleStr = rsCount.getString("totle");
				if(totleStr != null && !"".equals(totleStr)){
					totle = Integer.parseInt(totleStr);
				}
			}

			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String filepath  = rs.getString("filepath");//原始文件路径
                String wbfileext  = rs.getString("wbfileext");//原始文件后缀
                filepath = rootpath+ filepath;
                String newpath = filepath.substring(0,filepath.lastIndexOf("."))+wbfileext;

                File oldfile=new File(filepath);
                File newfile=new File(newpath);
                if(!oldfile.exists()){
                    System.out.println(filepath+"不存在！");
                    System.out.println("转换失败：文件不存在,总共"+ totle +"条，当前第" + index + "条" );
                }else{
                    if(newfile.exists()){//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                        System.out.println(newpath+"已经存在！");
                        System.out.println("转换失败：目标文件名已存在,总共"+ totle +"条，当前第" + index + "条" );
                    } else{
                        System.out.println("原始文件路径："+ filepath);
                        System.out.println("新　文件路径："+ newpath);
                        oldfile.renameTo(newfile);
                        System.out.println("转换成功，总共"+ totle +"条，当前第" + index + "条" );
                    }
                }
                index ++;
            }
		}catch(Exception e){
			System.out.print(e);
		}
	}
%>
</body>
</html>
