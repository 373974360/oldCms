<%@ page contentType="text/html; charset=utf-8"%>
<%
	String cid = request.getParameter("cat_id");
	String siteid = request.getParameter("site_id");
	String app_id = request.getParameter("app_id");
	if(siteid == null || siteid.equals("null")){
		siteid = "GK";
	}
	if(app_id == null || app_id.trim().equals("")){
		app_id = "cms";
	}
	String snum = request.getParameter("snum");
	if(snum == null || snum.trim().equals("") || snum.trim().equals("null") ){
		snum = "0";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>信息管理</title>
	<link type="text/css" rel="stylesheet" href="../../../styles/themes/default/tree.css" />
	<link type="text/css" rel="stylesheet" href="../../../styles/sq.css" />


	<jsp:include page="../../../include/include_tools.jsp"/>
	<script type="text/javascript" src="../../../js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../../../js/indexjs/indexList.js"></script>
	<script type="text/javascript" src="../../../js/indexjs/tools.js"></script>
	<script type="text/javascript" src="js/info.js?v=20170412"></script>

	<script type="text/javascript">

        var site_id = "<%=siteid%>";
        var app = "<%=app_id%>";
        var cid = "<%=cid%>";
        var snum = "<%=snum%>";
        var opt_ids = ","+getOptIDSByUser(app,site_id)+",";//登录人所拥有管理权限ID
        var gk_article = false;//特殊栏目标识，在政务公开中使用的是内容管理中的文章模型

        $(document).ready(function(){
            if(cid == 10 || cid == 11 || cid == 12 || app == "ggfw")
            {
                gk_article = true;
                //特殊栏目，不需要设置权限
                $("#btn305").hide();
                $("#btnSearch").hide();
                //$(".list_tab").eq(2).hide();
                $(".list_tab").eq(3).hide();
                $(".list_tab").eq(4).hide();
                $(".list_tab").eq(5).hide();
            }
            else
                setUserOperate();

            isSubNode(cid);

            initButtomStyle();
            initTabAndStatus();
            if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");

            $(".list_tab").eq(snum).click();

            showModels();
            if(subNode == false)
            {
                $(".x_add").click(
                    function (event) {
                        openAddInfoPage($(event.target).attr("value"));
                    }
                );
            }else
            {
                $(".infoadd_area").hide();
                $(":button[id='btn404']").hide();
                $(":button[id='btn304']").hide();
            }

            if(app == "zwgk")
            {
                //$(":button[id='btn306']").hide();
                $(":button[id='btn404']").hide();
                $(":button[id='btn304']").hide();
            }

            sso_method_cookie_value = request.getCookie(sso_method_cookie_name);
            if(sso_method_cookie_value != null && sso_method_cookie_value != ""){
                //添加信息
                if("addInfo" == sso_method_cookie_value){
                    $(".addInfo").css("display","inline-block");
                    $(".list_tab").eq(0).click();
                }

                //审核中信息
                if("checkingInfo" == sso_method_cookie_value){
                    $(".checkingInfo").css("display","inline-block");
                    $(".btn299").removeAttr("width");
                    $(".list_tab").eq(3).click();
                }

                //发布信息
                if("publishInfo" == sso_method_cookie_value){
                    $(".publishInfo").css("display","inline-block");
                    $(".list_tab").eq(8).click();
                    $(":button[id='btn404']").hide();
                    $(":button[id='btn305']").hide();
                    $(":button[id='btn306']").hide();
                    $(":button[id='btn307']").hide();
                    $(":button[id='btn332']").hide();
                    $(":button[id='btn999']").hide();
                    $(":button[id='btn302']").show();
                }

                //修改信息
                if("modifyInfo" == sso_method_cookie_value){
                    $(".modifyInfo").css("display","inline-block");
                    $(".btn299").removeAttr("width");
                    $(".list_tab").eq(0).click();
                }
                //删除信息
                if("delInfo" == sso_method_cookie_value){
                    $(".delInfo").css("display","inline-block");
                    $(".btn299").removeAttr("width");
                    $(".list_tab").eq(0).click();
                    $(".infoListTable").removeClass("hidden");
                    $(":button[id='btn404']").hide();
                    $(":button[id='btn305']").hide();
                    $(":button[id='btn306']").hide();
                    $(":button[id='btn307']").hide();
                    $(":button[id='btn999']").hide();
                }
                //撤销信息
                if("unPublishInfo" == sso_method_cookie_value){
                    $(".unPublishInfo").css("display","inline-block");
                    $(".btn299").removeAttr("width");
                    $(".list_tab").eq(0).click();
                    $(".infoListTable").removeClass("hidden");
                    $(":button[id='btn404']").hide();
                    $(":button[id='btn305']").hide();
                    $(":button[id='btn306']").hide();
                    $(":button[id='btn332']").hide();
                    $(":button[id='btn999']").hide();
                }
                //已撤销信息
                if("unPublishedInfo" == sso_method_cookie_value){
                    $(".unPublishedInfo").css("display","inline-block");
                    $(".btn299").removeAttr("width");
                    $(".list_tab").eq(1).click();
                    $(".infoListTable").removeClass("hidden");
                    $(":button[id='btn404']").hide();
                    $(":button[id='btn305']").hide();
                    $(":button[id='btn306']").hide();
                    $(":button[id='btn332']").hide();
                    $(":button[id='btn999']").hide();
                }
                //审核信息
                if("checkInfo" == sso_method_cookie_value){
                    $(".checkInfo").css("display","inline-block");
                    $(".btn299").removeAttr("width");
                    $(".list_tab").eq(2).click();
                }
                //退稿信息
                if("noPassInfo" == sso_method_cookie_value){
                    $(".noPassInfo").css("display","inline-block");
                    $(".btn299").removeAttr("width");
                    $(".list_tab").eq(4).click();
                }
                //草稿信息
                if("drafgInfo" == sso_method_cookie_value){
                    $(".drafgInfo").css("display","inline-block");
                    $(".btn299").removeAttr("width");
                    $(".list_tab").eq(5).click();
                }
                //回收站信息
                if("rubbyInfo" == sso_method_cookie_value){
                    $(".rubbyInfo").css("display","inline-block");
                    $(".btn299").removeAttr("width");
                    $(".list_tab").eq(6).click();
                }
                //获取信息
                if("getInfo" == sso_method_cookie_value){
                    $(".getInfo").css("display","inline-block");
                    $(".btn299").removeAttr("width");
                    $(".infoListTable").removeClass("hidden");
                    $(":button[id='btn305']").hide();
                    $(":button[id='btn306']").hide();
                    $(":button[id='btn307']").hide();
                    $(":button[id='btn332']").hide();
                    $(":button[id='btn999']").hide();
                }
                //移动信息
                if("moveInfo" == sso_method_cookie_value){
                    $(".moveInfo").css("display","inline-block");
                    $(".btn299").removeAttr("width");
                    $(".infoListTable").removeClass("hidden");
                    $(":button[id='btn404']").hide();
                    $(":button[id='btn305']").hide();
                    $(":button[id='btn307']").hide();
                    $(":button[id='btn332']").hide();
                    $(":button[id='btn999']").hide();
                }
                //推送信息
                if("pushInfo" == sso_method_cookie_value){
                    $(".pushInfo").css("display","inline-block");
                    $(".btn299").removeAttr("width");
                    $(".infoListTable").removeClass("hidden");
                    $(":button[id='btn404']").hide();
                    $(":button[id='btn306']").hide();
                    $(":button[id='btn307']").hide();
                    $(":button[id='btn332']").hide();
                    $(":button[id='btn999']").hide();
                }

                //归档信息
                if("backInfo" == sso_method_cookie_value){
                    $(".list_tab").eq(7).click();
                    $(".btn299").removeAttr("width");
                    $(".backInfo").css("display","inline-block");
                    $(".infoListTable").removeClass("hidden");
                    $(":button[id='btn404']").hide();//获取
                    $(":button[id='btn305']").hide();//推送
                    $(":button[id='btn306']").hide();//逸动
                    $(":button[id='btn307']").hide();//撤销
                    $(":button[id='btn332']").hide();//删除
                    $(":button[id='btn302']").hide();//发布
                }
            }


        });

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

        function openSendInfoPage()
        {
            OpenModalWindow("信息报送","/sys/cms/info/article/getReceiveSite.jsp?site_id="+site_id+"&app_id="+app,570,420);
        }

        function getSelectInfoBeans()
        {
            return table.getSelecteBeans();
        }
	</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>
			<td align="left" width="180">
				<select id="pageGoNum" name="pageSize" class="input_select width80" onchange="changeFactor()">

				</select>
				<select id="searchTimes" class="input_select width80" onchange="changeFactor2()">
					<option selected="selected" value="0b">日期</option>
					<option value="1b">今日</option>
					<option value="2b">昨日</option>
					<option value="3b">一周内</option>
					<option value="4b">一月内</option>
				</select>
			</td>
			<td align="left" class="btn299">
				<ul class="infoadd_area" id="btn299">
					<li class="x_add addInfo" style="display: none;" value="0" >
						<ul class="MUL" id="addLabList">
						</ul>
					</li>
				</ul>

			</td>

			<td align="left" valign="middle" >

				<input id="searchkey" type="text" class="input_text" style="width:240px;" value=""  /><input id="btn" type="button" value="搜索" onclick="searchInfo()"/>
				<select id="orderByFields" class="input_select hidden" onchange="changeTimeSort(this.value)">
					<option selected="selected" value="1">时间倒序</option>
					<option value="2">时间正序</option>
					<option value="3">权重倒序</option>
					<option value="4">权重正序</option>
				</select>

				&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="btnSearch" name="btn6" type="button" onclick="openHighSearchPage()" value="高级搜索" />
			</td>
		</tr>
	</table>
	<div>
		<ul class="fromTabs">
			<li class="hidden list_tab list_tab_cur addInfo delInfo unPublishInfo modifyInfo getInfo moveInfo pushInfo">
				<!--<div class="tab_left">
                    <div class="tab_right">已发</div>
                </div>-->
			</li>
			<li class="hidden list_tab  unPublishedInfo">
				<!--<div class="tab_left">
                    <div class="tab_right">已撤</div>
                </div>-->
			</li>
			<li class=" hidden list_tab checkInfo">
				<!--<div class="tab_left">
                    <div class="tab_right">待审</div>
                </div>-->
			</li>
			<li class="hidden list_tab checkingInfo">
				<!--<div class="tab_left">
                    <div class="tab_right">审核中</div>
                </div>-->
			</li>
			<li class="hidden list_tab noPassInfo">
				<!--<div class="tab_left">
                    <div class="tab_right">退稿</div>
                </div>-->
			</li>
			<li class="hidden list_tab drafgInfo">
				<!--<div class="tab_left">
                    <div class="tab_right">草稿</div>
                </div>-->
			</li>
			<li class="hidden list_tab rubbyInfo">
				<!--<div class="tab_left">
                    <div class="tab_right">回收站</div>
                </div>-->
			</li>
			<li class="hidden list_tab backInfo">
				<!--<div class="tab_left">
                    <div class="tab_right">待归档</div>
                </div>-->
			</li>
			<li class="hidden list_tab  publishedInfo">
				<%--<div class="tab_left">
                    <div class="tab_right">待发</div>
                </div>--%>
			</li>
		</ul>
	</div>
	<span class="blank9"></span>
	<div id="table"></div>
	<div id="turn"></div>
	<div class="infoListTable">
		<table class="table_option" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left" valign="middle">

					<input class="getInfo" id="btn404" name="btn1" type="button" onclick="getInfoFromOtherCat();" value="获取" />
					<input class="pushInfo" id="btn305" name="btn2" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForPush()');" value="推送" />
					<input class="moveInfo" id="btn306" name="btn3" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForMov()');" value="移动" />
					<input class="publishInfo" id="btn302" name="btn1" type="button" onclick="publicSelectCheckbox(table,'info_id','publishInfo()')" value="发布" />
					<input class="unPublishInfo" id="btn307" name="btn5" type="button" onclick="cancleInfo()" value="撤销" />
					<input class="delInfo" id="btn332" name="btn4" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />
					<input class="backInfo" id="btn999" name="btn9" type="button" onclick="backRecord(table,'info_id','backInfoData()');" value="归档" />
				</td>
			</tr>
		</table>
	</div>
	<!--
    <div class="infoListTable hidden" id="listTable_1">
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle">
                <input id="btn302" name="btn1" type="button" onclick="publicSelectCheckbox(table,'info_id','publishInfo()')" value="发布" />
                <input id="btn404" name="btn1" type="button" onclick="getInfoFromOtherCat();" value="获取" />
                <input id="btn305" name="btn2" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForPush()');" value="推送" />
                <input id="btn424" name="btn2" type="button" onclick="publicSelectCheckbox(table,'info_id','openSendInfoPage()');" value="站群报送" />
                <input id="btn306" name="btn3" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForMov()');" value="移动" />
                <input id="btn332" name="btn4" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />
                &nbsp;&nbsp;
                <input id="btnSearch" name="btn6" type="button" onclick="openHighSearchPage()" value="高级搜索" />
            </td>
        </tr>
    </table>
    </div>
    -->
	<%--<div class="infoListTable hidden" id="listTable_1">
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle">
                <input id="btn302" name="btn1" type="button" onclick="publicSelectCheckbox(table,'info_id','publishInfo()')" value="发布" />
                <input id="btn305" name="btn2" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForPush()');" value="推送" />
                <input id="btn306" name="btn3" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForMov()');" value="移动" />
                <input id="btn332" name="btn3" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />
                &nbsp;&nbsp;
                <input id="btnSearch" name="btn6" type="button" onclick="openHighSearchPage()" value="高级搜索" />
            </td>
        </tr>
    </table>
    </div> --%>

	<%--<div class="infoListTable hidden" id="listTable_2">
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle">
                <input id="btn303" name="btn1" type="button" onclick="publicSelectCheckbox(table,'info_id','onPass()');" value="通过" />
                <input id="btn303" name="btn2" type="button" onclick="publicSelectCheckbox(table,'info_id','noPassDesc()');" value="退稿" />
                <input id="btn305" name="btn2" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForPush()');" value="推送" />
                <input id="btn306" name="btn3" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForMov()');" value="移动" />
                <input id="btn332" name="btn3" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />
                &nbsp;&nbsp;
                <input id="btnSearch" name="btn6" type="button" onclick="openHighSearchPage()" value="高级搜索" />
            </td>
        </tr>
    </table>
    </div> --%>

	<%--<div class="infoListTable hidden" id="listTable_3">
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle">

                <input id="btnSearch" name="btn6" type="button" onclick="openHighSearchPage()" value="高级搜索" />
            </td>
        </tr>
    </table>
    </div> --%>

	<%--<div class="infoListTable hidden" id="listTable_4">
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle">
                <input id="btn" name="btn1" type="button" onclick="publicSelectCheckbox(table,'info_id','toPass()');" value="送审" />
                <input id="btn306" name="btn3" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForMov()');" value="移动" />
                <input id="btn332" name="btn3" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />
                &nbsp;&nbsp;
                <input id="btnSearch" name="btn6" type="button" onclick="openHighSearchPage()" value="高级搜索" />
            </td>
        </tr>
    </table>
    </div> --%>

	<%--<div class="infoListTable hidden" id="listTable_5">
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle">
                <input id="btn" name="btn1" type="button" onclick="publicSelectCheckbox(table,'info_id','toPass()');" value="送审" />
                <input id="btn305" name="btn2" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForPush()');" value="推送" />
                <input id="btn306" name="btn3" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForMov()');" value="移动" />
                <input id="btn332" name="btn3" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />
                &nbsp;&nbsp;
                <input id="btnSearch" name="btn6" type="button" onclick="openHighSearchPage()" value="高级搜索" />
            </td>
        </tr>
    </table>
    </div> --%>

	<%--<div class="infoListTable hidden" id="listTable_6">
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle">
                <input id="btn308" name="btn1" type="button" onclick="publicSelectCheckbox(table,'info_id','rebackInfo()');" value="还原" />
                <input id="btn309" name="btn3" type="button" onclick="deleteRecord(table,'info_id','realDelInfo()');" value="彻底删除" />
                <input id="btn310" name="btn3" type="button" onclick="clearAllInfo();" value="清空回收站" />
                &nbsp;&nbsp;
                <input id="btnSearch" name="btn6" type="button" onclick="openHighSearchPage()" value="高级搜索" />
            </td>
        </tr>
    </table>
    </div>--%>

</div>
</body>
</html>

