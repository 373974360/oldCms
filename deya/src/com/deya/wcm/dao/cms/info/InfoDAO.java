package com.deya.wcm.dao.cms.info;

import com.deya.util.DateUtil;
import com.deya.util.jspFilterHandl;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoDAO
{
	public static List<InfoCountBean> getInfoTotalForApp(Map<String, String> m)
	{
		return DBManager.queryFList("getInfoTotalForApp", m);
	}

	public static List<InfoCountBean> getSiteAccessStatistics(Map<String, String> m)
	{
		return DBManager.queryFList("getSiteAccessStatistics", m);
	}

	public static List<InfoCountBean> getInfoTotalForSiteUser(Map<String, String> m)
	{
		return DBManager.queryFList("getInfoTotalForSiteUser", m);
	}

	public static List<InfoBean> getBroInfoList(Map<String, String> map)
	{
		String type = BoneDataSourceFactory.getDataTypeName();
		if (type.equals("mssql"))
		{
			String page_count = getBroInfoCount(map);
			map.put("page_count", page_count + "");

			String start_num = (String)map.get("start_num");
			String page_size = (String)map.get("page_size");
			String page_cuur = "1";
			page_cuur = (Integer.valueOf(start_num).intValue() / Integer.valueOf(page_size).intValue() + 1) + "";
			map.put("page_cuur", page_cuur + "");

			String orderby = (String)map.get("orderby");
			String orderby_tempt_asc = orderby.replaceAll(" desc", " XXX").replaceAll(" asc", " desc").replaceAll(" XXX", " asc");
			map.put("orderby_tempt_asc", orderby_tempt_asc + "");
			String orderby_tempt = orderby.replaceAll("ci.", "tempt.");
			map.put("orderby_tempt", orderby_tempt + "");
		}
		return DBManager.queryFList("getBroInfoList", map);
	}

	public static String getBroInfoCount(Map<String, String> map)
	{
		return DBManager.getString("getBroInfoCount", map);
	}

	public static List<InfoBean> getGKZNInfoList(Map<String, String> map)
	{
		return DBManager.queryFList("getGKZNInfoList", map);
	}

	public static String getGKZNInfoCount(Map<String, String> map)
	{
		return DBManager.getString("getGKZNInfoCount", map);
	}

	public static InfoBean getInfoById(String info_id, String site_id) {
		Map map = new HashMap();
		map.put("site_id", site_id);
		map.put("info_id", info_id);
		return ((InfoBean)DBManager.queryFObj("selectInfoBeanById", map));
	}

	public static InfoBean getInfoById(String info_id) {
		Map map = new HashMap();
		map.put("info_id", info_id);
		return ((InfoBean)DBManager.queryFObj("selectInfoBeanById", map));
	}

	public static List<UserRegisterBean> getAllInuptUserID(Map<String, String> m)
	{
		return DBManager.queryFList("getAllInuptUserID", m);
	}

	public static boolean addInfo(InfoBean info, SettingLogsBean stl)
	{
		if (DBManager.insert("addInfo", info))
		{
			PublicTableDAO.insertSettingLogs("添加", "主信息", info.getInfo_id() + "", stl);
			return true;
		}
		return false;
	}

	public static boolean updateInfo(InfoBean info, SettingLogsBean stl)
	{
		if (DBManager.update("updateInfo", info)) {
			PublicTableDAO.insertSettingLogs("修改", "主信息", info.getInfo_id() + "", stl);
			return true;
		}
		return false;
	}

	public static List<InfoBean> getInfoListByInfoIDS(Map<String, String> m)
	{
		return DBManager.queryFList("getInfoListByInfoIDS", m);
	}

	public static boolean MoveInfo(InfoBean ib)
	{
		return DBManager.update("move_info", ib);
	}

	public static boolean passInfoStatus(String info_id, String info_status, String step_id, String released_dtime)
	{
		Map m = new HashMap();
		m.put("info_id", info_id);
		m.put("info_status", info_status);
		if ("8".equals(info_status))
			m.put("released_dtime", released_dtime);
		m.put("step_id", step_id);
		m.put("opt_dtime", DateUtil.getCurrentDateTime());

		return (DBManager.update("pass_info_status", m));
	}

	public static boolean noPassInfoStatus(String info_ids, String auto_desc, SettingLogsBean stl)
	{
		Map m = new HashMap();
		m.put("info_ids", info_ids);
		m.put("opt_dtime", DateUtil.getCurrentDateTime());
		m.put("auto_desc", auto_desc);
		if (stl != null)
		{
			m.put("modify_user", stl.getUser_id() + "");
		}
		else
		{
			m.put("modify_user", "0");
		}
		if (DBManager.update("noPass_info_status", m))
		{
			PublicTableDAO.insertSettingLogs("审核", "信息状态为不通过", info_ids, stl);
			return true;
		}
		return false;
	}

	public static boolean updateInfoStatus(String infoIds, String status, SettingLogsBean stl)
	{
		Map map = new HashMap();
		map.put("columnName", "final_status");
		map.put("columnValue", status);
		map.put("info_ids", infoIds);
		map.put("opt_dtime", DateUtil.getCurrentDateTime());
		if (DBManager.update("updateInfosStatusInteger", map)) {
			PublicTableDAO.insertSettingLogs("修改", "主信息最终状态更改为" + status, infoIds + "", stl);
			return true;
		}
		return false;
	}

	public static boolean updateInfoColumnValue(String infoIds, String column, String value)
	{
		Map map = new HashMap();
		map.put("columnName", column);
		map.put("columnValue", value);
		map.put("info_ids", infoIds);
		map.put("opt_dtime", DateUtil.getCurrentDateTime());
		return DBManager.update("updateInfosStatusInteger", map);
	}

	public static boolean updateInfoStatus2(InfoBean ib, String status, SettingLogsBean stl)
	{
		Map map = new HashMap();
		map.put("columnName", "info_status");
		map.put("columnValue", status);
		map.put("info_id", ib.getInfo_id() + "");
		map.put("released_dtime", ib.getReleased_dtime());
		if ((status == null) || (status.trim().equals(""))) {
			status = "0";
		}
		map.put("opt_dtime", DateUtil.getCurrentDateTime());
		return DBManager.update("updateInfosStatusInteger", map);
	}

	public static boolean deleteInfo(Map<String, String> map, SettingLogsBean stl)
	{
		if (deleteInfo(map)) {
			PublicTableDAO.insertSettingLogs("删除", "彻底删除信息", ((String)map.get("info_ids")) + "", stl);
			return true;
		}
		return false;
	}

	public static boolean deleteInfo(Map<String, String> map)
	{
		return DBManager.delete("deleteInfo", map);
	}

	public static boolean deleteInfoModel(Map<String, String> map)
	{
		return (DBManager.delete("delete_info_model", map));
	}

	public static boolean deleteInfoModelBySite(Map<String, String> map)
	{
		return (DBManager.delete("delete_info_model_bySiteid", map));
	}

	public static boolean clearTrach(String cat_ids, String app_id, String site_id, SettingLogsBean stl)
	{
		try
		{
			Map m = new HashMap();
			m.put("cat_ids", cat_ids);
			m.put("site_id", site_id);

			List<ModelBean> model_list = DBManager.queryFList("getModelTableNamebyInfoID", m);
			if ((model_list != null) && (model_list.size() > 0))
			{
				for (ModelBean mb:model_list)
				{
					if ((!("infoLink".equals(mb.getTable_name()))) && (!("".equals(mb.getTable_name()))))
					{
						m.put("table_name", mb.getTable_name());
						DBManager.delete("clear_trash_model", m);
					}
				}

				if ("zwgk".equals(app_id))
				{
					GKInfoDAO.clearGKInfo(m);
					GKInfoDAO.clearGKResFile(m);
				}

				DBManager.delete("deleteInfo", m);
				PublicTableDAO.insertSettingLogs("删除", "回收站信息 栏目", cat_ids + "", stl);
			}

			return true;
		}
		catch (Exception e) {
			e.printStackTrace(); }
		return false;
	}

	public static List<InfoBean> getFromInfoListByIDS(String delete_ids)
	{
		Map m = new HashMap();
		m.put("info_ids", delete_ids);
		return DBManager.queryFList("getFromInfoListByIDS", m);
	}

	public static List<InfoBean> getAtuoPublishInfoList()
	{
		Map m = new HashMap();
		m.put("current_time", DateUtil.getCurrentDateTime());
		String ip = ServerManager.LOCAL_IP;
		if ((ip != null) && (!("".equals(ip))) && (!("127.0.0.1".equals(ip))))
			m.put("server_ip", ServerManager.LOCAL_IP);
		return DBManager.queryFList("getAtuoPublishInfoList", m);
	}

	public static boolean updateAutoPublishVal(Map<String, String> m)
	{
		return DBManager.update("update_auto_publish_val", m);
	}

	public static String getPicModelContent(int info_id)
	{
		return DBManager.getString("getPicModelContent", Integer.valueOf(info_id));
	}

	public static List<InfoBean> getQuoteInfoList(String info_id)
	{
		return DBManager.queryFList("getQuoteInfoList", info_id);
	}

	public static int getInfoCount(Map<String, String> map)
	{
		return Integer.parseInt(DBManager.getString("getAllInfoCounts", map));
	}

	public static List<InfoBean> batchPublishContentHtml(Map<String, String> map)
	{
		return DBManager.queryFList("batchPublishContentHtml", map);
	}

	public static List<InfoBean> getInfoBeanList(Map<String, String> map)
	{
		if(jspFilterHandl.isTureKey(map.get("sort_name").toString())) {
			map.put("sort_name", "ci.released_dtime desc,ci.id");
		}
		if(!map.get("sort_type").toString().equals("desc") && !map.get("sort_type").toString().equals("asc")) {
			map.put("sort_type", "desc");
		}
		if(map.containsKey("highSearchString")&&jspFilterHandl.isTureKey(map.get("highSearchString").toString())) {
			map.put("highSearchString",null);
		}
		return DBManager.queryFList("selectInfoList", map);
	}

	public static boolean addRelatedInfo(RelatedInfoBean rinfo, SettingLogsBean stl)
	{
		if (DBManager.insert("insertRelatedInfo", rinfo)) {
			PublicTableDAO.insertSettingLogs("添加", "相关信息", rinfo.getInfo_id() + "", stl);
			return true;
		}
		return false;
	}

	public static boolean updateRelatedInfo(RelatedInfoBean rinfo, SettingLogsBean stl) {
		if (DBManager.update("updateRelatedInfo", rinfo)) {
			PublicTableDAO.insertSettingLogs("修改", "相关信息", rinfo.getInfo_id() + "", stl);
			return true;
		}
		return false;
	}

	public static boolean deleteRelatedInfo(Map<String, String> map, SettingLogsBean stl)
	{
		if (DBManager.delete("deleteRelatedInfo", map)) {
			PublicTableDAO.insertSettingLogs("删除", "相关信息", "", stl);
			return true;
		}
		return false;
	}

	public static List<RelatedInfoBean> getRelatedInfoList(Map<String, String> map)
	{
		return DBManager.queryFList("selectRelatedInfo", map);
	}

	public static List<RelatedInfoBean> getReleInfoListForAuto(Map<String, String> map)
	{
		return DBManager.queryFList("getReleInfoListForAuto", map);
	}

	public static List<RelatedInfoBean> getReleInfoListForAutoDelete(Map<String, String> map)
	{
		return DBManager.queryFList("getReleInfoListForAutoDelete", map);
	}

	public static List<RelatedInfoBean> orderByReleInfoList(String ids)
	{
		Map map = new HashMap();
		map.put("info_ids", ids);
		return DBManager.queryFList("orderByReleInfoList", map);
	}

	public static RelatedInfoBean getRelatedInfoBean(String id, String related_id) {
		Map map = new HashMap();
		map.put("info_id", id);
		map.put("related_info_id", related_id);
		return ((RelatedInfoBean)DBManager.queryFObj("selectOneRelatedInfo", map));
	}

	public static void addInfoHits(Map<String, String> m)
	{
		DBManager.update("add_info_hits", m);
	}

	public static void clearInfoHits(Map<String, String> m)
	{
		DBManager.update("clear_info_hits", m);
	}

	public static String getInfoClickCount(String info_id)
	{
		return DBManager.getString("getInfoClickCount", info_id);
	}

	public static String getQuoteInfoCount(int info_id, int cat_id, String site_id)
	{
		Map m = new HashMap();
		m.put("from_id", info_id + "");
		m.put("site_id", site_id);
		m.put("cat_id", cat_id + "");
		return DBManager.getString("getQuoteInfoCount", m);
	}

	public static List<InfoBean> getPublishInfoByCateID(Map<String, String> m)
	{
		return DBManager.queryFList("getPublishInfoByCateID", m);
	}

	public static void insertAccessInfo(InfoAccessBean accbean)
	{
		DBManager.update("add_accessinfo", accbean);
	}

	public static boolean updateInfoWeight(InfoBean infoBean)
	{
		return DBManager.update("updateInfoWeight", infoBean);
	}

	public static boolean setInfoTop(Map<String, String> m)
	{
		return DBManager.update("updateInfoisTop", m);
	}

	public static String getInfoByUrl(String url)
	{
		Map m = new HashMap();
		m.put("content_url", url);
		return DBManager.getString("getInfoByUrl", m);
	}

	public static void main(String[] args)
	{
	}
}