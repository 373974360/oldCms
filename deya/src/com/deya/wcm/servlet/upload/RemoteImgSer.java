package com.deya.wcm.servlet.upload;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.UploadManager;
import com.deya.util.img.ImageUtils;
import com.deya.util.io.FileOperation;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.material.MateInfoBean;
import com.deya.wcm.server.ServerManager;
import com.deya.wcm.services.material.MateInfoManager;
import com.deya.wcm.services.system.config.ConfigManager;

public class RemoteImgSer extends HttpServlet {
    private static String hd_url = "";//大图地址
    private static String thum_url = "";//缩略图地址
    private static String att_cname = "";//中文名称　原文件名
    private static long file_size = 0;//中文名称　原文件名
    private static String att_ename = "";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String num = request.getParameter("num");
        String callback = request.getParameter("jsonp");
        String sid = request.getParameter("sid");
        String site_id = request.getParameter("site_id");
        String user_id = request.getParameter("user_id");
        String src = request.getParameter("src");
        String savePath = UploadManager.getUploadFilePath(site_id) + "/";
        String url = UploadManager.getUploadFileSitePath(site_id);
        String img_domain = UploadManager.getImgBrowserUrl(site_id);
        sid = sid.replaceAll("cicro", "#");
        //System.out.println("savePath--------------------"+savePath);
        if (sid != null && !"".equals(sid) && !UploadManager.checkUploadSecretKey(sid)) {
            System.out.println("Upload validation errors");
            return;
        }
        try {
            thum_url = "";
            hd_url = "";
            att_cname = "";
            file_size = 0;

            String fileName = num + DateUtil.getCurrentDateTime("yyyyMMddhhmmsss");
            String extName = "";
            if (src.lastIndexOf(".") >= 0) {
                extName = src.substring(src.lastIndexOf(".")).toLowerCase();
                if (!UploadFileIfy.UPLOAT_FILE_EXT.contains("," + extName.substring(1) + ",")){
                    System.out.println("非法文件上传，后缀名："+extName+"；不允许上传！");
                    return;
                }
            }
            if (saveImgUrlAs(src, savePath, fileName, extName, site_id)) {
                att_ename = fileName + extName;
                String tmpUploadFilePath = UploadManager.getUploadFilePath2();
                savePath = savePath.substring(tmpUploadFilePath.length());
                if (ServerManager.isWindows()) {
                    savePath = savePath.replaceAll("\\\\", "\\/");
                    if (savePath.startsWith("//"))
                        savePath = savePath.substring(1);
                }
                addMateInfo(site_id, savePath, Integer.parseInt(user_id));

                //response.getWriter().println(callback+"({\"img_path\":\""+img_domain+savePath+fileName+extName+"\",\"num\":\""+num+"\"})");
                response.getWriter().println(callback + "({\"img_path\":\"" + url + fileName + extName + "\",\"num\":\"" + num + "\"})");
            } else
                response.getWriter().println("");
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            response.getWriter().println("");
        }
    }

    public static boolean saveImgUrlAs(String fileUrl, String savePath, String fileName, String extName, String site_id) {

        try {
            att_cname = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            String att_ename = fileName + extName;
            URL url = new URL(fileUrl);
            BufferedImage image = ImageIO.read(url);
            //ImageIO.write(image, "jpg", new File(savePath));

            return processImage(new File(FormatUtil.formatPath(savePath + att_ename)), image, fileName, extName, savePath, "0", "0", site_id, fileUrl);

        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e + fileUrl + savePath);
            return false;
        }
    }

    public static boolean processImage(File file, BufferedImage bis, String name, String extName, String savePath, String is_press, String press_osition, String site_id, String fileUrl) throws IOException {
        try {
            File saveFile;
            int w = bis.getWidth();
            int content_img_width = ImageUtils.getImgWidth();//文章正文的图片宽度
            int prev_img_width = ImageUtils.getImgPreWidth();//缩略图的图片宽度
            Map<String, String> m = new HashMap<String, String>();//取站点素材配置信息
            m.put("group", "attachment");
            m.put("site_id", site_id);
            m.put("app_id", "cms");
            Map<String, String> con_map = ConfigManager.getConfigMap(m);
            String normal_width = con_map.get("normal_width");//文章正文的图片宽度
            if (normal_width != null && !"".equals(normal_width))
                content_img_width = Integer.parseInt(normal_width);

            String thumb_width = con_map.get("thumb_width");//缩略图的图片宽度
            if (thumb_width != null && !"".equals(thumb_width))
                prev_img_width = Integer.parseInt(thumb_width);

            String is_compress = "1";//是否压缩图片
            if (con_map.containsKey("is_compress"))
                is_compress = con_map.get("is_compress");

            // 如果图是高清图片，进行缩小
            if (w > content_img_width && "1".equals(is_compress)) {
                hd_url = name + "_b" + extName;
                saveFile = new File(savePath + hd_url);
                ImageIO.write(bis, extName.substring(1), saveFile);

                if (extName.equals(".gif"))
                    ImageUtils.CreatetHumbnailByGif(saveFile, file, ImageUtils.getImgWidth());
                else
                    ImageUtils.CreatetHumbnail(saveFile, file, ImageUtils.getImgWidth());
            } else {
                saveFile = file;
                //ImageIO.write(bis, extName.substring(1), saveFile);
                try {
                    URL url = new URL(fileUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    DataInputStream in = new DataInputStream(connection.getInputStream());
                    DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath + name + extName));
                    byte[] buffer = new byte[4096];
                    int count = 0;
                    while ((count = in.read(buffer)) > 0) {
                        out.write(buffer, 0, count);
                    }
                    out.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            file_size = FileOperation.getFileSize(file);
            //如果图片宽度大于配置的宽度，生成缩略图
            if (w > prev_img_width) {
                thum_url = name + "_s" + extName;
                File humbImg = new File(savePath + thum_url);
                if (extName.equals(".gif"))
                    ImageUtils.CreatetHumbnailByGif(saveFile, humbImg, prev_img_width);
                else
                    ImageUtils.CreatetHumbnail(saveFile, humbImg, ImageUtils.getImgPreWidth());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addMateInfo(String site_id, String att_path, int user_id) {
        MateInfoBean bean = new MateInfoBean();
        bean.setAtt_id(Integer.parseInt(MateInfoManager.getArrIdFromTable()));
        bean.setF_id(0);
        bean.setSite_id(site_id);
        bean.setUser_id(user_id);
        bean.setAtt_path(att_path);
        bean.setAtt_ename(att_ename);
        bean.setAtt_cname(att_cname);
        bean.setThumb_url(thum_url);
        bean.setHd_url(hd_url);
        bean.setAlias_name(att_cname);
        bean.setFilesize(file_size);
        SettingLogsBean stl = new SettingLogsBean();
        stl.setUser_id(user_id);
        return MateInfoManager.insertMateInfo(bean, stl);
    }
}
