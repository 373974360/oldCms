<%@page contentType="application/msword; charset=gb2312" %>
<%@page import="com.deya.wcm.services.zwgk.export.ExportInfoRPC"%>
<%@page import="java.util.*"%>
<%@page import="com.deya.wcm.bean.zwgk.export.*"%>
<%@page import="com.deya.util.DateUtil"%>
<%@page import="com.deya.util.Encode"%>
<%
List<ExportDept> listResult = new ArrayList<ExportDept>();
String titlename = "";
try{
	String infoType = (String)request.getParameter("tsArea");
	
	String node_id = request.getParameter("node_id");
	if(node_id == null || "".equals(node_id) || "null".equals(node_id))
		node_id = "0";
	String cat_ids = request.getParameter("cat_ids");
	if(cat_ids == null || "".equals(cat_ids) || "null".equals(cat_ids))
		cat_ids = "0";
	String start_day = request.getParameter("start_day");
	String end_day = request.getParameter("end_day");
	titlename = request.getParameter("titlename");
	if(titlename == null ||  "null".equals(titlename))
		titlename = "";
	String extype = request.getParameter("extype");
	
	Map map = new HashMap();
	map.put("node_id",node_id);
	map.put("cat_ids",cat_ids);
	map.put("start_day",start_day);
	map.put("end_day",end_day+" 23:59:59");
	map.put("titlename",titlename);
	map.put("extype",extype);
	String[] node_ids = node_id.split(",");
	StringBuffer site_ids = new StringBuffer("");
	for(int i=0;i<node_ids.length;i++){
		if(i==node_ids.length-1){
			site_ids.append("'"+node_ids[i]+"'");
		}else{
			site_ids.append(",'"+node_ids[i] + "',");
		}
	}
	map.put("site_ids",site_ids);
	map.put("infoType",infoType);
	listResult = ExportInfoRPC.exportZwgkInfoByNodeCate(map);
	/*
	for(ExportDept exportDept : listResult){
		System.out.println(exportDept.getCatName()+"("+exportDept.getCountInfo()+")");
		List<ExportInfo> exportInfo = exportDept.getExportInfo();
		if(exportInfo.size()>0){
			for(ExportInfo info : exportInfo){
				System.out.println(info.getIndexCode()+"--"+info.getTitle());
			}
		}
	}
	*/
	
}catch(Exception e){
	e.printStackTrace();
}
%>
<%
String title = "";
if(titlename.equals("")){
	title = DateUtil.getCurrentDateTime();
}else{
	//title = Encode.iso_8859_1ToGbk(titlename);
	title = java.net.URLEncoder.encode(titlename, "UTF-8");
}
response.setHeader("Content-Disposition", "attachment;filename="+title+".doc");
//response.setHeader("Content-Disposition", "attachment;filename=文档.doc");
%>
<html xmlns:v="urn:schemas-microsoft-com:vml"
xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns:m="http://schemas.microsoft.com/office/2004/12/omml"
xmlns:st1="urn:schemas-microsoft-com:office:smarttags"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft Word 12">
<meta name=Originator content="Microsoft Word 12">
<o:SmartTagType namespaceuri="urn:schemas-microsoft-com:office:smarttags"
 name="chsdate"/>
 <xml>
<w:WordDocument>
  <w:View>Print</w:View>
</w:WordDocument>
</xml>
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
	{font-family:黑体;
	panose-1:2 1 6 9 6 1 1 1 1 1;
	mso-font-alt:SimHei;
	mso-font-charset:134;
	mso-generic-font-family:modern;
	mso-font-pitch:fixed;
	mso-font-signature:-2147482945 953122042 22 0 262145 0;}
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
	panose-1:0 0 0 0 0 0 0 0 0 0;
	mso-font-alt:仿宋;
	mso-font-charset:134;
	mso-generic-font-family:roman;
	mso-font-format:other;
	mso-font-pitch:auto;
	mso-font-signature:1 135135232 16 0 262144 0;}
@font-face
	{font-family:华文中宋;
	panose-1:2 1 6 0 4 1 1 1 1 1;
	mso-font-charset:134;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:647 135200768 16 0 262303 0;}
@font-face
	{font-family:"\@宋体";
	panose-1:2 1 6 0 3 1 1 1 1 1;
	mso-font-charset:134;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:3 680460288 22 0 262145 0;}
@font-face
	{font-family:"\@黑体";
	panose-1:2 1 6 9 6 1 1 1 1 1;
	mso-font-charset:134;
	mso-generic-font-family:modern;
	mso-font-pitch:fixed;
	mso-font-signature:-2147482945 953122042 22 0 262145 0;}
