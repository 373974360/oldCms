package com.deya.wcm.dao;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import com.deya.util.DateUtil;

import com.deya.wcm.bean.logs.SettingLogsBean;

import com.deya.wcm.db.DBManager;

/**
 * 
 * 公有表数据处理类.
 * 
 * <p>
 * Title: CicroDate
 * </p>
 * 
 * <p>
 * Description: 公有表数据处理类，包括sequence表等
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * <p>
 * Company: Cicro
 * </p>
 * 
 * @author zhuliang
 * 
 * @version 1.0
 * 
 *          *
 */

public class PublicTableDAO {

	// org

	public static String APP_TABLE_NAME = "cs_org_app";

	public static String DEPT_TABLE_NAME = "cs_org_dept";

	public static String DEPTLEVEL_TABLE_NAME = "cs_org_deptlevel";

	public static String DEPTMANAGER_TABLE_NAME = "cs_org_dept_manager";

	public static String USER_TABLE_NAME = "cs_org_user";

	public static String USERLEVEL_TABLE_NAME = "cs_org_userlevel";

	public static String USERREGISTER_TABLE_NAME = "cs_org_register";

	public static String GROUP_TABLE_NAME = "cs_org_group";

	public static String GROUPUSER_TABLE_NAME = "cs_org_group_user";

	public static String ROLE_TABLE_NAME = "cs_org_role";

	public static String ROLEAPP_TABLE_NAME = "cs_org_role_app";

	public static String ROLEUSER_TABLE_NAME = "cs_org_user_role";

	public static String ROLEGROUP_TABLE_NAME = "cs_org_group_role";

	public static String ROLEOPT_TABLE_NAME = "cs_org_role_opt";

	public static String OPT_TABLE_NAME = "cs_org_opt";

	public static String MENU_TABLE_NAME = "cs_org_menu";
	
	public static String DESKTOP_TABLE_NAME = "cs_user_desktop";

	// control

	public static String SITEGROUP_TABLE_NAME = "cs_site_group";

	public static String SITESERVER_TABLE_NAME = "cs_site_server";

	public static String SITE_TABLE_NAME = "cs_site";

	public static String SITEDOMAIN_TABLE_NAME = "cs_site_domain";

	public static String SITECONFIG_TABLE_NAME = "cs_site_config";

	public static String SITEAPP_TABLE_NAME = "cs_site_app";
	
	public static String SITECOUNT_TABLE_NAME = "cs_site_count";

	// system

	public static String METADATA_TABLE_NAME = "cs_info_meta";// 元数据表

	public static String FORMODEL_TABLE_NAME = "cs_info_model";// 内容模型表

	public static String FORMODELFIELD_TABLE_NAME = "cs_info_field";// 内容模型表字段表

	public static String SYS_DICT_CATEGORY_TABLE_NAME = "cs_sys_dict_category";// 数据字典分类

	public static String SYS_DICT_TABLE_NAME = "cs_sys_dict";// 数据字典项

	public static String SYS_CONFIG_TABLE_NAME = "cs_sys_config";//数据字典项

	public static String HOTWORD_TABLE_NAME = "cs_info_hotword";// 系统热词表

	public static String TAGS_TABLE_NAME = "cs_info_tags";// 系统Tags词语表

	public static String AUTHOR_TABLE_NAME = "cs_info_author";// 系统Tags词语表

	public static String SOURCE_TABLE_NAME = "cs_info_source";// 系统Tags词语表

	public static String COMMENT_TABLE_NAME = "cs_comment";// 评论表

	public static String WARE_CATEGORY_TABLE_NAME = "cs_ware_category";// 信息标签分类表

	public static String WARE_TABLE_NAME = "cs_ware";// 信息标签表

	public static String WARE_INFO_TABLE_NAME = "cs_ware_info";// 手动标签信息主表

	public static String WARE_INFOS_TABLE_NAME = "cs_ware_infos";// 手动标签信息从表

	public static String WARE_INFO_REF_TABLE_NAME = "cs_info_ref";// 9.5 信息推荐表

	public static String WARE_RELE_USER_TABLE_NAME = "cs_wcat_priv";// 标签分类用户关联表

	// template

