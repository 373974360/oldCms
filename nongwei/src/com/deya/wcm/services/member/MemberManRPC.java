package com.deya.wcm.services.member;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.member.*;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.org.user.SessionManager;

public class MemberManRPC {
    /**
     * 验证密码是否正确(用于修改密码)
     * @param MemberBean cb
     * @param String password
     *
     */
    public static boolean passWordCheck(String password,HttpServletRequest request)
    {
        return MemberManager.passWordCheck(getMemberBySession(request), password);
    }

    /**
     * 会员登录
     */
    public static String memberLogin(String me_account,String me_password,HttpServletRequest request)
    {
        return MemberLogin.memberLogin(me_account,me_password,request);
    }

    /**
     * 保存会员跳转前的页面地址
     */
    public static void setUrlToSession(String url,HttpServletRequest request)
    {
        SessionManager.set(request, "cicro_wcm_member_pro_url", url);
    }

    /**
     * 获取会员跳转前的页面地址
     */
    public static String getUrlFoSesson(HttpServletRequest request)
    {
        return (String)SessionManager.get(request, "cicro_wcm_member_pro_url");
    }

    /**
     * 从session得到会员对象
     *
     * @param HttpServletRequest request
     * @return MemberBean
     */
    public static MemberBean getMemberBySession(HttpServletRequest request)
    {
        return MemberLogin.getMemberBySession(request);
    }

    /**
     * 注销登录
     *
     * @param HttpServletRequest request
     * @return boolean
     */
    public static boolean logout(HttpServletRequest request){
        return MemberLogin.logout(request);
    }

    /**
     * 得到会员配置信息
     * @return	会员配置信息
     */
    public static MemberConfigBean getMemberConfigBean()
    {
        return MemberConfigManager.getMemberConfigBean();
    }

