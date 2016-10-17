<%@ page contentType="text/html; charset=utf-8"%>
<%
	String dept_id = request.getParameter("deptID");
	String lab_num = request.getParameter("labNum");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>机构管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/deptList.js"></script>
<script type="text/javascript" src="js/userList.js"></script>
<script type="text/javascript" src="js/deptManager.js"></script>
<script type="text/javascript">

var dept_id = "<%=dept_id%>";
var load_dept = false;
var load_admin = false;
var load_manager = false;
var lab_num = "<%=lab_num%>";

//如果部门ID为空,从登录人员中得到它能管理的部门节点第一个ID
if(dept_id == "null" || dept_id == "")
{
	var json_data = eval(DeptRPC.getDeptTreeByUser());	
	if(json_data != null)
		dept_id = json_data[0].id;
}

$(document).ready(function(){
	//让树节点选中
	top.treeNodeSelected(dept_id);
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	//reoloadDeptList();
	reoloadUserList();

	if(lab_num != "" && lab_num != "null" && lab_num != null)
	{
		//changeLabShowList(lable_name);
		$(".fromTabs li:nth-child("+lab_num+") .tab_right").click();
		$(".fromTabs li:nth-child("+lab_num+")").click();		
	}
});


//用于标签切换时显示列表数据，页面加载时，只加载了第一个标签中的列表内容（部门列表），切换到其它标签时，如果是第一次，需要加载数据
function changeLabShowList(labname)
{
	if(labname == "dept" && load_dept == false)
	{
		reoloadDeptList();
		load_dept = true;
	}	
	if(labname == "manager" && load_manager == false)
	{
		initManagerTable();
		showManagerList();	
		showManagerTurnPage();
		Init_InfoTable(manager_table.table_name);
		load_manager = true;
	}
	$(".search_td").hide();
	$("#"+labname+"_search").show();
}
</script>
</head>

<body>
<div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="left" class="fromTabs width10" style="">	
			
			<span class="blank3"></span>
		</td>
		<td align="left" width="50%">
			<ul class="fromTabs">
				<li class="list_tab list_tab_cur">
					<div class="tab_left">
						<div class="tab_right" onclick="changeLabShowList('user')">用户列表</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" onclick="changeLabShowList('dept')">部门列表</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" onclick="changeLabShowList('manager')">管理员列表</div>
					</div>
				</li>
			</ul>
		</td> 
		<td align="right" valign="middle" id="dept_search" class="search_td fromTabs hidden" >
			<select id="searchFields" class="input_select width70" >
				<option selected="selected" value="dept_name">部门名称</option>
				<option value="area_code">地区代码</option>
				<option value="dept_code">机构代码</option>
			</select>
			<input id="searchkey" type="text" class="input_text" value=""  /><input id="btnSearch" type="button" class="btn x2" value="搜索" onclick="deptSearchHandl(this)"/>		
			<span class="blank3"></span>
		</td>
		<td align="right" valign="middle" id="user_search" class="search_td fromTabs" >
			<select id="searchFields" class="input_select width50" >
				<option selected="selected" value="user_realname">姓名</option>
			</select>
			<input id="searchkey" type="text" class="input_text" value=""  /><input id="btnSearch" type="button" class="btn x2" value="搜索" onclick="userSearchHandl(this)"/>
			<select id="orderByFields" class="input_select" onchange="userSortHandl(this.value)">
				<option selected="selected" value="user_id,desc">按时间倒序(默认)</option>
				<option value="user_id,asc">按时间顺序</option>
			</select>
			<span class="blank3"></span>
		</td>
		<td align="right" valign="middle" id="manager_search" class="search_td fromTabs" style="display:none">			
			<span class="blank3"></span>
		</td>
	</tr>
</table>
</div>
<span class="blank3"></span>
<!-- 人员列表区域　开始 -->
<div class="infoListTable" id="listTable_0">
<div id="user_table"></div>
<div id="user_turn"></div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="openAddUserPage();" value="添加用户" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(user_table,'user_id','openUpdateUserPage()');" value="修改" />	
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(user_table,'user_id','openSelectSingleDept(\'选择组织机构节点\',\'moveUser\',\'\')')" value="移动用户" /><!-- openSelectSingleDept 函数在/sys/js/sysUI.js中 -->
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(user_table,'user_id','setUserStatus(1)')" value="停用" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(user_table,'user_id','setUserStatus(0)')" value="启用" />
				<input id="btn2" name="btn6" type="button" onclick="publicSelectCheckbox(user_table,'user_id','setUserToAdmin()');" value="置为管理员" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectSinglCheckbox(user_table,'user_id','openRegPage()')" value="帐号维护" />
				<input id="btn2" name="btn2" type="button" onclick="saveUserSort();" value="保存排序" />
				<input id="btn1" name="btn1" type="button" onclick="deleteRecord(user_table,'user_id','deleteUser()');" value="删除" /><!-- deleteRecord 函数在/sys/js/sysUI.js中 -->
			</td>
		</tr>
	</table>
</div>
<!-- 人员列表区域　结束 -->

<!-- 部门列表区域　开始 -->
<div class="infoListTable hidden" id="listTable_1">
	<div id="dept_table"></div>
	<div id="turn"></div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="openAddDeptPage();" value="新建部门" />
				<input id="btn2" name="btn2" type="button" onclick="updateRecord(table,'dept_id','openUpdateDeptPage()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'dept_id','openSelectSingleDept(\'选择组织机构节点\',\'moveDept\',\'\')')" value="移动部门" /><!-- openSelectSingleDept 函数在/sys/js/sysUI.js中 -->				
				<input id="btn2" name="btn2" type="button" onclick="saveDeptSort();" value="保存排序" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'dept_id','deleteDeptHandl()');" value="删除" /><!-- deleteRecord 函数在/sys/js/sysUI.js中 -->
			</td>
		</tr>
	</table>
</div>
<!-- 部门列表区域　结束 -->

<!-- 管理员列表区域　开始 -->
<div class="infoListTable hidden" id="listTable_2">
	<div id="manager_table"></div>
	<div id="manager_turn"></div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="addDeptManager();" value="添加管理员" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(manager_table,'user_id','deleteDeptManager()');" value="删除" /><!-- deleteRecord 函数在/sys/js/sysUI.js中 -->
			</td>
		</tr>
	</table>
</div>
<!-- 管理员列表区域　结束 -->
</div>
</body>
</html>
