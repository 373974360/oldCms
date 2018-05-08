package com.deya.wcm.dao;

import java.util.ArrayList;
import java.util.List;

import java.lang.reflect.Field;

public class LogUtil {
	
	public final static String AUDIT = "审核";
	public final static String DEAL = "处理";
	public final static String RELEASE = "发布";
	public final static String ADD = "添加";
	public final static String UPDATE = "修改";
	public final static String DELETE = "删除";
	public final static String PHPSICALLY_DEL = "彻底删除";
	public final static String SAVE_SORT = "保存排序";
	public final static String MOVE = "移动";
	public final static String SHIFT = "转移";
	public final static String SUBMIT = "提交";
	public final static String LOGIN = "登录"; 
	public final static String LOGOUT = "退出";
	public final static String CANCEL = "撤销";
	
	private static LogUtil util = new LogUtil(); 
	
	public static List<String> getAllString(){
		List<String> ret = new ArrayList<String>();
		Class<LogUtil> c = LogUtil.class;
		
		Field[] arr_f = c.getFields();
		for(Field f :arr_f) {
			String s = "";
			try {
				s = (String)(f.get(util));
			} catch (Exception ex) {
				continue; // 出错不进行其他处理,全部抛出异常,则列表为空
			}
			ret.add(s);
		}
		return ret;
	}
	
	public static void main(String []args){
		List<String> lt = getAllString();
		for(String s : lt) {
			System.out.println(s);
		}
		System.out.println("------");
	}
}
