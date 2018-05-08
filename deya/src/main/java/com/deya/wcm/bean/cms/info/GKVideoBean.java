package com.deya.wcm.bean.cms.info;

public class GKVideoBean extends GKInfoBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6936506210209352607L;
	/**
	 * 
	 */
	
	private int pic_id; //自增ID
	private int att_id; //附件ID
	private String video_path; //视频路径
	private int play_time; //视频时长
	private String info_content; //内容
	
	// get方法
	public int getPic_id() {
		return pic_id;
	}
	public int getAtt_id() {
		return att_id;
	}
	public String getVideo_path() {
		return video_path;
	}
	public int getPlay_time() {
		return play_time;
	}
	public String getInfo_content() {
		return info_content;
	}
	
	// set方法
	public void setPic_id(int picId) {
		pic_id = picId;
	}
	public void setAtt_id(int attId) {
		att_id = attId;
	}
	public void setVideo_path(String videoPath) {
		video_path = videoPath;
	}
	public void setPlay_time(int playTime) {
		play_time = playTime;
	}
	public void setInfo_content(String infoContent) {
		info_content = infoContent;
	}
}
