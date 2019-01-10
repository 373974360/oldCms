package com.deya.project.zjzf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZjzfTypeManager {
    public static List<ZjzfTypeBean> getZjzfTypeList() {
        return ZjzfTypeDAO.getZjzfTypeList(new HashMap<String, String>());
    }

    public static ZjzfTypeBean getZjzfTypeBean(int id) {
        return ZjzfTypeDAO.getZjzfTypeBean(id);
    }

    public static boolean insertZjzfType(ZjzfTypeBean zjzf) {
        return ZjzfTypeDAO.insertZjzfType(zjzf);
    }

    public static boolean deleteZjzfType(Map<String, String> m) {
        return ZjzfTypeDAO.deleteZjzfType(m);
    }

    public static boolean updateZjzfType(ZjzfTypeBean zjzf) {
        return ZjzfTypeDAO.updateZjzfType(zjzf);
    }
}
