package com.deya.wcm.jsonlistener;

public class JSONUtil {
	public Object getBean(String fullClassName) {
		try {
			return Class.forName(fullClassName).newInstance();
		} catch (Exception e) {
			return new Object();
		}
	}
}
