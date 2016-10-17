<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.deya.util.FormatUtil,com.deya.wcm.bean.cms.info.*,com.deya.wcm.services.cms.info.*" %>
<%@page import="com.deya.wcm.bean.appeal.model.*,com.deya.wcm.services.appeal.model.*" %>
<%@page import="com.deya.wcm.bean.appeal.sq.*,com.deya.wcm.services.appeal.sq.*" %>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager" %>
<%@page import="java.io.*" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>导数据</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
</head>

<body>
<%
    CmsToCms(2, 10013);
    CmsToCms(4, 10009);
    CmsToCms(5, 10009);

    CmsToCms(6, 10009);
    CmsToCms(8, 10005);

    CmsToCms(9, 10008);
    CmsToCms(10, 10012);
    CmsToCms(11, 10005);
    CmsToCms(12, 10014);

    CmsToCms(15, 10022);
    CmsToCms(16, 10022);
    CmsToCms(17, 10021);
    CmsToCms(27, 10015);

    CmsToCms(28, 10015);
    CmsToCms(31, 10016);
    CmsToCms(32, 10016);
    CmsToCms(37, 10014);
    CmsToCms(38, 10022);

    CmsToLink(19, 10023);
    CmsToLink(20, 10019);
    CmsToLink(22, 10010);
    CmsToLink(23, 10010);
    CmsToLink(25, 10020);

    CmsToPic(29, 10017);
    CmsToPic(33, 10030);
    CmsToPic(36, 10014);
    CmsToCms2("图片新闻", 10001);
    CmsToCms2("信息日报", 10004);
    CmsToCms2("公告栏", 10002);


%>


