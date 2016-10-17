package com.deya.wcm.dao.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.member.MemberBean;
import com.deya.wcm.bean.member.MemberCategoryBean;
import com.deya.wcm.bean.member.MemberConfigBean;
import com.deya.wcm.bean.member.MemberRegisterBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 * 会员管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description:会员管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author liqi
 * @version 1.0
 * * 
 */
public class MemberDAO {

	/**
	 * 得到所有的会员配置信息列表
	 * @return
	 * 		会员配置信息列表
	 */
	@SuppressWarnings("unchecked")
	public static List<MemberConfigBean> getAllMemberConfigList()
	{
		return DBManager.queryFList("getAllMemberConfigList", "");
	}

	/**
	 * 修改会员配置信息
	 * @param mcb	会员配置信息对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateMemberConfig(MemberConfigBean mcb, SettingLogsBean stl)
	{
		if(DBManager.update("updateMemberConfig", mcb))
		{
			PublicTableDAO.insertSettingLogs("修改", "会员配置", mcb.getConfig_id(), stl);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 删除会员配置信息
	 * @param mp
	 * @param stl
	 * @return	true 成功|false 失败
	 */
	public static boolean deleteMemberConfig(Map<String, String> mp, SettingLogsBean stl)
	{
		if(DBManager.delete("deleteMemberConfig", mp))
		{
			PublicTableDAO.insertSettingLogs("删除", "会员配置", mp.get("config_ids"), stl);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 得到所有会员分类信息列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<MemberCategoryBean> getAllMemberCategoryList()
	{
		return DBManager.queryFList("getAllMemberCategoryList", "");
	}

	/**
	 * 添加会员分类信息
	 * @param mcb	会员分类信息对象
	 * @param stl
	 * @return	true 成功| false	 失败
	 */
	public static boolean insertMemberCategory(MemberCategoryBean mcb, SettingLogsBean stl)
	{
		String id = PublicTableDAO.getIDByTableName(PublicTableDAO.MEMBER_CATEGORY_TABLE_NAME)+"";
		mcb.setMcat_id(id);
		if(DBManager.insert("insertMemberCategory", mcb))
		{
			PublicTableDAO.insertSettingLogs("添加", "会员分类", mcb.getMcat_id(), stl);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 修改会员分类信息
	 * @param mcb	会员分类信息对象
	 * @param stl
	 * @return	true 成功| fasle 失败
	 */
	public static boolean updateMemberCategory(MemberCategoryBean mcb, SettingLogsBean stl)
	{
		if(DBManager.update("updateMemberCategory", mcb))
		{
			PublicTableDAO.insertSettingLogs("修改", "会员分类", mcb.getMcat_id(), stl);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
     * 保存会员分类排序
     * @param String menu_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public static boolean saveMemberCategorySort(String mcat_id,SettingLogsBean stl)
	{
		if(mcat_id != null && !"".equals(mcat_id))
		{
			try{
				Map<String, Comparable> m = new HashMap<String, Comparable>();
				String[] tempA = mcat_id.split(",");
				for(int i=0;i<tempA.length;i++)
				{
					m.put("sort_id", (i+1));
					m.put("mcat_id", tempA[i]);
					DBManager.update("update_memberCategory_sort", m);
				}
				PublicTableDAO.insertSettingLogs("保存排序","会员分类",mcat_id,stl);
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else
		{
			return true;
		}
	}

	/**
	 * 删除会员分类信息
	 * @param mp
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteMemberCategory(Map<String, String> mp, SettingLogsBean stl)
	{
		if(DBManager.delete("deleteMemberCategory", mp))
		{
			PublicTableDAO.insertSettingLogs("删除", "会员分类", mp.get("mcat_ids"), stl);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 得到所有会员列表，列表仅包含部分信息
	 * @return	会员信息列表
	 */
	@SuppressWarnings("unchecked")
	public static List<MemberBean> getAllMemberList()
	{
		return DBManager.queryFList("getAllMemberList", "");
	}

	/**
	 * 根据分页情况取得会员列表
	 * @param map
	 * @return	会员信息列表
	 */
	@SuppressWarnings("unchecked")
	public static List<MemberBean> getMemberListByDB(Map map)
	{
		//System.out.println(map.get("search_name")+"----"+map.get("search_value"));
		return DBManager.queryFList("getMemberListByDB", map);
	}

	/**
	 * 根据会员ID取得会员信息
	 * @param id	会员ID
	 * @return	会员信息对象
	 */
	public static MemberBean getMemberBeanByID(String id)
	{
		return (MemberBean)DBManager.queryFObj("getMemberBeanByID", id);
	}

	/**
	 * 插入会员信息
	 * @param mb	会员对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean insertMember(MemberBean mb)
	{
		if(DBManager.insert("insertMember", mb))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 修改会员信息
	 * @param mb	会员对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateMember(MemberBean mb, SettingLogsBean stl)
	{
		if(DBManager.update("updateMember", mb))
		{
			PublicTableDAO.insertSettingLogs("修改", "会员主表", mb.getMe_id(), stl);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 会员修改资料信息
	 * @param mb	会员对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateMemberBrowser(MemberBean mb)
	{
		return DBManager.update("updateMember_browser", mb);
	}

	/**
	 * 根据会员IDS修改会员状态
	 * @param mp	key 值为 me_status和ids
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean checkMemberByIDS(Map<String, String> mp, SettingLogsBean stl)
	{
		if(DBManager.update("checkMemberByIDS", mp))
		{
			PublicTableDAO.insertSettingLogs("修改", "会员主表", mp.get("me_ids"), stl);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 删除会员信息
	 * @param mp	key="me_ids" values=会员ID,多个之间使用","分隔
	 * @param stl
	 * @return	true 成功|false 失败
	 */
	public static boolean deleteMember(Map<String, String> mp, SettingLogsBean stl)
	{
		if(DBManager.delete("deleteMember", mp))
		{
			PublicTableDAO.insertSettingLogs("删除", "会员主表", mp.get("me_ids"), stl);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 取得所有会员帐号信息
	 * @return	会员帐号信息列表
	 */
	@SuppressWarnings("unchecked")
	public static List<MemberRegisterBean> getAllMemberRegisterList()
	{
		return DBManager.queryFList("getAllMemberRegisterList", "");
	}

	/**
	 * 根据会员登录帐号取得帐号信息
	 * @param account	会员登录帐号
	 * @return	会员帐号信息
	 */
	public static MemberRegisterBean getMemberRegisterByID(String id)
	{
		return (MemberRegisterBean)DBManager.queryFObj("getMemberRegisterByID", id);
	}

	/**
	 * 得到会员帐号条数
	 * @return	String 会员帐号条数
	 */
	public static String getMemberCnt(Map<String, String> mp)
	{
		return (String)DBManager.queryFObj("getMemberCount", mp);
	}

	/**
	 * 插入会员帐号信息
	 * @param mrb	会员帐号对象
	 * @param stl
	 * @return	true 成功|false 失败
	 */
	public static boolean insertMemberRegister(MemberRegisterBean mrb)
	{
		if(DBManager.insert("insertMemberRegister", mrb))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 修改用帐户信息
	 * @param mrb	用户帐户对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateMemberRegister(MemberRegisterBean mrb, SettingLogsBean stl)
	{
		if(DBManager.update("updateMemberRegister", mrb))
		{
			PublicTableDAO.insertSettingLogs("修改", "会员帐号", mrb.getRegister_id(), stl);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 修改会员密码
	 * @param String register_id
	 * @param String new_password
	 * @return	true 修改成功|false 修改失败
	 */
	public static boolean updateMemberPassword(String register_id,String password)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("register_id", register_id);
		m.put("me_password", password);
		return DBManager.update("update_member_password", m);
	}

	/**
	 * 删除会员帐号信息
	 * @param mp
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteMemberRegister(Map<String, String> mp, SettingLogsBean stl)
	{
		if(DBManager.delete("deleteMemberRegister", mp))
		{
			PublicTableDAO.insertSettingLogs("删除", "会员帐号", mp.get("register_ids"), stl);
			return true;
		}
		else
		{
			return false;
		}
	}

	public static void main(String args[])
	{
		updateMemberPassword("0","111");
	}

}
