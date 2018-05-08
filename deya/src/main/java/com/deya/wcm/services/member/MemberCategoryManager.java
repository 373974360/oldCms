package com.deya.wcm.services.member;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.member.MemberCategoryBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.member.MemberDAO;

public class MemberCategoryManager implements ISyncCatch{

    private static Map<String ,MemberCategoryBean> cate_map = new TreeMap<String, MemberCategoryBean>();

    static{
        reloadCatchHandl();
    }

    public void reloadCatch()
    {
        reloadCatchHandl();
    }

    public static void reloadCatchHandl()
    {
        cate_map.clear();
        List<MemberCategoryBean> lt = MemberDAO.getAllMemberCategoryList();
        if(lt != null && lt.size()>0)
        {
            for(int i=0; i<lt.size(); i++)
            {
                cate_map.put(lt.get(i).getMcat_id(), lt.get(i));
            }
        }
    }

    /**
     * 初始化加载会员分类信息
     */
    public static void reloadMap()
    {
        SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.member.MemberCategoryManager");
    }

    /**
     * 得到会员分类列表
     * @return	会员分类列表
     */
    public static List<MemberCategoryBean> getAllMemberCateGoryList()
    {
        List<MemberCategoryBean> ret = new ArrayList<MemberCategoryBean>();
        Iterator<MemberCategoryBean> it = cate_map.values().iterator();
        while(it.hasNext())
        {
            ret.add(it.next());
        }
        Collections.sort(ret, new CateComparator());

        return ret;
    }

    /**
     * 根据会员分类ID取得会员分类信息
     * @param id 会员分类ID
     * @return	会员分类信息
     */
    public static MemberCategoryBean getMemberCategoryByID(String id)
    {
        MemberCategoryBean mcb = cate_map.get(id);
        return mcb;
    }

    /**
     * 添加会员分类
     * @return	true 成功| false 失败
     */
    public static boolean insertMemberCategory(MemberCategoryBean mcb, SettingLogsBean stl)
    {
        if(MemberDAO.insertMemberCategory(mcb, stl))
        {
            reloadMap();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 修改会员分类信息
     * @param mcb	会员分类对象
     * @param stl
     * @return	true 成功| false 失败
     */
    public static boolean updateMemberCategory(MemberCategoryBean mcb, SettingLogsBean stl)
    {
        if(MemberDAO.updateMemberCategory(mcb, stl))
        {
            reloadMap();
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
    public static boolean saveMemberCategorySort(String mcat_id,SettingLogsBean stl)
    {
        if(MemberDAO.saveMemberCategorySort(mcat_id, stl))
        {
            reloadMap();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 删除会员分类信息
     * @param mp	key=mcat_ids,values=会员分类IDS
     * @param stl
     * @return	true 成功| false 失败
     */
    public static boolean deleteMemberCategory(Map<String, String> mp, SettingLogsBean stl)
    {
        if(MemberDAO.deleteMemberCategory(mp, stl))
        {
            reloadMap();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 会员分类比较器，比较会员分类对象的sort_id
     * @author Administrator
     *
     */
    static class CateComparator implements Comparator<MemberCategoryBean>
    {
        @Override
        public int compare(MemberCategoryBean bean1, MemberCategoryBean bean2)
        {
            int ret = 0;
            if(bean1.getSort_id() > bean2.getSort_id())
            {
                ret = 1;
            }else if(bean1.getSort_id() == bean2.getSort_id())
            {
                ret = 0;
            }else
            {
                ret = -1;
            }
            return ret;
        }
    }
}


