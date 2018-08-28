<%@ page contentType="text/html; charset=utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站断链检测</title>
<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript">
var CheckErrorUrlRPC = jsonrpc.CheckErrorUrlRPC;
var beanList = null;
var table = new Table();
table.table_name = "info_table";
$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
    initTable();
});
function startCheckSite(){
    $("#info_table tbody").empty();
    $("#info_table tbody").append("<tr style=\"height:32px;line-height:32px;\"><td colspan=\"2\">扫描中...，请勿离开此页面...</td></tr>");
    var site_domain = $("#site_domain").val();
    var encode = $("#encode").val();
    beanList =  CheckErrorUrlRPC.startCheckSite(site_domain,encode);
    beanList = List.toJSList(beanList);//把list转成JS的List对象
    table.setBeanList(beanList,"td_list");//设置列表内容的样式
    table.show("table");
    Init_InfoTable(table.table_name);
}
function initTable(){

    var colsList = new List();

    colsList.add(setTitleClos("url","地址","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
    colsList.add(setTitleClos("code","错误代码","100px","","",""));

    table.setColsList(colsList);
    table.setAllColsList(colsList);
    table.enableSort=false;//禁用表头排序
    table.onSortChange = startCheckSite;
    table.show("table");//里面参数为外层div的id
}
</script>
</head>
<body>
<div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="left" width="40">
			域名：
		</td>
		<td align="left" width="180">
			<input type="text" class="input_text"  name="site_domain" id="site_domain" value="http://www.itl.gov.cn" style="width:180px;"/>
		</td>
		<td align="left" width="40">
			编码：
		</td>
		<td align="left" width="80">
			<select id="encode" name="encode" class="input_select" style="width:80px;">
				<option value="UTF-8">UTF-8</option>
				<option value="GB2312">GB2312</option>
			</select>
		</td>
		<td width="10"></td>
		<td align="left" valign="middle">
			<input id="btn1" name="btn1" type="button" onclick="startCheckSite()" value="开始检测" />
		</td>
	</tr>
	<tr style="height:10px; line-height:10px;overflow:hidden"><td  colspan="3" style="height:10px; line-height:10px;overflow:hidden"></td></tr>
</table>
<div id="table"></div>
</div>
</body>
</html>
