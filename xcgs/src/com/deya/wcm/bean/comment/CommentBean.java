/**
 * 名称: CommentBean.java<br>
 * 描述: <br>
 * 类型: Java<br>
 * 最近修改时间:May 9, 2012 10:44:57 AM<br>
 * @since  May 9, 2012
 * @author Administrator
 */
package com.deya.wcm.bean.comment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类型描述
 *
 * @since  May 9, 2012
 * @author Administrator
 *
 */
public class CommentBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8621787369672322228L;
	private int id = 0;
	private int info_id = 0;//信息ID
	private String info_uuid = "";//UUID 用于存储调查的ID
	private String app_id = "";//应用ID
	private String site_id = "";//站点ID
	private int info_type = 0;//信息类型，1，信息2，调查，3.诉求
	private int is_member = 0;//是否是会员 0非会员 1会员 
	private String nick_name = "";
	private String user_name = "";
	private int member_id = 0;
	private String tel = "";
	private String email = "";
	private String add_time = "";
	private String content = "";//内容 用于前台展示的，如果进行删除或审核不通过操作，此值付为"该评论已删除"
	private String content2 = "";//内容2 用于后台展示的
	private int is_report = 0;//是否被报
	private int report_count = 0;//举报数
	private String report_last_time = "";//最后举报时间
	private int is_quest = 0;//是否有敏感词
	private String ip = "";
	private String country = "";
	private int is_status  = 0;//评论状态 0未审核  1已审核
	private int support_count = 0;//支持数
	private int parent_id = 0;//父节点ID
	private String parent_str = "";//所有父节点ID
	private int is_replay = 0;//是否有回复
	private int is_delete = 0;//是否已删除
	private List<CommentBean> parent_list = new ArrayList<CommentBean>();
	private String title = "";
	private String content_url = "";
	private int is_top = 0;//置顶
	private String top_time = "";//置顶时间
	private CommentBean parent_comment = null;
	private int com_sort = 1;//盖楼时显示的排序，数据库无此字段
	private int count = 0;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getCom_sort() {
		return com_sort;
	}
	public void setCom_sort(int comSort) {
		com_sort = comSort;
	}
	public CommentBean getParent_comment() {
		return parent_comment;
	}
	public void setParent_comment(CommentBean parentComment) {
		parent_comment = parentComment;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getIs_top() {
		return is_top;
	}
	public void setIs_top(int isTop) {
		is_top = isTop;
	}
	public String getTop_time() {
		return top_time;
	}
	public void setTop_time(String topTime) {
		top_time = topTime;
	}
	public String getContent2() {
		return content2;
	}
	public void setContent2(String content2) {
		this.content2 = content2;
	}
	public String getReport_last_time() {
		return report_last_time;
	}
	public void setReport_last_time(String reportLastTime) {
		report_last_time = reportLastTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent_url() {
		return content_url;
	}
	public void setContent_url(String contentUrl) {
		content_url = contentUrl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInfo_id() {
		return info_id;
	}
	public void setInfo_id(int infoId) {
		info_id = infoId;
	}
	public String getInfo_uuid() {
		return info_uuid;
	}
	public void setInfo_uuid(String infoUuid) {
		info_uuid = infoUuid;
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
	public int getInfo_type() {
		return info_type;
	}
	public void setInfo_type(int infoType) {
		info_type = infoType;
	}
	public int getIs_member() {
		return is_member;
	}
	public void setIs_member(int isMember) {
		is_member = isMember;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nickName) {
		nick_name = nickName;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int memberId) {
		member_id = memberId;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String addTime) {
		add_time = addTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIs_report() {
		return is_report;
	}
	public void setIs_report(int isReport) {
		is_report = isReport;
	}
	public int getReport_count() {
		return report_count;
	}
	public void setReport_count(int reportCount) {
		report_count = reportCount;
	}
	
	public int getIs_quest() {
		return is_quest;
	}
	public void setIs_quest(int isQuest) {
		is_quest = isQuest;
	}
	
	public int getIs_status() {
		return is_status;
	}
	public void setIs_status(int isStatus) {
		is_status = isStatus;
	}
	public int getSupport_count() {
		return support_count;
	}
	public void setSupport_count(int supportCount) {
		support_count = supportCount;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parentId) {
		parent_id = parentId;
	}
	public String getParent_str() {
		return parent_str;
	}
	public void setParent_str(String parentStr) {
		parent_str = parentStr;
	}
	public int getIs_replay() {
		return is_replay;
	}
	public void setIs_replay(int isReplay) {
		is_replay = isReplay;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int isDelete) {
		is_delete = isDelete;
	}
	public List<CommentBean> getParent_list() {
		return parent_list;
	}
	public void setParent_list(List<CommentBean> parentList) {
		parent_list = parentList;
	}
	
}
