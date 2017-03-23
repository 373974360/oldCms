<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.deya.util.DateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>素材库管理</title>


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
var f_id ="<%=request.getParameter("f_id")%>";
if(f_id =="" || f_id == null || f_id == "null"){
	f_id = "0";
}
var site_id ="<%=request.getParameter("site_id")%>";
var app_id = "cms";
var user_id = parent.LoginUserBean.user_id;
var json_data;
var imgDomain;
var pageSize = 0;
var list_pageSize = 0;//列表形式时显示的初始个数

var isFirstUpload = "";

$(document).ready(function(){
	imgDomain = MateFolderRPC.getImgDomain(site_id);
	initPageSize();
	getMaterialConfig();
	initButtomStyle();
	init_input();
	iniLeftMenuTree();
	treeNodeSelected(f_id);
	
	iniUploadButtom();
	iniSuCaiList();
	iniSuCaiPage();
	getMateInfoList(f_id,"custom",site_id,imgDomain);
	
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
});

$(window).resize(function(){
	initPageSize();
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
	    'fileDesc' :file_desc,//上传文件类型说明
		'fileExt' :file_ext, //控制可上传文件的扩展名,启用本项时需同时声明fileDesc
	    'method':'get',//如果向后台传输数据，必须是get
	    'sizeLimit':524288000,//文件上传的大小限制，单位是字节
	    'auto' :false,//是否自动上传
	    'multi' :true,
	    'simUploadLimit' :1,//同时上传文件的数量
	    'buttonText' :'BROWSE',//浏览按钮图片
		'onSelect':function(event,queueID,fileObj){//选择完后触发的事件
			openUploadSpan();
			if(fileObj.size > 524288000){
				alert("附件过大，不允许上传！");
				return;
			}
		},
		'onError':function(event,queueID,fileObj,errorObj){
			alert("文件:" +fileObj.name + "上传失败！");	
		},
	    'onComplete': function(event,queueID,fileObj,response,data){//当上传完成后的回调函数，ajax方式哦~~
		    
			var checkedNode = $('#leftMenuTree').tree('getSelected');
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

			var folder_id = checkedNode.id+"";
		    if(folder_id.indexOf("type") == 0)
				folder_id = 0;
			addMateInfo(arr_id,folder_id,site_id,att_path,hd_url,thumb_url,att_ename,oldname,fileObj.size);
			saveFilePathInMap2(arr_id,att_ename,att_path,thumb_url,hd_url);//路径信息存入map
			if( oldname !="" && oldname.length > 10){
				oldname = oldname.substring(0,10);
			}
		
			var t_thumb_url = showPreviewBySuffix(imgDomain + att_path + thumb_url);
			var fan_class = "";
			if(".gif,.jpg,.png,.jpeg,.swf".indexOf(thumb_url) > -1)
			{
				fan_class = 'class=\"fancybox\"';
			}

			tmpPicLi = "<li id='"+arr_id+"'><a href='"+imgDomain + att_path + att_ename+"' title='"+oldname+"' "+fan_class+"><img src='"+t_thumb_url+"'/></a>";
			tmpPicLi += "<div><input id='"+arr_id+"' name='"+arr_id+"' type='checkbox'/><label>"+oldname+"</label></div></li>";

			if($("#sucaiList").children().length == 1 && isFirstUpload == "")
			{
				$("#sucaiList").empty();
				isFirstUpload = "NO";
			}
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
function beginUpload(){
	$("#uploadify").uploadifySettings('scriptData',{'is_press':1,'press_osition':0,'site_id':site_id,'sid':jsonrpc.MateInfoRPC.getUploadSecretKey()});
	$('#uploadify').uploadifyUpload();
}
</script>
</head>
<body>
<table class="table_option fromTabs" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="i006" name="i006" type="button" onclick="MateFolderClick(site_id,'1')" value="添加目录" />
			<input id="i006" name="i006" type="button" onclick="MateFolderClick(site_id,'2')" value="修改" />
			<input id="i006" name="i006" type="button" onclick="MateFolderClick(site_id,'3')" value="删除" />
			<span class="blank3"></span>
		</td>
		<td align="right" valign="middle" id="user_search" class="search_td">
			<input type="radio" id="ctype" name="ctype" value="0"/><label for="all">全部</label>
			<input type="radio" id="ctype" name="ctype" value="1" checked="true"/><label for="self">当前</label>
			<input type="text"  id="searchkey" name="searchkey" class="input_text" value="" />
			<input type="text" id="beignTime" name="beignTime"  size="11" class="input_text" value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true">至&nbsp;<input type="text" id="endTime" name="endTime"  size="11" class="input_text" value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true">
			<input type="button" id="btnSearch"  class="btn x2" value="搜索" onclick="getSearchInfos(site_id)"/>
			<span class="blank3"></span>
		</td>
		<td style="width:20px;"></td>
	</tr>
</table>
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
	 <td valign="top" style="white-space:normal">	
	    <div id="SucaiListAreaDiv">	
	    	<div id="tableoption" style="display:block">
				<table class="table_option fromTabs" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="left" valign="middle" style="padding-top:4px;" width="160px">
							<span class="showList piclist pointer" onclick="showSuCai('piclist',this)">缩图显示</span>
							<!-- <span class="showList list pointer" onclick="showSuCai('list',this)" >列表显示</span> -->
							<span class="showList detail_list pointer" onclick="showSuCai('detail_list',this)" >详细列表</span>
							<span class="blank3"></span>
						</td>
						<td align="left" valign="middle" style="padding-top:4px;">
							<span onclick="selectSuCai('all')" class="pointer">全选</span> | <span onclick="selectSuCai('other')" class="pointer">反选</span>
							<span class="blank3"></span>
						</td>
						<td align="left" valign="middle" style="width:110px;">
							已显示<span id="sucaiPageCount"></span>/<span id="sucaiFolderCount"></span>项
						</td>
						<td align="right" valign="middle" style="width:220px;overflow:hidden;">
							<div style="float:left;text-align:left;"><input type="file" name="uploadify" id="uploadify"/></div>
							<div style="float:left;text-align:left;">&#160;<input id="btn3" name="btn3" type="button" onclick="MoveClick()" value="转移" />
							<input id="btn3" name="btn3" type="button" onclick="MateInfoClick('1',site_id)" value="编辑" />
							<input id="btn3" name="btn3" type="button" onclick="MateInfoClick('2',site_id)" value="删除" /></div>
						</td>
						<td style="width:20px;"></td>
					</tr>
				</table>
			</div>
			<div id="SucaiListArea" class="contentBox6" style="display:block">
				<ul id="sucaiList" class="imgList">
				</ul>
				<span class="blank3"></span>
			</div>
		 </div>	
		 <div id="folderListDiv" style="display:none">
		      <ul id="folderLists"> 
			  </ul>
		 </div>	
	 </td>
	</tr>
</table>
<div id="fileUploadSpan" name="fileUploadSpan" class="hidden" title="上传素材">	
	<div class="msg_left_div back_img"></div>
	<div class="msg_right_div">
		<table border="0" cellpadding="0" cellspacing="0" align="left" height="270px" style="width:100%;">
			<tr><td valign="middle" align="left" class="lineHeight20px" >
				<div id="fileQueue" style="text-align:center; height:260px; overflow:auto;">
				</div>
			</td></tr>
		</table>	 
	</div>		
	<span class="blank3"></span>
	<div class="line2h"></div>
	<span class="blank3"></span>
	<div class="textCenter">
		<input id="i005" name="i005" type="button" onclick="beginUpload()" hidefocus="true" value="开始上传" />
		<input id="i005" name="i005" type="button" onclick="CloseUploadSpan();jQuery('#uploadify').uploadifyClearQueue()"  hidefocus="true" value="取消所有上传" />
	</div>
	<span class="blank3"></span>
</div>
<iframe id="delete_frame" class="hiddel" src="" width="0" height="0"></iframe>
</body>
</html>