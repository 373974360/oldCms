<%@page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>目录管理</title>
<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css" />
<style type="text/css">
.checkBox_mid{ vertical-align:middle; margin-right:10px;};
#v_type{height:50px;}
.checkBox_text{ vertical-align:text-top}
.span_left{ margin-left:14px;}
</style>
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="../../js/open-flash-chart/js/json2.js"></script>
<script type="text/javascript" src="../../js/open-flash-chart/js/swfobject.js"></script>
<script type="text/javascript">
    var SiteRPC = jsonrpc.SiteRPC;
    var KblmRPC = jsonrpc.KblmRPC;
    var beanList = null;
    $(document).ready(function(){
        initButtomStyle();
        init_FromTabsStyle();
        if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
        var siteList = SiteRPC.getSiteChildListByID("HIWCMcgroup");
        siteList = List.toJSList(siteList);
        for(var i=0;i<siteList.size();i++){
            $("#site_id").append("<option value=\""+siteList.get(i).site_id+"\">"+siteList.get(i).site_name+"</option>");
        }
    });
    function showList(){
        var site_id = $("#site_id").val();
        if(site_id==""){
            top.msgWargin("请选择站点");
            return;
        }
        $("#countList").empty();
        $("#countList").append("<tr style=\"height:32px;line-height:32px;\"><td colspan=\"3\">数据正在统计中，请稍候...</td></tr>");
        var mp = new Map();
        mp.put("site_id",site_id);
        beanList = KblmRPC.getNullCategoryByCount(mp);
        beanList = List.toJSList(beanList);
        createTableCate();

        // chart处理
        //setBarChart();

        $("#outFileBtn").show();
    }
    //生成内容表格
    function createTableCate(){
        $("#countList").empty();
        var treeHtmls = "<thead>" +
            "<tr> " +
            "   <th width='40%'>栏目</th>		" +
            "   <th width='20%'>全部信息</th>       " +
            "   <th width='20%'>已发信息</th>   " +
            "   <th width='10%'>图片信息</th>   " +
            "	<th width='10%'>发稿率</th> " +
            " </tr>" +
            "</thead>" +
            "<tbody>" ;
        if(beanList.size() != 0) {
            for(var i=0;i<beanList.size();i++){
                treeHtmls += setTreeNode(beanList.get(i), "",0);
            }
            treeHtmls += "</tbody>"+
                "<tfoot>" +
                "<td colspan=\"5\"></td>"+ " </tfoot>";
        } else {
            treeHtmls += "<tr><td colspan=\"5\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"5\"></td>"+" </tfoot>";
        }
        $("#countList").append(treeHtmls);
        iniTreeTable("countList");
    }
    //组织树列表选项字符串
    function setTreeNode(bean, parent_id,child)
    {
            //var index = pieChartDataList.size() + 1;
            var treeHtml = "";

            if(bean.is_leaf)
            {
                var type_calss= "";
                if(parent_id !=""){
                    type_calss = "class='child-of-node-"+parent_id+"'"
                }
                if(bean.inputCount==0){
                    type_calss = "class='child-of-node-"+parent_id+" selected'"
                }
                //alert("parent_id=="+parent_id+":::is_leaf==" + bean.cat_id);
                treeHtml+="<tr id='node-"+bean.cat_id+"' "+type_calss+" > <td><span class='file'>"+bean.cat_name+"</span></td>";
                treeHtml += setHandlList(bean);
                treeHtml+="</tr>";
            }
            else
            {
                var type_calss = "";
                if(parent_id !=""){
                    type_calss = "class='child-of-node-"+parent_id+"'"
                }
                if(bean.inputCount==0){
                    type_calss = "class='child-of-node-"+parent_id+" selected'"
                }
                //alert("parent_id=="+parent_id+":::not is_leaf==" + bean.cat_id);
                treeHtml+="<tr id='node-"+bean.cat_id+"' "+type_calss+" > <td><span class='folder'>"+bean.cat_name+"</span></td>";
                treeHtml += setHandlList(bean);
                treeHtml+="</tr>";
                var child_list = bean.child_list;
                child_list = List.toJSList(child_list);
                if(child_list.size() > 0)
                {
                    for(var i=0;i<child_list.size();i++)
                    {
                        treeHtml += setTreeNode(child_list.get(i), bean.cat_id+"",1);
                    }
                }
            }
        return treeHtml;
    }
    // 组织统计的数据
    function setHandlList(bean)
    {
        var str = "";
        if(bean)
        {
            str+="<td>"+bean.inputCount +"</td>";
            str+="<td>"+bean.releasedCount +"</td>";
            str+="<td>"+bean.picReleasedCount +"</td>";
            str+="<td>"+bean.releaseRate +"</td>";
        }
        return str;
    }
</script>
</head>
<body>
<table style="width:500px;margin:0px;" border="0" cellpadding="0" cellspacing="0" >
    <tr>
    	<td>
        	<span class="f_red">*</span>选择站点:
        </td>
        <td>
        <div style="float:left">
            <select id="site_id" class="input_select" style="width:230px;">
                <option value="">请选择站点</option>
            </select>
        </div>
        <div style="float:left;margin-left:8px;">
        	<input type="button" id="searchBtn"  onclick="showList()" value="统计"/>
        </div>
        </td>
    </tr>
</table>
<span class="blank3"></span>
<table width="100%">
    <tr valign="top" >
        <td>
            <div id="chart">

            </div>
        </td>
    </tr>
    <tr valign="top">
        <td>
            <div id="count">
                <table id="countList" class="treeTableCSS table_border"  border="0" cellpadding="0" cellspacing="0" width="100%">
                </table>
            </div>
        </td>
    </tr>
</table>
</body>
</html>