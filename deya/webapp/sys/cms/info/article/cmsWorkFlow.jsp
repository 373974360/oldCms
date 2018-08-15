<%@ page contentType="text/html; charset=utf-8" %>
<%
	String cid = request.getParameter("cat_id");
	String siteid = request.getParameter("site_id");
	String app_id = request.getParameter("app_id");
	if (siteid == null || siteid.equals("null")) {
		siteid = "";
	}
	if (app_id == null || app_id.trim().equals("")) {
		app_id = "cms";
	}
	String snum = request.getParameter("snum");
	if (snum == null || snum.trim().equals("") || snum.trim().equals("null")) {
		snum = "0";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=8"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>信息管理</title>
	<link type="text/css" rel="stylesheet" href="../../../styles/themes/default/tree.css"/>
	<link type="text/css" rel="stylesheet" href="../../../styles/sq.css"/>


	<jsp:include page="../../../include/include_tools.jsp"/>
	<script type="text/javascript" src="../../../js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../../../js/indexjs/indexList.js"></script>
	<script type="text/javascript" src="../../../js/indexjs/tools.js"></script>
	<script type="text/javascript" src="js/public.js"></script>

	<script type="text/javascript">

        var site_id = "<%=siteid%>";
        var app = "<%=app_id%>";
        var InfoBaseRPC = jsonrpc.InfoBaseRPC;
        var model_map = jsonrpc.ModelRPC.getModelMap();
        model_map = Map.toJSMap(model_map);
        var current_page_num = 1;
        var tp = new TurnPage();
        var beanList = null;
        var table = new Table();
        table.table_name = "table";
        var opt_ids = ","+getOptIDSByUser(app,site_id)+",";//登录人所拥有管理权限ID

        //搜索条件
        var tj = "";
        var highSearchString = "";

        $(document).ready(function () {
            initButtomStyle();
            showModels();
            reloadInfoDataList();
            showSelectDiv2("cat_tree", "cat_tree_div1", 300);
            getAllInuptUserID();
            setUserOperate();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");
        });

        function reloadInfoDataList() {
            initTable();
            tp.curr_page = current_page_num;
            showList();
        }

        function initTable() {
            var colsMap = new Map();
            var colsList = new List();

            colsList.add(setTitleClos("title", "标题", "", "", "", ""));//英文名，显示名，宽，高，样式名，点击事件　
            colsList.add(setTitleClos("cat_cname", "所属栏目", "100px", "", "", ""));
            colsList.add(setTitleClos("input_user_name", "添加人", "100px", "", "", ""));
            colsList.add(setTitleClos("input_dtime", "添加时间", "100px", "", "", ""));
            colsList.add(setTitleClos("modify_user_name", "审核人", "100px", "", "", ""));
            colsList.add(setTitleClos("released_dtime", "审核时间", "100px", "", "", ""));

            table.setColsList(colsList);
            table.setAllColsList(colsList);
            table.enableSort = false;//禁用表头排序
            table.onSortChange = showList;
            table.show("table");//里面参数为外层div的id

        }

        function showList() {
            var con_map = new Map();
            con_map.put("info_status", "8");
            con_map.put("final_status", "0");
            con_map.put("site_id", site_id);
            con_map.put("page_size", "15");
            con_map.put("start_num", "0");
            if(selectIds != null && selectIds != ""){
                con_map.put("cat_ids",selectIds);
            }
            con_map.put("app_id", app);
            con_map.put("start_num", tp.getStart());
            con_map.put("page_size", tp.pageSize);
            con_map.put("highSearchString", highSearchString);

            if (tj != "") {
                con_map.put("modelString", tj);
            } else {
                con_map.remove("modelString");
            }
            var sortCol = table.sortCol;
            var sortType = table.sortType;
            if (sortCol == "" || sortCol == null) {
                sortCol = "ci.released_dtime desc,ci.id";
                sortType = "desc";
            }
            con_map.put("sort_name", sortCol);
            con_map.put("sort_type", sortType);
            if (table.con_value.trim() != "" && table.con_value != null) {
                con_map.put("con_name", table.con_name);
                con_map.put("con_value", table.con_value);
            }
            beanList = InfoBaseRPC.getInfoList(con_map);
            beanList = List.toJSList(beanList);//把list转成JS的List对象

            curr_bean = null;
            table.setBeanList(beanList, "td_list");//设置列表内容的样式
            table.show("table");

            table.getCol("title").each(function (i) {
                $(this).css({"text-align":"left"});
                if(i>0)
                {
                    var title_flag = "";
                    var title_end_str = "";
                    if(beanList.get(i-1).is_host == 1)
                    {
                        title_flag = "<span class='f_red'>[引]</span>";
                    }
                    if(beanList.get(i-1).is_host == 2)
                    {
                        title_flag = "<span class='f_red'>[链]</span>";
                    }

                    if(beanList.get(i-1).pre_title != "")
                    {
                        title_flag += "<span >["+beanList.get(i-1).pre_title+"]</span>";
                    }

                    if(beanList.get(i-1).is_pic == 1)
                        title_end_str = "(图)";

                    //var model_ename = ModelRPC.getModelEName(beanList.get(i-1).model_id);
                    $(this).addClass(model_map.get(beanList.get(i-1).model_id).model_icon);
                    $(this).css("padding-left","20px");
                    var url = beanList.get(i-1).content_url;
                    $(this).html('<a href="'+url+'" target=_blank>'+title_flag+beanList.get(i-1).title+'</a>'+title_end_str);
                }
            });
            current_page_num = tp.curr_page;
            Init_InfoTable(table.table_name);
            showTurnPage(con_map);
        }

        function showTurnPage(con_map) {
            tp.total = InfoBaseRPC.getInfoCount(con_map);
            tp.show("turn", "");
            tp.onPageChange = showList;
        }

        function getAddPagebyModel(model_id) {
            var add_page = jsonrpc.ModelRPC.getModelAddPage(model_id);
            if (add_page == "" || add_page == null)
                add_page = "m_article.jsp";
            return add_page;
        }

        //单条信息撤销
        function doCancel(num) {
            var list = new List();
            list.add(beanList.get(num));
            if (InfoBaseRPC.updateInfoStatus(list, "3")) {
                parent.msgAlert("信息撤销成功");
                reloadInfoDataList();
            } else {
                parent.msgWargin("信息撤销失败");
            }
        }
        //单条信息逻辑删除
        function doDelete(num) {
            parent.msgConfirm(WCMLang.Delete_confirm, "doDeleteHandl(" + num + ")");
        }

        function doDeleteHandl(num) {
            var list = new List();
            list.add(beanList.get(num));
            if (InfoBaseRPC.deleteInfo(list)) {
                parent.msgAlert("信息" + WCMLang.Delete_success);
                reloadInfoDataList();
            } else {
                parent.msgWargin("信息" + WCMLang.Delete_fail);
            }
        }
        function showModels() {
            $("#pageGoNum").append("<option value=\"-1\" selected=\"selected\"  >全部</option> ");
            var is_first = false;
            var keys = model_map.keySet();
            for (var i = 0; i < 4; i++) {
                var model = model_map.get(keys[i]);
                if (model.app_id == app) {
                    $("#pageGoNum").append("<option value=\"" + model.model_id + "\">" + model.model_name + "</option> ");
                }
            }
        }

        //以模型为条件过滤
        function changeFactor() {
            var cf = $("#pageGoNum").val();
            if (cf == "-1") {
                tj = "";
            } else
                tj = " ci.model_id=" + cf;
            //alert(tj);
            reloadInfoDataList();
        }

        //搜索
        function searchInfo() {
            var cf = $("#pageGoNum").val();
            if (cf == "-1") {
                tj = "";
            } else{
                tj = " ci.model_id=" + cf;
            }
            var keywords = $("#searchkey").val();
            if (keywords.trim() != "") {
                table.con_name = "ci.title";
                table.con_value = keywords;
            } else {
                table.con_value = "";
            }
            var search_con = "";
            var orderByFields = "1";
            
            var start_time = $("#start_time").val();
            var end_time = $("#end_time").val();
            if (start_time != "" && start_time != null) {
                search_con += " and ci.input_dtime > '" + start_time + " 00:00:00'";
                if (end_time != "" && end_time != null) {
                    if (judgeDate(end_time, start_time)) {
                        msgWargin("结束时间不能小于开始时间");
                        return;
                    }
                }
            }
            if (end_time != "" && end_time != null) {
                search_con += " and ci.input_dtime < '" + end_time + " 23:59:59'";
            }
            highSearchHandl(search_con, orderByFields);
        }

        //打开高级搜索页面
        function openHighSearchPage() {
            parent.OpenModalWindow("高级搜索", "/sys/cms/info/article/publishInfoSearch.jsp", 350, 340);
        }

        //高级搜索处理
        function highSearchHandl(search_cons, order_type_num) {
            highSearchString = search_cons;//给搜索字符串付值
            changeTimeSortHandl(order_type_num);//得到排序方式
            current_page_num = 1;
            reloadInfoDataList();
        }

        function changeTimeSortHandl(val) {
            table.sortCol = "";
            table.sortType = "";

            switch (val) {
                case "1":
                    table.sortCol = "ci.released_dtime desc,ci.info_id";
                    table.sortType = "desc";
                    break;
                case "2":
                    table.sortCol = "ci.released_dtime asc,ci.info_id";
                    table.sortType = "asc";
                    break;
                case "3":
                    table.sortCol = "ci.weight desc,ci.info_id";
                    table.sortType = "desc";
                    break;
                case "4":
                    table.sortCol = "ci.weight asc,ci.info_id";
                    table.sortType = "desc";
                    break;
            }
        }

        //得到所有录入人列表
        function getAllInuptUserID() {
            var m = new Map();
            m.put("site_id", getCurrentFrameObj().site_id);
            var user_list = InfoBaseRPC.getAllInuptUserID(m);
            user_list = List.toJSList(user_list);
            $("#input_user").addOptions(user_list, "user_id", "user_realname");
        }
        function setUserOperate()
        {
            $("#btn299").hide();
            $(":button[id!='btn']").hide();

            if(opt_ids.indexOf(",299,") > -1){
                $("#btn299").show();
                $(".btn299").attr("width","80");
            }


            $(":button[id!='btn']").each(function(){
                var o_id = ","+$(this).attr("id").replace("btn","")+",";
                if(opt_ids.indexOf(o_id) > -1)
                    $(this).show();
            });
            $("#btnSearch").show();

        }
        function doSelect(dep_id,dept_name){
            $("#input_dept").val(dept_name);
        }
	</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" width="90">
				<select id="pageGoNum" name="pageSize" class="input_select width80">

				</select>
			</td>
			<td style=" width:220px;">
				<input type="text" id="cat_tree" value="所属栏目" style="width:204px; height:18px; overflow:hidden;"  readonly="readonly" onclick="showCategoryTree()"/>
				<div id="cat_tree_div1" class="select_div tip hidden border_color" style="width:204px; height:300px; overflow:hidden;border:1px #7f9db9 solid;" >
					<div id="leftMenuBox">
						<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
							<ul id="leftMenuTree1" class="easyui-tree" animate="true" style="width:204px; height:280px;">
							</ul>
						</div>
					</div>
				</div>
			</td>
			<td align="left" valign="middle">
                <span>标题：<span>
                <input id="searchkey" type="text" class="input_text" style="width:150px;" value=""/>
                <span>发起时间：</span>
                <input class="input_text" id="start_time" name="start_time" type="text"
					   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"
					   style="width:80px"/>
                -&nbsp;<input class="input_text" id="end_time" name="end_time" type="text"
							  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})"
							  readonly="readonly" style="width:80px"/>
                <input id="btn" type="button" value="搜索" onclick="searchInfo()"/>
                &nbsp;&nbsp;&nbsp;&nbsp;
					<!--<input id="btnSearch" name="btn6" type="button" onclick="openHighSearchPage()" value="高级搜索"/>-->
			</td>
		</tr>
	</table>
	<span class="blank3"></span>
	<div id="table"></div>
	<div id="turn"></div>

	<div class="infoListTable">
		<table class="table_option" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left" valign="middle">
					<%--<input id="btn303" name="btn1" type="button" onclick="publicSelectCheckbox(table,'info_id','onPass()');" value="通过" />
                    <input id="btn303" name="btn2" type="button" onclick="publicSelectCheckbox(table,'info_id','noPassDesc()');" value="退稿" />
                    <input id="btn301" name="btn3" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />--%>
				</td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>