@font-face
	{font-family:"\@华文中宋";
	panose-1:2 1 6 0 4 1 1 1 1 1;
	mso-font-charset:134;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:647 135200768 16 0 262303 0;}
@font-face
	{font-family:"\@仿宋_GB2312";
	panose-1:0 0 0 0 0 0 0 0 0 0;
	mso-font-charset:134;
	mso-generic-font-family:roman;
	mso-font-format:other;
	mso-font-pitch:auto;
	mso-font-signature:1 135135232 16 0 262144 0;}
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
	font-size:10.5pt;
	mso-bidi-font-size:11.0pt;
	font-family:"Calibri","sans-serif";
	mso-fareast-font-family:宋体;
	mso-bidi-font-family:"Times New Roman";
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
	font-family:"Calibri","sans-serif";
	mso-fareast-font-family:宋体;
	mso-bidi-font-family:"Times New Roman";
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
	font-family:"Calibri","sans-serif";
	mso-fareast-font-family:宋体;
	mso-bidi-font-family:"Times New Roman";
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
	mso-font-kerning:1.0pt;
	font-weight:bold;}
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
	{mso-style-name:"页眉 Char";
	mso-style-noshow:yes;
	mso-style-priority:99;
	mso-style-unhide:no;
	mso-style-locked:yes;
	mso-style-link:页眉;
	mso-ansi-font-size:9.0pt;
	mso-bidi-font-size:9.0pt;
	mso-font-kerning:1.0pt;}
span.Char1
	{mso-style-name:"页脚 Char";
	mso-style-noshow:yes;
	mso-style-priority:99;
	mso-style-unhide:no;
	mso-style-locked:yes;
	mso-style-link:页脚;
	mso-ansi-font-size:9.0pt;
	mso-bidi-font-size:9.0pt;
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
	margin:72.0pt 90.0pt 72.0pt 90.0pt;
	mso-header-margin:42.55pt;
	mso-footer-margin:49.6pt;
	mso-paper-source:0;
	layout-grid:15.6pt;}
div.Section1
	{page:Section1;}
-->
</style>
</head>

<body lang=ZH-CN style='tab-interval:21.0pt;text-justify-trim:punctuation'>
<div class=Section1 style='layout-grid:15.6pt'>

<p class=MsoTitle><span style='font-family:宋体;mso-ascii-font-family:Cambria;
mso-hansi-font-family:Cambria'><%=titlename%></span></p>

