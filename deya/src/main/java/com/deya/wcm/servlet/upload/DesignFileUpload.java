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
import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.UploadManager;
import com.deya.util.jconfig.JconfigUtilContainer;

@SuppressWarnings("serial")
public class DesignFileUpload extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		//System.out.println(request.getSession().getId());
		response.getWriter().println("OK");		
	}
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		String sid = request.getParameter("sid");		
		if(sid == null || "".equals(sid) || !UploadManager.checkUploadSecretKey(sid))
		{	
			System.out.println("Upload validation errors");
			return;
		}
		String up_type = request.getParameter("up_type");
		String css_ename = request.getParameter("css_ename");
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			return;
		}
		
		String browser_path = "";
		String savePath = "";
		if("thumb".equals(up_type))
		{
			browser_path = "/cms.files/design/thumb/";
			savePath = FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "cms_files") + "/design/thumb/");//这个是共享资源的东东，放在cws.files目录下
			
		}else
		{			
			savePath = FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "cms_files") + "/design/theme/"+css_ename+"/");//这个是共享资源的东东，放在cws.files目录下
		}
		//System.out.println("DesignFileUpload--------------"+savePath);
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
					if("thumb".equals(up_type))
					{
						name = DateUtil.getCurrentDateTime("yyyyMMddhhmmsss")+extName;
					}
					
					File f = new File(savePath);
					if(!f.exists())
					{
						f.mkdirs();
					}
					//System.out.println(savePath);
					File saveFile = new File(savePath +"/"+ name);
					item.write(saveFile);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else
				System.out.println("-------->    "+item.getFieldName()+ "  " +item.getString());
		}
		String outStr = "{\"att_path\":\"" + browser_path+name + "\"}";
		//System.out.println("DesignFileUpload--------------"+savePath);
		response.getWriter().print(outStr);
	}
}