<%!

    public void CmsToCms(int channl_id, int new_cat_id) {
        Connection conn = null;
        Statement stmt = null;
        Statement stmtCount = null;
        ResultSet rs = null;
        ResultSet rsCount = null;

        try {
            String site_id = "CMStlhg";

		  /*    Mysql  */
            String driverClassName = "com.mysql.jdbc.Driver";
            String userName = "root"; //登录用户名
            String userPasswd = "dyt@88352636"; //登录密码
            String dbName = "olddata";    //数据库名
            String url = "jdbc:mysql://localhost:3306/" + dbName + "?user=" + userName + "&password=" + userPasswd;
            Class.forName(driverClassName).newInstance();
            conn = DriverManager.getConnection(url);
            System.out.println("数据库连接成功");
            stmt = conn.createStatement();
		  

		  /*     Sqlserver  	
		  String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; 
		  String userName = "sa"; //登录用户名
		  String userPasswd = "zz?7231061"; //登录密码
		  String dbName="zizhougov";	//数据库名	
		  String url = "jdbc:sqlserver://10.119.80.100:1433;databaseName=" + dbName; 
		  Class.forName(driverClassName);
		  Connection conn = DriverManager.getConnection(url, userName, userPasswd);
		  System.out.println("数据库连接成功");
		  Statement stmt = conn.createStatement();
		  */
		  
		  /*     Oracle	
		  String driverClassName = "oracle.jdbc.driver.OracleDriver"; 
		  String userName = "webdb"; //登录用户名
		  String userPasswd = "webdb"; //登录密码
		  String dbName="orcl";	//数据库名	
		  String url = "jdbc:oracle:thin:@10.32.1.22:1521:" + dbName; 
		  Class.forName(driverClassName);
		  Connection conn = DriverManager.getConnection(url, userName, userPasswd);
		  System.out.println("数据库连接成功");
		  Statement stmt = conn.createStatement();
		  */

		  /*     Access		
		  String driverClassName = "sun.jdbc.odbc.JdbcOdbcDriver"; 
		  String url = "jdbc:odbc:oldData";     
		  Class.forName(driverClassName);
		  Connection conn = DriverManager.getConnection(url);
		  System.out.println("数据库连接成功");
		  Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		  */

            int totle = 0;
            int index = 1;
            stmtCount = conn.createStatement();

            String sql = "select id,title,classid,content,myaddtime from article where classid = " + channl_id;

            String sqlCount = "select count(*) as totle from article where classid = " + channl_id;

            rsCount = stmtCount.executeQuery(sqlCount);
            while (rsCount.next()) {
                String totleStr = rsCount.getString("totle");
                if (totleStr != null && !"".equals(totleStr)) {
                    totle = Integer.parseInt(totleStr);
                }
            }


            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String focus_pic = "";
                String publish_time = rs.getString("myaddtime");
                String publish_flag = "1";
                String clickcount = "0";
                String publish_user = "1";
                String article_id = rs.getString("id");
                String title = rs.getString("title");
                String top_title = "";
                String subtitle = "";
                String source_name = "";
                String author = "";
                String editor = "";
                String content = rs.getString("content");
                String keywords = "";
                String add_time = rs.getString("myaddtime");
                String add_user = "1";
                String status = "";
                String words_count = "";
                String add_user_name = "";
                String down = "";

                ArticleBean article = new ArticleBean();
                article.setInput_user(1);
                if ("1".equals(publish_flag)) {
                    article.setInfo_status(8);
                } else {
                    article.setInfo_status(4);
                }
                article.setModel_id(11);
                article.setApp_id("cms");
                article.setSite_id(site_id);

                int id = InfoBaseManager.getNextInfoId();
                article.setId(id);
                article.setInfo_id(id);
                article.setCat_id(new_cat_id);
                article.setGk_index("33-44-55-66");
                article.setGk_duty_dept("同乐海关");
                article.setGk_input_dept("同乐海关");
                if (title != null && !"".equals(title) && !"null".equals(title)) {
                    article.setTitle(title);
                } else {
                    article.setTitle("");
                }
                if (clickcount != null && !"".equals(clickcount) && !"null".equals(clickcount)) {
                    article.setHits(Integer.parseInt(clickcount));
                } else {
                    article.setHits(0);
                }
                if (subtitle != null && !"".equals(subtitle) && !"null".equals(subtitle)) {
                    article.setSubtitle(subtitle);
                } else {
                    article.setSubtitle("");
                }
                if (keywords != null && !"".equals(keywords) && !"null".equals(keywords)) {
                    article.setTags(keywords);
                } else {
                    article.setTags("");
                }
                if (author != null && !"".equals(author) && !"null".equals(author)) {
                    article.setAuthor(author);
                } else {
                    article.setAuthor("");
                }
                if (source_name != null && !"".equals(source_name) && !"null".equals(source_name)) {
                    article.setSource(source_name);
                } else {
                    article.setSource("");
                }
                if (add_user_name != null && !"".equals(add_user_name) && !"null".equals(add_user_name)) {
                    article.setEditor(add_user_name);
                } else {
                    article.setEditor("");
                }

                if (content != null && !"".equals(content) && !"null".equals(content)) {
                    String result = "";
                    /*String dir = "F:\\tongleweb\\thenews\\text\\";
                    File fp = new File(dir, content + ".asp");
                    FileReader freader = new FileReader(fp);
                    BufferedReader bfdreader = new BufferedReader(freader);
                    String str_line = bfdreader.readLine();
                    while (str_line != null) {
                        result += str_line;
                        str_line = bfdreader.readLine();
                    }
                    bfdreader.close();
                    freader.close();
                    */
                    content = result;
                }
                if (focus_pic != null && !"".equals(focus_pic) && !"null".equals(focus_pic)) {
                    article.setThumb_url(focus_pic);
                } else {
                    article.setThumb_url("");
                }
                article.setInfo_content(content);
                article.setDescription("");
                if (add_time != null && !"".equals(add_time) && add_time.length() > 20) {
                    add_time = add_time.substring(0, add_time.length() - 2);
                }
                if (publish_time != null && !"".equals(publish_time) && publish_time.length() > 20) {
                    publish_time = publish_time.substring(0, publish_time.length() - 2);
                }
                if ("null".equals(add_time) || add_time == null || "".equals(add_time)) {
                    article.setInput_dtime("2015-07-31 11:31:00");
                } else {
                    article.setInput_dtime(add_time);
                }
                if ("null".equals(publish_time) || publish_time == null || "".equals(publish_time)) {
                    article.setReleased_dtime(publish_time);
                } else {
                    article.setReleased_dtime(publish_time);
                }

                if (ModelUtil.insert(article, "article", null)) {
                    System.out.println("信息添加成功，信息ID为：" + article_id + ",总共" + totle + "条，当前第" + index + "条");
                    index++;
                } else {
                    System.out.println("信息添加失败，信息ID为：" + article_id + ",总共" + totle + "条，当前第" + index + "条");
                }
            }

        } catch (Exception e) {
            System.out.print(e);

        } finally {

        }
    }


