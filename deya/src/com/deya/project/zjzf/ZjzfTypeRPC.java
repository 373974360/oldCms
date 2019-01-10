package com.deya.project.zjzf;

import java.util.List;
import java.util.Map;

public class ZjzfTypeRPC {
    public static List<ZjzfTypeBean> getZjzfTypeList() {
        return ZjzfTypeManager.getZjzfTypeList();
    }

    public static ZjzfTypeBean getZjzfTypeBean(int id) {
        return ZjzfTypeManager.getZjzfTypeBean(id);
    }

    public static boolean insertZjzfType(ZjzfTypeBean zjzf) {
        return ZjzfTypeManager.insertZjzfType(zjzf);
    }

    public static boolean deleteZjzfType(Map<String, String> m) {
        return ZjzfTypeManager.deleteZjzfType(m);
    }

    public static boolean updateZjzfType(ZjzfTypeBean zjzf) {
        return ZjzfTypeManager.updateZjzfType(zjzf);
    }
}