<%
for(ExportDept exportDept : listResult){
	List<ExportInfo> exportInfo = exportDept.getExportInfo();
	String cate_name = exportDept.getCatName();
	int count_all = exportDept.getCountInfo();
	if(exportInfo.size()>0){
		%>
			<p class=MsoNormal style='margin-top:15.6pt;margin-right:0cm;margin-bottom:
				15.6pt;margin-left:0cm;mso-para-margin-top:1.0gd;mso-para-margin-right:0cm;
				mso-para-margin-bottom:1.0gd;mso-para-margin-left:0cm;line-height:16.0pt;
				mso-line-height-rule:exactly'><b><span style='font-size:15.0pt;font-family:
				"仿宋_GB2312","serif";mso-hansi-font-family:华文中宋'><%=cate_name%></span></b><b><span
				lang=EN-US style='font-size:15.0pt;font-family:"仿宋_GB2312","serif";mso-hansi-font-family:
				宋体'>( <%=count_all%></span></b><b><span style='font-size:15.0pt;font-family:"仿宋_GB2312","serif";
				mso-hansi-font-family:宋体'>条<span lang=EN-US>)<o:p></o:p></span></span></b></p>
		<%
	}else{
		%>
			<p class=MsoNormal style='margin-top:7.8pt;margin-right:0cm;margin-bottom:7.8pt;
				margin-left:0cm;mso-para-margin-top:.5gd;mso-para-margin-right:0cm;mso-para-margin-bottom:
				.5gd;mso-para-margin-left:0cm;line-height:16.0pt;mso-line-height-rule:exactly'><b><span
				style='font-size:14.0pt;font-family:"仿宋_GB2312","serif";mso-hansi-font-family:
				宋体'><%=cate_name%>（<span lang=EN-US><%=count_all%></span>条）<span lang=EN-US><o:p></o:p></span></span></b></p>
		<%
	}
	if(exportInfo.size()>0){
			for(ExportInfo info : exportInfo){
				String indexCode = info.getIndexCode();
				String infoTitle = info.getTitle();
				String time = info.getTime();
				String depName = info.getDepName();
			%>
			 <table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=546
				 style='width:409.4pt;border-collapse:collapse;border:none;mso-border-alt:solid windowtext .5pt;
				 mso-yfti-tbllook:1184;mso-padding-alt:0cm 5.4pt 0cm 5.4pt;mso-border-insideh:
				 .5pt solid windowtext;mso-border-insidev:.5pt solid windowtext'>
				 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>
				  <td width=83 valign=top style='width:62.1pt;border:solid windowtext 1.0pt;
				  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
				  <p class=MsoNormal style='line-height:16.0pt;mso-line-height-rule:exactly'><span
				  style='font-family:黑体;mso-font-kerning:0pt'>索 引 号</span><span lang=EN-US
				  style='font-size:16.0pt;mso-bidi-font-size:11.0pt;font-family:"仿宋_GB2312","serif";
				  mso-hansi-font-family:宋体;mso-font-kerning:0pt'><o:p></o:p></span></p>
				  </td>
				  <td width=217 valign=top style='width:163.05pt;border:solid windowtext 1.0pt;
				  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
				  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
				  <p class=MsoNormal style='line-height:16.0pt;mso-line-height-rule:exactly'><span
				  lang=EN-US style='mso-bidi-font-size:10.5pt;font-family:"仿宋_GB2312","serif";
				  mso-hansi-font-family:宋体;mso-font-kerning:0pt'><%=info.getIndexCode()%></span><span
				  lang=EN-US style='font-size:16.0pt;mso-bidi-font-size:11.0pt;font-family:
				  "仿宋_GB2312","serif";mso-hansi-font-family:宋体;mso-font-kerning:0pt'><o:p></o:p></span></p>
				  </td>
				  <td width=85 valign=top style='width:63.75pt;border:solid windowtext 1.0pt;
				  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
				  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
				  <p class=MsoNormal style='line-height:16.0pt;mso-line-height-rule:exactly'><span
				  style='font-family:黑体;mso-font-kerning:0pt'>发布机构</span><span lang=EN-US
				  style='font-size:16.0pt;mso-bidi-font-size:11.0pt;font-family:"仿宋_GB2312","serif";
				  mso-hansi-font-family:宋体;mso-font-kerning:0pt'><o:p></o:p></span></p>
				  </td>
				  <td width=161 valign=top style='width:120.5pt;border:solid windowtext 1.0pt;
				  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
				  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
				  <p class=MsoNormal style='line-height:16.0pt;mso-line-height-rule:exactly'><span
				  style='mso-bidi-font-size:10.5pt;font-family:"仿宋_GB2312","serif";mso-hansi-font-family:
				  宋体;mso-font-kerning:0pt'><%=info.getDepName()%></span><span lang=EN-US style='font-size:
				  16.0pt;mso-bidi-font-size:11.0pt;font-family:"仿宋_GB2312","serif";mso-hansi-font-family:
				  宋体;mso-font-kerning:0pt'><o:p></o:p></span></p>
				  </td>
				 </tr>
				 <tr style='mso-yfti-irow:1;height:17.35pt'>
				  <td width=83 valign=top style='width:62.1pt;border:solid windowtext 1.0pt;
				  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
				  padding:0cm 5.4pt 0cm 5.4pt;height:17.35pt'>
				  <p class=MsoNormal style='line-height:16.0pt;mso-line-height-rule:exactly'><span
				  style='font-family:黑体;mso-font-kerning:0pt'>文<span lang=EN-US><span
				  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp; </span></span>号</span><span
				  lang=EN-US style='mso-bidi-font-size:10.5pt;font-family:"仿宋_GB2312","serif";
				  mso-hansi-font-family:宋体;mso-font-kerning:0pt'><o:p></o:p></span></p>
				  </td>
				  <td width=217 valign=top style='width:163.05pt;border-top:none;border-left:
				  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
				  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
				  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:17.35pt'>
				  <p class=MsoNormal style='line-height:16.0pt;mso-line-height-rule:exactly'><span
				  style='mso-bidi-font-size:10.5pt;font-family:"仿宋_GB2312","serif";mso-hansi-font-family:
				  宋体;mso-font-kerning:0pt'><%=info.getDoc_no()%><span
				  lang=EN-US><o:p></o:p></span></span></p>
				  </td>
				  <td width=85 valign=top style='width:63.75pt;border-top:none;border-left:
				  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
				  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
				  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:17.35pt'>
				  <p class=MsoNormal style='line-height:16.0pt;mso-line-height-rule:exactly'><span
				  style='font-family:黑体;mso-font-kerning:0pt'>生成日期</span><span lang=EN-US
				  style='font-size:16.0pt;mso-bidi-font-size:11.0pt;font-family:"仿宋_GB2312","serif";
				  mso-hansi-font-family:宋体;mso-font-kerning:0pt'><o:p></o:p></span></p>
				  </td>
				  <td width=161 valign=top style='width:120.5pt;border-top:none;border-left:
				  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
				  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
				  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:17.35pt'>
				  <p class=MsoNormal style='line-height:16.0pt;mso-line-height-rule:exactly'><st1:chsdate
				  Year="2000" Month="5" Day="8" IsLunarDate="False" IsROCDate="False" w:st="on"><span
				   lang=EN-US style='mso-bidi-font-size:10.5pt;font-family:"仿宋_GB2312","serif";
				   mso-hansi-font-family:宋体;mso-font-kerning:0pt'><%=info.getTime()%></span></st1:chsdate><span
				  lang=EN-US style='font-size:16.0pt;mso-bidi-font-size:11.0pt;font-family:
				  "仿宋_GB2312","serif";mso-hansi-font-family:宋体;mso-font-kerning:0pt'><o:p></o:p></span></p>
				  </td>
				 </tr>
				 <tr style='mso-yfti-irow:2;height:17.65pt'>
				  <td width=83 valign=top style='width:62.1pt;border:solid windowtext 1.0pt;
				  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
				  padding:0cm 5.4pt 0cm 5.4pt;height:17.65pt'>
				  <p class=MsoNormal style='line-height:16.0pt;mso-line-height-rule:exactly'><span
				  style='font-family:黑体;mso-font-kerning:0pt'>信息名称</span><span lang=EN-US
				  style='font-size:16.0pt;mso-bidi-font-size:11.0pt;font-family:"仿宋_GB2312","serif";
				  mso-hansi-font-family:宋体;mso-font-kerning:0pt'><o:p></o:p></span></p>
				  </td>
				  <td width=463 colspan=3 valign=top style='width:347.3pt;border-top:none;
				  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
				  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
				  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:17.65pt'>
				  <p class=MsoNormal style='line-height:16.0pt;mso-line-height-rule:exactly'><span
				  style='mso-bidi-font-size:10.5pt;font-family:"仿宋_GB2312","serif";mso-hansi-font-family:
				  宋体;mso-font-kerning:0pt'><%=info.getTitle()%></span><span lang=EN-US
				  style='font-size:16.0pt;mso-bidi-font-size:11.0pt;font-family:"仿宋_GB2312","serif";
				  mso-hansi-font-family:宋体;mso-font-kerning:0pt'><o:p></o:p></span></p>
				  </td>
				 </tr>
				 <tr style='mso-yfti-irow:3;mso-yfti-lastrow:yes;height:60.6pt'>
				  <td width=83 valign=top style='width:62.1pt;border:solid windowtext 1.0pt;
				  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
				  padding:0cm 5.4pt 0cm 5.4pt;height:60.6pt'>
				  <p class=MsoNormal style='margin-top:2.0pt;margin-right:0cm;margin-bottom:
				  2.0pt;margin-left:0cm;mso-line-height-alt:2.0pt'><span style='font-family:
				  黑体;mso-font-kerning:0pt'>内容概述<span lang=EN-US><o:p></o:p></span></span></p>
				  </td>
				  <td width=463 colspan=3 valign=top style='width:347.3pt;border-top:none;
				  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
				  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
				  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:60.6pt'>
				  <p class=MsoNormal style='margin-top:2.0pt;margin-right:0cm;margin-bottom:
				  2.0pt;margin-left:0cm;text-indent:21.0pt;mso-char-indent-count:2.0;
				  mso-line-height-alt:2.0pt'><span style='mso-bidi-font-size:10.5pt;font-family:
				  "仿宋_GB2312","serif";mso-hansi-font-family:宋体;mso-font-kerning:0pt'><%=info.getDescription()%></span><span
				  lang=EN-US style='font-size:16.0pt;mso-bidi-font-size:11.0pt;font-family:
				  "仿宋_GB2312","serif";mso-hansi-font-family:宋体;mso-font-kerning:0pt'><o:p></o:p></span></p>
				  </td>
				 </tr>
				</table>
				<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>
			<%
			}
	}
    
}
%>
</div>
</body>
</html>