%>

<%!

    public void CmsToLink(int channl_id, int new_cat_id) {
        Connection conn = null;
        Statement stmt = null;
        Statement stmtCount = null;
        ResultSet rs = null;
        ResultSet rsCount = null;
        try {
            String site_id = "CMStlhg";

		  /*    Mysql  */
            String driverClassName = "com.mysql.jdbc.Driver";
            String userName = "root"; //登录用户名
            String userPasswd = "dyt@88352636"; //登录密码
            String dbName = "olddata";    //数据库名
            String url = "jdbc:mysql://localhost:3306/" + dbName + "?user=" + userName + "&password=" + userPasswd;
            Class.forName(driverClassName).newInstance();
            conn = DriverManager.getConnection(url);
            System.out.println("数据库连接成功");
            stmt = conn.createStatement();


		  /*     Sqlserver
		  String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		  String userName = "sa"; //登录用户名
		  String userPasswd = "zz?7231061"; //登录密码
		  String dbName="zizhougov";	//数据库名
		  String url = "jdbc:sqlserver://10.119.80.100:1433;databaseName=" + dbName;
		  Class.forName(driverClassName);
		  Connection conn = DriverManager.getConnection(url, userName, userPasswd);
		  System.out.println("数据库连接成功");
		  Statement stmt = conn.createStatement();
		  */

		  /*     Oracle
		  String driverClassName = "oracle.jdbc.driver.OracleDriver";
		  String userName = "webdb"; //登录用户名
		  String userPasswd = "webdb"; //登录密码
		  String dbName="orcl";	//数据库名
		  String url = "jdbc:oracle:thin:@10.32.1.22:1521:" + dbName;
		  Class.forName(driverClassName);
		  Connection conn = DriverManager.getConnection(url, userName, userPasswd);
		  System.out.println("数据库连接成功");
		  Statement stmt = conn.createStatement();
		  */

		  /*     Access
		  String strDirPath=getServletContext().getRealPath("/");
		  strDirPath=strDirPath.replace('\\','/');
		  String driverClassName = "com.hxtt.sql.access.AccessDriver";
		  String url = "jdbc:access:///"+strDirPath+"//oldData.mdb";
		  Class.forName(driverClassName);
		  Connection conn = DriverManager.getConnection(url);
		  System.out.println("数据库连接成功");
		  Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		  */

            int totle = 0;
            int index = 1;
            stmtCount = conn.createStatement();

            String sql = "select id,title,classid,content,myaddtime,downurl from down where classid = " + channl_id;

            String sqlCount = "select count(*) as totle from down where classid = " + channl_id;

            rsCount = stmtCount.executeQuery(sqlCount);
            while (rsCount.next()) {
                String totleStr = rsCount.getString("totle");
                if (totleStr != null && !"".equals(totleStr)) {
                    totle = Integer.parseInt(totleStr);
                }
            }


            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String focus_pic = "";
                String publish_time = rs.getString("myaddtime");
                String publish_flag = "1";
                String clickcount = "0";
                String publish_user = "1";
                String article_id = rs.getString("id");
                String title = rs.getString("title");
                String top_title = "";
                String subtitle = "";
                String source_name = "";
                String author = "";
                String editor = "";
                String content = rs.getString("downurl");
                String keywords = "";
                String add_time = rs.getString("myaddtime");
                String add_user = "1";
                String status = "";
                String words_count = "";
                String add_user_name = "";
                String down = "";

                GKInfoBean article = new GKInfoBean();
                article.setInput_user(1);
                if ("1".equals(publish_flag)) {
                    article.setInfo_status(8);
                } else {
                    article.setInfo_status(4);
                }
                article.setModel_id(12);
                article.setApp_id("cms");
                article.setSite_id(site_id);

                int id = InfoBaseManager.getNextInfoId();
                article.setId(id);
                article.setInfo_id(id);
                article.setCat_id(new_cat_id);
                article.setGk_index("33-44-55-66");
                article.setGk_duty_dept("同乐海关");
                article.setGk_input_dept("同乐海关");
                if (title != null && !"".equals(title) && !"null".equals(title)) {
                    article.setTitle(title);
                } else {
                    article.setTitle("");
                }
                if (clickcount != null && !"".equals(clickcount) && !"null".equals(clickcount)) {
                    article.setHits(Integer.parseInt(clickcount));
                } else {
                    article.setHits(0);
                }
                if (subtitle != null && !"".equals(subtitle) && !"null".equals(subtitle)) {
                    article.setSubtitle(subtitle);
                } else {
                    article.setSubtitle("");
                }
                if (keywords != null && !"".equals(keywords) && !"null".equals(keywords)) {
                    article.setTags(keywords);
                } else {
                    article.setTags("");
                }
                if (author != null && !"".equals(author) && !"null".equals(author)) {
                    article.setAuthor(author);
                } else {
                    article.setAuthor("");
                }
                if (source_name != null && !"".equals(source_name) && !"null".equals(source_name)) {
                    article.setSource(source_name);
                } else {
                    article.setSource("");
                }
                if (add_user_name != null && !"".equals(add_user_name) && !"null".equals(add_user_name)) {
                    article.setEditor(add_user_name);
                } else {
                    article.setEditor("");
                }

                if (focus_pic != null && !"".equals(focus_pic) && !"null".equals(focus_pic)) {
                    article.setThumb_url(focus_pic);
                } else {
                    article.setThumb_url("");
                }
                if (content != null && !"".equals(content) && !"null".equals(content)) {
                    String content_url = "/upload/oldfiles/file/" + content;
                    article.setContent_url(content_url);
                } else {
                    article.setContent_url("");
                }
                article.setDescription("");
                if (add_time != null && !"".equals(add_time) && add_time.length() > 20) {
                    add_time = add_time.substring(0, add_time.length() - 2);
                }
                if (publish_time != null && !"".equals(publish_time) && publish_time.length() > 20) {
                    publish_time = publish_time.substring(0, publish_time.length() - 2);
                }
                if ("null".equals(add_time) || add_time == null || "".equals(add_time)) {
                    article.setInput_dtime("2015-07-31 11:31:00");
                } else {
                    article.setInput_dtime(add_time);
                }
                if ("null".equals(publish_time) || publish_time == null || "".equals(publish_time)) {
                    article.setReleased_dtime(publish_time);
                } else {
                    article.setReleased_dtime(publish_time);
                }

                if (ModelUtil.insert(article, "link", null)) {
                    System.out.println("信息添加成功，信息ID为：" + article_id + ",总共" + totle + "条，当前第" + index + "条");
                    index++;
                } else {
                    System.out.println("信息添加失败，信息ID为：" + article_id + ",总共" + totle + "条，当前第" + index + "条");
                }
            }

        } catch (Exception e) {
            System.out.print(e);

        } finally {

        }
    }


