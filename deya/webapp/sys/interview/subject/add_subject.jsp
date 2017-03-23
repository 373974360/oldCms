<%@ page contentType="text/html; charset=utf-8"%>
<%
String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>访谈主题分类添加</title> 
	<jsp:include page="../../include/include_tools.jsp"/>
	<script src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
	<script src="js/subjectList.js"></script>
	<script src="js/public.js"></script>
	<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
	<script language="javascript" src="../../js/jquery.uploadify.js"></script>
	<script language="javascript" src="../../js/uploadFile/swfobject.js"></script>

	<SCRIPT LANGUAGE="JavaScript">
	<!--
		var site_id = "<%=site_id%>";
        var contentId = "info";
		var defaultBean = subjectBean;
		var topnum = request.getParameter("topnum");
		$(document).ready(function () {	
			
			init_input();
			init_FromTabsStyle();
            initUeditor(contentId);
			initUPLoadImg("s_for_pic_file","s_for_pic");
			initUPLoadMedia("s_for_video_file","s_for_video");
			initUPLoadMedia("s_history_video_file","s_history_video");
			

			if(request.getParameter("tn") != "main")
			{
				//$("#subject").find("#main_data").hide();
			}else
			{
				$("#subject").find("#main_data").show();
				$("#current_position_span").html("管理访谈主题");
				$("#submit_status").val("1");
			}

			$("#preview_view_forecast_button").hide();
			$("#preview_view_live_button").hide();
			$("#preview_view_history_button").hide();
			$("#preview_pic_button").hide();
							
			var categoryList = SubjectRPC.getSubCategoryAllName(site_id);
			categoryList = List.toJSList(categoryList);
			if(categoryList != null && categoryList.size() > 0)
			{
				$("#category_id").addOptions(categoryList,"category_id","category_name");
			}		
			else
			{
				msgWargin("请先创建访谈模型");
				window.history.go(-1);
				return;
			}
			
			var s_id = request.getParameter("id");
			
			if(s_id != null && s_id.trim() != "")
			{
			
				defaultBean = SubjectRPC.getSubjectObj(s_id);
								
				if(defaultBean)			
				{
					$("#subject").autoFill(defaultBean);
                    setV(contentId,defaultBean.info);
					var resouseList = SubjectRPC.getResouseListByManager(defaultBean.sub_id);
					
					if(resouseList != null)
					{
						resouseList = List.toJSList(resouseList);
						for(var i=0;i<resouseList.size();i++)
						{
							if(resouseList.get(i).affix_path != "")
							{
								if(resouseList.get(i).affix_type == "pic")
								{
									$("#s_for_pic").val(resouseList.get(i).affix_path);
									$("#preview_pic_button").show();
									
								}
								else
								{
									if(resouseList.get(i).affix_status == "forecast")
									{
										$("#s_for_video").val(resouseList.get(i).affix_path);
										$("#preview_view_forecast_button").show();
									}
									if(resouseList.get(i).affix_status == "live")
									{
										$("#s_live_video").val(resouseList.get(i).affix_path);
										$("#preview_view_live_button").show();
									}
									if(resouseList.get(i).affix_status == "history")
									{
										$("#s_history_video").val(resouseList.get(i).affix_path);
										$("#preview_view_history_button").show();
									}
								}
							}
						}
					}
				}					
				$("#addButton").click(updateSubject);
			
			}
			else
			{
				$("#addButton").click(saveSubject);
			}	
			
			initButtomStyle();
		}); 		
		function fnPreview(file_name)
		{
			if(file_name == "s_for_pic")
			{
				$("#preview_td").html('<img src="'+$("#"+file_name).val()+'" width="200px" height="150px">');
			}
			else
			{
				$("#preview_td").html('<embed id="live_video_embed" height="150" width="200" src="'+$("#"+file_name).val()+'" mediatype="video" autostart="true" loop="true" menu="true" sck_id="0"></embed>');
			}

		}		

