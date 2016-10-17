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

/**
 * 特殊上传，用于素材库之外的上传，如诉求系统上传的文件,图片不生成缩略图,有特定的目录.
 * <p>
 * Title: CicroDate
 * </p>
 * <p>
 * Description: 
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: Cicro
 * </p>
 * 
 * @author yuduochao
 * @version 1.0 *
 */

@SuppressWarnings("serial")
public class PeculiarUploadFile extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doGet--------------------start");
		response.getWriter().println("OK");
	}

	@SuppressWarnings({ "unchecked" })
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {			
	try{
		String savePath = JconfigUtilContainer.bashConfig().getProperty("path", "", "hostRoot_path");
		System.out.println("savePath--------------------" + savePath);
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			return;
		}
		
		String app_id = request.getParameter("app_id");
		String sid = request.getParameter("sid");		
		if(sid != null && !"".equals(sid) && !UploadManager.checkUploadSecretKey(sid))
		{	
			System.out.println("Upload validation errors");
			return;
		}
		savePath = FormatUtil.formatPath(savePath+"/"+app_id+"/upload/"+DateUtil.getCurrentDateTime("yyyyMM"));
		File ps = new File(savePath);
		if(!ps.exists())
			ps.mkdirs();
		
		Iterator<FileItem> it = fileList.iterator();
		String name = "";
		String extName = "";
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				name = item.getName();
				//long size = item.getSize();
				//String type = item.getContentType();				
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
				File file = null;
				do {
					// 生成文件名：
					name = DateUtil.getCurrentDateTime("yyyyMMddhhmmsss");
					name = savePath +"/"+ name + extName;
					file = new File(name);
				} while (file.exists());
				try {
					//诉求系统不需要判断图片类型，全部直接上传
					if("appeal".equals(app_id))
					{
						File saveFile = new File(name);
						item.write(saveFile);
					}
					else
					{
						if (extName.equals(".gif") || extName.equals(".jpg")
								|| extName.equals(".jpeg")
								|| extName.equals(".png")) {
							// processImage(file, item, name, extName,
							// savePath,is_press,press_osition);
						} else {
							File saveFile = new File(name);
							item.write(saveFile);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} //else
				//System.out.println("-------->    " + item.getFieldName() + "  "+ item.getString());
		}
		response.getWriter().print(name);
	}	catch (Exception e) {
		// TODO: handle exception
	}
		
	}
}
