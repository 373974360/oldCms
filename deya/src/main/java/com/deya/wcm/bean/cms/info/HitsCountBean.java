package com.deya.wcm.bean.cms.info;


/**
 * @author 符江波
 * @version 1.0
 * @date 2011-6-14 下午01:24:34
 */
public class HitsCountBean implements java.io.Serializable,Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7656316283038335035L;
	protected int info_id = 0;// bigint 20
	protected int cat_id = 0;// bigint 20
    protected int hits = 0;// bigint 20
	protected String cat_cname = "";
	protected String title = "";// varchar 250

    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_cname() {
        return cat_cname;
    }

    public void setCat_cname(String cat_cname) {
        this.cat_cname = cat_cname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }
}
