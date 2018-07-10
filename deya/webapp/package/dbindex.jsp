<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.deya.util.FormatUtil,com.deya.wcm.bean.cms.info.*,com.deya.wcm.services.cms.info.*"%>
<%@page import="com.deya.wcm.bean.appeal.model.*,com.deya.wcm.services.appeal.model.*"%>
<%@page import="com.deya.wcm.bean.appeal.sq.*,com.deya.wcm.services.appeal.sq.*"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager"%>
<%@ page import="com.deya.util.DateUtil" %>
<%
	String step_id = request.getParameter("step_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>数据迁移工具</title>
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<script type="text/javascript" src="/js/jquery.js"></script>
	<style type="text/css">
		td,input,textarea{font-size:14px;}
	</style>
</head>
<body>
<%
	if(StringUtils.isEmpty(step_id)){
%>
<form action="dbindex.jsp" method="post">
	<table>
		<tr>
			<td>数据类型：</td>
			<td>
				<select name="source_dbtype">
					<option value="0">mysql</option>
					<option value="1">sqlserver</option>
					<option value="2">oracle</option>
				</select>
			</td>
			<td>IP地址：</td>
			<td>
				<input type="text" name="source_ip" id="source_ip"/>
			</td>
			<td>端口：</td>
			<td>
				<input type="text" name="source_port" id="source_port"/>
			</td>
		</tr>
		<tr>
			<td>数据库名：</td>
			<td>
				<input type="text" name="source_dbname" id="source_dbname"/>
			</td>
			<td>登录用户：</td>
			<td>
				<input type="text" name="source_dbusername" id="source_dbusername"/>
			</td>
			<td>登录密码：</td>
			<td>
				<input type="text" name="source_dbpassword" id="source_dbpassword"/>
			</td>
		</tr>
		<tr>
			<td>站点ID：</td>
			<td>
				<input type="text" name="siteId" id="siteId"/>
			</td>
			<td>默认编辑：</td>
			<td colspan="3">
				<input type="text" name="deptName" id="deptName"/>
			</td>
		</tr>
		<tr>
			<td valign="top">栏目集合：</td>
			<td colspan="4">
				<textarea id="source_cat_array" name="source_cat_array" style="width:645px;height:100px;"></textarea>
			</td>
			<td valign="top">
				备注：为了不影响效率，需要哪些栏目填哪些栏目的ID；格式：[栏目名称|原栏目ID|目标栏目ID];如：[今日要闻|35|10021],[媒体聚焦|345|10022],[部门动态|634|10023],.......
			</td>
		</tr>
		<tr>
			<td valign="top">内容SQL：</td>
			<td colspan="4">
				<textarea id="source_select_sql" name="source_select_sql" style="width:645px;height:100px;"></textarea>
			</td>
			<td valign="top">
				备注：必须指定字段名称 如：select 字段1,字段2,字段3,...... from table_name where 栏目ID = #cat_ids order by .....；
				#cat_ids 为占位符
			</td>
		</tr>
		<tr>
			<td colspan="6">
				<input type="hidden" name="step_id" value="1">
				<input type="submit" value="下一步"/>
			</td>
		</tr>
	</table>
</form>
<%
} else if(step_id.equals("1")){
	String source_dbtype = request.getParameter("source_dbtype");//数据库类型
	String source_ip = request.getParameter("source_ip");//数据库IP
	String source_port = request.getParameter("source_port");//端口
	String source_dbname = request.getParameter("source_dbname");//数据库名
	String source_dbusername = request.getParameter("source_dbusername");//登录名
	String source_dbpassword = request.getParameter("source_dbpassword");//登录密码
	String source_select_sql = request.getParameter("source_select_sql");//查询sql
	String source_cat_array = request.getParameter("source_cat_array");//原网站栏目ID集合
	String deptName = request.getParameter("deptName");//默认部门名称
	String siteId = request.getParameter("siteId");//目标站点ID
	//解析字段
	String[] sourceColumnArray = source_select_sql.substring(source_select_sql.indexOf(" ")+1,source_select_sql.indexOf(" from")).split(",");
	String[] toColumnArray = {"article_id","title","top_title","subtitle","source_name","author","focus_pic","editor","topic_key","add_time","content_url","clickcount","publish_time","gk_index","description","downfile","content","doc_no","info_status"};
	String[] toColumnNameArray = {"信息ID","标题","上标题","副标题","来源","作者","标题图","编辑","关键词","添加时间","链接地址","点击数","发布时间","索引号","内容摘要","附件地址","正文内容","文号","发布状态"};

	//对应栏目
	String[] sourceCatIdArray = source_cat_array.split(",");
%>
<form action="dbindex.jsp" method="post" id="importStart">
	<input type="hidden" name="source_dbtype" value="<%=source_dbtype%>"/>
	<input type="hidden" name="source_ip" value="<%=source_ip%>"/>
	<input type="hidden" name="source_port" value="<%=source_port%>"/>
	<input type="hidden" name="source_dbname" value="<%=source_dbname%>"/>
	<input type="hidden" name="source_dbusername" value="<%=source_dbusername%>"/>
	<input type="hidden" name="source_dbpassword" value="<%=source_dbpassword%>"/>
	<input type="hidden" name="source_select_sql" value="<%=source_select_sql%>"/>
	<input type="hidden" name="siteId" value="<%=siteId%>"/>
	<input type="hidden" name="deptName" value="<%=deptName%>"/>
	<table>
		<tr>
			<td valign="top">字段对应关系：</td>
			<td>
				<table>
					<%
						for(String c:sourceColumnArray) {
						    if(c.indexOf(" as ")!=-1){
						        c = c.substring(c.indexOf("as ")+3,c.length());
							}
					%>
					<tr>
						<td><input type="text" name="source_column" readonly value="<%=c %>"/></td>
						<td>
							<select name="to_column">
								<option value="null">无</option>
								<%
									for(int i=0;i<toColumnNameArray.length;i++) {
								%>
								<option value="<%=toColumnArray[i]%>"><%=toColumnNameArray[i]%></option>
								<%
									}
								%>
							</select>
						</td>
					</tr>
					<%
						}
					%>
				</table>
			</td>
		</tr>

		<tr>
			<td valign="top">栏目对应关系：</td>
			<td>
				<table>
					<%
						for(String c:sourceCatIdArray) {
							c = c.substring(1,c.length()-1);
					%>
					<tr>
						<td><input type="text" name="source_cat_name" readonly value="<%=c.substring(0,c.indexOf("|")) %>"/></td>
						<td><input type="text" name="source_cat_id" readonly value="<%=c.substring(c.indexOf("|")+1,c.lastIndexOf("|")) %>"/></td>
						<td>
							目标栏目ID<input type="text" name="to_cat_id" value="<%=c.substring(c.lastIndexOf("|")+1,c.length()) %>"/> 备注：无对应栏目请填写 0
						</td>
					</tr>
					<%
						}
					%>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="hidden" name="step_id" value="2">
				<input type="button" value="上一步" onclick="javascript:history.go(-1);"/>
				<input type="submit" value="开始导入"/>
			</td>
		</tr>
	</table>
</form>
<%
}else if(step_id.equals("2")){
	String source_dbtype = request.getParameter("source_dbtype");//数据库类型
	String source_ip = request.getParameter("source_ip");//数据库IP
	String source_port = request.getParameter("source_port");//端口
	String source_dbname = request.getParameter("source_dbname");//数据库名
	String source_dbusername = request.getParameter("source_dbusername");//登录名
	String source_dbpassword = request.getParameter("source_dbpassword");//登录密码
	String source_select_sql = request.getParameter("source_select_sql");//查询sql
	String deptName = request.getParameter("deptName");//默认部门名称
	String siteId = request.getParameter("siteId");//目标站点ID


	String[] source_column = request.getParameterValues("source_column");
	String[] to_column = request.getParameterValues("to_column");


	String[] source_cat_id = request.getParameterValues("source_cat_id");
	String[] source_cat_name = request.getParameterValues("source_cat_name");
	String[] to_cat_id = request.getParameterValues("to_cat_id");


	String[] dirverClass = {"com.mysql.jdbc.Driver",
			"com.microsoft.sqlserver.jdbc.SQLServerDriver",
			"oracle.jdbc.driver.OracleDriver"};
	String[] linkUrl = {"jdbc:mysql://"+source_ip+":"+source_port+"/"+source_dbname+"?user="+source_dbusername+"&password="+source_dbpassword+"",
			"jdbc:sqlserver://"+source_ip+":"+source_port+";databaseName=" + source_dbname+"",
			"jdbc:oracle:thin:@"+source_ip+":"+source_port+":" + source_dbname+""};
	String msg = "";
%>
<table>
	<tr>
		<td>数据库驱动：</td>
		<td><%=dirverClass[Integer.parseInt(source_dbtype)]%></td>
	</tr>
	<tr>
		<td>链接字符串：</td>
		<td><%=linkUrl[Integer.parseInt(source_dbtype)]%></td>
	</tr>
	<tr>
		<td colspan="2" id="view_message"><a href="dbindex.jsp">回首页>>...</a></td>
	</tr>
</table>
<%
	try {
		for(int i=0;i<to_cat_id.length;i++){
			if(!to_cat_id[i].equals("0")){
				Connection conn = null;
				Class.forName(dirverClass[Integer.parseInt(source_dbtype)]);
				if (source_dbtype.equals("0")) {
					conn = DriverManager.getConnection(linkUrl[Integer.parseInt(source_dbtype)]);
				} else {
					conn = DriverManager.getConnection(linkUrl[Integer.parseInt(source_dbtype)], source_dbusername, source_dbpassword);
				}
				Statement stmt = conn.createStatement();

				int totle = 0;
				int index = 1;
				String article_id="";

				Statement stmtCount = conn.createStatement();
				String sql = source_select_sql.replace("#cat_ids",source_cat_id[i]);
				String sqlCount = sql.replace(sql.substring(sql.indexOf(" ")+1,sql.indexOf(" from")),"count(*) totle");

				ResultSet rsCount = stmtCount.executeQuery(sqlCount);
				while(rsCount.next())
				{
					String totleStr = rsCount.getString("totle");
					if(totleStr != null && !"".equals(totleStr))
					{
						totle = Integer.parseInt(totleStr);
					}
				}
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()) {
					ArticleBean article = new ArticleBean();
					article.setWeight(60);
					article.setApp_id("cms");
					article.setInfo_status(8);
					article.setModel_id(11);
					article.setInput_user(1);
					article.setSite_id(siteId);
					int id = InfoBaseManager.getNextInfoId();
					article.setId(id);
					article.setInfo_id(id);
					article.setCat_id(Integer.parseInt(to_cat_id[i]));
					String downFile = "";
					for(int j=0;j<to_column.length;j++){
						if(to_column[j].equals("article_id")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								article_id = v;
							}else{
								article_id = "";
							}
						}
						if(to_column[j].equals("downfile")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								downFile = v;
							}else{
								downFile = "";
							}
						}
						if(to_column[j].equals("info_status")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
							    try{
									article.setInfo_status(Integer.parseInt(v));
							    }catch (Exception e){
							        e.printStackTrace();
									article.setInfo_status(8);
							    }
							}else{
								article.setInfo_status(8);
							}
						}
						if(to_column[j].equals("title")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								article.setTitle(v);
							}else{
								article.setTitle("");
							}
						}
						if(to_column[j].equals("top_title")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								article.setTop_title(v);
							}else{
								article.setTop_title("");
							}
						}
						if(to_column[j].equals("subtitle")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								article.setSubtitle(v);
							}else{
								article.setSubtitle("");
							}
						}
						if(to_column[j].equals("source_name")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								article.setSource(v);
							}else{
								article.setSource("");
							}
						}
						if(to_column[j].equals("author")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								article.setAuthor(v);
							}else{
								article.setAuthor("");
							}
						}
						if(to_column[j].equals("focus_pic")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								article.setThumb_url(v);
							}else{
								article.setThumb_url("");
							}
						}
						if(to_column[j].equals("editor")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								article.setEditor(v);
								article.setGk_duty_dept(v);
								article.setGk_input_dept(v);
							}else{
								article.setEditor(deptName);
								article.setGk_duty_dept(deptName);
								article.setGk_input_dept(deptName);
							}
						}
						if(to_column[j].equals("keywords")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								article.setTopic_key(v);
							}else{
								article.setTopic_key("");
							}
						}
						if(to_column[j].equals("add_time")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								article.setInput_dtime(DateUtil.formatToDateTimeString(v));
							}else{
								article.setInput_dtime(DateUtil.getCurrentDateTime());
							}
						}
						if(to_column[j].equals("content_url")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								article.setContent_url(v);
								article.setModel_id(12);
							}else{
								article.setContent_url("");
								article.setModel_id(11);
							}
						}
						if(to_column[j].equals("clickcount")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								article.setHits(Integer.parseInt(v));
							}else{
								article.setHits(0);
							}
						}
						if(to_column[j].equals("publish_time")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								article.setReleased_dtime(DateUtil.formatToDateTimeString(v));
							}else{
								article.setReleased_dtime(DateUtil.getCurrentDateTime());
							}
						}
						if(to_column[j].equals("gk_index")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								article.setGk_index(v);
							}else{
								article.setGk_index("33-44-55-66");
							}
						}
						if(to_column[j].equals("description")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								article.setDescription(v);
							}else{
								article.setDescription("");
							}
						}
						if(to_column[j].equals("doc_no")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								article.setDoc_no(v);
							}else{
								article.setDoc_no("");
							}
						}
						if(to_column[j].equals("content")){
							String v = rs.getString(source_column[j].substring(source_column[j].indexOf(".")+1,source_column[j].length()));
							if(StringUtils.isNotEmpty(v)){
								if(StringUtils.isNotEmpty(downFile)){
									v+="<a href='"+downFile+"' target='_blank'>附件下载</a>";
								}
								article.setInfo_content(v);
							}else{
								article.setInfo_content("");
							}
						}
					}
					if(ModelUtil.insert(article,"article",null))
					{
						msg = source_cat_name[i]+"-"+source_cat_id[i]+" 的栏目正在执行；信息添加成功，信息ID为：" + article_id + ",总共"+ totle +"条，当前第" + index + "条";
						System.out.println(msg);
						index++;
					}else
					{
						msg = source_cat_name[i]+"-"+source_cat_id[i]+" 的栏目正在执行；信息添加失败，信息ID为：" + article_id + ",总共"+ totle +"条，当前第" + index + "条";
						System.out.println(msg);
						break;
					}
				}
				msg = "导入完成";
				System.out.println(msg);
				rsCount.close();
				rs.close();
				stmt.close();
				stmtCount.close();
				conn.close();
			}else{
				System.out.println("源栏目："+source_cat_name[i]+"；未找到目标栏目ID："+to_cat_id[i]+"；请检查...");
			}
		}
	}catch (Exception e){
		e.printStackTrace();
%>
<table>
	<tr>
		<td>出错了...</td>
	</tr>
</table>
<%
		}
	}
%>
