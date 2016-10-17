package com.deya.wcm.servlet.upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.UploadManager;
import com.deya.util.img.ImageUtils;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.server.ServerManager;
import com.deya.wcm.services.material.MateInfoRPC;
import com.deya.wcm.services.system.config.ConfigManager;

@SuppressWarnings("serial")
public class UploadFileIfy_20150601 extends HttpServlet {
	
	public static String hd_url = "";
	public static String thum_url = "";
	public static String NOTUPLOAT_FILE_EXT = ",php,php3,php5,phtml,asp,aspx,ascx,jsp,cfm,cfc,pl,bat,exe,dll,reg,cgi,";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String delete_file = request.getParameter("delete_file");
		String sid = FormatUtil.formatNullString(request.getParameter("sid"));	
		
		if(sid == null || "".equals(sid) || !UploadManager.checkUploadSecretKey(sid))
		{	
			System.out.println("Upload validation errors");
			return;
		}
		
		if(delete_file != null && !"".equals(delete_file))
		{
			String savePath = UploadManager.getUploadFilePath2();
			String[] tempA = delete_file.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				String file_path = FormatUtil.formatPath(savePath+tempA[i]);
				File f = new File(file_path);
				if(f.exists())
					f.delete();
			}
		}
	}
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String site_id = request.getParameter("site_id");
		String sid = FormatUtil.formatNullString(request.getParameter("sid"));	
		System.out.println("sid------------------"+sid);
		if(sid==null){
			sid = "";
		}
		if(!UploadManager.checkUploadSecretKey(sid))
		{	
			System.out.println("Upload validation errors");
			return;
		}
		
		thum_url = "";
		
		//System.out.println("site_id	********************	"+site_id);
		String savePath = UploadManager.getUploadFilePath(site_id)+"/";
		//System.out.println("savePath	********************	"+savePath);
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			return;
		}
		//是否生成水印，0为不生成，1为生成
		String is_press = request.getParameter("is_press");
		String press_osition = request.getParameter("press_osition");//水印位置
				
		Iterator<FileItem> it = fileList.iterator();
		String name = "";
		String extName = "";
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				name = item.getName();
				
				if (name == null || name.trim().equals("")) {
					continue;
				}
				// 扩展名格式：
				if (name.lastIndexOf(".") >= 0) {
					extName = name.substring(name.lastIndexOf(".")).toLowerCase();
					if(NOTUPLOAT_FILE_EXT.contains(","+extName.substring(1)+","))
						return;
				}
				File file = null;
				do {
					// 生成文件名：
					name = DateUtil.getCurrentDateTime("yyyyMMddhhmmsss");
					//System.out.println("name------------------------"+name);
					file = new File(savePath + name + extName);
				} while (file.exists());
				try {	
					
					if (extName.equals(".gif") || extName.equals(".jpg")
							|| extName.equals(".jpeg")
							|| extName.equals(".png")) {
						processImage(file,item,name,extName,savePath,is_press,press_osition,site_id);
					} else {					
						File saveFile = new File(savePath+name+extName);
						item.write(saveFile);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else
				System.out.println("-------->"+item.getFieldName());
		}
		
		String tmpUploadFilePath = UploadManager.getUploadFilePath2();
		
		savePath = savePath.substring(tmpUploadFilePath.length());
		if(thum_url == null || "".equals(thum_url))
			thum_url = name + extName;//如果不需要生成缩略图，付为本身的名称		
		
		if(ServerManager.isWindows())
		{
			savePath = savePath.replaceAll("\\\\", "\\/");
			if(savePath.startsWith("//"))
				savePath = savePath.substring(1);
		}
		String outStr = "{\"att_path\":\"" + savePath + "\",\"att_ename\":\"" + name + extName + "\",\"hd_url\":\"" + hd_url + "\",\"thum_url\":\"" + thum_url + "\"}";
		
		response.getWriter().print(outStr);
	}

	public static void processImage(File file, FileItem item, String name,
			String extName, String savePath,String is_press,String press_osition,String site_id) throws IOException {
		try {
			//水印图片
			String pressImg = "";
			int press_img_width = 300;
			int press_img_height = 150;
			float alpha = 0.8f;
			int content_img_width = ImageUtils.getImgWidth();//文章正文的图片宽度
			int prev_img_width = ImageUtils.getImgPreWidth();//缩略图的图片宽度
			
			Map<String,String> m = new HashMap<String,String>();//取站点素材配置信息			
			m.put("group", "attachment");
			m.put("site_id", site_id);
			m.put("app_id", "cms");
			Map<String,String> con_map = ConfigManager.getConfigMap(m);
			
			String watermark = con_map.get("watermark");//是否生成水印
			String press_osition_config = con_map.get("water_location");//水印位置
			if("0".equals(press_osition))//如何传过来的水印位置为0,取站点配置中的位置
				press_osition = press_osition_config;
			pressImg = con_map.get("water_pic");//水印图片
			if(pressImg != null && !"".equals(pressImg))
			{
				pressImg = JconfigUtilContainer.bashConfig().getProperty("save_path", "", "resource_server")+pressImg;				
			}
			String water_width = con_map.get("water_width");//水印图片生成条件　宽
			if(water_width != null && !"".equals(water_width))
				press_img_width = Integer.parseInt(water_width);
			String water_height = con_map.get("water_height");//水印图片生成条件　高
			if(water_height != null && !"".equals(water_height))
				press_img_height = Integer.parseInt(water_height);	
			String water_transparent = con_map.get("water_transparent");//水印透明度
			alpha = Float.parseFloat(water_transparent);
	System.out.println("alpha----------------"+alpha);		
			String normal_width = con_map.get("normal_width");//文章正文的图片宽度
			if(normal_width != null && !"".equals(normal_width))
				content_img_width = Integer.parseInt(normal_width);	
			
			String thumb_width = con_map.get("thumb_width");//缩略图的图片宽度
			if(thumb_width != null && !"".equals(thumb_width))
				prev_img_width = Integer.parseInt(thumb_width);	
			
			String is_compress = "1";//是否压缩图片
			if(con_map.containsKey("is_compress"))
				is_compress = con_map.get("is_compress");
						
			File saveFile;
			BufferedImage bis = ImageIO.read(item.getInputStream());
			int w = bis.getWidth();
			int h = bis.getHeight();
			
			// 如果图是高清图片，进行缩小
			if (w > content_img_width && "1".equals(is_compress)) {				
					saveFile = new File(savePath + name + "_b" + extName);
					hd_url = name + "_b" + extName;
					item.write(saveFile);								
					if (extName.equals(".gif"))
						ImageUtils.CreatetHumbnailByGif(saveFile, file,content_img_width);
					else
						ImageUtils.CreatetHumbnail(saveFile, file,content_img_width);
					
					if("1".equals(is_press) && "1".equals(watermark))
					{	
						ImageUtils.pressImage(pressImg,file,Integer.parseInt(press_osition),extName,alpha);
					}								
			} else {
				saveFile = file;
				item.write(saveFile);				
			}
			if("1".equals(is_press) && "1".equals(watermark) && w > press_img_width && h > press_img_height)
			{
				ImageUtils.pressImage(pressImg,saveFile,Integer.parseInt(press_osition),extName,alpha);
			}
			// 如果图片宽度大于配置的宽度，生成缩略图
			if(w > prev_img_width && !".png".equals(extName.toLowerCase()))
			{	
				thum_url = name + "_s" + extName;
				File humbImg = new File(savePath + name + "_s" + extName);
				if (extName.equals(".gif"))
					ImageUtils.CreatetHumbnailByGif(saveFile, humbImg,prev_img_width);
				else
					ImageUtils.CreatetHumbnail(saveFile, humbImg, prev_img_width);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}