package com.deya.wcm.bean.system.template;

import java.io.Serializable;

/**
 * 模板资源管理类
 * @author 朱亮
 * @version 1.0
 * @date   2011-3-24 上午11:46:50
 */

public class TemplateResourcesBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5959524680485539641L;
	private String file_name = "";
	private String file_type = "";
	private String file_size = "";
	private String file_path = "";
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String filePath) {
		file_path = filePath;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String fileName) {
		file_name = fileName;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String fileType) {
		file_type = fileType;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String fileSize) {
		file_size = fileSize;
	}
}
