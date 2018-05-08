package com.deya.wcm.services.interview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deya.wcm.bean.interview.ChatBean;
import com.deya.wcm.bean.interview.GuestBean;
import com.deya.wcm.bean.interview.SubjectResouse;

/**
 * 聊天程序前台访问类.
 * <p>Title: CicroDate</p>
 * <p>Description: 为聊天程序前台访问提供接口</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class ChatRoomRPC {
	/**
     * 游客登录
     * @param String sub_id 
     * @return String　用户名
     * */
	public static String guestLoginSet(String sub_id,HttpServletRequest request)
	{
		String user_num = "";
		Cookie[] cookies = request.getCookies(); 
		
		if(cookies != null)
		{
			 for (int i = 0; i < cookies.length; i++) 
		    {
		       Cookie c = cookies[i];     
		       if(c.getName().equalsIgnoreCase("user_num"))
		       {
		          user_num = c.getValue();
		       }	            
		    } 		
		}	

		if(user_num == null || "".equals(user_num))
		{		
			user_num = ""+getUserMark(sub_id);
			Cookie newcookie = new Cookie("user_num",user_num);
			newcookie.setMaxAge(60 * 60 * 3);
						
		}
		return user_num;
	}
	
	/**
     * 设置其它参数值
     * @param Map m 
     * @return List　列表
     * */
	public static boolean setOtherParam(String sub_id,String param_name,String param_value)
	{
		return ChatRoomServices.setOtherParam(sub_id, param_name, param_value);
	}
	
	/**
     * 得到其它参数设置
     * @param String sub_id
     * @return Map
     * */
	public static Map getOtherParamSet(String sub_id)
	{
		return ChatRoomServices.getOtherMap(sub_id);
	}
	
	/**
     * 设置禁言类型
     * @param String sub_id
     * @param String pro_type
     * @return 
     * */
	public static void setProhibitType(String sub_id,String pro_type)
	{
		ChatRoomServices.setProhibitType(sub_id, pro_type);
	}
	
	/**
     * 得到禁言类型
     * @param String sub_id
     * @param String pro_type
     * @return 
     * */
	public static String getProhibitType(String sub_id)
	{
		return ChatRoomServices.getProhibitType(sub_id);
	}
	
	/**
     * 得到已被禁言的用户列表 
     * @param String sub_id
     * @return 
     * */
	public static List getProhibitUsers(String sub_id)
	{
		return ChatRoomServices.getProhibitUsers(sub_id);
	}
	
	/**
     * 设置当前访谈的禁言用户
     * @param String sub_id
     * @param String user_name
     * @return 
     * */
	public static void setProhibitUsers(String sub_id,String user_num)
	{
		ChatRoomServices.setProhibitUsers(sub_id,user_num);
	}
	
	/**
     * 记录当前在线用户
     * @param String sub_id
     * @param String user_name
     * @param HttpServletRequest request
     * @return int　序号
     * */
	public static void recordUserInfo(String sub_id,GuestBean gb,HttpServletRequest request)
	{		
		gb.setIp(request.getRemoteAddr());
		ChatRoomServices.recordUserInfo(sub_id, gb);
	}
	
	/**
     * 删除当前在线用户
     * @param String sub_id
     * @param String user_name
     * @return 
     * */
	public static void unRecordUserInfo(String sub_id,String user_name)
	{
		ChatRoomServices.unRecordUserInfo(sub_id, user_name);
	}
	
	/**
     * 得到当前在线用户集合
     * @param String sub_id
     * @return Map
     * */
	public static Map getLiveUserInfo(String sub_id)
	{
		return ChatRoomServices.getLiveUserInfo(sub_id);
	}
	
	/**
     * 当前用户登录后，获得其序号
     * @param String sub_id
     * @return int　序号
     * */
	public static int getUserMark(String sub_id)
	{
		return ChatRoomServices.getUserMark(sub_id);
	}
	
	/**
     * 获得图文区和文字互动区所有信息集合（后台管理页面用）
     * @param String sub_id
     * @param int pic_start_num 图文直播区消息起始下标
     * @param int text_start_num 文字互动区消息起始下标
     * @param is_p_audit 图文直播区发言是否需要审核 0为不需要，1为需要
     * @param is_t_audit 文字互动区发言是否需要审核 0为不需要，1为需要
     * @param is_show_text  是否有文字互动区 0为没有，1为有
     * @return List　列表
     * */
	public static HashMap getAllMessageListByAdmin(String sub_id,int pic_start_num,int text_start_num,int is_p_audit,int is_t_audit,int is_show_text)
	{
		HashMap h = new HashMap();
		HashMap temp_p = ChatRoomServices.getAllPicTextInfoListByAdmin(sub_id, pic_start_num, is_p_audit);
		//加入图文区信息
		if(temp_p != null)
		{
			h.putAll(temp_p);		
			temp_p.clear();
		}
		//加入文字互动区信息
		if(is_show_text == 1)
		{
			temp_p = ChatRoomServices.getAllTextInfoListByAdmin(sub_id, text_start_num, is_t_audit);
			if(temp_p != null)
				h.putAll(temp_p);
		}
		
		//得到其它设置参数集合
		h.put("other_set", ChatRoomServices.getOtherMap(sub_id));
		return h;
	}
	
	/**
     * 获得图文区所有信息集合（后台管理页面用）
     * @param String sub_id
     * @param int start_num 消息起始下标
     * @param is_p_audit 发言是否需要审核 0为不需要，1为需要
     * @return List　列表
     * */
	public static HashMap getAllPicTextInfoListByAdmin(String sub_id,int start_num,int is_p_audit)
	{
		return ChatRoomServices.getAllPicTextInfoListByAdmin(sub_id, start_num, is_p_audit);
	}
	
	/**
     * 获得所有信息集合（前台用户使用）
     * @param String sub_id
     * @param int start_num 消息起始下标
     * @param is_p_audit 发言是否需要审核 0为不需要，1为需要
     * @return List　列表
     * */
	public static HashMap getAllMessageList(String sub_id,int pic_start_num,int text_start_num,boolean is_sync)
	{				if(is_sync)		{			ChatRoomServices.reloadSubjectResouse(sub_id);		}		
		HashMap h = new HashMap();
		HashMap temp_p = ChatRoomServices.getAllPicTextInfoList(sub_id, pic_start_num);
	
		if(temp_p != null)
		{
			h.putAll(temp_p);		
			temp_p.clear();
		}	
		
		temp_p = ChatRoomServices.getTextInfoList(sub_id, text_start_num);
		if(temp_p != null)
			h.putAll(temp_p);		
		
//		得到其它设置参数集合
		h.put("other_set", ChatRoomServices.getOtherMap(sub_id));
		return h;
	}
	
	/**
     * 插入图文区信息（后台管理页面用）
     * @param ChatBean
     * @return 
     * */
	public static void setPicTextInfo(String sub_id,ChatBean cb,int is_p_audit,HttpServletRequest request)
	{
		cb.setIp(request.getRemoteAddr());
		ChatRoomServices.setPicTextInfo(sub_id, cb, is_p_audit,false);
	}
	
	/**
     * 插入文字互动区信息
     * @param String sub_id
     * @param int ChatBean cb
     * @param is_t_audit 文字互动区发言是否需要审核 0为不需要，1为需要
     * @return 
     * */
	public static void setTextInfo(String sub_id,ChatBean cb,int is_t_audit,HttpServletRequest request)
	{
		cb.setIp(request.getRemoteAddr());
		ChatRoomServices.setTextInfo(sub_id, cb, is_t_audit,false);
	}
	
	/**
     * 审核通过图文区信息（后台管理页面用）
     * @param ChatBean
     * @return 
     * */
	public static void isPassChat(ChatBean cb)
	{
		ChatRoomServices.isPassChat(cb);
	}
	
	/**
     * 修改聊天记录
     * @param ChatBean 
     * @param is_p_audit 图文直播区发言是否需要审核 0为不需要，1为需要
     * @param is_t_audit 文字互动区发言是否需要审核 0为不需要，1为需要
     * @param boolean isUpdateCatch 是否需要更新缓存，历史状态下修改信息不需要更新缓存  true 为需要 false为不需要
     * @return List　列表
     * */
	public static boolean updateChat(ChatBean cb,int is_p_audit,int is_t_audit,boolean isUpdateCatch)
	{
		return ChatRoomServices.updateChat(cb,is_p_audit,is_t_audit,isUpdateCatch);
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
		return ChatRoomServices.deleteChat(cb, is_p_audit, is_t_audit,isUpdateCatch);
	}
	
	/**
     * 插入精彩图片
     * @param SubjectResouse
     * @return boolean
     * */
	public static boolean insertLivePic(SubjectResouse sr)
	{
		return ChatRoomServices.insertLivePic(sr);
	}
	

	/**
     * 插入视频 
     * @param SubjectResouse
     * @return boolean
     * */
	public static boolean insertLiveVideo(SubjectResouse sr)
	{
		return ChatRoomServices.insertLiveVideo(sr);
	}
	
	/**
     * 删除精彩图片
     * @param int id
     * @return boolean
     * */
	public static boolean deleteLivePic(String sub_id,int id)
	{
		return ChatRoomServices.deleteLivePic(sub_id,id);
	}
	
	/**
     * 得到精彩图片列表()
     * @param String sub_id
     * @param int index_num list集合下标，从该数据后取值
     * @return List　列表
     * */
	public static List getLivePicList(String sub_id,int index_num)
	{
		return ChatRoomServices.getLivePicList(sub_id,index_num);
	}
	
	/***************** 实录状态下提取数据方法 开始 **************************/
	/**
     * 根据sub_id 从数据库中取出该主题ID的聊天数据
     * @param String sub_id 
     * @return List 所有聊天记录
     * */
	public static List getChatListByDB(String sub_id)
	{
		return ChatRoomServices.getChatListByDB(sub_id);
	}
	
	/**
     * 得到历史视频
     * @param String sub_id 
     * @return String 视频地址
     * */
	public static String getHistoryVideo(String sub_id)
	{
		return ChatRoomServices.getHistoryVideo(sub_id);
	}
	
	/**
     * 得到附件列表
     * @param String sub_id 
     * @param String affix_type  附件类型
     * @param String affix_status  附件状态
     * @return List 
     * */
	public static List getResouseList(String sub_id,String affix_type,String affix_status)
	{
		return ChatRoomServices.getResouseList(sub_id,affix_type,affix_status);
	}
	/***************** 实录状态下提取数据方法 结束 **************************/
}
