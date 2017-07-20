package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
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
import com.deya.wcm.servlet.upload.UploadFileIfy;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class BinaryUploader {

    public static String[] NOTUPLOAT_FILE_EXT = {"php ","php3","php5","phtml","asp ","aspx","ascx","jsp","cfm","cfc",
            "pl","bat","exe","dll","reg","cgi","js","html","sh","action","vm"};

    public static final State save(HttpServletRequest request,
                                   Map<String,Object> conf) {
        State ueditorUpload = null;
        try {
            String site_id = request.getParameter("site_id");
            /*
            String sid = FormatUtil.formatNullString(request.getParameter("sid"));
            if ((sid == null) || ("".equals(sid)) || (!UploadManager.checkUploadSecretKey(sid))) {
                System.out.println("Upload validation errors");
                return null;
            }
            */

            String savePath = UploadManager.getUploadFilePath(site_id) + "/";

            DiskFileItemFactory fac = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(fac);
            upload.setHeaderEncoding("utf-8");
            List fileList = null;
            try {
                fileList = upload.parseRequest(request);
            } catch (FileUploadException ex) {
                return null;
            }

            String is_press = request.getParameter("is_press");
            String press_osition = "9";
            Iterator it = fileList.iterator();
            long totalSpace = 0;
            String name = "";
            String pic_name = "";
            String extName = "";
            FileItem item = null;
            String dateTime = DateUtil.getCurrentDateTime("yyyyMMdd") + genRandomNum(10);
            while (it.hasNext()) {
                item = (FileItem) it.next();
                if (!item.isFormField()) {
                    break;
                }
                item = null;
            }
            if (item == null) {
                return new BaseState(false,AppInfo.NOTFOUND_UPLOAD_DATA);
            } else {
                name = item.getName();
                totalSpace = item.getSize();
                pic_name = name;
                if ((name != null) && (!name.trim().equals(""))) {
                    if (name.lastIndexOf(".") >= 0) {
                        extName = name.substring(name.lastIndexOf(".")).toLowerCase();
                        String str[] = NOTUPLOAT_FILE_EXT;
                        boolean result = false;
                        for(int i=0;i<str.length;i++){
                            String s = str[i];
                            if(s!=null && !"".equals(s)){
                                s = s.toString();
                                result = extName.toLowerCase().contains(s);
                                if(result){
                                    break;
                                }
                            }
                        }
                        if (result){
                            return null;
                        }
                    }
                    File file = null;
                    name = dateTime;
                    file = new File(savePath + name + extName);
                    try {
                        if ((extName.equals(".gif")) || (extName.equals(".jpg")) ||
                                (extName.equals(".jpeg")) ||
                                (extName.equals(".png"))) {
                            processImage(file,item,name,extName,savePath,is_press,press_osition,site_id);
                        } else {
                            File saveFile = new File(savePath + name + extName);
                            item.write(saveFile);
                            do {
                                System.out.println("正在写入非图片文件！");
                            } while (!saveFile.exists());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            savePath = UploadManager.getUploadFileUrl(site_id);
            if (ServerManager.isWindows()) {
                savePath = savePath.replaceAll("\\\\","\\/");
                if (savePath.startsWith("//"))
                    savePath = savePath.substring(1);
            }

            String arr_id = MateInfoRPC.getArrIdFromTable();
            SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
            MateInfoBean mb = new MateInfoBean();
            mb.setAtt_id(Integer.parseInt(arr_id));
            mb.setF_id(0);
            mb.setSite_id(site_id);
            mb.setUser_id(stl.getUser_id());
            mb.setAtt_path(UploadManager.getUploadFileSitePath(site_id));
            mb.setAtt_cname(pic_name);
            mb.setHd_url(name + "_b" + extName);
            mb.setThumb_url(name + "_s" + extName);
            mb.setAtt_ename(name + extName);
            mb.setAlias_name(pic_name);
            mb.setFilesize(totalSpace);
            MateInfoManager.insertMateInfo(mb,stl);

            ueditorUpload = new BaseState(true);
            ueditorUpload.putInfo("title",pic_name);
            ueditorUpload.putInfo("url",savePath + name + extName);
            ueditorUpload.putInfo("type",extName);
            ueditorUpload.putInfo("original",pic_name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ueditorUpload;
		/*
		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false,AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }

		try {
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}

			if (fileStream == null) {
				return new BaseState(false,AppInfo.NOTFOUND_UPLOAD_DATA);
			}

			String savePath = (String) conf.get("savePath");
			String originFileName = fileStream.getName();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());
			savePath = savePath + suffix;

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix,(String[]) conf.get("allowFiles"))) {
				return new BaseState(false,AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath,originFileName);

			String physicalPath = (String) conf.get("rootPath") + savePath;

			InputStream is = fileStream.openStream();
			State storageState = StorageManager.saveFileByInputStream(is,
					physicalPath,maxSize);
			is.close();

			if (storageState.isSuccess()) {
				storageState.putInfo("url",PathFormat.format(savePath));
				storageState.putInfo("type",suffix);
				storageState.putInfo("original",originFileName + suffix);
			}

			return storageState;
		} catch (FileUploadException e) {
			return new BaseState(false,AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
		}
		return new BaseState(false,AppInfo.IO_ERROR);
		*/
    }

    public static void processImage(File file,FileItem item,String name,String extName,String savePath,String is_press,String press_osition,String site_id) throws IOException {
        try {
            String pressImg = "";
            int press_img_width = 300;
            int press_img_height = 150;
            int alpha = 80;
            int content_img_width = ImageUtils.getImgWidth();
            int prev_img_width = ImageUtils.getImgPreWidth();

            Map<String,String> m = new HashMap<String,String>();
            m.put("group","attachment");
            m.put("site_id",site_id);
            m.put("app_id","cms");
            Map<String,String> con_map = ConfigManager.getConfigMap(m);

            String watermark = (String) con_map.get("watermark");
            String press_osition_config = (String) con_map.get("water_location");
            if ("0".equals(press_osition))
                press_osition = press_osition_config;
            pressImg = (String) con_map.get("water_pic");
            if ((pressImg != null) && (!"".equals(pressImg))) {
                String img_domain = JconfigUtilContainer.bashConfig().getProperty("img_domain","","resource_server");
                if (img_domain != null && !"".equals(img_domain)) {
                    pressImg = JconfigUtilContainer.bashConfig().getProperty("save_path","","resource_server") + pressImg;
                } else {
                    String public_path = JconfigUtilContainer.bashConfig().getProperty("public_path","","resource_server");
                    String root_path = JconfigUtilContainer.bashConfig().getProperty("path","","root_path");
                    if (public_path != null && !"".equals(public_path)) {
                        pressImg = root_path + pressImg;
                    } else {
                        String allString = UploadManager.getUploadFilePath(site_id) + "/";
                        String replaString = UploadManager.getUploadFileSitePath(site_id);
                        pressImg = allString.replace(replaString,"") + pressImg;
                    }
                }
            }
            String water_width = (String) con_map.get("water_width");
            if ((water_width != null) && (!"".equals(water_width)))
                press_img_width = Integer.parseInt(water_width);
            String water_height = (String) con_map.get("water_height");
            if ((water_height != null) && (!"".equals(water_height)))
                press_img_height = Integer.parseInt(water_height);
            String water_transparent = (String) con_map.get("water_transparent");
            alpha = (int) (Float.parseFloat(water_transparent) * 100);
            String normal_width = (String) con_map.get("normal_width");
            if ((normal_width != null) && (!"".equals(normal_width))) {
                content_img_width = Integer.parseInt(normal_width);
            }
            String thumb_width = (String) con_map.get("thumb_width");
            if ((thumb_width != null) && (!"".equals(thumb_width))) {
                prev_img_width = Integer.parseInt(thumb_width);
            }
            String is_compress = "1";
            if (con_map.containsKey("is_compress")) {
                is_compress = (String) con_map.get("is_compress");
            }
            BufferedImage bis = null;
            try {
                bis = ImageIO.read(item.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(item.getInputStream());
                bis = decoder.decodeAsBufferedImage();
            }
            //BufferedImage bis = ImageIO.read(item.getInputStream());
            int w = bis.getWidth();
            int h = bis.getHeight();
            if (w > content_img_width) {
                File saveFile = new File(savePath + name + "_b" + extName);
                String hd_url = name + "_b" + extName;
                item.write(saveFile);
                do {
                    System.out.println("正在写入文件！");
                } while (!saveFile.exists());
                ImageUtils.cutImage(content_img_width,savePath + hd_url,savePath + name + extName);
                if ("1".equals(is_compress)) {
                    if (("1".equals(is_press)) && ("1".equals(watermark))) {
                        if ((pressImg != null) && (!"".equals(pressImg))) {
                            ImageUtils.addImgText(pressImg,savePath + name + extName,Integer.parseInt(press_osition),extName,alpha);
                        }
                    }
                }
                if (w > prev_img_width) {
                    String thum_url = name + "_s" + extName;
                    ImageUtils.cutImage(prev_img_width,savePath + hd_url,savePath + thum_url);
                }
            } else {
                File saveFile = file;
                item.write(saveFile);
                do {
                    System.out.println("正在写入文件！");
                } while (!saveFile.exists());
                if (("1".equals(is_press)) && ("1".equals(watermark)) && (w > press_img_width) && (h > press_img_height)) {
                    ImageUtils.addImgText(pressImg,savePath + name + extName,Integer.parseInt(press_osition),extName,alpha);
                }
                if (w > prev_img_width) {
                    String thum_url = name + "_s" + extName;
                    ImageUtils.cutImage(prev_img_width,savePath + name + extName,savePath + thum_url);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static boolean validType(String type,String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);

        return list.contains(type);
    }

    /**
     * 生成随机密码
     *
     * @param pwd_len
     * 生成的密码的总长度
     * @return 密码的字符串
     */
    public static String genRandomNum(int pwd_len) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 36;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = { 'a','b','c','d','e','f','g','h','i','j','k',
                'l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9' };
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < pwd_len) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
// TODO Auto-generated method stub
        //System.out.println(genRandomNum(20));//生成14位随机密码
    }
}
