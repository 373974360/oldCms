package com.deya.wcm.bean.zwgk.export;

import java.util.ArrayList;
import java.util.List;

public class ExportDept {
   
	private String cateId = ""; //栏目Id
	private String catName = ""; //栏目名称
	private int countInfo = 0;  //信息条数
	private List<ExportInfo> exportInfo = new ArrayList<ExportInfo>();  //列表信息
	
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public int getCountInfo() {
		return countInfo;
	}
	public void setCountInfo(int countInfo) {
		this.countInfo = countInfo;
	}
	public List<ExportInfo> getExportInfo() {
		return exportInfo;
	}
	public void setExportInfo(List<ExportInfo> exportInfo) {
		this.exportInfo = exportInfo;
	}
	public String getCateId() {
		return cateId;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	
	
	
}
