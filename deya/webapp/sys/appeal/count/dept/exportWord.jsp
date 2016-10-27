<%@page contentType="text/html; charset=GB2312"%>
<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.appeal.sq.SQManager,com.deya.wcm.bean.appeal.sq.SQBean"%>
<%@page import="com.deya.util.DateUtil"%>
<%@page import="java.util.*"%>
<%@page import="com.deya.wcm.bean.appeal.count.CountBean" %>
<%@page import="com.deya.wcm.bean.appeal.count.LetterCountBean" %>
<%@page import="com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.services.appeal.count.CountUtil"%>
<%@page import="java.io.File"%>
<%@page import="com.deya.wcm.services.appeal.count.*"%>
<%@page import="java.net.URLEncoder"%>
<%
    String nowDate = CountUtil.getNowDayDate();
	       response.setHeader("Content-Disposition","attachment;filename="+nowDate+".doc");
    
	String model_id = FormatUtil.formatNullString(request.getParameter("model_id"));
	String s = FormatUtil.formatNullString(request.getParameter("s"));
	String e = FormatUtil.formatNullString(request.getParameter("e"));
	String do_dept = FormatUtil.formatNullString(request.getParameter("do_dept"));
	String sq_status = FormatUtil.formatNullString(request.getParameter("sq_status"));
	String user_id = FormatUtil.formatNullString(request.getParameter("user_id"));

	List<LetterCountBean> list = new ArrayList<LetterCountBean>();
    Map  m = new HashMap();
	m.put("model_id", model_id);
	m.put("s",s);	
	m.put("e",e);
	
	if("all".equals(sq_status)){
		m.put("sq_status",sq_status);
	}
	if("0".equals(sq_status)){
		m.put("sq_status","0");
	}
	if("1".equals(sq_status)){
		m.put("sq_status","1");
	}
	if("2".equals(sq_status)){
		m.put("sq_status","2");
	}
	if("wei".equals(sq_status)){
		m.remove("sq_status");
		m.put("type","wei");
	}
	if(do_dept !=""){
		//System.out.println("===do_dept======"+do_dept);
		m.put("do_dept",do_dept);
		m.remove("user_id");
		list = CountServices.getListByModelIdAndDept(m);//第一个参数为站点ID，暂时默认为空	
	}else{
		//System.out.println("===user_id======"+user_id+"============sq_status====="+sq_status);
	    m.put("user_id",user_id);
		m.remove("do_dept");
		list = CountServices.getListByModelIdAndUserId(m);//用户处理的信件
	}
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns="http://www.w3.org/TR/REC-html40">
<meta http-equiv=Content-Type content="text/html;charset=GB2312">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft Word 12">
<meta name=Originator content="Microsoft Word 12">
<head>
<o:SmartTagType namespaceuri="urn:schemas-microsoft-com:office:smarttags" name="chsdate"/>
<style>
<!--
 /* Font Definitions */
 @font-face
	{font-family:宋体;
	panose-1:2 1 6 0 3 1 1 1 1 1;
	mso-font-alt:SimSun;
	mso-font-charset:134;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:3 680460288 22 0 262145 0;}
@font-face
	{font-family:"Cambria Math";
	panose-1:2 4 5 3 5 4 6 3 2 4;
	mso-font-charset:0;
	mso-generic-font-family:roman;
	mso-font-pitch:variable;
	mso-font-signature:-1610611985 1107304683 0 0 415 0;}
@font-face
	{font-family:Cambria;
	panose-1:2 4 5 3 5 4 6 3 2 4;
	mso-font-charset:0;
	mso-generic-font-family:roman;
	mso-font-pitch:variable;
	mso-font-signature:-1610611985 1073741899 0 0 415 0;}
@font-face
	{font-family:Calibri;
	panose-1:2 15 5 2 2 2 4 3 2 4;
	mso-font-charset:0;
	mso-generic-font-family:swiss;
	mso-font-pitch:variable;
	mso-font-signature:-520092929 1073786111 9 0 415 0;}
@font-face
	{font-family:仿宋_GB2312;
	mso-font-alt:微软雅黑;
	mso-font-charset:134;
	mso-generic-font-family:modern;
	mso-font-pitch:auto;
	mso-font-signature:1 135135232 0 0 262144 0;}
@font-face
	{font-family:"\@宋体";
	panose-1:2 1 6 0 3 1 1 1 1 1;
	mso-font-charset:134;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:3 680460288 22 0 262145 0;}
