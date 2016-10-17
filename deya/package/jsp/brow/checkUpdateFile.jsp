<%@page contentType="text/html" pageEncoding="UTF-8"%><%
    String lastVer = "1.0.0";
	String validateValue = request.getParameter("fieldValue"); 
	//System.out.println("validateValue----------" + validateValue);
	String ver = (String)request.getParameter("ver");
    if(ver==null){
    	ver = "";
    }
	//System.out.println("ver1----------" + ver);
	String platform = (String)request.getParameter("platform");
    if(platform==null){
    	platform = "";
    }
	//System.out.println("platform1----------" + platform);
	String result = "";
	 if(Integer.parseInt(ver.replaceAll("\\.",""))<Integer.parseInt(lastVer.replaceAll("\\.",""))){
		    result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	        result += "<results>";
            result += "<updateFileName>政府门户</updateFileName>";
                  
		    if(platform.trim().equals("0")){
				 result += "<updateFileUrl>http://www.cicro.com/MobleSite.ios</updateFileUrl>";
		    }else{
				 result += "<updateFileUrl>http://61.185.20.77/app/android_xy_1.1.0.apk</updateFileUrl>";
		    }
		    //result += "<updateFileUrl>http://www.oschina.net/uploads/osc.apk</updateFileUrl>";
		    result += "<fileSize>9745</fileSize>";
		    //result += "<result>1<result>";
		    result += "<version>"+lastVer+"</version>";
		    result += "</results>";
		}
	     
	out.println(result);
	//	out.println(jsoncallback+"("+listResultStr+")");
	//System.out.println("result---" + result);
%>
