<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String id = request.getParameter("id");	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑推荐信息</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="../styles/uploadify.css"/>
<jsp:include page="../../include/include_tools.jsp"/>
<script src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
<script type="text/javascript" src="../../js/jquery-plugin/iColorPicker.js"></script>
<script type="text/javascript" src="../../js/uploadTools.js"></script>
<script type="text/javascript" src="../../js/jquery.uploadify.js"></script>
<script type="text/javascript" src="../../js/uploadFile/swfobject.js"></script>
<script type="text/javascript" src="js/wareInfo.js"></script>
<script type="text/javascript">

var id = <%=id%>;
var site_id = top.getCurrentFrameObj().site_id;
var WareRPC = jsonrpc.WareRPC;
var WareInfos = new Bean("com.deya.wcm.bean.system.ware.WareInfos",true);
var val=new Validator();
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	publicUploadButtomLoad("uploadify",true,false,"",0,5,site_id,"savePicUrl");
	setPreTitle();
	showSelectDiv("pre_title","select4",120);

	if(id != null && id != "" && id != null)
	{
		defaultBean = WareRPC.getWareInfosBean(id);
		if(defaultBean != null)
		{
			$("#ware_infos").autoFill(defaultBean);	
		}
	}

});

function reSetForm()
{
	
}

function updateWareInfos()
{
	if(!standard_checkInputInfo("ware_infos"))
	{
		return;
	}
	var bean = BeanUtil.getCopy(WareInfos);	
	$("#ware_infos").autoBind(bean);
	bean.id = id;
	if(WareRPC.updateWareInfos(bean))
	{
		top.msgAlert("信息"+WCMLang.Add_success);
		top.getCurrentFrameObj().updateInfoToUL(bean);
		top.CloseModalWindow();
	}else
	{
		top.msgWargin("信息"+WCMLang.Add_fail);
	}
}

function showStringLength(inId, showId){
	$("#"+showId).html($("#"+inId).val().length);
}

function savePicUrl(url)
{
	$("#thumb_url").val(url);
}

function setPreTitle(){
	var beanList = jsonrpc.DataDictRPC.getDataDictList("pre_title");//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	var str = "";
	for(var i=0; i<beanList.size(); i++){
		str += '<li onclick="showValue(\'pre_title\',this)" style="cursor:default;float:none">'+beanList.get(i).dict_name+'</li>'
	}
	$("#selectList_pre").html(str);
}

function showValue(id,o){
	$("#"+id).val($(o).html());
}
</script>
<style>
/*信息添加页面*/
.selectDiv{border-bottom:1px #63c21c solid; font-size:12px;border:1px #7f9db9 solid;}
.selectDiv .listLi li{text-align: left; line-height:15px; height:15px;padding: 0; padding-left:3px; font-size:12px; color:#000; text-decoration:none;;margin:3px 0}
.selectDiv .listLi li:hover {color:#000;text-decoration:none; height:15px; width:100%; background-color: #3399ff; font-size:12px; border:1px #eee dotted;}
.selectDiv .listLi li:active {color:#000;text-decoration:none; height:15px; background-color: #3399ff; font-size:12px;}
.selectDiv .listLi li::visited {color:#000;text-decoration:none; height:15px; font-size:12px;}
</style>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<div id="ware_infos">
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>标题：</th>
			<td>
				<input type="text" id="pre_title" value="" style="width:60px; height:18px; overflow:hidden;" />
				<div id="select4" class="select_div tip hidden border_color selectDiv" style="width:134px; height:30px; ;overflow-x:hidden; overflow:auto; " >
					<div id="leftMenuBox">
						<ul id="selectList_pre" class="listLi"  style="width:134px; height:30px; text-align: left;">
						</ul>
					</div>
				</div>
				<input id="title" name="title" type="text" class="width250" maxlength="80" value="" onkeypress="showStringLength('title','wordnum')" onkeyup="showStringLength('title','wordnum')"  onblur="checkInputValue('title',false,160,'信息标题','')"/>
				<span id="wordnum">0</span>字
				<input id="title_color" name="title_color" type="text" style="width:60px;" class="iColorPicker" onchange="changeTitleColor(this)" value="" /> 				
			</td>
		</tr>
		<tr>
			<th>副标题：</th>
			<td>
				<input id="subtitle" name="subtitle" type="text" class="width300" maxlength="80" value="" onkeypress="showStringLength('subtitle','wordnum2')" onkeyup="showStringLength('subtitle','wordnum2')"/>
				<span id="wordnum2">0</span>字
			</td>
		</tr>
		<tr>
			<th>链接：</th>
			<td>
				<input id="content_url" name="content_url" type="text" class="width300" maxlength="200" value="" />
			</td>
		</tr>
		<tr>
			<th>焦点图片：</th>
			<td>
				<div style="float:left;margin:auto;"><input id="thumb_url" name="thumb_url" type="text" style="width:250px;" value="" /></div>
				<div style="float:left"><input type="file" name="uploadify" id="uploadify"/></div>
				<div style="float:left">&nbsp;<input id="i005" name="i005" type="button" onclick="openSelectMaterialPage('savePicUrl',site_id,'radio')" value="图片库" /></div>
				<div style="float:left">&nbsp;<input id="i005" name="i005" type="button" onclick="changeThumbUrl('thumb_url')" value="使用原图" /></div>
			</td>
		</tr>
		
		<tr>
			<th style="vertical-align:top;">内容摘要：</th>
			<td>
				<textarea id="description" name="description" style="width:300px;height:80px"></textarea>
			</td>
		</tr>
		<tr>
			<th>显示时间：</th>
			<td>
				<input class="Wdate width150" type="text" name="publish_dtime" id="publish_dtime" size="11" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="true" >
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
			<input id="addButton" name="btn1" type="button" onclick="updateWareInfos()" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('ware_infos',id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>

</form>
</body>
</html>
