package com.deya.wcm.bean.survey;
/**
 * 统计数据Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 统计数据Bean</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class StatisticsBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3337185805933363838L;
	private String item_id = "";
	private String item_num = "";
	private int counts = 0;
	private String proportion = "";//计算出来的百分比
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getItem_num() {
		return item_num;
	}
	public void setItem_num(String item_num) {
		this.item_num = item_num;
	}
	public String getProportion() {
		return proportion;
	}
	public void setProportion(String proportion) {
		this.proportion = proportion;
	}
}
