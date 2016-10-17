//得到url参数
function getUrlParam(name)
{
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	if (r!=null) return unescape(r[2]); return null; //返回参数值
} 


//$(function() {
	var href = window.location.href;
	if(href.indexOf(".htm?")>-1){
	      //alert(href);
		var cur_page = getUrlParam("cur_page");
		//alert(href.lastindexof("_"));
		href = href.substring(0,href.lastIndexOf("?"));
		href = href.substring(0,href.lastIndexOf("_")+1)+cur_page+".htm"; 
		//alert(href);
		window.location.href = href;
	}
//});


