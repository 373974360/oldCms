package com.deya.wcm.dao.interview;
import java.util.List;
import java.util.Map;
import com.deya.wcm.bean.interview.*;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 * 聊天室功能数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 聊天室功能的数据处理</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class ChatRoomDAO {
	/**
     * 设置访问用户总数
     * @param ChatBean 
     * @return List　列表
     * */
	public static boolean setCountUser(String sub_id)
	{
		return DBManager.update("setCountUser", sub_id);
	}
	
	/**
     * 添加聊天记录
     * @param ChatBean 
     * @return List　列表
     * */
	public static boolean insertChat(ChatBean cb)
	{
		cb.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.INTERVIEW_CHAT_TABLE_NAME));
		return DBManager.insert("cp_subChat_insert", cb);
	}
	
	/**
     * 修改聊天记录
     * @param ChatBean 
     * @return List　列表
     * */
	public static boolean updateChat(ChatBean cb)
	{		
		return DBManager.update("cp_subChat_update", cb);
	}
	
	/**
     * 删除聊天记录
     * @param ChatBean 
     * @return List　列表
     * */
	public static boolean deleteChat(String chat_id)
	{		
		return DBManager.delete("cp_subChat_delete", chat_id);
	}
	/**
     * 得到所有处在直播状态的主题
     * @param ChatBean 
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getLiveStatusSubjectList(String id)
	{
		return DBManager.queryFList("getLiveStatusSubject", id);
	}
	
	/**
     * 根据主题Sub_id得到所有的聊天记录
     * @param ChatBean 
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getChatListBySubID(String sub_id)
	{
		return DBManager.queryFList("getChatListBySubID", sub_id);
	}
	
	/**
     * 审核通过聊天记录
     * @param ChatBean 
     * @return boolean
     * */
	public static boolean updateChatAuditStatus(String chat_id)
	{
		return DBManager.update("cp_subChat_auditstatus", chat_id);
	}
	
	/**
     * 插入访谈附件，精彩图片 针对多个记录的
     * @param ChatBean 
     * @return boolean
     * */
	public static boolean insertLiveAffix(SubjectResouse sr)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.INTERVIEW_RESOUSE_TABLE_NAME);
		sr.setId(id);
		return DBManager.update("insertLiveAffix", sr);
	}
	
	/**
     * 插入视频 针对只有唯一记录的
     * @param ChatBean 
     * @return boolean
     * */
	public static boolean insertLiveAffixForSingle(SubjectResouse sr)
	{
		sr.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.INTERVIEW_RESOUSE_TABLE_NAME));
		return DBManager.insert("insertLiveAffixForSingle", sr);
	}
	
	public static String getResouseInfoByCon(Map<String,String> m)
	{
		return DBManager.getString("getResouseInfoByCon", m);
	}
	
	public static boolean updateLiveAffixForSingle(SubjectResouse sr)
	{
		return DBManager.update("updateLiveAffixForSingle", sr);
	}
	
	/**
     * 删除精彩图片
     * @param int id
     * @return  boolean
     * */
	public static boolean deleteLivePic(int id)
	{
		return DBManager.delete("deleteLivePic", id);
	}
	
	/**
     * 得到该主题下的所有附件信息
     * @param Map m 
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getResouseBySubID(String sub_id)
	{
		return DBManager.queryFList("getResouseBySubID", sub_id);
	}
	/**
     * 得条件到附件信息
     * @param Map m 
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List<SubjectResouse> getResouseList(Map m)
	{
		return  DBManager.queryFList("getResouseList",m);
	}
}
