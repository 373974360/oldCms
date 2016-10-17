//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.deya.util.DateUtil;
import com.deya.util.UploadManager;
import com.deya.util.img.ImageUtils;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.material.MateInfoBean;
import com.deya.wcm.server.ServerManager;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.material.MateInfoManager;
import com.deya.wcm.services.material.MateInfoRPC;
import com.deya.wcm.services.system.config.ConfigManager;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class BinaryUploader {
    public static String NOTUPLOAT_FILE_EXT = ",php,php3,php5,phtml,asp,aspx,ascx,jsp,cfm,cfc,pl,bat,dll,reg,cgi,";

    public BinaryUploader() {
    }

    public static final State save(HttpServletRequest request, Map<String, Object> conf) {
        BaseState ueditorUpload = null;

        try {
            String e = request.getParameter("site_id");
            String savePath = UploadManager.getUploadFilePath(e) + "/";
            DiskFileItemFactory fac = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(fac);
            upload.setHeaderEncoding("utf-8");
            List fileList = null;

            try {
                fileList = upload.parseRequest(request);
            } catch (FileUploadException var21) {
                return null;
            }

            String is_press = request.getParameter("is_press");
            String press_osition = "9";
            Iterator it = fileList.iterator();
            long totalSpace = 0L;
            String name = "";
            String pic_name = "";
            String extName = "";
            FileItem item = null;

            String dateTime;
            for(dateTime = DateUtil.getCurrentDateTime("yyyyMMdd") + genRandomNum(10); it.hasNext(); item = null) {
                item = (FileItem)it.next();
                if(!item.isFormField()) {
                    break;
                }
            }

            if(item == null) {
                return new BaseState(false, 7);
            }

            name = item.getName();
            totalSpace = item.getSize();
            pic_name = name;
            if(name != null && !name.trim().equals("")) {
                if(name.lastIndexOf(".") >= 0) {
                    extName = name.substring(name.lastIndexOf(".")).toLowerCase();
                    if(NOTUPLOAT_FILE_EXT.contains("," + extName.substring(1) + ",")) {
                        return null;
                    }
                }

                File arr_id = null;
                name = dateTime;
                arr_id = new File(savePath + dateTime + extName);

                try {
                    if(!extName.equals(".gif") && !extName.equals(".jpg") && !extName.equals(".jpeg") && !extName.equals(".png")) {
                        File stl = new File(savePath + name + extName);
                        item.write(stl);

                        do {
                            System.out.println("正在写入非图片文件！");
                        } while(!stl.exists());
                    } else {
                        processImage(arr_id, item, name, extName, savePath, is_press, press_osition, e);
                    }
                } catch (Exception var22) {
                    var22.printStackTrace();
                }
            }

            savePath = UploadManager.getUploadFileUrl(e);
            if(ServerManager.isWindows()) {
                savePath = savePath.replaceAll("\\\\", "\\/");
                if(savePath.startsWith("//")) {
                    savePath = savePath.substring(1);
                }
            }

            String arr_id1 = MateInfoRPC.getArrIdFromTable();
            SettingLogsBean stl1 = LogManager.getSettingLogsByRequest(request);
            MateInfoBean mb = new MateInfoBean();
            mb.setAtt_id(Integer.parseInt(arr_id1));
            mb.setF_id(0);
            mb.setSite_id(e);
            if (stl1 != null) {
                mb.setUser_id(stl1.getUser_id());
            }
            mb.setAtt_path(UploadManager.getUploadFileSitePath(e));
            mb.setAtt_cname(name);
            mb.setHd_url(name + "_b" + extName);
            mb.setThumb_url(name + "_s" + extName);
            mb.setAtt_ename(name + extName);
            mb.setAlias_name(pic_name);
            mb.setFilesize(Long.valueOf(totalSpace));
            MateInfoManager.insertMateInfo(mb, stl1);
            ueditorUpload = new BaseState(true);
            ueditorUpload.putInfo("title", pic_name);
            ueditorUpload.putInfo("url", savePath + name + extName);
            ueditorUpload.putInfo("type", extName);
            ueditorUpload.putInfo("original", pic_name);
        } catch (Exception var23) {
            var23.printStackTrace();
        }

        return ueditorUpload;
    }

    public static void processImage(File file, FileItem item, String name, String extName, String savePath, String is_press, String press_osition, String site_id) throws IOException {
        try {
            String e = "";
            int press_img_width = 300;
            int press_img_height = 150;
            boolean alpha = true;
            int content_img_width = ImageUtils.getImgWidth();
            int prev_img_width = ImageUtils.getImgPreWidth();
            HashMap m = new HashMap();
            m.put("group", "attachment");
            m.put("site_id", site_id);
            m.put("app_id", "cms");
            Map con_map = ConfigManager.getConfigMap(m);
            String watermark = (String)con_map.get("watermark");
            String press_osition_config = (String)con_map.get("water_location");
            if("0".equals(press_osition)) {
                press_osition = press_osition_config;
            }

            e = (String)con_map.get("water_pic");
            String water_width;
            String water_height;
            String water_transparent;
            String normal_width;
            String thumb_width;
            if(e != null && !"".equals(e)) {
                water_width = JconfigUtilContainer.bashConfig().getProperty("img_domain", "", "resource_server");
                if(water_width != null && !"".equals(water_width)) {
                    e = JconfigUtilContainer.bashConfig().getProperty("save_path", "", "resource_server") + e;
                } else {
                    water_height = JconfigUtilContainer.bashConfig().getProperty("public_path", "", "resource_server");
                    water_transparent = JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path");
                    if(water_height != null && !"".equals(water_height)) {
                        e = water_transparent + e;
                    } else {
                        normal_width = UploadManager.getUploadFilePath(site_id) + "/";
                        thumb_width = UploadManager.getUploadFileSitePath(site_id);
                        e = normal_width.replace(thumb_width, "") + e;
                    }
                }
            }

            water_width = (String)con_map.get("water_width");
            if(water_width != null && !"".equals(water_width)) {
                press_img_width = Integer.parseInt(water_width);
            }

            water_height = (String)con_map.get("water_height");
            if(water_height != null && !"".equals(water_height)) {
                press_img_height = Integer.parseInt(water_height);
            }

            water_transparent = (String)con_map.get("water_transparent");
            int alpha1 = (int)(Float.parseFloat(water_transparent) * 100.0F);
            normal_width = (String)con_map.get("normal_width");
            if(normal_width != null && !"".equals(normal_width)) {
                content_img_width = Integer.parseInt(normal_width);
            }

            thumb_width = (String)con_map.get("thumb_width");
            if(thumb_width != null && !"".equals(thumb_width)) {
                prev_img_width = Integer.parseInt(thumb_width);
            }

            String is_compress = "1";
            if(con_map.containsKey("is_compress")) {
                is_compress = (String)con_map.get("is_compress");
            }

            BufferedImage bis = null;

            try {
                bis = ImageIO.read(item.getInputStream());
            } catch (Exception var30) {
                var30.printStackTrace();
                JPEGImageDecoder h = JPEGCodec.createJPEGDecoder(item.getInputStream());
                bis = h.decodeAsBufferedImage();
            }

            int w = bis.getWidth();
            int h1 = bis.getHeight();
            File saveFile;
            String thum_url;
            if(w > content_img_width) {
                saveFile = new File(savePath + name + "_b" + extName);
                thum_url = name + "_b" + extName;
                item.write(saveFile);

                do {
                    System.out.println("正在写入文件！");
                } while(!saveFile.exists());

                ImageUtils.cutImage(content_img_width, savePath + thum_url, savePath + name + extName);
                if("1".equals(is_compress) && "1".equals(is_press) && "1".equals(watermark) && e != null && !"".equals(e)) {
                    ImageUtils.addImgText(e, savePath + name + extName, Integer.parseInt(press_osition), extName, alpha1);
                }

                if(w > prev_img_width) {
                    String thum_url1 = name + "_s" + extName;
                    ImageUtils.cutImage(prev_img_width, savePath + thum_url, savePath + thum_url1);
                }
            } else {
                saveFile = file;
                item.write(file);

                do {
                    System.out.println("正在写入文件！");
                } while(!saveFile.exists());

                if("1".equals(is_press) && "1".equals(watermark) && w > press_img_width && h1 > press_img_height) {
                    ImageUtils.addImgText(e, savePath + name + extName, Integer.parseInt(press_osition), extName, alpha1);
                }

                if(w > prev_img_width) {
                    thum_url = name + "_s" + extName;
                    ImageUtils.cutImage(prev_img_width, savePath + name + extName, savePath + thum_url);
                }
            }
        } catch (Exception var31) {
            var31.printStackTrace();
        }

    }

    private static boolean validType(String type, String[] allowTypes) {
        List list = Arrays.asList(allowTypes);
        return list.contains(type);
    }

    public static String genRandomNum(int pwd_len) {
        boolean maxNum = true;
        int count = 0;
        char[] str = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();

        while(count < pwd_len) {
            int i = Math.abs(r.nextInt(36));
            if(i >= 0 && i < str.length) {
                pwd.append(str[i]);
                ++count;
            }
        }

        return pwd.toString();
    }

    public static void main(String[] args) {
    }
}
