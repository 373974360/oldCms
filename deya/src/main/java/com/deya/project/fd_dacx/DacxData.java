package com.deya.project.fd_dacx;


import com.deya.util.FormatUtil;

import java.util.HashMap;
import java.util.Map;

public class DacxData {

    public static void getDacxSearchCon(String params, Map<String, String> con_map) {
        String[] tempA = params.split(";");
        for (int i = 0; i < tempA.length; i++) {
            if (tempA[i].toLowerCase().startsWith("name=")) {
                String name = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(name)) && (!name.startsWith("$name")) && (FormatUtil.isValiditySQL(name))) {
                    con_map.put("name", name);
                }
            }

            if (tempA[i].toLowerCase().startsWith("idcard=")) {
                String idCard = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(idCard)) && (!idCard.startsWith("$idcard")) && (FormatUtil.isValiditySQL(idCard))) {
                    con_map.put("idCard", idCard);
                }
            }
        }
    }

    public static DacxBean getXmkObject(String params) {
        Map con_map = new HashMap();
        getDacxSearchCon(params, con_map);
        return DacxManager.getDacxBean(con_map);
    }
}