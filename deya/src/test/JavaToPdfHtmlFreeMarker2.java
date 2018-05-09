import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.yinhai.pdf.PathUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @User: Administrator
 * @Date: 2018/5/8 0008
 */
public class JavaToPdfHtmlFreeMarker2 {
    private static final String DEST = "D:/HelloWorld_CN_HTML_FREEMARKER2.pdf";
    private static final String FONT = "D:/code/idea/cms/out/production/deya/font/wryh.ttf";
    private static final String HTML = "question_template.html";
    private static Configuration freemarkerCfg = null;

    static {
        freemarkerCfg = new Configuration();
        //freemarker的模板目录
        try {
            freemarkerCfg.setDirectoryForTemplateLoading(new File("D:/code/idea/cms/out/production/deya/" + "template"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, DocumentException {
        Map<String, Object> data = new HashMap();
        data.put("s_name", "我县召开第四次全国中药资源普查启动会");
        data.put("description", "我县召开第四次全国中药资源普查启动会我县召开第四次全国中药资源普查启动会，我县召开第四次全国中药资源普查启动会，我县召开第四次全国中药资源普查启动会，我县召开第四次全国中药资源普查启动会");
        data.put("add_time", "2018-05-03");
        data.put("publish_time", "2018-05-03");
        data.put("start_time", "2018-05-03");
        data.put("end_time", "2018-05-03");
        data.put("content", completeHtmlTag("<div id=\"subject_head\" onclick=\"ObjectPool[3808825].showSurveyAttr(this)\"><div id=\"s_name_show\">请输入您的问卷的标题</div><div id=\"s_description_show\">请填写关于此问卷的说明</div><div id=\"show_img_des_div\" onmouseover=\"show_img_desc(this)\" onmouseout=\"this.style.display='none'\"></div></div><div id=\"radioList_1_divs\" sort_num=\"1\" is_required=\"false\" item_id=\"1\" type=\"radioList\" c_least=\"\" c_maximum=\"\" onclick=\"ObjectPool[3808825].setCurrentDesigner(this)\" class=\"disigner_div_default disigner_div_checked\" is_update=\"true\"><div id=\"title_divs\"><div id=\"sort_num\" class=\"sort_num_div\">1.单选题&nbsp;<span id=\"req_span\" class=\"wargin_span\"></span></div><div style=\"CLEAR:both\"></div></div><div id=\"des_items_divs\"><ul id=\"item_ul\"><li class=\"li_css1\"><div id=\"item_img_div\" class=\"item_div_img\"><img onmouseover=\"showImgDescribe(this)\" onmouseout=\"closeImgDescribe()\" src=\"http://35.10.28.126/upload/CMScqgjj/201805/201805091106002.jpg\" width=\"100px\" height=\"120px\"></img><div id=\"item_img_describe\" class=\"item_div_img\"></div></div><div class=\"item_div_img\"><span>是</span></div></li><li class=\"li_css1\"><div><span>否</span></div></li></ul></div><div class=\"blankH5\"></div></div><div id=\"checkboxList_2_divs\" sort_num=\"2\" is_required=\"false\" item_id=\"2\" type=\"checkboxList\" c_least=\"\" c_maximum=\"\" onclick=\"ObjectPool[3808825].setCurrentDesigner(this)\" class=\"disigner_div_default\" is_update=\"true\"><div id=\"title_divs\"><div id=\"sort_num\" class=\"sort_num_div\">2.请在此输入问题标题&nbsp;<span id=\"req_span\" class=\"wargin_span\"></span></div><div style=\"CLEAR:both\"></div></div><div id=\"des_items_divs\"><ul id=\"item_ul\"><li class=\"li_css1\"><div><span>选项1</span></div></li><li class=\"li_css1\"><div><span>选项2</span></div></li></ul></div><div class=\"blankH5\"></div></div><div id=\"selectOnly_3_divs\" sort_num=\"3\" is_required=\"false\" item_id=\"3\" type=\"selectOnly\" c_least=\"\" c_maximum=\"\" onclick=\"ObjectPool[3808825].setCurrentDesigner(this)\" class=\"disigner_div_default\" is_update=\"true\"><div id=\"title_divs\"><div id=\"sort_num\" class=\"sort_num_div\">3.请在此输入问题标题&nbsp;<span id=\"req_span\" class=\"wargin_span\"></span></div><div style=\"CLEAR:both\"></div></div><div id=\"des_items_divs\"><table border=\"0\" style=\"width:98%\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><td>&nbsp;<select id=\"item_3\" name=\"item_3\" class=\"input_select\"><option value=\"\" is_update=\"true\">请选择</option><option value=\"1\" is_update=\"true\">选项1</option><option value=\"2\" is_update=\"true\">选项2</option></select></td></tr></tbody></table></div><div class=\"blankH5\"></div></div><div id=\"matrix_4_divs\" sort_num=\"4\" is_required=\"false\" item_id=\"5\" type=\"matrix\" c_least=\"\" c_maximum=\"\" onclick=\"ObjectPool[3808825].setCurrentDesigner(this)\" class=\"disigner_div_default\" is_update=\"true\"><div id=\"title_divs\"><div id=\"sort_num\" class=\"sort_num_div\">4.请在此输入问题标题&nbsp;<span id=\"req_span\" class=\"wargin_span\"></span></div><div style=\"CLEAR:both\"></div></div><div id=\"des_items_divs\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tbody><tr><td height=\"25px\" width=\"14%\"></td><td align=\"center\" width=\"14%\" score=\"1\" nowrap=\"\">很不满意</td><td align=\"center\" width=\"14%\" score=\"2\" nowrap=\"\">不满意</td><td align=\"center\" width=\"14%\" score=\"3\" nowrap=\"\">一般</td><td align=\"center\" width=\"14%\" score=\"4\" nowrap=\"\">满意</td><td align=\"center\" width=\"14%\" score=\"5\" nowrap=\"\">很满意</td><td width=\"14%\">&nbsp;</td></tr><tr><td class=\"m_td\">外观</td><td align=\"center\"></td><td align=\"center\"></td><td align=\"center\"></td><td align=\"center\"></td><td align=\"center\"></td><td class=\"m_td\"></td></tr><tr><td class=\"m_td\">样式</td><td align=\"center\"></td><td align=\"center\"></td><td align=\"center\"></td><td align=\"center\"></td><td align=\"center\"></td><td class=\"m_td\"></td></tr></tbody></table></div><div class=\"blankH5\"></div></div><div id=\"textareas_5_divs\" sort_num=\"5\" is_required=\"false\" item_id=\"7\" type=\"textareas\" c_least=\"\" c_maximum=\"\" onclick=\"ObjectPool[3808825].setCurrentDesigner(this)\" class=\"disigner_div_default\" is_update=\"true\"><div id=\"title_divs\"><div id=\"sort_num\" class=\"sort_num_div\">5.请在此输入问题标题&nbsp;<span id=\"req_span\" class=\"wargin_span\"></span></div><div style=\"CLEAR:both\"></div></div><div id=\"des_items_divs\"><textarea id=\"item_7\" name=\"item_7\" style=\"width:80%;height:100px\" class=\"input_textarea\"></textarea></div><div class=\"blankH5\"></div></div><div id=\"voteRadio_6_divs\" sort_num=\"6\" is_required=\"false\" item_id=\"8\" type=\"voteRadio\" c_least=\"\" c_maximum=\"\" onclick=\"ObjectPool[3808825].setCurrentDesigner(this)\" class=\"disigner_div_default\" is_update=\"true\"><div id=\"title_divs\"><div id=\"sort_num\" class=\"sort_num_div\">6.请在此输入问题标题&nbsp;<span id=\"req_span\" class=\"wargin_span\"></span></div><div style=\"CLEAR:both\"></div></div><div id=\"des_items_divs\"><table border=\"0\" style=\"width:100%\" cellpadding=\"0\" cellspacing=\"0\" input_type=\"radio\"><tbody><tr><td align=\"left\" width=\"\" height=\"20px\" nowrap=\"\"><span>选项1</span></td><td align=\"left\"><div id=\"pic_pro\" class=\"pro_back\"><div class=\"pro_fore\" style=\"width:20%\"></div></div><div style=\"float:left;padding-top:4px;padding-left:8px;width:120px\"><span id=\"vote_count\"></span><span id=\"vote_pro\"></span></div></td></tr><tr><td align=\"left\" width=\"\" height=\"20px\" nowrap=\"\"><span>选项2</span></td><td align=\"left\"><div id=\"pic_pro\" class=\"pro_back\"><div class=\"pro_fore\" style=\"width:20%\"></div></div><div style=\"float:left;padding-top:4px;padding-left:8px;width:120px\"><span id=\"vote_count\"></span><span id=\"vote_pro\"></span></div></td></tr><tr><td align=\"left\" width=\"\" height=\"20px\" nowrap=\"\"><span>选项3</span></td><td align=\"left\"><div id=\"pic_pro\" class=\"pro_back\"><div class=\"pro_fore\" style=\"width:20%\"></div></div><div style=\"float:left;padding-top:4px;padding-left:8px;width:120px\"><span id=\"vote_count\"></span><span id=\"vote_pro\"></span></div></td></tr><tr><td align=\"left\" width=\"\" height=\"20px\" nowrap=\"\"><span>选项4</span></td><td align=\"left\"><div id=\"pic_pro\" class=\"pro_back\"><div class=\"pro_fore\" style=\"width:20%\"></div></div><div style=\"float:left;padding-top:4px;padding-left:8px;width:120px\"><span id=\"vote_count\"></span><span id=\"vote_pro\"></span></div></td></tr><tr><td align=\"left\" width=\"\" height=\"20px\" nowrap=\"\"><span>选项5</span></td><td align=\"left\"><div id=\"pic_pro\" class=\"pro_back\"><div class=\"pro_fore\" style=\"width:20%\"></div></div><div style=\"float:left;padding-top:4px;padding-left:8px;width:120px\"><span id=\"vote_count\"></span><span id=\"vote_pro\"></span></div></td></tr></tbody></table></div><div class=\"blankH5\"></div></div><div id=\"voteCheckbox_7_divs\" sort_num=\"7\" is_required=\"false\" item_id=\"9\" type=\"voteCheckbox\" c_least=\"\" c_maximum=\"\" onclick=\"ObjectPool[3808825].setCurrentDesigner(this)\" class=\"disigner_div_default\" is_update=\"true\"><div id=\"title_divs\"><div id=\"sort_num\" class=\"sort_num_div\">7.请在此输入问题标题&nbsp;<span id=\"req_span\" class=\"wargin_span\"></span></div><div style=\"CLEAR:both\"></div></div><div id=\"des_items_divs\"><table border=\"0\" style=\"width:100%\" cellpadding=\"0\" cellspacing=\"0\" input_type=\"checkbox\"><tbody><tr><td align=\"left\" width=\"\" height=\"20px\" nowrap=\"\"><span>选项1</span></td><td align=\"left\"><div id=\"pic_pro\" class=\"pro_back\"><div class=\"pro_fore\" style=\"width:20%\"></div></div><div style=\"float:left;padding-top:4px;padding-left:8px;width:120px\"><span id=\"vote_count\"></span><span id=\"vote_pro\"></span></div></td></tr><tr><td align=\"left\" width=\"\" height=\"20px\" nowrap=\"\"><span>选项2</span></td><td align=\"left\"><div id=\"pic_pro\" class=\"pro_back\"><div class=\"pro_fore\" style=\"width:20%\"></div></div><div style=\"float:left;padding-top:4px;padding-left:8px;width:120px\"><span id=\"vote_count\"></span><span id=\"vote_pro\"></span></div></td></tr><tr><td align=\"left\" width=\"\" height=\"20px\" nowrap=\"\"><span>选项3</span></td><td align=\"left\"><div id=\"pic_pro\" class=\"pro_back\"><div class=\"pro_fore\" style=\"width:20%\"></div></div><div style=\"float:left;padding-top:4px;padding-left:8px;width:120px\"><span id=\"vote_count\"></span><span id=\"vote_pro\"></span></div></td></tr><tr><td align=\"left\" width=\"\" height=\"20px\" nowrap=\"\"><span>选项4</span></td><td align=\"left\"><div id=\"pic_pro\" class=\"pro_back\"><div class=\"pro_fore\" style=\"width:20%\"></div></div><div style=\"float:left;padding-top:4px;padding-left:8px;width:120px\"><span id=\"vote_count\"></span><span id=\"vote_pro\"></span></div></td></tr><tr><td align=\"left\" width=\"\" height=\"20px\" nowrap=\"\"><span>选项5</span></td><td align=\"left\"><div id=\"pic_pro\" class=\"pro_back\"><div class=\"pro_fore\" style=\"width:20%\"></div></div><div style=\"float:left;padding-top:4px;padding-left:8px;width:120px\"><span id=\"vote_count\"></span><span id=\"vote_pro\"></span></div></td></tr></tbody></table></div><div class=\"blankH5\"></div></div><div id=\"scale_8_divs\" sort_num=\"8\" is_required=\"false\" item_id=\"10\" type=\"scale\" c_least=\"\" c_maximum=\"\" onclick=\"ObjectPool[3808825].setCurrentDesigner(this)\" class=\"disigner_div_default\" is_update=\"true\"><div id=\"title_divs\"><div id=\"sort_num\" class=\"sort_num_div\">8.请在此输入问题标题&nbsp;<span id=\"req_span\" class=\"wargin_span\"></span></div><div style=\"CLEAR:both\"></div></div><div id=\"des_items_divs\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%\"><tbody><tr><th>很不满意</th><td><ul class=\"scale\" stype=\"img\"><li class=\"scale_li\" title=\"很不满意\" value=\"1\" score=\"1\" id=\"item_10\" is_update=\"true\"></li><li class=\"scale_li\" title=\"不满意\" value=\"2\" score=\"2\" id=\"item_10\" is_update=\"true\"></li><li class=\"scale_li\" title=\"一般\" value=\"3\" score=\"3\" id=\"item_10\" is_update=\"true\"></li><li class=\"scale_li\" title=\"满意\" value=\"4\" score=\"4\" id=\"item_10\" is_update=\"true\"></li><li class=\"scale_li\" title=\"很满意\" value=\"5\" score=\"5\" id=\"item_10\" is_update=\"true\"></li></ul></td><th>很满意</th></tr></tbody></table></div><div class=\"blankH5\"></div></div>",""));
        String content = JavaToPdfHtmlFreeMarker2.freeMarkerRender(data, HTML);
        JavaToPdfHtmlFreeMarker2.createPdf(content, DEST);
    }

    public static void createPdf(String content, String dest) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        // step 3
        document.open();
        // step 4
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontImp.register(FONT);
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new ByteArrayInputStream(content.getBytes()), null, Charset.forName("UTF-8"), fontImp);
        // step 5
        document.close();

    }

    /**
     * freemarker渲染html
     */
    public static String freeMarkerRender(Map<String, Object> data, String htmlTmp) {
        Writer out = new StringWriter();
        try {
            // 获取模板,并设置编码方式
            Template template = freemarkerCfg.getTemplate(htmlTmp);
            template.setEncoding("UTF-8");
            // 合并数据模型与模板
            template.process(data, out); //将合并后的数据和模板写入到流中，这里使用的字符流
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static String completeHtmlTag(String html, String domain) {
//        System.out.println(html);
//        System.out.println("-----------------------------");
        /*html = html.replaceAll("\\.\\./images", "/sys/images");
        html = html.replaceAll("<img(.*?)src=\"/((?!http|hppts).*?)", "<img$1src=\"" + domain + "/");
        html = html.replaceAll("<img(.*?)src=\'/((?!http|hppts).*?)", "<img$1src=\'" + domain + "/");
        String regxpForImgTag = "<img\\s*(.*?)[^>]*?>";
        Pattern pattern = Pattern.compile(regxpForImgTag);
        Matcher matcher = pattern.matcher(html);
        boolean isFirst = true;
        while (matcher.find()) {
            String temp = matcher.group();
            if (temp.indexOf("sys/images/pro_fore.png") >= 0) {
                if (isFirst) {
                    html = html.replaceAll(temp, "");
                    isFirst = false;
                }
            } else {
                html = html.replace(temp, temp.replace(">", "></img>"));
            }
        }

        //去掉调查中的特殊符号，因为不能转为pdf
        String regxpForInputTag = "<input\\s*(.*?)[^>]*?>";
        pattern = Pattern.compile(regxpForInputTag);
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replace(temp, "");
        }
        html = html.replaceAll("<br/>", "<br></br>");
        html = html.replaceAll("5票", "");   //投票单选、多选中去掉票数
        html = html.replaceAll("\\(20%\\)", "");    //投票单选、多选中去掉图片
        html = html.replaceAll("</div><div id=\"title_span\">", "");    //每个题目的标题折行*/
        String regxpForInputTag = "<div id=\"s_name_show\"\\s*(.*?)>(.*?)</div>";
        Pattern pattern = Pattern.compile(regxpForInputTag);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replace(temp, "");
        }

        regxpForInputTag = "<div id=\"s_description_show\"\\s*(.*?)>(.*?)</div>";
        pattern = Pattern.compile(regxpForInputTag);
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replace(temp, "");
        }

        regxpForInputTag = "<ul id=\"item_ul\">(.*?)</ul>";
        pattern = Pattern.compile(regxpForInputTag);
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replace(temp, temp.replaceAll("<ul id=\"item_ul\">","").replaceAll("</ul>",""));
        }

        regxpForInputTag = "<li class=\"li_css1\">(.*?)</li>";
        pattern = Pattern.compile(regxpForInputTag);
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replace(temp, temp.replaceAll("<li class=\"li_css1\">","").replaceAll("</li>",""));
        }
        regxpForInputTag = "<select id=\"(.*?)\" name=\"(.*?)\" class=\"input_select\">(.*?)</select>";
        pattern = Pattern.compile(regxpForInputTag);
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replace(temp, temp.replaceAll("<select id=\"(.*?)\" name=\"(.*?)\" class=\"input_select\">","").replaceAll("</select>",""));
        }
        regxpForInputTag = "<option value=\"(.*?)\" is_update=\"true\">(.*?)</option>";
        pattern = Pattern.compile(regxpForInputTag);
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replace(temp, temp.replaceAll("<option value=\"(.*?)\" is_update=\"true\">","").replaceAll("</option>"," "));
        }
        System.out.println(html);
        System.out.println("-------------------------------");
        return html;
    }


}
