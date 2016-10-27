<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>素材上传</title>
	
	
	<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
	<jsp:include page="../../include/include_tools.jsp"/>
	<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
	<script type="text/javascript" src="../../js/jquery.uploadify.js"></script>
	<script type="text/javascript" src="../../js/swfobject.js"></script>
	<script type="text/javascript" src="../js/mateInfoList.js"></script>
	<script type="text/javascript" src="../js/matefolder.js"></script>
	<script type="text/javascript">
	
	var f_id = "<%=request.getParameter("f_id")%>";
	var site_id ="<%=request.getParameter("site_id")%>";
	var user_id ="<%=request.getParameter("user_id")%>";
	
	$(document).ready( function() {
   		$("#uploadify").uploadify( {//初始化函数

	    'uploader' :'../images/uploadify.swf',//flash文件位置，注意路径
	    'script' :'/WCM/UploadFileIfy',//后台处理的请求
	    'cancelImg' :'../images/cancel.png',//取消按钮图片
		'buttonImg': '../images/upst.gif',
	    'folder' :'uploads',//您想将文件保存到的路径
	    'queueID' :'fileQueue',//与下面的上传文件列表id对应
	    'queueSizeLimit' :100,//上传文件的数量
	    'scriptData':{'is_press':'1','press_osition':'5'},//向后台传的数据
	    'fileDesc' :'gif文件或jpg文件或png文件',//上传文件类型说明
	    'fileExt' :'*.gif;*.jpg;*.png;*.jpeg', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
	    'method':'get',//如果向后台传输数据，必须是get
	    'sizeLimit':10000000,//文件上传的大小限制，单位是字节
	    'auto' :false,//是否自动上传
	    'multi' :true,
	    'simUploadLimit' :5,//同时上传文件的数量
	    'buttonText' :'BROWSE',//浏览按钮图片
		'auto' : false,//点击选择直接上传图片
		'onSelect':function(event, queueID, fileObj){//选择完后触发的事件
			alert(fileObj.size);
		},
	    'onComplete': function(event, queueID, fileObj,serverData,response,data) {//当上传完成后的回调函数，ajax方式哦~~
	     alert(queueID); 
		 alert(fileObj.filePath)
		 alert(data.response)
    }
   });
});
</script>
<style>
	#container{
		width:100%;
		height:auto;
		text-align:center;
	}
	#fileQueue {
		text-align:center;
		width: 600px;
		height: 320px;
		overflow: auto;
		border: 1px solid #E5E5E5;
		margin-bottom: 10px;
		margin-top: 10px;
	}
	/* --Uploadify -- */
	.uploadifyQueueItem {
		font: 11px Verdana, Geneva, sans-serif;
		border: 2px solid #E5E5E5;
		background-color: #F5F5F5;
		margin-top: 5px;
		padding: 10px;
		width: 400px;
	}
	.uploadifyProgressBar {
		background-color: #0099FF;
		width: 1px;
		height: 3px;
		border:1px solid  red;
	}
	#fileQueue .uploadifyQueueItem {
		font: 11px Verdana, Geneva, sans-serif;
		border: none;
		border-bottom: 1px solid #E5E5E5;
		background-color: #FFFFFF;
		padding: 5%;
		width: 90%;
	}
	#fileQueue .uploadifyError {
		background-color: #FDE5DD !important;
	}
	#fileQueue .uploadifyQueueItem .cancel {
		float: right;
	}
	.uploadifyError {
		border: 2px solid #FBCBBC !important;
		background-color: #FDE5DD !important;
	}
	.uploadifyQueueItem .cancel {
		float: right;
	}
	.uploadifyProgress {
		background-color: #FFFFFF;
		border-top: 1px solid #808080;
		border-left: 1px solid #808080;
		border-right: 1px solid #C5C5C5;
		border-bottom: 1px solid #C5C5C5;
		margin-top: 10px;
		width: 100%;
	}
 </style>
 </head>
 <body>
 <div id="container">
	   <div id="fileQueue"></div>
	   <input type="file" name="uploadify" id="uploadify" />
	   <p>
		    <a href="javascript:jQuery('#uploadify').uploadifyUpload()">开始上传</a>&nbsp;
		    <a href="javascript:jQuery('#uploadify').uploadifyClearQueue()">取消所有上传</a>
	   </p>
 </div>
 </body>
 </html>
</body>
</html>
