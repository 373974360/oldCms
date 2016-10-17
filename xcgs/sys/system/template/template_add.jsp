<%@ page contentType="text/html; charset=utf-8"%>
<%
String tc_id = request.getParameter("tc_id");
if(tc_id == null){
	tc_id = "0";
}
	String t_id = request.getParameter("t_id");
	String app_id = request.getParameter("app");
	String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模板维护</title>


<jsp:include page="../../include/include_tools.jsp"/>
<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css" />
<link rel="stylesheet" type="text/css" href="../../js/codeMirror/codemirror.css">
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="../../js/codeMirror/codemirror.js"></script>
<script type="text/javascript" src="../../js/codeMirror/xml.js"></script>
<script type="text/javascript" src="../../js/codeMirror/javascript.js"></script>
<script type="text/javascript" src="../../js/codeMirror/css.js"></script>
<script type="text/javascript" src="../../js/codeMirror/htmlmixed.js"></script>

<script type="text/javascript" src="js/tem_add_tools.js"></script>
<script type="text/javascript" src="js/template.js"></script>
<script type="text/javascript" src="/sys/js/jquery_selecter.js"></script>
<script type="text/javascript" src="js/template_custom.js"></script>
<script type="text/javascript">

var app_id = "<%=app_id%>";
var site_id = "<%=site_id%>";
var t_id = "<%=t_id%>";
var tcid = "<%=tc_id%>";
var defaultBean;
var areaId = "t_content";

var con_m = new Map();
con_m.put("app_id","0");
con_m.put("site_id","<%=site_id%>");
var wcat_id = "-1";
var wBeanList;
var wareMap = new Map();
var codeMap = new Map();

$(document).ready(function(){
	initButtomStyle();
	init_input();
	init_FromTabsStyle();
    initEditArea(areaId);
	
	
	showSelectDiv("wcat_name","cat_tree_div",300);
	showSelectDiv("template_name","template_tree_div",300);
	showWareCateTree();
	showWareBeanList("");
	//showCodeBeanList();
	//changeFormModel("cms_article_item");//内容模型
	if(t_id != "" && t_id != "null" && t_id != null)
	{		
		defaultBean = TemplateRPC.getTemplateEditById(t_id,'<%=site_id%>','<%=app_id%>');
		if(defaultBean)
		{
			$("#Template_table").autoFill(defaultBean);	
			/*
			if(defaultBean.t_content == null || defaultBean.t_content == "null")
				setValue("");
			else
				setValue(defaultBean.t_content);*/
            setValue(defaultBean.t_content);
		}
		$("#addButton").click(updateTemplateData);
	}
	else
	{
		//$("#app_id").val("<%=app_id%>");
		//$("#site_id").val("<%=site_id%>");
		$("#addButton").click(addTemplateData);
	}	



	//mCodeIconMouse();///sys/js/mCodeArea/js/codemirror.js

});

