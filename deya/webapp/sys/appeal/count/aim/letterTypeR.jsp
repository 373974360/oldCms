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
   List<CountBean> list = CountServices.getCountAimType(s,e,b_ids);
   pageContext.setAttribute("list",list); 
   
   
   String[] head = {"诉求目的","全部信件","正常信件","无效信件","重复信件","不予受理信件"};
   String[][] data = new String[list.size()][7];
   for(int i=0;i<list.size();i++){
	   CountBean countBean = list.get(i);
	   data[i][0] = countBean.getPurpose_name();
	   data[i][1] = countBean.getCount();
	   data[i][2] = countBean.getCount_normal();
	   data[i][3] = countBean.getCount_invalid();
	   data[i][4] = countBean.getCount_repeat();
	   data[i][5] = countBean.getCount_nohandle();
   }
   
   OutExcel oe=new OutExcel("统计表");
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
    //$("#excel_out").attr("onclick","window.open('${url}')");
});

</script>
</head>

<body>
<div>
<table id="infoList" class="table_border odd_even_list" border="0" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<td  width="25%" height="" name="config_key" id="cell_title_config_key" >诉求目的&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >全部信件&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >正常信件&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >无效信件&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >重复信件&#160;</td>
			<td  width="10%" height="" align="center"  name="config_value" id="cell_title_config_value" >不予受理信件&#160;</td>
		</tr>
	</thead>  
	<tbody>
		 <c:forEach var="bean" items="${list}" varStatus="status" begin="0" step="1">
		 <tr height="25px" >
			<td  align="left"  name="config_value" id="cell_title_config_value">${bean.purpose_name}&#160;</td>
			<td  align="center"  name="config_value" id="cell_title_config_value" >${bean.count}&#160;</td>
			<td  align="center"  name="config_value" id="cell_title_config_value" >${bean.count_normal}&#160;</td>
			<td  align="center"  name="config_value" id="cell_title_config_value" >${bean.count_invalid}&#160;</td>
			<td  align="center"  name="config_value" id="cell_title_config_value" >${bean.count_repeat}&#160;</td>
			<td  align="center"  name="config_value" id="cell_title_config_value" >${bean.count_nohandle}&#160;</td>
		</tr> 
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="5"></td><td></td>
		</tr>
	</tfoot>
</table>
</div>

</div>
<div id="turn" style="display:none"></div>
</div>
</body>

</html> 


