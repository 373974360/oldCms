package com.deya.project.dz_sign;

import com.deya.wcm.db.DBManager;

import java.util.List;
import java.util.Map;

/**
 * 描述：
 *
 * @Author: MaChaoWei
 * @Date: 2019/10/8 14:57
 */
public class SignDAO {

    public static boolean insertSign(SignBean signBean) {
        return DBManager.insert("insert_sign", signBean);
    }

    public static List<SignBean> signBeanList(Map<String,String> map){
        return DBManager.queryFList("getSignBeanList", map);
    }
}
