<%@ page contentType="text/html; charset=utf-8"%>
<%
    
   String t_id = request.getParameter("t_id")!=null?request.getParameter("t_id"):"";
   String site_id = request.getParameter("site_id")!=null?request.getParameter("site_id"):"";
   String t_cname = request.getParameter("t_cname")!=null?request.getParameter("t_cname"):"";
          t_cname = java.net.URLDecoder.decode(t_cname,"UTF-8"); 

String modify_dtime = request.getParameter("modify_dtime")!=null?request.getParameter("modify_dtime"):"";
String t_ename = request.getParameter("t_ename")!=null?request.getParameter("t_ename"):"";
String creat_dtime = request.getParameter("creat_dtime")!=null?request.getParameter("creat_dtime"):"";
String id = request.getParameter("id")!=null?request.getParameter("id"):"";
String modify_user = request.getParameter("modify_user")!=null?request.getParameter("modify_user"):"";
String tcat_id = request.getParameter("tcat_id")!=null?request.getParameter("tcat_id"):"";
String t_path = request.getParameter("t_path")!=null?request.getParameter("t_path"):"";
String app_id = request.getParameter("app_id")!=null?request.getParameter("app_id"):"";
String t_ver = request.getParameter("t_ver")!=null?request.getParameter("t_ver"):"";
String creat_user = request.getParameter("creat_user")!=null?request.getParameter("creat_user"):"";
 
modify_dtime=modify_dtime.replaceAll("%20"," ");
creat_dtime=creat_dtime.replaceAll("%20"," ");
 
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上传模板</title>


<jsp:include page="../../include/include_tools.jsp"/>
<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
<script language="javascript" src="/sys/js/jquery.uploadify.js"></script>
<script language="javascript" src="/sys/js/uploadFile/swfobject.js"></script>
<script type="text/javascript">
    
	var t_id='<%=t_id%>';
	var site_id='<%=site_id%>';
	var t_cname='<%=t_cname%>';
	var modify_dtime='<%=modify_dtime%>';
	var t_ename='<%=t_ename%>';
	var creat_dtime='<%=creat_dtime%>';
	var id='<%=id%>';
	var modify_user='<%=modify_user%>';
	var tcat_id='<%=tcat_id%>';
	var t_path='<%=t_path%>';
	var app_id='<%=app_id%>';
	var t_ver='<%=t_ver%>';
	var creat_user='<%=creat_user%>';

	$(document).ready(function(){
 
		initButtomStyle();
		init_FromTabsStyle();
		if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
		initTemplateFileUpLoad();
	});

	var current_folder = "";//保存目录，如果不为空，保存在此目录下,且于模板管理处上传资源
	function initTemplateFileUpLoad()
	{
		var sizeLimit = 104857600;
		
		 $("#uploadify").uploadify( {//初始化函数
			'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
			'script' :jsonrpc.SiteRPC.getDefaultSiteDomainBySiteID(site_id)+'/sys/servlet/TemplateResourcesUpload',//后台处理的请求
			'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
			'buttonImg': '/sys/js/uploadFile/upst.gif',
			'folder' :'uploads',//您想将文件保存到的路径
			'queueID' :'fileQueue',//与下面的上传文件列表id对应
			'queueSizeLimit' :100,//上传文件的数量
			'scriptData':{'site_id':site_id},//向后台传的数据
			'fileDesc' :'html,htm',//上传文件类型说明
			'fileExt' :'*.html;*.htm', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
			'method':'get',//如果向后台传输数据，必须是get
			'sizeLimit':sizeLimit,//文件上传的大小限制，单位是字节
			'auto' :true,//是否自动上传
			'multi' :true,
			'simUploadLimit' :1,//同时上传文件的数量
			'buttonText' :'BROWSE',//浏览按钮图片
			'auto' : true,//点击选择直接上传图片
			'onSelect':function(event, queueID, fileObj){//选择完后触发的事件
				$("#uploadify").uploadifySettings('scriptData',{'custom_folder':current_folder,'site_id':site_id,'sid':jsonrpc.MateInfoRPC.getUploadSecretKey()});
				if(fileObj.size > sizeLimit)
				{
					alert("附件太大");
					return;
				}			
			},
			'onSelectOnce':function(event,data){
				$("#fileQueue").show();
				fileCount = data.fileCount;			
			},
			'onCancel':function(event,queueId,fileObj,data){
				fileCount = data.fileCount;
			},
			'onComplete': function(event, queueID, fileObj,serverData,response,data) {//当上传完成后的回调函数			
				var objPath =  jQuery.parseJSON(serverData);			
				//uploadReturnHandl(objPath.att_path);
				$("#fileQueue").hide();
		 var isCorrect = jsonrpc.TemplateRPC.updateUploadTemplateFile(objPath.att_path,t_id,site_id,t_cname,modify_dtime,t_ename,creat_dtime,id,modify_user,tcat_id,t_path,app_id,t_ver,creat_user);
						if(isCorrect)
						{ 
							 top.msgAlert("模板"+WCMLang.Add_success);			
							 top.CloseModalWindow();  // 模态用此方法 关闭模态窗口
							 top.getCurrentFrameObj().reloadTemplateDataList();// 模态用此方法刷新父窗口
                            // 本页面刷新窗口
							//window.location.href = "/sys/system/template/templateCategoryList.jsp?tid="+tcat_id+"&site_id="+site_id;
						     
						}
						else
						{ 
							top.msgWargin("请重新上传！模板"+WCMLang.Add_fail);
						} 
			}
	   });  
	}

</script>

</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="role_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	     <tr> <div id="fileQueue" class="hidden"></div></tr>
		  <tr>
			<th><span class="f_red">*</span>模板名称：</th>
			<td class="width250">
				<input id="template_name" name="template_name" type="text" class="width150" value="<%=t_cname%>" readonly="readonly" />
			</td>
		  
            <td class="width250">
			<div id="uploadify_div" class=""><input type="file" name="uploadify" id="uploadify" /></div>	
		   </td>
		  </tr>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="top.CloseModalWindow();" value="关闭" />	
		</td>
	</tr>
</table>
 
</form>
</body>
</html>
