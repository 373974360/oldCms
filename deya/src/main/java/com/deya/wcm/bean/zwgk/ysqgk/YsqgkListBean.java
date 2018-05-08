package com.deya.wcm.bean.zwgk.ysqgk;

/**
 * 依申请 公开信息列表Bean
 * <p>Title: CicroDate</p>
 * <p>Description: 依申请 公开信息列表Bean</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author zhangqiang
 * @version 1.0
 * @date 2011-07-28 晚20:33:34
 */
public class YsqgkListBean implements java.io.Serializable{

	private static final long serialVersionUID = -7344433054755245726L;
	private int ysq_id=0;
	private String ysq_code="";
	private int ysq_type=0;
	private String name="";
	private String put_dtime="";
	private int publish_state=0;
	private int is_third=0;
	private int is_extend=0;
	private int timeout_flag=0;
	private String node_name="";
	private int do_state = 0;
    private String reply_dtime;
	
	
	public int getYsq_id() {
		return ysq_id;
	}
	public String getYsq_code() {
		return ysq_code;
	}
	public int getYsq_type() {
		return ysq_type;
	}
	public String getName() {
		return name;
	}
	public String getPut_dtime() {
		return put_dtime;
	}
	public int getPublish_state() {
		return publish_state;
	}
	public int getIs_third() {
		return is_third;
	}
	public int getIs_extend() {
		return is_extend;
	}
	public int getTimeout_flag() {
		return timeout_flag;
	}
	public void setYsq_id(int ysqId) {
		ysq_id = ysqId;
	}
	public void setYsq_code(String ysqCode) {
		ysq_code = ysqCode;
	}
	public void setYsq_type(int ysqType) {
		ysq_type = ysqType;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPut_dtime(String putDtime) {
		put_dtime = putDtime;
	}
	public void setPublish_state(int publishState) {
		publish_state = publishState;
	}
	public void setIs_third(int isThird) {
		is_third = isThird;
	}
	public void setIs_extend(int isExtend) {
		is_extend = isExtend;
	}
	public void setTimeout_flag(int timeoutFlag) {
		timeout_flag = timeoutFlag;
	}
	public String getNode_name() {
		return node_name;
	}
	public void setNode_name(String nodeName) {
		node_name = nodeName;
	}
	public int getDo_state() {
		return do_state;
	}
	public void setDo_state(int doState) {
		do_state = doState;
	}

    public String getReply_dtime() {
        return reply_dtime;
    }

    public void setReply_dtime(String reply_dtime) {
        this.reply_dtime = reply_dtime;
    }
}