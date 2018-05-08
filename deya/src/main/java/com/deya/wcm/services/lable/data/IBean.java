package com.deya.wcm.services.lable.data;

import java.util.Map;

public interface IBean {

	/**
	 * 实体bean需要实现的方法，把它的属性和值存放成map形式
	 * @return
	 */
	public Map<String, String> toMap();
}
