//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.deya.project.zjzf;

import java.util.List;
import java.util.Map;

public class ZJZFRPC {
    public ZJZFRPC() {
    }

    public static List<ZJZFBean> getGongMinList(Map<String, String> m) {
        return ZJZFManager.getGongMinList(m);
    }

    public static String getGongMinListCount(Map<String, String> m) {
        return ZJZFManager.getGongMinListCount(m);
    }

    public static ZJZFBean getGongMinBean(int id) {
        return ZJZFManager.getGongMinBean(id);
    }

    public static boolean insertZJZF(ZJZFBean zjzf) {
        return ZJZFManager.insertZJZF(zjzf);
    }

    public static boolean deleteZJZF(Map<String, String> m) {
        return ZJZFManager.deleteZJZF(m);
    }
}
