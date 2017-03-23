<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>访谈模型添加</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/releInfoList.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	<!--
		var topnum = request.getParameter("topnum");
		var site_id = jsonrpc.SiteRPC.getSiteIDByAppID("interview");
	    var sub_id = request.getParameter("sub_id");
		if(sub_id == "" || sub_id == null)
		{
			window.close();
		}
        var contentId = "content";

		var defaultBean = subjectReleInfo;
		$(document).ready(function () {
            initUeditor(contentId);
			init_input();
			
			var a_id = request.getParameter("id");
			
			if(a_id != null && a_id.trim() != "")
			{
				defaultBean = SubjectRPC.getSubReleInfo(a_id);

				if(defaultBean)
				{
					$("#subReleInfo").autoFill(defaultBean);
                    setV(contentId,defaultBean.content);
					if(defaultBean.info_type=="html")
					{
						changeInfo_Type('html');
					}
					else
					{
						changeInfo_Type('url');
					}
				}
				
				$("#addButton").click(updateReleInfo);
			}
			else
			{
				changeInfo_Type('html');
				$("#addButton").click(saveReleInfo);
			}

			initButtomStyle();
		}); 

		function changeInfo_Type(obj)
		{
			if(obj=="html")
			{
				$("#urlcontent").hide();
				$("#htmlcontent").show();
			}
			else
			{
				$("#urlcontent").show();
				$("#htmlcontent").hide();
			}
		}

	//-->
	</SCRIPT>	
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="subReleInfo" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>标题：</th>
			<td>
				<input id="info_name" name="info_name" type="text" class="width300" value="" onblur="checkInputValue('info_name',false,240,'标题','')"/>
				<!-- 隐含字段区域　开始 -->
				 <input type="hidden" id="id" name="id" value="0"/>
				 <input type="hidden" id="add_user" name="add_user"/> <!-- 登录人ID -->	 
				 <input type="hidden" id="user_name" name="user_name"/>
				 <!-- 隐含字段区域　结束 -->
			</td>			
		</tr>  
		<tr>
			<th>类型：</th>
			<td>
				<input type="radio" id="info_type" name="info_type" value="html" checked="checked" onClick="changeInfo_Type('html')"><label>HTML内容</label>
			    <input type="radio" id="info_type" name="info_type" value="url" onClick="changeInfo_Type('url')" ><label>URL址址</label>
			</td>			
		</tr>
		<tr id="urlcontent" height="30" style="display:none;">
		  <th>URL地址：</th>
		  <td><input class="inputTextForSingleLine" type="text" class="input_border" id="curl" name="curl" value="" /></td>			 
		 </tr>
		 <tr id="htmlcontent" style="display:none;">
		  <th style="vertical-align:top;">内容：</th>
		  <td>
              <script id="content" type="text/plain" style="width:620px;height:400px;"></script>
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
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('subReleInfo',id);" value="重置" />
			<input id="userAddCancel" name="btn1" type="button" onclick="tab_colseOnclick(curTabIndex);" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