	public static String TEMPLATE_TABLE_NAME = "cs_template";// 模板表

	public static String TEMPLATE_CLASS_TABLE_NAME = "cs_template_class";// 模板分类表

	public static String TEMPLATE_CATEGORY_TABLE_NAME = "cs_template_category";// 模板目录表

	public static String TEMPLATE_EDIT_TABLE_NAME = "cs_template_edit";// 模板分类表

	public static String TEMPLATE_VER_TABLE_NAME = "cs_template_ver";// 模板版本表
	
	public static String DESIGN_CSS_TABLE_NAME = "cs_design_css";// 模板版本表
	public static String DESIGN_LAYOUT_TABLE_NAME = "cs_design_layout";// 模板版本表
	public static String DESIGN_MODULE_TABLE_NAME = "cs_design_module";// 模板版本表
	public static String DESIGN_STYLE_TABLE_NAME = "cs_design_style";// 模板版本表
	public static String DESIGN_FRAME_TABLE_NAME = "cs_design_frame";// 模板版本表
	public static String DESIGN_CASE_TABLE_NAME = "cs_design_case";// 模板版本表
	public static String DESIGN_CATEGORY_TABLE_NAME = "cs_design_category";// 模板版本表

	// cms

	public static String WORKFLOW_TABLE_NAME = "cs_workflow";// 工作流主表

	public static String WORKFLOW_STEP_TABLE_NAME = "cs_workflow_step";// 工作流步骤表

	public static String WORKFLOW_LOG_TABLE_NAME = "cs_workflow_log";// 工作流日志表

	public static String WORKFLOW_STATUS_TABLE_NAME = "cs_info_status";// 工作流状态表

	public static String INFO_CLASS_TABLE_NAME = "cs_info_class";// 分类方式表

	public static String INFO_CATEGORY_TABLE_NAME = "cs_info_category";// 目录表
	
	public static String INFO_CATEGORY_REGU_TABLE_NAME = "cs_info_category_regu";// 目录获取规则表
	
	public static String INFO_CATEGORY_MODEL_TABLE_NAME = "cs_info_category_model";// 目录与内容模型关联表

	public static String INFO_ZT_CATEGORY_TABLE_NAME = "cs_zt_category";// 专题分类表

	public static String INFO_TABLE_NAME = "cs_info";// 信息主表
	
	public static String INFO_DIGG_LOG_TABLE_NAME = "cs_info_digg_log";// 信息主表

	public static String INFO_INFO_TABLE_NAME = "cs_info_info";// 相关信息表
	
	public static String INFO_ARTICLE_CATEGORY_TABLE_NAME = "cs_info_article";// 文章模型表
	
	public static String INFO_PIC_TABLE_NAME = "cs_info_pic";// 图组模型表
	
	public static String INFO_VIDEO_TABLE_NAME = "cs_info_video";// 视频模型表
	
	// public

	public static String INFO_UDEFINED_TABLE_NAME = "cs_info_udefined";// 自定义字段扩展表

	public static String SETTINGLOGS_TABLE_NAME = "cs_log_setting";
	
	public static String LOGINLOGS_TABLE_NAME = "cs_log_login";

	public static String FILTERWORD_TABLE_NAME = "cs_sys_filterword"; // 过滤词

	// member 会员

	public static String MEMBER_CATEGORY_TABLE_NAME = "cs_member_category";// 会员分类方式

	public static String MEMBER_TABLE_NAME = "cs_member"; // 会员主表

	public static String MEMBER_REGISTER_TABLE_NAME = "cs_member_register";// 会员帐号

	// interview 访谈

	public static String INTERVIEW_SCATEGORY_TABLE_NAME = "cp_scategory";

	public static String INTERVIEW_SUBJECT_TABLE_NAME = "cp_subject";

	public static String INTERVIEW_RESOUSE_TABLE_NAME = "cp_resouse";

	public static String INTERVIEW_ACTOR_TABLE_NAME = "cp_actor";

	public static String INTERVIEW_RELEINFO_TABLE_NAME = "cp_releinfo";

	public static String INTERVIEW_CHAT_TABLE_NAME = "cp_chat";

	// survey 调查

