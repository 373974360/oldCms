<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>信息</title>
		
		
		<jsp:include page="../../include/include_tools.jsp" />
		<script type="text/javascript" src="js/error_list.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
			    //alert(11);
				initButtomStyle();
				init_input();
				//alert(33);
				initViewData();
			});
		</script>
	</head>
	<body>
		<span class="blank12"></span>
		<form id="form1" name="form1" action="#" method="post">
			<table id="role_table" class="table_form" border="0" cellpadding="0"
				cellspacing="0">
				<tbody>
					<tr>
						<th width="130">
							信息ＩＤ：
						</th>
						<td class="">
							<span id="info_id"></span>
						</td>
					</tr>
					<tr id="system_selected" style="display:">
						<th>
							标题：
						</th>
						<td class="">
							<span id="info_title"></span>
						</td>
					</tr>
					<tr id="system_selected" style="display:">
						<th>
							信息URL：
						</th>
						<td class="">
							<span id="info_url"></span>
						</td>
					</tr>
					<tr id="system_selected" style="display:">
						<th>
							错误类型：
						</th>
						<td class="">
							<span id="err_type"></span>
						</td>
					</tr>
					<tr id="system_selected" style="display:">
						<th>
							错误内容：
						</th>
						<td class="">
							<span id="err_content"></span>
						</td>
					</tr>
					<tr id="system_selected" style="display:">
						<th>
							提交人姓名：
						</th>
						<td class="">
							<span id="err_name"></span>
						</td>
					</tr>
					<tr id="system_selected" style="display:">
						<th>
							提交人联系方式：
						</th>
						<td class="">
							<span id="err_name_tel"></span>
						</td>
					</tr>
					<tr id="system_selected" style="display:">
						<th>
							提交人IP：
						</th>
						<td class="">
							<span id="err_name_ip"></span>
						</td>
					</tr>
					<tr id="system_selected" style="display:">
						<th>
							提交时间：
						</th>
						<td class="">
							<span id="err_time"></span>
						</td>
					</tr>
					<tr id="system_selected" style="display:">
						<th>
							状态：
						</th>
						<td class="">
							<span id="err_state"></span>
						</td>
					</tr>	
					<tr id="err_noteTr" style="display:none">
						<th>
							意见：
						</th>
						<td class="">
							<span id="err_note"></span>
						</td>
					</tr>
					<tr id="o_timeTr" style="display:none">
						<th>
							处理时间：
						</th>
						<td class="">
							<span id="o_time"></span>
						</td>
					</tr>				
				</tbody>
			</table>
			<span class="blank12"></span>
			<div class="line2h"></div>
			<span class="blank3"></span>
			<table class="table_option" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td align="left" valign="middle" style="text-indent: 100px;">
						<input id="userAddCancel" name="btn1" type="button"
							onclick="top.CloseModalWindow();" value="关闭" />
					</td>
				</tr>
			</table>
			<span class="blank3"></span>
		</form>
	</body>
</html>
