<%@ page contentType="text/html; charset=utf-8"%>
<%
String cid = request.getParameter("cid");
String siteid = request.getParameter("site_id");
String infoid = request.getParameter("info_id");
String app_id = request.getParameter("app_id");
String model = request.getParameter("model");
if(cid == null || cid.equals("null")){
	cid = "0";
}
if(app_id == null || app_id.equals("null")){
	app_id = "0";
}
if(model == null || !model.matches("[0-9]*")){
	model = "0";
}
String topnum = request.getParameter("topnum");
if(topnum == null || topnum.trim().equals("") || topnum.trim().equals("null") ){
	topnum = "0";
}
String currentDateTime = com.deya.util.DateUtil.getCurrentDateTime("yyyy-MM-dd HH:mm");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>链接内容模型</title>
<jsp:include page="../include/include_info_tools.jsp"/>
<script type="text/javascript" src="js/m_link.js"></script>
<script type="text/javascript">

var topnum = "<%=topnum%>";
var site_id = "<%=siteid%>";
var cid = "<%=cid%>";
var app_id = "<%=app_id%>";
var infoid = "<%=infoid%>";
var contetnid = "info_content";
var linksInfo = "";
var focusInfo = "";
var mFlag = false;
var step_id="0"

$(document).ready(function(){
	$("#is_pic").hide();
	$("#is_pic").next().hide();

	initButtomStyle();
	init_input();
	reloadPublicGKInfo();
	$(":hidden[id='content_url']").remove();

	
	publicUploadButtomLoad("uploadify",true,false,"",0,5,getReleSiteID(),"savePicUrl");

	//$("#t1").hide();
	//$("#t2").hide();
	$("#t3").hide();
	$("#t4").hide();

	if(infoid != "" && infoid != "null" && infoid != null){		
		defaultBean = ModelUtilRPC.select(infoid,site_id,"link");
		if(defaultBean)
		{
			/*
			var title = defaultBean.title;
		    defaultBean.title = title.replace(/.*?<span[\s]*.*style=[\"|\'](.*?)[\"|\']>(.*?)<(.*)/ig,"$2");
			var style = title.replace(/.*?<span[\s]*.*style=[\"|\'](.*?)[\"|\'](.*)/ig,"$1"); 
	
			var strTitle = "\""+title+"\"";
			if(strTitle.indexOf("font-style")>0)
			{
				fontObliqueType="isOblique";
			} 
			if(strTitle.indexOf("font-weight")>0)
			{
				fontBoldType="isBold";
			}
			if(strTitle.indexOf("letter-spacing")>0)
			{
				var index = strTitle.indexOf("letter-spacing")+15; 
				var updatefontspace = strTitle.substring(index,index+1);
				fontspacesize = updatefontspace; 
				fontSpaceType="isSpace";
			}
			if(strTitle.indexOf("font-size")>0)
			{
				var index = strTitle.indexOf("font-size")+10; 
				var updatefontsize = strTitle.substring(index,index+2);
				fontSize = updatefontsize;
				fontSizeType="change";
			}
			
			$("#title").attr("style",style);
			*/
            defaultBean.step_id=step_id;
			$("#info_article_table").autoFill(defaultBean);				
			publicReloadUpdateGKInfos();
            if(defaultBean.info_status=='3'||defaultBean.info_status=='4'||defaultBean.info_status=='8'){//这些状态下修改信息的时候可以选择走保密审查
                if(defaultBean.info_status=='4'){
                    $("#audit_tr").addClass("hidden");
                    $("#li_ds").addClass("hidden");
                    $("#opt_bmsc input").attr("checked",'true');
                }
                $("#opt_bmsc").removeClass("hidden");
            }else if(defaultBean.info_status=='2'){//待审信息修改的时候屏蔽选择审批步骤和发布状态的按钮
                $("#info_staus_tr").addClass("hidden");
                $("#audit_tr").addClass("hidden");
            }else{
                $("#opt_bmsc").addClass("hidden");
            }
		}
		$("#addButton").click(updateInfoData);		
		mFlag = true;		
	}
	else
	{
	    $("#input_dtime").val("<%=currentDateTime%>");
		$("#addButton").click(addInfoData);		
		mFlag = false;
	}
});



function savePicUrl(url)
{
	$("#thumb_url").val(url);
}
</script>
</head>

<body>
<span class="blank12"></span>
<form action="#" name="form1">
<div id="info_article_table">
<jsp:include page="../include/include_public_gk.jsp"/>

<!-- 内容主体不同部分　开始 -->
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th ><span class="f_red">*</span>链接：</th>
			<td style="">
				<div style="float:left"><input id="content_url" name="content_url" type="text" class="width350" maxlength="900" value="http://" onblur="checkInputValue('content_url',false,900,'链接','')"/></div>

			</td>
		</tr>
		<tr>
			<th>缩略图：</th>
			<td>
				<div style="float:left;margin:auto;"><input id="thumb_url" name="thumb_url" type="text" style="width:250px;" value="" /></div>
				<div style="float:left"><input type="file" name="uploadify" id="uploadify"/></div>
				<div style="float:left">&nbsp;<input id="i005" name="i005" type="button" onclick="selectPageHandle()" value="图片库" /></div>
			</td>
		</tr>
	  </tbody>
	</table>
<!-- 内容主体不同部分　结束 -->

<jsp:include page="../include/include_public_high_gk.jsp"/>

</div>

<!--隔线开始-->
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<!--隔线结束-->
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />
			<input id="addReset" name="btn1" type="button" onclick="window.location.reload()" value="重置" />
			<input id="addCancel" name="btn1" type="button" onclick="tab_colseOnclick(curTabIndex)" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</div>
</form>
</body>
</html>