    /**
     * 修改会员配置信息
     * @param mcb	会员配置信息
     * @param request
     * @return	true 修改成功| false 修改失败
     */
    public static boolean updateMemberConfigBean(MemberConfigBean mcb, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null)
        {
            return MemberConfigManager.updateMemberConfig(mcb, stl);
        }
        else
        {
            return false;
        }
    }

    /**
     *  得到禁用用户名列表
     * @return	禁用用户名列表
     */
    public static List<String> getForbiddenName()
    {
        return MemberConfigManager.getForbiddenName();
    }

    /**
     * 得到会员分类信息列表
     * @return	会员信息列表
     */
    public static List<MemberCategoryBean> getMemberCategoryList()
    {
        return MemberCategoryManager.getAllMemberCateGoryList();
    }

    /**
     * 根据会员分类ID取得会员分类信息
     * @param id 会员分类ID
     * @return	会员分类信息
     */
    public static MemberCategoryBean getMemberCategoryByID(String id)
    {
        return MemberCategoryManager.getMemberCategoryByID(id);
    }

    /**
     * 插入会员分类信息
     * @param mcb	会员分类信息
     * @param request
     * @return	true 成功| false 失败
     */
    public static boolean insertMemberCategory(MemberCategoryBean mcb, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null)
        {
            return MemberCategoryManager.insertMemberCategory(mcb, stl);
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
    public static boolean saveMemberCategorySort(String mcat_id, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null)
        {
            return MemberCategoryManager.saveMemberCategorySort(mcat_id, stl);
        }
        else
        {
            return false;
        }
    }

    /**
     * 修改会员分类信息
     * @param mcb	会员分类信息
     * @param request
     * @return	true 成功| false 失败
     */
    public static boolean updateMemberCategory(MemberCategoryBean mcb, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null)
        {
            return MemberCategoryManager.updateMemberCategory(mcb, stl);
        }
        else
        {
            return false;
        }
    }

    /**
     * 删除会员分类信息
     * @param mp	删除条件
     * @param request
     * @return	true 成功| false 失败
     */
    public static boolean deleteMemberCategory(Map<String, String> mp, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null)
        {
            return MemberCategoryManager.deleteMemberCategory(mp, stl);
        }
        else
        {
            return false;
        }
    }

    /**
     * 得到会员列表
     * @param mp	取得列表条件
     * @return	会员列表
     */
    public static List<MemberBean> getMemberList(Map<String, String> mp)
    {
        return MemberManager.getMemberList(mp);
    }

    /**
     * 根据会员ID取得会员信息
     * @param id 会员ID
     * @return	会员信息
     */
    public static MemberBean getMemberBeanByID(String id)
    {
        return MemberManager.getMemberBeanByID(id);
    }

    /**
     * 取得会员信息条数
     * @param mp	条件
     * @return	会员数目
     */
    public static String getMemberCnt(Map<String, String> mp)
    {
        return MemberManager.getMemberCnt(mp);
    }

    /**
     * 取得会员登录名列表
     * @param lt	会员列表
     * @return	key=会员ID,values=会员名
     */
    public static Map<String, String> getMemberAccountList(List<MemberBean> lt)
    {
        return MemberManager.getMemberAccountList(lt);
    }

    /**
     * 修改会员信息
     * @param mb	会员对象
     * @param stl
     * @return	true 成功| false 失败
     */
    public static boolean updateMemberInfo(MemberBean mb, MemberRegisterBean mrb, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null)
        {
            return MemberManager.updateMemberInfo(mb, mrb, stl);
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
    public static boolean updateMember(MemberBean mb, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null)
        {
            return MemberManager.updateMember(mb, stl);
        }
        else
        {
            return false;
        }
    }

    /**
     * 根据用户ID修改用户状态信息
     * @param mp key 值为 me_status和ids
     * @return	true 成功| false 失败
     */
    public static boolean checkMemberByIDS(Map<String, String> mp, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null)
        {
            return MemberManager.checkMemberByIDS(mp, stl);
        }
        else
        {
            return false;
        }
    }

    /**
     * 通过会员ID取得会员帐号信息
     * @param account	会员ID
     * @return	会员帐号信息
     */
    public static MemberRegisterBean getMemberRegisterBeanByID(String id)
    {
        return MemberManager.getMemberRegisterBeanByID(id);
    }

    /**
     * 会员注册
     * @param mb	会员信息
     * @param mrb	帐号信息
     * @param request
     * @return	true 成功| false 失败
     */
    public static boolean RegisterMember(MemberBean mb, MemberRegisterBean mrb, HttpServletRequest request)
    {
        return MemberManager.RegisterMember(mb, mrb);
    }

    /**
     * 判断登录帐号是否存在
     * @param account	登录帐号
     * @return	true 存在| false 不存在
     */
    public static boolean isAccountExisted(String account)
    {
        return MemberManager.isAccountExisted(account);
    }

    /**
     * 删除会员信息
     * @param String me_ids
     * @param stl
     * @return	true 成功|false 失败
     */
    public static boolean deleteMember(String me_ids, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null)
        {
            return MemberManager.deleteMember(me_ids, stl);
        }
        else
        {
            return false;
        }
    }

    /******************* 分类与人员关联 开始 ********************************/
    /**
     * 根据会员分类ID得到关联的用户和用户组列表
     *
     * @param int wcat_id
     * @return list
     */
    public static List<MemberReleUser> getMemberReleUserListByCat(int wcat_id, String site_id)
    {
        return MemberReleUserManager.getMemberReleUserListByCat(wcat_id,site_id);
    }

    /**
     * 插入会员分类与人员的关联(以分类为主)
     *
     * @param int wcat_id
     * @param String usre_ids
     * @param String group_ids
     * @param String app_id
     * @param String site_id
     * @param SettingLogsBean stl
     * @return boolean
     */
    public static boolean insertMemberReleUserByCat(int wcat_id,String usre_ids,String group_ids,String app_id,String site_id,HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null)
        {
            return MemberReleUserManager.insertMemberReleUserByCat(wcat_id,usre_ids,group_ids,app_id,site_id,stl);
        }
        else
            return false;
    }

    /**
     * 根据用户ID，站点ID，应用ID得以它所能管理的会员分类ID集合
     *
     * @param String user_id
     * @param String site_id
     * @param String app_id
     * @return Set
     */
    public static String getMCatIDByUser(int user_id, String site_id){
        return MemberReleUserManager.getMCatIDByUser(user_id,site_id);
    }

    /******************* 分类与人员关联 结束 ********************************/

    public static void main(String arg[])
    {
        List<MemberCategoryBean> lt= getMemberCategoryList();
        for(int i=0; i<lt.size(); i++)
        {
            System.out.println(lt.get(i).getSort_id()+"-*-"+lt.get(i).getMcat_id());
        }
    }

}
