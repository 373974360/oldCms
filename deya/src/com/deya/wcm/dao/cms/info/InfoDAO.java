package com.deya.wcm.dao.cms.info;

//针对科委数据库修改
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.cms.count.InfoAccessBean;
import com.deya.wcm.bean.cms.count.InfoCountBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.cms.info.RelatedInfoBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.user.UserRegisterBean;
import com.deya.wcm.bean.system.formodel.ModelBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.zwgk.info.GKInfoDAO;
import com.deya.wcm.db.BoneDataSourceFactory;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.server.ServerManager;

/**
 * 基础信息的数据库操作类，被各个从表所引用
 * @author zhuliang
 * @version 1.0
 * @date   2011-6-14 下午05:07:06
 */
public class InfoDAO {
	/**
	 * 得到各部门节点信息量统计(已发布的)
	 * @param Map	 * 
	 * @return List<UserRegisterBean>
	 */
	@SuppressWarnings("unchecked")
	public static List<InfoCountBean> getInfoTotalForApp(Map<String,String> m)
	{
		return DBManager.queryFList("getInfoTotalForApp", m);
	}
	
	/**
	 * 得到各站点，部门所有内容的访问量统计排行
	 * @param Map	 * 
	 * @return List<UserRegisterBean>
	 */
	@SuppressWarnings("unchecked")
	public static List<InfoCountBean> getSiteAccessStatistics(Map<String,String> m)
	{
		return DBManager.queryFList("getSiteAccessStatistics", m);
	}
	
	/**
	 * 得到站点按人员统计信息发布量
	 * @param Map	 * 
	 * @return List<UserRegisterBean>
	 */
	@SuppressWarnings("unchecked")
	public static List<InfoCountBean> getInfoTotalForSiteUser(Map<String,String> m)
	{
		return DBManager.queryFList("getInfoTotalForSiteUser", m);
	}
	
	/**
	 * 根据条件得到信息列表（前台使用）
	 * @param Map	 * 
	 * @return List<UserRegisterBean>
	 */
	@SuppressWarnings("unchecked")
	public static List<InfoBean> getBroInfoList(Map<String, String> map)
	{ 
		String type = BoneDataSourceFactory.getDataTypeName();
		if(type.equals("mssql")){//是mssql数据库
			
			String page_count = getBroInfoCount(map);
			map.put("page_count",page_count+"");//总条数
			
			String start_num = (String)map.get("start_num");//开始条数
			String page_size = (String)map.get("page_size");//页面条数
			String page_cuur = "1";//当前页数
			page_cuur = (Integer.valueOf(start_num)/Integer.valueOf(page_size)+1)+"";
			map.put("page_cuur",page_cuur+"");//当前页数
			
			String orderby = (String)map.get("orderby");//排序方式
			String orderby_tempt_asc = orderby.replaceAll(" desc", " XXX").replaceAll(" asc", " desc").replaceAll(" XXX", " asc");
			map.put("orderby_tempt_asc",orderby_tempt_asc+"");
		    String orderby_tempt = orderby.replaceAll("ci.", "tempt.");
		    map.put("orderby_tempt",orderby_tempt+"");
		}
		return DBManager.queryFList("getBroInfoList", map);
	}
	
	/**
	 * 根据条件得到信息总数（前台使用）
	 * @param Map	 * 
	 * @return String
	 */
	public static String getBroInfoCount(Map<String, String> map)
	{
		return DBManager.getString("getBroInfoCount", map);
	}
	
	/**
	 * 得到公开指南或报报的汇总信息
	 * @param Map	
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<InfoBean> getGKZNInfoList(Map<String, String> map)
	{
		return DBManager.queryFList("getGKZNInfoList", map);
	}
	
	/**
	 * 得到公开指南或报报的汇总总数
	 * @param Map	 * 
	 * @return String
	 */
	public static String getGKZNInfoCount(Map<String, String> map)
	{
		return DBManager.getString("getGKZNInfoCount", map);
	}

	public static InfoBean getInfoById(String info_id, String site_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("site_id", site_id);
		map.put("info_id", info_id);
		return (InfoBean)DBManager.queryFObj("selectInfoBeanById", map);
	}
	
