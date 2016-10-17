<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>依申请公开业务</title>


<link type="text/css" rel="stylesheet" href="/sys/styles/main.css" />
<link type="text/css" rel="stylesheet" href="/sys/styles/sub.css" />
<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
<script type="text/javascript" src="/sys/js/jquery.js"></script>
<script type="text/javascript" src="/sys/js/jsonrpc.js"></script>
<script type="text/javascript" src="/sys/js/java.js"></script>
<script type="text/javascript" src="/sys/js/extend.js"></script>
<script type="text/javascript" src="/sys/js/jquery.c.js"></script>
<script type="text/javascript" src="/sys/js/common.js"></script>
<script type="text/javascript" src="/sys/js/validator.js"></script>
<script type="text/javascript" src="/sys/js/lang/zh-cn.js"></script>
<script type="text/javascript" src="/sys/js/sysUI.js"></script>
<script type="text/javascript" src="/sys/js/jquery.uploadify.js"></script>
<script type="text/javascript" src="/sys/js/uploadFile/swfobject.js"></script>
<script type="text/javascript" src="/sys/js/uploadTools.js"></script>
<script type="text/javascript" src="js/queryConf.js"></script>
<script type="text/javascript">
var MateInfoRPC = jsonrpc.MateInfoRPC;
var site_id = "<%=request.getParameter("site_id")%>";
var conf_id = "<%=request.getParameter("conf_id")%>";
$(document).ready(function(){
	initButtomStyle();
	init_input();
	loadExcelUpload();
});

var fileCount = 0;
function loadExcelUpload()
{
	$("#uploadify").uploadify( {//初始化函数
		'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
	    'script' :'/servlet/PeculiarUploadFile',//后台处理的请求
		'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/sys/js/uploadFile/upst.gif',
		'folder' :'uploads',//您想将文件保存到的路径
		'queueID' :'fileQueue',//与下面的上传文件列表id对应
		'queueSizeLimit' :1,//上传文件的数量
		//'scriptData':{'app_id':'appeal'},//向后台传的数据
		'fileDesc' :'xlsx,xls',//上传文件类型说明
		'fileExt' :'*.xlsx;*.xls', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
		'method':'get',//如果向后台传输数据，必须是get
		'sizeLimit':5242880,//文件上传的大小限制，单位是字节
		'auto' :true,//是否自动上传
		'multi' :false,
		'simUploadLimit' :1,//同时上传文件的数量
		'buttonText' :'BROWSE',//浏览按钮图片
		'onSelect':function(event,queueID,fileObj){//选择完后触发的事件
			
		   $("#uploadify").uploadifySettings('scriptData',{'app_id':'query','sid':MateInfoRPC.getUploadSecretKey()});
		   if(fileObj.size > 10000000){
				alert("附件过大，不允许上传！");
				return;
		   }		
		},
		'onSelectOnce':function(event,data){
			fileCount = data.fileCount;			
		},
		'onCancel':function(event,queueId,fileObj,data){
			fileCount = data.fileCount;
		},
		'onComplete': function(event,queueID,fileObj,serverData,response,data) {//当上传完成后的回调函数
			$("#file_url").val(serverData);
		}
   });
}

function setExcleItem(conf_id,site_id){
	var file_url = $("#file_url").val();
	if(file_url == ""){
		top.msgAlert("请上传Excel文件！");
		return;
	}else{
		if(QueryItemRPC.writeExcelItems(file_url,conf_id,site_id))
		{
			top.msgAlert("操作"+WCMLang.Add_success);
			window.location.href = "query_conf.jsp?site_id="+site_id;
		}
		else
		{
			top.msgWargin("操作"+WCMLang.Add_fail);
		}
	}
}
</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<div id="queryExcel_table" style="width:700px;">
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th valign="top" align="right">Excel文件: </th>
			<td>
				 <div style="float:left;margin:auto;">
					 <input id="file_url" name="file_url" type="text" style="width:250px;" value=""/>
					 <div style="height:5px;"></div>
					 <div id="fileQueue"></div>
					 <input id="uploadify" name="uploadify" type="file" style="width:200px;height24px;"/>
					 (只允许上传格式为:xls,xlsx)
				</div>
			</td>
		</tr>
	</tbody>
</table>
</div>
<!-- 分隔线开始 -->
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<!-- 分隔线结束 -->
 
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="setExcleItem(conf_id,site_id)" value="保存"/>
			<input id="btnCancel" name="btn1" type="button" onclick="window.history.go(-1)" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>		
</html>