//asf,avi,mpg,mpeg,mpe,mov,rmvb,wmv,wav,mid,midi,mp3,mpa,mp2,ra,ram,rm,wma
//*.asf;*.avi;*.mpg;*.mpeg;*.mpe;*.mov;*.rmvb;*.wmv;*.wav;*.mid;*.midi;*.mp3;*.mpa;*.mp2;*.ra;*.ram;*.rm;*.wma
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
						<div class="tab_right" >基础信息</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" >视频链接</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" >新闻稿</div>
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
		  <th><span class="f_red">*</span>访谈主题：</th>
		<td><input type="text" id="sub_name" name="sub_name" class="width300" onblur="checkInputValue('sub_name',false,240,'访谈主题','')"></td>
		 </tr>
		 <tr>		  
		  <th>访谈模型：</th>
		  <td><select id="category_id" name="category_id" class="width305"></select></td>
		 </tr>
		 <tr>		  
		  <th style="vertical-align:top;">访谈简介：</th>
		  <td style="padding-top:3px;padding-bottom:3px;">
			<textarea id="intro" name="intro" style="width:585px;height:50px;" onblur="checkInputValue('intro',true,1000,'访谈简介','')"></textarea>
		  </td>
		 </tr>
		 <tr>		  
		  <th><span class="f_red">*</span>开始时间：</th>
		  <td>
		  <input class="Wdate width150" type="text" name="start_time" id="start_time" size="11" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true,readOnly:true})" readonly="true" >&nbsp;&nbsp;
		  结束时间：<input class="Wdate width150" type="text" name="end_time" id="end_time" size="11" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true,readOnly:true})" readonly="true" >
		  </td>
		 </tr>
		 <tr id="main_data" >		  
		  <th>审核状态：</th>
		  <td>
			<ul>
				<li><input type="radio" id="audit_status" name="audit_status" value="0" checked="checked"><label>待审核&nbsp;&nbsp;&nbsp;</label></li>
				<li><input type="radio" id="audit_status" name="audit_status" value="1"><label>审核通过</label></li>
				<li><input type="radio" id="audit_status" name="audit_status" value="-1"><label>审核不通过</label></li>
			</ul>
		  </td>
		 </tr>
		 <tr id="main_data">		  
		  <th>发布状态：</th>
		  <td>
			<ul>
				<li><input type="radio" id="publish_status" name="publish_status" value="0" checked="checked"><label>未发布&nbsp;&nbsp;&nbsp;</label></li>
				<li><input type="radio" id="publish_status" name="publish_status" value="1" ><label>已发布&nbsp;&nbsp;&nbsp;</label></li>
				<!-- <li><input type="radio" id="publish_status" name="publish_status" value="2" >已归档&nbsp;&nbsp; -->
				<li><input type="radio" id="publish_status" name="publish_status" value="-1"><label>已撤消</label></li>
			</ul>
		  </td>
		 </tr>
		 <tr id="main_data">		  
		  <th>访谈状态：</th>
		  <td>
			<ul>
				<li><input type="radio" id="status" name="status" value="0"  checked="checked"><label>预告状态</label></li>
				<li><input type="radio" id="status" name="status" value="1" ><label>直播状态</label></li>
				<li><input type="radio" id="status" name="status" value="2" ><label>历史状态</label></li>
			</ul>
		  </td>
		 </tr>
	</tbody>
</table>
</div>

