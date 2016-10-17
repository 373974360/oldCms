package com.deya.wcm.services.search.search.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.deya.util.DateUtil;
import com.deya.util.Encode;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.util.xml.XmlManager;
import com.deya.wcm.services.control.domain.SiteDomainManager;
import com.deya.wcm.services.search.index.IndexManager;
import com.deya.wcm.services.search.util.tongyinci.InitCiku;

public class SearchUtil {
    
	//时间格式化
	public static String formatTimeYYYYMMDDHHMMSS(String time){
		try{
			if(time.length()>=14){
				String YYYY = time.substring(0,4);
				String MM = time.substring(4,6);
				String DD = time.substring(6,8);
				String HH = time.substring(8,10);
				String M = time.substring(10,12);
				String SS = time.substring(12,14);
				
				return YYYY+"-"+MM+"-"+DD + " "+HH +":"+M+":"+SS;
			}else{
				return formatTimeYYYYMMDD(time);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	//时间格式化
	public static String formatTimeYYYYMMDD(String time){
		try{
			if(time.length()>=8){
				String YYYY = time.substring(0,4);
				String MM = time.substring(4,6);
				String DD = time.substring(6,8);
				
				return YYYY+"-"+MM+"-"+DD ;
			}else{
				return "";
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//把xml字符串转化为map类型
	public static Map initPraToMap(String str){
		try {
	        Map map = new HashMap();
	        Node node = XmlManager.createNode(str);
	        NodeList nodeList = XmlManager.queryNodeList(node, "//param");
	        for(int i=0;i<nodeList.getLength();i++){	
	        	Node node2 = nodeList.item(i);
	        	String key = XmlManager.queryNodeValue(node2, "./key");
	        	String value = XmlManager.queryNodeValue(node2, "./value");
	        	map.put(key, value); 
	        }  
			return map;
		} catch (Exception e) {
			// TODO: handle exception
			return new HashMap();
		}
	}
	
	
	//去掉字符串前面的标点符号
	public static String deleteCodeByContent(String stringInfo){
        StringBuffer sb = new StringBuffer();
        String temp1 = "";
        String temp2 = "";
        if(stringInfo.length()<10){
        	temp1 = stringInfo;
        }else{
        	temp1 = stringInfo.substring(0, 10);
        	temp2 = stringInfo.substring(10, stringInfo.length());
        }
	    Pattern p = Pattern.compile("[.。,，\"“\\?？!！:：'‘《》（(）);；、`~@#￥%……&*$]");// 增加对应的标点   
	    Matcher m = p.matcher(temp1);   
	    String first = m.replaceAll("");
	    sb.append(first);
	    sb.append(temp2);
	    return sb.toString();
	}
	
	
	//组合request传递过来的参数
	public static String getXmlParam(HttpServletRequest request){
		StringBuffer sb = new StringBuffer("");
		try{
	        sb.append("<cicro>");
	        String type = (String)request.getParameter("type");
	        if(type!=null && !type.trim().equals("")){  
	        	sb.append("<param><key>type</key><value><![CDATA["+type+"]]></value></param>");
	        }else{
	        	type = (String)request.getAttribute("type");
	        	if(type!=null && !type.trim().equals("")){  
		        	sb.append("<param><key>type</key><value><![CDATA["+type+"]]></value></param>");
		        }
	        }
	        String typed = (String)request.getParameter("typed");
	        if(typed!=null && !typed.equals("")){  
	        	sb.append("<param><key>typed</key><value><![CDATA["+typed+"]]></value></param>");
	        } 
	           
	        //是否启用同音词功能
	        String ty = (String)request.getParameter("ty");
	        if(ty==null || ("").equals(ty)){  
	        	ty = "off";
	        }
	        
	        //String code = JconfigUtilContainer.bashConfig().getProperty("code", "utf8", "searchCode");
	        //System.out.println("code====" + code);
	        String q = (String)request.getParameter("q");
	        if(q!=null && !q.equals("")){  
	        	/*
	        	if(code.equals("utf8")){
	        		q = Encode.iso_8859_1ToUtf8(q);
	        	}else if(code.equals("gbk")){
	        		q = Encode.iso_8859_1ToGbk(q);
	        	}else if(code.equals("system")){
	        		q = Encode.iso_8859_1ToSystem(q);
	        	}
	        	*/
	        	if(ty.equals("on")){
	        		q = getTongyinciByString(q);
	        	}
	        	q = q.replaceAll("&", "AND");
	        	q = q.replaceAll(" ", "AND");
	        	sb.append("<param><key>q</key><value><![CDATA["+q+"]]></value></param>");
	        } 
	        String q2 = (String)request.getParameter("q2");
	        if(q2!=null && !q2.equals("")){   
	        	/*
	        	if(code.equals("utf8")){
	        		q2 = Encode.iso_8859_1ToUtf8(q2);
	        	}else if(code.equals("gbk")){
	        		q2 = Encode.iso_8859_1ToGbk(q2);
	        	}else if(code.equals("system")){
	        		q2 = Encode.iso_8859_1ToSystem(q2);
	        	}
	        	*/
	        	q2 = q2.replaceAll("&", "AND");
	            q2 = q2.replaceAll(" ", "AND");
	        	sb.append("<param><key>q2</key><value><![CDATA["+q2+"]]></value></param>");
	        }
	        

	        String p = (String)request.getParameter("p");
	        if(p==null || p.equals("")){
	        	p = "1";
	        }
	        sb.append("<param><key>p</key><value><![CDATA["+p+"]]></value></param>");
	        String pz = (String)request.getParameter("pz");
	        if(pz!=null && !pz.equals("")){  
	        	sb.append("<param><key>pz</key><value><![CDATA["+pz+"]]></value></param>");
	        }
	        String length = (String)request.getParameter("length");
	        if(length!=null && !length.equals("")){  
	        	sb.append("<param><key>length</key><value><![CDATA["+length+"]]></value></param>");
	        }
	        String color = (String)request.getParameter("color");
	        if(color!=null && !color.equals("")){  
	        	sb.append("<param><key>color</key><value><![CDATA["+color+"]]></value></param>");
	        }

	        String site_id = (String)request.getParameter("site_id")==null?"":(String)request.getParameter("site_id"); 
	        //System.out.println("site_id===" + site_id);
	        if(site_id.equals("")){
	        	site_id = (String)request.getAttribute("site_id")==null?"":(String)request.getAttribute("site_id");
	        }
	        if(site_id.equals("")){  
	        	//System.out.println("site_domain ===" + request.getLocalName());
	        	//得到站点id 搜索的信息和域名是同一个站点
	        	sb.append("<param><key>site_domain</key><value><![CDATA["+request.getLocalName()+"]]></value></param>");
	        	site_id = SiteDomainManager.getSiteIDByDomain(request.getLocalName());
	        	sb.append("<param><key>site_id</key><value><![CDATA["+site_id+"]]></value></param>");
	        }else{
                /*
	        	if(!site_id.equals("all")){//搜索的信息和域名不是同一个站点
	        		//System.out.println("site_id 222 ===" + site_id);
	        		//site_id = SiteDomainManager.getSiteIDByDomain(request.getLocalName());
		        	sb.append("<param><key>site_id</key><value><![CDATA["+site_id+"]]></value></param>");
		        }
		        */
                sb.append("<param><key>site_id</key><value><![CDATA["+site_id+"]]></value></param>");
	        }
	        //System.out.println("sb===" + sb);
	        
	        //过滤掉的站点id 
	        String ds_id = (String)request.getParameter("ds_id");
	        if(ds_id!=null && !ds_id.trim().equals("")){  
	        	sb.append("<param><key>ds_id</key><value><![CDATA["+ds_id+"]]></value></param>");
	        }else{
	        	ds_id = (String)request.getAttribute("ds_id");
		        if(ds_id!=null && !ds_id.trim().equals("")){  
		        	sb.append("<param><key>ds_id</key><value><![CDATA["+ds_id+"]]></value></param>");
		        }
	        }
	        
	        String categoryId = (String)request.getParameter("categoryId");
	        if(categoryId!=null && !categoryId.equals("")){  
	        	sb.append("<param><key>categoryId</key><value><![CDATA["+categoryId+"]]></value></param>");
	        } 
	        String scope = (String)request.getParameter("scope");
	        if(scope!=null && !scope.equals("")){  
	        	sb.append("<param><key>scope</key><value><![CDATA["+scope+"]]></value></param>");
	        }
	        
	        //高级要用到的参数
	        //搜索时间范围参数  datetype     date：一天内       week：一周内     month： 一月内
	        String ts = (String)request.getParameter("ts");
	        String datetype = (String)request.getParameter("datetype");
	        if(datetype!=null && !datetype.equals("")){
	        	if(datetype.equals("date")){
	        		ts = DateUtil.getDateBefore(DateUtil.getCurrentDate(),1);
	        	}else if(datetype.equals("week")){
	        		ts = DateUtil.getDateBefore(DateUtil.getCurrentDate(),7);
	        	}else if(datetype.equals("month")){
	        		ts = DateUtil.getDateBefore(DateUtil.getCurrentDate(),30);
	        	}
	        } 
	        if(ts!=null && !ts.equals("")){  
	        	sb.append("<param><key>ts</key><value><![CDATA["+Encode.iso_8859_1ToUtf8(ts)+"]]></value></param>");
	        }
	        String te = (String)request.getParameter("te");
	        if(te!=null && !ts.equals("")){  
	        	sb.append("<param><key>te</key><value><![CDATA["+Encode.iso_8859_1ToUtf8(te)+"]]></value></param>");
	        }
	        String st = (String)request.getParameter("st");
	        if(st!=null && !st.equals("")){  
	        	sb.append("<param><key>st</key><value><![CDATA["+Encode.iso_8859_1ToUtf8(st)+"]]></value></param>");
	        }
	        
	        //高级要用到的参数 -- 法律法规文件
	        String wnumber = (String)request.getParameter("wnumber");
	        if(wnumber!=null && !wnumber.equals("")){  
	        	sb.append("<param><key>wnumber</key><value><![CDATA["+Encode.iso_8859_1ToUtf8(wnumber)+"]]></value></param>");
	        }
	        String object_words = (String)request.getParameter("object_words");
	        if(object_words!=null && !object_words.equals("")){  
	        	sb.append("<param><key>object_words</key><value><![CDATA["+Encode.iso_8859_1ToUtf8(object_words)+"]]></value></param>");
	        }
	        String form_type = (String)request.getParameter("form_type");
	        if(form_type!=null && !form_type.equals("")){  
	        	sb.append("<param><key>form_type</key><value><![CDATA["+form_type+"]]></value></param>");
	        }
	        String content_type = (String)request.getParameter("content_type");
	        if(content_type!=null && !content_type.equals("")){  
	        	sb.append("<param><key>content_type</key><value><![CDATA["+content_type+"]]></value></param>");
	        } 
	        String publish_org = (String)request.getParameter("publish_org");
	        if(publish_org!=null && !publish_org.equals("")){  
	        	sb.append("<param><key>publish_org</key><value><![CDATA["+Encode.iso_8859_1ToUtf8(publish_org)+"]]></value></param>");
	        }
	        String is_host = (String)request.getParameter("is_host");
	        if(is_host!=null && !is_host.equals("")){ 
	        		sb.append("<param><key>is_host</key><value><![CDATA["+is_host+"]]></value></param>");
	        }
	        
	        //包含以下全部关键词用(每个关键词之间用空格分隔)
	        String qn1 = nullToString((String)request.getParameter("qn1"));
            //包含以下任意一个关键词用(每个关键词之间用空格分隔)
	        String qn2 = nullToString((String)request.getParameter("qn2"));
	        if(qn1!=null && !qn1.equals("")){  
		        if(ty.equals("on")){
	        		qn2 = qn2 + " " + getTongyinciByString(qn1);
	        	}else{
	        		sb.append("<param><key>qn1</key><value><![CDATA["+qn1+"]]></value></param>");
	        	}
		        
		        //添加用户搜索关键词 --- 上海科委定制-start
		        String site_idtemp = SiteDomainManager.getSiteIDByDomain(request.getLocalName());
		        com.deya.wcm.services.search.index.IndexManager.addKeys(qn1, site_idtemp);
		        //添加用户搜索关键词 --- 上海科委定制-end
	        } 
	        //System.out.println("qn2-----" + qn2);
	        if(qn2!=null && !qn2.trim().equals("")){  
	        	sb.append("<param><key>qn2</key><value><![CDATA["+qn2+"]]></value></param>");
	        } 
	        //不包含以下全部关键词用(每个关键词之间用空格分隔)
	        String qn3 = nullToString((String)request.getParameter("qn3"));
	        if(qn3!=null && !qn3.equals("")){  
	        	sb.append("<param><key>qn3</key><value><![CDATA["+codeString(qn3)+"]]></value></param>");
	        }
	        
	        String retype = (String)request.getParameter("retype");
	        if(retype!=null && !retype.equals("")){  
	        	sb.append("<param><key>retype</key><value><![CDATA["+Encode.iso_8859_1ToUtf8(retype)+"]]></value></param>");
	        }
	        
	        //排序方式  sort   time：按时间排序    weight：按匹配度排序
	        String sort = (String)request.getParameter("sort");
	        if(sort!=null && !sort.equals("")){  
	        	sb.append("<param><key>sort</key><value><![CDATA["+Encode.iso_8859_1ToUtf8(sort)+"]]></value></param>");
	        }
	         
	        sb.append("</cicro>");
	        //System.out.println("sb.toString()------" + sb.toString());
	        return sb.toString();
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	//转码
	public static String codeString(String q){
		String code = JconfigUtilContainer.bashConfig().getProperty("code", "utf8", "searchCode");
		if(q!=null && !q.equals("")){  
        	if(code.equals("utf8")){
        		q = Encode.iso_8859_1ToUtf8(q);
        	}else if(code.equals("gbk")){
        		q = Encode.iso_8859_1ToGbk(q);
        	}else if(code.equals("system")){
        		q = Encode.iso_8859_1ToSystem(q);
        	}
		}else{
			q="";
		}
		return q;
	}
	
	
	//通过搜索关键字来得到关键字的同音词
	public static String getTongyinciByString(String keys){
		StringBuffer sb = new StringBuffer("");
		try{
			if(keys.trim().equals("")){
				return "";
			}
			String split = " ";
			String[] str = keys.split(split);
			for(String s : str){
				sb.append(" "+InitCiku.getTongyinci(s));
			}
			
			return keys + sb.toString();
		}catch (Exception e) {
			e.printStackTrace();
			return keys;
		}
	}
	
	public static String nullToString(String s){
		if(s==null){
			s = "";
		}
		return s.trim();
	}
	
	
	public static void main(String arr[]){
		  String str = "。继续推进国产动漫振兴工程、国家数字电影制作基地建设工程、多媒体数据库和经济信息平台、“中华字库”工程、国家“知识资源数据库”出版工程等重大文化建设项目。选择一批具备实施条件的重点项目给予支持";
		  System.out.println(deleteCodeByContent(str));
		  System.out.println(getTongyinciByString("宁下"));
	}
}
