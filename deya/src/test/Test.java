import com.deya.util.UploadManager;
import gui.ava.html.image.generator.HtmlImageGenerator;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @User: Administrator
 * @Date: 2018/5/8 0008
 */
public class Test {
    public static void main(String[] args) {
        String domain = "http://avatar.csdn.net";
        String html = "<div id=\"subject_head\" onclick=\"ObjectPool[3808825].showSurveyAttr(this)\"><div id=\"s_name_show\">请输入您的问卷的标题</div><div id=\"s_description_show\">请填写关于此问卷的说明</div><input type=\"hidden\" id=\"max_item_num\" name=\"max_item_num\" value=\"11\"><input type=\"hidden\" id=\"max_sort_num\" name=\"max_sort_num\" value=\"9\"><div id=\"show_img_des_div\" onmouseover=\"show_img_desc(this)\" onmouseout=\"this.style.display='none'\"></div></div><div id=\"radioList_1_divs\" sort_num=\"1\" is_required=\"false\" item_id=\"1\" type=\"radioList\" c_least=\"\" c_maximum=\"\" onclick=\"ObjectPool[3808825].setCurrentDesigner(this)\" class=\"disigner_div_default disigner_div_checked\" is_update=\"true\"><div id=\"title_divs\"><div id=\"sort_num\" class=\"sort_num_div\">1.</div><div id=\"title_span\">单选题&nbsp;<span id=\"req_span\" class=\"wargin_span\"></span></div><div style=\"CLEAR:both\"></div></div><div id=\"des_items_divs\"><ul id=\"item_ul\"><li class=\"li_css1\"><div id=\"item_img_div\" class=\"item_div_img\"><img onmouseover=\"showImgDescribe(this)\" onmouseout=\"closeImgDescribe()\" src=\"/upload/CMScqgjj/201805/201805091106002.jpg\" width=\"100px\" height=\"120px\"><div id=\"item_img_describe\" class=\"item_div_img\"></div></div><div class=\"item_div_img\"><input type=\"radio\" class=\"checkbox input_radio\" onclick=\"ObjectPool[3808825].setThisInputCheck(this)\" id=\"item_1\" name=\"item_1\" value=\"1\" is_update=\"true\"><span>是</span></div></li><li class=\"li_css1\"><div><input type=\"radio\" class=\"checkbox input_radio\" onclick=\"ObjectPool[3808825].setThisInputCheck(this)\" id=\"item_1\" name=\"item_1\" value=\"2\" is_update=\"true\"><span>否</span></div></li></ul></div><div class=\"blankH5\"></div></div><div id=\"checkboxList_2_divs\" sort_num=\"2\" is_required=\"false\" item_id=\"2\" type=\"checkboxList\" c_least=\"\" c_maximum=\"\" onclick=\"ObjectPool[3808825].setCurrentDesigner(this)\" class=\"disigner_div_default\" is_update=\"true\"><div id=\"title_divs\"><div id=\"sort_num\" class=\"sort_num_div\">2.</div><div id=\"title_span\">请在此输入问题标题&nbsp;<span id=\"req_span\" class=\"wargin_span\"></span></div><div style=\"CLEAR:both\"></div></div><div id=\"des_items_divs\"><ul id=\"item_ul\"><li class=\"li_css1\"><div><input type=\"checkbox\" class=\"checkbox input_checkbox\" onclick=\"ObjectPool[3808825].setThisInputCheck(this)\" id=\"item_2\" name=\"item_2\" value=\"1\" is_update=\"true\"><span>选项1</span></div></li><li class=\"li_css1\"><div><input type=\"checkbox\" class=\"checkbox input_checkbox\" onclick=\"ObjectPool[3808825].setThisInputCheck(this)\" id=\"item_2\" name=\"item_2\" value=\"2\" is_update=\"true\"><span>选项2</span></div></li></ul></div><div class=\"blankH5\"></div></div><div id=\"selectOnly_3_divs\" sort_num=\"3\" is_required=\"false\" item_id=\"3\" type=\"selectOnly\" c_least=\"\" c_maximum=\"\" onclick=\"ObjectPool[3808825].setCurrentDesigner(this)\" class=\"disigner_div_default\" is_update=\"true\"><div id=\"title_divs\"><div id=\"sort_num\" class=\"sort_num_div\">3.</div><div id=\"title_span\">请在此输入问题标题&nbsp;<span id=\"req_span\" class=\"wargin_span\"></span></div><div style=\"CLEAR:both\"></div></div><div id=\"des_items_divs\"><table border=\"0\" style=\"width:98%\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><td>&nbsp;<select id=\"item_3\" name=\"item_3\" class=\"input_select\"><option value=\"\" is_update=\"true\">请选择</option><option value=\"1\" is_update=\"true\">选项1</option><option value=\"2\" is_update=\"true\">选项2</option></select></td></tr></tbody></table></div><div class=\"blankH5\"></div></div><div id=\"matrix_4_divs\" sort_num=\"4\" is_required=\"false\" item_id=\"5\" type=\"matrix\" c_least=\"\" c_maximum=\"\" onclick=\"ObjectPool[3808825].setCurrentDesigner(this)\" class=\"disigner_div_default\" is_update=\"true\"><div id=\"title_divs\"><div id=\"sort_num\" class=\"sort_num_div\">4.</div><div id=\"title_span\">请在此输入问题标题&nbsp;<span id=\"req_span\" class=\"wargin_span\"></span></div><div style=\"CLEAR:both\"></div></div><div id=\"des_items_divs\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tbody><tr><td height=\"25px\" width=\"14%\"></td><td align=\"center\" width=\"14%\" score=\"1\" nowrap=\"\">很不满意</td><td align=\"center\" width=\"14%\" score=\"2\" nowrap=\"\">不满意</td><td align=\"center\" width=\"14%\" score=\"3\" nowrap=\"\">一般</td><td align=\"center\" width=\"14%\" score=\"4\" nowrap=\"\">满意</td><td align=\"center\" width=\"14%\" score=\"5\" nowrap=\"\">很满意</td><td width=\"14%\">&nbsp;</td></tr><tr><td class=\"m_td\">外观</td><td align=\"center\"><input type=\"radio\" class=\"checkbox input_radio\" id=\"item_5\" name=\"item_5\" value=\"1\" is_update=\"true\"></td><td align=\"center\"><input type=\"radio\" class=\"checkbox input_radio\" id=\"item_5\" name=\"item_5\" value=\"2\" is_update=\"true\"></td><td align=\"center\"><input type=\"radio\" class=\"checkbox input_radio\" id=\"item_5\" name=\"item_5\" value=\"3\" is_update=\"true\"></td><td align=\"center\"><input type=\"radio\" class=\"checkbox input_radio\" id=\"item_5\" name=\"item_5\" value=\"4\" is_update=\"true\"></td><td align=\"center\"><input type=\"radio\" class=\"checkbox input_radio\" id=\"item_5\" name=\"item_5\" value=\"5\" is_update=\"true\"></td><td class=\"m_td\"></td></tr><tr><td class=\"m_td\">样式</td><td align=\"center\"><input type=\"radio\" class=\"checkbox input_radio\" id=\"item_6\"…r><tr><td align=\"left\" width=\"\" height=\"20px\" nowrap=\"\"><input onclick=\"ObjectPool[3808825].setThisInputCheck(this)\" type=\"radio\" id=\"item_8\" name=\"item_8\" value=\"5\" class=\"input_radio\" is_update=\"true\"><span>选项5</span></td><td align=\"left\"><div id=\"pic_pro\" class=\"pro_back\"><div class=\"pro_fore\" style=\"width:20%\"><img height=\"13\" width=\"149px\" alt=\"\" src=\"../images/pro_fore.png\"></div></div><div style=\"float:left;padding-top:4px;padding-left:8px;width:120px\"><span id=\"vote_count\">5票</span><span id=\"vote_pro\">(20%)</span></div></td></tr></tbody></table></div><div class=\"blankH5\"></div></div><div id=\"voteCheckbox_7_divs\" sort_num=\"7\" is_required=\"false\" item_id=\"9\" type=\"voteCheckbox\" c_least=\"\" c_maximum=\"\" onclick=\"ObjectPool[3808825].setCurrentDesigner(this)\" class=\"disigner_div_default\" is_update=\"true\"><div id=\"title_divs\"><div id=\"sort_num\" class=\"sort_num_div\">7.</div><div id=\"title_span\">请在此输入问题标题&nbsp;<span id=\"req_span\" class=\"wargin_span\"></span></div><div style=\"CLEAR:both\"></div></div><div id=\"des_items_divs\"><table border=\"0\" style=\"width:100%\" cellpadding=\"0\" cellspacing=\"0\" input_type=\"checkbox\"><tbody><tr><td align=\"left\" width=\"\" height=\"20px\" nowrap=\"\"><input onclick=\"ObjectPool[3808825].setThisInputCheck(this)\" type=\"checkbox\" id=\"item_9\" name=\"item_9\" value=\"1\" class=\"input_checkbox\" is_update=\"true\"><span>选项1</span></td><td align=\"left\"><div id=\"pic_pro\" class=\"pro_back\"><div class=\"pro_fore\" style=\"width:20%\"><img height=\"13\" width=\"149px\" alt=\"\" src=\"../images/pro_fore.png\"></div></div><div style=\"float:left;padding-top:4px;padding-left:8px;width:120px\"><span id=\"vote_count\">5票</span><span id=\"vote_pro\">(20%)</span></div></td></tr><tr><td align=\"left\" width=\"\" height=\"20px\" nowrap=\"\"><input onclick=\"ObjectPool[3808825].setThisInputCheck(this)\" type=\"checkbox\" id=\"item_9\" name=\"item_9\" value=\"2\" class=\"input_checkbox\" is_update=\"true\"><span>选项2</span></td><td align=\"left\"><div id=\"pic_pro\" class=\"pro_back\"><div class=\"pro_fore\" style=\"width:20%\"><img height=\"13\" width=\"149px\" alt=\"\" src=\"../images/pro_fore.png\"></div></div><div style=\"float:left;padding-top:4px;padding-left:8px;width:120px\"><span id=\"vote_count\">5票</span><span id=\"vote_pro\">(20%)</span></div></td></tr><tr><td align=\"left\" width=\"\" height=\"20px\" nowrap=\"\"><input onclick=\"ObjectPool[3808825].setThisInputCheck(this)\" type=\"checkbox\" id=\"item_9\" name=\"item_9\" value=\"3\" class=\"input_checkbox\" is_update=\"true\"><span>选项3</span></td><td align=\"left\"><div id=\"pic_pro\" class=\"pro_back\"><div class=\"pro_fore\" style=\"width:20%\"><img height=\"13\" width=\"149px\" alt=\"\" src=\"../images/pro_fore.png\"></div></div><div style=\"float:left;padding-top:4px;padding-left:8px;width:120px\"><span id=\"vote_count\">5票</span><span id=\"vote_pro\">(20%)</span></div></td></tr><tr><td align=\"left\" width=\"\" height=\"20px\" nowrap=\"\"><input onclick=\"ObjectPool[3808825].setThisInputCheck(this)\" type=\"checkbox\" id=\"item_9\" name=\"item_9\" value=\"4\" class=\"input_checkbox\" is_update=\"true\"><span>选项4</span></td><td align=\"left\"><div id=\"pic_pro\" class=\"pro_back\"><div class=\"pro_fore\" style=\"width:20%\"><img height=\"13\" width=\"149px\" alt=\"\" src=\"../images/pro_fore.png\"></div></div><div style=\"float:left;padding-top:4px;padding-left:8px;width:120px\"><span id=\"vote_count\">5票</span><span id=\"vote_pro\">(20%)</span></div></td></tr><tr><td align=\"left\" width=\"\" height=\"20px\" nowrap=\"\"><input onclick=\"ObjectPool[3808825].setThisInputCheck(this)\" type=\"checkbox\" id=\"item_9\" name=\"item_9\" value=\"5\" class=\"input_checkbox\" is_update=\"true\"><span>选项5</span></td><td align=\"left\"><div id=\"pic_pro\" class=\"pro_back\"><div class=\"pro_fore\" style=\"width:20%\"><img height=\"13\" width=\"149px\" alt=\"\" src=\"../images/pro_fore.png\"></div></div><div style=\"float:left;padding-top:4px;padding-left:8px;width:120px\"><span id=\"vote_count\">5票</span><span id=\"vote_pro\">(20%)</span></div></td></tr></tbody></table></div><div class=\"blankH5\"></div></div><div id=\"scale_8_divs\" sort_num=\"8\" is_required=\"false\" item_id=\"10\" type=\"scale\" c_least=\"\" c_maximum=\"\" onclick=\"ObjectPool[3808825].setCurrentDesigner(this)\" class=\"disigner_div_default\" is_update=\"true\"><div id=\"title_divs\"><div id=\"sort_num\" class=\"sort_num_div\">8.</div><div id=\"title_span\">请在此输入问题标题&nbsp;<span id=\"req_span\" class=\"wargin_span\"></span></div><div style=\"CLEAR:both\"></div></div><div id=\"des_items_divs\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%\"><tbody><tr><th>很不满意</th><td><ul class=\"scale\" stype=\"img\"><li class=\"scale_li\" title=\"很不满意\" value=\"1\" score=\"1\" id=\"item_10\" is_update=\"true\"></li><li class=\"scale_li\" title=\"不满意\" value=\"2\" score=\"2\" id=\"item_10\" is_update=\"true\"></li><li class=\"scale_li\" title=\"一般\" value=\"3\" score=\"3\" id=\"item_10\" is_update=\"true\"></li><li class=\"scale_li\" title=\"满意\" value=\"4\" score=\"4\" id=\"item_10\" is_update=\"true\"></li><li class=\"scale_li\" title=\"很满意\" value=\"5\" score=\"5\" id=\"item_10\" is_update=\"true\"></li></ul></td><th>很满意</th></tr></tbody></table></div><div class=\"blankH5\"></div></div>";
        html = html.replaceAll("\\.\\./images", "/sys/images");
        html = html.replaceAll("<img(.*?)src=\"/((?!http|hppts).*?)", "<img$1src=\"" + domain + "/");
        html = html.replaceAll("<img(.*?)src=\'/((?!http|hppts).*?)", "<img$1src=\'" + domain + "/");
        System.out.println(html);
        System.out.println("-------------------------------");
        String regxpForImgTag = "<img\\s*(.*?)[^>]*?>";
        Pattern pattern = Pattern.compile(regxpForImgTag);
        Matcher matcher = pattern.matcher(html);
        boolean isFirst = true;
        while (matcher.find()) {
            String temp = matcher.group();
            if (temp.indexOf("sys/images/pro_fore.png") >= 0) {
                if (isFirst) {
                    html = html.replaceAll(temp, temp.replaceAll(">", "></img>"));
                    isFirst = false;
                }
            } else {
                html = html.replace(temp, temp.replace(">", "></img>"));
            }
        }
        System.out.println(html);
        System.out.println("-------------------------------");
        //匹配input标签的正则表达式
        String regxpForInputTag = "<input\\s*(.*?)[^>]*?>";
        pattern = Pattern.compile(regxpForInputTag);
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replace(temp, temp.replace(">", "></input>"));
        }

