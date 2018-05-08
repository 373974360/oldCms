package com.deya.util;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import com.deya.util.jconfig.JconfigFactory;
import com.deya.util.jconfig.JconfigUtil;
@SuppressWarnings("deprecation")
public class UploadFile{
//	jConfig对象
	private static JconfigUtil bcConfig = JconfigFactory.getJconfigUtilInstance("bashConfig");
	//得到附件的访问路径
	private static String web_access_path = bcConfig.getProperty("web_access_path", "", "upload_file_path");
		
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {		
		Hashtable<String, String> h = uploadFiles(request);
		if(h == null)
		{
			response.getWriter().println("{\"err\":\"\",\"上传失败，请选择正确的文件类型\":\"\"}");			
		}
		else
			response.getWriter().println("{\"err\":\"\",\"msg\":\"" + h.get("filedata") + "\"}");
	}
	
	/**
     * 上传附件
     * @param HttpServletRequest request
     * @param String uploadType 上传文件类型参数，值必须是 img,file,media,flash 这4种类型,不能为空　
     * @return 信息列表
     */
	@SuppressWarnings({ "unchecked" })
	public static Hashtable<String, String> uploadFiles(HttpServletRequest request){
		try{		
			DiskFileUpload upload = new DiskFileUpload();
			upload.setHeaderEncoding("utf-8");
			java.util.List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			Hashtable<String,String> fields = new Hashtable<String,String>();
			int num=0;
			while (iter.hasNext()) 
			{
				FileItem item = (FileItem) iter.next();                
				String name = item.getFieldName();
				
				if (!item.isFormField())
				{	
					num++; //添加序号，区分多个文件上传时文件名重复
					FileItem uplFile = (FileItem)item;
					String fileNameLong = uplFile.getName();
					if (fileNameLong != null && !"".equals(fileNameLong)) 
					{
						String url = uploadFile(uplFile, fileNameLong,num);
						if(url == null)
							return null;						
						fields.put(name, url);						
					}
				}
				else
				{					
					fields.put(name, item.getString());
				}				
			}			
			return fields;
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static String uploadFile(FileItem uplFile, String fileNameLong,
			int newFileName) {
		String fileName = "";
		String uploadType = "";
		try {			
			fileNameLong = fileNameLong.replace('\\', '/');
			String[] pathParts = fileNameLong.split("/");
			fileName = pathParts[pathParts.length - 1];	
			String ext = getExtension(fileName).toLowerCase();
			
			//做判断,根据后缀名判断文件类型     
			uploadType = getUploadType(ext);
			if("".equals(uploadType))
				return "error file type";//错误的文件类型，不允许上传的文件类型
		
			String currentDirPath = bcConfig.getProperty(uploadType+"_path", "", "upload_file_path");
			
			File currentDir = new File(currentDirPath);
			if (!currentDir.exists()) {
				currentDir.mkdirs();
			}				

			fileName = "_" + getCurrentDateTime() + newFileName + "." +ext;
			File pathToSave = new File(currentDirPath, fileName);
			while (pathToSave.exists()) {
				pathToSave = new File(currentDirPath, fileName);
			}
			uplFile.write(pathToSave);
			return web_access_path+uploadType+"/"+fileName;
		} catch (Throwable ex) {
			ex.printStackTrace(System.out);
			return "upload fail";//上传失败
		}

	}

	public static String getUploadType(String ext)
	{			
		String exts = bcConfig.getProperty("img_type", "", "upload_file_path");
		if(exts.contains(ext))
		{
			return "img";
		}
		exts = bcConfig.getProperty("flash_type", "", "upload_file_path");		
		if(exts.contains(ext))
		{
			return "flash";
		}
		exts = bcConfig.getProperty("media_type", "", "upload_file_path");
		if(exts.contains(ext))
		{
			return "media";
		}
		exts = bcConfig.getProperty("file_type", "", "upload_file_path");		
		if(exts.contains(ext))
		{	
			return "file";
		}		
		return "";
	}
	
	public static String getCurrentDateTime() {
		Calendar cal = Calendar.getInstance();
		java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmss");
		return sdf.format(cal.getTime());
	}

	public static String getExtension(String fileName) {
		return fileName.replaceAll(".*\\.(.*?)","$1");
	}

}
