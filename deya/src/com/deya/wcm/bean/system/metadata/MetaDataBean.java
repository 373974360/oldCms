package com.deya.wcm.bean.system.metadata;
/**
 * 元数据Bean
 */
public class MetaDataBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5590847880439954472L;
	private int meta_id = 0;
	private String meta_ename = "";

	private String meta_sname = "";
	private String meta_cname = "";
	private String meta_define = "";
	private String meta_datatype = "";
	private String meta_codomain = "";
	private int meta_iselect = 0;//0：可选 1必选
	private int meta_maxtimes = 0;//0：为不限制次数
	private String meta_sample = "";
	private String meta_type = "";//element：元数据元素 entity：元数据实体
	private int parent_id = 0;
	private int is_core = 0;//0 核心元数据  1 业务元数据
	public int getMeta_id() {
		return meta_id;
	}
	public void setMeta_id(int metaId) {
		meta_id = metaId;
	}
	public String getMeta_ename() {
		return meta_ename;
	}
	public void setMeta_ename(String metaEname) {
		meta_ename = metaEname;
	}
	public String getMeta_sname() {
		return meta_sname;
	}
	public void setMeta_sname(String metaSname) {
		meta_sname = metaSname;
	}
	public String getMeta_cname() {
		return meta_cname;
	}
	public void setMeta_cname(String metaCname) {
		meta_cname = metaCname;
	}
	public String getMeta_define() {
		return meta_define;
	}
	public void setMeta_define(String metaDefine) {
		meta_define = metaDefine;
	}
	public String getMeta_datatype() {
		return meta_datatype;
	}
	public void setMeta_datatype(String metaDatatype) {
		meta_datatype = metaDatatype;
	}
	public String getMeta_codomain() {
		return meta_codomain;
	}
	public void setMeta_codomain(String metaCodomain) {
		meta_codomain = metaCodomain;
	}
	public int getMeta_iselect() {
		return meta_iselect;
	}
	public void setMeta_iselect(int metaIselect) {
		meta_iselect = metaIselect;
	}
	public int getMeta_maxtimes() {
		return meta_maxtimes;
	}
	public void setMeta_maxtimes(int metaMaxtimes) {
		meta_maxtimes = metaMaxtimes;
	}
	public String getMeta_sample() {
		return meta_sample;
	}
	public void setMeta_sample(String metaSample) {
		meta_sample = metaSample;
	}
	public String getMeta_type() {
		return meta_type;
	}
	public void setMeta_type(String metaType) {
		meta_type = metaType;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parentId) {
		parent_id = parentId;
	}
	public int getIs_core() {
		return is_core;
	}
	public void setIs_core(int isCore) {
		is_core = isCore;
	}
	
}
