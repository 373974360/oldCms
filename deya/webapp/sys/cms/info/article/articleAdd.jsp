<%@ page contentType="text/html; charset=utf-8"%>
<%
	String author_id = request.getParameter("author_id");
	String app_id = request.getParameter("app");
	String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息维护</title>
<link type="text/css" rel="stylesheet" href="../../../styles/themes/default/tree.css" />
<link type="text/css" rel="stylesheet" href="../../../styles/sq.css" />


<jsp:include page="../../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/article.js"></script>
<script type="text/javascript">

var author_id = "<%=author_id%>";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	init_editer_min("info_content");

	if(author_id != "" && author_id != "null" && author_id != null)
	{		
		defaultBean = AssistRPC.getAuthorById(author_id);
		if(defaultBean)
		{
			$("#info_article_table").autoFill(defaultBean);					
		}
		$("#addButton").click(updateInfoData);
	}
	else
	{
		$("#app_id").val("<%=app_id%>");
		$("#site_id").val("<%=site_id%>");
		$("#addButton").click(addInfoData);
	}
});


</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="info_article_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>
				信息ID：
			</th>
			<td>
				<input id="info_id" type="text" class="width200" value="" />
			</td>
		</tr>
		<tr>
			<th>
				分类ID：
			</th>
			<td>
				<input id="cat_id" type="text" class="width200" value="1" />
			</td>
		</tr>
		<tr>
			<th>
				模型ID：
			</th>
			<td>
				<input id="model_id" type="text" class="width200" value="1" />
			</td>
		</tr>
		<tr>
			<th>
				引用ID：
			</th>
			<td>
				<input id="from_id" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				标题：
			</th>
			<td>
				<input id="title" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				副标题：
			</th>
			<td>
				<input id="subtitle" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				标题颜色：
			</th>
			<td>
				<input id="title_color" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				缩略图：
			</th>
			<td>
				<input id="thumb_url" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				摘要：
			</th>
			<td>
				<input id="description" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				作者：
			</th>
			<td>
				<input id="author" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				来源：
			</th>
			<td>
				<input id="source" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				META关键词：
			</th>
			<td>
				<input id="info_keywords" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				META网页描述：
			</th>
			<td>
				<input id="info_description" type="text" class="width200"
					value="11" />
			</td>
		</tr>
		<tr>
			<th>
				Tags：
			</th>
			<td>
				<input id="tags" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				内容页静态访问地址：
			</th>
			<td>
				<input id="content_url" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				工作流ID：
			</th>
			<td>
				<input id="wf_id" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				步骤ID：
			</th>
			<td>
				<input id="step_id" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				信息状态：
			</th>
			<td>
				<input id="info_status" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				最终状态：
			</th>
			<td>
				<input id="final_status" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				权重：
			</th>
			<td>
				<input id="weight" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				总点击数：
			</th>
			<td>
				<input id="hits" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				日点击数：
			</th>
			<td>
				<input id="day_hits" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				周点击数：
			</th>
			<td>
				<input id="week_hits" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				月点击数：
			</th>
			<td>
				<input id="month_hits" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				最后点击时间：
			</th>
			<td>
				<input id="lasthit_dtime" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				是否允许评论：
			</th>
			<td>
				<input id="is_allow_comment" type="text" class="width200"
					value="11" />
			</td>
		</tr>
		<tr>
			<th>
				评论次数：
			</th>
			<td>
				<input id="comment_num" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				发布时间：
			</th>
			<td>
				<input id="released_dtime" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				录入人：
			</th>
			<td>
				<input id="input_user" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				录入时间：
			</th>
			<td>
				<input id="input_dtime" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				最新编辑人：
			</th>
			<td>
				<input id="modify_user" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				最新编辑时间：
			</th>
			<td>
				<input id="modify_dtime" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				最近操作时间：
			</th>
			<td>
				<input id="opt_dtime" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				是否自动发布：
			</th>
			<td>
				<input id="is_auto_up" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				上线时间：
			</th>
			<td>
				<input id="up_dtime" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				是否自动撤消：
			</th>
			<td>
				<input id="is_auto_down" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				down_dtime：
			</th>
			<td>
				<input id="down_dtime" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				是否主信息：
			</th>
			<td>
				<input id="is_host" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				标题Hash值：
			</th>
			<td>
				<input id="title_hashkey" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				应用id：
			</th>
			<td>
				<input id="app_id" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				站点id：
			</th>
			<td>
				<input id="site_id" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>
				版本：
			</th>
			<td>
				<input id="i_ver" type="text" class="width200" value="11" />
			</td>
		</tr>

<%--		<tr>--%>
<%--			<th>内容分类：</th>--%>
<%--			<td>--%>
<%--				<input type="text" id="cat_name" value="" class="width200" readOnly="readOnly"/><input type="hidden" id="cat_id" value="" />--%>
<%--				<div id="cat_tree_div" class="select_div tip hidden border_color">--%>
<%--					<div id="leftMenuBox">--%>
<%--						<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">--%>
<%--							<ul id="leftMenuTree" class="easyui-tree tree_list_none" animate="true">--%>
<%--							</ul>--%>
<%--						</div>--%>
<%--					</div>--%>
<%--				</div>--%>
<%--			</td>--%>
<%--		</tr>	--%>
<%--		<tr>--%>
<%--			<th>发生地区：</th>--%>
<%--			<td>--%>
<%--				<input type="text" id="area_name" value="" class="width200" readOnly="readOnly"/><input type="hidden" id="area_id" value="" />--%>
<%--				<div id="area_tree_div" class="select_div tip hidden border_color">--%>
<%--					<div id="areaBox">--%>
<%--						<div id="areaMenu" class="contentBox6 textLeft" style="overflow:auto">--%>
<%--							<ul id="areaTree" class="easyui-tree tree_list_none" animate="true">--%>
<%--							</ul>--%>
<%--						</div>--%>
<%--					</div>--%>
<%--				</div>--%>
<%--			</td>--%>
<%--		</tr>	--%>
<%--		<tr id="gzyy_open_tr">--%>
<%--			<th>公众意愿：</th>--%>
<%--			<td>--%>
<%--			<ul>--%>
<%--				<li><input id="is_open" name="is_open" type="radio"  value="1"/><label>公开</label></li>--%>
<%--				<li><input id="is_open" name="is_open" type="radio" checked="checked"  value="0" /><label>不公开</label></li>--%>
<%--			</ul>--%>
<%--			</td>--%>
<%--		</tr>--%>
		<tr>
			<th>信息内容 ：</th>
			<td>
				<textarea id="info_content" name="info_content" style="width:620px;height:200px"></textarea>
			</td>
		</tr>
		<tr>
			<th>页数：</th>
			<td>
				<input id="page_count" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>每页最大字数：</th>
			<td>
				<input id="prepage_wcount" type="text" class="width200" value="11" />
			</td>
		</tr>
		<tr>
			<th>字数：</th>
			<td>
				<input id="word_count" type="text" class="width200" value="11" />
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
			<input id="addReset" name="btn1" type="button" onclick="formReSet('info_article_table',info_id)" value="重置" />
			<input id="addCancel" name="btn1" type="button" onclick="tab_colseOnclick(curTabIndex)" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
