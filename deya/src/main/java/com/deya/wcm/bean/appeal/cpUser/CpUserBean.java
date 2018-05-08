/**
 * 注册用户
 */
package com.deya.wcm.bean.appeal.cpUser;

import java.io.Serializable;

/**
 * @author 王磊
 *
 */
public class CpUserBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1967980864292154584L;

	private int user_id; //用户ID
	
	private int dept_id; //部门ID
	
	public int getUser_id() {
		return user_id;
	}
	
	public void setUser_id(int userId) {
		user_id = userId;
	}
	
	public int getDept_id() {
		return dept_id;
	}
	
	public void setDept_id(int deptId) {
		dept_id = deptId;
	}
	
}
