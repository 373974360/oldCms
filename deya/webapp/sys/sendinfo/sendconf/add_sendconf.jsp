<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>报送配置</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/sendConfList.js"></script>
<script type="text/javascript">
var site_id = "${param.site_id}";
var topnum = "${param.topnum}";
var defaultBean;

$(document).ready(function(){
	reloadBefore();
	initButtomStyle();
	init_input();
	site_ids = top.getCurrentFrameObj(topnum).site_ids;
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
	
	reloadAfter();
});

//得到允许报送站点列表
function getReceiveSiteList(site_domain)
{
	if(!standard_checkInputInfo("sendConfig_table"))
	{
		return;
	}
	$("#site_list").empty();
	var siteJSONlist = eval(SendInfoRPC.getReceiveSiteList($("#site_domain").val()));
	if(siteJSONlist != null)
	{
		for(var i=0;i<siteJSONlist.length;i++)
		{
			if(site_ids.indexOf(siteJSONlist[i].id) == -1)
			{
				$("#site_list").append('<li style="float:none;height:20px"><input type="checkbox" id="c_site" value="'+siteJSONlist[i].id+'" onclick="selectedSite(this,\''+siteJSONlist[i].id+'\',\''+siteJSONlist[i].text+'\',\''+siteJSONlist[i].domain+'\')"><label onclick="lableClickSite(this)">'+siteJSONlist[i].text+'</label></div></li>');
			}else
			{
				$("#site_list").append('<li style="float:none;height:20px"><input type="checkbox" disabled="disabled"><span >'+siteJSONlist[i].text+'</span></div></li>');
			}
		}
		init_input();
	}else
	{
		top.msgWargin("未获取到站点信息，请确认域名是否正确，或目标站点已设置接收站点");
		return;
	}
}

//选择站点
function selectedSite(obj,site_id,site_name,site_domain)
{
	if($(obj).is(':checked'))
	{		
		$("#sel_list").append('<li style="float:none;height:20px" site_id="'+site_id+'" site_domain="'+site_domain+'">'+site_name+'<img onclick="deleteSiteList(this,\''+site_id+'\')" src="../../images/delete.png" width="15" height="15" alt="" align="right"/></li>');
	}
	else
	{
		$("#sel_list li[site_id='"+site_id+"']").remove();
	}
	
}

//删除已选站点 
function deleteSiteList(obj,site_id)
{
	$("#site_list :checkbox[value='"+site_id+"']").attr("checked",false);
	$(obj).parent().remove();
}

//点击lable触发事件
function lableClickSite(obj)
{
	$(obj).prev().attr("checked",$(obj).prev().is(':checked') == false ? true:false);
	$(obj).prev().click();
}
</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="sendConfig_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>站点域名：</th>
			<td >
				<input type="text" id="site_domain" class="width200" onblur="checkInputValue('site_domain',false,100,'站点域名','')">
				&nbsp;<input id="addButton" name="btn1" type="button" onclick="getReceiveSiteList()" value="获取" />
			</td>	
		</tr>
		<tr>
		   <th></th>
		   <td>
		    <table class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:530px" align="left">
			  <tbody>
			   <tr>
			    <th style="text-align:left">可选站点：</th>
				<th></th>
				<th style="text-align:left">已选站点：</th>
			   </tr>
			   <tr>
			    <td>
				 <div id="site_div" style="width:250px;height:260px;overflow:auto;" class="border_color">
					<div class="contentBox6 textLeft no_zhehang" style="overflow:auto">
						<ul id="site_list" class="easyui-tree no_zhehang" animate="true">
						</ul>
					</div>
				 </div>
				</td>
				<td>
				 >>
				</td>
				<td >
				 <div style="width:260px;height:260px;overflow:auto;" class="border_color">
					<ul id="sel_list" style="margin:10px">
					</ul>
				 </div>
				</td>
			   </tr>
			  </tbody>
			</table>
		   </td>
		</tr>
	</tbody>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="insertSendConfig()" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('sendConfig_table',id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex);" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
