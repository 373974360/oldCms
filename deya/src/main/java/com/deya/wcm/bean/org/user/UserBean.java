package com.deya.wcm.bean.org.user;

public class UserBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6678713125659864514L;
	private int user_id = 0;
	private int dept_id;//部门ID
	private String dept_name = "";
	private int userlevel_value = 0;//级别数值
	private String user_realname = "";//用户真实姓名
	private String user_aliasname = "";//别名/曾用名
	private String user_photo = "";//照片
	private int sex;//性别0：女 1：男
	private String birthday = "";//出生年月日
	private String nation = "";//民族
	private String age;//年龄
	private int is_marriage;//婚否 0：未婚1：已婚
	private String natives = "";//籍贯
	private String functions = "";//职务
	private String degree = "";//学历
	private String colleges = "";//毕业院校
	private String graduation_time = "";//毕业时间
	private String professional = "";//所学专业
	private String health = "";//健康状况
	private String tel = "";//电话
	private String phone = "";//手机
	private String email = "";
	private String address = "";
	private String postcode = "";
	private String idcard = "";
	private int user_status = 0;//用户状态 0：正常	 1：停用	-1：删除
	private String resume  = "";//简历
	private String user_memo = "";//备注
	private int is_publish = 0;//发布到公务员名录
	private String photo = "";//照片
	private String politics_status = "";//政治面貌
	private String bein_dept = "";//所属部门
	private String work_desc = "";//工作分工
	private String summary = "";//个人简介
	private int sort = 999;
	private String to_work_time = "";//参加工作时间
	
	public String getTo_work_time() {
		return to_work_time;
	}
	public void setTo_work_time(String toWorkTime) {
		to_work_time = toWorkTime;
	}
	public int getIs_publish() {
		return is_publish;
	}
	public void setIs_publish(int isPublish) {
		is_publish = isPublish;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getPolitics_status() {
		return politics_status;
	}
	public void setPolitics_status(String politicsStatus) {
		politics_status = politicsStatus;
	}
	public String getBein_dept() {
		return bein_dept;
	}
	public void setBein_dept(String beinDept) {
		bein_dept = beinDept;
	}
	public String getWork_desc() {
		return work_desc;
	}
	public void setWork_desc(String workDesc) {
		work_desc = workDesc;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String deptName) {
		dept_name = deptName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getColleges() {
		return colleges;
	}
	public void setColleges(String colleges) {
		this.colleges = colleges;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFunctions() {
		return functions;
	}
	public void setFunctions(String functions) {
		this.functions = functions;
	}
	public String getGraduation_time() {
		return graduation_time;
	}
	public void setGraduation_time(String graduation_time) {
		this.graduation_time = graduation_time;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
	public int getIs_marriage() {
		return is_marriage;
	}
	public void setIs_marriage(int is_marriage) {
		this.is_marriage = is_marriage;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getNatives() {
		return natives;
	}
	public void setNatives(String natives) {
		this.natives = natives;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getProfessional() {
		return professional;
	}
	public void setProfessional(String professional) {
		this.professional = professional;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getUser_aliasname() {
		return user_aliasname;
	}
	public void setUser_aliasname(String user_aliasname) {
		this.user_aliasname = user_aliasname;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_memo() {
		return user_memo;
	}
	public void setUser_memo(String user_memo) {
		this.user_memo = user_memo;
	}
	public String getUser_photo() {
		return user_photo;
	}
	public void setUser_photo(String user_photo) {
		this.user_photo = user_photo;
	}
	public String getUser_realname() {
		return user_realname;
	}
	public void setUser_realname(String user_realname) {
		this.user_realname = user_realname;
	}
	public int getUser_status() {
		return user_status;
	}
	public void setUser_status(int user_status) {
		this.user_status = user_status;
	}
	public int getUserlevel_value() {
		return userlevel_value;
	}
	public void setUserlevel_value(int userlevelValue) {
		userlevel_value = userlevelValue;
	}	
}