	public static String SURVEY_CATEGORY_TABLE_NAME = "cp_survey_category";

	public static String SURVEY_TABLE_NAME = "cp_survey";

	public static String SURVEY_SUB_TABLE_NAME = "cp_survey_sub";

	public static String SURVEY_ITEM_TABLE_NAME = "cp_survey_item";

	public static String SURVEY_CHILD_TABLE_NAME = "cp_survey_child";

	public static String SURVEY_ANSWER_TABLE_NAME = "cp_survey_answer";

	public static String SURVEY_ANSWER_ITEM_TABLE_NAME = "cp_survey_answer_item";

	// material

	public static String MateInfo_TABLE_NAME = "cs_attachment";// 附件表

	public static String MateFolder_TABLE_NAME = "cs_attachment_folder";// 附件目录表

	// appeal 诉求

	public static String APPEAL_AREA_TABLE_NAME = "cp_area";

	public static String APPEAL_CALENDAR_TABLE_NAME = "cp_calendar";

	public static String APPEAL_MODEL_TABLE_NAME = "cp_model";

	public static String APPEAL_SQ_TABLE_NAME = "cp_sq";

	public static String APPEAL_PROCESS_TABLE_NAME = "cp_process";

	public static String APPEAL_DEPT_TABLE_NAME = "cp_dept";

	public static String APPEAL_USER_TABLE_NAME = "cp_user";

	public static String APPEAL_Lead_TABLE_NAME = "cp_lead";

	public static String APPEAL_CATEGORY_TABLE_NAME = "cp_category";

	public static String APPEAL_PHRASAL_TABLE_NAME = "cp_phrasal";

	public static String APPEAL_SATISFATION_TABLE_NAME = "cp_satisfaction";

	public static String APPEAL_SATRECORD_TABLE_NAME = "cp_sat_record";

	public static String APPEAL_SQCUSTOM_TABLE_NAME = "cp_sq_custom";

	// 应用对应ID

	public static String APP_ORG = "org";

	public static String APP_SYSTEM = "system";

	public static String APP_CONTROL = "control";

	public static String APP_CMS = "cms";

	public static String APP_INFODIR = "infodir";

	public static String APP_ZWGK = "zwgk";

	public static String APP_STATISTICS = "statistics";

	// 政务公开

	public static String GK_NODE_CATEGORY = "cs_gk_node_category";

	public static String GK_NODE = "cs_gk_node";
	
	public static String GK_INDEX_SEQUENCE = "cs_gk_sequence";
	
	public static String INFO_RESFILE_TABLE_NAME = "cs_gk_resfile";
	
	public static String SER_CATEGORY_TABLE_NAME = "cs_ser_category";
	
	public static String SER_RESOUCE_TABLE_NAME = "cs_ser_resouce";
	
	public static String GK_APPCATALOG_TABLE_NAME = "cs_gk_app_catalog";
	
	public static String GK_APPREGU_TABLE_NAME = "cs_gk_app_regu";
	//依申请公开
	public static String YSQGK_CONFIG_TABLE_NAME = "cs_gk_ysq_config";
	public static String YSQGK_INFO_TABLE_NAME = "cs_gk_ysq";
	public static String YSQGK_PHRASAL_TABLE_NAME = "cs_gk_phrasal";

	// 代码片段

	public static String APPEAL_SNIPPET_TABLE_NAME = "cs_snippet";

	// 页面

	public static String PAGE_TABLE_NAME = "cs_page";


	//定制查询
	public static String DZ_CHAXUN_CONF_NAME = "cs_dz_chaxun_conf";
	public static String DZ_CHAXUN_DIC_NAME = "cs_dz_chaxun_dic";
	public static String DZ_CHAXUN_ITEM_NAME = "cs_dz_chaxun_item";
	
	//公务员名录配置表
	public static String MINGLU_TEMPLATE_TABLE_NAME = "cs_org_minlu_template";
	
	//纠错管理表
	public static String ERROR_TABLE_NAME = "cs_err_report";

