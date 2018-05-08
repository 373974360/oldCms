package com.deya.wcm.bean.cms.count;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * 站点统计信息bean
 * @author liqi
 *
 */
public class SiteCountBean implements Serializable{
	
	public SiteCountBean(){
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
	
	private String site_name = "";//站点名称
	private String site_id = "";//站点Id
	private String cat_name = "";//栏目名称
	private int cat_id = 0;//栏目Id
	private String user_name = ""; //用户名
	private int input_user; //用户id
	private int dept_id;//部门ID 
	private String dept_name;//部门名称
	private String tree_position;//部门tree_position
	private int icount = 0;	//统计结果   --仅用于对应ibatis取得结果的临时存放
	
	private int allCount = 0; //全部信息条数(按照信息类型划分)
	private int hostInfoCount = 0; //主信息条数
	private int refInfoCount = 0; //引用信息条数
	private int linkInfoCount = 0; //连接信息条数
	
	private int inputCount = 0; // 录入信息总数(包括已发布和未发布)
	private int releasedCount = 0; // 发布信息条数
	private int picReleasedCount = 0; // 发布信息条数 并且有图片的信息
    private int checkCount; // 待审信息条数
    private int backCount; // 退稿信息条数
    private int caogaoCount; // 退稿信息条数
	private String releaseRate; // 信息发布率
	
	private boolean is_leaf = true;// 此字段仅供前台显示时使用
	private List<SiteCountBean> child_list;// 此字段仅供前台显示时使用
	
	private String time; //统计时间
	
	public String getUser_name() {
		return user_name;
	}
	
	public int getInput_user() {
		return input_user;
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
		if(inputCount != 0){
			float i_rate = (float)releasedCount * 100 / inputCount;
			releaseRate = nf.format(i_rate);
		} else { 
			releaseRate = "0";
		}
		releaseRate += "%"; 
	}  
	 
	public String getReleaseRate() {
		return releaseRate;
	}

	public int getIcount() {
		return icount;
	}

	public void setIcount(int icount) {
		this.icount = icount;
	}

	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int deptId) {
		dept_id = deptId;
	}

	public int getPicReleasedCount() {
		return picReleasedCount;
	}

	public void setPicReleasedCount(int picReleasedCount) {
		this.picReleasedCount = picReleasedCount;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String deptName) {
		dept_name = deptName;
	}

	public boolean isIs_leaf() {
		return is_leaf;
	}

	public void setIs_leaf(boolean isLeaf) {
		is_leaf = isLeaf;
	}

	public List<SiteCountBean> getChild_list() {
		return child_list;
	}

	public void setChild_list(List<SiteCountBean> childList) {
		child_list = childList;
	}
	
	public static void main(String arr[]){
		
		int releasedCount = 119;
		int inputCount = 127;
		
		float i_rate = (float)releasedCount * 100 / inputCount;
		String releaseRate = nf.format(i_rate);
		System.out.println(releaseRate);
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String siteName) {
		site_name = siteName;
	}

	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String siteId) {
		site_id = siteId;
	}

	public String getCat_name() {
		return cat_name;
	}

	public void setCat_name(String catName) {
		cat_name = catName;
	}

	public int getCat_id() {
		return cat_id;
	}

	public void setCat_id(int catId) {
		cat_id = catId;
	}

	public String getTree_position() {
		return tree_position;
	}

	public void setTree_position(String treePosition) {
		tree_position = treePosition;
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
}