%>


<%!

    public void CmsToPic(int channl_id, int new_cat_id) {
        Connection conn = null;
        Statement stmt = null;
        Statement stmtCount = null;
        ResultSet rs = null;
        ResultSet rsCount = null;
        try {
            String site_id = "CMStlhg";

		  /*    Mysql   */
            String driverClassName = "com.mysql.jdbc.Driver";
            String userName = "root"; //登录用户名
            String userPasswd = "dyt@88352636"; //登录密码
            String dbName = "olddata";    //数据库名
            String url = "jdbc:mysql://localhost:3306/" + dbName + "?user=" + userName + "&password=" + userPasswd;
            Class.forName(driverClassName).newInstance();
            conn = DriverManager.getConnection(url);
            System.out.println("数据库连接成功");
            stmt = conn.createStatement();


		  /*     Sqlserver
		  String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		  String userName = "sa"; //登录用户名
		  String userPasswd = "zz?7231061"; //登录密码
		  String dbName="zizhougov";	//数据库名
		  String url = "jdbc:sqlserver://10.119.80.100:1433;databaseName=" + dbName;
		  Class.forName(driverClassName);
		  Connection conn = DriverManager.getConnection(url, userName, userPasswd);
		  System.out.println("数据库连接成功");
		  Statement stmt = conn.createStatement();
		  */

		  /*     Oracle
		  String driverClassName = "oracle.jdbc.driver.OracleDriver";
		  String userName = "webdb"; //登录用户名
		  String userPasswd = "webdb"; //登录密码
		  String dbName="orcl";	//数据库名
		  String url = "jdbc:oracle:thin:@10.32.1.22:1521:" + dbName;
		  Class.forName(driverClassName);
		  Connection conn = DriverManager.getConnection(url, userName, userPasswd);
		  System.out.println("数据库连接成功");
		  Statement stmt = conn.createStatement();
		  */

		  /*     Access
		  String strDirPath=getServletContext().getRealPath("/");
		  strDirPath=strDirPath.replace('\\','/');
		  String driverClassName = "com.hxtt.sql.access.AccessDriver";
		  String url = "jdbc:access:///"+strDirPath+"//oldData.mdb";
		  Class.forName(driverClassName);
		  Connection conn = DriverManager.getConnection(url);
		  System.out.println("数据库连接成功");
		  Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		  */

            int totle = 0;
            int index = 1;
            stmtCount = conn.createStatement();

            String sql = "select id,title,classid,content,myaddtime,picarry,titlepic from pic where classid = " + channl_id;

            String sqlCount = "select count(*) as totle from pic where classid = " + channl_id;

            rsCount = stmtCount.executeQuery(sqlCount);
            while (rsCount.next()) {
                String totleStr = rsCount.getString("totle");
                if (totleStr != null && !"".equals(totleStr)) {
                    totle = Integer.parseInt(totleStr);
                }
            }


            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String focus_pic = rs.getString("titlepic");
                String publish_time = rs.getString("myaddtime");
                String publish_flag = "1";
                String clickcount = "0";
                String publish_user = "1";
                String article_id = rs.getString("id");
                String title = rs.getString("title");
                String top_title = "";
                String subtitle = "";
                String source_name = "";
                String author = "";
                String editor = "";
                String content = rs.getString("downurl");
                String keywords = "";
                String add_time = rs.getString("myaddtime");
                String add_user = "1";
                String status = "";
                String words_count = "";
                String add_user_name = "";
                String down = "";
                String picsUrl = rs.getString("picarry");

                PicBean article = new PicBean();
                article.setInput_user(1);
                if ("1".equals(publish_flag)) {
                    article.setInfo_status(8);
                } else {
                    article.setInfo_status(4);
                }
                article.setModel_id(10);
                article.setApp_id("cms");
                article.setSite_id(site_id);

                int id = InfoBaseManager.getNextInfoId();
                article.setId(id);
                article.setInfo_id(id);
                article.setCat_id(new_cat_id);
                article.setGk_index("33-44-55-66");
                article.setGk_duty_dept("同乐海关");
                article.setGk_input_dept("同乐海关");
                if (title != null && !"".equals(title) && !"null".equals(title)) {
                    article.setTitle(title);
                } else {
                    article.setTitle("");
                }
                if (clickcount != null && !"".equals(clickcount) && !"null".equals(clickcount)) {
                    article.setHits(Integer.parseInt(clickcount));
                } else {
                    article.setHits(0);
                }
                if (subtitle != null && !"".equals(subtitle) && !"null".equals(subtitle)) {
                    article.setSubtitle(subtitle);
                } else {
                    article.setSubtitle("");
                }
                if (keywords != null && !"".equals(keywords) && !"null".equals(keywords)) {
                    article.setTags(keywords);
                } else {
                    article.setTags("");
                }
                if (author != null && !"".equals(author) && !"null".equals(author)) {
                    article.setAuthor(author);
                } else {
                    article.setAuthor("");
                }
                if (source_name != null && !"".equals(source_name) && !"null".equals(source_name)) {
                    article.setSource(source_name);
                } else {
                    article.setSource("");
                }
                if (add_user_name != null && !"".equals(add_user_name) && !"null".equals(add_user_name)) {
                    article.setEditor(add_user_name);
                } else {
                    article.setEditor("");
                }

                if (focus_pic != null && !"".equals(focus_pic) && !"null".equals(focus_pic)) {
                    article.setThumb_url("/upload/oldfiles/small/" + focus_pic);
                } else {
                    article.setThumb_url("");
                }


                if (picsUrl != null && !"".equals(picsUrl) && !"null".equals(picsUrl)) {
                    String[] pics = picsUrl.split(",");
                    ArrayList<PicItemBean> pbList = new ArrayList<PicItemBean>();
                    for (String pic : pics) {
                        PicItemBean pb = new PicItemBean();
                        pb.setAtt_id(0);
                        pb.setPic_path("/upload/oldfiles/picture/" + pic);
                        pbList.add(pb);
                    }
                }

                if (content != null && !"".equals(content) && !"null".equals(content)) {
                    article.setPic_content(content);
                } else {
                    article.setPic_content("");
                }
                article.setDescription("");
                if (add_time != null && !"".equals(add_time) && add_time.length() > 20) {
                    add_time = add_time.substring(0, add_time.length() - 2);
                }
                if (publish_time != null && !"".equals(publish_time) && publish_time.length() > 20) {
                    publish_time = publish_time.substring(0, publish_time.length() - 2);
                }
                if ("null".equals(add_time) || add_time == null || "".equals(add_time)) {
                    article.setInput_dtime("2015-07-31 11:31:00");
                } else {
                    article.setInput_dtime(add_time);
                }
                if ("null".equals(publish_time) || publish_time == null || "".equals(publish_time)) {
                    article.setReleased_dtime(publish_time);
                } else {
                    article.setReleased_dtime(publish_time);
                }

                if (ModelUtil.insert(article, "pic", null)) {
                    System.out.println("信息添加成功，信息ID为：" + article_id + ",总共" + totle + "条，当前第" + index + "条");
                    index++;
                } else {
                    System.out.println("信息添加失败，信息ID为：" + article_id + ",总共" + totle + "条，当前第" + index + "条");
                }
            }

        } catch (Exception e) {
            System.out.print(e);

        } finally {

        }
    }


