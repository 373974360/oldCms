<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>维护配置</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/siteConfigList.js"></script>
<script type="text/javascript">
var SiteConfigRPC = jsonrpc.SiteConfigRPC;
var SiteRPC = jsonrpc.SiteRPC;
var site_id = "${param.site_id}"; 
var defaultBean_click_step;
var defaultBean_count_initial;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	loadClickStep();
	loadCountInitial();
});

//计数器增加值
function loadClickStep()
{
	defaultBean_click_step = SiteConfigRPC.getConfigValues(site_id,"click_step");
	if(defaultBean_click_step != null)
	{
		$("#click_step").val(defaultBean_click_step.config_value);		
	}
}

//计数器初始值
function loadCountInitial()
{
	defaultBean_count_initial = SiteConfigRPC.getConfigValues(site_id,"count_initial");
	if(defaultBean_count_initial != null)
	{
		$("#count_initial").val(defaultBean_count_initial.config_value);		
	}
}

function saveClickStep()
{
	var SiteConfigBean = new Bean("com.deya.wcm.bean.control.SiteConfigBean",true);
	SiteConfigBean.site_id = site_id;
	SiteConfigBean.config_key = "click_step";
	SiteConfigBean.config_value = $("#click_step").val();
	var b = false;
	if(defaultBean_click_step != null)
	{
		SiteConfigBean.config_id = defaultBean_click_step.config_id;
		b = SiteConfigRPC.updateSiteConfig(SiteConfigBean);
	}else
		b = SiteConfigRPC.insertSiteConfig(SiteConfigBean);

	if(b && SiteRPC.updateStep(site_id,$("#click_step").val()))
	{		
		top.msgAlert("计数器增加值"+WCMLang.Set_success);
	}else
	{
		top.msgWargin("计数器增加值"+WCMLang.Set_fail);
	}
}

function saveCountInitial()
{
	var SiteConfigBean = new Bean("com.deya.wcm.bean.control.SiteConfigBean",true);
	SiteConfigBean.site_id = site_id;
	SiteConfigBean.config_key = "count_initial";
	SiteConfigBean.config_value = $("#count_initial").val();
	var b = false;
	if(defaultBean_count_initial != null)
	{
		SiteConfigBean.config_id = defaultBean_count_initial.config_id;
		b = SiteConfigRPC.updateSiteConfig(SiteConfigBean);
	}else
		b = SiteConfigRPC.insertSiteConfig(SiteConfigBean);

	if(b && SiteRPC.updateHitForSite(site_id,$("#count_initial").val()))
	{		
		top.msgAlert("计数器初始值"+WCMLang.Set_success);
	}else
	{
		top.msgWargin("计数器初始值"+WCMLang.Set_fail);
	}
}

function setSiteGray()
{
	jsonrpc.ConfigRPC.setSiteWebPageGreyORNoGrey(site_id);
	top.msgAlert("网站置灰切换成功");
}
</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="config_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>计数器增加值：</th>
			<td class="width250">
				<input id="click_step" name="click_step" type="text" class="width300" value="1" onblur="checkInputValue('click_step',false,3,'计数器增加值','checkInt')"/>
			</td>
			<td><input id="addButton" name="btn1" type="button" value="保存" onclick="saveClickStep()"/>	</td>
		</tr>
		<tr>
			<th>计数器初始值：</th>
			<td >
				<input id="count_initial" name="count_initial" type="text" class="width300" value="1" onblur="checkInputValue('count_initial',false,10,'计数器初始值','checkInt')"/>
			</td>
			<td><input id="addButton" name="btn1" type="button" value="保存" onclick="saveCountInitial()"/>	</td>
		</tr>
		<tr>
			<th>网站置灰切换：</th>
			<td>
				
			</td>
			<td><input id="addButton" name="btn1" type="button" value="保存" onclick="setSiteGray()"/>	</td>
		</tr>
	</tbody>
</table>
</form>
</body>
</html>
