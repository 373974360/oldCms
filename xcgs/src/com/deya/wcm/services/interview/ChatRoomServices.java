package com.deya.wcm.services.interview;
import java.util.*;

import com.deya.wcm.dao.interview.ChatRoomDAO;
import com.deya.wcm.services.system.filterWord.FilterWordManager;
import com.deya.wcm.bean.interview.*;
import com.deya.wcm.bean.system.filterWord.FilterWordBean;
import com.deya.util.*;

/**
 * 聊天室逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 聊天室的逻辑处理
 *  其中需要4种集合
 *  sub_id+"_apl" 图文区所有消息的集合 all_pictext_list
 *  sub_id+"_cpl" 可以在前台展示的图文区信息集合 can_pictext_list
 *  sub_id+"_atl" 文字互动区所有消息的集合 all_text_list
 *  sub_id+"_ctl" 可以在前台展示的文字互动区信息集合 can_text_list
 *  apl 和 atl作为需要审核的过度集合，如果信息都不需要审核，可省略这两个集合，直接使用cpl or ctl,以减少不必要的内存占用
 * </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class ChatRoomServices {
	//存储有关聊天室的集合，全部以sub_id区分,类型有 
	//sub_id+"_apl" 图文区所有消息的集合 all_pictext_list
	//sub_id+"_cpl" 可以在前台展示的图文区信息集合 can_pictext_list
	//sub_id+"_atl" 文字互动区所有消息的集合 all_text_list
	//sub_id+"_ctl" 可以在前台展示的文字互动区信息集合 can_text_list
	//live_pic(List SubjectResouse) 直播精彩图片	
	@SuppressWarnings("unchecked")
	private static Map<String,List> chat_map = new HashMap<String,List>();
	//存储有关访问用户的集合，全部以sub_id区分,类型有 
	//live_user（map）当前在线用户
	//user_count(GuestBean) 在线用户序号
	//prohibit_users(string) 禁言用户
    //prohibit_ips(string) 禁言的IP地址
	//prohibit_type(string) 禁言类型
	@SuppressWarnings("unchecked")
	private static Map user_map = new HashMap();
	//其它参数集合
	//sub_id+"_live_vidao"直播视频地址
	//sub_id+"_history_vidao"历史视频地址
	@SuppressWarnings("unchecked")
	private static Map other_map = new HashMap();
	//关键词过滤
	private static String keyword = "";
	//昵称过滤词
	private static String nick_filter = "";
	
	static{
		//启动时读取数据库，查出正处在直播状态的主题，并取出里面的内容加载到集合列表中,初始参数为空，加载所有的查出的数据
		reloadChat("");		
		//启动时加载过滤词库
		reloadFilterWord();
	}
	//加载过滤词库
	public static void reloadFilterWord()
	{
		
		List<FilterWordBean> f_l = FilterWordManager.getFilterWordList();
		if(f_l != null && f_l.size() > 0)
		{
			for(int i=0;i<f_l.size();i++)
			{				
				nick_filter += ","+f_l.get(i).getPattern();				
				keyword += ","+f_l.get(i).getPattern();
			}
			if(nick_filter.startsWith(","))
				nick_filter = nick_filter.substring(1);
			if(keyword.startsWith(","))
				keyword = keyword.substring(1);
		}
	}
	
	/**
     * 启动时读取数据库，查出正处在直播状态的主题，并取出里面的内容加载到集合列表中
     * @param String id 主题ID，此方法还被别的类调用，传入参数，对此ID的值进行加载
     * @return 
     * */
	@SuppressWarnings("unchecked")
	public static void reloadChat(String id)
	{		
		List subjectList = ChatRoomDAO.getLiveStatusSubjectList(id);		
		if(subjectList != null && subjectList.size() > 0)
		{
			for(int i=0;i<subjectList.size();i++)
			{
				Map m = MapManager.mapKeyValueToLow((HashMap)subjectList.get(i));
				String sub_id = (String)m.get("sub_id");
				int is_p_audit = Integer.parseInt(m.get("is_p_audit").toString());
				int is_t_audit = Integer.parseInt(m.get("is_t_audit").toString());
				//加载聊天记录
				reloadChatInfo(sub_id,is_p_audit,is_t_audit);
				//加载出该主题的附件信息，如精彩图片，预告图片，视频，直播视频等
				reloadSubjectResouse(sub_id);				
			}			
		}
	}
	
	
