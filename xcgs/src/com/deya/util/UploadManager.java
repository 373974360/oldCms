package com.deya.util;

import java.io.File;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.services.control.domain.SiteDomainManager;
import com.deya.wcm.services.material.MateInfoRPC;

public class UploadManager {
    private static String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path");
    private static String hostRoot_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "hostRoot_path");
    private static String public_save_path = JconfigUtilContainer.bashConfig().getProperty("save_path", "", "resource_server");
    private static String public_path = JconfigUtilContainer.bashConfig().getProperty("public_path", "", "resource_server");
    private static String img_domain = JconfigUtilContainer.bashConfig().getProperty("img_domain", "", "resource_server");
    private static String site_port = JconfigUtilContainer.bashConfig().getProperty("port", "", "site_port");

    /**
     * 在上传之前获取密钥，用于做上传安全处理
     * @param
     * @return String
     * */
    public static String getUploadSecretKey()
    {
        String sid = "";
        try{
            //密钥字符串由3部分组成，上传字符串,当前时间，长整型字符串（会用于计算，1分钟内生效 ）,随机字符串，由逗号分割
            String key = RandomStrg.getRandomStr("",RandomStrg.getRandomStr("0-9","1"))+","+DateUtil.dateToTimestamp();
            //加密
            CryptoTools ct = new CryptoTools();
            //由于uploadfileify在获取参数的时候把+号换成空格，所以需要替换成一个特殊的字符串，然后转回来

            sid = ct.encode(key).replace("+", "WcMrEPlAcE").substring(3);
        }catch (Exception e) {
            e.printStackTrace();
            return 	sid;
        }

        return 	sid;
    }

    /**
     * 验证提交的字符串是否正确，且日期是否为当天的
     * @param
     * @return String
     * */
    public static boolean checkUploadSecretKey(String key)
    {
        try{//因为#号提交时可能会被过滤，所以在上面要去掉，这里加上
            if(key == null || "".equals(key.trim())){
                key = MateInfoRPC.getUploadSecretKey();
            }

            key = "=#="+key;
            CryptoTools ct = new CryptoTools();
            key = ct.decode(key.replace("WcMrEPlAcE", "+"));
            key = key.substring(key.indexOf(",")+1);

            int times = 60 * Integer.parseInt(JconfigUtilContainer.bashConfig().getProperty("value", "10", "upload_check_times"));
            long timel = Long.parseLong(key);

            //得到字符串的时间，如果在一分钟内，此key有效，否则无效果了
            return (DateUtil.dateToTimestamp() - timel) < (1000 * times + 100);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 得到附件路径
     *
     * @param return
     *            路径
     */
    public static String getUploadFilePath2()
    {
        String savePath = "";
        if(img_domain != null && !"".equals(img_domain.trim()))
        {
            savePath = FormatUtil.formatPath(public_save_path);
            return savePath;
        }else
        {
            return FormatUtil.formatPath(root_path);
        }
    }


    /**
     * 得到附件路径
     *
     * @param return
     *            路径
     */
    public static String getUploadFileUrl(String site_id)
    {
        String savePath = "";
        if ((img_domain != null) && (!"".equals(img_domain.trim())))
        {
            if(site_port != null && !"".equals(site_port.trim()))
            {
                savePath = "http://"+JconfigUtilContainer.bashConfig().getProperty("img_domain", "", "resource_server")+":"+site_port;
            }else{
                savePath = "http://"+JconfigUtilContainer.bashConfig().getProperty("img_domain", "", "resource_server");
            }
        }
        else
        {
            if(public_path != null && !"".equals(public_path))
            {
                savePath = "/cms.files";
            }
        }
        savePath = savePath + getUploadFileSitePath(site_id);
        return savePath;
    }


    /**
     * 根据站点ID得到附件的保存全路径
     *
     * @param String site_id
     *            站点ID
     * @param return
     *            路径
     */
    public static String getUploadFilePath(String site_id)
    {
        String savePath = "";
        if ((img_domain != null) && (!"".equals(img_domain.trim())))
        {
            savePath = FormatUtil.formatPath(public_save_path);
        }
        else
        {
            if(public_path != null && !"".equals(public_path))
            {
                savePath = FormatUtil.formatPath(public_path);
            }
            else
            {
                savePath = hostRoot_path + "/" + SiteDomainManager.getDefaultSiteDomainBySiteID(site_id) + "/ROOT";
            }

        }
        savePath = FormatUtil.formatPath(savePath + getUploadFileSitePath(site_id));
        File f1 = new File(savePath);
        if (!f1.exists()) {
            f1.mkdirs();
        }
        return savePath;
    }

    /**
     * 根据站点ID得到附件的保存路径
     *
     * @param String site_id
     *            站点ID
     * @param return
     *            路径
     */
    public static String getUploadFileSitePath(String site_id)
    {
        return "/upload/" + site_id + "/" + DateUtil.getCurrentDateTime("yyyyMM")+"/";
    }

    /**
     * 根据站点ID得到附件的访问路径
     *
     * @param String site_id
     *            站点ID
     * @param return
     *            访问路径
     */
    public static String getImgBrowserUrl(String site_id)
    {
        if(img_domain != null && !"".equals(img_domain.trim()))
            if(site_port != null && !"".equals(site_port.trim()))
            {
                return "http://"+JconfigUtilContainer.bashConfig().getProperty("img_domain", "", "resource_server")+":"+site_port;
            }else{
                return "http://"+JconfigUtilContainer.bashConfig().getProperty("img_domain", "", "resource_server");
            }
        else
        {
			/*
			if(site_id != null && !"".equals(site_id.trim()))
			{
				if(site_port != null && !"".equals(site_port.trim()))
				{
					return "http://"+SiteDomainManager.getDefaultSiteDomainBySiteID(site_id)+":"+site_port;
				}else{
					return "http://"+SiteDomainManager.getDefaultSiteDomainBySiteID(site_id);
				}
			}else{
				return "";
			}
			*/
            return "";
        }
    }

    /**
     * 百度上传文件列出目录
     *
     * @param String site_id
     *            站点ID
     * @param return
     *            路径
     */
    public static String getUploadFilePathForUeditor(String site_id)
    {
        String savePath = "";
        if ((img_domain != null) && (!"".equals(img_domain.trim())))
        {
            savePath = FormatUtil.formatPath(public_save_path);
        }
        else
        {
            if(public_path != null && !"".equals(public_path))
            {
                savePath = FormatUtil.formatPath(public_path);
            }
            else
            {
                savePath = hostRoot_path + "/" + SiteDomainManager.getDefaultSiteDomainBySiteID(site_id) + "/ROOT";
            }

        }
        String dirString = getUploadFileSitePath(site_id);
        savePath = FormatUtil.formatPath(savePath + dirString.substring(0, dirString.length() - 8));
        File f1 = new File(savePath);
        if (!f1.exists()) {
            f1.mkdirs();
        }
        return savePath;
    }

    /**
     * 百度上传文件列出目录
     *
     * @param String site_id
     *            站点ID
     * @param return
     *            路径
     */
    public static String getFileUrlForUeditor(String site_id)
    {
        String savePath = "";
        if ((img_domain != null) && (!"".equals(img_domain.trim())))
        {
            if(site_port != null && !"".equals(site_port.trim()))
            {
                savePath = "http://"+JconfigUtilContainer.bashConfig().getProperty("img_domain", "", "resource_server")+":"+site_port;
                return savePath;
            }else{
                savePath = "http://"+JconfigUtilContainer.bashConfig().getProperty("img_domain", "", "resource_server");
                return savePath;
            }
        }
        else
        {
            if(public_path != null && !"".equals(public_path))
            {
                savePath = root_path;
                return savePath;
            }
            else
            {
                savePath = hostRoot_path + "/" + SiteDomainManager.getDefaultSiteDomainBySiteID(site_id) + "/ROOT";
                return savePath;
            }
        }
    }

    public static void main(String[] args)
    {
        //String str = getUploadSecretKey();
        //System.out.println("www.so.com:99".replaceAll("(.*)([:][0-9]*)(.*?)", "$1"));
        System.out.println("/deya/cms/vhosts/www.demo.com/ROOT/upload/CMSdemo/201508".replace("/upload/CMSdemo/201508/", ""));
    }
}