<%@ page contentType="text/html; charset=utf-8"%>
<%
	out.println(request.getSession().getId());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>诉求列表</title>



<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript"  src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
<script language="javascript" src="../../js/jquery.uploadify.js"></script>
<script language="javascript" src="../../js/uploadFile/swfobject.js"></script>

<script type="text/javascript" src="js/sqAdd.js"></script>
<script type="text/javascript">

var model_bean;

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	getPurposeList();
	getModelList();
	showSPDept($("#model_id :selected").val());

	initUPLoad();
});


//
function getModelList(){
	var p_list = SQModelRPC.getAllSQModelList();
	p_list = List.toJSList(p_list);
	$("#model_id").addOptions(p_list,"model_id","model_cname");
}

function showSPDept(val)
{
	var model_bean = SQModelRPC.getModelBean(val);	
	$("#do_dept").empty();
	if(model_bean.relevance_type == 0)
	{	
		var dept_map = getModelDeptMapByMID(val);	
		for(var i=0;i<dept_map.keySet().length;i++)
		{
			$("#do_dept").addOptionsSingl(dept_map.keySet()[i],dept_map.get(dept_map.keySet()[i]));
		}
	}else
	{
		var list = getModelLeadListByMID(val);		
		if(list != null && list.size() > 0)
		{			
			$("#do_dept").addOptions(list,"dept_id","lead_name");
		}		
	}
	getQueryCode(val);
}

var attBean = null;
var fileCount = 0;
function initUPLoad()
{
	var sizeLimit = SQRPC.getAppealFileSize();
	
	 $("#uploadify").uploadify( {//初始化函数
		'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
		'script' :'http://img.site.com/sys/PeculiarUploadFile',//后台处理的请求
		'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/sys/js/uploadFile/upst.gif',
		'folder' :'uploads',//您想将文件保存到的路径
		'queueID' :'fileQueue',//与下面的上传文件列表id对应
		'queueSizeLimit' :100,//上传文件的数量
		'scriptData':{'app_id':'appeal','sid':getSessionID()},//向后台传的数据
		'fileDesc' :'txt,doc,docx,rar,zip',//上传文件类型说明
		'fileExt' :'*.txt;*.doc;*.docx;*.rar;*.zip', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
		'method':'get',//如果向后台传输数据，必须是get
		'sizeLimit':sizeLimit,//文件上传的大小限制，单位是字节
		'auto' :true,//是否自动上传
		'multi' :false,
		'simUploadLimit' :2,//同时上传文件的数量
		'buttonText' :'BROWSE',//浏览按钮图片
		'auto' : false,//点击选择直接上传图片
		'onSelect':function(event, queueID, fileObj){//选择完后触发的事件
			if(fileObj.size > sizeLimit)
			{
				alert("附件太大");
				return;
			}			
		},
		'onSelectOnce':function(event,data){
			fileCount = data.fileCount;			
		},
		'onCancel':function(event,queueId,fileObj,data){
			fileCount = data.fileCount;
		},
		'onComplete': function(event, queueID, fileObj,serverData,response,data) {//当上传完成后的回调函数		 
			 var s = serverData;
			 attBean = BeanUtil.getCopy(attBean);	
			 attBean.att_name = fileObj.name;
			 attBean.att_path = serverData;
			 insertSQ();
		}
   });
}
</script>
</head>
<body>
<div>
	<table id="sq" width="50%">
		<tr>
			<td>业务类型：</td>
			<td><select id="model_id" style="width:200px" onchange="showSPDept(this.value)"></select></td>
		</tr>
		<tr>
			<td>提交部门：</td>
			<td>
			 <select id="do_dept" style="width:200px"></select>
			</td>
		</tr>
		<tr>
			<td>真实姓名</td>
			<td><input type="text" id="sq_realname"  value="sq_realname"></td>
		</tr>
		<tr>
			<td>性别</td>
			<td><input type="text" id="sq_sex" value="1"></td>
		</tr>
		<tr>
			<td>职业</td>
			<td><input type="text" id="sq_vocation" value="sq_vocation"></td>
		</tr>
		<tr>
			<td>年龄</td>
			<td><input type="text" id="sq_age" value="10"></td>
		</tr>
		<tr>
			<td>地址</td>
			<td><input type="text" id="sq_address" value="sq_address"></td>
		</tr>
		<tr>
			<td>邮箱</td>
			<td><input type="text" id="sq_email" value="sq_email"></td>
		</tr>
		<tr>
			<td>固定电话</td>
			<td><input type="text" id="sq_tel" value="sq_tel"></td>
		</tr>

		<tr>
			<td>手机</td>
			<td><input type="text" id="sq_phone" value="sq_phone"></td>
		</tr>
		<tr>
			<td>身份证ID</td>
			<td><input type="text" id="sq_card_id" value="sq_card_id"></td>
		</tr>		
		<tr>
			<td>是否公开</td>
			<td><input type="text" id="is_open" value="0"></td>
		</tr>
		
		<tr>
			<td>标题</td>
			<td><input type="text" id="sq_title"  value="sq_title"></td>
		</tr>
		<tr>
			<td>诉求内容</td>
			<td><input type="text" id="sq_content" value="sq_content"></td>
		</tr>
		<tr>
			<td>信件类型</td>
			<td><input type="text" id="sq_type" value="1"></td>
		</tr>
		<tr>
			<td>查询码</td>
			<td><input type="text" id="query_code"  value="query_code"></td>
		</tr>		
		<tr>
			<td>诉求目的</td>
			<td><select id="pur_id" style="width:200px"></select></td>
		</tr>
		<tr>
			<td>来信人数</td>
			<td>
				<input type="radio" id="people_num" name="people_num" value="1">1-4
				<input type="radio" id="people_num" name="people_num" value="2">5-99
				<input type="radio" id="people_num" name="people_num" value="3">100-999
				<input type="radio" id="people_num" name="people_num" value="4">1000人以上
			</td>
		</tr>
		<tr>
			<td>提交时间</td>
			<td><input class="Wdate width150" type="text" name="sq_dtime" id="sq_dtime" size="11" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true,readOnly:true})" readonly="true" ></td>
		</tr>
		<tr>
			<td>附件</td>
			<td>
			<div id="fileQueue"></div>
			<input type="file" name="uploadify" id="uploadify" />			
			<p>
				<a href="javascript:jQuery('#uploadify').uploadifyUpload();">开始上传</a>&nbsp;
				<a href="javascript:jQuery('#uploadify').uploadifyClearQueue()">取消所有上传</a>
		    </p>
			</td>
		</tr>
		
	</table>
	<input type="button" onclick="fnOK();" value="提交">
</div>
</body>
</html>