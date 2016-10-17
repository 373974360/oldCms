package com.deya.wcm.services.model;

public class WcmZykFile {
   
	private int id ;
	private int info_id;
	private String file_id;
	private int fileSize;
	private String fileSufix;
	private String fileName;
	private String businessId;
	private String fieldName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInfo_id() {
		return info_id;
	}
	public void setInfo_id(int infoId) {
		info_id = infoId;
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String fileId) {
		file_id = fileId;
	}
	public String getFileSufix() {
		return fileSufix;
	}
	public void setFileSufix(String fileSufix) {
		this.fileSufix = fileSufix;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	
}
