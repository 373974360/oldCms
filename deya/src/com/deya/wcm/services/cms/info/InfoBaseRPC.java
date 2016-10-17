package com.deya.wcm.services.cms.info;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.cms.info.RelatedInfoBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.user.UserRegisterBean;
import com.deya.wcm.dao.cms.info.InfoDAO;
import com.deya.wcm.rmi.file.FileRmiFactory;
import com.deya.wcm.services.Log.LogManager;

/**
 * @author 符江波
 * @version 1.0
 * @date   2011-6-15 上午10:18:04
 */
public class InfoBaseRPC {
	/**
	 * 根据标题得到tags关键词
	 * @param String title
	 * @return String 
	 */
	public static String getTagsForTitle(String title)
	{
		return com.deya.analyzer.AnalyzerManager.getWordsByTitle(title);
	}
	
	/**
	 * 推送信息操作
	 * @param List<InfoBean> l 要得到的信息ID
	 * @param String s_site_id
	 * @param String s_app_id
	 * @param String cat_ids
	 * @param int get_type 获取方式 0 克隆  1引用  2 链接
	 * @param String is_publish
	 * @throws boolean 
	 */
	public static boolean infoTo(List<InfoBean> l,String s_site_id,String s_app_id,String cat_ids,int get_type,boolean is_publish)
	{
		return InfoBaseManager.infoTo(l, s_site_id, s_app_id, cat_ids, get_type, is_publish);
	}
	
