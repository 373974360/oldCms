package com.deya.wcm.bean.zwgk.count;

public class GKysqCountBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9027633750624474062L;

	/**
	 * 取得征询第三方时do_state设置为1000,
	 * 取得延期时do_state设置为2000,取得超期时do_state设置为3000
	 */
	private int do_state = 0;
	private int count = 0; // 数据库取得的统计值
	
	// 申请单状态统计字段
	private String node_id = ""; // 节点ID
	private String node_name = ""; // 节点名称
	private int totalCnt = 0;	// 申请信息总数
	private int unAcceptCnt = 0; // 待处理
	private int acceptedCnt = 0; // 已处理
	private int replyCnt = 0; // 以回复
	private int invalidCnt = 0;	// 置为无效
	
	private int thirdCnt = 0; // 征询第三方
	private int delayCnt = 0; // 延期
	private int timeoutCnt = 0; // 超期
	private String put_dtime = "";
	
	private String parent_id = "";	// 父节点ID
	
	// 申请单处理方式统计字段
	private int reply_type = 0; // 回复类型字段
	private int type_total = 0; // 处理方式统计总数
	
	private int qbgk_cnt = 0; // 全部公开数目
	private int bfgk_cnt = 0; // 部分公开数目
	private int bygk_cnt = 0; // 不予公开数目
	private int fbdwxx_cnt = 0; // 非本单位信息数目
	private int xxbcz_cnt = 0; // 信息不存在数目
	
	public String getNode_name() {
		return node_name;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public int getUnAcceptCnt() {
		return unAcceptCnt;
	}
	public int getAcceptedCnt() {
		return acceptedCnt;
	}
	public int getReplyCnt() {
		return replyCnt;
	}
	public int getInvalidCnt() {
		return invalidCnt;
	}
	public int getThirdCnt() {
		return thirdCnt;
	}
	public int getDelayCnt() {
		return delayCnt;
	}
	public int getTimeoutCnt() {
		return timeoutCnt;
	}
	public String getParent_id() {
		return parent_id;
	}
	public String getId() {
		return node_id;
	}
	public void setNode_name(String nodeName) {
		node_name = nodeName;
	}
	public void setTotalCnt() {
		this.totalCnt = unAcceptCnt + acceptedCnt + replyCnt + invalidCnt;
	}
	public void setUnAcceptCnt(int unAcceptCnt) {
		this.unAcceptCnt = unAcceptCnt;
		setTotalCnt();
	}
	public void setAcceptedCnt(int acceptedCnt) {
		this.acceptedCnt = acceptedCnt;
		setTotalCnt();
	}
	public void setReplyCnt(int replyCnt) {
		this.replyCnt = replyCnt;
		setTotalCnt();
	}
	public void setInvalidCnt(int invalidCnt) {
		this.invalidCnt = invalidCnt;
		setTotalCnt();
	}
	public void setThirdCnt(int thirdCnt) {
		this.thirdCnt = thirdCnt;
	}
	public void setDelayCnt(int delayCnt) {
		this.delayCnt = delayCnt;
	}
	public void setTimeoutCnt(int timeoutCnt) {
		this.timeoutCnt = timeoutCnt;
	}
	public void setParent_id(String parentId) {
		parent_id = parentId;
	}
	public void setId(String id) {
		this.node_id = id;
	}
	public int getCount() {
		return count;
	}
	public int getDo_state() {
		return do_state;
	}
	public String getNode_id() {
		return node_id;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setDo_state(int doState) {
		do_state = doState;
	}
	public void setNode_id(String nodeId) {
		node_id = nodeId;
	}
	public String getPut_dtime() {
		return put_dtime;
	}
	public void setPut_dtime(String putDtime) {
		put_dtime = putDtime;
	}
	public int getQbgk_cnt() {
		return qbgk_cnt;
	}
	public int getBfgk_cnt() {
		return bfgk_cnt;
	}
	public int getBygk_cnt() {
		return bygk_cnt;
	}
	public int getFbdwxx_cnt() {
		return fbdwxx_cnt;
	}
	public int getXxbcz_cnt() {
		return xxbcz_cnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public void setQbgk_cnt(int qbgkCnt) {
		qbgk_cnt = qbgkCnt;
		setType_total();
	}
	public void setBfgk_cnt(int bfgkCnt) {
		bfgk_cnt = bfgkCnt;
		setType_total();
	}
	public void setBygk_cnt(int bygkCnt) {
		bygk_cnt = bygkCnt;
		setType_total();
	}
	public void setFbdwxx_cnt(int fbdwxxCnt) {
		fbdwxx_cnt = fbdwxxCnt;
		setType_total();
	}
	public void setXxbcz_cnt(int xxbczCnt) {
		xxbcz_cnt = xxbczCnt;
		setType_total();
	}
	public int getReply_type() {
		return reply_type;
	}
	public int getType_total() {
		return type_total;
	}
	public void setReply_type(int replyType) {
		reply_type = replyType;
	}
	public void setType_total() {
		type_total = qbgk_cnt + bfgk_cnt + bygk_cnt + fbdwxx_cnt + xxbcz_cnt;
	}
	public void setType_total(int typeTotal) {
		
	}
	
	
}
