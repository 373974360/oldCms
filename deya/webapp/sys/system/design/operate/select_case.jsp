<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择创建方式</title>


<link rel="stylesheet" type="text/css" href="/sys/styles/themes/default/tree.css">
<jsp:include page="../../../include/include_tools.jsp"/>
<script type="text/javascript" src="/sys/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/sys/js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/design_util.js"></script>
<script type="text/javascript">
var DesignRPC = jsonrpc.DesignRPC;
var site_id="${param.site_id}";

$(document).ready(function(){
	initButtomStyle();
	init_input();
	$("label").live("click",function(){
		$(this).prev().click();
	});
	getCssList();
	getCaseList();
	getZTCategory();
});

function showTree(val)
{
	switch(val)
	{
		case "0":$("#css_div").show();
				$("#case_div").hide();
			   $("#cat_tree_div").hide();
			   break;
		case "1":$("#case_div").hide();
				$("#css_div").hide();
			   $("#cat_tree_div").show();
			   break;
		case "2":$("#case_div").show();
				$("#css_div").hide();
			   $("#cat_tree_div").hide();
			   break;
	}
}

//得到所有专题目录
function getZTCategory()
{		
	var c_data = eval(jsonrpc.CategoryRPC.getZTCategoryTreeJsonStr(site_id));
	if(c_data != null)
	{
		for(var i=0;i<c_data.length;i++)
		{
			if(c_data[i].children != null && c_data[i].children.length > 0)
			{
				for(var j=0;j<c_data[i].children.length;j++)
				{
					c_data[i].children[j].state = "";
					c_data[i].children[j].children = null;
				}
			}		
		}
		setAppointTreeJsonData("leftMenuTree",c_data);
		
		initMenuTree();
	}
}

//初始化栏目树
function initMenuTree()
{	
	$("#leftMenuTree .tree-icon").after('<SPAN class="tree-checkbox tree-checkbox0"></SPAN>');
	$("#leftMenuTree .tree-checkbox").click(function(){
		if($(this).attr("class").indexOf("tree-checkbox0") > -1)
		{
			$("#leftMenuTree .tree-checkbox").removeClass("tree-checkbox1");			
			$(this).addClass("tree-checkbox1");
			temp_cat_id = $(this).parent().attr("node-id");
			temp_cat_name = $(this).parent().text();
		}
	});
	$("#leftMenuTree > li > div > .tree-checkbox").remove();		
}

//得到方案套餐列表
function getCaseList()
{	
	var m = new Map();
	m.put("start_num", 0);	
	m.put("page_size", 20);

	var caseList = DesignRPC.getDesignCaseList(m);
	caseList = List.toJSList(caseList);
	for(var i=0;i<caseList.size();i++)
	{
		var tmpPicLi = "<li><div style='width:100%;text-align:ceter'><img src='"+caseList.get(i).thumb_url+"' width='70px' height='80px' align='center'/></div>";

		tmpPicLi += "<div><input id='case_radio' name='case_radio' type='radio' value='"+caseList.get(i).case_id+"'";
			
		tmpPicLi +="/><label>"+caseList.get(i).case_name+"</label></div></li>";

		$("#caseList").append(tmpPicLi);
	}
	
	init_input();
}

function getCssList()
{	
	var m = new Map();
	m.put("start_num", 0);	
	m.put("page_size", 20);

	var cssList = DesignRPC.getDesignCssList(m);
	cssList = List.toJSList(cssList);
	for(var i=0;i<cssList.size();i++)
	{
		var tmpPicLi = "<li><div style='width:100%;text-align:ceter'><img src='"+cssList.get(i).thumb_url+"' width='70px' height='80px' align='center'/></div>";

		tmpPicLi += "<div><input id='css_radio' name='css_radio' type='radio' value='"+cssList.get(i).css_id+"'";
			
		tmpPicLi +="/><label>"+cssList.get(i).css_name+"</label></div></li>";

		$("#cssList").append(tmpPicLi);
	}
	
	init_input();
}

function returnValues()
{
	var c_type = $(":checked[id='create_type']").val();	
	var v_id = "";
	if(c_type == 0)
	{
		v_id = $(":checked[id='css_radio']").val();	
		if(v_id == null)
		{
			msgWargin("请选择主题风格");
			return;
		}
	}
	if(c_type == 1)
	{
		v_id = $("#leftMenuTree .tree-checkbox1").parent().attr("node-id");
		if(v_id == null)
		{
			msgWargin("请选择类似创建目录");
			return;
		}
	}
	if(c_type == 2)
	{
		v_id = $(":checked[id='case_radio']").val();	
		if(v_id == null)
		{
			msgWargin("请选择方案套餐");
			return;
		}
	}
	getCurrentFrameObj().openDesignPage(c_type,v_id);
	CloseModalWindow();
}
</script>
</head>

<body>
<table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:98%">
	<tbody>
		<tr>		
			<th>创建方式：</th>
			<td align="center" >
				<ul>
					<li><input type="radio" name="create_type" id="create_type" value="0" checked="checked" onclick="showTree(this.value)"/><label>空白页面</label></li>
					<li><input type="radio" name="create_type" id="create_type" value="1" onclick="showTree(this.value)"/><label>类似创建</label></li>
					<li><input type="radio" name="create_type" id="create_type" value="2" onclick="showTree(this.value)"/><label>方案套餐</label></li>
				</ul>
			</td>
		</tr>
		<tr>
			<td colspan="2" height="410px">
				<div id="css_div" style="width:100%;height:410px;overflow:auto;background:#FFFFFF;" class="border_color">
					<ul id="cssList" class="imgList">
					</ul>
				</div>
				<div id="case_div" style="width:100%;height:410px;overflow:auto;background:#FFFFFF;" class="border_color hidden">
					<ul id="caseList" class="imgList">
					</ul>
				</div>
				<div id="cat_tree_div" class="select_div border_color hidden" style="width:100%; height:410px; overflow:auto;border:1px #7f9db9 solid;">
					<div id="leftMenuBox">
						<div id="leftMenu" class="contentBox6 textLeft" style="height:400px;">
							<ul id="leftMenuTree" class="easyui-tree" animate="true" style="width:176px;overflow:hidden">
							</ul>
							<span class="blank3"></span >
						</div>
					</div>
				</div>
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
			<input id="addButton" name="btn1" type="button" onclick="returnValues()" value="创建" />			
			<input id="userAddCancel" name="btn1" type="button" onclick="CloseModalWindow();" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>

</form>
</body>
</html>
