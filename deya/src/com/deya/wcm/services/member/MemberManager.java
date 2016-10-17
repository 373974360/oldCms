package com.deya.wcm.services.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.util.CryptoTools;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.member.MemberBean;
import com.deya.wcm.bean.member.MemberRegisterBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.member.MemberDAO;

public class MemberManager implements ISyncCatch{

    private static Map<String, MemberBean> member_map = new HashMap<String, MemberBean>();
    private static Map<String, MemberRegisterBean> mem_reg_map = new HashMap<String, MemberRegisterBean>();//key=会员ID,values=登录名

    static{
        reloadCatchHandl();
    }

    public void reloadCatch()
    {
        reloadCatchHandl();
    }

    public static void reloadCatchHandl()
    {
        member_map.clear();
        List<MemberBean> ltMember = MemberDAO.getAllMemberList();
        if(ltMember != null && ltMember.size()>0)
        {
            for(int i=0;i<ltMember.size(); i++)
            {
                member_map.put(ltMember.get(i).getMe_id(), ltMember.get(i));
            }
        }

        mem_reg_map.clear();
        List<MemberRegisterBean> ltReg = MemberDAO.getAllMemberRegisterList();
        if(ltReg != null && ltReg.size()>0)
        {
            for(int i=0;i<ltReg.size(); i++)
            {
                mem_reg_map.put(ltReg.get(i).getMe_account(), ltReg.get(i));
            }
        }
    }

    /**
     * 初始化会员管理
     */
    public static void reloadMemberMap()
    {
        SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.member.MemberManager");
    }

    public static void reloadRegisterMap()
    {
        SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.member.MemberManager");
    }

    /**
     * 会员登录
     */
    public static String memberLogin(String me_account,String me_password)
    {
        CryptoTools ct = new CryptoTools();
        me_password = ct.encode(me_password);
        if(mem_reg_map.containsKey(me_account))
        {
            MemberRegisterBean mrb = mem_reg_map.get(me_account);
            if(me_password.equals(mrb.getMe_password()))
            {
                System.out.println(member_map.size());
                MemberBean mb = member_map.get(mrb.getMe_id());
                if(mb != null)
                {
                    if(mb.getMe_status() == 1)
                    {
                        return "true";
                    }
                    else
                        return mb.getMe_status()+"";
                }
                else
                    return "false";
            }else
                return "false";
        }else
            return "false";
    }

    /**
     * 验证密码是否正确(用于修改密码)
     * @param MemberBean cb
     * @param String password
     *
     */
    public static boolean passWordCheck(MemberBean cb,String password)
    {
        MemberRegisterBean mb = getMemberRegisterBeanByID(cb.getMe_id());
        return password.equals(mb.getMe_password());
    }

    /**
     * 根据帐号得到会员对象
     */
    public static MemberBean getMemberBenaByAccount(String me_account)
    {
        MemberRegisterBean mrb = mem_reg_map.get(me_account);
        MemberBean mb = member_map.get(mrb.getMe_id());
        if(mb != null)
            return mb;
        else
            return null;
    }

    /**
     * 根据分页信息取得会员信息列表
     * @param mp
     * @return	会员信息列表
     */
    @SuppressWarnings("unchecked")
    public static List<MemberBean> getMemberList(Map mp)
    {
        return MemberDAO.getMemberListByDB(mp);
    }

    /**
     * 取得会员信息条数
     * @param mp	条件
     * @return	会员数目
     */
    public static String getMemberCnt(Map<String, String> mp)
    {
        return MemberDAO.getMemberCnt(mp);
    }

    /**
     * 根据会员ID取得会员信息
     * @param id	会员ID
     * @return	会员信息
     */
    public static MemberBean getMemberBeanByID(String id)
    {
        return MemberDAO.getMemberBeanByID(id);
    }

