<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String site_id = request.getParameter("site_id");
	site_id = (site_id==null) ? "" : site_id;	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>列表生成工具</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/tem_tools.js"></script>
<script type="text/javascript" src="js/hot_list.js"></script>
<script type="text/javascript">
var aidnum;
var site_id = "<%=site_id%>";

var site_domain = "";
var app_id = "cms";
var TemplateRPC = jsonrpc.TemplateRPC;
$(document).ready(function(){
	if(site_id == "")
	{
		site_id = top.current_site_id;
		$("#cancel").click(function(){top.CloseModalWindow()});
		$("#addButton").click(saveImg);
	}else
	{//从标签编辑器过来的是用showModalDialog方式打开的，所以这里得特殊处理一下
		aidnum = window.dialogArguments;
		$("#cancel").click(function(){window.close()});
		$("#addButton").click(returnValueHandl);
	}
	site_domain = jsonrpc.SiteRPC.getSiteDomain(site_id);

	initButtomStyle();
	init_input();	
	initImgFile();	
});

var current_folder = "";
function initImgFile()
{	
	var json_data = eval(TemplateRPC.getFolderJSONStr(site_id));
	setLeftMenuTreeJsonData(json_data);
	$('#leftMenuTree').tree({
		onClick:function(node){
			if(node.attributes!=undefined){   
				current_folder = node.attributes.url; 
				showTemplateImgList();					
            }  
		}
	});
	$('#leftMenuTree div[node-id="1"]').parent().remove();
	$('#leftMenuTree div[node-id="2"]').parent().remove();
	$('#leftMenuTree div[node-id="0"]').click();
}

function showTemplateImgList()
{
	$("#list_ul").empty();
	var list = TemplateRPC.getResourcesListBySiteID(current_folder);//第一个参数为站点ID，暂时默认为空	
	list = List.toJSList(list);
	if(list != null && list.size() > 0)
	{
		var str = "";
		for(var i=0;i<list.size();i++)
		{
			var path = list.get(i).file_path;
			if(path.indexOf("\\") > -1)
			{
				path = path.replace(/\\/ig,"/");					
			}
			path = path.substring(path.indexOf("/images"));
			str += '<li style="width:130px;height:150px;float:left;margin:5px 3px;border:1px solid #9c9c9c"><div style="height:130px;width:130px;text-align:center"><img width="120px" height="120px" style="padding:5px 5px" src="'+site_domain+path+'"></div><div style="text-align:center;padding-top:3px"><input type="radio" id="r_'+i+'" name="imgfile" value="'+path+'"/><label for="r_'+i+'">'+list.get(i).file_name+'</label></div></li>';
		}
		$("#list_ul").append(str);
		init_input();
	}
}

function saveImg()
{
	var path = $(":checked").val();
	if(path == "" || path == null)
	{
		top.msgWargin("请选择图片");
		return;
	}else
	{
		top.CloseModalWindow();
		top.getCurrentFrameObj().uploadReturnHandl(path);
		//top.CloseModalWindow();
	}	
	
}

function returnValueHandl()
{
	var path = $(":checked").val();
	if(path == "" || path == null)
	{
		top.msgWargin("请选择图片");
		return;
	}else
	{
		aidnum.uploadReturnHandl(path);
		window.close();
	}
}

</script>
<style>
#format_selectList li{cursor:default;width:150px;margin:3px 0}
</style>
</head>

<body>
<span class="blank3"></span>
<form id="form1" name="form1" action="#" method="post">
<div>
<table id="Template_table" class="" border="0" cellpadding="0" cellspacing="0" width="98%">
	<tbody>
		<tr>			
			<td width="170px" valign="top">
			  <div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto;height:390px">
				<ul id="leftMenuTree" class="easyui-tree" animate="true">
				</ul>
			 </div>
			</td>			
			<td valign="top">
			  <div id="cat_tree_div2" class="border_color" style="margin:0 auto;width:100%; height:390px; overflow:auto;" >
				<ul id="list_ul">

				</ul>
			  </div>
			</td>
		</tr>
	</tbody>
</table>
</div>

<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="" value="插入" />	
			<input id="cancel" name="btn1" type="button" onclick="" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
