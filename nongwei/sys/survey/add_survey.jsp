<%@ page contentType="text/html; charset=utf-8"%>
<%
String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 

	<title>访谈主题分类添加</title> 	
	<link rel="stylesheet" type="text/css" href="../js/jquery-ui/themes/base/jquery.ui.all.css"  />
	<link rel="stylesheet" type="text/css" href="../styles/themes/icon.css" />
	<jsp:include page="../include/include_tools.jsp"/>	
	<!-- <script type="text/javascript" src="/cms.files/component/common/htmledit/fckeditor.js"></script>
	<script type="text/javascript" src="/cms.files/component/common/htmledit/editor/js/cicroUtil.js"></script> -->	
	<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
	<script language="javascript" src="../js/jquery.uploadify.js"></script>
	<script language="javascript" src="../js/uploadFile/swfobject.js"></script>

	<script type="text/javascript" src="../js/jquery-ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="../js/jquery-ui/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="../js/jquery-ui/jquery.ui.mouse.js"></script>
	<script type="text/javascript" src="../js/jquery-ui/jquery.ui.button.js"></script>
	<script type="text/javascript" src="../js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../js/jquery-ui/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="../js/jquery-ui/jquery.ui.position.js"></script>
	<script type="text/javascript" src="../js/jquery-ui/jquery.ui.dialog.js"></script>
	<script type="text/javascript"  src="../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>

	<script type="text/javascript"  src="js/surveyList.js"></script>
	<script type="text/javascript"  src="js/survey_designer.js"></script>

	<style>
	*{margin:0 0;padding:0 0}
	/*组件区域样式*/
	 #ware_lab_div{float:right;padding-right:0px;width:259px;}	.ware_lab_check{float:left;margin:auto;width:85px;height:19px;font-weight:bold;text-align:center;padding-top:9px;color:#43546E;background-image:url('../images/gpps_sprites.gif');background-position:-42px -106px;}	
	 .ware_lab_default{float:left;margin:auto;width:87px;height:18px;padding-top:9px;font-weight:bold;color:#788B9A;text-align:center;cursor:pointer;border-bottom:1px solid #8797A6;}
	
	#attrs_div{border-right:1px solid #8797A6;border-left:1px solid #8797A6;border-bottom:1px solid #8797A6;background:#F5FAFE;clear:both;overflow-y:auto;overflow-x:hidden}
	.attrs_div{width:94%;margin:0 auto;text-align:left;}
	#subject_attr_div{padding-left:8px}
	.text_div{height:17px;clear:both;}
	.radioList_button{width:104px;height:23px;background-image:url(../images/gpps_sprites.gif);background-position:-131px -107px;cursor:pointer;}
	.checkboxList_button{width:104px;height:23px;background-image:url(../images/gpps_sprites.gif);background-position:-239px -107px;cursor:pointer;}
	.selectOnly_button{width:104px;height:23px;background-image:url(../images/gpps_sprites.gif);background-position:-346px -107px;cursor:pointer;}
	.matrix_button{width:104px;height:23px;background-image:url(../images/gpps_sprites.gif);background-position:-454px -107px;cursor:pointer;}
	.textareas_button{width:104px;height:23px;background-image:url(../images/gpps_sprites.gif);background-position:-670px -107px;cursor:pointer;}
	.uploadfile_button{width:104px;height:23px;background-image:url(../images/gpps_sprites.gif);background-position:-131px -133px;cursor:pointer;}
	.voteRadio_button{width:104px;height:23px;background-image:url(../images/gpps_sprites.gif);background-position:-239px -133px;cursor:pointer;}
	.voteCheckbox_button{width:104px;height:23px;background-image:url(../images/gpps_sprites.gif);background-position:-346px -133px;cursor:pointer;}
	.scale_button{width:104px;height:23px;background-image:url(../images/gpps_sprites.gif);background-position:-563px -107px;cursor:pointer;}
	
	.common_button_frame_div{clear:both;height:220px;overflow:auto}
	.common_button_div{clear:both;height:25px;}

	/*属性　选项文字表格样式*/
	.itemattr_title_td{background:#DAE8F8;border-bottom:2px solid #FFFFFF}
	.itemattr_td{background:#DAE8F8;padding:3px}
	.itemattr_td_spa{background:#DAE8F8;border-bottom:2px solid #FFFFFF;padding:3px}

	DIV{color:#355286;}
	IMG{cursor:pointer}
	TH{padding-right:24px}
	.blankH5{height:5px;clear:both;overflow:hidden;display:block;}
	.blankH9{height:9px;clear:both;overflow:hidden;display:block;}
	.blankH29{height:29px;clear:both;overflow:hidden;display:block;}
	.error_input_back{background:red}

	.disigner_div_default{margin-top:13px;padding:5px;cursor:pointer;}
	.disigner_div_checked{border:1px solid #9FB2C7;}
	.disigner_div_move{border:1px solid #A94B3B;}
	.div_default{clear:both;height:25px}

	.textarea_defaule{width:98%;height:100px;overflow:auto}

	#batch_item_common_div li{LIST-STYLE-TYPE: none; FLOAT: left;cursor:pointer;width:40px;text-align:center}

	/*设计区域外层DIV样式*/
	#design_div{width:98%;overflow:auto;}
	#design_div textarea{overflow:auto}
	/*题目选项区域外层DIV样式*/
	#des_items_divs{padding-left:24px;text-align:left;clear:both;}
	

	#s_name_show{text-align:center;width:100%;font-size:20px;font-weight:bold;margin-top:5px;color:#355286}
	#s_description_show{width:500px;text-indent:2em;text-align:left;margin-top:5px;line-height:20px;}
	#s_name_div{cursor:pointer;}
	.item_div_img{text-indent:2em;}
	/*选项标题外层div*/
	#title_divs{margin-top:13px;height:20px;text-align:left;font-weight:bold;HEIGHT: auto;line-height:20px}
	/*题目序号外层DIV样式*/
	.sort_num_div{WIDTH: 20px; FLOAT: left; FONT-WEIGHT: bold;}
	/*题目标题外层DIV样式*/
	#title_span{overflow: hidden;}


	#items_divs{text-align:left;clear:both;}
	.items_divs{text-align:left;height:20px;clear:both;}
	.imitation_a{text-underline-position: below; text-decoration: underline;cursor:pointer;}
	/*选项提示信息*/
	#description_div{text-align:left;text-indent:2em;margin-top:5px;clear:both;;line-height:20px}
	/*矩阵题td字符样式*/
	.m_td{font-weight:bold;height:25px}

	#tools_button_div{text-align:right;clear:both;width:99%;height:20px}
	 
	 /*单选按钮列表*/
	 .item_ul{width:90%;}
	 li{list-style-type:none;padding-top:3px;padding-bottom:3px;}
	.li_css1{float:left;width:90%}
	.li_css2{float:left;width:49%}
	.li_css3{float:left;width:31%}
	.li_css4{float:left;width:24%}
	.li_css5{float:left;width:19%}
	.li_css6{float:left;width:16%}
	.li_css7{float:left;width:14%}
	.li_css8{float:left;width:12%}
	.li_css9{float:left;width:11%}
	.li_css10{float:left;width:9%}

	/*选项中图片外层div*/
	#item_img_div{padding-bottom:5px;}
	/*选项中图片描述内容外层div*/
	#show_img_des_div{width:300px;position:absolute;background:#FFFFFF;padding:5px;border:1px solid #9FB2C7;text-indent:2em;line-height:20px;display:none;text-align:left}

	/*量表图样式*/
	.scale{margin-right:12px}
	.scale LI {LIST-STYLE-TYPE: none; FLOAT: left}
	.scale_li{WIDTH: 32px; BACKGROUND: url(../images/scale.gif) no-repeat; HEIGHT: 32px; CURSOR: pointer; TEXT-DECORATION: none;PADDING-LEFT: 13px;}
	.scale_li_selected{WIDTH: 32px; BACKGROUND: url(../images/scale_selected.gif) no-repeat; HEIGHT: 32px; CURSOR: pointer; TEXT-DECORATION: none;PADDING-LEFT: 3px}
	.scale_li_radio{padding-right:24px}

	/*投票题*/
	div.pro_back {background:transparent url(../images/pro_back.png) no-repeat scroll 0 0;float:left;height:14px;margin:3px 0 0 5px;padding:0 0 0 1px;width:150px;}
	div.pro_back .pro_fore{overflow:hidden;display: block;}
	
	a.button {cursor:pointer;background: transparent url('../images/bg_button_a.gif') no-repeat scroll top right;     color: #708BAA;     display: block;     float: left; font-size:12px;font-family:宋体;  height: 20px;  margin-right: 6px;     padding-right: 8px; /* sliding doors padding */     text-decoration: none; }  
	a.button span { background: transparent url('../images/bg_button_span.gif') no-repeat top left;     display: block;     line-height: 14px;     padding: 3px 0 3px 5px; font-size:12px;font-family:宋体;} 
	a.button:active { background-position: bottom right;     color: #708BAA;     outline: none; /* hide dotted outline in Firefox */ }  
	a.button:active span {     background-position: bottom left;     padding: 4px 0 2px 5px; /* push text down 1px */ } 
    #contents{border:none;}
	</style>
	<script language="javascript">
	<!--	   
		var site_id = "<%=site_id%>";
		var sd = null;
		var s_id = request.getParameter("s_id");
		var is_update = true;//是否允许修改,true表示该问卷还没有答卷，false为有答卷，已有的数据不能删除排序，不允许在选项中间进行插入操作
		var defaultBean;
		$(document).ready(function () {	
			
			initButtomStyle();
			init_FromTabsStyle();
			if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");

			sd = new sDesigner();
			sd.designerObj = $("#design_div");
			sd.wareObj = $("#ware_div");
			sd.showWareArea();	
			
			getSurveyCategoryName();//得到问卷分类名称

			if(s_id != "" && s_id != null)
			{
				defaultBean = SurveyRPC.getSurveyBean(s_id);
				var htmls = defaultBean.survey_content;				
				htmls = htmls.replace(/(ObjectPool\[)(.*?)(\])/ig,"$1"+sd.name+"$3");

				var answer_count = SurveyRPC.getAnswerListCount(s_id,"");
				if(answer_count != 0)
					is_update = false;

				$("#design_div").html(htmls);
				$("#survey").autoFill(defaultBean);	
				setUpdateStatusToHtml();
				setOnclickToDiv();

				sd.item_num = parseInt($("#max_item_num").val());
				sd.sort_num = parseInt($("#max_sort_num").val());
				
				$("#s_description").val($("#s_description_show").html());
				
				if(defaultBean.spacing_interval != "")
				{
					$("#spacing_type").val(defaultBean.spacing_interval.substring(0,1));
					$("#spacing_time").val(defaultBean.spacing_interval.substring(1))
				}
			}
		
			setContentAreaHeight();
			$("#addButton").click(saveSurvey);
		}); 
		function setContentAreaHeight()
		{						
			var div_height = parseInt(top.$("#leftMenu").css("height"));
			$("#content_area").css("height",div_height-12);				
			$("#design_div").css("height",div_height-20);	
			$("#attrs_div").css("height",div_height-51);
		}

		//修改时，原先设计的选项不允许修改，在此给选项加上修改状态
		function setUpdateStatusToHtml()
		{
			$("#design_div").find("input[type=radio]").attr("is_update",is_update);
			$("#design_div").find("input[type=checkbox]").attr("is_update",is_update);
			$("#design_div > div[type=scale] LI").attr("is_update",is_update);
			$("#design_div").find("option").attr("is_update",is_update);			
		}

		//给div动态加上点击事件，不知道什么原因造成，在设计点，执行this.current_designer_obj.click();事件后，该主题外层div的点击事件就消失了，所以在这里特殊处理一下
		function setOnclickToDiv()
		{
			$("#design_div > div").each(function(i){
				if(i > 0)
				{
					if($(this).attr("onclick") == null)
					{
						$(this).bind("click",function(){						
							ObjectPool[sd.name].setCurrentDesigner(this);
						});					
					}
					$(this).attr("is_update",is_update);
				}
			});
		}
			
		//打开预览问卷窗口
	    function previewSurvey()
		{
			$("#tools_button_div").remove();
			var openH = window.open("preview_survey.html");
		}
		//得到设计区域的html，提供给预览窗口用
		function getHtmls()
		{			
			return $("#content_area").html();
		}

	//-->
	</script>	
</head> 
<body> 
<table id="survey" class="table_form" border="0" cellpadding="0" cellspacing="0">
<input type="hidden" name="s_id" id="s_id">
	<tbody>
		<tr>
		  <td id="content_area" class="content_form_td" valign="top" align="center" >
		   <!-- 设计区域　开始 -->
		   <div id="design_div" class="design_div" >		   
		   
		   </div>
		   <!-- 设计区域　结束 -->
		  </td>		
		  <td width="266px" valign="top" align="right">			
			<!-- 组件区域　开始 -->
			<div id="ware_div" style="width:259px;text-align:left;" >		   
			   
			</div>
			<!-- 组件区域　结束 -->
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
			<input id="userAddReset" name="btn1" type="button" onclick="window.location.reload()" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="history.go(-1)" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
<!-- 编辑器区域 开始-->
<div id="htmleditor_dialog" title="HTML编辑器" style="width:700px;height:480px;display:none">
<div class="blankH9"></div>
 <table id="" border="0" cellpadding="0" cellspacing="0" width="96%" align="center" >
	<tr><td>
        <script id="contents" type="text/plain" style="height:320px"></script>
    </td></tr>
  <!-- <tr>
   <td height="25px" width="28px">			    
	<script type="text/javascript">	
		var sBasePath = "/cms.files/component/common/htmledit/";
	var oFCKeditor = new FCKeditor('contents') ;
	oFCKeditor.BasePath	= sBasePath ;
	oFCKeditor.Height	= 360 ;
	oFCKeditor.Width	= 670 ;
	oFCKeditor.Value = '' ;
	oFCKeditor.Create() ;
	
	</script>
   </td>
  </tr>
  <tr>
   <td><span class="blank3"></span>
	<input id="tool_btn" name="btn1" type="button" value="word格式清理" onclick="formatStyle('clearWordStyle','contents')" style="cursor:hand"/>
    <input id="tool_btn" name="btn1" type="button" value="清除样式" onclick="formatStyle('clearStyle','contents')" style="cursor:hand"/>
    <input id="tool_btn" name="btn1" type="button" value="去除空行" onclick="formatStyle('clearBlankRow','contents')" style="cursor:hand"/>
    <input id="tool_btn" name="btn1" type="button" value="首行缩进" onclick="formatStyle('firstRowIndent','contents')" style="cursor:hand"/>
    <input id="tool_btn" name="btn1" type="button" value="全角转半角" class="Button"	onclick="formatStyle('qjTObj','contents')" style="cursor:hand"/>
   </td>
  </tr> -->
 </table>
 <br />
 <table border="0" cellpadding="0" cellspacing="0" width="98%" class="content_lab_table" align="center">
  <tr>
   <td class="b_bottom_l"></td>
   <td class="b_bottom_c" align="center">	
	 <input id="HtmlButton" name="btn1" type="button" onclick="subHTMLEditor()" value="保存" />	
	 <input id="HtmlButton" name="btn1" type="button" onclick="closeHTMLEditor()" value="关闭" />		 
   </td>
  </tr>
 </table>	
</div>
<!-- 编辑器区域 结束-->

</body> 

</html> 

