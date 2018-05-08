/*     */ package com.deya.wcm.dataCollection.util;
/*     */ 
/*     */ import com.deya.util.jconfig.JconfigUtilContainer;
/*     */ import com.deya.wcm.bean.control.SiteDomainBean;
/*     */ import com.deya.wcm.services.control.domain.SiteDomainManager;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.htmlparser.tags.MetaTag;
/*     */ 
/*     */ public class FormatString
/*     */ {
/*     */   public static String filterHtmlTag(String str)
/*     */   {
/*  27 */     String newStr = "";
/*  28 */     newStr = str.replaceAll("</?[^<]+>", "");
/*  29 */     return newStr;
/*     */   }
/*     */ 
/*     */   public static String filterStrForKeyword(String value)
/*     */   {
/*  39 */     String str = value;
/*     */ 
/*  41 */     str = str.replaceAll("文章关键词：", "");
/*     */ 
/*  43 */     str = str.replaceAll("&nbsp;", "");
/*     */ 
/*  45 */     return str;
/*     */   }
/*     */ 
/*     */   public static String getPageEncode(String html)
/*     */   {
/*  59 */     String encode = "";
/*  60 */     MetaTag metaTag = (MetaTag)ParserUtils.parseTag(html, MetaTag.class, "http-equiv", "Content-type");
/*  61 */     if (metaTag != null)
/*     */     {
/*  63 */       encode = metaTag.getMetaContent();
/*  64 */       if (encode.length() > 0)
/*     */       {
/*  66 */         encode = formatPageEncode(encode);
/*     */       }
/*     */     }
/*  69 */     return encode;
/*     */   }
/*     */ 
/*     */   public static String formatPageEncode(String str)
/*     */   {
/*  74 */     String reg = "^[a-zA-Z/;]*?\\s*charset=([a-zA-Z_-0-9]+)";
/*  75 */     String newEncode = str.replaceAll(reg, "$1");
/*  76 */     return newEncode;
/*     */   }
/*     */ 
/*     */   public static int urlmarkNum(String urlbymark)
/*     */   {
/*  91 */     int num = 0;
/*  92 */     String reg = "[<](.*?)[>]+";
/*  93 */     Pattern p = Pattern.compile(reg);
/*  94 */     Matcher m = p.matcher(urlbymark);
/*  95 */     while (m.find()) {
/*  96 */       num++;
/*     */     }
/*  98 */     return num;
/*     */   }
/*     */ 
/*     */   public static String getManagerPath()
/*     */   {
/* 106 */     String path = JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path");
/* 107 */     path = path + File.separator + "dataCollection" + File.separator + "log";
/* 108 */     return path;
/*     */   }
/*     */ 
/*     */   public static String getArtPicUploadPath(String site_id)
/*     */   {
/* 117 */     String path = JconfigUtilContainer.bashConfig().getProperty("path", "", "hostRoot_path");
/* 118 */     String domain = getHostSiteDomainBySiteID(site_id);
/* 119 */     path = path + File.separator + domain + File.separator + "ROOT" + File.separator + "collArtPic";
/* 120 */     return path;
/*     */   }
/*     */ 
/*     */   public static String getHostSiteDomainBySiteID(String site_id)
/*     */   {
/* 130 */     String domain_name = "";
/* 131 */     List<SiteDomainBean> l = SiteDomainManager.getDomainListBySiteID(site_id);
/* 132 */     if ((l != null) && (l.size() > 0))
/*     */     {
/* 134 */       for (SiteDomainBean sdb : l)
/*     */       {
/* 136 */         if ((site_id.equals(sdb.getSite_id())) && (sdb.getIs_host() == 1))
/* 137 */           domain_name = sdb.getSite_domain();
/*     */       }
/*     */     }
/* 140 */     return domain_name;
/*     */   }
/*     */ 
/*     */   public static String getSiteDomain(String site_id)
/*     */   {
/* 149 */     return SiteDomainManager.getDefaultSiteDomainBySiteID(site_id);
/*     */   }
/*     */ 
/*     */   public static boolean strIsNull(String str)
/*     */   {
/* 160 */     if (str == null) {
/* 161 */       str = "";
/*     */     }
/* 163 */     str = str.trim();
/* 164 */     if ((!str.equals("")) && (!str.equals("null"))) {
/* 165 */       return true;
/*     */     }
/* 167 */     return false;
/*     */   }
/*     */ 
/*     */   public static List<String> getImage(String content)
/*     */   {
/* 184 */     List list = new ArrayList();
/*     */ 
/* 186 */     String imageReg = "src.*?=[\"](.*?)[\"]";
/*     */ 
/* 188 */     Pattern p = Pattern.compile(imageReg);
/* 189 */     Matcher m = p.matcher(content);
/* 190 */     while (m.find()) {
/* 191 */       list.add(m.group().replace("src=", "").replaceAll("\"", ""));
/*     */     }
/* 193 */     return list;
/*     */   }
/*     */ 
/*     */   public static String getPicName(String str)
/*     */   {
/* 205 */     String pic_name = "";
/* 206 */     String[] s = str.split("/");
/* 207 */     for (int i = 0; i < s.length; i++) {
/* 208 */       pic_name = s[(s.length - 2)];
/*     */     }
/* 210 */     pic_name = str.substring(str.indexOf(pic_name) - 1, str.length());
/* 211 */     return pic_name;
/*     */   }
/*     */ 
/*     */   public static boolean contentIsHaveImage(String str)
/*     */   {
/* 224 */     boolean b = false;
/* 225 */     String s = "";
/* 226 */     String imageReg = "<img|IMG.*?src.*?=.*?(.*?)>";
/* 227 */     Pattern p = Pattern.compile(imageReg);
/* 228 */     Matcher m = p.matcher(str);
/* 229 */     while (m.find()) {
/* 230 */       s = m.group();
/*     */     }
/* 232 */     if (strIsNull(s)) {
/* 233 */       b = true;
/*     */     }
/* 235 */     return b;
/*     */   }
/*     */ 
/*     */   public static String filterTitleKeyWord(String title)
/*     */   {
/* 248 */     String[] s = { "|", "_", "-" };
/* 249 */     for (int i = 0; i < s.length; i++) {
/* 250 */       if (title.contains(s[i])) {
/* 251 */         title = title.substring(0, title.indexOf(s[i]));
/*     */       }
/*     */     }
/* 254 */     return title;
/*     */   }
/*     */ 
/*     */   public static String filterURL(String str)
/*     */   {
/* 263 */     String newStr = "";
/* 264 */     if (strIsNull(str)) {
/* 265 */       newStr = str.replaceAll(" ", "%20");
/* 266 */       newStr = newStr.replaceAll("\\(", "%28");
/* 267 */       newStr = newStr.replaceAll("\\)", "%29");
/*     */     }
/* 269 */     return newStr;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 274 */     String str = "asdfasdhfaoisdhfpoiahsd[oifhasodihfasdhflkahsdfha<IMG border=0 src=\"/c/news/20140810/skdfashdak.jsp\">hdiofhaiosdhfadfhaiosdhfiahsdhfaihsdiof";
/*     */ 
/* 278 */     str = "/uploads/allimg/140110/1-1401101K3393J.JPG";
/*     */ 
/* 286 */     System.out.println(getPicName(str));
/*     */   }
/*     */ }

/* Location:           C:\Users\li\Desktop\dataCollection.zip
 * Qualified Name:     dataCollection.util.FormatString
 * JD-Core Version:    0.6.2
 */