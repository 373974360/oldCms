package com.deya.wcm.server;

import java.io.File;
import java.util.Date;

import com.deya.license.GetLicense;
import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.io.FileOperation;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.services.control.site.SiteManager;


public class LicenseCheck {
    //public static String HAVE_APP_IDS = "system,org,cms,control,zwgk,appeal,ggfw";
    //public static String HAVE_APP_IDS = "system,org,cms,control,zwgk,appeal,ggfw";
    private static String HAVE_APP_IDS = JconfigUtilContainer.bashConfig().getProperty("apps", "", "have_apps");
    public static int LICENSE_SITE_NUM = 100;

    private static String defaultLicensePath = FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path") + File.separator + "license" + File.separator + "license.data");
    private static String defaultLicensePathRoot = FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path") + File.separator + "license");

    public static boolean isHaveApp(String app_id) {
        if ("all".equals(HAVE_APP_IDS)) {
            return true;
        }
        return HAVE_APP_IDS.contains(app_id);
    }

    public static boolean createLicense(String key) {
        try {
            File fileRoot = new File(defaultLicensePathRoot);
            if (!fileRoot.exists()) {
                fileRoot.mkdir();
            }
            FileOperation.writeStringToFile(new File(defaultLicensePath), key, false);
            if (!check()) {
                com.deya.wcm.startup.ServerListener.isLicenseRight = false;
                //FileOperation.deleteDir(defaultLicensePathRoot);
                return false;
            }
            com.deya.wcm.startup.ServerListener.isLicenseExist = true;
            com.deya.wcm.startup.ServerListener.isLicenseRight = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getAppForLicense() {
        return HAVE_APP_IDS;
    }

    public static boolean isLicenseExist() {
        try {
            if (new File(defaultLicensePath).exists()) {
                return true;
            }
            System.out.println("License----------not found-----");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean check() {
        try {
            GetLicense lic = new GetLicense("");

            if (!lic.ifServerIdentical()) {
                System.out.println("LicenseCheck : server CPU or MAC inconsistent  ");
                return false;
            }

            if (!checkTimeOut(lic.getLicenseItemValue("wcm", "time_limit"), lic.getLicenseItemValue("wcm", "start_time"))) {
                System.out.println("LicenseCheck : Licence timeout");
                return false;
            }

            LICENSE_SITE_NUM = Integer.parseInt(lic.getLicenseItemValue("wcm", "site_num"));
            if (checkSiteCount(LICENSE_SITE_NUM)) {
                System.out.println("LicenseCheck : More than the number of site licenses");
                return false;
            }

            String app_ids = lic.getLicenseItemValue("wcm", "app_ids");

            if (app_ids != null) {
                HAVE_APP_IDS = app_ids;
            }

            System.out.println("LicenseCheck : it's OK");
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
        return true;
    }

    public static boolean checkSiteCount(int lic_site_num) {
        try {
            int site_count = SiteManager.getSiteList().size();

            if (site_count == 0) {
                return false;
            }

            return site_count > lic_site_num;
        } catch (Exception e) {
            System.out.print(e);
        }
        return true;
    }

    public static boolean checkTimeOut(String time_limit, String start_time) {
        if ((time_limit == null) || ("".equals(time_limit)) || ("0".equals(time_limit))) {
            return true;
        }

        try {
            //System.out.println("startupDate===============" + start_time);

            if ((start_time == null) || ("".equals(start_time))) {
                System.out.println("LicenseCheck：startup time is null!");
                return false;
            }
            Date endDate_D = DateUtil.getDateTimesAfter(start_time + " 00:00:00", Integer.parseInt(time_limit) * 30);
            Date current_date = new Date();
            return endDate_D.after(current_date);
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e);
        }
        return false;
    }

    public static void main(String[] args) {
    }
}