%>


<%!

    public void CmsToCms2(String className, int new_cat_id) {
        Connection conn = null;
        Statement stmt = null;
        Statement stmtCount = null;
        ResultSet rs = null;
        ResultSet rsCount = null;
        try {
            String site_id = "CMStlhg";

		  /*    Mysql  */
            String driverClassName = "com.mysql.jdbc.Driver";
            String userName = "root"; //登录用户名
            String userPasswd = "dyt@88352636"; //登录密码
            String dbName = "olddata";    //数据库名
            String url = "jdbc:mysql://localhost:3306/" + dbName + "?user=" + userName + "&password=" + userPasswd;
            Class.forName(driverClassName).newInstance();
            conn = DriverManager.getConnection(url);
            System.out.println("数据库连接成功");
            stmt = conn.createStatement();


		  /*     Sqlserver
		  String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		  String userName = "sa"; //登录用户名
		  String userPasswd = "zz?7231061"; //登录密码
		  String dbName="zizhougov";	//数据库名
		  String url = "jdbc:sqlserver://10.119.80.100:1433;databaseName=" + dbName;
		  Class.forName(driverClassName);
		  Connection conn = DriverManager.getConnection(url, userName, userPasswd);
		  System.out.println("数据库连接成功");
		  Statement stmt = conn.createStatement();
		  */

		  /*     Oracle
		  String driverClassName = "oracle.jdbc.driver.OracleDriver";
		  String userName = "webdb"; //登录用户名
		  String userPasswd = "webdb"; //登录密码
		  String dbName="orcl";	//数据库名
		  String url = "jdbc:oracle:thin:@10.32.1.22:1521:" + dbName;
		  Class.forName(driverClassName);
		  Connection conn = DriverManager.getConnection(url, userName, userPasswd);
		  System.out.println("数据库连接成功");
		  Statement stmt = conn.createStatement();
		  */

		  /*     Access
		  String strDirPath=getServletContext().getRealPath("/");
		  strDirPath=strDirPath.replace('\\','/');
		  String driverClassName = "com.hxtt.sql.access.AccessDriver";
		  String url = "jdbc:access:///"+strDirPath+"//oldData.mdb";
		  Class.forName(driverClassName);
		  Connection conn = DriverManager.getConnection(url);
		  System.out.println("数据库连接成功");
		  Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		  */

            int totle = 0;
            int index = 1;
            stmtCount = conn.createStatement();

            String sql = "select id,title,titlepic,content,myaddtime from news where classname = '" + className + "'";

            String sqlCount = "select count(*) as totle from news where  classname = '" + className + "'";

            rsCount = stmtCount.executeQuery(sqlCount);
            while (rsCount.next()) {
                String totleStr = rsCount.getString("totle");
                if (totleStr != null && !"".equals(totleStr)) {
                    totle = Integer.parseInt(totleStr);
                }
            }


            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String focus_pic = rs.getString("titlepic");
                ;
                String publish_time = rs.getString("myaddtime");
                String publish_flag = "1";
                String clickcount = "0";
                String publish_user = "1";
                String article_id = rs.getString("id");
                String title = rs.getString("title");
                String top_title = "";
                String subtitle = "";
                String source_name = "";
                String author = "";
                String editor = "";
                String content = rs.getString("content");
                String keywords = "";
                String add_time = rs.getString("myaddtime");
                String add_user = "1";
                String status = "";
                String words_count = "";
                String add_user_name = "";
                String down = "";

                ArticleBean article = new ArticleBean();
                article.setInput_user(1);
                if ("1".equals(publish_flag)) {
                    article.setInfo_status(8);
                } else {
                    article.setInfo_status(4);
                }
                article.setModel_id(11);
                article.setApp_id("cms");
                article.setSite_id(site_id);

                int id = InfoBaseManager.getNextInfoId();
                article.setId(id);
                article.setInfo_id(id);
                article.setCat_id(new_cat_id);
                article.setGk_index("33-44-55-66");
                article.setGk_duty_dept("同乐海关");
                article.setGk_input_dept("同乐海关");
                if (title != null && !"".equals(title) && !"null".equals(title)) {
                    article.setTitle(title);
                } else {
                    article.setTitle("");
                }
                if (clickcount != null && !"".equals(clickcount) && !"null".equals(clickcount)) {
                    article.setHits(Integer.parseInt(clickcount));
                } else {
                    article.setHits(0);
                }
                if (subtitle != null && !"".equals(subtitle) && !"null".equals(subtitle)) {
                    article.setSubtitle(subtitle);
                } else {
                    article.setSubtitle("");
                }
                if (keywords != null && !"".equals(keywords) && !"null".equals(keywords)) {
                    article.setTags(keywords);
                } else {
                    article.setTags("");
                }
                if (author != null && !"".equals(author) && !"null".equals(author)) {
                    article.setAuthor(author);
                } else {
                    article.setAuthor("");
                }
                if (source_name != null && !"".equals(source_name) && !"null".equals(source_name)) {
                    article.setSource(source_name);
                } else {
                    article.setSource("");
                }
                if (add_user_name != null && !"".equals(add_user_name) && !"null".equals(add_user_name)) {
                    article.setEditor(add_user_name);
                } else {
                    article.setEditor("");
                }

                if (content != null && !"".equals(content) && !"null".equals(content)) {
                    String result = "";
                    /*
                    String dir = "F:\\tongleweb\\thenews\\text\\";
                    File fp = new File(dir, content + ".asp");
                    FileReader freader = new FileReader(fp);
                    BufferedReader bfdreader = new BufferedReader(freader);
                    String str_line = bfdreader.readLine();
                    while (str_line != null) {
                        result += str_line;
                        str_line = bfdreader.readLine();
                    }
                    bfdreader.close();
                    freader.close();
                    */
                    content = result;
                }
                if (focus_pic != null && !"".equals(focus_pic) && !"null".equals(focus_pic)) {
                    article.setThumb_url("/upload/oldfiles/fouse/" + focus_pic);
                } else {
                    article.setThumb_url("");
                }
                article.setInfo_content(content);
                article.setDescription("");
                if (add_time != null && !"".equals(add_time) && add_time.length() > 20) {
                    add_time = add_time.substring(0, add_time.length() - 2);
                }
                if (publish_time != null && !"".equals(publish_time) && publish_time.length() > 20) {
                    publish_time = publish_time.substring(0, publish_time.length() - 2);
                }
                if ("null".equals(add_time) || add_time == null || "".equals(add_time)) {
                    article.setInput_dtime("2015-07-31 11:31:00");
                } else {
                    article.setInput_dtime(add_time);
                }
                if ("null".equals(publish_time) || publish_time == null || "".equals(publish_time)) {
                    article.setReleased_dtime(publish_time);
                } else {
                    article.setReleased_dtime(publish_time);
                }

                if (ModelUtil.insert(article, "article", null)) {
                    System.out.println("信息添加成功，信息ID为：" + article_id + ",总共" + totle + "条，当前第" + index + "条");
                    index++;
                } else {
                    System.out.println("信息添加失败，信息ID为：" + article_id + ",总共" + totle + "条，当前第" + index + "条");
                }
            }
        } catch (Exception e) {
            System.out.print(e);

        } finally {

        }
    }


%>


</body>
</html>
