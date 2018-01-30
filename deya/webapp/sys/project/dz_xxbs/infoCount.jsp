<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.deya.util.DateUtil" %>
<%@page import="java.util.Date" %>
<%
    String start_day = request.getParameter("start_day");
    String end_day = request.getParameter("end_day");

    String now = DateUtil.getCurrentDate();
    String now1 = now.substring(0, 7) + "-01";
    if (start_day == null || "".equals(start_day)) {
        start_day = now1;
    }
    if (end_day == null || "".equals(end_day)) {
        end_day = now;
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>目录管理</title>
    <jsp:include page="../../include/include_tools.jsp"/>
    <script type="text/javascript" src="../../js/indexjs/indexList.js"></script>
    <script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="../../js/indexjs/tools.js"></script>
    <script type="text/javascript">
        var DeptRPC = jsonrpc.DeptRPC;
        var XxbsRPC = jsonrpc.XxbsRPC;
        var deptBean = DeptRPC.getDeptBeanByID(LoginUserBean.dept_id);
        var table = new Table();
        $(function () {
            $("#searchBtn").click(function () {
                var m = new Map();
                m.put("dept_id", deptBean.dept_id + '');
                m.put("start_time", $("#start_day").val());
                m.put("end_time", $("#end_day").val());
                beanList = XxbsRPC.getXxbsDeptCount(m);
                beanList = List.toJSList(beanList);//把list转成JS的List对象
                $("#countList tbody").empty();
                console.log(beanList);
                for(var i=0;i<beanList.size();i++){
                    $("#countList tbody").append("<tr class='initialized'>" +
                        "<td><span class='file\'>"+beanList.get(i).map.deptName+"</span></td>" +
                        "<td>"+beanList.get(i).map.deptCount_d+"</td>" +
                        "<td>"+beanList.get(i).map.deptCount_a+"</td>" +
                        "<td>"+beanList.get(i).map.deptCount_b+"</td>" +
                        "<td>"+beanList.get(i).map.deptCount_c+"</td></tr>");
                }
            });
        });
    </script>
</head>
<body>
<table style="width:100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td colspan="3" class="search_td fromTabs">
            <div style="height:auto; border:#A00 0px;">
                <div id="defauleTime" style="float:left;">

                    <input class="Wdate" type="text" name="start_day" id="start_day" size="11"
                           style="height:16px;line-height:16px;"
                           value="<%=start_day%>" onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd'})"
                           readonly/>
                    --
                    <input class="Wdate" type="text" name="end_day" id="end_day" size="11"
                           style="height:16px;line-height:16px;"
                           value="<%=end_day%>" onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd'})"
                           readonly/>

                    <input type="button" id="searchBtn" value="统计"/>

                </div>
            </div>
        </td>
    </tr>
</table>
<table id="countList" class="treeTableCSS table_border treeTable" cellspacing="0" cellpadding="0" border="0">
    <thead>
    <tr>
        <th>报送单位</th>
        <th>报送总数</th>
        <th>未采用</th>
        <th>已采用</th>
        <th>已退稿</th>
    </tr>
    </thead>
    <tbody>

    </tbody>
    <tfoot>
    <tr>
        <td colspan="5"></td>
    </tr>
    </tfoot>
</table>
</body>
</html>
