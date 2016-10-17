<%@page contentType="text/html" pageEncoding="UTF-8"%><%
    String lastVer = "2.1";
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
				 result += "<updateFileUrl>http://itunes.apple.com/cn/app/kai-yuan-zhong-%20guo/id524298520?mt=8</updateFileUrl>";
		    }else{
				 result += "<updateFileUrl>http://www.oschina.net/uploads/osc.apk</updateFileUrl>";
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
