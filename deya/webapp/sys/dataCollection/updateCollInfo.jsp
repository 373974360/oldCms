<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String info_id = request.getParameter("info_id");
String site_id = request.getParameter("site_id");
if(site_id == null || "".equals(site_id) || "null".equals(site_id))
	site_id = "";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  
<title>修改采集信息</title>
<jsp:include page="../include/include_tools.jsp"/>
<script type="text/javascript">
var CollectionDataRPC = jsonrpc.CollectionDataRPC;
var articleInfoBean = new Bean("com.deya.wcm.dataCollection.bean.ArticleInfoBean",true);
var infoid = "<%=info_id%>"; 
var tab_index = "${param.tab_index}";
var contentId = "art_content";
var site_id = "<%=site_id%>";
var defaultBean;

$(document).ready(function(){
	init_input();	
	initButtomStyle();
    initUeditor(contentId);

	if(infoid != "" && infoid != "null" && infoid != null)
	{
		defaultBean = CollectionDataRPC.getCollDataInfobyid(infoid);
		if(defaultBean)
		{
			$("#collinfo_article_table").autoFill(defaultBean);
            setV(contentId,defaultBean.art_content);
		}
		$("#updateButton").click(updateCollDataInfobyid);
	}
});

function updateCollDataInfobyid()
{
	var bean = BeanUtil.getCopy(articleInfoBean);	
	$("#collinfo_article_table").autoBind(bean);
	bean.id = infoid;
	bean.art_content = getV(contentId);
	if(CollectionDataRPC.updateCollDataInfo(bean))
	{
		msgAlert("修改成功!");
		getCurrentFrameObj(tab_index).reloadInfoDataList();
		tab_colseOnclick(curTabIndex);
	}else{
		msgAlert("修改失败!");
	}
	
}
</script>
</head>

<body>
  <table id="collinfo_article_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>标题：</th>
			<td>
				<input type="text" id="art_title" name="art_title" style="width:400px;" />
			</td>
		</tr>
		<tr>
			<th>来源：</th>
			<td>
				<input type="text" id="art_source" name="art_source"  style="width:200px;" />
			</td>
		</tr>
		<tr>
			<th>作者：</th>
			<td>
				<input type="text" id="art_author" name="art_author"  style="width:200px;" />
			</td>
		</tr>
		<tr>
			<th>关键词：</th>
			<td>
				<textarea id="art_keyWords" name="info_keywords" style="width:90%;height:70px"></textarea>
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">详细内容：</th>
			<td style="">
                <script id="art_content" type="text/plain" style="width:100%;height:300px;"></script>
				<div class="hidden" id="word_count_div"></div>
			<span class="blank9"></span>
		</tr>
	  </tbody>
	</table>
	<span class="blank12"></span>
	<div class="line2h"></div>
	<span class="blank3"></span>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle" style="text-indent:100px;">
				<input id="updateButton" name="btn1" type="button" onclick="" value="保存" />
				<input id="addCancel" name="btn1" type="button" onclick="tab_colseOnclick(curTabIndex)" value="取消" />
			</td>
		</tr>
	</table>
</body>
</html>
