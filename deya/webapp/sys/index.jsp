<%@ page contentType="text/html; charset=utf-8"%>
<%
	String method = request.getParameter("method");
	String menuUrl = request.getParameter("menuUrl");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>政务信息管理平台</title>


	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<link type="text/css" rel="stylesheet" href="styles/main.css" />
	<link type="text/css" rel="stylesheet" href="styles/index.css" />
	<link rel="stylesheet" type="text/css" href="styles/themes/default/tree.css">
	<link rel="stylesheet" type="text/css" href="styles/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-ui/themes/base/jquery.ui.all.css"  />
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jTimers.js"></script>
	<script type="text/javascript" src="js/jquery-plugin/jquery.cookie.js"></script>
	<script type="text/javascript" src="js/jquery-ui/jquery.effects.core.js"></script>
	<script type="text/javascript" src="js/jquery-ui/jquery.effects.blind.js"></script>
	<!--<script type="text/javascript" src="js/jquery-easyui/jquery.tree.js"></script>-->
	<script type="text/javascript" src="js/jquery-easyui/jquery.easyui.min.js"></script>

	<script type="text/javascript" src="js/jquery-ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="js/jquery-ui/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="js/jquery-ui/jquery.ui.mouse.js"></script>
	<script type="text/javascript" src="js/jquery-ui/jquery.ui.button.js"></script>
	<script type="text/javascript" src="js/jquery-ui/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="js/jquery-ui/jquery.ui.position.js"></script>
	<script type="text/javascript" src="js/jquery-ui/jquery.ui.dialog.js"></script>
	<script type="text/javascript" src="js/tools.js"></script>
	<script type="text/javascript" src="js/sysUI.js"></script>
	<script type="text/javascript" src="js/java.js"></script>
	<script type="text/javascript" src="js/extend.js"></script>
	<script type="text/javascript" src="js/jsonrpc.js"></script>
	<script type="text/javascript" src="js/jquery.c.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/validator.js"></script>
	<script type="text/javascript" src="js/indexjs/indexList.js"></script>
	<script type="text/javascript" src="js/indexjs/tools.js"></script>

	<script type="text/javascript">

        var curTreeID = "";	//防止重复刷新左侧树节点的临时变量，记录当树根ID
        var curTabIndex = 1;

        var jsonData;
        var chold_jData;

        var dept_jdata;

        var method = "<%=method%>";

        var menuUrl = "<%=menuUrl%>";

        var pageName = "页面";

        $(document).ready(function(){

            $("#sysname").html('<img alt="" src="images/system_name.png" />');


            iniBannerTake();
            init_page();
            iniBannerTakeUpDown();
            $("#user_name").text(LoginUserBean.user_realname);
            $("#curDate").html(getDateAndWeek());

            //加载栏目数据 menuListToJsonStrByUserID 参数,第一个为用户ID,第2个为要展示的第一级系统菜单,如此为组织机构的,那它对应的就是2,如果显示全部的,输入空

            addTab(false);
            setMainIframeUrl("space.html","Home");

            jsonData = eval(UserLogin.menuListToJsonStrByUserID(LoginUserBean.user_id,""));
            if(jsonData.length > 0){
                chold_jData = jsonData[0].children;
			}
            //我的平台树字符串
            myPlatform_jsonData = eval(UserLogin.getMyPlatformTreeStr());

            initTopMenu();
            try{
                iniLeftMenuTree();
            }catch(e){
                window.location.reload();
            }

            iniMenu();
            //iniMenuOnMouseOver();
            initButtomStyle();

            initLastSelectMenu();//选中用户最后一次选择的菜单

            //createSiteMenu();



            //初始化默认点击站点内容管理
            $('#top_menu #6').click();

            //这里判断method，并且写入cookie
            if(method != null && method != "null"){
                request.setCookie(sso_method_cookie_name,method);
            }

            if(menuUrl != null && menuUrl != "null"){
                $('#area_left').css("display","none");
                if(menuUrl.indexOf("verifyInfoList") >= 0){
                    pageName = "审核信息";
                }
                if(menuUrl.indexOf("authorDataList") >= 0){
                    pageName = "作者管理";
                }
                if(menuUrl.indexOf("sourceDataList") >= 0){
                    pageName = "来源管理";
                }
                if(menuUrl.indexOf("managerList") >= 0){
                    pageName = "素材管理";
                }
                if(menuUrl.indexOf("cmsCountList") >= 0){
                    pageName = "信息统计";
                }
                if(menuUrl.indexOf("cmsAssessList") >= 0){
                    pageName = "工作量考核";
                }
                if(menuUrl.indexOf("cmsUpdateList") >= 0){
                    pageName = "信息更新统计";
                }
                if(menuUrl.indexOf("categoryList") >= 0){
                    pageName = "目录管理";
                }
                if(menuUrl.indexOf("pageList") >= 0){
                    pageName = "页面管理";
                }
                if(menuUrl.indexOf("roleList") >= 0){
                    pageName = "站点角色管理";
                }
                if(menuUrl.indexOf("SiteUserList") >= 0){
                    pageName = "站点用户管理";
                }
                if(menuUrl.indexOf("workFlowList") >= 0){
                    pageName = "工作流管理";
                }
                if(menuUrl.indexOf("surveyCategoryList") >= 0){
                    pageName = "问卷分类管理";
                }
                if(menuUrl.indexOf("surveyList") >= 0){
                    pageName = "问卷调查管理";
                }
                if(menuUrl.indexOf("deptList") >= 0){
                    pageName = "用户管理";
                }
                if(menuUrl.indexOf("syncYinHaiDeptAdd") >= 0){
                    pageName = "增量同步组织机构";
                }
                if(menuUrl.indexOf("syncYinHaiDeptAll") >= 0){
                    pageName = "全量同步组织机构";
                }
                if(menuUrl.indexOf("syncYinHaiUserAdd") >= 0){
                    pageName = "增量同步用户";
                }
                if(menuUrl.indexOf("syncYinHaiUserAll") >= 0){
                    pageName = "全量同步用户";
                }
                if(menuUrl.indexOf("loginLogList") >= 0){
                    pageName = "登录日志";
                }
                if(menuUrl.indexOf("csLogList") >= 0){
                    pageName = "审计日志";
                }
                setMainIframeUrl(menuUrl,pageName);
            }

        });

        $(window).resize(function(){
            init_page();
        });


        function openOrgPage()
        {
            window.open("org/index.jsp");
        }

        function getDeptTreeJsonData()
        {
            dept_jdata = eval(DeptRPC.getDeptTreeByUser());
            setLeftMenuTreeJsonData(dept_jdata);
        }

	</script>
