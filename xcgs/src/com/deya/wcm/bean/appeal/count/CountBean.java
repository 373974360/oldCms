package com.deya.wcm.bean.appeal.count;


/**
 *  诉求统计分析Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 诉求统计分析Bean类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * 
 * @author lisupei
 * @version 1.0
 * * 2012-6-27 添加节点深度属性
 */
public class CountBean{

	
	private String business_name;   //业务名称
	  private String purpose_name;   //诉求目的
	  private String dept_name;      //处理部门
	  private int dept_id;      //处理部门id
	  private Integer node_level; //节点深度
	  private String user_realname;  //处理人
	  private String user_id;  //处理人
	  
	          
	 
	  private String countall; //总件数  == count;//全部信件
	  private String countdai; //待处理
	  private String countchu; //处理中 
	  private String countshen; //待审核
	  private String countend; //已办结 
	  private String countwei; //办结率
	  private String countendp; //办结率
	  
	  private String count;//全部信件 
	  private String count_normal;//正常信件
	  private String count_invalid;//无效信件
	  private String count_repeat;//重复信件
	  private String count_nohandle;//不予受理信件 
	  
	  private String count_over;//超期件
	  private String count_warn;//预警件
	  private String count_yellow;//黄牌件
	  private String count_red;//红牌件
	  private String count_supervise;//督办件 
	  private String count_limit;//延期件 
	  private float f_temp_count = 0;
	  
	 
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int deptId) {
		dept_id = deptId;
	}
	public String getUser_id() {
			return user_id;
		}
	public void setUser_id(String userId) {
			user_id = userId;
	}
		
    public String getUser_realname() {
		return user_realname;
	}
	public void setUser_realname(String userRealname) {
		user_realname = userRealname;
	}
	  
	public String getCount_over() {
		return count_over;
	}
	public void setCount_over(String countOver) {
		count_over = countOver;
	}
	public String getCount_warn() {
		return count_warn;
	}
	public void setCount_warn(String countWarn) {
		count_warn = countWarn;
	}
	public String getCount_yellow() {
		return count_yellow;
	}
	public void setCount_yellow(String countYellow) {
		count_yellow = countYellow;
	}
	public String getCount_red() {
		return count_red;
	}
	public void setCount_red(String countRed) {
		count_red = countRed;
	}
	public String getCount_supervise() {
		return count_supervise;
	}
	public void setCount_supervise(String countSupervise) {
		count_supervise = countSupervise;
	}
	        
	public String getCountall() {
		return countall;
	}
	public void setCountall(String countall) {
		this.countall = countall;
	}
	public String getCountdai() {
		return countdai;
	}
	public void setCountdai(String countdai) {
		this.countdai = countdai;
	}
	public String getCountchu() {
		return countchu;
	}
	public void setCountchu(String countchu) {
		this.countchu = countchu;
	}
	public String getCountshen() {
		return countshen;
	}
	public void setCountshen(String countshen) {
		this.countshen = countshen;
	}
	public String getCountend() {
		return countend;
	}
	public void setCountend(String countend) {
		this.countend = countend;
	}
	public String getCountendp() {
		return countendp;
	}
	public void setCountendp(String countendp) {
		this.countendp = countendp;
	}
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String businessName) {
		business_name = businessName;
	}
	public String getPurpose_name() {
		return purpose_name;
	}
	public void setPurpose_name(String purposeName) {
		purpose_name = purposeName;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getCount_normal() {
		return count_normal;
	}
	public void setCount_normal(String countNormal) {
		count_normal = countNormal;
	}
	public String getCount_invalid() {
		return count_invalid;
	}
	public void setCount_invalid(String countInvalid) {
		count_invalid = countInvalid;
	}
	public String getCount_repeat() {
		return count_repeat;
	}
	public void setCount_repeat(String countRepeat) {
		count_repeat = countRepeat;
	}
	public String getCount_nohandle() {
		return count_nohandle;
	}
	public void setCount_nohandle(String countNohandle) {
		count_nohandle = countNohandle;
	}
	public String getCount_limit() {
		return count_limit;
	}
	public void setCount_limit(String countLimit) {
		count_limit = countLimit;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String deptName) {
		dept_name = deptName;
	}
	public String getCountwei() {
		return countwei;
	}
	public void setCountwei(String countwei) {
		this.countwei = countwei;
	}	
	public Integer getNode_level() {
		return node_level;
	}
	public void setNode_level(Integer nodeLevel) {
		node_level = nodeLevel;
	}
	public float getF_temp_count() {
		return f_temp_count;
	}
	public void setF_temp_count(float fTempCount) {
		f_temp_count = fTempCount;
	}
}
