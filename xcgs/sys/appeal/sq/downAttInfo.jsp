<%@ page contentType="text/html; charset=utf-8"%>

<jsp:directive.page import="java.io.*,java.util.*,com.deya.wcm.bean.appeal.sq.*,com.deya.wcm.services.appeal.sq.*"/>

<%	

	String sq_id = request.getParameter("sq_id");

	String atype = request.getParameter("atype");

	String att_path = "";

	String att_name = "";



	List<SQAttachment> l = SQManager.getSQAttachmentList(sq_id,atype);

	if(l != null && l.size() > 0)

	{

		att_path = l.get(0).getAtt_path();

		att_name = l.get(0).getAtt_name();

	}	

	response.reset();

	response.setHeader("Location",att_name);	

	//response.setContentType("application/octet-stream; charset=utf-8");  

	//response.setHeader("Content-Disposition","attachment; filename=\""+new String(att_name.getBytes("utf-8"),"iso8859-1") +"\"");

   

	

	java.io.BufferedInputStream bis = null;   

	  java.io.BufferedOutputStream bos = null;   

	 

	  try {       
		  att_name = att_name.replaceAll(" ","");
		  if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0){
			  att_name = java.net.URLEncoder.encode(att_name, "UTF-8");
          }
		  

	    bis = new java.io.BufferedInputStream(new java.io.FileInputStream(att_path));   

	    response.setContentType("application/octet-stream");   

	    response.setHeader("Content-disposition",   

	        "attachment; filename="+ new String(att_name.getBytes("utf-8"),"iso8859-1"));   

	    bos = new java.io.BufferedOutputStream(response.getOutputStream());   

	    byte[] buff = new byte[2048];   

	    int bytesRead;   

	    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {   

	      bos.write(buff, 0, bytesRead);   

	    }   

	    out.clear();   

	    out = pageContext.pushBody();   

	  } catch (Exception e) {   

	    //response.setContentType("text/html; charset=utf-8");   

	    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />对不起，找不到该文件："+att_name);   

	    //e.printStackTrace();   

	  } finally {   

	    if (bis != null){   

	      bis.close();   

	      bis = null;   

	    }   

	    if (bos != null){   

	      bos.close();   

	      bos = null;   

	    }   

	  }   

		 

/*

   

	 //新建文件输入输出流

	OutputStream output = null;

	FileInputStream fis = null;

	try{		

		File f = new File(att_path);

		//新建文件输入输出流对象

		output = response.getOutputStream();

		fis = new FileInputStream(f);

		//设置每次写入缓存大小

		byte[] b = new byte[(int)f.length()];

		//out.print(f.length());

		//把输出流写入客户端

		int i = 0;

		while((i = fis.read(b)) > 0){

			output.write(b, 0, i);

		}

		output.flush();

	}

	catch(Exception e){

		e.printStackTrace();

	}

	finally{

		if(fis != null){

			fis.close();

			fis = null;

		}

		if(output != null){

			output.close();

			output = null;

		}

	}*/



  //out.println("<script>window.close()</script>");

%>