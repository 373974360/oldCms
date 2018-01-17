<%@ page contentType="text/html; charset=utf-8" %>
<%
    String siteid = request.getParameter("site_id");
    String topnum = request.getParameter("topnum");
    if(topnum == null || topnum.trim().equals("") || topnum.trim().equals("null") ){
        topnum = "0";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>信息维护</title>
    <jsp:include page="include_info_tools.jsp"/>
    <script type="text/javascript" src="js/info_add.js?v=20180117"></script>
    <script type="text/javascript">
        var site_id = "<%=siteid%>";
        var app_id = "cms";
        var contentId = "contents";
        var topnum = "<%=topnum%>";
        $(function(){
            initUeditor(contentId);
            init_input();
            initButtomStyle();
            reloadPublicInfo();
            publicUploadButtomLoad("uploadify",true,false,"",0,5,getReleSiteID(),"savePicUrl");
        });
    </script>
</head>
<body>
<span class="blank12"></span>
<form action="#" name="form1">
    <div id="info_article_table">
        <input type="hidden" id="input_user"/>
        <input type="hidden" id="do_dept"/>
        <table class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th><span class="f_red">*</span>信息标题：</th>
                <td>
                    <input id="title" name="title" type="text" class="width400" value="" onblur="checkInputValue('title',false,240,'信息标题','')"/>
                </td>
            </tr>
            </tbody>
        </table>
        <table class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th>报送单位：</th>
                <td width="150">
                    <div id="a11">
                        <input type="text" id="do_dept_name" value=""
                               style="width:145px; height:18px; overflow:hidden;" readonly/>
                    </div>
                </td>
                <th style="width:89px;">作者：</th>
                <td width="142">
                    <div id="a12">
                        <input type="text" id="author" value="" style="width:141px; height:18px; overflow:hidden;"/>
                    </div>
                </td>
                <td></td>
            </tr>
            </tbody>
        </table>
        <table class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th>报送人：</th>
                <td width="178">
                    <div id="a12">
                        <input type="text" id="editor" value="" style="width:145px; height:18px; overflow:hidden;" readonly/>
                    </div>
                </td>
                <th style="width:66px;"><span class="f_red">*</span>报送时间：</th>
                <td>
                    <input id="released_dtime" name="released_dtime" type="text"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})"
                           readonly="readonly"  onblur="checkInputValue('title',false,240,'报送时间','')"/>
                </td>
            </tr>
            </tbody>
        </table>
        <table class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th>关键词：</th>
                <td>
                    <input id="keywords" name="keywords" type="text" class="width400" maxlength="80" value=""/>
                </td>
                <td></td>
            </tr>
            </tbody>
        </table>
        <table class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th style="vertical-align:top;">内容概述：</th>
                <td>
                    <textarea id="description" name="description" style="width:60%;height:70px"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
        <table class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th style="vertical-align:top;">详细内容：</th>
                <td style="">
                    <script id="contents" type="text/plain" style="width:95%;height:400px;"></script>
                </td>
            </tr>
            <tr>
                <th>缩略图：</th>
                <td>
                    <div style="float:left;margin:auto;">
                        <input id="thumb_url" name="thumb_url" type="text" style="width:250px;" value=""/>
                    </div>
                    <div style="float:left">
                        <input id="i005" name="i005" type="button" onclick="getContentThumb()" value="自动获取"/>
                    </div>
                    <div style="float:left">
                        <input type="file" name="uploadify" id="uploadify"/>
                    </div>
                    <div style="float:left">
                        <input id="i005" name="i005" type="button" onclick="selectPageHandle()" value="图片库"/>
                    </div>
                </td>
            </tr>
            <tr>
                <th></th>
                <td>
                    <div id="thumburldiv" style="float:left;margin:auto; border:1px solid #ccc">
                        <ul id="thumburlList">
                        </ul>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

<!--隔线开始-->
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<!--隔线结束-->
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
<tr>
<td align="left" valign="middle" style="text-indent:100px;">
<input id="addButton" name="btn1" type="button" onclick="addInfoData()" value="保存" />
<input id="addReset" name="btn1" type="button" onclick="window.location.reload()" value="重置" />
<input id="addCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="取消" />
</td>
</tr>
</table>
<span class="blank3"></span>
    </div>
</form>
</body>
</html>