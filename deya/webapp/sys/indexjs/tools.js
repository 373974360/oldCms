//首页框架所用到的功能js包括菜单，弹出窗口等js

//初始化顶级功能菜单
function initTopMenu()
{	
	$.each(jsonData, function(i,item){
			var li = creatTopMenuLi(item);
			$(li).attr("level","0");
			//防止事件冒泡，只在最外层bind事件即可
			$(li).bind('click', function(event) {
				if($(this).data("attributes").handls != "" && $(this).data("attributes").handls != null)
				{
					eval($(this).data("attributes").handls);
				}else
				{
					for(var i=0;i<jsonData.length;i++)
					{
						if(jsonData[i].id == $(this).attr("id"))
						{
							child_jData = jsonData[i].children;
							iniMenu();
							iniMenuOnMouseOver();
						}
					}
				}
			});
			$("#top_menu > li > ul").append(li);
		});
}

//初始化一、二级横向菜单
var isChangeTreeStatus = false;//判断是否改变了左侧的树
function iniMenu()
{
		$("#menu").empty();
		try{
			$.each(child_jData, function(i,item){
				var li = creatMenuLi(item);
				
				$(li).attr("level","0");
				//防止事件冒泡，只在最外层bind事件即可
				$(li).bind('click', function(event) {
					
					var selectLi = null;					
					if($(event.target).attr("level")=="1")
					{
						selectLi = $(event.target).parent().parent();
						treeNodeSelected($(event.target).attr("id"));
					}
					else
					{
						selectLi = $(event.target);	
					}
					
					if($(selectLi).data("children") != undefined)
					{						
						if(curTreeID != $(selectLi).attr("id") || isChangeTreeStatus == true)
						{
							curTreeID = $(selectLi).attr("id");
							setLeftMenuTreeJsonData($(selectLi).data("children"));	
							isChangeTreeStatus = false;
						}
					}
					
					$("#menu>li").removeClass("focused");
					$(selectLi).addClass("focused");

					if($(event.target).data("attributes").handls != "" && $(event.target).data("attributes").handls != null)
					{
						eval($(event.target).data("attributes").handls);
					}						
					setMainIframeUrl($(event.target).data("attributes").url,$(event.target).data("title"));						
					
					curTreeID = $(selectLi).attr("id");
					
				});
				//默认选中项				
				if(i==0)
				{
					$(li).click();
				}
				if(item.children != undefined && item.children.length>0)
				{
					var subul = document.createElement("ul")
					$.each(item.children, function(i,subitem){
						var subli = creatMenuLi(subitem);
						$(subli).attr("level","1");
						$(subul).append(subli);		  
					});
					$(li).append(subul);	
				}
				$("#menu").append(li);
			});
		}catch(e){};
		
}

//创建横向菜单Li对像，并绑定子节点json数据
function creatTopMenuLi(jsonData){
	try{
		var li = document.createElement("li")
		$(li).attr("id",jsonData.id);
		$(li).text(jsonData.text);
		$(li).data("title",jsonData.text);
		$(li).data("attributes",jsonData.attributes);		
		return $(li);
	}catch(e){};
}

//创建横向菜单Li对像，并绑定子节点json数据
function creatMenuLi(jsonData){
	var li = document.createElement("li")
	$(li).attr("id",jsonData.id);
	//$(li).html(jsonData.text);
	$(li).text(jsonData.text);
	$(li).data("title",jsonData.text);
	$(li).data("attributes",jsonData.attributes);
	if(jsonData.children != undefined && jsonData.children.length>0)
	{
		$(li).data("children",jsonData.children);
	}
	
	return $(li);
}

//左边栏目树对像赋值json格式数据
function setLeftMenuTreeJsonData(jsonData)
{
	isChangeTreeStatus = true;
	if(jsonData != undefined && jsonData.length>0)
	{
		$('#leftMenuTree').empty();
		$('#leftMenuTree').tree('append',{
			parent: (null),
			data:jsonData
		});	
	}
}

//默认选中左侧树某节点
function treeNodeSelected(nodid)
{
	$("div[node-id]").removeClass("tree-node-selected");	
	$("div[node-id='"+nodid+"']").addClass("tree-node-selected");	
	//$('#leftMenuTree').tree('remove', $('#leftMenuTree').tree('select',1));
}

