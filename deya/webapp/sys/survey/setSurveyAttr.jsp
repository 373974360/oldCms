<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>问卷属性设置</title>


<link rel="stylesheet" type="text/css" href="/sys/styles/themes/default/tree.css" />
<jsp:include page="../include/include_tools.jsp"/>
<script type="text/javascript"  src="../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
<script language="javascript" src="../js/jquery.uploadify.js"></script>
<script language="javascript" src="../js/uploadFile/swfobject.js"></script>
<script type="text/javascript" src="../js/extend.js"></script>
<script type="text/javascript" src="js/surveyList.js?v=2017"></script>

<!-- <script type="text/javascript" src="/cms.files/component/common/htmledit/editor/js/cicroUtil.js"></script>
<script type="text/javascript" src="/cms.files/component/common/htmledit/fckeditor.js"></script> -->
<SCRIPT LANGUAGE="JavaScript">
	<!--
		var site_id = "CMScdgjj";
		var s_id = request.getParameter("s_id");
		var top_index = request.getParameter("top_index");
		$(document).ready(function () {	
			initUPLoadImg("fileName","pic_path");
			
			getSurveyCategoryName();//得到问卷分类名称
			var defaultSurveyBean = SurveyRPC.getSurveyBean(s_id);			
			$("#survey").autoFill(defaultSurveyBean);	
			//KE.html("verdict",defaultSurveyBean.verdict);
			if(defaultSurveyBean.spacing_interval != "")
			{
				$("#spacing_type").val(defaultSurveyBean.spacing_interval.substring(0,1));
				$("#spacing_time").val(defaultSurveyBean.spacing_interval.substring(1))
			}

			if(defaultSurveyBean.pic_path.trim() != "")
			{
				$("#pre_div").html('<img src="'+defaultSurveyBean.pic_path+'"/>');
				$("#pre_div").show();
			}

			initButtomStyle();
			init_input();
			init_FromTabsStyle();
		}); 
