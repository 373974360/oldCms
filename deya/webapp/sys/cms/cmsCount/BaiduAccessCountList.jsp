<%@ page import="com.deya.util.DateUtil" %>
<%@page contentType="text/html; charset=utf-8" %>
<%
    String start_date = DateUtil.getCurrentDateTime("yyyyMMdd");
    String end_date = start_date.substring(0,6)+"01";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>站点信息访问量统计</title>
    <meta name="generator" content="cicro-Builder"/>
    <meta name="author" content="cicro"/>
    <jsp:include page="../../include/include_tools.jsp"/>
    <script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
    <script type="text/javascript" src="../../js/indexjs/tools.js"></script>
    <script type="text/javascript" src="/sys/js/open-flash-chart/js/json2.js"></script>
    <script type="text/javascript" src="/sys/js/open-flash-chart/js/swfobject.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            //今日流量
            outline();
            //top 10 搜索词
            keyWords();
            //top 10 受访页面
            openPage();
            //top 10来源网站
            sourceSite();
            //top 10入口页面
            loadingPage();
            //新老访客
            visitorType();
        });
        function visitorType(){
            var data = {
                indicators:'pv_count',
                method: 'overview/getVisitorType'
            }
            $.get('/sys/cms/cmsCount/BaiduApi.jsp', data, function (result) {
                var json = eval("("+ result +")");
                var newObj = json.body.data[0].result.newVisitor;
                var oldObj = json.body.data[0].result.oldVisitor;
                var _html = "<tr><td></td><td>"+newObj.ratio+"%</td><td>"+oldObj.ratio+"%</td><td width='100'></td></tr>";
                _html += "<tr><td>浏览量</td><td>"+newObj.pv_count+"</td><td>"+oldObj.pv_count+"</td><td width='100'></td></tr>";
                _html += "<tr><td>访客数</td><td>"+newObj.visitor_count+"</td><td>"+oldObj.visitor_count+"</td><td width='100'></td></tr>";
                _html += "<tr><td>跳出率</td><td>"+newObj.bounce_ratio+"%</td><td>"+oldObj.bounce_ratio+"%</td><td width='100'></td></tr>";
                _html += "<tr><td>平均访问时长</td><td>"+formatSeconds(newObj.avg_visit_time)+"</td><td>"+formatSeconds(oldObj.avg_visit_time)+"</td><td width='100'></td></tr>";
                _html += "<tr><td>平均访问页数</td><td>"+newObj.avg_visit_pages+"</td><td>"+oldObj.avg_visit_pages+"</td><td width='100'></td></tr>";
                $("#visitorType").append(_html)
            })
        }
        function sourceSite(){
            var data = {
                indicators:'pv_count',
                method: 'overview/getSourceSite'
            }
            $.get('/sys/cms/cmsCount/BaiduApi.jsp', data, function (result) {
                var json = eval("("+ result +")");
                var _html = "";
                $.each(json.body.data[0].result.items, function(index, items){
                    _html+="<tr><td style='text-align:left;padding-left:15px;'>"+items[0]+"</td><td>"+items[1]+"</td><td>"+items[2]+"%</td><td></td></tr>";
                });
                $("#sourceSite").append(_html)
            })
        }
        function loadingPage(){
            var data = {
                indicators:'pv_count',
                method: 'overview/getLandingPage'
            }
            $.get('/sys/cms/cmsCount/BaiduApi.jsp', data, function (result) {
                var json = eval("("+ result +")");
                var _html = "";
                $.each(json.body.data[0].result.items, function(index, items){
                    _html+="<tr><td style='text-align:left;padding-left:15px;'>"+items[0]+"</td><td>"+items[1]+"</td><td>"+items[2]+"%</td><td></td></tr>";
                });
                $("#loadingPage").append(_html)
            })
        }
        function openPage(){
            var data = {
                indicators:'pv_count',
                method: 'overview/getVisitPage'
            }
            $.get('/sys/cms/cmsCount/BaiduApi.jsp', data, function (result) {
                var json = eval("("+ result +")");
                var _html = "";
                $.each(json.body.data[0].result.items, function(index, items){
                    _html+="<tr><td style='text-align:left;padding-left:15px;'>"+items[0]+"</td><td>"+items[1]+"</td><td>"+items[2]+"%</td><td></td></tr>";
                });
                $("#openPage").append(_html)
            })
        }
        function keyWords(){
            var data = {
                indicators:'pv_count',
                method: 'overview/getWord'
            }
            $.get('/sys/cms/cmsCount/BaiduApi.jsp', data, function (result) {
                var json = eval("("+ result +")");
                var _html = "";
                $.each(json.body.data[0].result.items, function(index, items){
                    _html+="<tr><td style='text-align:left;padding-left:15px;'>"+items[0]+"</td><td>"+items[1]+"</td><td>"+items[2]+"%</td><td></td></tr>";
                });
                $("#keywords").append(_html)
            })
        }
        function outline(){
            var data = {
                method: 'overview/getOutline'
            }
            $.get('/sys/cms/cmsCount/BaiduApi.jsp', data, function (result) {
                var json = eval("("+ result +")");
                var _html = "";
                $.each(json.body.data[0].result.items, function(index, items){
                    if(index==2){
                        _html+="<tr><td>"+items[0]+"</td><td>"+items[1].val+"</td><td>"+items[2].val+"</td><td>"+items[3].val+"</td><td>"+items[4].val+"%</td><td>"+formatSeconds(items[5].val)+"</td><td></td></tr>";
                    }else if(index==5){
                        _html+="<tr><td>"+items[0]+"</td><td title='峰值日："+items[1].date+"'>"+items[1].val+"</td><td title='峰值日："+items[2].date+"'>"+items[2].val+"</td><td title='峰值日："+items[3].date+"'>"+items[3].val+"</td><td title='峰值日："+items[4].date+"'>"+items[4].val+"%</td><td title='峰值日："+items[5].date+"'>"+formatSeconds(items[5].val)+"</td><td></td></tr>";
                    }else{
                        _html+="<tr><td>"+items[0]+"</td><td>"+items[1]+"</td><td>"+items[2]+"</td><td>"+items[3]+"</td><td>"+items[4]+"%</td><td>"+formatSeconds(items[5])+"</td><td></td></tr>";
                    }
                });
                $("#outline").append(_html)
            })
        }
        function formatSeconds(value) {
            if(value=="--"){
                return "--";
            }
            var theTime = parseInt(value);// 秒
            var theTime1 = 0;// 分
            var theTime2 = 0;// 小时
            if(theTime > 60) {
                theTime1 = parseInt(theTime/60);
                theTime = parseInt(theTime%60);
                if(theTime1 > 60) {
                    theTime2 = parseInt(theTime1/60);
                    theTime1 = parseInt(theTime1%60);
                }
            }
            var result = ""+parseInt(theTime)+"秒";
            if(theTime1 > 0) {
                result = ""+parseInt(theTime1)+"分"+result;
            }
            if(theTime2 > 0) {
                result = ""+parseInt(theTime2)+"小时"+result;
            }
            return result;
        }
    </script>
	<style type="text/css">
        .table_option td{text-align: right;}
        .table_option tr:hover{cursor:pointer;background: #73B1E0;}
        .table_option tr:nth-child(odd){background:#FFF;}
        .gaikuang{height:210px;border:1px solid #CCC;width:98%;}
        .gaikuang1{height:300px;border:1px solid #CCC;float:left;width:49%;}
        .overview-card-title {border-bottom:1px solid #CCC;height: 24px;font-weight: bold;padding-left: 10px;line-height: 24px;background: url(/sys/images/sq_title_bg.gif) 0 -4px repeat-x;cursor: pointer;}
    </style>
</head>
<body>
    <div class="gaikuang">
        <div class="overview-card-title">今日流量</div>
        <table id="outline" class="table_option" border="0" cellpadding="0" cellspacing="0" >
            <tr>
                <td></td>
                <td>浏览量(PV)</td>
                <td>浏览量(UV)</td>
                <td>IP数</td>
                <td>跳出率</td>
                <td>平均访问时长</td>
                <td width="100"></td>
            </tr>
        </table>
    </div>
    <div class="gaikuang1">
        <div class="overview-card-title">Top10搜索词</div>
        <table id="keywords" class="table_option" border="0" cellpadding="0" cellspacing="0" >
            <tr>
                <td style='text-align:left;padding-left:15px;'>搜索词</td>
                <td>浏览量(PV)</td>
                <td>占比</td>
                <td width="100"></td>
            </tr>
        </table>
    </div>
    <div class="gaikuang1">
        <div class="overview-card-title">Top10来源网站</div>
        <table id="sourceSite" class="table_option" border="0" cellpadding="0" cellspacing="0" >
            <tr>
                <td style='text-align:left;padding-left:15px;'>来源网站</td>
                <td>浏览量(PV)</td>
                <td>占比</td>
                <td width="100"></td>
            </tr>
        </table>
    </div>
    <div class="gaikuang1">
        <div class="overview-card-title">Top10入口页面</div>
        <table id="loadingPage" class="table_option" border="0" cellpadding="0" cellspacing="0" >
            <tr>
                <td style='text-align:left;padding-left:15px;'>入口页面</td>
                <td>浏览量(PV)</td>
                <td>占比</td>
                <td width="100"></td>
            </tr>
        </table>
    </div>
    <div class="gaikuang1">
        <div class="overview-card-title">Top10受访页面</div>
        <table id="openPage" class="table_option" border="0" cellpadding="0" cellspacing="0" >
            <tr>
                <td style='text-align:left;padding-left:15px;'>受访页面</td>
                <td>浏览量(PV)</td>
                <td>占比</td>
                <td width="100"></td>
            </tr>
        </table>
    </div>
</body>
</html>
