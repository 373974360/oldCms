package com.deya.project.dz_sign;

import com.deya.util.DateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 *
 * @Author: MaChaoWei
 * @Date: 2019/10/8 14:57
 */
public class SignRPC {
    public static boolean insertSign(SignBean signBean){
        return SignManager.insertSign(signBean);
    }

    public static SignBean getSignByAccount(String account,String hallName){
        Map<String,String> map = new HashMap<>();
        map.put("me_account",account);
        map.put("hall_name",hallName);
        map.put("sign_time", DateUtil.getCurrentDate());
        map.put("start_num","0");
        map.put("page_size","20");
        List<SignBean> list = SignManager.getSignBeanList(map);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }


    public static List<SignBean> getSignBeanList(Map<String,String> map){
        return SignManager.getSignBeanList(map);
    }
    public static String getSignBeanListCount(Map<String,String> map){
        return SignManager.getSignBeanListCount(map);
    }
}
