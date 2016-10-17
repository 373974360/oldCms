<%@ page contentType="text/html; charset=utf-8"%>
<%
	String dept_id = request.getParameter("dept_id");
	String user_id = request.getParameter("user_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>政务信息管理平台</title>


<jsp:include page="../../include/include_tools.jsp"/>
<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
<script language="javascript" src="../../js/jquery.uploadify.js"></script>
<script language="javascript" src="../../js/uploadFile/swfobject.js"></script>
<script type="text/javascript" src="js/userList.js"></script>
<script type="text/javascript">
var MateInfoRPC = jsonrpc.MateInfoRPC;
var imgDomain = MateInfoRPC.getImgDomain("");
var dept_id = "<%=dept_id%>";
var user_id = "<%=user_id%>";
var defaultBean;
$(document).ready(function(){
	initButtomStyle();
	init_input();
	getUserLevelList();
	uploadPhoto();

	if(user_id != "" && user_id != "null" && user_id != null)
	{
		$("#isAddReg_tr").hide();
		defaultBean = UserManRPC.getAllUserInfoByID(user_id);
		if(defaultBean)
		{
			$("#user_table").autoFill(defaultBean);
			//KE.html("work_desc",defaultBean.work_desc);
			
			if(defaultBean.photo != "")
			{
				$("#photo_div").html("<img src='"+defaultBean.photo+"' align='center' width='200px' height='270px'/>");
			}
		}
		$("#userAddButton").click(updateUser);
	}
	else
	{
		$("#bein_dept").val(jsonrpc.DeptRPC.getDeptName(dept_id));
		$("#userAddButton").click(addUser);
	}
});

//init_editer("work_desc");


function showPhoto(url)
{
	if(url.trim() != "")
		$("#photo_div").html("<img src='"+url+"' align='center' width='200px' height='270px'/>");
	else
		$("#photo_div").html("");
}

function uploadPhoto()
{

	$("#uploadFile").uploadify({//初始化函数
	    'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
	    'script' :''+imgDomain+'/servlet/UploadFileIfy',//后台处理的请求
		'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/sys/js/uploadFile/upst.gif',
	    'folder' :'folder',//您想将文件保存到的路径
	    'queueID' :'fileQueue',//与下面的上传文件列表id对应
	    'queueSizeLimit' :50,//上传文件的数量
	    //'scriptData' :{'is_press':0,'press_osition':5,'site_id':site_id},//向后台传的数据
	    'fileDesc' :'jpg,jpeg,gif,png',//上传文件类型说明
		'fileExt' :'*.jpg;*.jpeg;*.gif;*.png;', //控制可上传文件的扩展名,启用本项时需同时声明fileDesc
	    'method':'get',//如果向后台传输数据，必须是get
	    'sizeLimit':31457280,//文件上传的大小限制，单位是字节
	    'auto' :true,//是否自动上传
	    'multi' :false,
	    'simUploadLimit' :1,//同时上传文件的数量
	    'buttonText' :'BROWSE',//浏览按钮图片
		'onSelect':function(event,queueID,fileObj){//选择完后触发的事件
			$("#uploadFile").uploadifySettings('scriptData',{'is_press':0,'press_osition':0,'site_id':'org','sid':MateInfoRPC.getUploadSecretKey()});	
		},
		'onError':function(event,queueID,fileObj,errorObj){
			alert("文件:" +fileObj.name + "上传失败！");	
		},
	    'onComplete': function(event,queueID,fileObj,response,data){//当上传完成后的回调函数，ajax方式哦~~
			var objPath =  jQuery.parseJSON(response);			
			
			pic_url = imgDomain + objPath.att_path + objPath.att_ename;
			$("#photo").val(pic_url);
			showPhoto(pic_url);
 	    },
 	   	'onAllComplete': function(event,data){
 	    	
 	    }
   });
}
</script>
<style>
.width460{width:460px;}
.width155{width:155px;}
#photo_div img{ width:150px; height:200px;}
</style>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">

<div id="user_table">
<table  class="table_form" align="left" style="width:780px;" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<td rowspan="14" width="150px"  style="text-align: center; vertical-align: top;">
				<span class="blank9"></span>
				<div id="photo_div" style="width:150px;height:200px;border:1px solid #D7E1F0;margin-top:10px;margin-bottom:10px"></div>
				<div><input type="text" id="photo" name="photo" style="width:150px" onblur="showPhoto(this.value)" onfocus="showPhoto(this.value)"></div>
				<span class="blank6"></span>
				<div class="textCenter"><input type="file" id="uploadFile" name="uploadFile" ></div>
				<span class="blank9"></span>
				<div class="textCenter"><input type="checkbox" id="is_publish" name="is_publish" value="1"><label>发布到公务员名录</label></div>
			</td>
			<th><span class="f_red">*</span>姓名：</th>
			<td>
				<input id="user_realname" name="user_realname" type="text" class="width150" value="" onblur="checkInputValue('user_realname',false,100,'姓名','')"/>
			</td>
			<th>曾用名：</th>
			<td >
				<input id="user_aliasname" name="user_aliasname" type="text" class="width150" value="" onblur="checkInputValue('user_aliasname',true,100,'曾用名','')"/>
			</td>
		 </tr>
		<tr>			
			<th>人员级别：</th>
			<td >
				<select id="userlevel_value" name="userlevel_value" class="width155"><option value="0"></option></select>
			</td>
			<th>所属部门：</th>
			<td>
				<input id="bein_dept" name="bein_dept" type="text" class="width150" value="" onblur="checkInputValue('bein_dept',true,100,'所属部门','')"/>
			</td>
		</tr>				
		<tr>
			<th>性别：</th>
			<td >
				<select id="sex" name="sex" class="width155"><option value="1">男</option><option value="0">女</option></select>
			</td>
			<th>婚否：</th>
			<td >
				<select id="is_marriage" name="is_marriage" class="width155"><option value="0">未婚</option><option value="1">已婚</option></select>
			</td>
		</tr>		
		<tr>
			<th>民族：</th>
			<td >
				<input id="nation" name="nation" type="text" class="width150" value="" onblur="checkInputValue('nation',true,10,'民族','')"/>
			</td>
			<th>籍贯：</th>
			<td >
				<input id="natives" name="natives" type="text" class="width150" value="" onblur="checkInputValue('natives',true,50,'籍贯','')"/>
			</td>
		</tr>
		<tr>
			<th>政治面貌：</th>
			<td >
				<input id="politics_status" name="politics_status" type="text" class="width150" value="" onblur="checkInputValue('politics_status',true,80,'政治面貌','')"/>
			</td>
			<th>健康状况：</th>
			<td >
				<input id="health" name="health" type="text" class="width150" value="" onblur="checkInputValue('health',true,50,'健康状况','')"/>
			</td>
		</tr>
		<tr>
			<th>身份证号：</th>
			<td >
				<input id="idcard" name="idcard" type="text" class="width150" value="" onblur="checkInputValue('idcard',true,20,'身份证号','checkIDCard')"/>
			</td>
			<th>年龄：</th>
			<td >
				<input id="age" name="age" type="text" class="width150" value="0" onblur="checkInputValue('age',true,3,'年龄','checkInt')"/>
			</td>
		</tr>
		<tr>
			<th>出生年月：</th>
			<td >
				<input id="birthday" name="birthday" type="text" class="width150" value="" />
			</td>		
			<th>参加工作时间：</th>
			<td >
				<input id="to_work_time" name="to_work_time" type="text" class="width150" value="" onblur="checkInputValue('to_work_time',true,80,'参加工作时间','')" />
			</td>	
		</tr>
		<tr>
			<th>最高学历：</th>
			<td >
				<input id="degree" name="degree" type="text" class="width150" value="" />
			</td>
			<th>毕业时间：</th>
			<td >
				<input id="graduation_time" name="graduation_time" type="text" class="width150" value="" />
			</td>	
		</tr>
		<tr>
			<th>所学专业：</th>
			<td >
				<input id="professional" name="professional" type="text" class="width150" value="" onblur="checkInputValue('professional',true,20,'所学专业','')"/>
			</td>
			<th>毕业院校：</th>
			<td >
				<input id="colleges" name="colleges" type="text" class="width150" value="" onblur="checkInputValue('address',true,80,'地址','')"/>
			</td>
		</tr>
		<tr>
			<th>移动电话：</th>
			<td >
				<input id="phone" name="phone" type="text" class="width150" value="" onblur="checkInputValue('phone',true,20,'移动电话','checkMobile')"/>
			</td>
			<th>电子邮箱：</th>
			<td >
				<input id="email" name="email" type="text" class="width150" value="" onblur="checkInputValue('email',true,40,'Email','checkEmail')"/>
			</td>			
		</tr>
		<tr>
			<th>固定电话：</th>
			<td >
				<input id="tel" name="tel" type="text" class="width150" value=""  onblur="checkInputValue('tel',true,20,'固定电话','checkTelephone')"/>
			</td>
			<th>邮编：</th>
			<td >
				<input id="postcode" name="postcode" type="text" class="width150" value="" onblur="checkInputValue('postcode',true,8,'邮编','checkZip')"/>
			</td>
		</tr>
		<tr>
			<th>办公地址：</th>
			<td colspan="3">
				<input id="address" name="address" type="text" class="width460" value="" onblur="checkInputValue('address',true,80,'地址','')"/>
			</td>
		</tr>
		<tr>
			<th>职务：</th>
			<td colspan="3">
				<input id="functions" name="functions" type="text" class="width460" value="" onblur="checkInputValue('functions',true,100,'职务','')"/>
			</td>
		</tr>
		<tr>
			<th>工作分工：</th>
			<td colspan="3">
				<input id="work_desc" name="work_desc" type="text" class="width460" value=""  onblur="checkInputValue('functions',true,100,'工作分工','')" />
				<!-- <textarea id="work_desc" name="work_desc" style="width:585px;;height:180px;"></textarea>		 -->
			</td>
		</tr>
	</tbody>
</table>
<span class="blank3"></span>
<table class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th style="vertical-align:top;">简历：</th>
			<td colspan="3">
				<textarea id="resume" name="resume" style="width:627px;;height:180px;"></textarea>
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">个人简介：</th>
			<td colspan="3">
				<textarea id="summary" name="summary" style="width:627px;;height:180px;"></textarea>
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">备注：</th>
			<td colspan="3">
				<textarea id="user_memo" name="user_memo" style="width:627px;;height:100px;" onblur="checkInputValue('user_memo',true,1000,'备注','')"></textarea>
			</td>
		</tr>
		<tr>
			<th>用户状态：</th>
			<td >
				<input id="user_status" name="user_status" type="radio" value="0" checked="true"/><label>启用</label>
				<input id="user_status" name="user_status" type="radio" value="1"/><label>停用</label>
			</td>
		</tr>
		<tr id="isAddReg_tr">
			<th style="vertical-align:top;">是否添加帐号：</th>
			<td colspan="3">
				<input id="isAddReg" name="isAddReg" type="checkbox" onclick="showRegTable(this)"/>		
			</td>
		</tr>
	 </tbody>
	</table>
</div>
	<table id="reg_table" class="table_form" border="0" cellpadding="0" cellspacing="0" style="display:none">
	<tbody>
				
		<tr>
			<th><span class="f_red">*</span>帐号名称：</th>
			<td >
				<input id="username" name="username" type="text" class="width200" value="" onblur="checkInputValue('username',false,20,'帐号名称','')" />最少2个字符
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>密码：</th>
			<td >
				<input id="password" name="password" type="password" class="width200" value="" onblur="checkInputValue('password',false,30,'密码','')"/>请输入6-16位字符
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>密码确认：</th>
			<td >
				<input id="check_password" name="check_password" type="password" class="width200" value=""/>
			</td>
		</tr>
		<tr>
			<th>帐号状态：</th>
			<td >
				<input id="register_status" name="register_status" type="radio" value="0"/><label>启用</label>
				<input id="register_status" name="register_status" type="radio" value="1"/><label>停用</label>
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
			<input id="userAddButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="window.location.reload()" value="重置" />
			<input id="userAddCancel" name="btn1" type="button" onclick="window.history.go(-1)" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