//	加载该主题的附件信息，如精彩图片，预告图片，视频，直播视频等
	@SuppressWarnings("unchecked")
	public static void reloadSubjectResouse(String sub_id)
	{		
		try
		{			
			List<SubjectResouse> lp = ChatRoomDAO.getResouseBySubID(sub_id);
			if(lp != null && lp.size() > 0)
			{
				List<SubjectResouse> temp_picList = new ArrayList<SubjectResouse>();
				for(int i=0;i<lp.size();i++)
				{
					//如果是精彩图片，写入到list
					if("live".equals(lp.get(i).getAffix_status()) && "pic".equals(lp.get(i).getAffix_type()))
					{
						temp_picList.add(lp.get(i));
					}
					else
					{
						//其它的如预告视频，预告图片，直播视频，直播写入到map中						
						other_map.put(sub_id+"_"+lp.get(i).getAffix_status()+"_"+lp.get(i).getAffix_type(), lp.get(i).getAffix_path());
					}
				}
				if(temp_picList != null && temp_picList.size() > 0)
					chat_map.put(sub_id+"_live_pic", temp_picList);
			}
		}catch(Exception e){			
			e.printStackTrace();
			System.out.println("reload live pic error");
		}	
	}
	
	//加载聊天记录
	@SuppressWarnings("unchecked")
	private static void reloadChatInfo(String sub_id,int is_p_audit,int is_t_audit)
	{
		if(chat_map.containsKey(sub_id+"_apl"))
			chat_map.remove(sub_id+"_apl");
		if(chat_map.containsKey(sub_id+"_cpl"))
			chat_map.remove(sub_id+"_cpl");
		if(chat_map.containsKey(sub_id+"_atl"))
			chat_map.remove(sub_id+"_atl");
		if(chat_map.containsKey(sub_id+"_ctl"))
			chat_map.remove(sub_id+"_ctl");
		List chat_list = ChatRoomDAO.getChatListBySubID(sub_id);
		if(chat_list != null && chat_list.size() > 0)
		{
			for(int j=0;j<chat_list.size();j++)
			{
				ChatBean cb = (ChatBean)chat_list.get(j);
				if("pic".equals(cb.getChat_area()))
				{
					setPicTextInfo(sub_id,cb,is_p_audit,true);							
				}
				if("text".equals(cb.getChat_area()))
				{
					setTextInfo(sub_id,cb,is_t_audit,true);
				}
			}
		}
	}
	
	/**
     * 设置禁言类型
     * @param String sub_id
     * @param String pro_type
     * @return 
     * */
	@SuppressWarnings("unchecked")
	public static void setProhibitType(String sub_id,String pro_type)
	{			
		user_map.put(sub_id+"_prohibit_type", pro_type);
	}
	
	/**
     * 得到禁言类型
     * @param String sub_id
     * @return String pro_type
     * */
	public static String getProhibitType(String sub_id)
	{	//如果没有设置禁言类型，默认对用户进行禁言		
		if(user_map.containsKey(sub_id+"_prohibit_type"))
		{
			return (String)user_map.get(sub_id+"_prohibit_type");
		}
		else
			return "uname";		
	}
	
	/**
     * 得到已被禁言的用户列表 
     * @param String sub_id
     * @return 
     * */
	@SuppressWarnings("unchecked")
	public static List getProhibitUsers(String sub_id)
	{
		List<GuestBean> GuestList = new ArrayList<GuestBean>();
		String users = "";
		String ips = "";
		try{
			//如果是IP过滤，需要取出针对IP过滤的集合及针对用户过滤的集合
			if("ip".equals(getProhibitType(sub_id)) && user_map.containsKey(sub_id+"_prohibit_ips"))
			{	
				//取出IP过虑集合
				ips = (String)user_map.get(sub_id+"_prohibit_ips");	
				//取出用户过滤集合
				if(user_map.containsKey(sub_id+"_prohibit_users"))
					users = (String)user_map.get(sub_id+"_prohibit_users");
				
				//首先从根据sub_id从用户列表中得到所有在线用户集合
				if(user_map.containsKey(sub_id+"_live_user"))
				{
					Map<String,GuestBean> live_user = (HashMap)user_map.get(sub_id+"_live_user");		
					//遍历所有的在线用户集合
					Iterator iter = live_user.entrySet().iterator(); 
					while (iter.hasNext()) { 
					    Map.Entry entry = (Map.Entry) iter.next(); 	
					    String key = (String)entry.getKey();
					    //取出Guest对象
					    GuestBean gb = (GuestBean)entry.getValue(); 						    
						//判断该用启的IP是否在IP过滤集合中 及 判断该用户是否在用户过滤集合中存在
					    if(ips.contains(gb.getIp()+",") || users.contains(key))								
								GuestList.add(gb);							
					} 
				}
				
			}
			else
			{
				//从用户名过滤集合中取出对象
				if(user_map.containsKey(sub_id+"_prohibit_users"))
				{
					users = (String)user_map.get(sub_id+"_prohibit_users");
					
					if(users != null && !"".equals(users))
					{
						if(user_map.containsKey(sub_id+"_live_user"))
						{
							Map<String,GuestBean> live_user = (HashMap)user_map.get(sub_id+"_live_user");
							String[] tempA = users.split(",");
							//tempA[i]记录的集是live_user的key值
							for(int i=0;i<tempA.length;i++)
							{
								GuestList.add(live_user.get(tempA[i]));
							}
						}
					}
					
				}
			}
		}catch(Exception e){			
			e.printStackTrace();
		}
		
		return GuestList;
	}	
	
	/**
     * 设置当前访谈的禁言用户
     * @param String sub_id
     * @param String user_name
     * @return 
     * */
	@SuppressWarnings("unchecked")
	public static void setProhibitUsers(String sub_id,String user_num)
	{
		String str = "";
		try{
			//针对IP禁言,存储IP地址
			if("ip".equals(getProhibitType(sub_id)))
			{
				//首先从根据sub_id从用户列表中得到所有在线用户集合			
				Map<String,GuestBean> live_user = (HashMap)user_map.get(sub_id+"_live_user");			
				if(live_user != null)
				{
					if(user_map.containsKey(sub_id+"_prohibit_ips"))
					{
						str = (String)user_map.get(sub_id+"_prohibit_ips");
					}
					if(!str.contains(live_user.get(user_num).getIp()+","))
					{
						str += live_user.get(user_num).getIp()+",";					
						user_map.put(sub_id+"_prohibit_ips", str);
					}
				}
			}
			else
			{//针对用户禁言,存储用户序号
				if(user_map.containsKey(sub_id+"_prohibit_users"))
				{
					str = (String)user_map.get(sub_id+"_prohibit_users");
				}
				if(!str.contains(user_num+","))
				{
					str += user_num+","; 				
					user_map.put(sub_id+"_prohibit_users", str);
				}
			}
		}catch(Exception e){			
			e.printStackTrace();
		}	
	}
	
	/**
     * 记录当前在线用户
     * @param String sub_id
     * @param String user_name
     * @param String ip
     * @return 
     * */
	@SuppressWarnings("unchecked")
	public static void recordUserInfo(String sub_id,GuestBean gb)
	{
		Map<String,GuestBean> live_user = new HashMap();
		if(user_map.containsKey(sub_id+"_live_user"))
		{
			live_user = (HashMap)user_map.get(sub_id+"_live_user");
			live_user.put(gb.getUser_num(), gb);
		}
		else
		{//在线用记MAP			
			live_user.put(gb.getUser_num(), gb);
			user_map.put(sub_id+"_live_user", live_user);
		}
	}
	
	/**
     * 删除当前在线用户
     * @param String sub_id
     * @param String user_name
     * @return 
     * */
	@SuppressWarnings("unchecked")
	public static void unRecordUserInfo(String sub_id,String user_name)
	{
		Map<String,String> live_user = (HashMap)user_map.get(sub_id+"_live_user");
		live_user.remove(user_name);		
	}
	
	/**
     * 得到当前在线用户集合
     * @param String sub_id
     * @return Map
     * */
	@SuppressWarnings("unchecked")
	public static Map getLiveUserInfo(String sub_id)
	{
		return (HashMap)user_map.get(sub_id+"_live_user");
	}
	/**
     * 当前用户登录后，获得其序号
     * @param String sub_id
     * @return int　序号
     * */
	@SuppressWarnings("unchecked")
	public static int getUserMark(String sub_id)
	{
		int count = 100;
		if(user_map.containsKey(sub_id+"_user_count"))
		{
			count = (Integer)user_map.get(sub_id+"_user_count")+1;			
		}
		else
		    count = 101;		
		user_map.put(sub_id+"_user_count",count);
		//写库
		ChatRoomDAO.setCountUser(sub_id);
		return count;
	}
	
	/**
     * 获得图文区所有信息集合（后台管理页面用）
     * @param String sub_id
     * @param int start_num 消息起始下标
     * @param is_p_audit 发言是否需要审核 0为不需要，1为需要
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static HashMap getAllPicTextInfoListByAdmin(String sub_id,int start_num,int is_p_audit)
	{
		try
		{			
			List<ChatBean> tL = new ArrayList<ChatBean>();
			//如果不需要审核，直接使用can_pictext_list
			if(is_p_audit == 1 && chat_map.containsKey(sub_id+"_apl"))
			{
				tL = chat_map.get(sub_id+"_apl");
			}
			if(is_p_audit == 0 && chat_map.containsKey(sub_id+"_cpl"))
			{
				tL = chat_map.get(sub_id+"_cpl");
			}
			return setChatListToMap(tL,"pic",start_num);
		}catch(Exception e){			
			e.printStackTrace();
			return null;
		}		
	}
	/**
     * 获得文字互动区信息集合（前台用户使用）
     * @param String sub_id
     * @param int start_num 消息起始下标
     * @param is_t_audit 发言是否需要审核 0为不需要，1为需要
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static HashMap getTextInfoList(String sub_id,int start_num){
		try
		{			
			List<ChatBean> tL = new ArrayList<ChatBean>();
			if(chat_map.containsKey(sub_id+"_ctl"))
			{
				tL = chat_map.get(sub_id+"_ctl");
				//刚才进入的用户起始数为0，为访问性能，不可能把所有的消息返回给用户，在下面加入判断，读取最近的5条信息
				/*
				if(start_num == 0)
				{
					if(tL.size() > 5)
						start_num = tL.size()-5;
				}*/			
			}			
			return setChatListToMap(tL,"text",start_num);
		}catch(Exception e){			
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
     * 获得图文区所有信息集合（前台用户使用）
     * @param String sub_id
     * @param int start_num 消息起始下标
     * @param is_p_audit 发言是否需要审核 0为不需要，1为需要
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static HashMap getAllPicTextInfoList(String sub_id,int start_num){
		try
		{			
			List<ChatBean> tL = new ArrayList<ChatBean>();
			if(chat_map.containsKey(sub_id+"_cpl"))
			{
				tL = chat_map.get(sub_id+"_cpl");
			}			
			return setChatListToMap(tL,"pic",start_num);
		}catch(Exception e){			
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * 获得文字互区所有信息集合（后台管理页面用）
     * @param String sub_id
     * @param int start_num 消息起始下标
     * @param is_t_audit 发言是否需要审核 0为不需要，1为需要
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static HashMap getAllTextInfoListByAdmin(String sub_id,int start_num,int is_t_audit)
	{
		try
		{			
			List<ChatBean> tL = new ArrayList<ChatBean>();
			//如果不需要审核，直接使用can_pictext_list
			if(is_t_audit == 1 && chat_map.containsKey(sub_id+"_atl"))
			{
				tL = chat_map.get(sub_id+"_atl");
			}
			if(is_t_audit == 0 && chat_map.containsKey(sub_id+"_ctl"))
			{
				tL = chat_map.get(sub_id+"_ctl");
			}			
			return setChatListToMap(tL,"text",start_num);
		}catch(Exception e){			
			e.printStackTrace();
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap setChatListToMap(List tL,String keyName,int start_num)
	{
		HashMap rMap = new HashMap();		
		try
		{
			if(tL != null && tL.size() > 0)
			{	
				rMap.put(keyName+"_mList", tL.subList(start_num, tL.size()));
				rMap.put(keyName+"_max_num", tL.size());
				return rMap;
			}
			else
				return null;
		}catch(Exception e){			
				e.printStackTrace();
				return null;
		}
	}
	
	/**
     * 插入图文区信息（后台管理页面用）
     * @param String sub_id
     * @param int ChatBean cb
     * @param is_p_audit 发言是否需要审核 0为不需要，1为需要
     * @return 
     * */
	@SuppressWarnings("unchecked")
	public static synchronized void setPicTextInfo(String sub_id,ChatBean cb,int is_p_audit,boolean is_db)
	{
		//如果是初始化从数据库中读取出来不需要付值
		if(!is_db)
		{
			cb.setSub_id(sub_id);
			cb.setChat_id(UUID.randomUUID().toString());
			cb.setPut_time(DateUtil.getCurrentDateTime());
		}
		if(is_p_audit == 1)
		{
			if(chat_map.containsKey(sub_id+"_apl"))
			{
				//用集合的长度做为消息的ID，此ID可做下标使用
				cb.setIndex_num(chat_map.get(sub_id+"_apl").size());
				chat_map.get(sub_id+"_apl").add(cb);
			}
			else
			{
				List<ChatBean> l = new ArrayList<ChatBean>();
//				用集合的长度做为消息的ID，此ID可做下标使用,初始为0
				cb.setId(0);
				l.add(cb);
				chat_map.put(sub_id+"_apl", l);
			}			
		}//如果不需要审核或通过审核的，数据直接写入，不需要使用_apl过度集合,
		if(is_p_audit == 0 || cb.getAudit_status() == 1)
		{
			if(chat_map.containsKey(sub_id+"_cpl"))
			{
				cb.setIndex_num(chat_map.get(sub_id+"_cpl").size());
				chat_map.get(sub_id+"_cpl").add(cb);
			}
			else
			{
				List<ChatBean> l = new ArrayList<ChatBean>();
				cb.setIndex_num(0);
				l.add(cb);
				chat_map.put(sub_id+"_cpl", l);
			}
		}
		//写入数据库 如果是初始化从数据库中读取出来的不写库
		if(!is_db)
			ChatRoomDAO.insertChat(cb);
	}
	
	/**
     * 修改聊天记录
     * @param ChatBean 
     * @param is_p_audit 图文直播区发言是否需要审核 0为不需要，1为需要
     * @param is_t_audit 文字互动区发言是否需要审核 0为不需要，1为需要
     * @param boolean isUpdateCatch 是否需要更新缓存，历史状态下修改信息不需要更新缓存  true 为需要 false为不需要
     * @return boolean
     * */
	public static boolean updateChat(ChatBean cb,int is_p_audit,int is_t_audit,boolean isUpdateCatch)
	{	
		if("pic".equals(cb.getChat_area()))
		{
			return 	updateChatHandl(cb,is_p_audit,"p",isUpdateCatch);	
		}
		else
		{
			return 	updateChatHandl(cb,is_t_audit,"t",isUpdateCatch);	
		}
	}
	/**
     * 修改聊天记录操作方法
     * @param ChatBean 
     * @param is_audit 是否需要审核 0为不需要，1为需要
     * @param boolean isUpdateCatch 是否需要更新缓存，历史状态下修改信息不需要更新缓存  true 为需要 false为不需要
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public static boolean updateChatHandl(ChatBean cb,int is_audit,String list_type,boolean isUpdateCatch)
	{
		ChatBean old_cBean = new ChatBean();
		try{//是否需要更新缓存
			if(isUpdateCatch)
			{
				if(is_audit == 1)
				{//如果需要审核，从all_pic_list or all_text_list中取出对应的值,并更新all的集合
					old_cBean = (ChatBean)chat_map.get(cb.getSub_id()+"_a"+list_type+"l").get(cb.getIndex_num());
					old_cBean.setContent(cb.getContent());
					
					//更新can_pic_list集合
					List tempList = chat_map.get(cb.getSub_id()+"_c"+list_type+"l");
					for(int i=tempList.size();i > -1;i--)
					{
						ChatBean tempChatBena = (ChatBean)tempList.get(i-1);					
						if(cb.getChat_id().equals(tempChatBena.getChat_id()))	
						{
							tempChatBena.setContent(cb.getContent());
							break;
						}
					}
				}
				else
				{
					//不需要审核，直接操作can_pic_list or can_text_list
					old_cBean = (ChatBean)chat_map.get(cb.getSub_id()+"_c"+list_type+"l").get(cb.getIndex_num());
					old_cBean.setContent(cb.getContent());
				}	
			}
			else
				old_cBean = cb;
		}
		catch(Exception e){			
			e.printStackTrace();
		}
		return ChatRoomDAO.updateChat(old_cBean);
	}
	
	/**
     * 删除聊天记录
     * @param ChatBean 
     * @param is_p_audit 图文直播区发言是否需要审核 0为不需要，1为需要
     * @param is_t_audit 文字互动区发言是否需要审核 0为不需要，1为需要
     * @param boolean isUpdateCatch 是否需要更新缓存，历史状态下删除信息不需要更新缓存  true 为需要 false为不需要
     * @return boolean
     * */
	public static boolean deleteChat(ChatBean cb,int is_p_audit,int is_t_audit,boolean isUpdateCatch)
	{
		if("pic".equals(cb.getChat_area()))
		{
			return 	deleteChatHandl(cb,is_p_audit,"p",isUpdateCatch);	
		}
		else
		{
			return 	deleteChatHandl(cb,is_t_audit,"t",isUpdateCatch);	
		}		
	}
	/**
     * 删除聊天记录操作方法
     * @param ChatBean 
     * @param is_audit 是否需要审核 0为不需要，1为需要
     * @param boolean isUpdateCatch 是否需要更新缓存，历史状态下删除信息不需要更新缓存 true 为需要 false为不需要
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public static boolean deleteChatHandl(ChatBean cb,int is_audit,String list_type,boolean isUpdateCatch)
	{
		@SuppressWarnings("unused")
		ChatBean old_cBean = new ChatBean();
		try{
			if(isUpdateCatch)			{
				if(is_audit == 1)
				{
					chat_map.get(cb.getSub_id()+"_a"+list_type+"l").set(cb.getIndex_num(),null);
					
					//更新can_pic_list集合
					List tempList = chat_map.get(cb.getSub_id()+"_c"+list_type+"l");
					for(int i=tempList.size();i > -1;i--)
					{
						ChatBean tempChatBena = (ChatBean)tempList.get(i-1);														if(cb.getChat_id().equals(tempChatBena.getChat_id()))	
						{//为保证List的长度不变，删除时将此位置的对象置为null
							chat_map.get(cb.getSub_id()+"_c"+list_type+"l").set(i-1,null);
							break;
						}
					}
				}
				else
				{
					//不需要审核，直接操作can_pic_list or can_text_list
					chat_map.get(cb.getSub_id()+"_c"+list_type+"l").set(cb.getIndex_num(),null);
					
				}	
			}
		}
		catch(Exception e){			
			e.printStackTrace();
		}
		return ChatRoomDAO.deleteChat(cb.getChat_id());
	}
	
	/**
     * 插入文字互动区信息
     * @param String sub_id
     * @param int ChatBean cb
     * @param is_t_audit 文字互动区发言是否需要审核 0为不需要，1为需要
     * @return 
     * */
	@SuppressWarnings("unchecked")
	public static synchronized void setTextInfo(String sub_id,ChatBean cb,int is_t_audit,boolean is_db)
	{
//		如果是初始化从数据库中读取出来不需要付值,不需要进行判断
		if(!is_db)
		{
			cb.setSub_id(sub_id);
			cb.setChat_id(UUID.randomUUID().toString());
			cb.setPut_time(DateUtil.getCurrentDateTime());
			//如果不是管理说的话，进行下列判断
			if(!"admin".equals(cb.getChat_type()))
			{
				//判断该主题是否有禁言用户
				if(user_map.containsKey(sub_id+"_prohibit_users"))
				{  
					String users = (String)user_map.get(sub_id+"_prohibit_users");			
					//如果该用户巳被禁言，不写入数据,除去
					if(!"admin".equals(cb.getChat_type()) && users.contains(cb.getUser_num()+","))
					{
						return;
					}			
				}
				//判断是否需要针对IP进行禁言
				if("ip".equals(getProhibitType(sub_id)))
				{	//是否有禁言的IP
					if(user_map.containsKey(sub_id+"_prohibit_ips"))
					{  
						String ips = (String)user_map.get(sub_id+"_prohibit_ips");			
						//如果该IP巳被禁言，不写入数据,除去
						if(!"admin".equals(cb.getChat_type()) && ips.contains(cb.getIp()+","))
						{
							return;
						}			
					}
				}
				//判断运行期间是否禁言互动发言 0为不允许
				if("0".equals(other_map.get(sub_id+"_is_permit_speak")))
				{
					return;
				}
				
				//判断是否启用关键词过滤
				if("1".equals(other_map.get(sub_id+"_is_t_keyw")))
				{
					//如果启用，判断发言内容是否有需要过滤的东东
					if(ifHaveKeyFilter(cb.getContent()))
					{
						//过滤方式,0表示不入数据，直播返回						
						if(!other_map.containsKey(sub_id+"_filter_type") || "0".equals(other_map.get(sub_id+"_filter_type")))
						{
							return;
						}
						//如果为1，对过滤内容进行替换
						if("1".equals(other_map.get(sub_id+"_filter_type")))
						{
							replacekeyWordByContent(cb);
						}
					}
				}
				//判断是否启用昵称过滤
				if("1".equals(other_map.get(sub_id+"_is_t_flink")))
				{
					if(ifHaveNickFilter(cb.getChat_user()))
					{
						return;
					}
				}
			}
		}
		
		
		//是否需要审核
		if(is_t_audit == 1)
		{
			if(chat_map.containsKey(sub_id+"_atl"))
			{
				//用集合的长度做为消息的ID，此ID可做下标使用
				cb.setIndex_num(chat_map.get(sub_id+"_atl").size());
				chat_map.get(sub_id+"_atl").add(cb);
			}
			else
			{
				List<ChatBean> l = new ArrayList<ChatBean>();
				//用集合的长度做为消息的ID，此ID可做下标使用,初始为0
				cb.setIndex_num(0);
				l.add(cb);
				chat_map.put(sub_id+"_atl", l);
			}
			
//			如果是管理员模拟游客说话，不需要审核，直接进入ctl集合
			if(cb.getAudit_status() == 1)
			{
				if(chat_map.containsKey(sub_id+"_ctl"))
				{
					cb.setIndex_num(chat_map.get(sub_id+"_ctl").size());
					chat_map.get(sub_id+"_ctl").add(cb);
				}
				else
				{
					List<ChatBean> l = new ArrayList<ChatBean>();	
					cb.setIndex_num(0);
					l.add(cb);
					chat_map.put(sub_id+"_ctl", l);
				}
			}
		}else
		{		
			if(chat_map.containsKey(sub_id+"_ctl"))
			{
				cb.setIndex_num(chat_map.get(sub_id+"_ctl").size());
				chat_map.get(sub_id+"_ctl").add(cb);
			}
			else
			{
				List<ChatBean> l = new ArrayList<ChatBean>();
				cb.setIndex_num(0);			
				l.add(cb);
				chat_map.put(sub_id+"_ctl", l);
			}			
		}	
//		写入数据库 如果是初始化从数据库中读取出来的不写库
		if(!is_db)
			ChatRoomDAO.insertChat(cb);
	}
	
	/**
     * 审核通过图文区信息（后台管理页面用）
     * @param ChatBean cb
     * @return 
     * */
	public static boolean isPassChat(ChatBean cb)
	{
		if("pic".equals(cb.getChat_area()))
		{
			return 	isPassChatHandl(cb,"p");	
		}
		else
		{
			return 	isPassChatHandl(cb,"t");	
		}
	}
	/**
     * 审核通过图文区信息操作方法（后台管理页面用）
     * @param ChatBean cb
     * @param list_type 操作集合类型，是图文还是文字互动
     * @return 
     * */
	@SuppressWarnings("unchecked")
	public static boolean isPassChatHandl(ChatBean cb,String list_type)
	{
		ChatBean old_cBean = new ChatBean();
		try{
			//从all_pic_list or all_text_list中取出对应的值,并更新all的集合
			old_cBean = (ChatBean)chat_map.get(cb.getSub_id()+"_a"+list_type+"l").get(cb.getIndex_num());
			old_cBean.setAudit_status(1);
			
			//向can_pic_list or can_text_list集合中写入值
			if(chat_map.containsKey(cb.getSub_id()+"_c"+list_type+"l"))
			{
				chat_map.get(cb.getSub_id()+"_c"+list_type+"l").add(old_cBean);	
			}else
			{
				List l = new ArrayList();
				l.add(old_cBean);
				chat_map.put(cb.getSub_id()+"_c"+list_type+"l", l);
			}
			
		}
		catch(Exception e){			
			e.printStackTrace();
		}
		return ChatRoomDAO.updateChatAuditStatus(old_cBean.getChat_id());
	}
	
	/**
     * 插入精彩图片
     * @param SubjectResouse
     * @return 
     * */
	@SuppressWarnings("unchecked")
	public static boolean insertLivePic(SubjectResouse sr)
	{
		sr.setAdd_time(DateUtil.getCurrentDateTime());
		if(ChatRoomDAO.insertLiveAffix(sr))
		{
			try
			{
				//添加成功后写入缓存
				if(chat_map.containsKey(sr.getSub_id()+"_live_pic"))
				{
					chat_map.get(sr.getSub_id()+"_live_pic").add(sr);
				}
				else
				{
					List l = new ArrayList();
					l.add(sr);
					chat_map.put(sr.getSub_id()+"_live_pic", l);
				}
			}catch(Exception e){			
				e.printStackTrace();
			}	
			return true;
		}
		else
			return false;
	}
	
	/**
     * 插入视频
     * @param SubjectResouse
     * @return 
     * */
	public static boolean insertLiveVideo(SubjectResouse sr)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("sub_id", sr.getSub_id());
		m.put("affix_type", sr.getAffix_type());
		m.put("affix_status", sr.getAffix_status());
		boolean isOk = false;
		
		if("0".equals(ChatRoomDAO.getResouseInfoByCon(m)))
		{
			sr.setAdd_time(DateUtil.getCurrentDateTime());
			isOk = ChatRoomDAO.insertLiveAffixForSingle(sr);
		}else
			isOk = ChatRoomDAO.updateLiveAffixForSingle(sr);
		if(isOk)
		{				
			setOtherParam(sr.getSub_id(),sr.getAffix_status()+"_video",sr.getAffix_path());
			return true;
		}
		else
			return false;
	}
	
	/**
     * 删除精彩图片
     * @param int id
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public synchronized static boolean deleteLivePic(String sub_id,int id)
	{		
		if(ChatRoomDAO.deleteLivePic(id))
		{
			try{
				for(int i=0;i<chat_map.get(sub_id+"_live_pic").size();i++)
				{
					SubjectResouse sr = (SubjectResouse)chat_map.get(sub_id+"_live_pic").get(i);
					if(sr != null && sr.getId() == id)
					{
						chat_map.get(sub_id+"_live_pic").set(i, null);
						break;
					}	
				}
			}catch(Exception e){			
				e.printStackTrace();
			}
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
     * 得到精彩图片列表
     * @param Map m 
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getLivePicList(String sub_id,int index_num)
	{
		if(chat_map.containsKey(sub_id+"_live_pic"))
		{			
			return chat_map.get(sub_id+"_live_pic").subList(index_num, chat_map.get(sub_id+"_live_pic").size());			
		}
		else
			return null;
	}
	/**
     * 根据sub_id得到其它参数集合本主题的设置信息
     * @param Map m 
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static Map getOtherMap(String sub_id)
	{
		Map o = new HashMap();
		try
		{
			Iterator iter = other_map.entrySet().iterator(); 
			while (iter.hasNext()) { 
			    Map.Entry entry = (Map.Entry) iter.next(); 	
			    String key = (String)entry.getKey();
			    if(key.startsWith(sub_id))	
			    {
			    	o.put(key, entry.getValue());
			    }
			}
		}catch(Exception e){			
			e.printStackTrace();
		}
		
		return o;
	}	
	
	/**
     * 设置其它参数值
     * @param Map m 
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static boolean setOtherParam(String sub_id,String param_name,String param_value)
	{
		try{
			other_map.put(sub_id+"_"+param_name, param_value);
			return true;
		}catch(Exception e){			
			e.printStackTrace();
			return false;
		}
	}
	
	/**
     * 判断内容是否有需要过滤的内容
     * @param String content 
     * @return boolean 如果有过滤内容，返回true,否则返回false
     * */
	public static boolean ifHaveKeyFilter(String content)
	{			
		if(keyword == null || "".equals(keyword))
		{
			return false;
		}
		else
		{
			String[] tempA = keyword.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				if(content.contains(tempA[i]))
				{
					return true;
				}
			}
			return false;
		}
		
	}
	
	/**
     * 替换发言内容中的关键字
     * @param ChatBean cb 
     * @return  
     * */
	public static void replacekeyWordByContent(ChatBean cb)
	{
		if(keyword != null && !"".equals(keyword))
		{
			String[] tempA = keyword.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				cb.setContent(cb.getContent().replaceAll(tempA[i], "***"));
			}
		}
	}
	
	/**
     * 判断昵称是否在昵称过滤中
     * @param String chat_user 
     * @return boolean 如果有过滤内容，返回true,否则返回false
     * */
	public static boolean ifHaveNickFilter(String chat_user)
	{				
		if(nick_filter == null || "".equals(nick_filter))
		{
			return false;
		}
		else
		{
			String[] tempA = nick_filter.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				if(chat_user.contains(tempA[i]))
				{
					return true;
				}
			}
			return false;
		} 
	}
	
	/***************** 实录状态下提取数据方法 开始 **************************/
	/**
     * 根据sub_id 从数据库中取出该主题ID的聊天数据
     * @param String sub_id 
     * @return List 所有聊天记录
     * */
	@SuppressWarnings("unchecked")
	public static List getChatListByDB(String sub_id)
	{		
		return ChatRoomDAO.getChatListBySubID(sub_id);		
	}
	/**
     * 得到历史视频
     * @param String sub_id 
     * @return String 视频地址
     * */
	public static String getHistoryVideo(String sub_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("sub_id", sub_id);
		m.put("affix_type", "video");
		m.put("affix_status", "history");
		List<SubjectResouse> l = ChatRoomDAO.getResouseList(m);
		if(l != null && l.size() >0)
		{
			return l.get(0).getAffix_path();
		}
		else
			return "";
	}
	
	/**
     * 得到附件列表
     * @param String sub_id
     * @param String affix_type  附件类型
     * @param String affix_status  附件状态
     * @return List 
     * */
	@SuppressWarnings("unchecked")
	public static List getResouseList(String sub_id,String affix_type,String affix_status)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("sub_id", sub_id);
		m.put("affix_type", affix_type);
		m.put("affix_status", affix_status);
		return ChatRoomDAO.getResouseList(m);
		
	}
	/***************** 实录状态下提取数据方法 结束 **************************/
	
	public static void main(String[] args)
	{
		System.out.println(getLivePicList("193ed61c-42dd-4e90-b5a3-8f8a48e02c21",0));
	}
}
