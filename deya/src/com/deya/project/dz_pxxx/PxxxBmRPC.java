package com.deya.project.dz_pxxx;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.OutExcel;
import com.deya.util.jconfig.JconfigFactory;
import com.deya.util.jconfig.JconfigUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public class PxxxBmRPC {
    public static String getPxxxBmCount(Map<String, String> m) {
        return PxxxBmManager.getPxxxBmCount(m);
    }

    public static List<PxxxBmBean> getPxxxBmList(Map<String, String> m) {
        return PxxxBmManager.getPxxxBmList(m);
    }

    public static List<PxxxBmBean> getAllPxxxBmList() {
        return PxxxBmManager.getAllPxxxBmList();
    }

    public static List<PxxxBmBean> getAllPxxxBmByPxID(Map<String, String> m) {
        return PxxxBmManager.getAllPxxxBmByPxID(m);
    }

    public static PxxxBmBean getPxxxBmBean(String gq_id) {
        return PxxxBmManager.getPxxxBmBean(gq_id);
    }

    public static boolean updatePxxxBm(PxxxBmBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return PxxxBmManager.updatePxxxBm(hb, stl);
        }
        return false;
    }

    public static boolean insertPxxxBm(PxxxBmBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return PxxxBmManager.insertPxxxBm(hb, stl);
        }
        return false;
    }

    public static boolean deletePxxxBm(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return PxxxBmManager.deletePxxxBm(m, stl);
        }
        return false;
    }

    public static String exportPxxxBm(Map<String, String> m) {
        List<PxxxBmBean> pxxxBmList = getPxxxBmList(m);
        if (pxxxBmList != null && pxxxBmList.size() > 0) {
            String[][] data = new String[pxxxBmList.size() + 1][12];
            data[0][0] = "培训名称";
            data[0][1] = "报名ID";
            data[0][2] = "姓名";
            data[0][3] = "性别";
            data[0][4] = "身份证号";
            data[0][5] = "工作单位";
            data[0][6] = "职务";
            data[0][7] = "联系方式";
            data[0][8] = "继续教育证书编号";
            data[0][9] = "报名时间";
            data[0][10] = "是否住宿";
            data[0][11] = "备注";
            for (int i = 0; i < pxxxBmList.size(); i++) {
                PxxxBmBean pxxxBmBean = pxxxBmList.get(i);
                data[i + 1][0] = pxxxBmBean.getPxmc();
                data[i + 1][1] = pxxxBmBean.getBmid();
                data[i + 1][2] = pxxxBmBean.getXm();
                data[i + 1][3] = pxxxBmBean.getXb();
                data[i + 1][4] = pxxxBmBean.getSfzh();
                data[i + 1][5] = pxxxBmBean.getGzdw();
                data[i + 1][6] = pxxxBmBean.getZw();
                data[i + 1][7] = pxxxBmBean.getLxfs();
                data[i + 1][8] = pxxxBmBean.getQq();
                data[i + 1][9] = pxxxBmBean.getBmsj();
                data[i + 1][10] = pxxxBmBean.getSfzs();
                data[i + 1][11] = "";
            }
            String file_name = DateUtil.getCurrentDateTime("yyyyMMddHHmmss");
            JconfigUtil bc = JconfigFactory.getJconfigUtilInstance("bashConfig");
            String path = bc.getProperty("path", "", "manager_path").trim() + "/pxxxBm";
            String tempPath = path + "/" + DateUtil.getCurrentDate();
            File file2 = new File(FormatUtil.formatPath(tempPath));
            if (!file2.exists()) {
                file2.mkdirs();
            }
            String xFile = FormatUtil.formatPath(tempPath + "/" + file_name + ".xls");

            //OutExcel.deleteFile(path);

            OutExcel oe = new OutExcel("培训信息报名表");

            oe.doOut(xFile, data);
            return (tempPath + "/" + file_name + ".xls").replace("/cicro/wcm/vhosts/common", "");
        }
        return "";
    }
}

/* Location:           E:\Xshell\219.144.222.46(省水保)\classes\com.zip
 * Qualified Name:     com.deya.project.sb_xmgl.Sb_xmglRPC
 * JD-Core Version:    0.6.2
 */