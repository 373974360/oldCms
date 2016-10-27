<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>信息推送</title>
		<jsp:include page="../include/include_info_tools.jsp"/>

		<script type="text/javascript">
var SendInfoRPC = jsonrpc.SendInfoRPC;
var SendRecordBean = new Bean("com.deya.wcm.bean.sendInfo.SendRecordBean",true);
var InfoBean = new Bean("com.deya.wcm.bean.cms.info.InfoBean",true);
var site_list;

$(document).ready(function() {
	reloadBefore();
	initButtomStyle();
	getSendConfigList();
	if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
		$("html").css("overflowY", "scroll");

	reloadAfter();
});
	
//得到允许报送的站点列表
function getSendConfigList()
{
	site_list = SendInfoRPC.getSendConfigList();
	site_list = List.toJSList(site_list);
	$("#site_select").addOptions(site_list,"site_id","site_name");
}

//得到允许报送的栏目
function showReceiveCateList(val)
{
	if(val == "")
		return;
	$("#cat_list").empty();
	try{
		var json_data = eval(SendInfoRPC.getReceiveCategoryList(getReceiveSiteDomain(val)));
		if(json_data == null || json_data[0].children.length == 0)
		{
			top.msgWargin("该站点没有可以报送的目录");
			return;
		}else
		{
			for(var i=0;i<json_data[0].children.length;i++)
			{
				$("#cat_list").append('<li style="float:none;height:20px" site_id="'+json_data[0].id+'" site_name="'+json_data[0].text+'"><input type="checkbox" id="cat_id" value="'+json_data[0].children[i].id+'" onclick="selectedSite(this,\''+json_data[0].children[i].id+'\',\''+json_data[0].children[i].text+'\')"><label onclick="lableClickSite(this)">'+json_data[0].children[i].text+'</label></div></li>');
			}
			init_input();
		}
	}
	catch(e)
	{
		top.msgWargin("该站点没有可以报送的目录");
		return;
	}
	
}

//根据站点ID得到域名
function getReceiveSiteDomain(s_id)
{
	for(var i=0;i<site_list.size();i++)
	{
		if(site_list.get(i).site_id == s_id)
			return site_list.get(i).site_domain;
	}
}

//选择站点
function selectedSite(obj,cat_id,cat_name)
{
	
	if($(obj).is(':checked'))
	{		
		var p_obj = $(obj).parent();
		$("#sel_list").append('<li style="float:none;height:20px" site_id="'+p_obj.attr("site_id")+'" site_name="'+p_obj.attr("site_name")+'" cat_name="'+cat_name+'" cat_id="'+cat_id+'">'+p_obj.attr("site_name")+" - "+cat_name+'<img onclick="deleteSiteList(this,\''+cat_id+'\')" src="../../../images/delete.png" width="15" height="15" alt="" align="right"/></li>');
	}
	else
	{
		$("#sel_list li[cat_id='"+cat_id+"']").remove();
	}
	
}

//删除已选站点 
function deleteSiteList(obj,cat_id)
{
	$("#cat_list :checkbox[value='"+cat_id+"']").attr("checked",false);
	$(obj).parent().remove();
}

//点击lable触发事件
function lableClickSite(obj)
{
	$(obj).prev().attr("checked",$(obj).prev().is(':checked') == false ? true:false);
	$(obj).prev().click();
}

function fnOK()
{
	if($("#sel_list li").length == 0)
	{
		top.msgWargin("请选择要报送的站点目录");
		return;
	}
	var info_beans = top.getCurrentFrameObj().getSelectInfoBeans();
	var site_id = top.getCurrentFrameObj().site_id;
	var app_id = top.getCurrentFrameObj().app;
	var sendRecordList = new List();
	$("#sel_list li").each(function(){
		var bean = BeanUtil.getCopy(SendRecordBean);
		var t_site_id = $(this).attr("site_id");
		var t_site_name = $(this).attr("site_name");
		var t_cat_id = $(this).attr("cat_id");
		var t_cat_cname = $(this).attr("cat_name");
		bean.send_site_id = site_id;
		bean.send_app_id = app_id;
		bean.send_user_id = LoginUserBean.user_id;
		bean.t_site_id = t_site_id;
		bean.t_site_name = t_site_name;
		bean.t_cat_id = t_cat_id;
		bean.t_cat_cname = t_cat_cname;
		sendRecordList.add(bean);
		
	});
	var info_list = new List();
	for(var i=0;i<info_beans.size();i++)
	{
		var bean = BeanUtil.getCopy(InfoBean);
		bean.info_id = info_beans.get(i).info_id;
		bean.cat_id = info_beans.get(i).cat_id;
		bean.model_id = info_beans.get(i).model_id;
		bean.site_id = site_id;
		info_list.add(bean);
	}

	var mess = SendInfoRPC.insertSendInfo(sendRecordList,info_list);
	if(mess == "")
	{
		top.msgAlert("信息报送成功");		
		top.CloseModalWindow();
		return;
	}
	if(mess == "false")
	{
		top.msgWargin("信息报送失败,请重新报送");
		return;
	}
	top.msgWargin(mess.substring(1)+" 的站点报送失败，请重新报送至该站点");
	return;
	
}

</script>
</head>
	<body>
		<span class="blank3"></span>
		<form id="form1" name="form1" action="#" method="post">		
		<table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0">
			<tbody>
				<tr>
					<td colspan="2">
						<select id="site_select" class="width200" onchange="showReceiveCateList(this.value)">
							<option value="">请选择报送站点</option>
						</select>
					</td>
				</tr>
				<tr>
				 <td>
					<table class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:430px" align="left">
					  <tbody>
					   <tr>
						<th style="text-align:left">可选栏目：</th>
						<th></th>
						<th style="text-align:left">已选栏目：</th>
					   </tr>
					   <tr>
						<td>
						 <div id="site_div" style="width:200px;height:260px;overflow:auto;" class="border_color">
							<div class="contentBox6 textLeft no_zhehang" style="overflow:auto">
								<ul id="cat_list" class="easyui-tree no_zhehang" animate="true">
								</ul>
							</div>
						 </div>
						</td>
						<td>
						 >>
						</td>
						<td >
						 <div style="width:300px;height:260px;overflow:auto;" class="border_color">
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
					<input type="button" value="确定" class="btn1" onclick="fnOK()" />
					<input type="button" value="取消" class="btn1" onclick="top.CloseModalWindow();" />
				</td>
			</tr>
		</table>
		<span class="blank3"></span>
		</form>
	</body>
</html>