//插入树节点
function insertTreeNode(nodeData)
{	
	var node = $('#leftMenuTree').tree('getSelected');   
	$('#leftMenuTree').tree('append',{   
		parent: node.target,   
		data:nodeData
	});       
}

//修改树节点
function updateTreeNode(node_id,node_text)
{
	$("div[node-id='"+node_id+"'] .tree-title").text(node_text);
}

//删除树节点
function deleteTreeNode(node_id)
{
	var tempArr = node_id.split(",");
	for(var i=0;i<tempArr[i];i++)
	{
		$("div[node-id='"+tempArr[i]+"']").parent().remove();		
	}	
}

//初始化左边栏树对像
function iniLeftMenuTree()
{
	$('#leftMenuTree').tree({
		//url: 'data/tree_data_tongji.json',
		//url: jsonData,
		onClick:function(node){
			if(node.attributes!=undefined){   
				setMainIframeUrl(node.attributes.url,node.text,node.attributes.handls); 
            }  
		}
	});
}

function setMainIframeUrl(url,title,handls)
{
	if(url == "" || url == null) return;
	
	var curIframe = $("#iframe_"+curTabIndex);
	var curTab = $("#tab_"+curTabIndex);

	if(handls != "" && handls != null)
	{
		eval(handls);
	}else
	{	//alert(handls)
		if(url.indexOf("managerList.jsp") > -1)
		{
			$(curIframe).attr("scrolling","no");
		}
		$(curIframe).attr("src",url);
		$("#tab_"+curTabIndex+" .tabs-title").text(title);
		showTabsScroller();
	}
}

//窗体中各元素位置及大小自适应
//bannerHeight为隐藏Banner时用
function init_page()
{
	//alert($("#takeup").attr("class"));
	//var minWidth = 1003;//窗口区域最小宽度
	var minWidth = 779;//窗口区域最小宽度
	var window_height = $(window).height();
	var banner_height = $("#banner").height()
	var area_nav_height = $("#area_nav").height();
	var area_nav_spaceline_height = $("#area_nav_spaceline").height();
	var copyright_height = $("#copyright").height();
	
	var area_frame_tabs_height = $("#area_frame_tabs").height();
	var area_frame_local_height = $("#area_frame_local").height();
	var area_frame_buttom_height = $("#area_frame_buttom").height();

	//alert("========="+$("#takeup").attr("class"));
	if($("#takeup").attr("class")=="takeDown")
	{
		banner_height = 0;
	}
	//alert(banner_height);
	var area_main_height = window_height - banner_height - area_nav_height - area_nav_spaceline_height - copyright_height ;
	$("#area_main").css("height",area_main_height);
	$("#area_left").css("height",area_main_height);
	
	$("#area_leftContent").css("height",area_main_height-$("#nav_title").height()+29);
	$("#leftMenu").css("height",area_main_height-$("#nav_title").height()-55);//53为cicro product’logo height
	$("#area_right").css("height",area_main_height);
	
	var area_frame_work_height = area_main_height - area_frame_tabs_height - area_frame_local_height - area_frame_buttom_height;
	
	$("#area_frame_work").css("height",area_frame_work_height);
	$("#mainFrame").css("height",area_frame_work_height);
	
	setTabsheaderWidth();
	
	if($(window).width()<minWidth)
	{
		$("#area_nav").css("width",minWidth);
		$(".banaerContent").css("width",minWidth);
		$("#area_main_table").css("width",minWidth);
	}
	else
	{
		$("#area_nav").css("width",$(window).width());
		$(".banaerContent").css("width",$(window).width());
		$("#area_main_table").css("width",$(window).width());
	}
	//alert($(window).width());
}

function setTabsheaderWidth()
{
	if(switch_flip % 2 == 0)
		$(".tabs-header").css("width",$(window).width()-20);
	else
		$(".tabs-header").css("width",$(window).width()-233);
}


//左侧菜单打开关闭
var switch_flip = 1;
function switchSysBar()
{
	$("#area_left").toggle( switch_flip++ % 2 == 0 );
	if(switch_flip % 2 == 0)
	{
		$("#switchSysBar_pic").attr("title","打开左栏")
	}
	else
	{
		$("#switchSysBar_pic").attr("title","关闭左栏")
	}
}