    /**
     * 添加会员信息
     * @param mb	会员对象
     * @param stl
     * @return	true 成功| false 失败
     */
    private static boolean insertMember(MemberBean mb)
    {
        if(MemberDAO.insertMember(mb))
        {
            reloadMemberMap();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 修改会员帐号，会员信息
     * @param mb	会员信息对象
     * @param mrb	会员帐号对象
     * @param stl
     * @return	true 成功| false 失败
     */
    public static boolean updateMemberInfo(MemberBean mb, MemberRegisterBean mrb, SettingLogsBean stl)
    {
        CryptoTools ct = new CryptoTools();
        mrb.setMe_password(ct.encode(mrb.getMe_password()));
        boolean flg = updateMemberRegister(mrb, stl);
        if(flg)
        {
            flg = updateMember(mb, stl);
        }
        return flg;
    }

    /**
     * 修改会员信息
     * @param mb	会员对象
     * @param stl
     * @return	true 成功| false 失败
     */
    public static boolean updateMember(MemberBean mb, SettingLogsBean stl)
    {
        if(MemberDAO.updateMember(mb, stl))
        {
            reloadMemberMap();
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
        if(MemberDAO.updateMemberBrowser(mb))
        {
            reloadMemberMap();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 根据用户ID修改用户状态
     * @param mp	key 值为 me_status和ids
     * @return	用户信息列表
     */
    public static boolean checkMemberByIDS(Map<String, String> mp, SettingLogsBean stl)
    {
        if(MemberDAO.checkMemberByIDS(mp, stl))
        {
            reloadMemberMap();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 通过会员ID取得会员帐号信息
     * @param id	会员ID
     * @return	会员帐号信息
     */
    public static MemberRegisterBean getMemberRegisterBeanByID(String id)
    {
        MemberRegisterBean mrb = MemberDAO.getMemberRegisterByID(id);
        CryptoTools ct = new CryptoTools();
        mrb.setMe_password(ct.decode(mrb.getMe_password()));
        return mrb;
    }

    /**
     * 取得会员登录名
     * @param lt	会员列表
     * @return	key=会员ID,values=会员名
     */
    public static Map<String, String> getMemberAccountList(List<MemberBean> lt)
    {
        Map<String, String> ret = new HashMap<String, String>();
        if(lt != null && lt.size()>0)
        {
            for(int i=0; i<lt.size(); i++)
            {
                String key = lt.get(i).getMe_id();
                ret.put(key, getMemberAccount(key));
            }
        }
        return ret;
    }

    /**
     * 取得会员登录名
     * @param lt	会员列表
     * @return	key=会员ID,values=会员名
     */
    public static String getMemberAccount(String me_id)
    {
        Set<String> set = mem_reg_map.keySet();
        for(String i : set){
            MemberRegisterBean mrb = mem_reg_map.get(i);
            if(me_id.equals(mrb.getMe_id()))
                return mrb.getMe_account();
        }
        return "";
    }

    /**
     * 判断登录名是否存在
     * @param account	登录名
     * @return	true 存在| false 不存在
     */
    public static boolean isAccountExisted(String account)
    {
        if(mem_reg_map.keySet().contains(account))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 插入会员帐号信息
     * @param mrb	会员帐号对象
     * @param stl
     * @return	true 成功| false 失败
     */
    private static boolean insertMemberRegister(MemberRegisterBean mrb)
    {
        CryptoTools ct = new CryptoTools();
        mrb.setMe_password(ct.encode(mrb.getMe_password()));
        if(!isAccountExisted(mrb.getMe_account()) && MemberDAO.insertMemberRegister(mrb))
        {
            reloadRegisterMap();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 添加注册会员信息
     * @param mb	会员信息
     * @param mrb	帐号信息
     * @param stl
     * @return	true 添加成功| false 添加失败
     */
    public static boolean RegisterMember(MemberBean mb, MemberRegisterBean mrb)
    {
        String me_id = PublicTableDAO.getIDByTableName(PublicTableDAO.MEMBER_TABLE_NAME)+"";
        String reg_id = PublicTableDAO.getIDByTableName(PublicTableDAO.MEMBER_REGISTER_TABLE_NAME)+"";
        mb.setMe_id(me_id);
        mrb.setMe_id(me_id);
        mrb.setRegister_id(reg_id);

        boolean flg = insertMemberRegister(mrb);
        if(flg)
        {
            flg = insertMember(mb);
        }
        return flg;
    }

    /**
     * 修改会员密码
     * @param String me_id
     * @param String new_password
     * @return	true 修改成功|false 修改失败
     */
    public static boolean updateMemberPassword(String me_id,String password)
    {
        MemberRegisterBean mrb =  getMemberRegisterBeanByID(me_id);
        CryptoTools ct = new CryptoTools();
        password = ct.encode(password);
        if(MemberDAO.updateMemberPassword(mrb.getRegister_id(), password))
        {
            reloadRegisterMap();
            return true;
        }else
            return false;
    }

    /**
     * 修改会员帐号信息
     * @param mrb	会员帐号信息
     * @param stl
     * @return	true 修改成功|false 修改失败
     */
    public static boolean updateMemberRegister(MemberRegisterBean mrb, SettingLogsBean stl)
    {
        if(MemberDAO.updateMemberRegister(mrb, stl))
        {
            reloadRegisterMap();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 删除会员帐号信息
     * @param String me_ids
     * @param stl
     * @return	boolean
     */
    public static boolean deleteMember(String me_ids,SettingLogsBean stl)
    {
        Map<String, String> mp = new HashMap<String, String>();
        mp.put("ids", me_ids);
        mp.put("me_status", "-2");
        if(MemberDAO.checkMemberByIDS(mp, stl))
        {
            reloadRegisterMap();
            reloadMemberMap();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * @param arg
     */
    public static void main(String arg[])
    {
		/*
		MemberBean mb = new MemberBean();
		MemberRegisterBean mrb = new MemberRegisterBean();
		mb.setMe_nickname("testIN");
		mrb.setMe_account("liqi_huaidan");
		mrb.setMe_password("123456");		
		RegisterMember(mb, mrb, new SettingLogsBean());*/
        System.out.println(getMemberBenaByAccount("qier121"));

    }

}
