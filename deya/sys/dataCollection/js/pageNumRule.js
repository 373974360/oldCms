
function showChooseAddUrlDiv(AddUrlType)
{
	if(AddUrlType == 0)
	{
		$("#oneURLDiv").show();
		$("#manyURLDiv").hide();
	}
	if(AddUrlType == 1)
	{
		$("#oneURLDiv").hide();
		$("#manyURLDiv").show();
	}
}
//点击通配符触发事件
var typeValue=0; //用于区分用户选择的是等差还是等比
function previewAddUrl()
{
	getTxt1CursorPosition();  //获取光标位置
	addURLInfotopreview();
}

//添加预览链接
function addURLInfotopreview()
{
	var tableStr = "<table style='float:left;'><tbody>";
	if(typeValue == 0) //等差数列
	{
		var a1 = parseInt($("#firstTermBad").val()); //首项
		var n = parseInt($("#TermNumBad").val());  //项数
		var d = parseInt($("#toleranceBad").val()); //公差
		for(var i=1;i<=n;i++)
		{
			an = a1+(i-1)*d;
			var url = $("#addurlByMany").val(); //循环获取input中的值
			url = url.replace(/[\(](.*?)[\)]+/ig,an);
			tableStr += "<tr><td>"+url+"</td></tr>";
		}
	}else{ //等比数列
		var a1 = parseInt($("#firstTerm").val()); //首项
		var n = parseInt($("#TermNum").val());  //项数
		var q = parseInt($("#tolerance").val()); //公比
		for(var i=1;i<=n;i++)
		{
			an = a1*(Math.pow(q,i-1));
			var url = $("#addurlByMany").val();
			url = url.replace(/[\(](.*?)[\)]+/ig,an);
			tableStr += "<tr><td>"+url+"</td></tr>";
		}
	} 
	tableStr += "</tbody></table>";
	$("#previewURL").empty();
	$("#previewURL").append(tableStr);
}

//向光标位置添加(*)通配符
function getTxt1CursorPosition()
{            
	var oTxt = document.getElementById("addurlByMany");
	var cursurPosition = getCursortPosition(oTxt);  
	
	var str = oTxt.value.substring(0,cursurPosition)+"(*)"+oTxt.value.substring(cursurPosition,oTxt.value.length);
	$("#addurlByMany").val(str);
}

//获取光标位置函数
function getCursortPosition(ctrl) 
{ 	
	var CaretPos = 0; // IE Support 
	if(document.selection) 	
	{
		ctrl.focus(); 
		var Sel = document.selection.createRange(); 
		Sel.moveStart('character', -ctrl.value.length); 
		CaretPos = Sel.text.length;
		
	}else if (ctrl.selectionStart || ctrl.selectionStart == '0') // Firefox support
	{
		CaretPos = ctrl.selectionStart;
	}
	return (CaretPos); 
} 

//获取用户选择的是等比还是等差数列
function getEqualGradeorRatio(value)
{
	typeValue = value;
}

function addOneURL()
{
	var addURL = $("#addurlByOne").val();
	$("#save_collURL").empty();
	$("#save_collURL").val(addURL);
}

function addManyURL()
{
	var addURL = $("#addurlByMany").val();
	
	var reg=/^(http|https):\/\/(.*?)/ig;

	if(!reg.test(addURL)){
		top.msgAlert("地址不是以http://或https://开头!");
	}else{
		if(addURL.indexOf("(*)")>-1)
		{   
			if(typeValue == 0){ //等差数列
				var a1 = parseInt($("#firstTermBad").val()); //首项
				var n = parseInt($("#TermNumBad").val());  //项数
				var d = parseInt($("#toleranceBad").val()); //公差
				addURL = addURL.replace(/[\(](.*?)[\)]+/ig,"<0,"+a1+","+n+","+d+">");
			}else{ //等比
				var a1 = parseInt($("#firstTerm").val()); //首项
				var n = parseInt($("#TermNum").val());  //项数
				var q = parseInt($("#tolerance").val()); //公比
				addURL = addURL.replace(/[\(](.*?)[\)]+/ig,"<1,"+a1+","+n+","+q+">");
			}
			$("#save_collURL").empty();
			$("#save_collURL").val(addURL);
		}else{
			top.msgAlert("请使用(*)通配符匹配网址!");
		}
	}
	
}