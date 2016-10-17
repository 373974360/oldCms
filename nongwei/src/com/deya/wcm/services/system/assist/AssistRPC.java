package com.deya.wcm.services.system.assist;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.assist.AuthorBean;
import com.deya.wcm.bean.system.assist.HotWordBean;
import com.deya.wcm.bean.system.assist.SourceBean;
import com.deya.wcm.bean.system.assist.TagsBean;
import com.deya.wcm.services.Log.LogManager;

/**
 * 
 * @author 符江波
 * @date   2011-3-21
 */
public class AssistRPC {

	//热词维护
	public static List<HotWordBean> getAllHotWordList(){
		return HotWordManager.getAllHotWordList();
	}
	
	public static List<HotWordBean> getHotWordList(Map<String,String> con_map){
		return HotWordManager.getHotWordListForDB(con_map);
	}
	
	public static String getHotWordCount(Map<String,String> con_map){
		return HotWordManager.getHotWordCount(con_map);
	}
	
	public static HotWordBean getHotWordById(int hot_id){
		return HotWordManager.getHotWordBean(hot_id);
	}
	
	public static boolean updateHotWordById(HotWordBean hw,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return HotWordManager.updateHotWord(hw,stl);
		}else
			return false;	
	}
	
	public static boolean addHotWordById(HotWordBean hw,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return HotWordManager.addHotWord(hw,stl);
		}else
			return false;	
	}
	
	public static boolean delHotWordById(String hot_id,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return HotWordManager.delHotWordById(hot_id,stl);
		}else
			return false;	
	}
	
	public static List<TagsBean> getTagListByAPPSite(String app_id, String site_id)
	{
		return TagsManager.getTagListByAPPSite(app_id, site_id);
	}
	
	//系统Tags词语
	public static List<TagsBean> getAllTagsList(){
		return TagsManager.getAllTagsList();
	}
	
	public static List<TagsBean> getTagsList(Map<String,String> con_map){
		return TagsManager.getTagsListForDB(con_map);
	}
	
	public static String getTagsCount(Map<String,String> con_map){
		return TagsManager.getTagsCount(con_map);
	}
	
	public static TagsBean getTagsById(int tag_id){
		return TagsManager.getTagsBean(tag_id);
	}
	
	public static boolean updateTagsById(TagsBean tag,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TagsManager.updateTags(tag,stl);
		}else
			return false;	
	}
	
	public static boolean addTagsById(TagsBean tag,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TagsManager.addTags(tag,stl);
		}else
			return false;	
	}
	
	public static boolean delTagsById(String tag_id,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return TagsManager.delTagsById(tag_id,stl);
		}else
			return false;	
	}
	
	//作者
	public static List<AuthorBean> getAllAuthorList(){
		return AuthorManager.getAllAuthorList();
	}
	
	public static List<AuthorBean> getAuthorList(Map<String,String> con_map){
		return AuthorManager.getAuthorListForDB(con_map);
	}
	
	public static String getAuthorCount(Map<String,String> con_map){
		return AuthorManager.getAuthorCount(con_map);
	}
	
	public static AuthorBean getAuthorById(int author_id){
		return AuthorManager.getAuthorBean(author_id);
	}
	
	public static boolean updateAuthorById(AuthorBean author,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return AuthorManager.updateAuthor(author,stl);
		}else
			return false;	
	}
	
	public static boolean addAuthorById(AuthorBean author,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return AuthorManager.addAuthor(author,stl);
		}else
			return false;	
	}
	
	public static boolean delAuthorById(String author_id,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return AuthorManager.delAuthorById(author_id,stl);
		}else
			return false;	
	}
	
	//来源
	public static List<SourceBean> getAllSourceList(){
		return SourceManager.getAllSourceList();
	}
	
	public static List<SourceBean> getSourceList(Map<String,String> con_map){
		return SourceManager.getSourceListForDB(con_map);
	}
	
	public static String getSourceCount(Map<String,String> con_map){
		return SourceManager.getSourceCount(con_map);
	}
	
	public static SourceBean getSourceById(int source_id){
		return SourceManager.getSourceBean(source_id);
	}
	
	public static boolean updateSourceById(SourceBean source,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SourceManager.updateSource(source,stl);
		}else
			return false;	
	}
	
	public static boolean addSourceById(SourceBean source,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SourceManager.addSource(source,stl);
		}else
			return false;	
	}
	
	public static boolean delSourceById(String source_id,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SourceManager.delSourceById(source_id,stl);
		}else
			return false;	
	}
}
