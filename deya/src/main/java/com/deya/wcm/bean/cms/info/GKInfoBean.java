package com.deya.wcm.bean.cms.info;

import java.util.ArrayList;
import java.util.List;

public class GKInfoBean extends InfoBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 601951881592800267L;
		
	protected String gk_index= "";//索引号
	protected String gk_year = "";//年份
	protected int gk_num = 0;//序号
	protected String doc_no = "";//文号
	protected String gk_file = "";//公文文件
	protected String generate_dtime = "";//生成日期
	protected String effect_dtime = "";//生效日期
	protected String aboli_dtime = "";//废止日期
	protected int topic_id = 0;//主题分类ID
	protected String topic_name = "";//主题分类名称
	protected int theme_id = 0;//体裁分类ID
	protected String theme_name = "";//体裁分类名称
	protected int serve_id = 0;//服务对象分类ID
	protected String serve_name = "";//服务对象分类名称
	protected String topic_key = "";//主题关键词
	protected String place_key = "";//位置关键词
	protected String language = "";//语种
	protected String carrier_type = "";//载体类型
	protected String gk_validity = "";//信息有效性
	protected String gk_format = "";//记录形式
	protected String gk_way = "";//公开形式
	protected int gk_type = 0;//公开方式(类型)
	protected String gk_no_reason = "";//不公开理由
	protected String gk_time_limit = "";//公开时限
	protected String gk_range = "";//公开范围
	protected String gk_proc = "";//公开程序|审核程序	
	protected String gk_duty_dept = "";//责任部门（科室）
	protected String gk_input_dept = "";//发布机构
	protected List<GKResFileBean> file_list = new ArrayList<GKResFileBean>();
	protected String file_head = "";//文件题头
	protected String gk_signer = "";//签发人

	
	public String getGk_signer() {
		return gk_signer;
	}
	public void setGk_signer(String gkSigner) {
		gk_signer = gkSigner;
	}
	public String getFile_head() {
		return file_head;
	}
	public void setFile_head(String fileHead) {
		file_head = fileHead;
	}
	public String getGk_index() {
		return gk_index;
	}
	public void setGk_index(String gkIndex) {
		gk_index = gkIndex;
	}
	public String getGk_year() {
		return gk_year;
	}
	public void setGk_year(String gkYear) {
		gk_year = gkYear;
	}
	public int getGk_num() {
		return gk_num;
	}
	public void setGk_num(int gkNum) {
		gk_num = gkNum;
	}
	public List<GKResFileBean> getFile_list() {
		return file_list;
	}
	public void setFile_list(List<GKResFileBean> fileList) {
		file_list = fileList;
	}
	public String getDoc_no() {
		return doc_no;
	}
	public void setDoc_no(String docNo) {
		doc_no = docNo;
	}
	public String getGk_file() {
		return gk_file;
	}
	public void setGk_file(String gkFile) {
		gk_file = gkFile;
	}
	public String getGenerate_dtime() {
		return generate_dtime;
	}
	public void setGenerate_dtime(String generateDtime) {
		generate_dtime = generateDtime;
	}
	public String getEffect_dtime() {
		return effect_dtime;
	}
	public void setEffect_dtime(String effectDtime) {
		effect_dtime = effectDtime;
	}
	public String getAboli_dtime() {
		return aboli_dtime;
	}
	public void setAboli_dtime(String aboliDtime) {
		aboli_dtime = aboliDtime;
	}
	public int getTopic_id() {
		return topic_id;
	}
	public void setTopic_id(int topicId) {
		topic_id = topicId;
	}
	public String getTopic_name() {
		return topic_name;
	}
	public void setTopic_name(String topicName) {
		topic_name = topicName;
	}
	public int getTheme_id() {
		return theme_id;
	}
	public void setTheme_id(int themeId) {
		theme_id = themeId;
	}
	public String getTheme_name() {
		return theme_name;
	}
	public void setTheme_name(String themeName) {
		theme_name = themeName;
	}
	public int getServe_id() {
		return serve_id;
	}
	public void setServe_id(int serveId) {
		serve_id = serveId;
	}
	public String getServe_name() {
		return serve_name;
	}
	public void setServe_name(String serveName) {
		serve_name = serveName;
	}
	public String getTopic_key() {
		return topic_key;
	}
	public void setTopic_key(String topicKey) {
		topic_key = topicKey;
	}
	public String getPlace_key() {
		return place_key;
	}
	public void setPlace_key(String placeKey) {
		place_key = placeKey;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCarrier_type() {
		return carrier_type;
	}
	public void setCarrier_type(String carrierType) {
		carrier_type = carrierType;
	}
	public String getGk_validity() {
		return gk_validity;
	}
	public void setGk_validity(String gkValidity) {
		gk_validity = gkValidity;
	}
	public String getGk_format() {
		return gk_format;
	}
	public void setGk_format(String gkFormat) {
		gk_format = gkFormat;
	}
	public String getGk_way() {
		return gk_way;
	}
	public void setGk_way(String gkWay) {
		gk_way = gkWay;
	}
	public int getGk_type() {
		return gk_type;
	}
	public void setGk_type(int gkType) {
		gk_type = gkType;
	}
	public String getGk_no_reason() {
		return gk_no_reason;
	}
	public void setGk_no_reason(String gkNoReason) {
		gk_no_reason = gkNoReason;
	}
	public String getGk_time_limit() {
		return gk_time_limit;
	}
	public void setGk_time_limit(String gkTimeLimit) {
		gk_time_limit = gkTimeLimit;
	}
	public String getGk_range() {
		return gk_range;
	}
	public void setGk_range(String gkRange) {
		gk_range = gkRange;
	}
	public String getGk_proc() {
		return gk_proc;
	}
	public void setGk_proc(String gkProc) {
		gk_proc = gkProc;
	}	
	public String getGk_duty_dept() {
		return gk_duty_dept;
	}
	public void setGk_duty_dept(String gkDutyDept) {
		gk_duty_dept = gkDutyDept;
	}
	public String getGk_input_dept() {
		return gk_input_dept;
	}
	public void setGk_input_dept(String gkInputDept) {
		gk_input_dept = gkInputDept;
	}
}
