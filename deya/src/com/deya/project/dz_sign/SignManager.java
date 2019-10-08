package com.deya.project.dz_sign;

import com.deya.util.DateUtil;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.List;
import java.util.Map;

/**
 * 描述：
 *
 * @Author: MaChaoWei
 * @Date: 2019/10/8 14:57
 */
public class SignManager {
    public static boolean insertSign(SignBean signBean){
        signBean.setSign_id(PublicTableDAO.getIDByTableName("dz_sign"));
        signBean.setSign_status(1);
        signBean.setSign_time(DateUtil.getCurrentDateTime());
        return SignDAO.insertSign(signBean);
    }

    public static List<SignBean> signBeanList(Map<String,String> map){
        return SignDAO.signBeanList(map);
    }
}