//兼容了IE6的一级下拉菜单效果
function iniMenuOnMouseOver()
{
	$("#top_menu > li > ul > li").each(function(){
		$(this).hover(
		  function () {
			$(this).addClass("iehover");
		  },
		  function () {
			$(this).removeClass("iehover");
		  }
		);		
	})

	$("#menu li").each(function(){
		$(this).hover(
		  function () {
			$(this).addClass("iehover");
		  },
		  function () {
			$(this).removeClass("iehover");
		  }
		);		
	});
}

//Banner区域收缩展开效果(cookie)
function iniBannerTake(){
	//alert("$.cookie('CS_Server_BannerTake')=="+$.cookie('CS_Server_BannerTake'));
	if($.cookie('CS_Server_BannerTake')==null) return;
	
	if($.cookie('CS_Server_BannerTake')=="takeDown")
	{
		$("#takeup").removeClass("takeUp");
		$("#takeup").addClass("takeDown");
		$("#banner").hide();
	}
	if($.cookie('CS_Server_BannerTake')=="takeUp")
	{
		$("#takeup").removeClass("takeDown");
		$("#takeup").addClass("takeUp");
		$("#banner").show();
	}
}

//Banner区域收缩展开效果
function iniBannerTakeUpDown(){
	$("#takeup").click( function(){
		if($(this).attr("class")=="takeUp")
		{
			$(this).removeClass("takeUp");
			$(this).addClass("takeDown");
			$.cookie('CS_Server_BannerTake', 'takeDown');
			$("#banner").hide("blind",null,200,init_page);
		}
		else
		{
			$(this).removeClass("takeDown");
			$(this).addClass("takeUp");
			$.cookie('CS_Server_BannerTake', 'takeUp');
			$("#banner").show("blind",null,200,init_page);
			
		}
	});
}

var tabIndex = 100;
/*isColseBar是否要关闭按钮
@param value_name 要提取的ID属性名称
@param handl_name 成功后要执行的函数名
*/
function addTab(isColseBar,page_path,titles)
{
	var strColseBar = "";
	if(isColseBar)
	{
		strColseBar = "<a href=\"javascript:void(0)\" class=\"tabs-close\" onclick=\"tab_colseOnclick('"+tabIndex+"');\"></a>";
	}
	var tmpLi = "<li id=\"tab_"+tabIndex+"\"  class=\"\"><div><div><a href=\"javascript:void(0)\" class=\"tabs-inner\" onclick=\"tabOnclick('"+tabIndex+"');\"><span class=\"tabs-title tabs-closable\">信息维护</span><span class=\"tabs-icon\"></span></a>"+strColseBar+"</div></div></li>";
	
	$(".tabs").append(tmpLi);
	 
	var tmpIframe = "<iframe id=\"iframe_"+tabIndex+"\" frameborder=\"0\" scrolling=\"auto\" onload=\"\" src=\"\" width=\"100%\" height=\"100%\"></iframe>"
	$("#mainFrame > iframe").hide();
	$("#mainFrame").append(tmpIframe);
	
	
	showTabsScroller();
	
	tabOnclick(tabIndex);
	if(page_path != "" && page_path != null)
		setMainIframeUrl(page_path,titles);
	else
		setMainIframeUrl("/sys/space.html","信息维护");
	tabIndex++;
}


//刷新主页签左右滚动按钮及添加新页签按钮
function showTabsScroller()
{
	var tabsWidth = 0;
	
	var vp = $(".tabs > li").last();
	var offset = vp.offset();
	var leftAreaWidht = 0;
	if(switch_flip % 2 == 0)
	{
		leftAreaWidht = 25;
	}
	else
	{
		leftAreaWidht = 233;
	}
	tabsWidth = offset.left - leftAreaWidht + vp.width() + 15;

	var tWidth = $(".tabs-header").width()
	//alert(tabsWidth+"==="+tWidth);
	if(tabsWidth>=tWidth)
	{
		$(".tabs-scroller-left").show();
		$(".tabs-scroller-right").show();	
		$("#tab_add").css("left",tWidth-30);
	}
	else
	{
		$(".tabs-scroller-left").hide();
		$(".tabs-scroller-right").hide();
		$("#tab_add").css("left",tabsWidth);
	}
}