</head>

<body>
<!--Banner区域开始-->
<div id="banner" class="hidden">
	<table border="0" cellpadding="0" cellspacing="0" class="banaerContent">
		<tr>
			<td align="left" valign="top">
				<div class="left textLeft" >
					<span class="blank18"></span>
					<!--<div class="left" style="width:30px;">&nbsp;</div>-->
					<div class="left" style="">
						<span class="blank6"></span>
						<div id="sysname">政务信息管理平台</div>

					</div>
					<!--<div class="left" style="width:8px;">&nbsp;</div>-->
					<div class="left" style="">
						<!--  -->
						<ul id="top_menu">

						</ul>
						<!--  -->
					</div>
				</div>
			</td>
			<td align="left" valign="top">
				<div id="top_right" class="right textRight" >
					<span class="blank9"></span>
					<div id="help_or_exit" class="textRight" >
						<a href="http://172.16.5.12:12345/cdplat/logout" target="_self">注销</a></div>
					<span class="blank18"></span>
					<div id="user_info">
						<span id="user_name"></span>，欢迎您！
					</div>
				</div>
			</td>
		</tr>
	</table>
</div>
<!--Banner区域结束-->

<!--一级菜区域开始-->
<div id="area_nav"  class="hidden">
	<table border="0" cellspacing="0" cellpadding="0" width="100%" >
		<tr>
			<!--<td style="width:15px;"></td>-->
			<td id="system_name" align="center" valign="middle" >

			</td>
			<td id="main_nav"  align="left" valign="top" >
				<!--菜单开始-->
				<ul id="menu">

				</ul>
				<!--菜单结束-->
			</td>
			<td width="16" align="center" valign="middle" >
				<div id="takeup" class="takeUp">&nbsp;</div>
			</td>
			<td width="7" align="center" valign="middle">&nbsp;

			</td>
		</tr>
	</table>
</div>
<!--一级菜区域结束-->
<span id="area_nav_spaceline"></span>

