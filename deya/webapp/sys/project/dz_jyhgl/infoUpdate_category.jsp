<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String gz_id = request.getParameter("gz_id");
	String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>配置栏目</title>
<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<style>
.table_form2 td{ text-align:left; color:#32609E; border-collapse:collapse; vertical-align:middle; vertical-align:middle;}
.table_form2 td li{ float:left; padding-right:20px;}
</style>
<script type="text/javascript">
var gz_id = "<%=gz_id%>";
var site_id = "<%=site_id%>";
var CategoryRPC = jsonrpc.CategoryRPC;
var InfoUpdateRPC = jsonrpc.InfoUpdateRPC;
var json_data;
var selected_category_ids = "";
$(document).ready(function(){
	initButtomStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
    selected_category_ids = ","+InfoUpdateRPC.getInfoUpdateCategoryByGzId(gz_id)+",";//得到能管理的栏目ID
	init_FromTabsStyle();
    showCategoryTree();
});
function showCategoryTree()
{
	json_data = eval(CategoryRPC.getCategoryTreeBySiteID(site_id));
	json_data = json_data[0].children;
	setLeftMenuTreeJsonData(json_data);
	initMenuClick("leftMenuTree");
	setSelectedNode("leftMenuTree");
}
//初始化复选框树
function initMenuClick(div_name)
{
    $("#"+div_name+" .tree-icon").after('<SPAN class="tree-checkbox tree-checkbox0"></SPAN>');

    $("#"+div_name+" .icon-category").next().remove();//icon-category 表示该节点是zt的分类，不属于节点，所以删除掉
    $("#"+div_name+" .tree-checkbox").click(function(){
        if($(this).attr("class").indexOf("tree-checkbox0") > -1)
        {
            $(this).parent().parent().find(".tree-checkbox").removeClass("tree-checkbox0");
            $(this).parent().parent().find(".tree-checkbox").removeClass("tree-checkbox1");
            $(this).parent().parent().find(".tree-checkbox").addClass("tree-checkbox3");

            $(this).removeClass("tree-checkbox3");
            $(this).addClass("tree-checkbox1");
        }
        else
        {
            if($(this).attr("class").indexOf("tree-checkbox1") > -1)
            {
                $(this).parent().parent().find(".tree-checkbox").removeClass("tree-checkbox3");
                $(this).parent().parent().find(".tree-checkbox").addClass("tree-checkbox0");

                $(this).removeClass("tree-checkbox1");
                $(this).addClass("tree-checkbox0");
            }
        }
    });
}
//选中已选　过的栏目
function setSelectedNode(div_name)
{
    $("#"+div_name+" div[node-id]").each(function(){
        if(selected_category_ids.indexOf(","+$(this).attr("node-id")+",") > -1)
        {
            $(this).find(".tree-checkbox").click();
        }
    });
}
function checkAllTree()
{
    if($("#checkAll").is(':checked'))
    {
        $("#checkAll").attr("checked",false);

        var allObj = $("#leftMenuTree .tree-checkbox");
        allObj.removeClass("tree-checkbox1");
        allObj.removeClass("tree-checkbox3");
        allObj.addClass("tree-checkbox0");
    }else
    {
        $("#checkAll").attr("checked",true);

        var allObj = $("#leftMenuTree .tree-checkbox");
        allObj.removeClass("tree-checkbox1");
        allObj.removeClass("tree-checkbox0");
        allObj.addClass("tree-checkbox3");

        var obj = $("#leftMenuTree > li > div > .tree-checkbox");
        obj.removeClass("tree-checkbox0");
        obj.removeClass("tree-checkbox3");
        obj.addClass("tree-checkbox1");

    }
}
function checkAllTree2(obj)
{
    if(!$(obj).is(':checked'))
    {
        var allObj = $("#leftMenuTree .tree-checkbox");
        allObj.removeClass("tree-checkbox1");
        allObj.removeClass("tree-checkbox3");
        allObj.addClass("tree-checkbox0");
    }else
    {


        var allObj = $("#leftMenuTree .tree-checkbox");
        allObj.removeClass("tree-checkbox1");
        allObj.removeClass("tree-checkbox0");
        allObj.addClass("tree-checkbox3");

        var obj = $("#leftMenuTree > li > div > .tree-checkbox");
        obj.removeClass("tree-checkbox0");
        obj.removeClass("tree-checkbox3");
        obj.addClass("tree-checkbox1");

    }
}
function getSelectedNodeIDS(div_name)
{
    var ids = "";
    $("#"+div_name+" .tree-checkbox1").each(function(){
        ids += ","+$(this).parent().attr("node-id");
    });
    return ids;
}

function saveInfoUpdateCategory(){
    var cate_ids = getSelectedNodeIDS("leftMenuTree") + getSelectedNodeIDS("zt_leftMenuTree") + getSelectedNodeIDS("fw_leftMenuTree");
    if(cate_ids != "" && cate_ids != null){
        cate_ids = cate_ids.substring(1);
	}
    if(InfoUpdateRPC.insertInfoUpdateCategory(cate_ids,gz_id))
    {
        top.msgAlert("栏目配置"+WCMLang.Add_success);
        top.CloseModalWindow();
        top.getCurrentFrameObj().reloadDataList();
    }
    else
    {
        top.msgWargin("栏目配置"+WCMLang.Add_fail);
    }
}
</script>
</head>
<body>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" align="left">
	<tr>
		<td align="left" class="fromTabs width10" style="">
			<span class="blank3"></span>
		</td>
		<td align="left" width="100%">
			<ul class="fromTabs">
				<li class="list_tab list_tab_cur">
					<div class="tab_left">
						<div class="tab_right" >选择栏目</div>
					</div>
				</li>
			</ul>
		</td>
	</tr>
</table>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">

<div class="infoListTable" id="listTable_0">
	<table id="table" class="" border="0" cellpadding="0" cellspacing="0" style="width:94%">
		<tbody>
		<tr>
			<td align="center" >
				<div style="text-align:left"><input type="checkbox" id="checkAll" onclick="checkAllTree2(this)"><label onclick="checkAllTree()">全选</label></div>
				<div id="leftMenuBox" style="width:100%;height:381px;overflow:auto;border:1px solid #828790;background:#FFFFFF;">
					<div id="leftMenu" class="contentBox6 textLeft" >
						<ul id="leftMenuTree" class="easyui-tree" animate="true">
						</ul>
					</div>
				</div>
			</td>
		</tr>
		</tbody>
	</table>
<span class="blank12"></span>
</div>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="saveInfoUpdateCategory()" value="保存" />
			<input id="userAddReset" name="btn1" type="button" onclick="form1.reset()" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
