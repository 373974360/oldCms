package com.deya.wcm.services.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.comment.CommentDAO;
import com.deya.wcm.bean.comment.CommentSet;

public class CommentSetManager implements ISyncCatch{
private static Map<String,CommentSet> map = new HashMap<String,CommentSet>();
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		map.clear();
		try{
			List<CommentSet> list = CommentDAO.getCommentSetList(); 
			for(CommentSet set : list){
				List<String> tactList = new ArrayList<String>();
				String app_id = set.getApp_id()==null?"":set.getApp_id();
				String site_id = set.getSite_id()==null?"":set.getSite_id();
				String s[] = set.getTact_word().split("[,，]");
				for (int i = 0; i < s.length; i++){
					if(s[i]!=null && !"".equals(s[i])){
						tactList.add(s[i]);
					}
				}
				set.setTactList(tactList);
				map.put(app_id+"-"+site_id,set);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	//得到所有的列表并放进map中
	public static void reloadList(){
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.comment.CommentSetManager");
	}
	
	     
	//通过site_id和app_id得到评论配置  -- 外部调用 
	public static CommentSet getCommentSetBySiteIdAndAppID(String site_id,String app_id){
		CommentSet commentSet = new CommentSet();
		try{
			app_id = site_id==null?"":app_id;
			site_id = site_id==null?"":site_id;
			if(map.containsKey(app_id+"-"+site_id)){
				commentSet = map.get(app_id+"-"+site_id);
			}
			return commentSet;
		}catch (Exception e) {
			e.printStackTrace();
			return commentSet;
		}
	}
	
	
	//通过site_id和app_id得到评论配置  -- 动态读取
	public static CommentSet getCommentSetBySiteIdAndAppIDD(String site_id,String app_id){
		CommentSet commentSet = new CommentSet();
		try{
			Map<String,String> map = new HashMap<String,String>();
			map.put("app_id", app_id);
			if(site_id!=null && !"".equals(site_id)){
				map.put("site_id", site_id);
			}
			int count = CommentDAO.getCommentSetCount(map);
			if(count==0){
				commentSet.setApp_id(app_id);
				commentSet.setCom_prefix("");
				commentSet.setIp_time("0");
				commentSet.setIs_code("1");
				commentSet.setIs_need("1");
				commentSet.setIs_public("1");
				commentSet.setPass_size(5);
				commentSet.setSite_id(site_id);
				commentSet.setTact_word("");
				commentSet.setTime_spacer("60");
				CommentDAO.addCommentSet(commentSet);
			}else{
				commentSet = CommentDAO.getCommentSetByAppIdAndSiteId(site_id, app_id);
			}			
			return commentSet;
		}catch (Exception e) {
			e.printStackTrace();
			return commentSet;
		}
	}
	
	
	/**
     * 修改信息
     * @param SubScription subScription 
     * @return boolean
     * */
	public static boolean updateCommentSet(CommentSet commentSet) {
		CommentDAO.updateCommentSet(commentSet);
		reloadList();
		return true;
	}
}
