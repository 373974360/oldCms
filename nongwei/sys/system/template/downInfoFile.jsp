<%@ page contentType="text/html; charset=utf-8"%>
 
<%
    
     String site_id=request.getParameter("site_id")!=null?request.getParameter("site_id").trim():"";  
     String tid = request.getParameter("tid")!=null?request.getParameter("tid").trim():"";  
     String filePath="";
     if(tid ==""||site_id ==""){
	   return;
	 }
     com.deya.wcm.bean.control.SiteBean siteBean = com.deya.wcm.services.control.site.SiteManager.getSiteBeanBySiteID(site_id);
	 String uploadpath = siteBean.getSite_path();
	 filePath =uploadpath+"/WEB-INF/template/"+tid+"_vm.vm";//服务器文件路径  全路径
	 String fileName = tid+"_vm.html"; // 文件名，输出到用户的下载对话框
   
    // 从文件完整路径中提取文件名，并进行编码转换，防止不能正确显示中文名
	try {
		if (fileName.lastIndexOf("/") > 0) {
			fileName = new String(fileName.substring(
					fileName.lastIndexOf("/") + 1, fileName.length())
					.getBytes("GB2312"), "ISO8859_1");
		} else if (fileName.lastIndexOf("\\") > 0) { 
			fileName = new String(fileName.substring(
					fileName.lastIndexOf("\\") + 1, fileName.length())
					.getBytes("GB2312"), "ISO8859_1");
		}

	} catch (Exception e) {
	}

	java.io.BufferedInputStream bis = null;
	java.io.BufferedOutputStream bos = null;
	try {
		bis = new java.io.BufferedInputStream(
				new java.io.FileInputStream(filePath));
	} catch (Exception e) {
		e.printStackTrace();
		return;
	}
	response.reset();
	// 设置响应头和保存文件名
	response.setContentType("APPLICATION/OCTET-STREAM");
	response.setHeader("Content-Disposition", "attachment; filename=\""
			+ fileName + "\"");

	try {
		bos = new java.io.BufferedOutputStream(response
				.getOutputStream());

		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}

		//    out.clear();
		//    out=pageContext.pushBody();

		System.out.println("文件下载完毕.");
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("下载文件失败!");
	} finally {
		if (bis != null) {
			try {
				bis.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bis = null;
		}
		if (bos != null) {
			try {
				bos.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bos = null;
		}
	}
	

	out.clear();
	out=pageContext.pushBody();
%>