	//应用组件之留言板
	public static String GUESTBOOKSUB__TABLE_NAME = "cs_guestbook_sub";//主题表
	public static String GUESTBOOK__TABLE_NAME = "cs_guestbook";//内容表
	public static String GUESTBOOK_CATEGORY_TABLE_NAME = "cs_guestbook_cat";//主题表
	public static String GUESTBOOK_CLASS_TABLE_NAME = "cs_guestbook_class";//内容表
	
	//评论配置表
	public static String COMSET_TABLE_NAME = "cs_comment_set";
	public static String COMMENT_MAIN_TABLE_NAME = "cs_comment_main";
	
	//访问统计表
	public static String AccessInfoCount_TABLE_NAME = "cs_info_access";
	
	//报送功能
	public static String RECEIVE_CONFIG_TABLE_NAME = "cs_receive_config";
	public static String RECEIVE_CAT_TABLE_NAME = "cs_receive_cat";
	public static String SEND_CONFIG_TABLE_NAME = "cs_send_config";
	public static String RECEIVE_INFO_TABLE_NAME = "cs_receive_info";
	public static String SEND_RECORD_TABLE_NAME = "cs_send_record";
	/**
	 * 
	 * 根据表名得到它的下一位ID
	 * 
	 * @param String
	 *            table_name
	 * 
	 * @return List
	 * 
	 * */

	@SuppressWarnings("unchecked")
	public static synchronized int getIDByTableName(String table_name)

	{

		String id = DBManager.getString("getSequence", table_name);

		if (id == null || "".equals(id) || "null".equals(id))

		{

			Map<String, Comparable> m = new HashMap<String, Comparable>();

			m.put("table_name", table_name);

			m.put("seq", 1);

			DBManager.insert("insert_sequence", m);

			return 1;

		}

		else {

			int id_n = Integer.parseInt(id) + 1;

			DBManager.update("update_sequence", table_name);

			return id_n;

		}

	}

	/**
	 * 
	 * 插入操作类审计日志
	 * 
	 * @param Map
	 *            m
	 * 
	 * @return boolean
	 * 
	 * */

	public static boolean insertSettingLogs(String oper_type, String oper_name,
			String ids, SettingLogsBean stl)

	{
		if(stl != null)
		{
			stl.setAudit_des("[" + oper_type + "]" + oper_name + "ID为： " + ids
					+ " 的数据");
	
			stl.setAudit_id(getIDByTableName(SETTINGLOGS_TABLE_NAME));
	
			stl.setAudit_time(DateUtil.getCurrentDateTime());
	
			return DBManager.insert("insert_setting_logs", stl);
		}
		return true;
	}

	/**
	 * 
	 * 创建表信息
	 * 
	 * @param Map
	 *            map
	 * 
	 * @return boolean
	 */

	@SuppressWarnings("unchecked")
	public static boolean createSql(Map map) {

		try {

			DBManager.update("initSql_createSql", map);

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			return false;

		}

	}

	/**
	 * 
	 * 判断表是否存在
	 * 
	 * @param Map
	 *            map
	 * 
	 * @return String
	 */

	@SuppressWarnings("unchecked")
	public static String getTable(Map map) {

		try {

			return DBManager.getString("initSql_getCount", map);

		} catch (Exception e) {

			e.printStackTrace();

			return "-1";

		}

	}
	
	/**
	 * 
	 * 执行动态sql查询
	 * 
	 * @param String sql
	 * 
	 * @return String
	 */	
	@SuppressWarnings("unchecked")
	public static List executeSearchSql(String sql)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("sql", sql);
		return DBManager.queryFList("getSearchSql",m);		
	}
	
	/**
	 * 
	 * 执行动态sql
	 * 
	 * @param String sql
	 * 
	 * @return String
	 */
	public static boolean executeDynamicSql(String sql)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("sql", sql);		
		if(sql.startsWith("insert"))
		{
			return DBManager.insert("insert_dynamic_sql", m);			
		}
		if(sql.startsWith("update") || sql.startsWith("alter") || sql.startsWith("create"))
		{
			return DBManager.update("initSql_createSql", m);
		}
		if(sql.startsWith("delete"))
		{
			return DBManager.update("delete_dynamic_sql", m);
		}
		return true;
	}

	public static void main(String arg[])
	{		
		System.out.println(executeSearchSql("select * from cs_info").get(0));
	}
}