function tabOnclick(index)
{
	//alert(index);
	//alert($("#tab_"+index).attr("id"));
	$(".tabs > li").removeClass("tabs-selected");
	$("#tab_"+index).addClass("tabs-selected");

	$("#mainFrame > iframe").hide();
	$("#iframe_"+index).show();
	
	curTabIndex = index;
	
}

function tab_colseOnclick(index)
{
	var preLiIndex = null;
	$(".tabs > li").each(function(i){
		if($(this).attr("id")==("tab_"+index)){return false;}
		preLiIndex = $(this).attr("id").substring(4,$(this).attr("id").length);
	 });

	$("#tab_"+index).detach();
	$("#iframe_"+index).detach();
	
	if(index == curTabIndex)
	{
		tabOnclick(preLiIndex);
	}
	
	showTabsScroller();
}




function OpenModalWindow(title,url,width,height)
{
	$('#sysDialog_Frame').attr("src",url);
	$("#sysDialog").dialog({
			resizable: false,
			width:width,
			height:height,
			modal: true,
			title:title
		});
}
function CloseModalWindow()
{	
	$("#sysDialog").dialog('close');
	$('#sysDialog_Frame').attr("src","");
}

/******************** 提示框操作　开始  *************************/
function msgAlert(msg)
{
	msgHandl("alert",msg);
	$("#msgAlert").oneTime(1300, function() {
		$(this).fadeOut(300,function(){
			$(this).dialog('close');
		});
	});	
}

function msgError(msg)
{
	msgHandl("error",msg);
}

function msgWargin(msg)
{
	msgHandl("wargin",msg);
}

function msgHandl(type,msg)
{
	var ms = "提示信息";
	$("#msgAlert div:first").removeAttr("class");
	if(type == "alert")
	{
		ms = "提示信息";
		//$("#msgAlert").attr("title","提示信息");
		$("#msgAlert div:first").addClass("alert_img");
	}
	if(type == "error")
	{
		ms = "错误";
		//$("#msgAlert").attr("title","错误");
		$("#msgAlert div:first").addClass("error_img");
	}
	if(type == "wargin")
	{
		ms = "警告";
		//$("#msgAlert").attr("title","警告");
		$("#msgAlert div:first").addClass("wargin_img");
	}

	$("#msgAlert div:first").addClass("msg_left_div");
	//首先解除操作按钮上的绑定事件，要不然使用click会累加事件的
	$("#msgAlert input").unbind('click');
	//给操作按钮加上点击事件
	$("#msgAlert input").click(function(){$("#msgAlert").dialog('close');});
	//加入描述语句
	$("#msgAlert td").html(msg);
	$("#msgAlert").dialog({
			resizable: false,
			width:340,
			height:213,
			modal: true,
			title:ms
	});
}

//fun参数：用于用户点击确认按钮后执行的事件，其中iframe_1需要替换为当前所在iframe的名称，需要一个当前iframe的对象
function msgConfirm(msg,fun)
{	
	//首先解除操作按钮上的绑定事件，要不然使用click会累加事件的
	$("#msgConfirm input:first").unbind('click');
	$("#msgConfirm input:last").unbind('click');
	//给操作按钮加上点击事件
	
	$("#msgConfirm input:first").click(function(){$("#msgConfirm").dialog('close');eval('document.getElementById("iframe_'+curTabIndex+'").contentWindow.'+fun)});
	$("#msgConfirm input:last").click(function(){$("#msgConfirm").dialog('close');});
	//加入描述语句
	$("#msgConfirm td").html(msg);
	$("#msgConfirm").dialog({
			resizable: false,
			width:340,
			height:213,
			modal: true
		});
}

/******************** 提示框操作　结束  *************************/

function loginOut()
{
	if(UserLogin.loginOut())
		window.location.href = "/sys/login.jsp";
	else
		alert("注销失败");
}

//得到当前IFrame窗口对象
function getCurrentFrameObj()
{
	return document.getElementById("iframe_"+curTabIndex).contentWindow;
}