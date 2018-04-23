package com.deya.wcm.bean.cms.category;

public class CategoryBean implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6833159982899502073L;
    private int id = 0;//类目ID
    private int cat_id = 0;//类目ID
    private int class_id = 0;//分类方式ID
    private int parent_id = 0;//父类目ID
    private int wf_id = 0;//工作流ID
    private int is_wf_publish = 0;//终审后是否自动发布
    private String cat_code = "";//类目编号
    private String cat_ename = "";//类目名称（英）
    private String cat_cname = "";//类目名称（中）
    private String cat_position = "";//类目路径
    private int cat_level = 0;//深度/级别
    private int is_mutilpage = 0;//单页信息目录
    private String jump_url = "";//前台目录跳转URL
    private int template_index = 0;//栏目首页模板
    private int template_list = 0;//列表页模板
    private int is_generate_index = 0;//是否生成栏目首页
    private String urlrule_index = "";//栏目首页URL目录规则
    private String urlrule_list = "";//列表页URL目录规则
    private String urlrule_content = "";//内容页URL目录规则
    private int is_allow_submit = 0;//是否允许用户投稿
    private int is_allow_comment = 0;//是否允许评论
    private int is_comment_checked = 0;//评论是否需要审核
    private int is_show = 1;//自动导航是否显示
    private int is_show_tree = 1;//前台栏目树
    private String cat_keywords = "";//栏目META关键词
    private String cat_description = "";//栏目META网页描述
    private int cat_sort = 9999;//排序
    private int is_sync = 0;//同步类型
    private int cat_source_id = 0;//源分类ID
    private int cat_class_id = 0;//默认分类法类目ID
    private int is_disabled = 1;//是否启用,共享（暂不用）
    private String cat_memo = "";//备注
    private String app_id = "";//应用ID
    private String site_id = "";//站点ID、公开节点
    private int cat_type = 0;//0：普通栏目1：专题2：服务应用目录
    private int zt_cat_id = 0;//专题分类ID
    private int is_archive = 0;//是否已归档 0：正常 1：已归档 归档的栏目不显示在 填报栏目中
    private boolean is_sub = false;//是否是枝节点
    private String hj_sql = "";

    private String gkzt = "";//公开主体
    private String gksx = "";//公开时限
    private String sxyj = "";//公开事项依据
    private String gkfs = "";//公开方式
    private String gkgs = "";//公开格式
    private String gkbz = "";//公开备注
    private int mlsx = 0;//目录属性，1:新闻目录，2:公开目录

    public int getIs_show_tree() {
        return is_show_tree;
    }

    public void setIs_show_tree(int isShowTree) {
        is_show_tree = isShowTree;
    }

    public String getHj_sql() {
        return hj_sql;
    }

    public void setHj_sql(String hjSql) {
        hj_sql = hjSql;
    }

    public boolean isIs_sub() {
        return is_sub;
    }

    public void setIs_sub(boolean isSub) {
        is_sub = isSub;
    }

    public String getJump_url() {
        return jump_url;
    }

    public void setJump_url(String jumpUrl) {
        jump_url = jumpUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCat_type() {
        return cat_type;
    }

    public void setCat_type(int catType) {
        cat_type = catType;
    }

    public int getZt_cat_id() {
        return zt_cat_id;
    }

    public void setZt_cat_id(int ztCatId) {
        zt_cat_id = ztCatId;
    }

    public int getIs_archive() {
        return is_archive;
    }

    public void setIs_archive(int isArchive) {
        is_archive = isArchive;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int catId) {
        cat_id = catId;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int classId) {
        class_id = classId;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parentId) {
        parent_id = parentId;
    }

    public int getWf_id() {
        return wf_id;
    }

    public void setWf_id(int wfId) {
        wf_id = wfId;
    }

    public int getIs_wf_publish() {
        return is_wf_publish;
    }

    public void setIs_wf_publish(int isWfPublish) {
        is_wf_publish = isWfPublish;
    }

    public String getCat_code() {
        return cat_code;
    }

    public void setCat_code(String catCode) {
        cat_code = catCode;
    }

    public String getCat_ename() {
        return cat_ename;
    }

    public void setCat_ename(String catEname) {
        cat_ename = catEname;
    }

    public String getCat_cname() {
        return cat_cname;
    }

    public void setCat_cname(String catCname) {
        cat_cname = catCname;
    }

    public String getCat_position() {
        return cat_position;
    }

    public void setCat_position(String catPosition) {
        cat_position = catPosition;
    }

    public int getCat_level() {
        return cat_level;
    }

    public void setCat_level(int catLevel) {
        cat_level = catLevel;
    }

    public int getIs_mutilpage() {
        return is_mutilpage;
    }

    public void setIs_mutilpage(int isMutilpage) {
        is_mutilpage = isMutilpage;
    }

    public int getTemplate_index() {
        return template_index;
    }

    public void setTemplate_index(int templateIndex) {
        template_index = templateIndex;
    }

    public int getTemplate_list() {
        return template_list;
    }

    public void setTemplate_list(int templateList) {
        template_list = templateList;
    }

    public int getIs_generate_index() {
        return is_generate_index;
    }

    public void setIs_generate_index(int isGenerateIndex) {
        is_generate_index = isGenerateIndex;
    }

    public String getUrlrule_index() {
        return urlrule_index;
    }

    public void setUrlrule_index(String urlruleIndex) {
        urlrule_index = urlruleIndex;
    }

    public String getUrlrule_list() {
        return urlrule_list;
    }

    public void setUrlrule_list(String urlruleList) {
        urlrule_list = urlruleList;
    }

    public String getUrlrule_content() {
        return urlrule_content;
    }

    public void setUrlrule_content(String urlruleContent) {
        urlrule_content = urlruleContent;
    }

    public int getIs_allow_submit() {
        return is_allow_submit;
    }

    public void setIs_allow_submit(int isAllowSubmit) {
        is_allow_submit = isAllowSubmit;
    }

    public int getIs_allow_comment() {
        return is_allow_comment;
    }

    public void setIs_allow_comment(int isAllowComment) {
        is_allow_comment = isAllowComment;
    }

    public int getIs_comment_checked() {
        return is_comment_checked;
    }

    public void setIs_comment_checked(int isCommentChecked) {
        is_comment_checked = isCommentChecked;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int isShow) {
        is_show = isShow;
    }

    public String getCat_keywords() {
        return cat_keywords;
    }

    public void setCat_keywords(String catKeywords) {
        cat_keywords = catKeywords;
    }

    public String getCat_description() {
        return cat_description;
    }

    public void setCat_description(String catDescription) {
        cat_description = catDescription;
    }

    public int getCat_sort() {
        return cat_sort;
    }

    public void setCat_sort(int catSort) {
        cat_sort = catSort;
    }

    public int getIs_sync() {
        return is_sync;
    }

    public void setIs_sync(int isSync) {
        is_sync = isSync;
    }

    public int getCat_source_id() {
        return cat_source_id;
    }

    public void setCat_source_id(int catSourceId) {
        cat_source_id = catSourceId;
    }

    public int getCat_class_id() {
        return cat_class_id;
    }

    public void setCat_class_id(int catClassId) {
        cat_class_id = catClassId;
    }

    public int getIs_disabled() {
        return is_disabled;
    }

    public void setIs_disabled(int isDisabled) {
        is_disabled = isDisabled;
    }

    public String getCat_memo() {
        return cat_memo;
    }

    public void setCat_memo(String catMemo) {
        cat_memo = catMemo;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String appId) {
        app_id = appId;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String siteId) {
        site_id = siteId;
    }

    public String getGkzt() {
        return gkzt;
    }

    public void setGkzt(String gkzt) {
        this.gkzt = gkzt;
    }

    public String getGksx() {
        return gksx;
    }

    public void setGksx(String gksx) {
        this.gksx = gksx;
    }

    public String getSxyj() {
        return sxyj;
    }

    public void setSxyj(String sxyj) {
        this.sxyj = sxyj;
    }

    public String getGkfs() {
        return gkfs;
    }

    public void setGkfs(String gkfs) {
        this.gkfs = gkfs;
    }

    public String getGkgs() {
        return gkgs;
    }

    public void setGkgs(String gkgs) {
        this.gkgs = gkgs;
    }

    public String getGkbz() {
        return gkbz;
    }

    public void setGkbz(String gkbz) {
        this.gkbz = gkbz;
    }

    public int getMlsx() {
        return mlsx;
    }

    public void setMlsx(int mlsx) {
        this.mlsx = mlsx;
    }
}
