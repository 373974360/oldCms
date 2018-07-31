package com.deya.wcm.services.cms.count;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.deya.util.FormatUtil;
import com.deya.util.OutExcel;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.appeal.sq.SQBean;
import com.deya.wcm.bean.cms.count.CmsCountBean;
import com.deya.wcm.bean.cms.count.SiteCountBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.dao.cms.count.CmsCountDAO;
import com.deya.wcm.services.appeal.count.CountUtil;
import com.deya.wcm.services.appeal.sq.SQManager;
import com.deya.wcm.services.org.user.UserLogin;

import javax.servlet.http.HttpServletRequest;

public class CmsCountRPC {
    /**
     * 按照栏目分类,或者日期取得站点统计信息
     *
     * @param mp
     * @return 取得的统计信息列表
     */
    public static List<CmsCountBean> getInfoCount(Map<String, String> mp) {
        return CmsCountManager.getInfoCount(mp);
    }

    /**
     * 按照用户分类,取得站点统计信息
     *
     * @param mp
     * @return 取得的统计信息列表
     */
    public static List<CmsCountBean> getInputCountList(Map<String, String> mp) {
        return CmsCountManager.getInputCountList(mp);
    }

    /**
     * 根据用户ID取得站点下的统计信息
     *
     * @param mp
     * @return 取得的统计信息列表
     */
    public static List<CmsCountBean> getInputCountListByUserID(Map<String, String> mp) {
        return CmsCountManager.getInputCountListByUserID(mp);
    }

    /**
     * 统计导出Excel文件处理类 -- 按栏目统计
     *
     * @param List listBean
     * @param List headerList
     * @return String 文件路径名字
     */
    public static String cmsInfoOutExcel(List listBean, List headerList) {
        return CmsCountExcelOut.cmsInfoOutExcel(listBean, headerList);
    }

    /**
     * 统计导出Excel文件 -- 按人员统计
     *
     * @param List listBean
     * @param List headerList
     * @return String 文件路径名字
     */
    public static String cmsWorkInfoOutExcel(List listBean, List headerList) {
        return CmsCountExcelOut.cmsWorkInfoOutExcel(listBean, headerList);
    }

    /**
     * 取得单个用户各个栏目下的工作量统计信息
     *
     * @param mp
     * @return 用户工作量统计信息列表
     */
    public static List<SiteCountBean> getInputCountListByUserIDCate(Map<String, String> mp) {
        return CmsCountManager.getInputCountListByUserIDCate(mp);
    }


    /**
     * 取得所选栏目下的工作量统计信息
     *
     * @param mp
     * @return 取得所选栏目下的工作量统计信息列表
     */
    public static List<SiteCountBean> getInputCountListByCate(Map<String, String> mp) {
        return CmsCountManager.getInputCountListByCate(mp);
    }

    /**
     * 取得所选栏目下的信息更新情况
     *
     * @param mp
     * @return 取得所选栏目下的信息更新列表
     */
    public static List<SiteCountBean> getInfoUpdateListByCate(Map<String, String> mp) {
        return CmsCountManager.getInfoUpdateListByCate(mp);
    }

    /**
     * 统计导出Excel文件 -- 取得所选栏目下的信息更新情况
     *
     * @param List listBean
     * @param List headerList
     * @return String 文件路径名字
     */
    public static String cmsUpdateInfoOutExcel(List listBean, List headerList) {
        return CmsCountExcelOut.cmsUpdateInfoOutExcel(listBean, headerList);
    }

    /**
     * 取用户工作量统计信息详细列表
     *
     * @param mp
     * @return 用户工作量统计信息详细列表
     */
    public static List<InfoBean> getInfoListByUserIDTimeType(Map<String, String> map) {
        return CmsCountManager.getInfoListByUserIDTimeType(map);
    }

    /**
     * 根据选择的行导出Excel
     */
    public static String getInfoListByUserIDTimeTypeExcel(Map<String, String> map) {
        List<InfoBean> list = CmsCountManager.getInfoListByUserIDTimeType(map);
        //删除今天以前的文件夹
        String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path");
        String path = FormatUtil.formatPath(root_path + "/cms/cmsCount/file/byuser/");
        CountUtil.deleteFile(path);

        //创建今天的文件夹和xls文件
        String nowDate = CountUtil.getNowDayDate();
        String fileTemp2 = FormatUtil.formatPath(path + File.separator + nowDate);
        File file2 = new File(fileTemp2);
        if (!file2.exists()) {
            file2.mkdir();
        }
        String nowTime = CountUtil.getNowDayDateTime();
        String xls = nowTime + CountUtil.getEnglish(1) + ".xls";
        String xlsFile = fileTemp2 + File.separator + xls;
        String urlFile = "/sys/cms/cmsCount/file/byuser/" + nowDate + File.separator + xls;

        String[] head = {"信息标题", "信息网址", "添加时间", "信息状态"};
        String[][] data = new String[list.size()][4];
        String url = "";
        String info_status = "";
        for (int i = 0; i < list.size(); i++) {
            InfoBean infoBean = list.get(i);
            data[i][0] = infoBean.getTitle();//信息标题
            url = infoBean.getContent_url();
            if (!url.substring(0, 4).equals("http")) {
                url = "http://www.xixianxinqu.gov.cn" + url;
            }
            data[i][1] = url;//信息网址
            data[i][2] = infoBean.getInput_dtime();//添加时间
            if (infoBean.getInfo_status() == 8) {
                info_status = "已发";
            }
            if (infoBean.getInfo_status() == 4) {
                info_status = "待发";
            }
            if (infoBean.getInfo_status() == 2) {
                info_status = "待审";
            }
            if (infoBean.getInfo_status() == 1) {
                info_status = "退稿";
            }
            if (infoBean.getInfo_status() == 0) {
                info_status = "草稿";
            }
            data[i][3] = info_status;//信息状态
        }

        OutExcel oe = new OutExcel("详细信息列表");
        oe.doOut(xlsFile, head, data);
        return urlFile;
    }


    /**
     * 按照作者分类,取得站点统计信息
     *
     * @param mp
     * @return 取得的统计信息列表
     */
    public static List<CmsCountBean> getAuthorCountList(Map<String, String> mp) {
        return CmsCountManager.getAuthorCountList(mp);
    }
}