	public static InfoBean getInfoById(String info_id){
		Map<String, String> map = new HashMap<String, String>();	
		map.put("info_id", info_id);
		return (InfoBean)DBManager.queryFObj("selectInfoBeanById", map);
	}
	
	/**
	 * 根据条件得到所有录入人信息
	 * @param Map
	 * @param stl
	 * @return List<UserRegisterBean>
	 */
	@SuppressWarnings("unchecked")
	public static List<UserRegisterBean> getAllInuptUserID(Map<String,String> m)
	{
		return DBManager.queryFList("getAllInuptUserID", m);
	}
	
	
	
	/**
	 * add BaseInfomation Operate
	 * @param info
	 * @param stl
	 * @return boolean
	 */
	public static boolean addInfo(InfoBean info, SettingLogsBean stl){
		//根据信息的发布时间获取距离本栏目上一条信息的间隔天数

		if(DBManager.insert("addInfo", info))
		{			
			PublicTableDAO.insertSettingLogs("添加","主信息",info.getInfo_id()+"",stl);						
			return true;
		}else
			return false;
	}
	
	/**
	 * update BaseInfomation Operate
	 * @param info
	 * @param stl
	 * @return boolean
	 */
	public static boolean updateInfo(InfoBean info, SettingLogsBean stl){			
		if(DBManager.update("updateInfo", info)){			
			PublicTableDAO.insertSettingLogs("修改","主信息",info.getInfo_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 根据多个信息ID得到列表
	 * @param Map m
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<InfoBean> getInfoListByInfoIDS(Map<String,String> m)
	{
		return DBManager.queryFList("getInfoListByInfoIDS", m);
	}
	
	/**
	 * 转移信息
	 * @param Map m
	 * @return boolean
	 */
	public static boolean MoveInfo(InfoBean ib)
	{
		return DBManager.update("move_info", ib);		
	}
	
	/**
	 * 审核信息通过
	 * @param String info_ids
	 * @param String info_status
	 * @param String step_id
	 * @return boolean
	 */
	public static boolean passInfoStatus(String info_id,String info_status,String step_id,String released_dtime)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("info_id", info_id);
		m.put("info_status", info_status);
		if("8".equals(info_status))
			m.put("released_dtime", released_dtime);
		m.put("step_id", step_id);
		m.put("opt_dtime", DateUtil.getCurrentDateTime());
		
		if(DBManager.update("pass_info_status", m))
		{			
			return true;
		}else
			return false;
	}
	
	/**
	 * 审核信息不通过
	 * @param String info_ids
	 * @param String auto_desc 退回意见
	 * @param stl
	 * @return boolean
	 */
	public static boolean noPassInfoStatus(String info_ids,String auto_desc,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("info_ids", info_ids);
		m.put("opt_dtime", DateUtil.getCurrentDateTime());
		m.put("auto_desc", auto_desc);
		if(stl != null)
		{
			m.put("modify_user",stl.getUser_id()+"");
		}
		else
		{
			m.put("modify_user","0");
		}
		if(DBManager.update("noPass_info_status", m))
		{
			PublicTableDAO.insertSettingLogs("审核","信息状态为不通过",info_ids,stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * update BaseInfomation Operate,eg. -1删除,0正常,1归档
	 * @param infoIds
	 * @param stl
	 * @return boolean
	 */
	public static boolean updateInfoStatus(String infoIds, String status, SettingLogsBean stl){
		Map<String, String> map = new HashMap<String, String>();
		map.put("columnName", "final_status");
		map.put("columnValue", status);
		map.put("info_ids", infoIds);
		map.put("opt_dtime", DateUtil.getCurrentDateTime());
		if(DBManager.update("updateInfosStatusInteger", map)){
			PublicTableDAO.insertSettingLogs("修改","主信息最终状态更改为"+status,infoIds+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改指定字段值
	 * @param infoIds
	 * @param column
	 * @param value
	 * @return
	 */
	public static boolean updateInfoColumnValue(String infoIds, String column, String value){
		Map<String, String> map = new HashMap<String, String>();
		map.put("columnName", column);
		map.put("columnValue", value);
		map.put("info_ids", infoIds);
		map.put("opt_dtime", DateUtil.getCurrentDateTime());
		return DBManager.update("updateInfosStatusInteger", map);
	}
	
	/**
	 * update BaseInfomation Status. eg. 
	 *   0：草稿
	 *   1：退稿
	 *   2：待审
	 *   3：撤稿
	 *   4：待发
	 *   8：已发
	 * @param infoIds
	 * @param status
	 * @param stl
	 * @return boolean
	 */
	public static boolean updateInfoStatus2(InfoBean ib,String status, SettingLogsBean stl){
		Map<String, String> map = new HashMap<String, String>();
		map.put("columnName", "info_status");
		map.put("columnValue", status);
		map.put("info_id", ib.getInfo_id()+"");
		map.put("released_dtime",ib.getReleased_dtime());
		if(status == null || status.trim().equals("")){
			status = "0";
		}			
		map.put("opt_dtime", DateUtil.getCurrentDateTime());	
		return DBManager.update("updateInfosStatusInteger", map);
	}
	
	
	public static boolean deleteInfo(Map<String, String> map, SettingLogsBean stl){
		if(deleteInfo(map)){
			PublicTableDAO.insertSettingLogs("删除","彻底删除信息",map.get("info_ids")+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 根据站点ID删除主信息	
	 * @param map
	 * @param stl
	 * @return
	 */
	public static boolean deleteInfo(Map<String, String> map){
		return DBManager.delete("deleteInfo", map);	
	}
	
	/**
	 * 通过info_id删除模型(包括文章，图片，视频)信息	
	 * @param map
	 * @param stl
	 * @return
	 */
	public static boolean deleteInfoModel(Map<String, String> map)
	{
		if(DBManager.delete("delete_info_model", map))
		{			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 通过site_id删除模型(所有内容模型)	
	 * @param map
	 * @param stl
	 * @return
	 */
	public static boolean deleteInfoModelBySite(Map<String, String> map)
	{
		if(DBManager.delete("delete_info_model_bySiteid", map))
		{			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 通过cat_id,site_id清空回收站
	 * @param String cat_ids
	 * @param String site_id
	 * @param stl
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean clearTrach(String cat_ids,String app_id,String site_id,SettingLogsBean stl)
	{
		try{
			Map<String,String> m = new HashMap<String,String>();
			m.put("cat_ids", cat_ids);
			m.put("site_id", site_id);
			//首先根据栏目ID查到所有要删除信息关联的内容模型的表名
			List<ModelBean> model_list = DBManager.queryFList("getModelTableNamebyInfoID", m);
			if(model_list != null && model_list.size() > 0)
			{
				for(ModelBean mb : model_list)
				{//链接类型的没副表
					if(!"infoLink".equals(mb.getTable_name()) && !"".equals(mb.getTable_name()))
					{
						m.put("table_name", mb.getTable_name());
						DBManager.delete("clear_trash_model", m);
					}
				}
				//清空gk的公用表
				if("zwgk".equals(app_id))
				{
					GKInfoDAO.clearGKInfo(m);
					GKInfoDAO.clearGKResFile(m);
				}
				//删除主信息
				DBManager.delete("deleteInfo", m);
				PublicTableDAO.insertSettingLogs("删除","回收站信息 栏目",cat_ids+"",stl);				
			}
			
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 根据信息ID得到列表，包括被它引用的 用于在删除主信息时
	 * @param String delete_ids
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<InfoBean> getFromInfoListByIDS(String delete_ids)
	{		
		Map<String,String> m = new HashMap<String,String>();
		m.put("info_ids",delete_ids);
		return DBManager.queryFList("getFromInfoListByIDS", m);
	}
	
	/**
	 * 得到需要自动发布或撤消的信息
	 * @param 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<InfoBean> getAtuoPublishInfoList()
	{				
		Map<String,String> m = new HashMap<String,String>();
		m.put("current_time", DateUtil.getCurrentDateTime());
		String ip = ServerManager.LOCAL_IP;
		if(ip != null && !"".equals(ip) && !"127.0.0.1".equals(ip))
			m.put("server_ip", ServerManager.LOCAL_IP);
		return DBManager.queryFList("getAtuoPublishInfoList", m);
	}
	
	/**
	 * 修改自动发布撤消完成后的值
	 * @param Map m
	 * @return boolean
	 */
	public static boolean updateAutoPublishVal(Map<String,String> m)
	{
		return DBManager.update("update_auto_publish_val", m);	
	}
		
	/**
	 *根据信息ID得到图组内容模型的详细内容
	 * @param int info_id
	 * @return Stirng
	 */
	public static String getPicModelContent(int info_id)
	{
		return DBManager.getString("getPicModelContent", info_id);
	}
	
	/**
	 * 根据信息ID得到被引用列表
	 * @param String info_ids
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<InfoBean> getQuoteInfoList(String info_id)
	{				
		return DBManager.queryFList("getQuoteInfoList", info_id);
	}
	
	/**
	 * doSelect the Infomations count
	 * @param cat_id
	 * @param site_id
	 * @return int
	 */
	public static int getInfoCount(Map<String, String> map){
		return Integer.parseInt(DBManager.getString("getAllInfoCounts", map));
	}
	public static int getGKBZHInfoCount(Map<String, String> map){
		return Integer.parseInt(DBManager.getString("getAllGKBZHInfoCounts", map));
	}
	
	/**
	 * 批量发布静态内容页,根据栏目,站点和发布时间得到信息
	 * @param map
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public static List<InfoBean> batchPublishContentHtml(Map<String, String> map)
	{
		return DBManager.queryFList("batchPublishContentHtml", map);
	}
	
	@SuppressWarnings("unchecked")
	public static List<InfoBean> getInfoBeanList(Map<String, String> map){
		//System.out.println("getInfoBeanList  map----"+map);
		return DBManager.queryFList("selectInfoList", map);
	}
	public static List<InfoBean> getGKBZHInfoBeanList(Map<String, String> map){
		//System.out.println("getInfoBeanList  map----"+map);
		return DBManager.queryFList("selectGKBZHInfoList", map);
	}
	
	//相关信息处理
	public static boolean addRelatedInfo(RelatedInfoBean rinfo, SettingLogsBean stl){
		if(DBManager.insert("insertRelatedInfo", rinfo)){
			PublicTableDAO.insertSettingLogs("添加","相关信息",rinfo.getInfo_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	public static boolean updateRelatedInfo(RelatedInfoBean rinfo, SettingLogsBean stl){
		if(DBManager.update("updateRelatedInfo", rinfo)){
			PublicTableDAO.insertSettingLogs("修改","相关信息",rinfo.getInfo_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	public static boolean deleteRelatedInfo(Map<String, String> map, SettingLogsBean stl){
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("info_ids", rinfoIds);
		if(DBManager.delete("deleteRelatedInfo", map)){
			PublicTableDAO.insertSettingLogs("删除","相关信息","",stl);
			return true;
		}else
			return false;
	}
	
	@SuppressWarnings("unchecked")
	public static List<RelatedInfoBean> getRelatedInfoList(Map<String, String> map){
		return DBManager.queryFList("selectRelatedInfo", map);
	}
	
	//根据关键词自动找到相关信息
	@SuppressWarnings("unchecked")
	public static List<RelatedInfoBean> getReleInfoListForAuto(Map<String, String> map){
		return DBManager.queryFList("getReleInfoListForAuto", map);
	}

    //根据关键词自动找到相关信息
    @SuppressWarnings("unchecked")
    public static List<RelatedInfoBean> getReleInfoListForAutoDelete(Map<String, String> map){
        return DBManager.queryFList("getReleInfoListForAutoDelete", map);
    }
	
	//对相关信息按时间排序
	@SuppressWarnings("unchecked")
	public static List<RelatedInfoBean> orderByReleInfoList(String ids)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("info_ids", ids);
		return DBManager.queryFList("orderByReleInfoList", map);
	}
	
	public static RelatedInfoBean getRelatedInfoBean(String id, String related_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("info_id", id);
		map.put("related_info_id", related_id);
		return (RelatedInfoBean)DBManager.queryFObj("selectOneRelatedInfo", map);
	}
	
	/**
	 * 添加内容的点击次数
	 * @param Map<String,String> m 包含info_id,num 递增增加数,时间
	 * @return 
	 */
	public static void addInfoHits(Map<String,String> m)
	{
		 DBManager.update("add_info_hits", m);
	}
	
	/**
	 * 清空点击次数
	 * @param Map<String,String> m 字段名称 day_hits update_hits month_hits
	 * @return 
	 */
	public static void clearInfoHits(Map<String,String> m)
	{
		 DBManager.update("clear_info_hits", m);
	}
	
	/**
	 * 根据信息ID得到点击次数
	 * @param String info_id
	 * @return String
	 */
	public static String getInfoClickCount(String info_id)
	{
		return DBManager.getString("getInfoClickCount", info_id);
	}
	
	/**
	 * 根据栏目ID，站点D，信息ID判断此信息是否已被引用
	 * @param String info_id
	 * @return String
	 */
	public static String getQuoteInfoCount(int info_id,int cat_id,String site_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("from_id", info_id+"");
		m.put("site_id", site_id);
		m.put("cat_id", cat_id+"");
		return DBManager.getString("getQuoteInfoCount", m);
	}

	public static boolean infoGKBZHGet(List<InfoBean> l,String gkhj_id,String gkhj_name,int cat_id)
	{
		if(!l.isEmpty()){
			String info_ids = "";
			for(InfoBean bean:l){
				info_ids+=bean.getInfo_id()+",";
			}
			info_ids = info_ids.substring(0,info_ids.length()-1);
			Map<String,String> m = new HashMap<String,String>();
			m.put("info_ids",info_ids);
			m.put("cat_id",cat_id+"");
			m.put("gkhj_id",gkhj_id);
			m.put("gkhj_name",gkhj_name);
			return DBManager.update("infoGKBZHGet",m);
		}
		return false;
	}
	public static boolean deleteGKBZHInfo(List<InfoBean> l)
	{
		if(!l.isEmpty()){
			String info_ids = "";
			for(InfoBean bean:l){
				info_ids+=bean.getInfo_id()+",";
			}
			info_ids = info_ids.substring(0,info_ids.length()-1);
			Map<String,String> m = new HashMap<String,String>();
			m.put("info_ids",info_ids);
			return DBManager.update("deleteGKBZHInfo",m);
		}
		return false;
	}

	public static boolean MoveGKBZHInfo(List<InfoBean> l,int cat_id)
	{
		if(!l.isEmpty()){
			String info_ids = "";
			for(InfoBean bean:l){
				info_ids+=bean.getInfo_id()+",";
			}
			info_ids = info_ids.substring(0,info_ids.length()-1);
			Map<String,String> m = new HashMap<String,String>();
			m.put("info_ids",info_ids);
			m.put("cat_id",cat_id+"");
			return DBManager.update("MoveGKBZHInfo",m);
		}
		return false;
	}
	/**
	 * 根据栏目ID得到所以已发布过的信息（用于删除栏目时，得到信息ID，删除搜索引擎中的数据）
	 * @param Map<String,String> m
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<InfoBean> getPublishInfoByCateID(Map<String,String> m)
	{
		return DBManager.queryFList("getPublishInfoByCateID", m);
	}
	
	/**
	 * 添加访问次数
	 * @param bean 
	 * @return 
	 */
	public static void insertAccessInfo(InfoAccessBean accbean)
	{
		 DBManager.update("add_accessinfo", accbean);
	}
	
	/**
	 * 修改信息权重
	 * @param bean 
	 * @return 
	 */
	public static boolean updateInfoWeight(InfoBean infoBean)
	{
		 return DBManager.update("updateInfoWeight", infoBean);
	}
	
	public static boolean setInfoTop(Map<String, String> m)
	{
		return DBManager.update("updateInfoisTop", m);
	}

    /**
     * 根据OA的url判断信息是否已经入库
     * */

    public static String getInfoByUrl(String url)
    {
        Map<String,String> m = new HashMap<String,String>();
        m.put("content_url", url);
        return DBManager.getString("getInfoByUrl", m);
    }

	
	public static void main(String[] args) {
		
	}
}
