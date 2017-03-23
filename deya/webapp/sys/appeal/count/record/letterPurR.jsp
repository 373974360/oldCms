<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.util.DateUtil"%>
<%@page import="java.util.*"%>
<%@page import="com.deya.wcm.bean.appeal.count.CountBean" %>
<%@page import="com.deya.util.jconfig.*"%>
<%@page import="com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.services.appeal.count.CountUtil"%>
<%@page import="java.io.File"%>
<%@page import="com.deya.wcm.services.appeal.count.OutExcel"%>
<%@page import="com.deya.wcm.services.appeal.count.CountServices"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
   String now = DateUtil.getCurrentDateTime();
   //now = now.substring(0,7);
   pageContext.setAttribute("now",now);
   
   String s = (String)request.getParameter("s");
   String e = (String)request.getParameter("e");
   String b_ids = (String)request.getParameter("b_ids");
   pageContext.setAttribute("s",s);
   pageContext.setAttribute("e",e);
   
    //删除今天以前的文件夹
	String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path");
	String path = FormatUtil.formatPath(root_path + "/appeal/count/record/");  
	CountUtil.deleteFile(path);
	
	//创建今天的文件夹和xls文件
	String nowDate = CountUtil.getNowDayDate();
	String fileTemp2 = FormatUtil.formatPath(path+File.separator+nowDate);
	File file2 = new File(fileTemp2);
	if(!file2.exists()){
		file2.mkdir();
	} 
	String nowTime = CountUtil.getNowDayDateTime(); 
	String xls = nowTime + CountUtil.getEnglish(1)+".xls";
	String xlsFile = fileTemp2+File.separator+xls;
	String urlFile = "/sys/appeal/count/record/"+nowDate+File.separator+xls;
	
   List<Object[]> list = CountServices.getCountRecordPur(s,e,b_ids);
   pageContext.setAttribute("list",list);
   
   //得到满意度列表
   List<Map>  listSatisfaction =com.deya.wcm.services.appeal.count.CountServicesUtil.getSatisfactionList();
  
   String[] head = new String[listSatisfaction.size()+4];
   head[0] = "诉求目的";
   head[1] = "已办结数";
   head[2] = "已评价数"; 
   head[listSatisfaction.size()+3] = "总平均分"; 
   for(int i=0;i<listSatisfaction.size();i++){
	   head[i+3] = (String)(listSatisfaction.get(i)).get("name");
   }
   pageContext.setAttribute("headSize",head.length);
   pageContext.setAttribute("names",head);
   
   String[][] data = new String[list.size()][head.length];
   for(int i=0;i<list.size();i++){   
	   Object[] objects = list.get(i);
	   for(int j=0;j<objects.length;j++){
		   data[i][j] = (String)objects[j]; 
	   }
   }
   
   OutExcel oe=new OutExcel("部门统计表");
   oe.doOut(xlsFile,head,data);
   pageContext.setAttribute("url",urlFile);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>按诉求目的-办理情况</title>  


<jsp:include page="../../../include/include_tools.jsp"/>

<script type="text/javascript" src="../../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/banliqingkuang.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	Init_InfoTable("infoList"); 

    window.setExcelOutUrl('${url}');
});

</script>
</head>

<body>
<div>
<table id="infoList" class="table_border odd_even_list" border="0" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<!--  
			<td  width="25%" height="" name="config_key" id="cell_title_config_key" >诉求目的&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >总件数&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >待处理&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >处理中&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >待审核&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >已办结&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >办结率&#160;</td>
			--> 
			<c:forEach var="name" items="${names}" varStatus="status" begin="0" step="1">
			    <c:choose>
				    <c:when test="${status.index==0}">
				       <td  width="10%" height="" name="config_value" id="cell_title_config_value" >${name}&#160;</td>
				    </c:when>
				    <c:otherwise>
				       <td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >${name}&#160;</td>
				    </c:otherwise>
			    </c:choose>
			</c:forEach>
		</tr>
	</thead>  
	<tbody>
		 <c:forEach var="bean" items="${list}" varStatus="status" begin="0" step="1">
		 <tr height="25px" >	
			<c:forEach var="objectStr" items="${bean}" varStatus="status" begin="0" step="1">
			     <c:choose>
				    <c:when test="${status.index==0}">
				       <td  name="config_value" id="cell_title_config_value" >${objectStr}&#160;</td>
				    </c:when>
				    <c:otherwise>
				       <td  align="center"  name="config_value" id="cell_title_config_value" >${objectStr}&#160;</td>
				    </c:otherwise>
			    </c:choose>
			</c:forEach>
		</tr> 
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="${headSize-1}"></td><td></td>
		</tr>
	</tfoot>
</table>
</div>

</div>
<div id="turn" style="display:none"></div>
</div>
</body>

</html> 