	/**
	 * 获取信息操作(如果需要生成页面，使用rmi来调用此方法，直接在那台服务器上生成)
	 * @param List<InfoBean> l 要得到的信息ID
	 * @param String s_site_id
	 * @param String s_app_id
	 * @param int cat_id
	 * @param int get_type 获取方式 0 克隆  1引用  2 链接
	 * @param String is_publish
	 * @param String user_id
	 * @throws boolean 
	 */
	public static boolean infoGet(List<InfoBean> l,String s_site_id,String s_app_id,int cat_id,int get_type,boolean is_publish,int user_id)
	{
		if(is_publish)
		{
			try {
				return FileRmiFactory.infoGet(InfoBaseManager.getInfoReleSiteID(s_site_id,s_app_id), l, s_site_id, s_app_id, cat_id, get_type, is_publish, user_id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		else
			return InfoBaseManager.infoGet(l, s_site_id, s_app_id, cat_id, get_type, is_publish, user_id);
	}
	
	/**
	 * 根据信息ID得到点击次数
	 * @param String info_id
	 * @return String
	 */
	public static String getInfoClickCount(String info_id)
	{
		return InfoBaseManager.getInfoClickCount(info_id);
	}
	
	/**
	 * 批量发布静态内容页,根据栏目,站点和发布时间得到信息
	 * @param map
	 * @return boolean
	 */
	public static boolean batchPublishContentHtml(Map<String, String> map)
	{
		String site_id = "";
		String app_id = "";
		if(map.containsKey("site_id"))
			site_id = map.get("site_id");
		if(map.containsKey("app_id"))
			app_id = map.get("app_id");
		return FileRmiFactory.batchPublishContentHtml(InfoBaseManager.getInfoReleSiteID(site_id,app_id), map);
	}
	
	/**
	 * 根据信息ID和站点ID得到信息对象
	 * @param map
	 * @return boolean
	 */
	public static InfoBean getInfoById(String info_id, String site_id){
		return InfoBaseManager.getInfoById(info_id,site_id);
	}
	
	/**
	 * 根据信息ID得到信息对象
	 * @param map
	 * @return boolean
	 */
	public static InfoBean getInfoById(String info_id){
		return InfoBaseManager.getInfoById(info_id);
	}
	
	/**
	 * 同时发布到其它栏目，添加时用,没有发布,不需要rmi
	 * @param int Object ob
	 * @param String to_cat_ids 要报送到的目标栏目
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertInfoToOtherCat(Object o,String to_cat_ids, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return InfoBaseManager.insertInfoToOtherCat(o,to_cat_ids,stl);
		}else
			return false;
	}	
	
	/**
	 * 根据条件得到所有录入人信息
	 * @param Map
	 * @param stl
	 * @return List<UserRegisterBean>
	 */	
	public static List<UserRegisterBean> getAllInuptUserID(Map<String,String> m)
	{
		return InfoBaseManager.getAllInuptUserID(m);
	}
	
	/**
	 * 得到公开指南或报报的汇总信息
	 * @param Map	
	 * @return List
	 */	
	public static List<InfoBean> getGKZNInfoList(Map<String, String> map)
	{
		return InfoBaseManager.getGKZNInfoList(map);
	}
	
	/**
	 * 根据信息ID得到被引用列表
	 * @param String info_ids
	 * @return List
	 */
	public static List<InfoBean> getQuoteInfoList(String info_id)
	{
		return InfoBaseManager.getQuoteInfoList(info_id);
	}
	
	/**
	 * 得到公开指南或报报的汇总总数
	 * @param Map	 * 
	 * @return String
	 */
	public static String getGKZNInfoCount(Map<String, String> map)
	{
		return InfoBaseManager.getGKZNInfoCount(map);
	}

	public static List<InfoBean> getInfoList(Map<String, String> map){
		return InfoBaseManager.getInfoList(map);
	}
	
	public static int getInfoCount(Map<String, String> map){
		return InfoBaseManager.getInfoCount(map);
	}
	
	/**
	 * 删除信息
	 * @param List<InfoBean> l
	 * @return List
	 */
	public static boolean deleteInfo(List<InfoBean> l, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return FileRmiFactory.deleteInfo(InfoBaseManager.getInfoReleSiteID(l.get(0).getSite_id(),l.get(0).getApp_id()), l, stl);
		}else
			return false;		
	}
	
	/**
	 * 彻底删除信息
	 * @param String delete_ids
	 * @param stl
	 * @return
	 */
	public static boolean realDeleteInfo(List<InfoBean> l, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return InfoBaseManager.realDeleteInfo(l, stl);
		}else
			return false;
	}
	
	/**
	 * 转移信息
	 * @param List<InfoBean> l
	 * @param int to_cat_id 要转移的目标栏目ID
	 * @param String app_id
	 * @param String site_id
	 * @return boolean
	 */
	public static boolean MoveInfo(List<InfoBean> l,int to_cat_id,String app_id,String site_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return FileRmiFactory.MoveInfo(InfoBaseManager.getInfoReleSiteID(site_id,app_id),l,to_cat_id,app_id,site_id, stl);
		}else
			return false; 
	}
	
