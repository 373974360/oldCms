<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.deya.util.DateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择素材                                                                                                   </title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="../styles/uploadify.css"/>
<link rel="stylesheet" type="text/css" href="/sys/styles/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../js/jquery-ui/themes/base/jquery.ui.all.css"  />
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="../../js/jquery-plugin/fancybox/jquery.fancybox.css" media="screen" />
<script type="text/javascript" src="../../js/jquery-plugin/fancybox/jquery.mousewheel.pack.js"></script>
<script type="text/javascript" src="../../js/jquery-plugin/fancybox/jquery.fancybox.pack.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="../../js/jquery.uploadify.js"></script>
<script type="text/javascript" src="../../js/swfobject.js"></script>
<script type="text/javascript" src="../js/mateList.js"></script>
<script type="text/javascript" src="/sys/js/jquery.uploadify.js"></script>
<script type="text/javascript" src="/sys/js/uploadFile/swfobject.js"></script>
<script type="text/javascript" src="../../js/jquery-ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="../../js/jquery-ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="../../js/jquery-ui/jquery.ui.mouse.js"></script>
<script type="text/javascript" src="../../js/jquery-ui/jquery.ui.button.js"></script>
<script type="text/javascript" src="../../js/jquery-ui/jquery.ui.draggable.js"></script>
<script type="text/javascript" src="../../js/jquery-ui/jquery.ui.position.js"></script>
<script type="text/javascript" src="../../js/jquery-ui/jquery.ui.dialog.js"></script>
<script type="text/javascript">
input_type ="<%=request.getParameter("i_type")%>";

var f_id ="<%=request.getParameter("f_id")%>";
if(f_id =="" || f_id == null || f_id == "null"){
	f_id = "0";
}

var aidnum = window.dialogArguments;

var site_id ="<%=request.getParameter("site_id")%>";
var handl_name ="<%=request.getParameter("handl_name")%>";
var user_id = aidnum.LoginUserBean.user_id;
var json_data;
var imgDomain;
var pageSize = 20;
var list_pageSize = 60;//列表形式时显示的初始个数

$(document).ready(function(){
	
	if(input_type == "checkbox")
	{
		$("#returnButton").removeClass("hidden");
	}

	imgDomain = MateInfoRPC.getImgDomain();
	initButtomStyle();
	init_input();
	iniLeftMenuTree();
	treeNodeSelected(f_id);
	
	iniUploadButtom();
	iniSuCaiList();
	iniSuCaiPage();
	getMateInfoList(f_id,"custom",site_id,imgDomain);
	setScrollHandl();
	
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
});
	$(window).resize(function(){
		iniSuCaiPage();	
	});



