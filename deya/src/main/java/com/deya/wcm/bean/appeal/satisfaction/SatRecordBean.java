package com.deya.wcm.bean.appeal.satisfaction;

public class SatRecordBean {
	private int rec_id = 0;
	private int sq_id = 0;
	private int sat_id = 0;
	private int sat_score = 0;
	private String add_dtime = "";
	public int getRec_id() {
		return rec_id;
	}
	public void setRec_id(int recId) {
		rec_id = recId;
	}
	public int getSq_id() {
		return sq_id;
	}
	public void setSq_id(int sqId) {
		sq_id = sqId;
	}
	public int getSat_id() {
		return sat_id;
	}
	public void setSat_id(int satId) {
		sat_id = satId;
	}
	public int getSat_score() {
		return sat_score;
	}
	public void setSat_score(int satScore) {
		sat_score = satScore;
	}
	public String getAdd_dtime() {
		return add_dtime;
	}
	public void setAdd_dtime(String addDtime) {
		add_dtime = addDtime;
	}
}