<!--主体区域开始-->

<div id="area_main" style="height:100%;">
	<table id="area_main_table" border="0" cellpadding="0" cellspacing="0" width="100%" height="100%" >
		<tr>
			<td class="area_main_space">&nbsp;</td>
			<td id="area_left" valign="top">
				<div id="area_leftContent" class="">
					<div id="nav_title">我的平台</div>
					<div id="leftMenuBox">
						<div id="leftMenu" class="contentBox6 textLeft">
							<ul id="leftMenuTree" class="easyui-tree" animate="true">

							</ul>
						</div>
					</div>
				</div>
			</td>
			<td id="area_cut" onclick="switchSysBar();" valign="top">
				<img id="switchSysBar_pic" src="images/switchSysBar.gif" title="关闭左栏"  />
			</td>
			<td id="area_right" align="left" valign="top">
				<!--右区域头/页签区域开始-->
				<div id="area_frame_tabs">
					<div class="area_frame_tabs_left">
						<div class="area_frame_tabs_right">
							<div class="tabs-header" style=" padding-left:5px;">
								<span class="blank5"></span>

								<div class="tabs-scroller-left" style="display:none; "></div>
								<div class="tabs-scroller-right" style="display:none; "></div>


								<ul class="tabs">

								</ul>


							</div>
						</div>
					</div>
				</div>
				<!--右区域头/页签区域结束-->
				<!--当前位置开始-->
				<div id="area_frame_local">
					<div class="area_frame_local_left">
						<div class="area_frame_local_right">
							<div id="cur_local">
								<!--当前位置&nbsp;:&nbsp;平台管理&nbsp;&gt;&nbsp;注册管理-->
							</div>
						</div>
					</div>
				</div>
				<!--当前位置结束-->
				<!--主工作区开始-->
				<div id="area_frame_work">
					<div class="area_frame_work_left">
						<div class="area_frame_work_right">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="2">&nbsp;</td>
									<td id="mainFrame" align="left" valign="top">
										<iframe id="iframe_1" frameborder="0" scrolling="auto" onload="" src="" width="100%" height="100%" style="overflow-x:hidden;"></iframe>

									</td>
									<td width="2"></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<!--主工作区结束-->
				<!--右区域尾开始-->
				<div id="area_frame_buttom">
					<div class="area_frame_buttom_left">
						<div class="area_frame_buttom_right">&nbsp;</div>
					</div>
				</div>
				<!--右区域尾结束-->
			</td>
			<td class="area_main_space"></td>
		</tr>
	</table>
</div>
<!--主体区域结束-->

<!--页尾版权区域开始-->
<div id="copyright">
</div>
<!--页尾版权区域结束-->

<!-- 模态窗口设计区开始 -->
<div id="sysDialog" name="sysDialog" class="hidden" title="My Window">
	<iframe id="sysDialog_Frame" name="sysDialog_Frame" src="spaceIframe.html" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
</div>

<div id="msgAlert" name="msgAlert" class="hidden" title="提示信息">
	<div class="msg_left_div back_img"></div>
	<div class="msg_right_div">
		<table border="0" cellpadding="0" cellspacing="0" align="left" height="139px">
			<tr><td valign="middle" align="left" class="lineHeight20px"></td></tr>
		</table>
	</div>
	<span class="blank3"></span>
	<div class="line2h"></div>
	<span class="blank3"></span>
	<div class="textCenter">
		<input id="i005" name="i005" type="button" hidefocus="true" value="　确定　" />
	</div>
	<span class="blank3"></span>
</div>

<div id="msgConfirm" name="msgConfirm" class="hidden" title="提示信息">
	<div class="msg_left_div back_img"></div>
	<div class="msg_right_div">
		<table border="0" cellpadding="0" cellspacing="0" align="left" height="139px">
			<tr><td valign="middle" align="left" class="lineHeight20px"></td></tr>
		</table>
	</div>
	<span class="blank3"></span>
	<div class="line2h"></div>
	<span class="blank3"></span>
	<div class="textCenter">
		<input id="i005" name="i005" type="button" hidefocus="true" value="　是　" />
		<input id="i005" name="i005" type="button"  hidefocus="true" value="　否　" />
	</div>
	<span class="blank3"></span>
</div>
<!-- 模态窗口设计区结束 -->
</body>
</html>
