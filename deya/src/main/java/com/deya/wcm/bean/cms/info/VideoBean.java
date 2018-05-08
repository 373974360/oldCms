package com.deya.wcm.bean.cms.info;

/**
 * @author 符江波
 * @version 1.0
 * @date 2011-6-14 下午02:07:45
 */
public class VideoBean extends GKInfoBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4389963487358178950L;
	private int att_id; //附件ID
	private String video_path = ""; //视频路径
	private int play_time; //视频时长
	private String info_content = ""; //内容

	// get方法
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