	/**
	 * 通过cat_id,site_id清空回收站
	 * @param String cat_ids
	 * @param String site_id
	 * @param stl
	 * @return
	 */	
	public static boolean clearTrach(String cat_ids,String app_id,String site_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return InfoBaseManager.clearTrach(cat_ids,app_id,site_id, stl);
		}else
			return false; 
	}
	
	/**
	 * 回归
	 * @param List<InfoBean> l
	 * @param stl
	 * @return boolean
	 */
	public static boolean goBackInfo(List<InfoBean> l, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return InfoBaseManager.goBackInfo(l, stl);
		}else
			return false;		
	}
	
	/**
	 * 归档
	 * @param infoIds
	 * @param stl
	 * @return boolean
	 */
	public static boolean backInfo(String infoIds, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return InfoBaseManager.backInfo(infoIds, stl);
		}else
			return false;	
	}
	
	/**
	 * 发布，撤销信息
	 * @param List<InfoBean> l
	 * @param String status
	 * @return boolean
	 */
	public static boolean updateInfoStatus(List<InfoBean> l, String status, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return FileRmiFactory.updateInfoStatus(InfoBaseManager.getInfoReleSiteID(l.get(0).getSite_id(),l.get(0).getApp_id()), l, status, stl);
		}else
			return false;	
	}
	
	/**
	 * 纯修改信息（用于修改引用信息的主表内容）
	 * @param info
	 * @param stl
	 * @return boolean
	 */
	public static boolean updateInfo(InfoBean info, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			if(info.getInfo_status() == 8)
			{				
				return FileRmiFactory.updateInfoEvent(InfoBaseManager.getInfoReleSiteID(info.getSite_id(),info.getApp_id()), info, stl);
			} 
			else
				return InfoBaseManager.updateInfoEvent(info, stl);
		}else
			return false;	
	}

    /**
     * 纯修改信息（用于修改引用信息的主表内容）
     * @param info
     * @param stl
     * @return boolean
     */
    public static boolean updateInfoList(List<InfoBean> list, HttpServletRequest request){
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            for(InfoBean info : list) {
                if (info.getInfo_status() == 8) {
                    FileRmiFactory.updateInfoEvent(InfoBaseManager.getInfoReleSiteID(info.getSite_id(), info.getApp_id()), info, stl);
                } else
                    InfoBaseManager.updateInfoEvent(info, stl);
            }
            return true;
        }else
            return false;
    }
	
	
	/**
	 * 审核信息通过
	 * @param List<InfoBean> info_list
	 * @param stl
	 * @return boolean
	 */
	public static boolean passInfoStatus(List<InfoBean> info_list,String user_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return FileRmiFactory.passInfoStatus(InfoBaseManager.getInfoReleSiteID(info_list.get(0).getSite_id(),info_list.get(0).getApp_id()), info_list, user_id, stl);
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
	public static boolean noPassInfoStatus(String info_ids,String auto_desc,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return InfoBaseManager.noPassInfoStatus(info_ids,auto_desc,stl);
		}else
			return false;
	}
	
	public static String getTempJsonDataTree(){
		String s = "[{\"id\":0,\"iconCls\":\"icon-org\", \"text\":\"站点\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?cid=0\"},\"children\":[{\"id\":10,\"iconCls\":\"icon-templateFolder\",\"text\":\"会员\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?cid=10\"}},{\"id\":8,\"iconCls\":\"icon-templateFolder\",\"text\":\"调查\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?cid=8\"}},{\"id\":7,\"iconCls\":\"icon-templateFolder\",\"text\":\"访谈\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?cid=7\"}},{\"id\":6,\"iconCls\":\"icon-templateFolder\",\"text\":\"诉求\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?cid=6\"}},{\"id\":3,\"iconCls\":\"icon-templateFolder\",\"state\":'closed',\"text\":\"文章\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?cid=3\"},\"children\":[{\"id\":158,\"iconCls\":\"icon-templateFolder\",\"text\":\"13\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?cid=158\"}},{\"id\":148,\"iconCls\":\"icon-templateFolder\",\"state\":'closed',\"text\":\"12\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?cid=148\"},\"children\":[{\"id\":157,\"iconCls\":\"icon-templateFolder\",\"text\":\"vvvvvvv\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?cid=157\"}},{\"id\":156,\"iconCls\":\"icon-templateFolder\",\"text\":\"dddcvcvcvc\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?cid=156\"}},{\"id\":155,\"iconCls\":\"icon-templateFolder\",\"text\":\"bbbbbb\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?cid=155\"}}]}]},{\"id\":2,\"iconCls\":\"icon-templateFolder\",\"text\":\"系统\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?cid=2\"}},{\"id\":1,\"iconCls\":\"icon-templateFolder\",\"text\":\"首页\",\"attributes\":{\"url\":\"/sys/cms/info/article/articleDataList.jsp?cid=1\"}}]}]";
		return s;
	}
	
	//得到新的信息ID
	public static int getInfoId(){
		return InfoBaseManager.getNextInfoId();
	}
	
	//得到关联信息的新ID
	public static int getReleInfoID()
	{
		return InfoBaseManager.getReleInfoID();
	}
	
	//相关信息处理
	public static boolean addRelatedInfo(RelatedInfoBean rinfo, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return InfoBaseManager.addRelatedInfo(rinfo, stl);
		}else
			return false;	
	}
	
	//修改相关信息
	public static boolean updateRelatedInfo(RelatedInfoBean rinfo, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return InfoBaseManager.updateRelatedInfo(rinfo, stl);
		}else
			return false;	
	}
	
	//删除相关信息
	public static boolean deleteRelatedInfo(Map<String, String> map, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return InfoBaseManager.deleteRelatedInfo(map, stl);
		}else
			return false;	
	}
	
	//得到相关信息
	public static List<RelatedInfoBean> getRelatedInfoList(Map<String, String> map){
		return InfoBaseManager.getRelatedInfoList(map);
	}
	
	//得到相关信息对象
	public static RelatedInfoBean getRelatedInfoBean(String id, String related_id){
		return InfoBaseManager.getRelatedInfoBean(id,related_id);
	}
	
	/**
	 * 生成内容页
	 * @param List<InfoBean> l
	 * @throws IOException 
	 */
	public static boolean createContentHTML(List<InfoBean> l) throws IOException
	{		
		return FileRmiFactory.createContentHTML(InfoBaseManager.getInfoReleSiteID(l.get(0).getSite_id(),l.get(0).getApp_id()), l);
	}
	
	/**
	 * 添加内容的点击次数
	 * @param String info_id
	 * @param String num 递增增加数
	 * @return boolean
	 */
	public static void addInfoHits(String info_id,String num)
	{
		// TODO 处理点击事件
		InfoBaseManager.addInfoHits(info_id,num,"");
	}
	
	/**
     * 根据用户ID，站点ID得到该用户能看到的待审信息
     * @param int user_id
     * @param String site_id
     * @param page_size
     * @return Map 这里返回Map 把总数和列表放在一起，以名分开取的时候浪费性能,取条件时耗性能
     * */
	public static Map<String,Object> getWaitVerifyInfoList(Map<String,String> m)
	{
		return InfoDesktop.getWaitVerifyInfoList(m);
	}
	
	public static void insertAccessInfo(String info_id,String info_tile,int cat_id,String app_id,String site_id,String access_url,HttpServletRequest request)
    {
		InfoBaseManager.insertAccessInfo(info_id,info_tile,cat_id,app_id,site_id,access_url, request);
	}
	
	/**
	 * 修改信息权重
	 * @param bean 
	 * @return 
	 */
	public static boolean updateInfoWeight(int info_id,int weight)
	{
		InfoBean bean = new InfoBean();
		bean.setInfo_id(info_id);
		bean.setWeight(weight); 
		return InfoDAO.updateInfoWeight(bean);
	}
	
	public static boolean setInfoTop(String istop, String info_id)
	{
		return InfoExpandManager.setInfoTop(istop, info_id);
	}

	public static void main(String[] args) {
        /*
		Map<String, String> m = new java.util.HashMap<String, String>();
		m.put("app_id", "0");
		m.put("site_id", "0");
		m.put("start_num", "0");
		m.put("page_size", "10");
		m.put("sort_name", "info_id");
		m.put("sort_type", "desc");
		
		m.put("is_auto_up", "0");
		m.put("is_host", "0");
		m.put("info_status", "0");
		m.put("final_status", "0");
		m.put("cat_id", "0");
		
		//System.out.println(getInfoList(m));
		//System.out.println(getRelatedInfoList(new java.util.HashMap<String, String>()));
		RelatedInfoBean ri = new RelatedInfoBean();
		ri.setContent_url("utl-------------------=");
		ri.setModel_id(1);
		ri.setRelated_info_id(1);
		ri.setSort_id(1);
		ri.setThumb_url("src==================");
		ri.setTitle("a");
		addRelatedInfo(ri,null);
		*/
        String[]  aa = "10006".split(",");
        System.out.println(aa[0]);
	}
}
