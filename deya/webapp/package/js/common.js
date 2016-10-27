// JavaScript Document

//禁用右键、文本选择功能、复制按键
/*
jQuery(function($) {
	$(document).bind("contextmenu",function(){return false;});
	$(document).bind("selectstart",function(){return false;});
	$(document).keydown(function(){return key(arguments[0])});
});
*/

//加入收藏
//<a href="javascript:void(0);" onclick="addBookmark('网站名称',location.href)">加入收藏</a>
function addBookmark(title, url) {
	if (document.all)
	{  
		try 
		{
			window.external.addFavorite(url, title);
		}  
		catch (e1)  
		{
			try
			{
				window.external.addToFavoritesBar(url,title);
			}
			catch (e2)
			{
				alert('加入收藏失败，请您使用Ctrl+D进行添加手工加入。')
			}
		}
	} 
	else if (window.external) 
	{
		window.sidebar.addPanel(url, title,"");
	}
	else 
	{
		alert('加入收藏失败，请您使用Ctrl+D进行添加手工加入。')
	}  
}


//设置为首页
//<a href="javascript:void(0);" target="_top" onclick="SetHome(this,window.location)">设为首页</a>
function setHome(obj,url){ 
	if (document.all)
	{
		document.body.style.behavior = 'url(#default#homepage)';
		document.body.setHomePage(url);
	}
	else if (window.sidebar)
	{
		if(window.netscape)
		{
		try{netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");}
		catch (e){alert( "该操作被浏览器拒绝，如果想启用该功能，请在地址栏内输入 about:config,然后将项 signed.applets.codebase_principal_support 值该为true" );}
		}
	}
	var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components. interfaces.nsIPrefBranch);   
	prefs.setCharPref('browser.startup.homepage',url);   
} 


//页签切换效果
function setTab(name,cursel,n){
	for(i=1;i<=n;i++){
		var menu=$("#"+name+i);
		var con=$("#con_"+name+"_"+i);		
		if(i==cursel)
		{
			$(menu).addClass("hover");
			$(con).fadeTo("slow", 1);
		}
		else
		{
			$(menu).removeClass("hover");
			$(con).hide();
		}
	}
}


//正文字号大中小
//<span class="pointer" onclick="doZoom('small');">小</span>
//<span class="pointer" onclick="doZoom('');">中</span>
//<span class="pointer" onclick="doZoom('big');">大</span>
function doZoom(fontSizeClass)
{
	$("#info_content").removeClass("info_content_small").removeClass("info_content_big").removeClass("info_content_mid");
	if(fontSizeClass!="")
	{
		$("#info_content").addClass("info_content_"+fontSizeClass);
	}
}


//iframe自适应高度，此为注册函数，必须放到WEB服务器上访问才能看到效果
//frameID为iframe的ID
//addHeight为修正值，一般为0~30
//minHeight为最小值
function regFrameAutoHeight(frameID,addHeight,minHeight)
{
	$("#"+frameID).load(function(){
		var mainHeight = $(this).contents().find("body").height()+addHeight;
		if(minHeight<mainHeight)
		{
			$(this).height(mainHeight);
		}
	}); 
}