</script>
<style>
.table_form2{ width:100%; border-collapse:collapse;}
.table_form2 tr{}
.table_form2 th{ width:100px; text-align:right; padding-right:5px; color:#32609E; border-collapse:collapse; vertical-align:middle; padding:4px 4px;}
.table_form2 td{ text-align:left; color:#32609E; border-collapse:collapse; vertical-align:middle; vertical-align:middle; padding:4px 2px; padding-left:10px;}

#m_code_editor{height:24px;width:100%;border:1px solid #C4CAD8;background:#E1EEF8}
#m_code_editor .code_icon{float:left;width:22px;height:22px;padding:1px 2px}
</style>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="Template_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>			
			<th><span class="f_red">*</span>模板名称：</th>
			<td>
				<input id="t_cname" name="t_cname" type="text" class="width200" value="" onblur="checkInputValue('t_cname',false,60,'模板中文名','')"/>
				
				<input id="tcat_id" name="tcat_id" type="hidden" class="width200" value="<%=tc_id %>"/>
				<input id="t_id" name="t_id" type="hidden" class="width200" value="0"/>
				<input id="t_ver" name="t_ver" type="hidden" class="width200" value="0"/>
				<input id="creat_user" name="creat_user" type="hidden" class="width200" value="0"/>
				<input id="creat_dtime" name="creat_dtime" type="hidden" class="width200" value="0"/>
				<input id="modify_user" name="modify_user" type="hidden" class="width200" value="0"/>
				<input id="modify_dtime" name="modify_dtime" type="hidden" class="width200" value="0"/>
				<input id="app_id" name="app_id" type="hidden" class="width200" value="<%=app_id%>"/>
				<input id="site_id" name="site_id" type="hidden" class="width200" value="<%=site_id%>"/>
				<input id="t_path" name="t_path" type="hidden" class="width200" value="0"/>
			</td>
			<th class="hidden"><span class="f_red">*</span>模板英文名：</th>
			<td class="hidden">
				<input id="t_ename" name="t_ename" type="text" class="width200" value="vm" />
			</td>
		</tr>
		
	</tbody>
</table>
<table id="" class="table_form2" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0" style="width:100%;height:407px;">
					<tr>
						<td style="border:1px solid #9DBFDD;">
                            <!--
							<div style="" id="m_code_editor">
							  <div class="code_icon">
							    <img src='/sys/js/mCodeArea/images/creaList.png' width="20px" height="20px" alt="公用列表生成工具" style="padding:1px 2px;cursor:pointer" onclick="mCodeIconEvent('creaList')">
							  </div>
							  <div class="code_icon">
							    <img src='/sys/js/mCodeArea/images/hotList.png' width="20px" height="20px" alt="热点信息生成工具" style="padding:1px 2px;cursor:pointer" onclick="mCodeIconEvent('hotList')">
							  </div>
							  <div class="code_icon">
							    <img src='/sys/js/mCodeArea/images/creaLink.png' width="20px" height="20px" alt="获取链接地址工具" style="padding:1px 2px;cursor:pointer" onclick="mCodeIconEvent('creaLink')">
							  </div>
							  <div class="code_icon">
							    <img src='/sys/js/mCodeArea/images/selImg.png' width="20px" height="20px" alt="选择图片" style="padding:1px 2px;cursor:pointer" onclick="mCodeIconEvent('selImg')">
							  </div>
							</div>
							-->
                            <div id="t_content" name="t_content" style="width:100%;height:437px;"></div>
						</td>
						<td width="220" style="vertical-align:top">
							<!-- 组件页签开始 -->
							<table class="table_option" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td align="left"  width="100%" style="padding-bottom:0px;">
										<ul class="fromTabs" style="margin-left: -10px; margin-right:-2px;">
											<li class="list_tab list_tab_cur">
												<div class="tab_left">
													<div class="tab_right">标签</div>
												</div>
											</li>
											<li class="list_tab">
												<div class="tab_left">
													<div class="tab_right">片断</div>
												</div>
											</li>
											<li class="list_tab">
												<div class="tab_left" onclick="showTemplateCategoryTree()">
													<div class="tab_right">模板</div>
												</div>
											</li>
											<li class="list_tab">
												<div class="tab_left">
													<div class="tab_right">对象</div>
												</div>
											</li>
										</ul>
									</td>
								</tr>
							</table>
							<div style="border:1px solid #B2C7DC; border-top:none; height:400px; padding:7px; overflow:auto;">
								<div class="infoListTable" id="listTable_0">
									<div id="a11">
										<input type="text" id="wcat_name" value="标签分类" style="width:176px; height:18px; overflow:hidden;" readonly="readonly"/>
										<div id="cat_tree_div" class="select_div tip hidden border_color" style="width:176px; height:340px; overflow:hidden;" >
											<div id="leftMenuBox">
												<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
													<ul id="leftMenuTree" class="easyui-tree" animate="true" style="width:220px; height:360px;">
													</ul>
												</div>
											</div>
										</div>
										<ul class="wareList" id="wareListData">
											
										</ul>
									</div>
								</div>	
								<div class="infoListTable hidden" id="listTable_1" >
									<div id="a22">
										<ul class="wareList" id="codeListData">
											
										</ul>
									</div>
								</div>
								<div class="infoListTable hidden" id="listTable_2" >
									<div id="template_div">
										<input type="text" id="template_name" value="模板分类" style="width:176px; height:18px; overflow:hidden;" readonly="readonly"/>
										<div id="template_tree_div" class="select_div tip hidden border_color" style="width:176px; height:340px; overflow:hidden;" >
											<div id="leftMenuBox2">
												<div id="leftMenu2" class="contentBox6 textLeft" style="overflow:auto;height:307px;">
													<ul id="leftMenuTree2" class="easyui-tree" animate="true" style="width:382px; height:360px;">
													</ul>
												</div>
											</div>
										</div>
										<ul class="wareList" id="templateList">
											
										</ul>
									</div>
								</div>
								<div class="infoListTable hidden" id="listTable_3" >
									<select id="model_select" style="width:176px;" onchange="changeFormModel(this.value)">
										<option value="cms_article_item">文章</option>
										<option value="cms_pic_item">图组</option>
										<option value="cms_video_item">视频</option>										
										<option value="gk_tygs_item">公开－通用格式</option>
										<option value="gk_flgw_item">公开－法律公文</option>
										<option value="gk_jggk_item">公开－机构概况</option>
										<option value="gk_ldcy_item">公开－领导成员</option>
										<option value="gk_xsjg_item">公开－内设科室及下属机构</option>
										<option value="gk_xzzf_item">公开－行政执法</option>
										<option value="gk_bszn_item">公开－办事指南</option>
										<option value="gk_gkzy_item">公开－公开指引</option>
										<option value="gk_gkzn_item">公开－公开指南</option>
										<option value="gk_gknb_item">公开－公开年报</option>
										<option value="sq_item">诉求内容</option>
									</select>
									<ul class="wareList" id="formModelList" style="height:360px;overflow:auto">
											
									</ul>
								</div>
							</div>
							<!-- 组件页签结束 -->	
						</td>
					</tr>
				</table>
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('Template_table',t_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="closePage();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
