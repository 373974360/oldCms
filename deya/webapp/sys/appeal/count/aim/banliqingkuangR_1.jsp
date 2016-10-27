<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.util.DateUtil"%>
<%@page import="java.util.*"%>
<%@ page import="com.deya.wcm.bean.appeal.count.CountBean" %>
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
	String path = FormatUtil.formatPath(root_path + "/appeal/count/aim/");  
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
	String urlFile = "/sys/appeal/count/aim/"+nowDate+File.separator+xls;
	
	//假数据
   List<CountBean> list = CountServices.getCountAimHandle(s,e,b_ids);
   pageContext.setAttribute("list",list);
   
   
   String[] head = {"业务类型","总件数","待处理","处理中","待审核","已办结","办结率"};
   String[][] data = new String[list.size()][7];
   for(int i=0;i<list.size();i++){
	   CountBean countBean = list.get(i);
	   data[i][0] = countBean.getBusiness_name();
	   data[i][1] = countBean.getCountall();
	   data[i][2] = countBean.getCountdai();
	   data[i][3] = countBean.getCountchu();
	   data[i][4] = countBean.getCountshen();
	   data[i][5] = countBean.getCountend();
	   data[i][6] = countBean.getCountendp();
   }//331637123
   
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
});

</script>
</head>

<body>
<div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>	
		<td class="fromTabs">
			<input id="addButton" name="btn1" type="button" onclick="window.location.href='banliqingkuang.jsp'" value="统计条件" />	
			<input id="excel_out" name="btn1" type="button"  onclick="window.open('${url}')" value="导出" />
			<span class="blank3"></span>
		</td>  
		<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
			
		</td>		
	</tr>
</table>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>
			<td height="20px" align="center" width="120px">报表生成时间：&#160;</td>
			<td  height="20px" align="left" >&#160;<span id="current_time">${now}</span></td>
		 </tr>
		 <tr>
			<td height="20px" align="center" rowspan="4" width="120px">统计条件：&#160;</td>
			<td height="20px" align="left" >&#160;统计方式：&#160;<span id="time_type">按诉求目的-办理情况</span>
			</td>
		 </tr>
		 <tr>
			<td height="20px" align="left" >&#160;日期范围：&#160;<span id="times">${s}----${e}</span>				
			</td>
		 </tr>
</table>	
<div id="table">

<div>
<table id="infoList" class="table_border odd_even_list" border="0" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<td  width="25%" height="" name="config_key" id="cell_title_config_key" >业务类型&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >总件数&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >待处理&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >处理中&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >待审核&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >已办结&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >办结率&#160;</td>
		</tr>
	</thead>  
	<tbody>
		 <c:forEach var="bean" items="${list}" varStatus="status" begin="0" step="1">
		 <tr height="25px" >
			<td  align="left"  name="config_value" id="cell_title_config_value">${bean.b_name}&#160;</td>
			<td  align="center"  name="config_value" id="cell_title_config_value" >${bean.countall}&#160;</td>
			<td  align="center"  name="config_value" id="cell_title_config_value" >${bean.countdai}&#160;</td>
			<td  align="center"  name="config_value" id="cell_title_config_value" >${bean.countchu}&#160;</td>
			<td  align="center"  name="config_value" id="cell_title_config_value" >${bean.countshen}&#160;</td>
			<td  align="center"  name="config_value" id="cell_title_config_value" >${bean.countend}&#160;</td>
			<td  align="center"  name="config_value" id="cell_title_config_value" >${bean.countendp}&#160;</td>
		</tr> 
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="6"></td><td></td>
		</tr>
	</tfoot>
</table>
</div>

</div>
<div id="turn" style="display:none"></div>
</div>
</body>

</html> 


