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
public class JavaToPdfHtmlFreeMarker {
    private static final String DEST = "D:/HelloWorld_CN_HTML_FREEMARKER.pdf";
    private static final String FONT = "font/wryh.ttf";
    private static final String HTML = "article_template.html";
    private static Configuration freemarkerCfg = null;

    static {
        freemarkerCfg = new Configuration();
        //freemarker的模板目录
        try {
            freemarkerCfg.setDirectoryForTemplateLoading(new File(PathUtil.getCurrentPath() + "template"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, DocumentException {
        Map<String, Object> data = new HashMap();
        data.put("top_title", "");
        data.put("title", "我县召开第四次全国中药资源普查启动会");
        data.put("sub_title", "");
        data.put("author", "薛文成");
        data.put("publish_time", "2018-05-03");
        data.put("hits", "2039");
        data.put("content", "<p style=\"text-align: center\"><img src=\"http://www.wubu.gov.cn/upload/CMSwubu/201805/20180503bzocnnf8td.jpg\" title=\"4.jpg\" alt=\"4.jpg\" style=\"\"></img></p><p style=\"text-indent: 2em; text-align: left;\">为全面贯彻落实陕西省第四次全国中药资源普查工作有关精神，实施好我县中药资源普查工作，5月2日，我县在政府三楼会议室召开第四次全国中药资源普查工作启动会。副县长张腊梅主持会议并讲话。参加会议的有县发改局、国土局、农业局、林业局、卫计局、市监局、各镇（街道）、科技局等相关单位负责人及西北农林科技大学生命学院郝文芳教授等一行七人。<br></br></p><p style=\"text-indent: 2em; text-align: left;\">会议宣读了《吴堡县人民政府办公室关于成立吴堡县第四次全国中药资源普查工作领导小组及办公室的通知》。</p><p style=\"text-align: center\"><img src=\"http://www.wubu.gov.cn/upload/CMSwubu/201805/20180503vq3sq72dc6.jpg\" title=\"3.jpg\" alt=\"3.jpg\" style=\"\"></img></p><p style=\"text-indent: 2em; text-align: left;\">郝文芳在会上就此次普查工作的背景、目的、意义、组织管理及后勤保障等，提出了相关的要求和建议。并对下一步如何对中药资源进行普查作了部署，同时希望我县有关部门协助中药普查小组调查。她表示此次普查将完成吴堡县野生药用植物调查(重点品种样方调查、一般品种样线调查)，人工种植药用植物调查(走访、实地)、中药材流通市场调查，中药资源相关传统知识调查等工作。<br></br></p><p style=\"text-align: center\"><img src=\"http://www.wubu.gov.cn/upload/CMSwubu/201805/20180503y7xcfg7u87.jpg\" title=\"2.jpg\" alt=\"2.jpg\" style=\"\"></img></p><p style=\"text-indent: 2em; text-align: left;\">张腊梅指出，中药资源是中医中药持续发展和保障人民健康的重要物质基础，是国家中医药持续发展的基础性和战略性资源，吴堡县作为全国第四次中药资源普查陕西24个试点县之一，通过此次普查，能够让大家更加全面、宏观地掌握我县中药资源的整体情况，将彻底摸清我县中药资源家底，筛选出适宜我县生长，医用价值和经济效益好，同时能够大面积人工栽培的品种，在全县范围内进行推广种植，助力脱贫攻坚，也是发展我县中医药事业的一次难得机会。<br></br></p><p style=\"text-indent: 2em; text-align: left;\">张腊梅要求，一是各相关单位要统一思想、提高认识，深刻领会开展此次中药资源普查工作的重要意义；二是要精心组织、通力协作，保障中药普查工作的顺利推进；三是全力做好此次中药普查工作的后勤保障；四是开展普查工作的同时要做好自身安全保护，做到防患于未然。</p><p style=\"text-align: center\"><img src=\"http://www.wubu.gov.cn/upload/CMSwubu/201805/20180503b1wgwtd39y.jpg\" title=\"1.jpg\" alt=\"1.jpg\" style=\"\"></img></p><p style=\"text-indent: 2em; text-align: left;\"><br></br></p><p style=\"text-indent: 2em; text-align: right;\"><span style=\"font-size: 14px;\"><strong>（责任编辑：尚锴焯）</strong></span></p>");
        String content = JavaToPdfHtmlFreeMarker.freeMarkerRender(data, HTML);
        JavaToPdfHtmlFreeMarker.createPdf(content, DEST);
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


}
