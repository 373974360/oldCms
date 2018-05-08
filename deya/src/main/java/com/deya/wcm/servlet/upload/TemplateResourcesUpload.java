package com.deya.wcm.servlet.upload;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.deya.util.FormatUtil;
import com.deya.util.UploadManager;
import com.deya.util.ZipManager;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.org.user.UserLogin;

@SuppressWarnings("serial")
public class TemplateResourcesUpload extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		//System.out.println(request.getSession().getId());
		response.getWriter().println("OK"+UserLogin.checkLoginBySession(request));		
	}
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		
		String sid = request.getParameter("sid");
		if(sid == null || "".equals(sid) || !UploadManager.checkUploadSecretKey(sid))
		{	
			System.out.println("Upload validation errors "+sid);
			return;
		}
		String custom_folder = request.getParameter("custom_folder");//指定的保存目录
		//System.out.println("custom_folder------------------"+custom_folder);
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			return;
		}
		
		String site_id = request.getParameter("site_id");
		String browser_path = "";
		String savePath = "";
		if("shared_res".equals(site_id))
			savePath = JconfigUtilContainer.bashConfig().getProperty("path", "", "root_path")+"/cms.files";//这个是共享资源的东东，放在cws.files目录下
		else
			savePath = SiteManager.getSiteBeanBySiteID(site_id).getSite_path();
			
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
					extName = name.substring(name.lastIndexOf("."))
							.toLowerCase();
					if(UploadFileIfy.NOTUPLOAT_FILE_EXT.contains(","+extName.substring(1)+","))
						return;
				}				
				try {					
					if (extName.equals(".gif") || extName.equals(".jpg")
							|| extName.equals(".jpeg")
							|| extName.equals(".png") || extName.equals(".swf")) {
						savePath = FormatUtil.formatPath(savePath+"/images");
						browser_path = "/images";
					}
					if (extName.equals(".js")) {
						savePath = FormatUtil.formatPath(savePath+"/js");
						browser_path = "/js";
					}
					if (extName.equals(".css")) {
						savePath = FormatUtil.formatPath(savePath+"/styles");
						browser_path = "/styles";
					}
					//有指定目录
					if(custom_folder != null && !"".equals(custom_folder))
					{
						savePath = custom_folder;
					}
					//上传的zip文件
					if (extName.equals(".zip")) {
						savePath = SiteManager.getSiteBeanBySiteID(site_id).getSite_path();						
					}
					
					File f = new File(savePath);
					if(!f.exists())
					{
						f.mkdirs();
					}
					//System.out.println(savePath);
					File saveFile = new File(savePath +File.separator+ name);
					item.write(saveFile);
					
					//对zip文件进行解压
					if (extName.equals(".zip")) {
						if(ZipManager.decompress(savePath +File.separator+ name,savePath))
						{
							saveFile.delete();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else
				System.out.println("-------->    "+item.getFieldName()+ "  " +item.getString());
		}
		String outStr = "{\"att_path\":\"" + browser_path+"/"+name + "\"}";
		response.getWriter().print(outStr);
	}
}
