<%@ page contentType="text/html; charset=utf-8"%>
<%
String app_id = request.getParameter("app_id");
if(app_id == null){
	app_id = "0";
}

String site_id = request.getParameter("site_id");
if(site_id == null){
	site_id = "0";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>评论配置</title>
	
	
	<jsp:include page="../include/include_tools.jsp"/>
	<script type="text/javascript" src="js/commentSet.js">
</script>
<style rel="stylesheet" type="text/css">
.sq_box{border:0px solid #C5DDF1;}
.sq_title_box{ height:22px; font-weight:bold; padding-left:10px; line-height:22px; background:url(../images/sq_title_bg.gif) 0 -4px repeat-x; cursor:pointer;}
.sq_title{ width:300px; height:22px; float:left; font-weight:bold; color:#606AA3; padding-left:18px; line-height:22px;cursor:pointer;}
.sq_title_plus{ background:url(../images/sq_title_plus.gif)  0 -3px no-repeat;}
.sq_title_minus{ background:url(../images/sq_title_minus.gif)  0 -3px no-repeat;}
.sq_title_right{ width:120px; height:22px; line-height:22px; float:right;font-weight:bold; color:#606AA3; text-align:right; padding-right:10px; cursor:pointer;}
.sq_box_content{ padding:3px; text-align:left; padding-left:80px;}
</style>
<script type="text/javascript">

var site_id = "<%=site_id%>";
var app_id = "<%=app_id%>";

var commentSet = new Bean("com.deya.wcm.bean.comment.CommentSet",true);

$(document).ready(function(){
	initButtomStyle();
	init_input();
	//initAttachConfig();
	//$("#addButton").click(saveAttachConfig);
	commentSet = jsonrpc.CommentSetRPC.getCommentSetBySiteIdAndAppIDD(site_id, app_id);
	$(":radio[name='is_public'][value='"+commentSet.is_public+"']").attr("checked",true);
	$("#com_prefix").val(commentSet.com_prefix); 
	$(":radio[name='is_need'][value='"+commentSet.is_need+"']").attr("checked",true);
	$(":radio[name='is_code'][value='"+commentSet.is_code+"']").attr("checked",true);
	$("#time_spacer").val(commentSet.time_spacer); 
	$("#ip_time").val(commentSet.ip_time); 
	$("#pass_size").val(commentSet.pass_size); 
	$("#tact_word").val(commentSet.tact_word);
});


function fuOK(){
    commentSet.site_id = site_id;
	commentSet.app_id = app_id;
	commentSet.is_public = $(":radio[name='is_public'][checked]").val();
    commentSet.com_prefix = $("#com_prefix").val();
	commentSet.is_need = $(":radio[name='is_need'][checked]").val();
	commentSet.is_code = $(":radio[name='is_code'][checked]").val();
	commentSet.time_spacer = $("#time_spacer").val();
	commentSet.ip_time = $("#ip_time").val();
	commentSet.pass_size = $("#pass_size").val();
	commentSet.tact_word = $("#tact_word").val();

	if(jsonrpc.CommentSetRPC.updateCommentSet(commentSet))
	//if(true)
	{
		msgAlert("信息修改成功！");
	}
	else
	{
		msgWargin("信息修改失败！");
	}

}
</script>
	</head>
	<body>
	<div style="text-align:left;">
		<form id="form1" name="form1" action="#" method="post">
		<input type="hidden" name="group" id="group" value="attachment" />
			<div class="sq_box">
				<div class="sq_title_box">
					<div class="sq_title sq_title_minus">配置</div>
				</div>
				<div class="sq_box_content">
					<table class="table_form" border="0"
						cellpadding="0" cellspacing="0">
						<tbody>
							<tr>
								<th >允许游客评论：</th>
								<td>
									<ul>
										<li>
										  <input type="radio" id="is_public1" name="is_public" value="1" checked="checked"/><label>允许</label> 
			 							 &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="is_public2" name="is_public" value="0" /><label>不允许</label> 
										</li>
									</ul>
								</td>
							</tr>
							<tr>
								<th >评论名前缀：</th>
								<td>
									<ul>
										<li>
											<input id="com_prefix" name="com_prefix" type="text" value="" />
										</li>
									</ul>
								</td>
							</tr>
							<tr>
								<th >是否需要审核：</th>
								<td>
									<ul>
										<li>
										  <input type="radio" id="is_need1" name="is_need" value="1" checked="checked"/><label>需要</label> 
			 							 &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="is_need2" name="is_need" value="0" /><label>不需要</label> 
										</li>
									</ul>
								</td>
							</tr>
							<tr class="hidden">
								<th >是否开启验证码：</th>
								<td>
									<ul>
										<li>
										  <input type="radio" id="is_code1" name="is_code" value="1" checked="checked"/><label>开启</label> 
			 							 &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="is_code2" name="is_code" value="0" /><label>不开启</label> 
										</li>
									</ul>
								</td>
							</tr>
							<tr>
								<th >时间间隔：</th>
								<td>
									<ul>
										<li>
										 <input id="time_spacer" name="time_spacer" type="text" value="60" />分钟  
										</li>
									</ul>
								</td>
							</tr>
							<tr>
								<th ></th>
								<td>
									<ul>
										<li>
										 注：发布，回复，支持的时间间隔（由cookies控制）值为0时不起作用
										</li>
									</ul>
								</td>
							</tr>
							<tr>
								<th >屏蔽ip时间：</th>
								<td>
									<ul>
										<li>
										 <input id="ip_time" name="ip_time" type="text" value="0" />分钟 
										</li>
									</ul>
								</td>
							</tr>
							<tr>
								<th ></th>
								<td>
									<ul>
										<li>
										 注：发布，回复的时间间隔（由ip控制）值为0时不起作用
										</li>
									</ul>
								</td>
							</tr>
							<tr class="hidden">
								<th >最大楼层数：</th>
								<td>
									<ul>
										<li>
										 <input id="pass_size" name="pass_size" type="text" value="5" />层 
										</li>
									</ul>
								</td>
							</tr>
							<tr class="hidden">
								<th ></th>
								<td>
									<ul>
										<li>
										 注：超过最大楼层数评论将自动隐藏，点击展开
										</li>
									</ul>
								</td>
							</tr>
							<tr>
								<th>敏感字词：</th>
								<td>
									<ul>
										<li>
										 <textarea id="tact_word" name="tact_word" style="width:400px;height:80px"></textarea>
										</li>
									</ul>
								</td>
							</tr>
							<tr>  
								<th ></th>
								<td>
									<ul>
										<li>
										 注：多个字词以逗号分隔
										</li>
									</ul>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<span class="blank12"></span>
			<div class="line2h"></div>
			<span class="blank3"></span>
			<table class="table_option" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td align="left" valign="middle" style="text-indent: 100px;">
						<input id="addButton" name="btn1" type="button" onclick="fuOK()"
							value="保存" />
					</td>
				</tr>
			</table>
			<span class="blank3"></span>
		</form>
		</div>
	</body>
</html>