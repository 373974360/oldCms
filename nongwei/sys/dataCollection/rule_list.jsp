<%@ page contentType="text/html; charset=utf-8"%>
<%
    String site_id = request.getParameter("site_id");
    site_id = (site_id==null) ? "" : site_id;

    String app_id = request.getParameter("app_id");
    String id = request.getParameter("id");
    if(id == null)
    {
        id = "0";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>采集规则管理</title>
<meta name="generator" content="cicro-Builder" />
<meta name="ware" content="cicro" />
<jsp:include page="../include/include_tools.jsp" />
<link rel="stylesheet" type="text/css" href="../styles/themes/default/tree.css" />
<script type="text/javascript" src="../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/rule_list.js"></script>
<script type="text/javascript">
    var app_id = "<%=app_id%>";
    var site_id = "<%=site_id%>";
    var id = "<%=id%>";

    con_m.put("app_id",app_id);
    con_m.put("site_id",site_id);

    $(document).ready(function(){
        initButtomStyle();
        init_FromTabsStyle();
        if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height())
            $("html").css("overflowY","scroll");

        //设置左侧Tree
        setLeftTreeHeight();
        showRuleCateTree();

        initTable();
        initCollState();
    });

    function initCollState(){
        var RuleIdlist = CollectionDataRPC.getAllCollIsRuning();
        if(RuleIdlist!=null){
            RuleIdlist = List.toJSList(RuleIdlist);
            for(var i=0;i<RuleIdlist.size();i++){
                $("#collstate_"+RuleIdlist.get(i)).html("<font color='red'>开始</font>");
            }
        }
    }

    function showRuleCateTree()
    {
        json_data = eval(CollectionDataRPC.getJSONTreeBySiteUser(top.LoginUserBean.user_id,site_id));
        setLeftMenuTreeJsonData(json_data);
        initMenuTree();
        treeNodeSelected(id);
    }

    function initMenuTree()
    {
        $('#leftMenuTree').tree({
            onClick:function(node)
            {
                changeOperateListTable(node.id);
            }
        });
    }

    // 点击左侧树事件
    function changeOperateListTable(o_id)
    {
        id = o_id;
        loadRuleTable();
    }

</script>
</head>
<body>
<table style="width:100%" border="0" cellpadding="0" cellspacing="0" >
    <tr>
        <td width="200px" valign="top">
            <div id="leftMenuBox">
                <div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
                    <ul id="leftMenuTree" class="easyui-tree" animate="true">
                    </ul>
                </div>
            </div>
        </td>
        <td class="width10"></td>
        <td valign="top">
            <div>
                <div id="rule_table"></div>
                <div id="rule_turn"></div>
                <table class="table_option" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td align="left" valign="middle">
                            <input id="userAddCancel" name="btn1" type="button" onclick="openaddRuleTabPage()" value="添加" />
                            <input id="userAddCancel" name="btn1" type="button" onclick="deleteRecord(table,'id','deleteRuleByid()');" value="删除" />
                            <input id="userAddCancel" name="btn1" type="button" onclick="updateRecord(table,'id','updateRuleById()')" value="修改" />
                        </td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
</table>
</body>
</html>