<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>专题设计</title>



<link rel="stylesheet" type="text/css" href="/sys/js/jquery-easyui/style/easyui.css">
<link rel="stylesheet" type="text/css" href="/cms.files/design/styles/default.css" />
<link type="text/css" rel="stylesheet" href="/cms.files/styles/group.css" />
<link rel="stylesheet" type="text/css" href="/sys/styles/design.css">
<link rel="stylesheet" type="text/css" href="/sys/styles/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="/sys/styles/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/sys/js/jquery-ui/themes/base/jquery.ui.all.css"  />

<link rel="stylesheet" type="text/css" href="/styles/news.skin/news.css" />

<script type="text/javascript" src="/sys/js/jquery.js"></script>
<script type="text/javascript" src="/sys/js/tools.js"></script>
<script type="text/javascript" src="/sys/js/sysUI.js"></script>
<script type="text/javascript" src="/sys/js/java.js"></script>
<script type="text/javascript" src="/sys/js/extend.js"></script>
<script type="text/javascript" src="/sys/js/jsonrpc.js"></script>
<script type="text/javascript" src="/sys/js/jquery.c.js"></script>
<script type="text/javascript" src="/sys/js/common.js"></script>
<script type="text/javascript" src="/sys/js/indexjs/indexList.js"></script>
<script type="text/javascript" src="/sys/js/indexjs/tools.js"></script>
<script type="text/javascript" src="/sys/js/lang/zh-cn.js"></script>
<script type="text/javascript" src="/sys/js/jquery-plugin/colResizable-1.3.min.js"></script>
<script type="text/javascript" src="/sys/js/jTimers.js"></script>
<script type="text/javascript" src="/sys/js/jquery-plugin/jquery.cookie.js"></script>
<script type="text/javascript" src="/sys/js/jquery-ui/jquery.effects.core.js"></script>
<script type="text/javascript" src="/sys/js/jquery-ui/jquery.effects.blind.js"></script>
<script type="text/javascript" src="/sys/js/jquery-easyui/jquery.easyui.min.js"></script> 
<script type="text/javascript" src="/sys/js/jquery-ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="/sys/js/jquery-ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="/sys/js/jquery-ui/jquery.ui.mouse.js"></script>
<script type="text/javascript" src="/sys/js/jquery-ui/jquery.ui.button.js"></script>
<script type="text/javascript" src="/sys/js/jquery-ui/jquery.ui.draggable.js"></script>
<script type="text/javascript" src="/sys/js/jquery-ui/jquery.ui.position.js"></script>
<script type="text/javascript" src="/sys/js/jquery-ui/jquery.ui.dialog.js"></script>
<script type="text/javascript" src="/sys/js/jquery-ui/jquery.ui.sortable.js"></script>
<script type="text/javascript" src="js/design_util.js"></script>
<!-- 这个css为风格的css,会动态替换此路径 -->
<link rel="stylesheet" id="css_link" type="text/css" href="">
	