function iniUploadButtom()
{
	$("#uploadify").uploadify( {//初始化函数
	    'uploader' :'../images/uploadify.swf',//flash文件位置，注意路径
	    'script' :''+imgDomain+'/servlet/UploadFileIfy',//后台处理的请求
		'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/sys/js/uploadFile/upst.gif',
	    'folder' :'folder',//您想将文件保存到的路径
	    'queueID' :'fileQueue',//与下面的上传文件列表id对应
	    'queueSizeLimit' :50,//上传文件的数量
	    //'scriptData' :{'is_press':0,'press_osition':5,'site_id':site_id},//向后台传的数据
	    'fileDesc' :'jpg,jpeg,gif,png,doc,docx,txt,zip',//上传文件类型说明
		'fileExt' :'*.jpg;*.jpeg;*.gif;*.png;*.doc;*.docx;*.txt,*.zip;', //控制可上传文件的扩展名,启用本项时需同时声明fileDesc
	    'method':'get',//如果向后台传输数据，必须是get
	    'sizeLimit':10000000,//文件上传的大小限制，单位是字节
	    'auto' :true,//是否自动上传
	    'multi' :true,
	    'simUploadLimit' :1,//同时上传文件的数量
	    'buttonText' :'BROWSE',//浏览按钮图片
		'onSelect':function(event,queueID,fileObj){//选择完后触发的事件
			$("#uploadify").uploadifySettings('scriptData',{'is_press':0,'press_osition':5,'site_id':site_id,'sid':jsonrpc.MateInfoRPC.getUploadSecretKey()});
			if(fileObj.size > 10000000){
				alert("附件过大，不允许上传！");
				return;
			}
		},
		'onError':function(event,queueID,fileObj,errorObj){
			alert("文件:" +fileObj.name + "上传失败！");	
		},
	    'onComplete': function(event,queueID,fileObj,response,data){//当上传完成后的回调函数，ajax方式哦~~
	
			var arr_id = MateInfoRPC.getArrIdFromTable();
			var att_path = "";  
			var att_ename = "";
			var hd_url ="";
			var thumb_url =""; 
			var tt_att_ename = "";
			var tt_thumb_url = "";
			var tmpPicLi  = "";
			var objPath =  jQuery.parseJSON(response);
			var oldname = fileObj.name;
			att_path = objPath.att_path;
		    att_ename = objPath.att_ename;
		    hd_url = objPath.hd_url;
		    thumb_url = objPath.thum_url;
		   
			addMateInfo(arr_id,0,site_id,att_path,hd_url,thumb_url,att_ename,oldname,fileObj.size);

			if( oldname !="" && oldname.length > 10){
				oldname = oldname.substring(0,10);
			}
		
			tmpPicLi = "<li id='"+arr_id+"'><a href='"+imgDomain + att_path + att_ename+"' title='"+fileObj.name+"'  class=\"fancybox\"><img src='"+imgDomain + att_path + thumb_url+"'/></a>";

			var click_str = "";
			if(input_type == "radio")
			{
				click_str = 'onclick="selectPicUrlHandl(\''+imgDomain + att_path + att_ename+'\',\''+oldname+'\')"';				
			}

			tmpPicLi += "<div><input id='"+arr_id+"' name='"+arr_id+"' type='"+input_type+"' "+click_str+"/><label>"+oldname+"</label></div></li>";
			$("#sucaiList").prepend(tmpPicLi);
 	    },
 	   	'onAllComplete': function(event,data){
 	    	CloseUploadSpan();
 	    	init_input();
 	   		iniFancybox();
 	   		iniSuCaiList();
 	    }
   });
}

//返回选中的图片
function returnSelectedPic()
{
	var urls = "";
	var names = "";
	$(":checked").each(function(i){
		if(i > 0)
		{
			urls += ",";
			names += ",";
		}
		urls += $(this).parent().parent().find("a").attr("href");		
		names += $(this).parent().parent().find("a").attr("title");
	});
	eval("aidnum."+handl_name+"('"+urls+"','"+names+"')");
	window.close();
}

//选择图片，并返回路径
function selectPicUrlHandl(url,oldname)
{alert(oldname)
	//eval("aidnum."+handl_name+"('"+url+"','"+oldname+"')");
	//window.close();
}
</script>
</head>
<body>

<table style="width:100%" border="0" cellpadding="0" cellspacing="0" >
	<tr>
	 <td width="160px" valign="top">
		<div id="leftMenuBox">
			<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
				<ul id="leftMenuTree" class="easyui-tree" animate="true">
				</ul>
			</div>
		</div>
	 </td>
	 <td class="width10"></td>
	 <td valign="top">		
		<table class="table_option fromTabs" border="0" cellpadding="0" cellspacing="0">
			<tr>				
				<td align="right" valign="middle" style="width:190px;overflow:hidden;">
					<div style="float:right;text-align:left;">&nbsp;&nbsp;<input id="returnButton" class="hidden" name="btn1" type="button" onclick="returnSelectedPic()" value="确定" /></div>
					<div style="float:right;text-align:left;"><input type="file" name="uploadify" id="uploadify"/></div>					
					<span class="blank3"></span>
				</td>
				<td style="width:20px;"></td>
			</tr>
		</table>
		
		<div id="SucaiListArea" class="contentBox6">
			<ul id="sucaiList" class="imgList">
			</ul>
			<span class="blank3"></span>
		</div>	
	 </td>
	</tr>
</table>
</body>
</html>