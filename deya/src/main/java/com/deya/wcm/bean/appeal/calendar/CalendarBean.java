package com.deya.wcm.bean.appeal.calendar;

 
/**
 * 节假日管理Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 节假日管理Bean类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author yuduochao
 * @version 1.0
 * * 
 */
public class CalendarBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1851619970884172534L;
	
	private int ca_id;          // 主键id
	private String ca_name;	    // 节假日名字
	private String start_dtime; // 开始时间
	private String end_dtime;  	// 结束时间
	private int ca_flag;	    // 是否上班的标识     0：休假  1：补假---
   
	private int ca_type;        // 性质   0：其他       1：元旦       2：春节       3：清明节 
                                //      4：劳动节  5：端午节  6：中秋节  7：国庆节
	
	public int getCa_id() {
		return ca_id;
	}

	public void setCa_id(int caId) {
		ca_id = caId;
	}

	public String getCa_name() {
		return ca_name;
	}

	public void setCa_name(String caName) {
		ca_name = caName;
	}

	public String getStart_dtime() {
		return start_dtime;
	}

	public void setStart_dtime(String startDtime) {
		start_dtime = startDtime;
	}

	public String getEnd_dtime() {
		return end_dtime;
	}

	public void setEnd_dtime(String endDtime) {
		end_dtime = endDtime;
	}

	public int getCa_flag() {
		return ca_flag;
	}

	public void setCa_flag(int caFlag) {
		ca_flag = caFlag;
	}

	public int getCa_type() {
		return ca_type;
	}

	public void setCa_type(int caType) {
		ca_type = caType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