<style type="text/css">
	*{
		border: 0;
		margin: 0;
		padding: 0;
	}

	BODY{background:#f7f7f7}

	.design_head{width:100%;height:42px;padding-top:5px;background-image:url('/sys/images/desing_bak.jpg');background-repeat:repeat}
	
	li{list-style-type:none;list-style:none;}
	#msgAlert .alert_img{background:url('/sys/images/alert.png') no-repeat center center;}
	#msgAlert .error_img{background:url('/sys/images/error.png') no-repeat center center;}
	#msgAlert .wargin_img{background:url('/sys/images/wargin.png') no-repeat center center;}
	#msgConfirm .back_img{background:url('/sys/images/confirm.png') no-repeat center center;}
	.msg_left_div{float:left;width:115px;height:139px;}
	.msg_right_div{float:left;width:200px;overflow:hidden;color:#31619C}

	.column {}
	.layout_td{height:40px;border:1px dashed #000000;border-collapse:collapse;empty-cells:show;}
	.portlet {width:100%;cursor:move;}
	.portlel_move{border: 1px solid red;}
	
	.ui-sortable-placeholder { border: 1px dotted red; visibility: visible !important; height: 50px !important; }
	
	.module_top_button{
		position:absolute;
		width:32px;
		height:32px;
		background:#ccc;		
		cursor:pointer;
		display:none;
		z-index:999
	}
	.layout_top_button{
		position:absolute;
		width:32px;
		height:32px;
		background:#ccc;
		cursor:pointer;
		display:none;
		z-index:1000
	}
	.temp_message_div{
		width:100%;
		height:100%;
		text-align:center;
		font-size:20px;
		font-weight:bold;
		cursor:pointer;
		color:#32609E;
		padding-top:3px
	}

	a.save_btn{width:85px;height:26px; display:block; background:url('/sys/images/design_btn.jpg') right top no-repeat; border:0}
	a.save_btn:hover{background:url('/sys/images/design_btn.jpg') right bottom no-repeat;}

	a.cancel_btn{width:85px;height:26px; display:block; background:url('/sys/images/design_btn.jpg') left top no-repeat; border:0}
	a.cancel_btn:hover{background:url('/sys/images/design_btn.jpg') left bottom no-repeat;}
	</style>
	<script type="text/javascript">
	var DesignRPC = jsonrpc.DesignRPC;
	var TemplateRPC = jsonrpc.TemplateRPC;
	var CategoryRPC = jsonrpc.CategoryRPC;
	var des_catBean;
	
	var is_update = false;//是否是新增还是修改
	var site_id="${param.site_id}";
	var app_id="${param.app_id}";
	var cat_id="${param.cat_id}";
	var c_name=decodeURI(request.getParameter("c_name"));	
	var page_type="${param.p_type}";
	var create_type = "${param.create_type}";
	var value_id = "${param.v_id}";
	var default_css_id;//所关联主题风格ID
	
	var template_cat_id = 1;//默认模板分类ID 1

	var message = '<div class="temp_message_div">点击添加资源</div>';
	var portlet_str = '<div class="portlet"></div>';
	var current_layout;//当前布局
	var currnet_layout_td;//当前布局td区域
	var currnet_module;//当前资源
	var module_map = new Map();//用于存储模块对象，便在保存的时候进行代码替换

	$(document).ready(function(){	
		init_input();
		initButtomStyle();
		//获取已有的数据
		getDesignCategoryBean();

		//得到外框的高度
		$("#width").val($("#container").width());
		
		//加载布局按钮点击事件
		loadLayoutButtonClick();
		//加载模块按钮点击事件
		loadModuleButtonClick();			
		//加载拖拽事件
		loadSortableEcent();
		//加载资源的初始鼠标事件
		loadSoucsMove();
		//加载布局的初始鼠标事件
		loadLayoutMove();
		//加载布局块鼠标事件
		loadLayTDMove();				
	});

	//根据栏目ID，站点ID，页面类型得到设计源码
	function getDesignCategoryBean()
	{
		des_catBean = DesignRPC.getDesignCategoryBean(cat_id,site_id,page_type);
	
		if(des_catBean != null)
		{
			is_update = true;
			insertCode(des_catBean.design_content);
			default_css_id = des_catBean.css_id;
			loadCssObject(default_css_id);
		}else
		{
			//根据创建类型取出样式数据
			switch(create_type)
			{
				case "0":loadCssObject(value_id);break;
				case "1":loadCategoryObject();break
				case "2":loadCaseObject();break
			}
		}
	}

	//把源代码加载到设计容器中
	function insertCode(code_str)
	{
		$("#container_parent").html(code_str);
		//将存储在container中的背景图加载到body对象中，并清空
		var body_backgound_img = $("#container").css("background-image");
		if(body_backgound_img != "" && body_backgound_img != null && body_backgound_img.substring(body_backgound_img.length-4,body_backgound_img.length-2) != "ne")
		{
			//body_backgound_img = body_backgound_img.substring(5,body_backgound_img.length-2);
			var repeat_val = $("#container").css("background-repeat");
			var positionY_val = $("#container").css("background-positionY");
			var positionX_val = $("#container").css("background-positionX");
			$("body").css("background-image",body_backgound_img).css("background-repeat",repeat_val).css("background-positionY",positionY_val).css("background-positionX",positionX_val);
			$("#container").css("background-image","url('')").css("background-repeat","").css("background-positionY","").css("background-positionX","");
		}
	}

	//加载风格样式
	function loadCssObject(css_id)
	{
		default_css_id = css_id;
		var css_obj = DesignRPC.getDesignCssBean(css_id);		
		if(css_obj != null)
		{
			$("#css_link").attr("href","/cms.files/design/theme/"+css_obj.css_ename+"/theme.css")
		}
	}

	//加载方案套餐
	function loadCaseObject()
	{
		var case_obj = DesignRPC.getDesignCaseBean(value_id);
		insertCode(case_obj.case_content);		
		loadCssObject(case_obj.css_id);
	}

	//类似创建
	function loadCategoryObject()
	{
		var cat_design_obj = DesignRPC.getDesignCategoryBean(value_id,site_id,page_type);
		if(cat_design_obj != null)
		{
			insertCode(cat_design_obj.design_content);		
			loadCssObject(cat_design_obj.css_id);
		}
	}


	//加载布局按钮点击事件
	function loadLayoutButtonClick()
	{
		$('.layout_top_button').click(function(e){
			$('#layout_menu').menu('show',{
				left: e.pageX,
				top: e.pageY
			});
		});
	}
	
	//加载模块按钮点击事件
	function loadModuleButtonClick()
	{
		$('.module_top_button').click(function(e){
			$('#soucs_menu').menu('show',{
				left: e.pageX,
				top: e.pageY
			});
		});
	}

	//加载拖拽事件
	function loadSortableEcent()
	{
		$(".column").sortable({
			connectWith: '.column'
		});
		$(".portlet").addClass("ui-widget ui-widget-content ui-helper-clearfix ui-corner-all");		
	}

	//加载资源的初始鼠标事件
	function loadSoucsMove()
	{		
		$('.portlet').live('mouseover', function(event) {			
			if($(this).find(".temp_message_div").length == 0)
			{				
				var position = $(this).position();
				$(".module_top_button").css("left",position.left+$(this).width()-$(".module_top_button").width()).css("top",position.top+3).show();	
			}
			currnet_module = $(this);
			$('.portlet').removeClass("portlel_move");
			$(this).addClass("portlel_move");
		});
	}

	
	//加载布局的初始鼠标事件
	function loadLayoutMove()
	{
		$('.layout_table').live('mouseover', function(event) {
			current_layout = $(this);
			var position = $(this).position();
			$(".layout_top_button").css("left",position.left+3).css("top",position.top+3).show();
		});
		loadColResiz();
				
	}

	//加载表格拖放宽度
	function loadColResiz()
	{
		$(".layout_table").colResizable({
			liveDrag:true,		
			draggingClass:"dragging"});
	}

	//加载布局块鼠标事件，用于判断如果布局中为空的话，显示添加资源按钮
	function loadLayTDMove()
	{		
		$('.temp_message_div').live('click', function(event) {		
			currnet_module = $(this).parent();
			insertModule("insert");					 
		});
	}

	//布局上移下移
	function moveLayout(flag)
	{
		switch(flag)
		{
			case "top":$("#container table").eq(0).prepend(current_layout);
					   return;
			case "prev":current_layout.prev().before(current_layout);
					   return;
			case "next":current_layout.next().after(current_layout);
					   return;
			case "bottom":$("#container").append(current_layout);
					   return;

		}
	}

	//打开模块选择窗口
	function insertModule(action_type)
	{
		OpenModalWindow("选择模块资源","module_add.jsp?site_id="+site_id+"&a_t="+action_type,760,570);
	}

	//插入模块事件
	function insertModuleEvent(moduleBean,frameBean,sel_style_id,sel_class_name,datasouce_str,temp_cat_name,action_type,attrList)
	{		
		switch(action_type)
		{
			case "insert" : currnet_module.empty();
							insertModuleHandl(moduleBean,frameBean,sel_style_id,sel_class_name,datasouce_str,temp_cat_name,currnet_module,attrList);
							break;
			case "after":currnet_module.after(portlet_str);
						 currnet_module.next().addClass("ui-widget ui-widget-content ui-helper-clearfix ui-corner-all");
						 insertModuleHandl(moduleBean,frameBean,sel_style_id,sel_class_name,datasouce_str,temp_cat_name,currnet_module.next(),attrList);
						 break;
			case "before":currnet_module.before(portlet_str);
						 currnet_module.prev().addClass("ui-widget ui-widget-content ui-helper-clearfix ui-corner-all");
						 insertModuleHandl(moduleBean,frameBean,sel_style_id,sel_class_name,datasouce_str,temp_cat_name,currnet_module.prev(),attrList);
						 break;
		}		
	}
	
	//插入模块操作实现
	function insertModuleHandl(moduleBean,frameBean,sel_style_id,sel_class_name,datasouce_str,temp_cat_name,currentObj,attrList)
	{
		module_map.put(moduleBean.module_id,moduleBean);

		currentObj.append(frameBean.frame_content);//加入框架代码
		currentObj.find(" > div").attr("frame_id",frameBean.frame_id);//加入框架ID标识
		
		var new_body = currentObj.find(".module_body");
		new_body.html(moduleBean.module_content);//加入模板代码
		var content_parent_div = new_body.find(" > div");
		content_parent_div.attr("module_id",moduleBean.module_id);//加入模块ID标识
		if(moduleBean.datasouce_type == 0)
		{
			//如果是列表类型，将数据源id写到属性中
			new_body.find(" > div").attr("cat_ids",datasouce_str);
			var ahref = "/info/iList.jsp?cat_id="+datasouce_str;//链接地址
			currentObj.find("h2 a").attr("href",ahref);
			currentObj.find(".more a").attr("href",ahref);
			if(temp_cat_name != "" && temp_cat_name != null)
			{
				currentObj.find("h2 a").text(temp_cat_name);
			}
		}else
		{
			//文类类型，将值写入到 module_body 的下层div中
			new_body.find(" > div").append(datasouce_str);
		}

		//判断内容样式是否为空
		if(sel_style_id != "" && sel_style_id != null)
		{
			if(sel_class_name.indexOf("li_icon_") == 0)
			{
				new_body.find("li").addClass(sel_class_name);
			}else
				new_body.find(" > div").addClass(sel_class_name);//加入内容样式
			new_body.find(" > div").attr("style_id",sel_style_id);//加入内容样式ID标识

			
		}
		
		//根据属性处理各项设置
		if(attrList != null && attrList.size() > 0)
		{
			var attr_str = "";
			for(var i=0;i<attrList.size();i++)
			{
				if(i > 0)
					attr_str += ",";
				attr_str += attrList.get(i).ename+"*"+attrList.get(i).value;
				
				attrList.get(i).action(currentObj);
				content_parent_div.attr("attr_str",attr_str);
			}			
		}
	}

	//修改模板资源
	function updateModuleEvent(frameBean,sel_style_id,sel_class_name,old_class_name,datasouce_type,datasouce_str,temp_cat_name,attrList)
	{
		if(frameBean != null)
		{
			var old_soucs = currnet_module.find(".module_body").children();
			currnet_module.empty();
			currnet_module.append(frameBean.frame_content);
			currnet_module.find(" > div").attr("frame_id",frameBean.frame_id);//加入框架ID标识
			
			currnet_module.find(".module_body").append(old_soucs);			
		}
		
		if(sel_style_id != "" && sel_style_id != null)
		{
			var new_body = currnet_module.find(".module_body");
			if(old_class_name != "" && old_class_name != null)
			{
				new_body.children().removeClass(old_class_name);
				new_body.find("li").removeClass(old_class_name);
			}
			
			if(sel_class_name.indexOf("li_icon_") == 0)
			{
				new_body.find("li").addClass(sel_class_name);
			}else
				new_body.find(" > div").addClass(sel_class_name);//加入内容样式
			new_body.find(" > div").attr("style_id",sel_style_id);//加入内容样式ID标识
		}

		if(datasouce_type == 0)
		{
			//如果是列表类型，将数据源id写到属性中
			currnet_module.find(".module_body > div").attr("cat_ids",datasouce_str);
			var ahref = "/info/iList.jsp?cat_id="+datasouce_str;//链接地址
			currnet_module.find("h2 a").attr("href",ahref);
			currnet_module.find(".more a").attr("href",ahref);	
			
			if(temp_cat_name != "" && temp_cat_name != null)
			{
				currnet_module.find("h2 a").text(temp_cat_name);
			}
		}else
		{
			//文类类型，将值写入到 module_body 的下层div中
			currnet_module.find(".module_body > div").html(datasouce_str);			
		}

		//根据属性处理各项设置
		if(attrList != null && attrList.size() > 0)
		{
			var attr_str = "";
			var co = currnet_module.find(".module_body > div");
			for(var i=0;i<attrList.size();i++)
			{
				if(i > 0)
					attr_str += ",";
				attr_str += attrList.get(i).ename+"*"+attrList.get(i).value;
				attrList.get(i).action(currnet_module);
				co.attr("attr_str",attr_str);
			}
			
		}
	}	

	//打开布局选择窗口
	function insertLayout(position)
	{			
		OpenModalWindow("选择布局","layout_select.jsp?position="+position,750,550);
	}

	//插入布局
	function insertLayoutHandl(layout_code,position)
	{		
		switch(position)
		{
			case "after":current_layout.after(layout_code);
					     current_layout.next().find(".portlet").addClass("ui-widget ui-widget-content ui-helper-clearfix ui-corner-all").append(message);

					   break;
			case "before":current_layout.before(layout_code);
						 current_layout.prev().find(".portlet").addClass("ui-widget ui-widget-content ui-helper-clearfix ui-corner-all").append(message);
					   break;
		}		
		loadSortableEcent();

		loadColResiz();
	}

	//删除布局
	function deleteLayout()
	{
		current_layout.remove();
	}

	//删除资源
	function deleteModule()
	{
		currnet_module.empty();
		
		if(currnet_module.parent().children().length == 1)
		{//如果只有一个资源的话，加上默认消息，否则直接删除了
			currnet_module.append(message);
			$(".module_top_button").hide();
		}
	}

	//设置主体框架宽度
	function setContainerWidth(val)
	{
		if(val.match(/[0-9]{1,4}/) != null)
		{
			$("#container").css("width",val);
		}		
	}

	//上传背景图
	function setBacgground(url,repeat_val,positionX_val,positionY_val,action_type)
	{
		var new_body;
		if(action_type == "layout")
		{
			new_body = currnet_module.find(".module_body");			
		}
		else
		{
			new_body = $("body");
		}
		new_body.css("background-image","url('"+url+"')");
		new_body.css("background-repeat",repeat_val);
		new_body.css("background-positionY",positionY_val);
		new_body.css("background-positionX",positionX_val);
	}

	//打开上传背景图窗口
	function openUploadBGImg(action_type)
	{
		OpenModalWindow("上传背景图","upload_bgImg.jsp?site_id="+site_id+"&action_type="+action_type,750,414);
	}

	//从map中得到module对象
	function getModuelBeanForMap(m_id)
	{
		if(module_map.containsKey(m_id))
		{
			return module_map.get(m_id);
		}else
		{
			var mo = DesignRPC.getDesignModuleBean(m_id);
			module_map.put(m_id,mo);
			return mo;
		}
	}

	//保存代码
	function saveCode()
	{		
		//清除空余的布局标签 <DIV class="portlet ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" sizcache="5" sizset="0"></DIV>
		$(".portlet").each(function(){
			if($(this).children().length == 0)
				$(this).remove();
		});
		//第一步：拷贝代码到临时div中
		$("#temp_container_parent").html($("#container_parent").html());
		//第二步：去掉默认的资源　temp_message_div
		$("#temp_container_parent .temp_message_div").remove();
		
		//删除拖拽产生的废代码
		$("#temp_container_parent .CRC").remove();

		//第三步：替换代码		
		$("#temp_container_parent .module_body > div").each(function(){			
			var module_id = $(this).attr("module_id");	
			var random_num = $(this).attr("random_num");
			var v_code = getModuelBeanForMap(module_id).v_code;
			
			if(module_map.get(module_id).datasouce_type == 0)
			{//列表类型的需要替换代码,首页的要替换，列表页不替换
				if($(this).attr("cat_ids") != "" && $(this).attr("cat_ids") != null)
				{
					v_code = v_code.replace("$cat_id",$(this).attr("cat_ids"));
				}
				if(page_type == "list")
				{
					var h2_obj = $(this).parent().parent().find("h2 a");
					if(h2_obj.text() == "标题文字")
					{
						h2_obj.text("$cco.cat_cname");
						h2_obj.attr("href","/info/iList.jsp?cat_id=$!cat_id");
						$(this).parent().parent().find(".more").remove();
					}		
				}
				
				//替换参数,根据随机码从得到map中得到设置过的属性对象
				var attr_str = $(this).attr("attr_str");				
				if(attr_str != "" && attr_str != null)
				{
					var tempA = attr_str.split(",");
					for(var i=0;i<tempA.length;i++)
					{
						var ename = tempA[i].substring(0,tempA[i].indexOf("*"));
						var value = tempA[i].substring(tempA[i].indexOf("*")+1);						
						var o = eval("new "+ename+"()");						
						v_code = o.replaceStr(v_code,value,$(this));
					}
				}
				$(this).removeAttr("attr_str");	
				$(this).empty();
				$(this).html(v_code);

				//加入内容样式
				var style_id = $(this).attr("style_id");
				if(style_id != "" && style_id != null)
				{
					var style_name = DesignRPC.getDesignStyleBean(style_id).class_name;
					if(style_name.indexOf("li_icon_") == 0)
					{
						$(this).find("li").addClass(style_name);
					}else
						$(this).find(" > div").addClass(style_name);
				}

				$(this).parent().parent().unwrap();//去掉所有的　portlet　拖拽层
			}
		});
		//最后一步：将body里的背景图片放到　设计区域的　container 中
		var body_str = "";

		var body_backgound_img = $("body").css("background-image");
		var body_backgound_repeat = "";
		var body_backgound_positionY = "";
		var body_backgound_positionX = "";
		if(body_backgound_img != "" && body_backgound_img != null && body_backgound_img != "none")
		{			
			//body_backgound_img = body_backgound_img.substring(5,body_backgound_img.length-2);
			body_backgound_img = body_backgound_img.replace(/\"/ig,"'");
			body_backgound_img = "url('"+body_backgound_img.substring(body_backgound_img.indexOf("/images"));
			body_backgound_repeat = $("body").css("background-repeat");
			body_backgound_positionY = $("body").css("background-positionY");
			body_backgound_positionX = $("body").css("background-positionX");

			$("#container_parent #container").css("background-image",body_backgound_img).css("background-repeat",body_backgound_repeat).css("background-positionY",body_backgound_positionY).css("background-positionX",body_backgound_positionX);

			body_str = '<body style="background-image:'+body_backgound_img+';background-repeat:'+body_backgound_repeat+';background-positionY:'+body_backgound_positionY+';background-positionX:'+body_backgound_positionX+';">';
			
		}
		$("#temp_container_parent #container").css("background-image","").css("background-repeat","").css("background-positionY","").css("background-positionX","");//清除模板代码container中的背景图样式
		$("#temp_container_parent #container").addClass("mainBox");
		//$("#source_code").val($("#container_parent").html());
		//$("#code").val($("#temp_container_parent").html());
	
		//模板Bean
		var template_bean = BeanUtil.getCopy(new Bean("com.deya.wcm.bean.system.template.TemplateEditBean",true));
		template_bean.tcat_id = template_cat_id;
		template_bean.t_content = getCodeHandl(body_str,$("#temp_container_parent").html().replace(/\&lt;/ig,"<").replace(/\&gt;/ig,">"));
		template_bean.t_ename = "vm";
		template_bean.t_cname = c_name+"_"+page_type;
		template_bean.app_id = app_id;
		template_bean.site_id = site_id;
		template_bean.t_path = 0;

		if(is_update)
		{
			template_bean.id = des_catBean.template_id;
			template_bean.t_id = des_catBean.template_id;
		}
		else
		{
			//新增的话需要新ID
			template_bean.id = TemplateRPC.getNewTemplate();
			template_bean.t_id = template_bean.id;
		}
		//页面设计Bean
		var designCategoryBean = BeanUtil.getCopy(new Bean("com.deya.wcm.bean.system.design.DesignCategoryBean",true));
		designCategoryBean.cat_id = cat_id;
		designCategoryBean.template_id = template_bean.id;
		designCategoryBean.design_content = $("#container_parent").html();
		designCategoryBean.page_type = page_type;
		designCategoryBean.app_id = app_id;
		designCategoryBean.site_id = site_id;
		designCategoryBean.css_id = default_css_id;
		//栏目与模板关联
		var m = new Map();
		m.put("site_id",site_id);
		m.put("cat_id",cat_id);
		m.put("template_id",template_bean.id+"");
		if(page_type == "index")
			m.put("template_type","template_index");
		if(page_type == "list")
			m.put("template_type","template_list");
		if(page_type == "content")
			m.put("template_type","template_content");
		if(is_update)
		{
			if(TemplateRPC.addTemplateEditForDesign(template_bean,is_update)　&& DesignRPC.updateDesignCategory(designCategoryBean))
			{			
				msgAlert("页面"+WCMLang.Add_success);				
			}else
				msgWargin("页面"+WCMLang.Add_fail);		

		}else
		{
			if(TemplateRPC.addTemplateEditForDesign(template_bean,is_update) && DesignRPC.insertDesignCategory(designCategoryBean) && CategoryRPC.updateCategoryTemplate(m))
			{				
				msgAlert("页面"+WCMLang.Add_success);
				is_update = true;				
			}else
			{
				msgWargin("页面"+WCMLang.Add_fail);
			}
		}
	}

	function getCodeHandl(body_str,code_str)
	{
		var html_str = '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">';		
		html_str += '<html xmlns="http://www.w3.org/1999/xhtml">\n';
		html_str += '<head>\n';
		html_str += '<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />\n';
		html_str += '<link type="text/css" rel="stylesheet" href="/cms.files/styles/group.css" />\n';
		html_str += '<link rel="stylesheet" type="text/css" href="'+$("#css_link").attr("href")+'">\n';
		html_str += '<link rel="stylesheet" type="text/css" href="/cms.files/design/styles/default.css" />\n';
		
		if(page_type == "content")
		{
			html_str += '<link rel="stylesheet" type="text/css" href="/styles/news.skin/news.css" />';
			html_str += '<title>$!InfoData.title</title>';
			html_str += '<meta name="keywords" content="$!InfoData.info_keywords" />';
			html_str += '<meta name="description" content="$!InfoData.info_description" />';
		}else
		{
			html_str += '#set ($cco=$InfoUtilData.getCategoryObject("$cat_id","$site_id","$node_id"))';
			html_str += '<title>$cco.cat_cname</title>';
			html_str += '<meta name="keywords" content="$cco.cat_keywords" />';
			html_str += '<meta name="description" content="$cco.cat_description" />';
		}
		html_str += '<script type="text/javascript" src="/cms.files/js/jquery.js"><'+'/script>\n';
		html_str += '<script type="text/javascript" src="/js/common.js"><'+'/script>\n';
		html_str += '</head>\n';
		if(body_str != "" && body_str != null)
		{
			html_str += body_str;
		}else
			html_str += '<body>\n';

		html_str += code_str;
		html_str += '</body>\n</html>';

		return html_str;

	}

	//打开切换主题风格窗口
	function openChangeCSS()
	{
		OpenModalWindow("选择主题风格","select_css.jsp?css_id="+default_css_id,750,510);
	}

	
	</script>
</head>
<body>
<!-- 设计区域头部 开始 -->
<div class="design_head">
	<div class="left" style="padding-left:200px;padding-top:10px"><strong>页面宽度：</strong><input type="text" id="width" name="width" style="width:50px" onblur="setContainerWidth(this.value)"/>像素(px)</div>
	<div class="left" class="width50" style="padding-left:10px;padding-top:10px"><input id="addButton" name="btn1" type="button" onclick="openUploadBGImg('body')" value="背景图设置" /></div>
	<div class="left" class="width50" style="padding-left:10px;padding-top:10px"><input id="addButton" name="btn1" type="button" onclick="openChangeCSS()" value="切换主题风格" /></div>
	<div class="left" style="float:right;padding-top:7px;padding-right:100px;text-align:right" >
		<div style="width:85px;height:26px;float:right"><a class="save_btn" href="javascript:saveCode()"></a></div>
		<div style="width:12px;float:right">&nbsp;</div>
		<div style="width:85px;height:26px;float:right;margin-left:12px"><a class="cancel_btn" href="javascript:window.close()"></a></div>
	</div>
</div>
<!-- 设计区域头部 结束 -->

<div class="layout_top_button"><img src="/sys/images/layout_edit.jpg" width="32" height="32" alt="" /></div>
<div class="module_top_button"><img src="/sys/images/module_edit.jpg" width="32" height="32" alt="" /></div>

<!-- 页面设计区域　开始 -->
<div id="container_parent">
<div id="container" style="width:990px;margin:0 auto" class="mainBox">
	<table width="100%" border="0" cellpadding="0" cellspacing="" class="layout_table">
		<tr>
			<td valign="top" class="column layout_td">
				<div class="portlet">
					<div class="temp_message_div">点击添加资源</div>
				</div>
			</td>
		</tr>
	</table>	
</div>
</div>
<!-- 页面设计区域　结束 -->
<div id="temp_container_parent" class="hidden">
	
</div>

<!-- 布局操作按钮　开始 -->
<div id="layout_menu" class="easyui-menu" style="width:120px;">
		<div onclick="javascript:moveLayout('top')">居顶</div>
		<div onclick="javascript:moveLayout('prev')">上移</div>
		<div onclick="javascript:moveLayout('next')">下移</div>
		<div onclick="javascript:moveLayout('bottom')">居底</div>
		<div class="menu-sep"></div>
		<div onclick="javascript:insertLayout('before')">前面插入布局</div>
		<div onclick="javascript:insertLayout('after')">后面插入布局</div>
		<div class="menu-sep"></div>
		<div onclick="javascript:deleteLayout()">删除</div>
		<div class="menu-sep"></div>
		<div>关闭</div>
	</div>
<!-- 布局操作按钮　结束 -->
<!-- 资源操作按钮　开始 -->
<div id="soucs_menu" class="easyui-menu" style="width:120px;">
		<div onclick="javascript:insertModule('update')">修改模块</div>
		<div class="menu-sep"></div>
		<div onclick="javascript:insertModule('before')">前面插入模块</div>
		<div onclick="javascript:insertModule('after')">后面插入模块</div>
		<div class="menu-sep"></div>
		<div onclick="javascript:openUploadBGImg('layout')">设置背景图</div>
		<div class="menu-sep"></div>
		<div onclick="javascript:deleteModule()">删除</div>
		<div class="menu-sep"></div>
		<div>关闭</div>
	</div>
<!-- 资源操作按钮　结束 -->


<!-- 模态窗口设计区开始 -->
<div id="sysDialog" name="sysDialog" class="hidden" title="My Window">
	<iframe id="sysDialog_Frame" name="sysDialog_Frame" src="/sys/spaceIframe.html" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
</div>
 
<div id="msgAlert" name="msgAlert" class="hidden" title="提示信息">	
	<div class="msg_left_div back_img"></div>
	<div class="msg_right_div">
		<table border="0" cellpadding="0" cellspacing="0" align="left" height="139px">
			<tr><td valign="middle" align="left" class="lineHeight20px"></td></tr>
		</table>	 
	</div>
	<span class="blank3"></span>
	<div class="line2h"></div>
	<span class="blank3"></span>
	<div class="textCenter">
		<input id="i005" name="i005" type="button" hidefocus="true" value="　确定　" />
	</div>
	<span class="blank3"></span>
</div>
 
<div id="msgConfirm" name="msgConfirm" class="hidden" title="提示信息">	
	<div class="msg_left_div back_img"></div>
	<div class="msg_right_div">
		<table border="0" cellpadding="0" cellspacing="0" align="left" height="139px">
			<tr><td valign="middle" align="left" class="lineHeight20px"></td></tr>
		</table>	 
	</div>		
	<span class="blank3"></span>
	<div class="line2h"></div>
	<span class="blank3"></span>
	<div class="textCenter">
		<input id="i005" name="i005" type="button" hidefocus="true" value="　是　" />
		<input id="i005" name="i005" type="button"  hidefocus="true" value="　否　" />
	</div>
	<span class="blank3"></span>
</div>
<!-- 模态窗口设计区结束 -->


<textarea style="width:100%;height:300px" id="source_code" class="hidden"></textarea>
<textarea style="width:100%;height:300px" id="code" class="hidden"></textarea>
</body>
</html>
