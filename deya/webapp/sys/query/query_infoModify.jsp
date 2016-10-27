<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>数据修改</title>
<meta name="generator" content="cicro-Builder"/>
<meta name="author" content="cicro"/>
<jsp:include page="../include/include_tools.jsp"/>
<style>
.addTabale{width:400px;float:left;margin-left:20px;line-height:20px;text-align:left;}
</style>
<script type="text/javascript">
var conf_id =<%=request.getParameter("conf_id")%>;
var site_id ="<%=request.getParameter("site_id")%>";
var item_id ="<%=request.getParameter("item_id")%>";

var QueryItemRPC = jsonrpc.QueryItemRPC;
var QueryDicRPC = jsonrpc.QueryDicRPC;
var QueryItemBean = new Bean("com.deya.wcm.bean.query.QueryItemBean",true);
var QueryDicBean = new Bean("com.deya.wcm.bean.query.QueryDicBean",true);
var val = new Validator();

$(document).ready(function(){
	initButtomStyle();
	if(item_id != "" && item_id != "null" && item_id != null)
	{		
		var BeanList = QueryItemRPC.getQueryItemBeans(item_id,conf_id);
		    BeanList = List.toJSList(BeanList);//把list转成JS的List对象
		if(BeanList != null && BeanList.size() > 0)
			addDict(BeanList);		
	}
	
});
    
function addDict(BeanList)
{
	var str ="";
	for(var i=1;i<=BeanList.size();i++)
	{		
		QueryDicBean = QueryDicRPC.getDicByConf_Dic_Id(BeanList.get(i-1).item_key,conf_id);
		str += '<tr style="height:24px;">'
		str	+= '<td class="width100" align="right" id='+BeanList.get(i-1).item_key+' name ='+BeanList.get(i-1).item_key+'>'+QueryDicBean.field_cname+':&nbsp;&nbsp;</td>'
		str	+= '<td align="left" style="text-align:left;">';
		str	+= '<input id='+BeanList.get(i-1).item_value+' name='+BeanList.get(i-1).item_value+' value='+BeanList.get(i-1).item_value+' style="width:150px;text-align:left;"/>';
		str += '</td>';
		str	+='</tr>';
	}
	$("#from_table").html(str);	
}

function insertQueryDicBean()
{
	var list = new List();
	if(!standard_checkInputInfo("from_table"))
	{
		return;
	}
	$("#from_table tr").each(function(i){
		var Bean = BeanUtil.getCopy(QueryItemBean);
		var m = new Map();

		Bean.conf_id = conf_id;
		Bean.site_id = site_id;
		Bean.item_id = item_id;
		Bean.item_key = $(this).find("td").attr("id");
		if($(this).find("td input").val() == ""){
			top.msgAlert("信息不能为空!");
			return;
		}else{
			Bean.item_value = $(this).find("td input").val();
		}
		list.add(Bean);
	});
	if(QueryItemRPC.updateQueryItem(list))
	{
			top.msgAlert("信息"+WCMLang.Add_success);
			window.history.go(-1);
	}else
	{
			top.msgWargin("信息"+WCMLang.Add_fail);
	}
}
</script>
</head>
<body>
<span class="blank6"></span>
<form id="form1" name="form1" action="#" method="post">
<div class="textLeft" style="padding-left:10px;">
	<table id="from_table" class="addTabale" border="0" cellpadding="0" cellspacing="0">
	</table>
</div>
<!--隔线开始-->
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<!--隔线结束-->
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="insertQueryDicBean()" value="保存" />
			<input id="userAddReset" name="btn1" type="button" onclick="window.location.reload()" value="重置" />
			<input id="btn2" name="btn2" type="button" onclick="window.history.go(-1)" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
