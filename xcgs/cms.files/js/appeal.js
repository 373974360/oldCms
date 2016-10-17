

var fileCount = 0;
function loadSQFormUpload()
{
	var sizeLimit = 5242880;
	$("#uploadify").uploadify( {//初始化函数
		'uploader' :'/cms.files/js/jquery-plugin/uploadFile/uploadify.swf',//flash文件位置，注意路径
		'script' :'/servlet/PeculiarUploadFile',//后台处理的请求
		'cancelImg' :'/cms.files/js/jquery-plugin/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/cms.files/js/jquery-plugin/uploadFile/upst.gif',
		'folder' :'uploads',//您想将文件保存到的路径
		'queueID' :'fileQueue',//与下面的上传文件列表id对应
		'queueSizeLimit' :100,//上传文件的数量
		//'scriptData':{'app_id':'appeal'},//向后台传的数据
		'fileDesc' :'jpg,bmp,png,gif,doc,docx,xls,txt,rar,zip',//上传文件类型说明
		'fileExt' :'*.jpg;*.bmp;*.png;*.gif;*.doc;*.docx;*.xls;*.txt;*.rar;*.zip', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
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
			$("#sq_atta_name").val(fileObj.name);
			$("#sq_atta_path").val(serverData);
			appealForm.submit(); 
		}
   });
}

function insertSQ()
{
	if(fileCount > 0)
	{
		$("#uploadify").uploadifySettings('scriptData',{'app_id':'appeal','sid':jsonrpc.MateInfoRPC.getUploadSecretKey()});
		jQuery('#uploadify').uploadifyUpload();
		return false;
	}else
	{
		appealForm.submit(); 	
	}
}

function initSatisForm()
{
	var validator = $("#satisForm").validate({
		rules: {
			sq_code: {
				required: true
			},
			query_code:{
				required: true
			}
		},
		messages: {
			sq_code: {
				required: "请输入信件编码！"
			},
			query_code: {
				required: "请输入查询码！"
			}
			
		},		
		errorPlacement: function(error, element) {
			error.appendTo( element.parent("td").find("div.cError") );
		},

		submitHandler: function(form) {
			var sat_score_str = "";
			$("input[id='sat_score']").each(function(){
				sat_score_str += ","+$(this).val();
			})
			$("input[id='sat_score_str']").val(sat_score_str.substring(1));

			var raty_score_str = "";
			$("input[id='raty_score']").each(function(){
				raty_score_str += ","+($(this).val()*2);
			})
			$("input[id='raty_score_str']").val(raty_score_str.substring(1));
			
			form.submit();  // 提交表单
		},

		success: function(label) {
			//label.text("ok!").addClass("success");
		}
	});
}

function iniLoginForm()
{
	var validator = $("#loginForm").validate({
		rules: {
			me_account: {
				required: true
			},
			me_password:{
				required: true
			},
			auth_code:{
				required: true
			}
		},
		messages: {
			me_account: {
				required: "请输入帐号！"
			},
			me_password: {
				required: "请输入密码！"
			},
			auth_code: {
				required: "请输入验证码！"
			}
			
		},
		
		errorPlacement: function(error, element) {
			error.appendTo( element.parent("td").find("div.cError") );
		},

		submitHandler: function(form) {
			alert("验证成功!");
			//form.submit();   提交表单
		},

		success: function(label) {
			//label.text("ok!").addClass("success");
		}
	});
	
}



function iniSearchForm()
{
	var validator = $("#searchForm").validate({
		rules: {
			sq_code: {
				required: true
			},
			query_code:{
				required: true
			}
		},
		messages: {
			sq_code: {
				required: "请输入信件编码！"
			},
			query_code: {
				required: "请输入查询码！"
			}
			
		},
		
		errorPlacement: function(error, element) {
			error.appendTo( element.parent("td").find("div.cError") );
		},

		submitHandler: function(form) {
			//alert("验证成功!");
			form.submit();  // 提交表单
		},

		success: function(label) {
			//label.text("ok!").addClass("success");
		}
	});
	
}


function iniAppealForm()
{	
	var validator = $("#appealForm").validate({
		rules: {
			ld:{
				required: true
			},
			sq_realname: {
				required: true 
			},
			sq_phone:{
				required: true
			},
			sq_address:{
				required: true
			},
			sq_email:{
				required: true,
				email: true			
			},
			sq_title:{
				required: true
			},
			sq_content:{
				required: true
			},
			auth_code:{
				required: true
			},
			do_dept:{
				required: true
			}
			
			
		},
		messages: {
			ld:{
				required: "请选择领导！"
			},
			do_dept:{
				required: "请选择部门！"
			},
			sq_realname: {
				required: "请输入真实姓名！"
			},
			sq_phone: {
				required: "请输入手机号码！"
			},
			sq_address: {
				required: "请输入联系地址！"
			},
			sq_email: {
				required: "请输入Email！",
				email: jQuery.format("请正确输入正确的Email地址")
			},
			sq_title:{
				required: "请输入信件标题！"
			},
			sq_content:{
				required: "请输入信件内容！"
			},
			auth_code:{
				required: "请输入验证码！"
			}
			
		},
		
		errorPlacement: function(error, element) {
			error.appendTo( element.parent("td").find("div.cError") );
			error.appendTo( element.parent("span").parent("td").find("div.cError") );
			error.appendTo( element.parent("div").parent("td").find("div.cError") );
		},

		submitHandler: function(form) {
			//$("#submit_name").val($(":radio[name='do_dept'][checked=true]").next().text());		
			$("#submit_name").val($("#do_dept").find("option:selected").text());	
				
			insertSQ();			
		},

		success: function(label) {
			//label.text("ok!").addClass("success");
		}
	});
	
}

function changeCreateImage(){
  $("#img").attr("src","/servlet/CreateImage?"+Math.random()); 
}

//提交评论
function submitAppealComment(sq_id)
{
	if(memberBean == null)
	{
		var me_account = $("#me_account").val();
		var me_password = $("#me_password").val();
		if(me_account != "" && me_account != null && me_password != "" && me_password != null)
		{
			memberLogin("me_account","me_password");			
		}
		else
		{
			alert("必须先登录才能评论");
			return;
		}
	}
	memberBean = MemberManRPC.getMemberBySession();
	if(memberBean != null)
	{		
		var c_content = $("#comment_content").val();
		if(c_content == "" || c_content == null)
		{
			alert("评论内容不能为空");
			return;
		}
		if(insertComment("appeal","",sq_id,c_content) == true)
		{
			$("#comment_content").val("");
			alert("评论成功，欢迎您的参与");
			return;
		}else
		{
			alert("评论失败");
		}
	}
}

//加载会员信息
function loadMemberInfoToInput()
{
	var memberBean = jsonrpc.MemberManRPC.getMemberBySession();
	if(memberBean != null)
	{
		$("#sq_realname").val(memberBean.me_realname);
		$("inupt[name='sq_sex'][value="+memberBean.me_sex+"]").attr("checked",true);
		$("#sq_phone").val(memberBean.me_phone);
		$("#sq_email").val(memberBean.me_email);
		$("#sq_address").val(memberBean.me_address);
		$("#me_id").val(memberBean.me_id);
		$("#sq_card_id").val(memberBean.me_card_id);
	}
}
