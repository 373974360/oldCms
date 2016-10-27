<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String site_id = request.getParameter("site_id");
	site_id = (site_id==null) ? "" : site_id;
	
	String app_id = request.getParameter("app_id");
	String type = request.getParameter("type");
	String id = request.getParameter("id"); // 信息标签的主键ID
	String wcat_id = request.getParameter("wcat_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息标签分类</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css" />
<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script charset="utf-8" src="../../kindeditor/kindeditor.js"></script>
<!-- <script type="text/javascript" src="../../js/editArea/edit_area_full.js"></script> -->
<script type="text/javascript" src="/sys/js/mCodeArea/js/codemirror.js"></script>
<script type="text/javascript" src="js/wareList.js"></script>
<script type="text/javascript">
	
	var app_id = "<%=app_id%>";
	var site_id = "<%=site_id%>";
	var type = "<%=type%>";
	var id = "<%=id%>";
	var wcat_id = "<%=wcat_id%>";
	
	con_m.put("app_id",app_id);
	con_m.put("site_id",site_id);
	
	var val = new Validator();
	var defaultBean;
	$(document).ready(function(){
		initButtomStyle();
		init_FromTabsStyle();
		init_input();
		if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
		
		initPage();
		showSelectDiv("wcat_name","cat_tree_div",300);
		showWareCateTree();

		mCodeIconMouse();///sys/js/mCodeArea/js/codemirror.js
	});
	
	function initPage()
	{		
		if(type == "update")
		{
			defaultBean = WareRPC.getWareByID(id);	
			if(defaultBean.info_num < 0)
				defaultBean.info_num = 1;
			$("#savaBtn").click(saveUpdateWare);
			$("#ware_table").autoFill(defaultBean);
			$("#ware_content").val(defaultBean.ware_content);			
			//editAreaLoader.setValue("ware_content",defaultBean.ware_content);
			$(":radio[name='tware_type'][value='"+defaultBean.ware_type+"']").attr("checked","checked");
			$("#info_num").attr("readOnly","readOnly");
			changeWare_type(defaultBean.ware_type);
		}
		else
		{
			$("#savaBtn").click(saveAddWare);
			changeWare_type(0);
		}
		
		var categoryBean = WareRPC.getWareCategoryByID(wcat_id);
		$("#wcat_name").val(categoryBean.wcat_name);
		$("#wcat_id").val(wcat_id);

		initEditArea("ware_content");
	}
	
	function initEditArea(textareaID)
	{
		/*
		editAreaLoader.init({
			id: textareaID	// id of the textarea to transform		
			,start_highlight: true	// if start with highlight
			,allow_resize: "both"
			,font_size: "12"
			,allow_toggle: true
			,word_wrap: true
			,language: "zh"
			,syntax: "html"	
			//,display: "later"
			,toolbar: "new_document, save, load, |,search, go_to_line, |, undo, redo, |, select_font, |, word_wrap, |, help"
		});	*/
		editAreaLoader = CodeMirror.fromTextArea(textareaID, {
		height: "260px",
		width: "97%",		
		parserfile: ["/sys/js/mCodeArea/js/parsexml.js", "/sys/js/mCodeArea/js/parsecss.js", "/sys/js/mCodeArea/js/tokenizejavascript.js", "/sys/js/mCodeArea/js/parsejavascript.js", "/sys/js/mCodeArea/js/parsehtmlmixed.js"],
		stylesheet: ["/sys/js/mCodeArea/css/xmlcolors.css", "/sys/js/mCodeArea/css/jscolors.css", "/sys/js/mCodeArea/css/csscolors.css"],
		path: "js/",
		continuousScanning: 500,
		lineNumbers: true
	  });
	}
	
	function changeWare_type(wtype)
	{
		$("#tr_info_num").hide();	//行数
		$("#tr_rss_url").hide();	//RSS地址
		$("#tr_ware_interval").hide();	//更新频率
		 
		switch(wtype)
		{
			case 0:	//静态
			 
			 break
			case 1:	//自动
			 $("#tr_ware_interval").show();
			 break
			case 2:	//手动
			 $("#tr_info_num").show();
			 break
			case 3:	//RSS
			 $("#tr_rss_url").show();
			 $("#tr_ware_interval").show();
			 break
			default: 
		}
	}	
	
	function showWareCateTree(){
		json_data = eval(WareRPC.getJSONTreeStr(con_m));
		setLeftMenuTreeJsonData(json_data);
		initMenuTree();
		treeNodeSelected(wcat_id);
	}
	
	function initMenuTree()
	{
		$('#leftMenuTree').tree({		
			onClick:function(node){			
				selectedCat(node.id,node.text);            
			}
		});
	}

	//点击树节点,修改菜单列表数据 内容
	function selectedCat(id,text){
		$("#wcat_name").val(text);
		$("#wcat_id").val(id);
		$("#cat_tree_div").hide();
	}
</script>
<style>
.customLable{}
.customLable li{ color:#333; cursor:pointer; text-decoration:underline;}

#m_code_editor{height:24px;width:100%;border:1px solid #C4CAD8;background:#E1EEF8}
#m_code_editor .code_icon{float:left;width:22px;height:22px;padding:1px 2px}
</style>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="ware_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>所属分类：</th>
			<td>
				<input type="text" id="wcat_name" value="" class="width200" readonly="readonly"/><input type="hidden" id="wcat_id" value="" />
				<div id="cat_tree_div" class="select_div tip hidden border_color">
					<div id="leftMenuBox">
						<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
							<ul id="leftMenuTree" class="easyui-tree" animate="true">
							</ul>
						</div>
					</div>
				</div>
			
				<input id="id" name="id" type="hidden" value="" />
				<input id="ware_id" name="ware_id" type="hidden" value="" />
				<input id="site_id" name="site_id" type="hidden" value="<%=site_id%>" />
				<input id="app_id" name="app_id" type="hidden" value="<%=app_id%>" />
				<input id="ware_type" name="ware_type" type="hidden" value="0"/>
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>标签名称：</th>
			<td>
				<input id="ware_name" name="" type="text" class="width200" value=""  onblur="checkInputValue('ware_name',false,36,'标签名称','')"/><span id="wordnum">12</span>字
			</td>
		</tr>
		
		<tr>
			<th><span class="f_red">*</span>标签类型：</th>
			<td>
				<ul>
					<li><input name="tware_type" onclick="changeWare_type(0)" type="radio" checked="checked" value="0"/><label onclick="changeWare_type(0)" for="d">静态</label></li>
					<li><input name="tware_type" onclick="changeWare_type(1)" type="radio" value="1"/><label onclick="changeWare_type(1)" for="e">自动</label></li>
					<li><input name="tware_type" onclick="changeWare_type(2)" type="radio" value="2"/><label onclick="changeWare_type(2)" for="f">手动</label></li>
					<!--
					<li><input name="tware_type" onclick="changeWare_type(3)" type="radio" value="3"/><label onclick="changeWare_type(3)" for="g">RSS</label></li>
					-->
				</ul>
			</td>
		</tr>
		<tr id="tr_rss_url">
			<th>RSS地址：</th>
			<td>
				<input id="rss_url" name="rss_url" type="text" class="width200" value="" />
			</td>
		</tr>
		<tr id="tr_info_num">
			<th><span class="f_red">*</span>信息行数：</th>
			<td>
				<input id="info_num" name="info_num" type="text" class="width200" value="1"   onblur="checkInputValue('info_num',false,10,'信息行数','checkNumber')"/>行&nbsp;&nbsp;&nbsp;&nbsp;
				接受推荐：&nbsp;<input type="checkbox" id="receive_recom" name="receive_recom" value="1">
			</td>
		</tr>
		<tr id="tr_ware_interval">
			<th><span class="f_red">*</span>更新频率：</th>
			<td>
				<input id="ware_interval" name="ware_interval" type="text" class="width200" value="0"  onblur="checkInputValue('ware_interval',false,10,'更新频率','checkNumber')"/>秒（0表示手动）
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;"><span class="f_red">*</span>标签内容：</th>
			<td style="border:1px solid #9DBFDD;">
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
				<textarea id="ware_content" name="ware_content" style="width:90%;;height:300px;"></textarea>
			</td>
		</tr>
		<tr>
			<th>宽度：</th>
			<td>
				<input id="ware_width" name="ware_width" type="text" class="width200" value="0" />px
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">备注：</th>
			<td>
				<textarea id="ware_memo" name="ware_memo" style="width:90%;;height:80px;"></textarea>
			</td>
		</tr>
	</tbody>
</table>
<!--隔线开始-->
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<!--隔线结束-->
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="savaBtn" name="btn1" type="button" onclick="javascript:void(0);" value="保存" />
			<input id="cancelBtn" name="btn2" type="button" onclick="backWareList()" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>