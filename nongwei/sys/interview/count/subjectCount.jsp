<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>访谈热度排行</title> 
	<jsp:include page="../../include/include_tools.jsp"/>
	<script src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>    
	<SCRIPT language="javascript">
	<!--
	var site_id = "<%=site_id%>";
	var SubjectRPC = jsonrpc.SubjectRPC;
   $(document).ready(function () {	
		$("#start_time").val(getCurrentDate());
		$("#end_time").val(getCurrentDate());
		
		var categoryList = SubjectRPC.getSubCategoryAllName(site_id);
		categoryList = List.toJSList(categoryList);
		if(categoryList != null && categoryList.size() > 0)
		{
			var str = '<li style="float:left;height:20px"><input type="checkbox" id="checkAll" name="checkAll" checked="true" onclick="checkboxAll(this)"/><b>全选</b></li>';
			for(var i=0;i<categoryList.size();i++)
			{
				str += '<li style="float:left;height:20px"><input type="checkbox" id="category_id" name="category_id" value="'+categoryList.get(i).category_id+'" checked onclick="unCheckAll(this)"/><label>'+categoryList.get(i).category_name+'</label></li>';
			}
			$("#category_div").append(str);
		}

		initButtomStyle();
		init_input();
	}); 
	
	
	function checkboxAll(obj)
	{
		$(":checkbox").checkAll($(obj).is(':checked'));
	}

	function fnOK()
	{
		var s_time = $("#start_time").val();
		var e_time = $("#end_time").val();
		if(s_time != "" && e_time != "")
		{
			s_time = s_time+" 00:00";
			e_time = e_time+" 23:29";

			var val=new Validator();
			val.checkDataTimeAfter(s_time,e_time,"统计时间","start_time");	
			if(!val.getResult()){		
				return;
			}
		}
		else
		{
			top.msgWargin("请输入时间范围");
			return;
		}
		var category_name = "";
		var category_ids = "";
		$(":checkbox[id=category_id]").each(function(){
			if($(this).is(':checked'))
			{
				category_ids += ","+$(this).val();
				category_name += ","+$(this).parent().text();
			}				
		});
		category_ids = category_ids.substring(1);	
		category_name = category_name.substring(1);

		if(category_ids == "")
		{
			top.msgWargin("请选择访谈模型");
			return;
		}
		window.location.href = "subjectCountDetail.jsp?site_id="+site_id+"&st="+s_time+"&et="+e_time+"&ot="+$("#ordery_type").val()+"&cn="+$("#count_num").val()+"&ids="+category_ids+"&na="+category_name+"&tt="+$("#time_type").val();
	}
	//-->
	</SCRIPT>
	
</head> 
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="count_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>时间范围：</th>
			<td >
				<input class="Wdate width100" type="text" name="start_time" id="start_time"  onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true">
		         --
			    <input class="Wdate width100" type="text" name="end_time" id="end_time"  onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true">
			</td>			
		</tr>
		<tr style="display:none">
			<th>统计方式：</th>
			<td >
				<select id="time_type" name="time_type" class="width205">
				 <option value="apply_time">按照创建时间统计</option>
				 <option value="publish_time">按照发布时间统计</option>
				 <option value="start_time">按照直播时间统计</option>
				</select>
			</td>			
		</tr>		
		<tr>
			<th>排行依据：</th>
			<td colspan="3">
				<select id="ordery_type" name="ordery_type" class="width205">
				 <option value="user">按照访问人数</option>
				 <option value="chat">按照网友发言条数</option>
				</select>	
			</td>
		</tr>
		<tr>
			<th>显示条数：</th>
			<td colspan="3">
				<select id="count_num" name="count_num" class="width205">
				 <option value="10">前10条</option>
				 <option value="20">前20条</option>
				 <option value="30">前30条</option>
				 <option value="0">所有的</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>访谈模型：</th>
			<td colspan="3">				
				  <ul id="category_div">
				   
				  </ul>							
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
			<input id="addButton" name="btn1" type="button" onclick="fnOK()" value="统计" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('count_table','')" value="重置" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>

</html> 

