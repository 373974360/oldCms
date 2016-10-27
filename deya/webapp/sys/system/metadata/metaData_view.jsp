<%@ page contentType="text/html; charset=utf-8"%>
<%
	String meta_id = request.getParameter("meta_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>元数据维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/metaDataList.js"></script>
<script type="text/javascript">

var meta_id = "<%=meta_id%>";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();

	if(meta_id != "" && meta_id != "null" && meta_id != null)
	{		
		defaultBean = MetaDataRPC.getMetaDataBean(meta_id);
		if(defaultBean)
		{
			$("#metadata_table").autoFill(defaultBean);					
		}
		disabledWidget();
	}
	
});


</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="metadata_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>类型：</th>
			<td colspan="3">
				<select id="is_core" name="is_core" class="width205">				
				 <option value="0">核心元数据</option>
				 <option value="1">业务元数据</option>
				</select>
			</td>			
		</tr>
		<tr>
			<th>中文名称：</th>
			<td class="width250">
				<input id="meta_cname" name="meta_cname" type="text" class="width200" value="" onblur="checkInputValue('meta_cname',false,240,'中文名称','')"/>
				<input type="hidden" name="meta_id" id="meta_id" value="0">
			</td>
			<th>定义：</th>
			<td >
				<input id="meta_define" name="meta_define" type="text" class="width200" value="" onblur="checkInputValue('meta_define',false,240,'定义','')"/>
			</td>
		</tr>	
		<tr>
			<th>英文名称：</th>
			<td >
				<input id="meta_ename" name="meta_ename" type="text" class="width200" value="" onblur="checkInputValue('meta_ename',false,80,'英文名称','checkLetter')"/>
			</td>
			<th>数据类型：</th>
			<td >
				<select id="meta_datatype" name="meta_datatype" class="width205">
				 <option value="string">字符串</option>
				 <option value="datetime">日期型</option>
				 <option value="number">数值型</option>
				 <option value="mixed">复合型</option>
				</select>
			</td>
		</tr>	
		<tr>
			<th>值域：</th>
			<td >
				<input id="meta_codomain" name="meta_codomain" type="text" class="width200" value="" onblur="checkInputValue('meta_codomain',false,240,'值域','')"/>
			</td>
			<th>短名：</th>
			<td >
				<input id="meta_sname" name="meta_sname" type="text" class="width200" value="" onblur="checkInputValue('meta_sname',false,20,'短名','checkLetter')"/>
			</td>
		</tr>
		<tr>
			<th>是否可选：</th>
			<td >
				<input id="meta_iselect" name="meta_iselect" type="radio" value="0" checked="true"/><label>可选</label>
				<input id="meta_iselect" name="meta_iselect" type="radio" value="1" /><label>必选</label>
			</td>
			<th>最大出现次数：</th>
			<td >
				<input id="meta_maxtimes" name="meta_maxtimes" type="text" class="width200" value="0" onblur="checkInputValue('meta_maxtimes',false,20,'最大出现次数','checkInt')"/>
			</td>
		</tr>		
		<tr>
			<th>元数据类型：</th>
			<td >
				<select id="meta_type" name="meta_type" class="width205">
				 <option value=""></option>
				 <option value="element">元数据元素</option>
				 <option value="entity">元数据实体</option>
				</select>
			</td>
			<th>实体与元素关系：</th>
			<td >
				<input id="parent_id" name="parent_id" type="text" class="width200" value="0" onblur="checkInputValue('parent_id',false,20,'实体与元素关系','')"/>
			</td>
		</tr>
		<tr>
			<th>取值示例：</th>
			<td colspan="3">
				<input id="meta_sample" name="meta_sample" type="text" style="width:567px" value="" onblur="checkInputValue('meta_sample',true,240,'取值示例','')"/>
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
			<input id="userAddButton" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="关闭" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
