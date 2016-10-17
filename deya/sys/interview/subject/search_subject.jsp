<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>访谈主题查询</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript"  src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
	<script src="js/subjectList.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	<!--		
		var top_index = request.getParameter("ti");
		$(document).ready(function () {				
			var main_type = request.getParameter("tn");			
			
			$("#submit_status_tr").hide();
			
			var categoryList = SubjectRPC.getSubCategoryAllName();
			categoryList = List.toJSList(categoryList);
			if(categoryList != null)
			{
				$("#category_id").addOptions(categoryList,"category_id","category_name");
			}	
			
			initButtomStyle();
			init_input();
		}); 			
						
		function fnSearch()
		{
			var str = "";
			if($("#key_word").val().trim() != "")
			{
				var v = new Validator();
				if(!v.checkSpecialStr($("#key_word").val(),"主题名称"))
				{
					return;
				}
				str += " and sub.sub_name like '%"+$("#key_word").val()+"%'";
			}
			if($("#category_id").val().trim() != "")
			{
				str += " and sub.category_id = '"+$("#category_id").val()+"'";
			}
			if($("#apply_dept").val().trim() != "")
			{
				str += " and sub.apply_dept in ('"+$("#apply_dept").val().replace(/,/g,"','")+"')";
			}

			var user_ids = $("#apply_user").val().trim();			
			
			str += " and sub.apply_user in ('"+user_ids+"')";
			

			var s_time = $("#s_time").val();
			var e_time = $("#e_time").val();
			if(e_time != "")
			{
				str += " and  start_time < '"+e_time+" 23:59:59'";
			}
			if(s_time != "")
			{				
				str += " and  start_time > '"+s_time+" 00:00:00'";							
			}
			
			if(s_time != "" && e_time != "" && s_time != e_time)
			{
				if(!judgeDate(s_time,e_time))
				{
					parent.alertN("结束日期不能小于开始日期");
					return;
				}
			}	

			if($("#submit_status").val().trim() != "")
			{
				str += " and sub.submit_status = "+$("#submit_status").val()+"";
			}
			
			if($("#audit_status").val().trim() != "")
			{
				str += " and sub.audit_status = "+$("#audit_status").val()+"";
			}

			if($("#publish_status").val().trim() != "")
			{
				str += " and sub.publish_status = "+$("#publish_status").val()+"";
			}

			if($("#status").val().trim() != "")
			{
				str += " and sub.status = "+$("#status").val()+"";
			}

			if($("#recommend_flag").val().trim() != "")
			{
				str += " and sub.recommend_flag = "+$("#recommend_flag").val()+"";
			}

			
			top.getCurrentFrameObj(top_index).searchHandl(str);
			top.tab_colseOnclick(top.curTabIndex);			

		}

		function selectUser(ids,names)
		{
			$("#apply_user_name").val(names);
			$("#apply_user").val(ids);
		}
	//-->
	</SCRIPT>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="role_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			 <th>主题名称：</th>
				<td><input type="text" class="width300" id="key_word" name="key_word"  maxlength="100"/></td>
			 </tr>
			 <tr>				
				<th>访谈模型：</th>
				<td ><select id="category_id" name="category_id" class="width305"><option value="">--请选择--</option></select></td>
			 </tr>

			 <tr id="apply_tr" class="hidden">
			  
			  <th class="hidden">申请部门：</th>
		      <td >
			   <input type="text" id="apply_dept_name" name="apply_dept_name" style="width:205px" readonly="true">　<input type="hidden" id="apply_dept" name="apply_dept">
				   <input id="addButton" name="btn1" type="button" onclick="openSelectSingleDept('选择部门','selectUser')" value="选择" />
			  </td>
			 </tr>
			 <tr id="apply_tr" class="hidden">			  
			  <th>申请人：</th>
		      <td >
			   <input type="text" id="apply_user_name" name="apply_user_name" style="width:205px" readonly="true"><input type="hidden" id="apply_user" name="apply_user">
				   <input id="addButton" name="btn1" type="button" onclick="openSelectUserPage('选择用户','selectUser')" value="选择" />
			  </td>
			 </tr>
			 <tr>			  
			  <th>直播时间：</th>
		      <td >
				<input class="Wdate" type="text" class="width150" name="s_time" id="s_time" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="true" style="width:120">--
				<input class="Wdate" type="text" class="width150" name="e_time" id="e_time" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="true" style="width:120">
				</td>
			 </tr>			 
			 <tr id="submit_status_tr">
			  
			  <th>提交状态：</th>
		      <td >
			   <select id="submit_status" name="submit_status" class="width305">
			    <option value="">--请选择--</option>
			    <option value="1">已提交</option>
				<option value="0">未提交</option>				
			   </select>
			  </td>
			 </tr>
			 <tr >
			  
			  <th>审核状态：</th>
		      <td>
			   <select id="audit_status" name="audit_status" class="width305">
			    <option value="">--请选择--</option>
			    <option value="0">待审核</option>
				<option value="1">审核通过</option>
				<option value="-1">审核不通过</option>
			   </select>
			  </td>
			 </tr>
			 <tr >
			  
			  <th>发布状态：</th>
		      <td>
			   <select id="publish_status" name="publish_status" class="width305">
			    <option value="">--请选择--</option>
			    <option value="0">未发布</option>
				<option value="1">已发布</option>
				<option value="-1">已撤消</option>
			   </select>
			  </td>
			 </tr>
			 <tr >
			  
			  <th>访谈状态：</th>
		      <td>
			   <select id="status" name="status" class="width305">
			    <option value="">--请选择--</option>
			    <option value="0">预告状态</option>
				<option value="1">直播状态</option>
				<option value="2">历史状态</option>
			   </select>
			  </td>
			 </tr>
			 <tr id="apply_tr">			  
			  <th>是否推荐：</th>
		      <td>
			   <select id="recommend_flag" name="recommend_flag" class="width305">
			    <option value="">--请选择--</option>
			    <option value="1">是</option>
				<option value="0">否</option>
			   </select>
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
			<input id="addButton" name="btn1" type="button" onclick="fnSearch()" value="确定" />
			<input id="userAddButton" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="关闭" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