//    init_editer("verdict");
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
			

	    function changeContentLable(obj)
		{
			$("#lable_tr").find("TD").attr("class","content_lab");
			$(obj).attr("class","content_lab_check");
			var c_id = $(obj).attr("id")+"_tab";
			$("#content_area").find("TABLE").hide();
			$("#"+c_id).show();
		}

		//得到上传图片地址后插入信息
		function returnUploadValue(furl)
		{
			if(furl != "" && furl != null)
			{
				$("#pic_path").val(furl);		
				setSurveyAttrHandl();
			}
			else
				alertWar("上传失败，请重新提交");
		}		

		function prevImg()
		{
			var path = $("#pic_path").val();
			if(path != "" && path != null)
			{
				$("#prev_div").show();
				$("#prev_div").html('<img src="'+path+'">');
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
						<div class="tab_right" >基础信息</div>
					</div>
				</li>
				<li class="list_tab hidden">
					<div class="tab_left">
						<div class="tab_right" >文字说明</div>
					</div>
				</li>
			</ul>
		</td> 	
		
	</tr>
</table>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<div id="survey">
<div class="infoListTable" id="listTable_0">
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
 <!-- 隐含字段区域　开始 -->
 <input type="hidden" id="s_id" name="s_id" value="0"/>	
 <input type="hidden" id="s_name" name="s_name" value="0"/>	
 <!-- 隐含字段区域　结束 -->
	<tbody>
		<tr>
			<th>问卷调查分类：</th>
			<td>
				<select id="category_id" name="category_id" class="width305"></select>
			</td>			
		</tr>		
		<tr>		  
		  <th>开始时间：</th>
		  <td><input class="Wdate width150" type="text" name="start_time" id="start_time" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readOnly="readonly" >&nbsp;</td>
		 </tr>
		 <tr>
		  
		  <th>结束时间：</th>
		  <td><input class="Wdate width150" type="text" name="end_time" id="end_time" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readOnly="readonly" >&nbsp;</td>	 </tr>
		 <tr>		  
		  <th>重复提交间隔：</th>
		  <td ><input type="text" class="spacing_time" id="spacing_time" name="ip_fre" style="width:50px" maxlength="5" onblur="checkInputValue('spacing_time',true,5,'重复提交间隔次数','checkUnsignedInt')"><select id="spacing_type" name="spacing_type"><option value="d">天</option><option value="h">小时</option><option value="m">分钟</option></select></td>
		 </tr>
		 <tr>
		  
		  <th>同IP重复提交数：</th>
		  <td ><input type="text" id="ip_fre" name="ip_fre" style="width:50px" value="1" maxlength="3" onblur="checkInputValue('ip_fre',false,3,'同一IP重复提交次数','checkUnsignedInt')">&nbsp;注：输入0值不限制重复提交次数</td>
		 </tr>
		 <tr>
		  
		  <th>只允许会员参与：</th>
		  <td>
			<ul>
				<li><input type="radio" id="is_register" name="is_register" checked=true value="0"><label>是</label></li>
				<li><input type="radio" id="is_register" name="is_register" checked=true value="1"><label>否</label></li>
			</ul>
		  </td>
		 </tr>
		 <tr>
		  
		  <th>是否需要验证码：</th>
		  <td>
			<ul>
				<li><input type="radio" id="is_checkcode" name="is_checkcode" value="1"><label>是</label></li>
				<li><input type="radio" id="is_checkcode" name="is_checkcode" checked=true value="0"><label>否</label></li>
			</ul>
		  </td>
		 </tr>
		 <tr>
		  
		  <th>进行中显示结果：</th>
		  <td ><input type="checkbox" checked="true" id="is_show_result" name="is_show_result" value="1" checked="true">&nbsp;</td>
		 </tr>
		 <tr>
		  
		  <th>截止后显示结果：</th>
		  <td >
			<ul>
				<li><input type="radio" id="show_result_status" name="show_result_status" value="0"><label>不显示</label></li>
				<li><input type="radio" id="show_result_status" name="show_result_status" value="1" checked="true"><label>显示统计结果</label></li>
				<li><input type="radio" id="show_result_status" name="show_result_status" value="2"><label>显示文字说明</label></li>				
			</ul>
		   </td>
		 </tr>
		 <tr class="hidden">
		  
		  <th>前台展示模板：</th>
		  <td ><input type="text" id="model_path" name="model_path" class="width300" maxlength="80"></td>
		 </tr>
		 <tr>
		  
		  <th class="hidden">焦点图片：</th>
		  <td class="hidden">
			<div style="float:left;margin:auto;"><input type="text" id="pic_path" name="pic_path" class="width500" maxlength="80"></div>
			<div style="float:left;margin:auto;"><input type="file" id="fileName" name="fileName" style="width:300px" maxlength="80"></div>
			<div style="float:left;margin:auto;">&nbsp;<input type="button" id="" name="" maxlength="80" value="预览" onclick="prevImg()"></div>
		  </td>
		 </tr>
		 <tr>		  
		  <th></th>
		  <td >
			<div id="prev_div" style="width:400px;height:400px;overflow:auto" class="hidden"></div>
			
		  </td>
		 </tr>
	</tbody>
</table>
</div>

<div class="infoListTable hidden" id="listTable_1">
<table id="pzxx_tab" class="table_form" border="0" cellpadding="0" cellspacing="0" >
	<tbody>		
		<tr>
				 <td>
				 <textarea id="verdict" name="verdict" style="width:98%;height:400px"></textarea>
				  <!-- <div style="display:none"><textarea id="verdict" name="verdict" ></textarea></div>
					<script type="text/javascript">
						function FCKeditor_OnComplete(editorInstance)
						{
							editorInstance.Value = $("#verdict").val();
						}
						var sBasePath = "/cms.files/component/common/htmledit/";
						var oFCKeditor = new FCKeditor('verdict') ;
						oFCKeditor.BasePath	= sBasePath ;
						oFCKeditor.Height	= 360 ;
						oFCKeditor.Value = '' ;
						oFCKeditor.Create() ;
					</script> -->
				 </td>
				</tr>
				<!--<tr>
				 <td>
				  <input id="tool_btn"  type="button" value="word格式清理" class="Button" onclick="formatStyle('clearWordStyle','verdict')" style="cursor:hand"/>
				  <input id="tool_btn"  type="button" value="清除样式" class="Button" onclick="formatStyle('clearStyle','verdict')" style="cursor:hand"/>
			      <input id="tool_btn"  type="button" value="去除空行" class="Button" onclick="formatStyle('clearBlankRow','verdict')" style="cursor:hand"/>
				  <input id="tool_btn"  type="button" value="首行缩进" class="Button" onclick="formatStyle('firstRowIndent','verdict')" style="cursor:hand"/>
				  <input id="tool_btn"  type="button" value="全角转半角" class="Button"	onclick="formatStyle('qjTObj','verdict')" style="cursor:hand"/>
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
			<input id="addButton" name="btn1" type="button" onclick="setSurveyAttrHandl()" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="window.location.reload()" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="parent.tab_colseOnclick(parent.curTabIndex)" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>

</form>
</body>
</html>
