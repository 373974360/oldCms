//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.deya.project.zjzf;

import com.deya.util.DateUtil;
import com.deya.wcm.dao.PublicTableDAO;
import java.util.List;
import java.util.Map;

public class ZJZFManager {
    public ZJZFManager() {
    }

    public static List<ZJZFBean> getGongMinList(Map<String, String> m) {
        return ZJZFDAO.getGongMinList(m);
    }

    public static String getGongMinListCount(Map<String, String> m) {
        return ZJZFDAO.getGongMinListCount(m);
    }

    public static ZJZFBean getGongMinBean(int id) {
        return ZJZFDAO.getGongMinBean(id);
    }

    public static boolean insertZJZF(ZJZFBean zjzf) {
        zjzf.setId(PublicTableDAO.getIDByTableName("project_zjzf"));
        zjzf.setAdd_time(DateUtil.getCurrentDateTime());
        return ZJZFDAO.insertZJZF(zjzf);
    }

    public static boolean deleteZJZF(Map<String, String> m) {
        return ZJZFDAO.deleteZJZF(m);
    }
}
