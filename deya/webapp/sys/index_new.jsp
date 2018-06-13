<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>政务信息管理平台</title>


	<meta http-equiv="X-UA-Compatible" content="IE=11" />
	<link type="text/css" rel="stylesheet" href="styles/main.css" />
	<link type="text/css" rel="stylesheet" href="styles/index.css" />
	<link rel="stylesheet" type="text/css" href="styles/themes/default/tree.css">
	<link rel="stylesheet" type="text/css" href="styles/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-ui/themes/base/jquery.ui.all.css"  />
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript">
		function changeUrl(url){
		    $('#area_frame_work').css("height",700);
		    $('#iframe_1').attr("src",url);
            $('#iframe_1').css("height",700);
		}
	</script>
</head>

<body>
<span id="area_nav_spaceline"></span>
<!--主体区域开始-->
<div id="area_main">
	<table id="area_main_table" border="0" cellpadding="0" cellspacing="0" width="100%" height="100%" >
		<tr>
			<td class="area_main_space">&nbsp;</td>
			<td id="area_left" valign="top">
				<div id="area_leftContent" class="">
					<div id="nav_title">我的平台</div>
					<div id="leftMenuBox">
						<div id="leftMenu" class="contentBox6 textLeft">
							<ul id="leftMenuTree" class="easyui-tree" animate="true">
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?method=addInfo')">添加信息</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?method=modifyInfo')">修改信息</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?method=delInfo')">删除信息</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/cms/info/article/publishList.jsp')">发布信息</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/cms/info/article/auditPublishList.jsp')">待发信息</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/cms/info/article/bmscInfoDataList.jsp')">保密审查</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?method=unPublishInfo')">撤销信息</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?method=unPublishedInfo')">已撤信息</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?method=checkInfo')">审核信息（分栏目）</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/cms/info/article/verifyInfoList.jsp')">审核信息</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/cms/info/article/checkingList.jsp')">审核中信息</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?method=noPassInfo')">退稿信息</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?method=drafgInfo')">草稿信息</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?method=rubbyInfo')">回收站信息</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?method=getInfo')">获取信息</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?method=moveInfo')">移动信息</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?method=pushInfo')">推送信息</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?method=backInfo')">归档信息</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/system/assist/author/authorDataList.jsp')">作者管理</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/system/assist/source/sourceDataList.jsp')">来源管理</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/material/operate/managerList.jsp')">素材管理</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/cms/cmsCount/cmsCountList.jsp')">信息统计</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/cms/cmsCount/cmsAssessList.jsp')">工作量考核</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/cms/cmsCount/cmsUpdateList.jsp')">信息更新统计</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/cms/category/categoryList.jsp?app_id=cms')">目录管理</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/page/pageList.jsp?app_id=cms')">页面管理</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/org/role/roleList.jsp?app=system')">站点角色管理</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/org/siteUser/SiteUserList.jsp?appId=cms')">站点用户管理</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/cms/workflow/workFlowList.jsp?app_id=system')">工作流管理</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/survey/surveySettingList.jsp')">问卷配置管理</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/survey/surveyCategoryList.jsp')">问卷分类管理</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/survey/surveyList.jsp')">问卷调查管理</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/survey/surveyAuditList.jsp')">问卷调查审核</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/org/dept/deptList.jsp')">用户管理</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/sso/syncYinHaiDeptAdd.jsp')">增量同步组织机构</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/sso/syncYinHaiDeptAll.jsp')">全量同步组织机构</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/sso/syncYinHaiUserAdd.jsp')">增量同步用户</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/sso/syncYinHaiUserAll.jsp')">全量同步用户</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/system/log/loginLogList.jsp')">登录日志</a> </li>
								<li><a href="#" onclick="changeUrl('/sys/index.jsp?menuUrl=/sys/system/log/csLogList.jsp')">审计日志</a> </li>
							</ul>
						</div>
					</div>
				</div>
			</td>
			<td id="area_right" align="left" valign="top">
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
</body>
</html>
