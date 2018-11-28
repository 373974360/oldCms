<%@ page contentType="application/json; charset=utf-8"%>
<%@ page import="com.deya.util.FormatUtil"%>
<%@ page import="com.deya.wcm.bean.cms.info.ArticleBean"%>
<%@ page import="com.deya.wcm.services.cms.info.InfoBaseManager"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.deya.wcm.services.zwgk.index.IndexManager"%>
<%@ page import="com.deya.util.DateUtil"%>
<%@ page import="com.deya.wcm.services.cms.info.ModelUtil"%><%@ page import="org.apache.commons.lang3.StringUtils"%><%@ page import="com.deya.util.Base64Utiles"%><%@ page import="com.deya.wcm.services.search.util.word.WordService"%><%@ page import="java.io.File"%><%@ page import="com.deya.util.WordUtils"%>
<%
    String callback = request.getParameter("callback");
    if(callback != null && !"".equals(callback)){
        out.println(callback + "(" + setNews(request) + ")");
    }else{
        out.println(setNews(request));
    }
%>
<%!
    public String setNews(HttpServletRequest request){
        String json = "";
        String site_id = "CMSxxxq";//站点ID
        String upload_path = "/deya/cms/vhosts/www.xixianxinqu.gov.cn/ROOT";
        String file_path= "/upload/"+site_id+"/"+DateUtil.getCurrentDateTime("yyyyMMdd")+"/";
        int cat_id = 10002;//写入目录
        String title = FormatUtil.formatNullString(request.getParameter("title"));//标题
        String doc_no = FormatUtil.formatNullString(request.getParameter("doc_no"));//发文字号
        String publish_dept = FormatUtil.formatNullString(request.getParameter("publish_dept"));//发布机构
        String gk_signer = FormatUtil.formatNullString(request.getParameter("gk_signer"));//签发人
        String keywords = FormatUtil.formatNullString(request.getParameter("keywords"));//关键字
        String publish_date = FormatUtil.formatNullString(request.getParameter("publish_date"));//发布日期
        String info_content = FormatUtil.formatNullString(request.getParameter("info_content"));//纯文字推送的 正文内容
        String info_content_file = FormatUtil.formatNullString(request.getParameter("info_content_file"));//word推送的 正文内容

        File currentDir = new File(upload_path+file_path);
        if (!currentDir.exists()) {
            currentDir.mkdirs();
        }

        //word内容推送解析
        if(StringUtils.isNotEmpty(info_content_file)){
            String ext = info_content_file.substring(info_content_file.lastIndexOf("."),info_content_file.length());
            String fileName = DateUtil.getCurrentDateTime("yyyyMMddHHmmss") +ext;
            boolean b = Base64Utiles.base64ToFile(info_content_file.substring(0,info_content_file.lastIndexOf(".")),upload_path+file_path+fileName);
            if(b){
                if(ext.equals(".docx")||ext.equals(".DOCX")){
                    info_content = WordUtils.Word2007ToHtml(upload_path+file_path,fileName);
                }else{
                    info_content = "";
                }
            }
        }


        //附件相关
        int file_count = Integer.parseInt(request.getParameter("file_count"));//附件个数
        if(file_count>0){
            String downHtml = "<p>附件：</p>";
            for(int i=0;i<file_count;i++){
                String file = FormatUtil.formatNullString(request.getParameter("file_"+(i+1)));//附件base64编码
                String file_name = FormatUtil.formatNullString(request.getParameter("file_name_"+(i+1)));//附件中文名称

                String ext = file.substring(file.lastIndexOf("."),file.length());
                String fileName = DateUtil.getCurrentDateTime("yyyyMMddHHmmss") +ext;
                boolean b = Base64Utiles.base64ToFile(file.substring(0,file.lastIndexOf(".")),upload_path+file_path+fileName);
                if(b){
                    downHtml += "<p><a href='"+file_path+fileName+"' target='_blank'>"+file_name+"</a></p>";
                }
            }
            info_content += downHtml;
        }

        info_content = info_content.replace(upload_path,"");


        ArticleBean article = new ArticleBean();
        article.setCat_id(cat_id);
        article.setInput_user(1);//默认录入人是系统管理员
        article.setInfo_status(2);//默认状态待审
        article.setModel_id(11);
        article.setApp_id("cms");
        article.setSite_id(site_id);
        int id = InfoBaseManager.getNextInfoId();
        article.setId(id);
        article.setInfo_id(id);
        article.setHits(0);
        article.setSubtitle("");
        article.setTags("");
        article.setAuthor("");
        article.setSource("");
        article.setEditor("");
        article.setDescription("");
        article.setThumb_url("");
        article.setGk_signer(gk_signer);
        Map<String,String> imap = IndexManager.getIndex(site_id,article.getCat_id()+"","","");
        if(!imap.isEmpty()&&imap.containsKey("gk_index")){
            article.setGk_index(imap.get("gk_index"));
        }
        if(title != null && !"".equals(title) && !"null".equals(title)){
            article.setTitle(title);
        }else{
            article.setTitle("");
        }
        if(doc_no != null && !"".equals(doc_no) && !"null".equals(doc_no)){
            article.setDoc_no(doc_no);
        }else{
            article.setDoc_no("");
        }
        if(publish_dept != null && !"".equals(publish_dept) && !"null".equals(publish_dept)){
            article.setGk_input_dept(publish_dept);
        }else{
            article.setGk_input_dept("");
        }
        if(keywords != null && !"".equals(keywords) && !"null".equals(keywords)){
            article.setTopic_key(keywords);
        }else{
            article.setTopic_key("");
        }
        if(info_content != null && !"".equals(info_content) && !"null".equals(info_content)){
            article.setInfo_content(info_content);
        }else{
            article.setInfo_content("");
        }
        if(publish_date != null && !"".equals(publish_date) && !"null".equals(publish_date)){
            article.setInput_dtime(publish_date);
            article.setReleased_dtime(publish_date);
        }else{
            article.setInput_dtime(DateUtil.getCurrentDateTime());
            article.setReleased_dtime(DateUtil.getCurrentDateTime());
        }
        if(ModelUtil.insert(article,"article",null)){
            json = "{\"code\":1,\"msg\":\"success\"}";
        }else{
            json = "{\"code\":0,\"msg\":\"error\"}";
        }
        return "["+json+"]";
    }
%>