<div class="infoListTable hidden" id="listTable_1">
<table id="splj_tab" class="table_form" border="0" cellpadding="0" cellspacing="0" >
	<tbody>		
		<tr>
			<th>访谈图片：</th>			
			<td><div style="float:left;margin:auto;"><input type="text"  id="s_for_pic" name="s_for_pic" class="width500" onblur="checkInputValue('s_for_pic',true,240,'访谈图片','')"></div><div style="float:left"><input type="file"  id="s_for_pic_file" name="s_for_pic_file"></div><div style="float:left;margin-left:12px;margin-top:3px"><a  hidefocus="true" id="preview_pic_button"><span id="" onclick="fnPreview('s_for_pic')">&nbsp;预览</span></a></div></td>
		</tr>
		<tr>			
			<th>预告视频：</th>
			<td><div style="float:left;margin:auto;"><input type="text"  id="s_for_video" name="s_for_video" class="width500"  onblur="checkInputValue('s_for_video',true,240,'预告视频','')"></div><div style="float:left"><input type="file"  id="s_for_video_file" name="s_for_video_file"></div><div style="float:left;margin-left:12px;margin-top:3px"><a  hidefocus="true" id="preview_view_forecast_button"><span id="" onclick="fnPreview('s_for_video')">&nbsp;预览</span></a></div></td>
		</tr>
		<tr>		
			<th>直播视频：</th>
			<td><div style="float:left;margin:auto;"><input type="text"  id="s_live_video" name="s_live_video" class="width500" onblur="checkInputValue('s_live_video',true,240,'直播视频','')"></div><div style="float:left;margin-left:12px;margin-top:3px"><a  hidefocus="true" id="preview_view_live_button"><span id="" onclick="fnPreview('s_live_video')">&nbsp;预览</span></a></div><div id="video_div" style="clear:both;width:100%;height:150px;overflow:auto;display:none"></div></td>
		</tr>
		<tr>
			<th>历史视频：</th>
			<td><div style="float:left;margin:auto;"><input type="text"  id="s_history_video" name="s_history_video" class="width500" onblur="checkInputValue('s_history_video',true,240,'历史视频','')"></div><div style="float:left"><input type="file"  id="s_history_video_file" name="s_history_video_file"></div><div style="float:left;margin-left:12px;margin-top:3px"><a  hidefocus="true" id="preview_view_history_button"><span id="" onclick="fnPreview('s_history_video')">&nbsp;预览</span></a></div></td>
		</tr>
		<tr>
		   <td height="32px" >&nbsp;</td>
		   <td  id="preview_td"></td>
		</tr>
		<tr>
		   <td height="32px" >&nbsp;</td>
		   <td><div id="fileQueue"></div></td>
		</tr>
	</tbody>
</table>
</div>

<div class="infoListTable hidden" id="listTable_2">
<table id="xwg_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
		<td>
         <script id="info" type="text/plain" style="width:98%;height:400px;"></script>
		</td>
	  </tr>	
	</tbody>
</table>
<!-- 
<table id="xwg_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
		<td>
		 <div style="display:none"><textarea id="info" name="info"></textarea></div>
		<script type="text/javascript">
			function FCKeditor_OnComplete(editorInstance)
			{
				editorInstance.Value = $("#info").val();
			}
			var sBasePath = "/cms.files/component/common/htmledit/";
			var oFCKeditor = new FCKeditor('info') ;
			oFCKeditor.BasePath	= sBasePath ;
			oFCKeditor.Height	= 360 ;
			oFCKeditor.Value = '' ;
			oFCKeditor.Create() ;
		</script>
		</td>
	</tr>
	<tr>
	 <td>
	  <input id="tool_btn" name="btn1" type="button" value="WORD格式清理" onclick="formatStyle('clearWordStyle','info')" style="cursor:hand"/>
	  <input id="tool_btn" name="btn1" type="button" value="清除样式" onclick="formatStyle('clearStyle','info')" style="cursor:hand"/>
	  <input id="tool_btn" name="btn1" type="button" value="去除空行" onclick="formatStyle('clearBlankRow','info')" style="cursor:hand"/>
	  <input id="tool_btn" name="btn1" type="button" value="首行缩进" onclick="formatStyle('firstRowIndent','info')" style="cursor:hand"/>
	  <input id="tool_btn" name="btn1" type="button" value="全角转半角" class="Button"	onclick="formatStyle('qjTObj','info')" style="cursor:hand"/>
	 </td>
	</tr>
	</tbody>
</table>

-->
</div>
</div>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="window.location.reload()" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="tab_colseOnclick(curTabIndex);" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>

</form>
</body> 

</html> 

