<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String app_id = request.getParameter("app_id");
	String site_id = request.getParameter("site_id");
	String handl_name = request.getParameter("handl_name");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上传背景图</title>


<link type="text/style" rel="stylesheet" href="/sys/styles/uploadify.style" />
<jsp:include page="../../../include/include_tools.jsp"/>
<script language="javascript" src="/sys/js/jquery.uploadify.js"></script>
<script language="javascript" src="/sys/js/uploadFile/swfobject.js"></script>
<script type="text/javascript" src="/sys/js/uploadTools.js"></script>
<script type="text/javascript">

var action_type = "${param.action_type}";
var site_id = "${param.site_id}";
var img_url = "";
$(document).ready(function(){
	$("label").live("click",function(){
		$(this).prev().click();
	});
	initButtomStyle();
	init_input();
	initTemplateUpLoadSingl("backgoundimg","getImgUrl");
	var obj;
	if(action_type == "layout")
	{
		obj = top.currnet_module.find(".module_body");		
	}else
	{
		obj = top.$("body");		
	}
	
	img_url = obj.css("background-image");		
	if(img_url != "")
	{
		img_url = img_url.substring(5,img_url.length-2);
		$("#img_div").css("background-image","url("+img_url+")");
		$("#img_div").css("background-repeat",obj.css("background-repeat"));
		$("#img_div").css("background-positionY",obj.css("background-positionY"));
		$("#img_div").css("background-positionX",obj.css("background-positionX"));
		
		$(":radio[id='repeat_radio'][value='"+obj.css("background-repeat")+"']").attr("checked",true);
		$(":radio[id='positionX_radio'][value='"+obj.css("background-positionX")+"']").attr("checked",true);
		$(":radio[id='positionY_radio'][value='"+obj.css("background-positionY")+"']").attr("checked",true);
		
	}
});


function getImgUrl(url)
{
	if(url != "")
	{		
		$("#img_div").css("background-image","url('"+url+"')");
		$("#img_div").css("background-repeat",$("#repeat_radio :checked").val());
		$("#img_div").css("background-positionY",$("#positionY_radio :checked").val());
		$("#img_div").css("background-positionX",$("#positionX_radio :checked").val());
		img_url = url;
	}
}

function clearImg()
{
	img_url = "";
	$("#img_div").css("background-image","");
}

function returnValues()
{
	top.setBacgground(img_url,$(":radio[id='repeat_radio'][checked]").val(),$(":radio[id='positionX_radio'][checked]").val(),$(":radio[id='positionY_radio'][checked]").val(),action_type);
	top.CloseModalWindow();
}

function setDivBackgoundAttr(name,val)
{
	$("#img_div").css(name,val);
}
</script>
</head>

<body>
<table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:97%">
	<tbody>
		<tr>			
			<td valign="top" class="150px" style="vertical-align:top;">
				<div>
					<div class="left" class="width150"><input type="file" id="backgoundimg" name="backgoundimg"/></div>
					<div class="left" class="width100">&nbsp;&nbsp;<input id="addButton" name="btn1" type="button" onclick="clearImg()" value="清空素材" /></div>
				</div>
				<div class="blank3"></div>
				<div class="lineHeight25px" style="color:#32609E;">平铺方式</div>
				<div class="lineHeight20px"><input type="radio" id="repeat_radio" name="repeat_radio" value="repeat" checked="true" onclick="setDivBackgoundAttr('background-repeat',this.value)"><label>平铺</label></div>
				<div class="lineHeight20px"><input type="radio" id="repeat_radio" name="repeat_radio" value="no-repeat" onclick="setDivBackgoundAttr('background-repeat',this.value)"><label>不平铺</label></div>
				<div class="lineHeight20px"><input type="radio" id="repeat_radio" name="repeat_radio" value="repeat-y" onclick="setDivBackgoundAttr('background-repeat',this.value)"><label>纵向平铺</label></div>
				<div class="lineHeight20px"><input type="radio" id="repeat_radio" name="repeat_radio" value="repeat-x" onclick="setDivBackgoundAttr('background-repeat',this.value)"><label>横向平铺</label></div>
				<div class="lineHeight25px" style="color:#32609E;">水平方向位置</div>
				<div class="lineHeight20px"><input type="radio" id="positionX_radio" name="positionX_radio" value="left" onclick="setDivBackgoundAttr('background-positionX',this.value)"><label>居左</label></div>
				<div class="lineHeight20px"><input type="radio" id="positionX_radio" name="positionX_radio" value="center" checked="true" onclick="setDivBackgoundAttr('background-positionX',this.value)"><label>居中</label></div>
				<div class="lineHeight20px"><input type="radio" id="positionX_radio" name="positionX_radio" value="right" onclick="setDivBackgoundAttr('background-positionX',this.value)"><label>居右</label></div>
				<div class="lineHeight25px" style="color:#32609E;">垂直方向位置</div>
				<div class="lineHeight20px"><input type="radio" id="positionY_radio" name="positionY_radio" value="top" onclick="setDivBackgoundAttr('background-positionY',this.value)"><label>居顶</label></div>
				<div class="lineHeight20px"><input type="radio" id="positionY_radio" name="positionY_radio" value="center" checked="true" onclick="setDivBackgoundAttr('background-positionY',this.value)"><label>居中</label></div>
				<div class="lineHeight20px"><input type="radio" id="positionY_radio" name="positionY_radio" value="bottom" onclick="setDivBackgoundAttr('background-positionY',this.value)"><label>居底</label></div>
			</td>
			<td>
			   <div class="left" id="img_div" style="width:528px;height:310px;overflow:auto;background:#FFFFFF;" class="border_color" ></div>
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
			<input id="addButton" name="btn1" type="button" onclick="returnValues()" value="保存" />
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>

</body>
</html>