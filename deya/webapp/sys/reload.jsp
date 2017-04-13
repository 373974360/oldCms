<%@ page contentType="text/html; charset=utf-8"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String name = request.getParameter("name");	
	if("user".equals(name))
	{
		com.deya.wcm.services.org.user.UserRegisterManager.reloadUserRegister();
		com.deya.wcm.services.org.user.UserManager.reloadUser();
		com.deya.wcm.services.org.siteuser.SiteUserManager.reloadSiteuser();
		com.deya.wcm.services.org.user.UserLevelManager.reloadUserLevel();
	}
	if("dept".equals(name))
	{
		com.deya.wcm.services.org.dept.DeptManager.reloadDept();
		com.deya.wcm.services.org.dept.DeptManager.reloadDeptManager();
		com.deya.wcm.services.org.dept.DeptLevelManager.reloadDeptLevel();
	}
	if("usergroup".equals(name))
	{
		com.deya.wcm.services.org.group.GroupManager.reloadGroup();
		com.deya.wcm.services.org.group.GroupManager.reloadGroupUser();
	}
	if("role".equals(name))
	{
		com.deya.wcm.services.org.role.RoleManager.reloadRole();
		com.deya.wcm.services.org.role.RoleManager.reloadRoleApp();
		com.deya.wcm.services.org.role.RoleOptManager.reloadRoleOpt();
		com.deya.wcm.services.org.role.RoleUserManager.reloadRoleUser();
		com.deya.wcm.services.org.role.RoleUGroupManager.reloadRoleUGroup();
	}
	if("operate".equals(name))
	{
		com.deya.wcm.services.org.operate.OperateManager.reloadOperate();
	}
	if("menu".equals(name))
	{
		com.deya.wcm.services.org.operate.MenuManager.reloadMenu();
	}
	if("siteconfig".equals(name))
	{
		com.deya.wcm.services.control.config.SiteConfigManager.reloadSiteConfigList();
	}
	if("domain".equals(name))
	{
		com.deya.wcm.services.control.domain.SiteDomainManager.reloadSiteDomainList();
	}
	if("sitegroup".equals(name))
	{
		com.deya.wcm.services.control.group.SiteGroupManager.reloadGroupList();
	}
	if("siteserver".equals(name))
	{
		com.deya.wcm.services.control.server.SiteServerManager.reloadServerList();
	}
	if("site".equals(name))
	{
		com.deya.wcm.services.control.site.SiteManager.reloadSiteList();		
		com.deya.wcm.services.control.site.SiteAppRele.reloadSiteAppRele();		
	}
	if("cmscategory".equals(name))
	{
		com.deya.wcm.services.cms.category.CategoryManager.reloadCategory();
		com.deya.wcm.services.cms.category.CateClassManager.reloadCateClass();
		com.deya.wcm.services.cms.category.CategoryModelManager.reloadCategoryModel();
		com.deya.wcm.services.cms.category.CategoryReleManager.reloadCategoryRele();
		com.deya.wcm.services.cms.category.CategorySharedManager.reloadCategoryShared();
		com.deya.wcm.services.cms.category.SyncManager.reloadSync();
		com.deya.wcm.services.cms.category.ZTCategoryManager.reloadZTCategory();
	}
	if("workflow".equals(name))
	{
		com.deya.wcm.services.cms.workflow.WorkFlowManager.reloadWorkFlow();
	}
	if("gkclass".equals(name))
	{
		com.deya.wcm.services.zwgk.node.GKNodeCateManager.reloadGKNodeCategory();
		com.deya.wcm.services.zwgk.node.GKNodeManager.reloadGKNode();
		com.deya.wcm.services.zwgk.index.IndexManager.reLoadRoleMap();
	}
	if("sysconfig".equals(name))
	{
		com.deya.wcm.services.system.config.ConfigManager.clearMap();
	}
	if("sysall".equals(name))
	{
		com.deya.wcm.services.system.assist.AuthorManager.reloadAuthor();
		com.deya.wcm.services.system.assist.HotWordManager.reloadHotWord();
		com.deya.wcm.services.system.assist.SourceManager.reloadSource();
		com.deya.wcm.services.system.assist.TagsManager.reloadTags();
		com.deya.wcm.services.system.dict.DataDictionaryManager.clearMap();
		com.deya.wcm.services.system.filterWord.FilterWordManager.reloadFilterWord();
	}
	if("frommodel".equals(name))
	{
		com.deya.wcm.services.system.formodel.ModelFieldManager.reloadModelField();
		com.deya.wcm.services.system.formodel.ModelManager.reloadModel();
		com.deya.wcm.services.system.metadata.MetaDataManager.reloadMetaData();
	}
	if("template".equals(name))
	{
		com.deya.wcm.services.system.template.TemplateUtils.initTemplatePathMap();
		com.deya.wcm.services.system.template.TemplateCategoryManager.reloadTemplateCategory();	
		com.deya.wcm.services.system.template.TemplateVerManager.reloadTemplate();		
	}
	if("ware".equals(name))
	{
		com.deya.wcm.services.system.ware.WareCategoryManager.reloadMap();
		com.deya.wcm.services.system.ware.WareManager.reloadMap();
		com.deya.wcm.services.system.ware.WareReleUserManager.reloadWareReleUser();
	}
	if("page".equals(name))
	{
		com.deya.wcm.services.page.PageManager.reloadPage();
	}
	if("material".equals(name))
	{
		com.deya.wcm.services.material.MateFolderManager.reloadMateFolder();
	}
	if("member".equals(name))
	{
		com.deya.wcm.services.member.MemberCategoryManager.reloadMap();
		com.deya.wcm.services.member.MemberConfigManager.reload();
		com.deya.wcm.services.member.MemberManager.reloadMemberMap();
		com.deya.wcm.services.member.MemberManager.reloadRegisterMap();
	}
	if("sq".equals(name))
	{
		com.deya.wcm.services.appeal.category.CategoryManager.reloadAppCate();
		com.deya.wcm.services.appeal.model.ModelManager.reloadModel();
		com.deya.wcm.services.appeal.model.ModelManager.reloadModelDeptList();
		com.deya.wcm.services.appeal.model.ModelManager.reloadModelUserList();
		com.deya.wcm.services.appeal.model.CPFromManager.reloadCPFrom();
		com.deya.wcm.services.appeal.calendar.CalendarManager.reloadCalendar();
		com.deya.wcm.services.appeal.cpDept.CpDeptManager.loadCpdept();
		com.deya.wcm.services.appeal.cpLead.CpLeadManager.reloadCpLeadList();
		com.deya.wcm.services.appeal.cpUser.CpUserManager.reloadCpUserDept();
		com.deya.wcm.services.appeal.purpose.PurposeManager.reloadMap();
		com.deya.wcm.services.appeal.satisfaction.SatisfactionManager.reloadSatisfaction();
	}
	if("interview".equals(name))
	{
		com.deya.wcm.services.interview.SubjectCategoryServices.reloadSubjectCategory();
		com.deya.wcm.services.interview.SubjectServices.clearMap();
	}
	if("survey".equals(name))
	{
		com.deya.wcm.services.survey.SurveyCategoryService.reloadSurveyCategory();
		com.deya.wcm.services.survey.SurveyService.reLoadSurveyBean();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>政务信息管理平台</title>


<meta http-equiv="X-UA-Compatible" content="IE=8" />

</head>

<body>
<table border="0">
<tr><td><a href="?name=user">重载用户信息</a></td></tr>
<tr><td><a href="?name=dept">重载部门信息</a></td></tr>
<tr><td><a href="?name=usergroup">重载用户组信息</a></td></tr>
<tr><td><a href="?name=role">重载角色信息</a></td></tr>
<tr><td><a href="?name=operate">重载权限信息</a></td></tr>
<tr><td><a href="?name=menu">重载栏目信息</a></td></tr>
<tr><td></td></tr>
<tr><td><a href="?name=siteconfig">重载站点配置信息</a></td></tr>
<tr><td><a href="?name=domain">重载站点域名信息</a></td></tr>
<tr><td><a href="?name=sitegroup">重载网站群信息</a></td></tr>
<tr><td><a href="?name=siteserver">重载网站群服务器信息</a></td></tr>
<tr><td><a href="?name=site">重载站点信息</a></td></tr>
<tr><td><a href="?name=material">重载素材库信息</a></td></tr>
<tr><td></td></tr>
<tr><td><a href="?name=cmscategory">重载内容管理目录信息</a></td></tr>
<tr><td><a href="?name=workflow">重载工作流信息</a></td></tr>
<tr><td></td></tr>
<tr><td><a href="?name=gkclass">重载信息公开栏目节点信息</a></td></tr>
<tr><td></td></tr>
<tr><td><a href="?name=sysconfig">重载系统配置（站点水印）信息</a></td></tr>
<tr><td><a href="?name=sysall">重载系统修改（如热词，作者，来源信息）信息</a></td></tr>
<tr><td><a href="?name=frommodel">重载内容模型信息</a></td></tr>
<tr><td><a href="?name=template">重载模板信息</a></td></tr>
<tr><td><a href="?name=ware">重载标签信息</a></td></tr>
<tr><td><a href="?name=page">重载页面信息</a></td></tr>
<tr><td><a href="?name=member">重载会员信息</a></td></tr>
<tr><td></td></tr>
<tr><td><a href="?name=sq">诉求信息</a></td></tr>
<tr><td><a href="?name=interview">在线访谈信息</a></td></tr>
<tr><td><a href="?name=survey">在线调查信息</a></td></tr>
</table>
</body>
</html>
