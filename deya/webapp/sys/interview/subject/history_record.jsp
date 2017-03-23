<%@ page contentType="text/html; charset=utf-8"%>
<%
    String site_id = request.getParameter("site_id");
    if(site_id == null || "".equals(site_id) || "null".equals(site_id))
        site_id = "";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>访谈主题历史记录维护</title> 
	<jsp:include page="../../include/include_tools.jsp"/>
	<script src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
        var site_id = "<%=site_id%>";
		var ChatRPC = jsonrpc.ChatRPC;
		var SubjectRPC = jsonrpc.SubjectRPC;
		var subjectBean = new Bean("com.deya.wcm.bean.interview.SubjectBean",true);

		var sub_id = request.getParameter("sub_id");
        var record_pic = "history_record_pic";
        var record_text = "history_record_text";

		$(document).ready(function () {
            initUeditor(record_pic);
            initUeditor(record_text);
			getHistoryRecord();
			initButtomStyle();
			init_input();
			init_FromTabsStyle();

		}); 
		function getHistoryRecord()
		{
			var defaultBean = subjectBean;
			defaultBean = SubjectRPC.getHistoryRecord(sub_id);
			$("#subject").autoFill(defaultBean);
            setV(record_pic,defaultBean.history_record_pic);
            setV(record_text,defaultBean.history_record_text);
		}

		function showCommitTD(flag)
		{
			if(flag == 1)
			{
				$("#comment_0").find("TD").removeClass();			
				$("#comment_1").show();
			}
			else
			{
				$("#comment_0").find("TD").addClass("content_td");
				$("#comment_1").hide();
			}
		}
			
		var chat_type = "pic";
	    function changeContentLable(types)
		{
			chat_type = types;
			/*
			$("#lable_tr").find("TD").attr("class","content_lab");
			$(obj).attr("class","content_lab_check");
			var c_id = $(obj).attr("id")+"_tab";
			$("#content_area").find("TABLE").hide();
			$("#"+c_id).show();*/
		}

		function getChatValue()
		{
			msgConfirm("该功能会清空编辑器中的内容，请谨慎操作，是否继续？","getChatValueHandl()");
		}

		function getChatValueHandl()
		{
			var str = "";			
			var l = ChatRPC.getChatListByDB(sub_id);			
			if(l != null)
			{
				l = List.toJSList(l);
				for(var i=0;i<l.size();i++)
				{
					if(l.get(i).chat_area == chat_type)
					{
						str += '<div style="padding:8px;font-size:12px"><strong>'+l.get(i).chat_user+'：</strong>'+l.get(i).content+'</div>';
					}						
				}/*
				var oEditor = FCKeditorAPI.GetInstance('history_record_'+chat_type) ;
				oEditor.SetHTML(str);*/
                setV("history_record_"+chat_type,str);
			}
		}

		function saveRecord()
		{/*
			var oEditor = FCKeditorAPI.GetInstance('history_record_pic'); 
			var c = oEditor.GetXHTML(true);
			$("#history_record_pic").val(c);
			

			oEditor = FCKeditorAPI.GetInstance('history_record_text'); 
			c = oEditor.GetXHTML(true);
			$("#history_record_text").val(c);*/

			var bean = BeanUtil.getCopy(subjectBean);
			$("#subject").autoBind(bean);
            bean.history_record_pic = getV(record_pic);
            bean.history_record_text = getV(record_text);
			bean.sub_id = sub_id;
			bean.update_user = LoginUserBean.user_id;

			if(SubjectRPC.updateHistoryRecord(bean))
			{
				msgAlert("历史记录"+WCMLang.Add_success);
				history.go(-1);
			}
			else
			{
				msgWargin("历史记录"+WCMLang.Add_fail);
			}
		}
	//-->
	</SCRIPT>
</head> 
<body> 	
<table class="table_option" border="0" cellpadding="0" cellspacing="0" align="left">
	<tr>
		<td align="left" class="fromTabs width10" style="">	
			
			<span class="blank3"></span>
		</td>
		<td align="left" width="100%">
			<ul class="fromTabs">
				<li class="list_tab list_tab_cur">
					<div class="tab_left">
						<div class="tab_right" onclick="changeContentLable('pic')">图文区文字记录</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" onclick="changeContentLable('text')">互动区文字记录</div>
					</div>
				</li>
			</ul>
		</td> 	
		
	</tr>
