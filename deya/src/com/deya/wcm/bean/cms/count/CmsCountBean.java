package com.deya.wcm.bean.cms.count;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 站点统计信息bean
 * @author liqi
 *
 */
public class CmsCountBean implements Serializable{
	
	public CmsCountBean(String input_user, String user_name, String count){
		int i_count = 0;
		int i_input_user = 0;
		setUser_name(user_name);
		try{ 
			i_count = Integer.parseInt(count);
			i_input_user = Integer.parseInt(input_user);
			
			setCount(i_count);
			setInput_user(i_input_user);
		}catch(NumberFormatException ne){
			
		}
	}
	
	public CmsCountBean(){
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1256934576049508129L;
	
	// 发稿率保留的位数
	private final static int DIGIT = 2;
	// 数字格式化对象,用于对发稿率进行格式化
	private static NumberFormat nf = null;
	static{
		nf = new DecimalFormat();
		nf.setMaximumFractionDigits(DIGIT);
		nf.setRoundingMode(RoundingMode.HALF_UP);
	}
	
	private String user_name = ""; //用户名
	private int input_user; //用户id
	private int count;	//统计结果   --仅用于对应ibatis取得结果的临时存放
	private int cat_id; // 栏目信息id
	private String cat_name = ""; //栏目信息名称
	
	private int allCount; //全部信息条数(按照信息类型划分)
	private int hostInfoCount; //主信息条数
	private int refInfoCount; //引用信息条数
	private int linkInfoCount; //连接信息条数
	
	private int inputCount; // 录入信息总数(包括已发布和未发布)
	private int releasedCount; // 发布信息条数
    private int checkCount; // 待审信息条数
    private int backCount; // 退稿信息条数
    private int caogaoCount; // 退稿信息条数
	private String releaseRate; // 信息发布率
	
	private int picInfoCount = 0; //已发信息 并且有图片的信息
	
	private String time; //统计时间
	
	public String getUser_name() {
		return user_name;
	}
	
	public int getInput_user() {
		return input_user;
	}
	
	public int getCount() {
		return count;
	}
	
	public int getCat_id() {
		return cat_id;
	}
	
	public String getCat_name() {
		return cat_name;
	}

	public int getAllCount() {
		return allCount;
	}

	public int getHostInfoCount() {
		return hostInfoCount;
	}

	public int getRefInfoCount() {
		return refInfoCount;
	}

	public int getLinkInfoCount() {
		return linkInfoCount;
	}

	public String getTime() {
		return time;
	}

	public void setUser_name(String userName) {
		user_name = userName;
	}

	public void setInput_user(int inputUser) {
		input_user = inputUser;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setCat_id(int catId) {
		cat_id = catId;
	}

	public void setCat_name(String catName) {
		cat_name = catName;
	}

	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	
	/**
	 *  自动设置全部信息统计数
	 */
	private void setAllCount() {
		this.allCount = getHostInfoCount() + getRefInfoCount() + getLinkInfoCount();
	}

	public void setHostInfoCount(int hostInfoCount) {
		this.hostInfoCount = hostInfoCount;
		setAllCount();
	}

	public void setRefInfoCount(int refInfoCount) {
		this.refInfoCount = refInfoCount;
		setAllCount();
	}

	public void setLinkInfoCount(int linkInfoCount) {
		this.linkInfoCount = linkInfoCount;
		setAllCount();
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getInputCount() {
		return inputCount;
	}

	public int getReleasedCount() {
		return releasedCount;
	}

	public void setInputCount(int inputCount) {
		this.inputCount = inputCount;
	}

	public void setReleasedCount(int releasedCount) {
		this.releasedCount = releasedCount;
	}

	/**
	 * 设置发布率,在调用本方法前请先确保已发和全部统计都已经设值
	 * 否则,会出现发布率为0%的情况
	 */
	public void setReleaseRate() {
		if(getInput_user() != 0){
			float i_rate = (float)releasedCount * 100 / inputCount;
			releaseRate = nf.format(i_rate);
		} else {
			releaseRate = "0";
		}
	}

    public int getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(int checkCount) {
        this.checkCount = checkCount;
    }

    public int getBackCount() {
        return backCount;
    }

    public void setBackCount(int backCount) {
        this.backCount = backCount;
    }

    public int getCaogaoCount() {
        return caogaoCount;
    }

    public void setCaogaoCount(int caogaoCount) {
        this.caogaoCount = caogaoCount;
    }

    public String getReleaseRate() {
		return releaseRate;
	}

	public int getPicInfoCount() {
		return picInfoCount;
	}

	public void setPicInfoCount(int picInfoCount) {
		this.picInfoCount = picInfoCount;
	}
}
