<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>楼宇信息列表</title>
    <jsp:include page="../../include/include_tools.jsp"/>
    <link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css"/>
    <script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../js/indexjs/tools.js"></script>
    <script src="js/louyuList.js"></script>
    <script type="text/javascript">
        var lpcode = request.getParameter("lpcode");
        if(lpcode==null){
            lpcode="";
        }
        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");

            //设置左侧Tree
            setLeftTreeHeight();
            showRuleCateTree();

            initTable();
            reloadLouYuList();
        });
        function showRuleCateTree() {
            json_data = eval(LouPanRPC.getLouPanTreeList());
            setLeftMenuTreeJsonData(json_data);
            initMenuTree();
            treeNodeSelected(lpcode);
        }
        function initMenuTree() {
            $('#leftMenuTree').tree({
                onClick: function (node) {
                    changeOperateListTable(node.id);
                }
            });
        }
        // 点击左侧树事件
        function changeOperateListTable(node_id) {
            lpcode = node_id;
            reloadLouYuList();
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
                    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td class="fromTabs">
                                <input id="btn1" name="btn1" type="button" onclick="insertLouYuPage()" value="添加"/>
                                <input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'id','updateLouYuPage()');" value="修改"/>
                                <input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'id','deleteLouYu()')" value="删除"/>
                                <span class="blank3"></span>
                            </td>
                            <td align="right" valign="middle" id="dept_search" class="search_td fromTabs">
                                <input id="searchkey" type="text" class="input_text" value=""/>
                                <input id="btnSearch" type="button"  class="btn x2" value="搜索" onclick="searchHandl(this)"/>
                                <span class="blank3"></span>
                            </td>
                        </tr>
                    </table>
                    <span class="blank3"></span>
                    <div id="table"></div><!-- 列表DIV -->
                    <div id="turn"></div><!-- 翻页DIV -->
                </div>
            </td>
        </tr>
    </table>
</body>
</html>