</table>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<div id="subject">
<!-- 隐含字段区域　开始 -->
 <input type="hidden" id="id" name="id" value="0"/>
 <input type="hidden" id="sub_id" name="sub_id"/>
 <input type="hidden" id="submit_status" name="submit_status" value="0"/>
 
 <!-- 隐含字段区域　结束 -->
<div class="infoListTable" id="listTable_0">
<table id="jcxx_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
		 <td>
             <script id="history_record_pic" type="text/plain" style="width:700px;height:400px;"></script>
		  <!-- <div style="display:none"><textarea id="history_record_pic" name="history_record_pic" ></textarea></div>
			<script type="text/javascript">
				function FCKeditor_OnComplete(editorInstance)
				{
					editorInstance.Value = $("#history_record_pic").val();
				}
				var sBasePath = "/cms.files/component/common/htmledit/";
				var oFCKeditor = new FCKeditor('history_record_pic') ;
				oFCKeditor.BasePath	= sBasePath ;
				oFCKeditor.Height	= 400 ;
				oFCKeditor.Value = '' ;
				oFCKeditor.Create() ;
			</script> -->
		 </td>
		</tr><!-- 
		<tr>
		 <td>
		  <input id="tool_btn" name="btn1" type="button" value="WORD格式清理" onclick="formatStyle('clearWordStyle','history_record_pic')" style="cursor:hand"/>
		  <input id="tool_btn" name="btn1" type="button" value="清除样式" onclick="formatStyle('clearStyle','history_record_pic')" style="cursor:hand"/>
		  <input id="tool_btn" name="btn1" type="button" value="去除空行" onclick="formatStyle('clearBlankRow','history_record_pic')" style="cursor:hand"/>
		  <input id="tool_btn" name="btn1" type="button" value="首行缩进" onclick="formatStyle('firstRowIndent','history_record_pic')" style="cursor:hand"/>
		  <input id="tool_btn" name="btn1" type="button" value="全角转半角" class="Button"	onclick="formatStyle('qjTObj','history_record_pic')" style="cursor:hand"/>
		 </td>
		</tr> -->
	</tbody>
</table>
</div>

<div class="infoListTable hidden" id="listTable_1">
<table id="xwg_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
		 <td>
             <script id="history_record_text" type="text/plain" style="width:700px;height:400px;"></script>
		 <!-- 
		  <div style="display:none"><textarea id="history_record_text" name="history_record_text" ></textarea></div>
			<script type="text/javascript">
				function FCKeditor_OnComplete(editorInstance)
				{
					editorInstance.Value = $("#history_record_text").val();
				}
				var sBasePath = "/cms.files/component/common/htmledit/";
				var oFCKeditor2 = new FCKeditor('history_record_text') ;
				oFCKeditor2.BasePath	= sBasePath ;
				oFCKeditor2.Height	= 400 ;
				oFCKeditor2.Value = '' ;
				oFCKeditor2.Create() ;
			</script> -->
		 </td>
		</tr>
		<!-- <tr>
		 <td>
		  <input id="tool_btn" name="btn1" type="button" value="WORD格式清理" onclick="formatStyle('clearWordStyle','history_record_text')" style="cursor:hand"/>
		  <input id="tool_btn" name="btn1" type="button" value="清除样式" onclick="formatStyle('clearStyle','history_record_text')" style="cursor:hand"/>
		  <input id="tool_btn" name="btn1" type="button" value="去除空行" onclick="formatStyle('clearBlankRow','history_record_text')" style="cursor:hand"/>
		  <input id="tool_btn" name="btn1" type="button" value="首行缩进" onclick="formatStyle('firstRowIndent','history_record_text')" style="cursor:hand"/>
		  <input id="tool_btn" name="btn1" type="button" value="全角转半角" class="Button"	onclick="formatStyle('qjTObj','history_record_text')" style="cursor:hand"/>
		 </td>
		</tr> -->
	</tbody>
</table>
</div>
</div>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="getChatValue()" value="导入访谈记录" />	
			<input id="addButton" name="btn1" type="button" onclick="saveRecord()" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="window.location.reload()" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="history.go(-1)" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>

</form>
</body> 

</html> 

