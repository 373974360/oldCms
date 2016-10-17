<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%@ page import="com.deya.wcm.services.cms.category.CategoryManager" %>
<%@ page import="com.deya.wcm.services.org.dept.DeptManager" %>
<%@ page import="com.deya.wcm.bean.org.dept.DeptBean" %>
<%
    String site_id = request.getParameter("site_id");
    String dept_id = request.getParameter("dept_id");
    String source = "";
    String start_day = request.getParameter("start_day");
    String end_day = request.getParameter("end_day");
    if(dept_id == null || "".equals(dept_id) || "null".equals(dept_id))
    {
        source = "";
    }
    else {
        DeptBean dept = DeptManager.getDeptBeanByID(dept_id);
        source = "'" + dept.getDept_name() + "','" ;
        String childs = DeptManager.getChildDeptIDSByID(dept_id);
        if(childs != null && !"".equals(childs))
        {
            String[] childStr = childs.split(",");
            for(String temp_id : childStr)
            {
                dept = DeptManager.getDeptBeanByID(temp_id);
                source = source + dept.getDept_name() + "','";
            }
        }
        source = source.substring(0,source.length() - 2);
    }

    if(site_id == null || "".equals(site_id) || "null".equals(site_id))
        site_id = "";
    String now = DateUtil.getCurrentDate() ;
    String now1 = now.substring(0,7)+"-01";
    if(start_day == null || "".equals(start_day)) {
        start_day = now1;
    }
    if(end_day == null || "".equals(end_day)) {
        end_day = now;
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查看统计详细信息</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<style type="text/css">
.checkBox_mid{ vertical-align:middle; margin-right:10px;};
#v_type{height:50px;}
.checkBox_text{ vertical-align:text-top}
.span_left{ margin-left:14px;}
</style>
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript">
var CmsCountRPC = jsonrpc.CmsCountRPC;

var mp = new Map();
var beanList = null;

var site_id = "<%=site_id%>";
var source = "<%=source%>";
var start_day = "<%=start_day%>";
var end_day = "<%=end_day%>";

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	//得到父页面中设置的参数
	showList();
});

// 显示统计结果列表
function showList()
{		
	mp.put("start_day",start_day + " 00:00:00");
	mp.put("end_day",end_day +" 23:59:59");
    mp.put("info_status","8");
	mp.put("site_id",site_id);
    if(source != "")
    {
        mp.put("source",source);
    }
    mp.put("orderby","ci.released_dtime desc");

	beanList = CmsCountRPC.getInfoListByDept(mp);
	beanList = List.toJSList(beanList);
	createTable();
}
// 生成内容表格
function createTable()
{
	$("#treeTableCount").empty();
	var treeHtmls = "<thead>" +
    "   <tr>" +
    "   <th width='8%' style='text-align:center'>序号</th>"+
    "   <th width='18%' style='text-align:center'>发布时间</th>"+
    "   <th width='25%' style='text-align:center'>标题</th>"+
    "   <th width='13%' style='text-align:center'>栏目名称</th>"+
    "   <th width='10%' style='text-align:center'>作者</th>"+
    "   <th width='18%' style='text-align:center'>部门</th>"+
    "   <th width='8%' style='text-align:center'>评级</th>"+
    "</tr>" +
    "</thead>" +
    "<tbody>" ;
    if(beanList.size() != 0)
    {
		for(var i=0;i<beanList.size();i++)
		{
			treeHtmls += setHandlList(i+1,beanList.get(i));
		}
		treeHtmls += "</tbody>"+
		"<tfoot>" +
	  	"<td colspan=\"7\"></td>"+ " </tfoot>";
 	}else{
		treeHtmls += "<tr><td colspan=\"7\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"7\"></td>"+" </tfoot>";
	}
	$("#treeTableCount").append(treeHtmls);
	iniTreeTable("treeTableCount");
}

// 组织统计的数据
function setHandlList(sort,bean)
{
	var str = "<tr>";
	if(bean != null)
	{
        str+="<td style='text-align:center'>" + sort + "</td>";
        str+="<td style='text-align:center'>"+bean.released_dtime+"</td>";
        str+="<td><a href='/info/contentView.jsp?info_id="+bean.info_id+"' target=\"_blank\">"+bean.title+"</a></td>";
        str+="<td style='text-align:center'>"+bean.cat_cname+"</td>";
        str+="<td style='text-align:center'>"+bean.author+"</td>";
        str+="<td style='text-align:center'>"+bean.source+"</td>";
        str+="<td style='text-align:center'>"+bean.info_level+"</td>";
	}
	str+="</tr>";
	return str;
}

//导出
function  outFileBtn(){
    if(beanList.size() != 0) {
        var listHeader = new List();
        listHeader.add("序号");
        listHeader.add("发布时间");
        listHeader.add("标题");
        listHeader.add("栏目名称");
        listHeader.add("作者");
        listHeader.add("部门");
        listHeader.add("评级");
        var url = CmsCountRPC.getInfoListByCatOutExcel(listHeader,mp,"部门信息统计表");
        location.href=url;
    }
}
</script>
</head>
<body>
<span class="blank3"></span>
<div>
   <center><input type="button" id="searchBtn"  onclick="outFileBtn()" value="导出到Excel"/></center>
</div>
<span class="blank3"></span>
<table width="100%">
    <tr valign="top">
     	<td>
		<table id="treeTableCount" class="treeTableCSS table_border"  border="0" cellpadding="0" cellspacing="0">
		</table>
       </td>
     </tr>
</table>
<span class="blank3"></span>
<span class="blank3"></span>
</body>
</html>