@font-face
	{font-family:"\@仿宋_GB2312";
	mso-font-charset:134;
	mso-generic-font-family:modern;
	mso-font-pitch:auto;
	mso-font-signature:1 135135232 0 0 262144 0;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{mso-style-unhide:no;
	mso-style-qformat:yes;
	mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:12.0pt;
	mso-bidi-font-size:10.0pt;
	font-family:"Times New Roman","serif";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoHeader, li.MsoHeader, div.MsoHeader
	{mso-style-noshow:yes;
	mso-style-priority:99;
	mso-style-link:"页眉 Char";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:center;
	mso-pagination:none;
	tab-stops:center 207.65pt right 415.3pt;
	layout-grid-mode:char;
	border:none;
	mso-border-bottom-alt:solid windowtext .75pt;
	padding:0cm;
	mso-padding-alt:0cm 0cm 1.0pt 0cm;
	font-size:9.0pt;
	font-family:"Times New Roman","serif";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoFooter, li.MsoFooter, div.MsoFooter
	{mso-style-noshow:yes;
	mso-style-priority:99;
	mso-style-link:"页脚 Char";
	margin:0cm;
	margin-bottom:.0001pt;
	mso-pagination:none;
	tab-stops:center 207.65pt right 415.3pt;
	layout-grid-mode:char;
	font-size:9.0pt;
	font-family:"Times New Roman","serif";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoTitle, li.MsoTitle, div.MsoTitle
	{mso-style-priority:10;
	mso-style-unhide:no;
	mso-style-qformat:yes;
	mso-style-link:"标题 Char";
	mso-style-next:正文;
	margin-top:12.0pt;
	margin-right:0cm;
	margin-bottom:3.0pt;
	margin-left:0cm;
	text-align:center;
	mso-pagination:none;
	mso-outline-level:1;
	font-size:16.0pt;
	font-family:"Cambria","serif";
	mso-fareast-font-family:宋体;
	mso-bidi-font-family:"Times New Roman";
	mso-ansi-language:X-NONE;
	mso-fareast-language:X-NONE;
	font-weight:bold;}
p.MsoDocumentMap, li.MsoDocumentMap, div.MsoDocumentMap
	{mso-style-noshow:yes;
	mso-style-priority:99;
	mso-style-link:"文档结构图 Char";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:9.0pt;
	font-family:宋体;
	mso-hansi-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	mso-font-kerning:1.0pt;}
span.Char
	{mso-style-name:"标题 Char";
	mso-style-priority:10;
	mso-style-unhide:no;
	mso-style-locked:yes;
	mso-style-parent:"";
	mso-style-link:标题;
	mso-ansi-font-size:16.0pt;
	mso-bidi-font-size:16.0pt;
	font-family:"Cambria","serif";
	mso-ascii-font-family:Cambria;
	mso-fareast-font-family:宋体;
	mso-hansi-font-family:Cambria;
	mso-bidi-font-family:"Times New Roman";
	font-weight:bold;}
span.Char0
	{mso-style-name:"文档结构图 Char";
	mso-style-noshow:yes;
	mso-style-priority:99;
	mso-style-unhide:no;
	mso-style-locked:yes;
	mso-style-link:文档结构图;
	mso-ansi-font-size:9.0pt;
	mso-bidi-font-size:9.0pt;
	font-family:宋体;
	mso-ascii-font-family:宋体;
	mso-hansi-font-family:"Times New Roman";
	mso-font-kerning:1.0pt;}
span.Char1
	{mso-style-name:"页眉 Char";
	mso-style-noshow:yes;
	mso-style-priority:99;
	mso-style-unhide:no;
	mso-style-locked:yes;
	mso-style-link:页眉;
	mso-ansi-font-size:9.0pt;
	mso-bidi-font-size:9.0pt;
	font-family:"Times New Roman","serif";
	mso-ascii-font-family:"Times New Roman";
	mso-hansi-font-family:"Times New Roman";
	mso-font-kerning:1.0pt;}
span.Char2
	{mso-style-name:"页脚 Char";
	mso-style-noshow:yes;
	mso-style-priority:99;
	mso-style-unhide:no;
	mso-style-locked:yes;
	mso-style-link:页脚;
	mso-ansi-font-size:9.0pt;
	mso-bidi-font-size:9.0pt;
	font-family:"Times New Roman","serif";
	mso-ascii-font-family:"Times New Roman";
	mso-hansi-font-family:"Times New Roman";
	mso-font-kerning:1.0pt;}
.MsoChpDefault
	{mso-style-type:export-only;
	mso-default-props:yes;
	mso-ascii-font-family:Calibri;
	mso-fareast-font-family:宋体;
	mso-hansi-font-family:Calibri;}
 /* Page Definitions */
@page Section1
	{size:595.3pt 841.9pt;
	margin:72.0pt 54.0pt 72.0pt 54.0pt;
	mso-header-margin:42.55pt;
	mso-footer-margin:49.6pt;
	mso-paper-source:0;
	layout-grid:16.3pt;}
div.Section1
	{page:Section1;}
-->
</style>
</head>
<body lang=ZH-CN style='tab-interval:21.0pt;text-justify-trim:punctuation'>
<div class=Section1 style='layout-grid:16.3pt'>
<p class=MsoTitle style='margin-left:12.0pt;mso-para-margin-left:1.0gd'><span
lang=X-NONE style='font-family:宋体;mso-ascii-font-family:Cambria;mso-hansi-font-family:
Cambria'>诉求信件办理情况统计表</span><span lang=X-NONE></span></p>
<div align=center>
<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=638
 style='width:478.4pt;margin-left:28.15pt;border-collapse:collapse;border:none;
 mso-border-alt:solid windowtext .5pt;mso-yfti-tbllook:1184;mso-padding-alt:
 0cm 5.4pt 0cm 5.4pt;mso-border-insideh:.5pt solid windowtext;mso-border-insidev:
 .5pt solid windowtext'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>
  <td width=120 style='width:100pt;border:solid windowtext 1.0pt;mso-border-alt:
  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:24.0pt;
  mso-line-height-rule:exactly'><b><span style='font-size:10.5pt;font-family:
  仿宋_GB2312;mso-hansi-font-family:宋体'>信件编码<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td style='width:200.6pt;border:solid windowtext 1.0pt;border-left:
  none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:24.0pt;
  mso-line-height-rule:exactly'><b><span style='font-size:10.5pt;font-family:
  仿宋_GB2312;mso-hansi-font-family:宋体;color:black'>信件标题<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td width=92 style='width:69.15pt;border:solid windowtext 1.0pt;border-left:
  none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:24.0pt;
  mso-line-height-rule:exactly'><b><span style='font-size:10.5pt;font-family:
  仿宋_GB2312;mso-hansi-font-family:宋体'>来信时间<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td width=90 style='width:60pt;border:solid windowtext 1.0pt;border-left:
  none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:24.0pt;
  mso-line-height-rule:exactly'><b><span style='font-size:10.5pt;font-family:
  仿宋_GB2312;mso-hansi-font-family:宋体;color:black'><nobr>处理状态</nobr><span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td width=90 style='width:60pt;border:solid windowtext 1.0pt;border-left:
  none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:24.0pt;
  mso-line-height-rule:exactly'><b><span style='font-size:10.5pt;font-family:
  仿宋_GB2312;mso-hansi-font-family:宋体'><nobr>递交渠道</nobr><span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
 </tr>
<%
    if(list != null)
    {
		   for(int i=0;i<list.size();i++){   
			   LetterCountBean lcBean = list.get(i);
			    String sq_conde = lcBean.getSq_code();
				String sq_title = lcBean.getSq_title();
				String sq_dtime = lcBean.getSq_dtime().substring(0,10);
				String status = lcBean.getSq_status();
				String model_cname = lcBean.getModel_cname();
				String temp ="";
				
			    if("0".equals(status)){
			    	temp ="待处理";
			    }else if("1".equals(status)){
			    	temp ="处理中";
			    }else if("2".equals(status)){
			    	temp ="待审核";
			    }else if("3".equals(status)){
			    	temp ="已办结";
			    }else if("wei".equals(status)){
			    	temp ="未办结";
			    }
				
%>
 <tr style='mso-yfti-irow:1'>
  <td width=120 style='width:102.2pt;border:solid windowtext 1.0pt;border-top:
  none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='line-height:16.0pt;mso-line-height-rule:exactly'><span
  lang=EN-US style='font-size:10.5pt;font-family:仿宋_GB2312;mso-hansi-font-family:
  宋体'><%=sq_conde%><o:p></o:p></span></p>
  </td>
  <td style='width:200.6pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='line-height:16.0pt;mso-line-height-rule:exactly'><span
  style='font-size:10.5pt;font-family:仿宋_GB2312;mso-hansi-font-family:宋体'><%=sq_title%>
  <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
  <td width=92 style='width:69.15pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal style='line-height:16.0pt;mso-line-height-rule:exactly'><st1:chsdate
  Year="2000" Month="5" Day="8" IsLunarDate="False" IsROCDate="False" w:st="on"><span
   lang=EN-US style='font-size:10.5pt;font-family:仿宋_GB2312;mso-hansi-font-family:
   宋体'><%=sq_dtime%></span></st1:chsdate><span lang=EN-US style='font-size:10.5pt;
  font-family:仿宋_GB2312;mso-hansi-font-family:宋体'><o:p></o:p></span></p>
  </td>
  <td width=90 style='width:52.85pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt;
  mso-line-height-rule:exactly'><span style='font-size:10.5pt;font-family:仿宋_GB2312;
  mso-hansi-font-family:宋体'><%=temp%><span lang=EN-US><o:p></o:p></span></span></p>
  </td>
  <td width=90 style='width:53.6pt;border-top:none;border-left:none;border-bottom:
  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;mso-border-top-alt:
  solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt;
  mso-line-height-rule:exactly'><span style='font-size:10.5pt;font-family:仿宋_GB2312;
  mso-hansi-font-family:宋体'><%=model_cname%><span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
<%		   
	   } 
   }
%>
</table>
</div>
</div>
</body>
</html>