        html = html.replaceAll("<br/>", "<br></br>");
        System.out.println(html);

        String htmlstr =
                ""+"<body style=\"background:#f4f4f4;margin:0;padding:0;font-size:14px;color:#333;line-height: 1;box-sizing:border-box;\">" +
                        "<div  style=\"box-sizing:border-box;width:980px;padding: 32px 40px;overflow: hidden;margin:0 auto;\">" +
                        "<div style=\"box-sizing:border-box;text-align: right;width:100%;height:16px;font-size: 16px;line-height: 16px;\">住院</div>" +
                        "<div style=\"box-sizing:border-box;width:100%;height:60px;line-height: 1;overflow:hidden;text-align: center;\">" +
                        "<div style=\"box-sizing:border-box;display: inline-block;margin:0 auto;\">" +
                        "<div style=\"box-sizing:border-box;display:block;font-size: 24px;\">首都医科大学附属北京儿童医院</div>" +
                        "<div style=\"box-sizing:border-box;display:block;font-size: 20px;margin-top:16px;\">影像检查诊断报告单</div>" +
                        "</div>" +
                        "</div>" +
                        "<ul id=\"item_ul\"><li class=\"li_css1\"><div id=\"item_img_div\" class=\"item_div_img\"><img onmouseover=\"showImgDescribe(this)\" onmouseout=\"closeImgDescribe()\" src=\"http://35.10.28.126/upload/CMScqgjj/201805/201805091106002.jpg\" width=\"100px\" height=\"120px\"></img><div id=\"item_img_describe\" class=\"item_div_img\"></div></div><div class=\"item_div_img\"><input type=\"radio\" class=\"checkbox input_radio\" onclick=\"ObjectPool[3808825].setThisInputCheck(this)\" id=\"item_1\" name=\"item_1\" value=\"1\" is_update=\"true\"><span>是</span></div></li><li class=\"li_css1\"><div><input type=\"radio\" class=\"checkbox input_radio\" onclick=\"ObjectPool[3808825].setThisInputCheck(this)\" id=\"item_1\" name=\"item_1\" value=\"2\" is_update=\"true\"><span>否</span></div></li></ul>"+
                        "<div style=\"box-sizing:border-box;width:100%;border:solid 2px #000;border-right:none;border-left:none; line-height:28px;height:62px;margin-top:10px;padding:2px 8px;\">" +
                        "<table border=\"0\" style=\"width:100%;border-spacing:0px ; \">" +
                        "<tr style=\"width:100%;height:16px;\" border=\"0\">" +
                        "<td style=\"width:25%;\">姓名：张三丰</td>" +
                        "<td style=\"width:25%;\">性别：男</td>" +
                        "<td style=\"width:25%;\">年龄：4岁</td>" +
                        "<td style=\"width:25%;\">ID号：  0009090990</td>" +
                        "</tr>" +
                        "<tr style=\"width:100%;height:16px;\" border=\"0\">" +
                        "<td style=\"\">科室：内分泌</td>" +
                        "<td style=\"width:25%;\">HIS号：987922</td>" +
                        "<td style=\"width:25%;\">检查类别：MR</td>" +
                        "<td style=\"width:25%;\">检验号：201209090909</td>" +
                        "</tr>" +
                        "</table>" +
                        "</div>" +
                        "<div style=\"min-height:30px;clear:both;overflow:hidden;margin-top:8px;\">" +
                        "<table border=\"0\" style=\"width:100%;border-spacing:0px ; \">" +
                        "<tr style=\"width:100%;height:16px;\" border=\"0\">" +
                        "<td style=\"width:90px;font-size: 16px;line-height:24px;color:#000;font-weight:700;\">检查部位：</td>" +
                        "<td style=\"width:810px;padding-left:8px;line-height:20px;padding-top:2px;\">小腿哦UI电话小腿小腿哦U话小腿哦UI电话</td>" +
                        "</tr>" +
                        "</table>" +
                        "</div>" +
                        "<div style=\"min-height:100px;clear:both;overflow:hidden;margin-top:20px;\">" +
                        "<table border=\"0\" style=\"width:100%;border-spacing:0px ; \">" +
                        "<tr style=\"width:100%;\" border=\"0\" valign=\"top\">" +
                        "<td style=\"width:90px;font-size: 16px;line-height:24px;color:#000;font-weight:700;\">影像表现：</td>" +
                        "<td style=\"width:810px;padding-left:8px;line-height:20px;padding-top:2px;\">小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UIUI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UIUI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话</td>" +
                        "</tr>" +
                        "</table>" +
                        "</div>" +
                        "<div style=\"min-height:100px;clear:both;overflow:hidden;margin-top:30px;\">" +
                        "<table border=\"0\" style=\"width:100%;border-spacing:0px ; \">" +
                        "<tr style=\"width:100%;\" border=\"0\" valign=\"top\">" +
                        "<td style=\"width:90px;font-size: 16px;line-height:24px;color:#000;font-weight:700;\">印象：</td>" +
                        "<td style=\"width:810px;padding-left:8px;line-height:20px;padding-top:2px;\">小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UIUI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UIUI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话小腿哦UI电话</td>" +
                        "</tr>" +
                        "</table>" +
                        "</div>" +
                        "<div style=\"margin-top:20px;height:24px;line-height:24px;\">" +
                        "<table border=\"0\" style=\"width:100%;border-spacing:0px ; \">" +
                        "<tr style=\"width:100%;height:24x;\" border=\"0\">" +
                        "<td style=\"width:36%;\">报告日期：2017-11-22 11:12:09</td>" +
                        "<td style=\"width:34%;\">报告医生：唐小小</td>" +
                        "<td style=\"\">审核医生：李若曦</td>" +
                        "</tr>" +
                        "</table>" +
                        "</div>" +
                        "<div style=\"border-top:solid 2px #000;margin-top:6px;font-size:10px;font-weight:600;line-height:26px;padding-left:10px;\">  " +
                        "注：本报告仅供临床医师参考，影像科医师签字 后生效" +
                        "</div>" +
                        "" +
                        "</div>" +
                        "</body>"
                ;
        try {
//            FileUtil.readAscFile(
            byte[] bytes = HtmlToImageUtils.html2jpeg(Color.white, htmlstr,
                    1300, 1200, new EmptyBorder(0, 0,
                            0, 0));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
