import com.deya.util.UploadManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @User: Administrator
 * @Date: 2018/5/8 0008
 */
public class Test {
    public static void main(String[] args) {
        String files[] = {".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".rar", ".zip", ".pdf", ".txt"};
        String domain = "http://avatar.csdn.net";
        String html = "<p style=\"text-align: center\"><img src=\"/upload/CMScqgjj/201805/20180508djodbx59ml.jpg\" alt=\"20180508djodbx59ml.jpg\"/></p><p style=\"text-indent: 2em;\">渝公积金发〔2016〕65号</p><p style=\"text-align: left; text-indent: 2em;\">各分中心、办事处、处室，公积金贷款承办银行：</p><p style=\"text-align: left; text-indent: 2em;\">为加强住房公积金二手房贷款管理，规范高效办理住房公积金二手房贷款，根据国家政策和相关金融法规，住房公积金个人住房贷款相关政策规定，现将《重庆市住房公积金二手房贷款管理办法》印发给你们，请遵照执行。</p><p style=\"text-align: left; text-indent: 2em;\">本管理办法自印发之日起执行，原《重庆市住房公积金二手房贷款办法》（渝公积金发〔2004〕427号）停止执行。</p><p style=\"text-align: right; text-indent: 2em;\">重庆市住房公积金管理中心</p><p style=\"text-align: right; text-indent: 2em;\">2016年8月23日</p><p style=\"text-align: center; text-indent: 2em;\">重庆市住房公积金二手房贷款管理办法</p><p style=\"text-align: center; text-indent: 2em;\">第一章 总 则</p><p style=\"text-align: left; text-indent: 2em;\">第一条 \n" +
                "根据国务院《住房公积金管理条例》，中国人民银行《贷款通则》、《个人住房贷款管理办法》，《重庆市住房公积金管理中心关于住房公积金个人住房贷款政策及相关规定的通知》（渝公积金发〔2016〕6号）的相关规定制定本办法。</p><p style=\"text-align: left; text-indent: 2em;\">第二条 \n" +
                "本办法所称二手房是指产权关系明晰，已依法取得《房地产权证》（或《房屋所有权证》和《国有土地使用证》）或《不动产权证书》，可进入住房二级市场自由交易的成套住宅类房屋；所称售房人是指上述权证中载明的房屋权利人。</p><p style=\"text-align: left; text-indent: 2em;\">第三条 \n" +
                "本办法所称二手房贷款是指重庆市住房公积金管理中心（以下简称“公积金中心”）运用住房公积金资金，委托承办公积金贷款的商业银行（以下简称“承办银行”）向符合公积金贷款条件，在我市城镇购买再交易住房的个人发放的住房公积金二手房贷款（以下简称“二手房贷款”）。</p><p style=\"text-align: left; text-indent: 2em;\">在公积金贷款额度不能满足借款人购房需求时，由承办银行运用自有资金，向同时符合承办银行自营性个人住房贷款条件的个人发放的住房公积金二手房组合贷款。</p><p style=\"text-align: left; text-indent: 2em;\">第四条 \n" +
                "本办法适用于公积金中心及其委托的承办银行网点（以下简称“经办网点”）为借款人办理二手房贷款。</p><p style=\"text-align: center; text-indent: 2em;\">第二章 \n" +
                "贷款对象和条件</p><p style=\"text-align: left; text-indent: 2em;\">第五条 贷款对象</p><p style=\"text-align: left; text-indent: 2em;\">二手房贷款对象为申请贷款前6个月（含）及以上按时足额连续缴存住房公积金（含异地缴存），有稳定的经济收入，具备按期偿还贷款本息的能力和意愿，资信良好，具有完全民事行为能力的个人。</p><p style=\"text-align: left; text-indent: 2em;\">申请二手房贷款借款人的配偶，在借款人申请贷款前6个月（含）及以上按时足额连续缴存住房公积金（含异地缴存），具有完全民事行为能力的，可以共同参与贷款（以下简称“参贷”），配偶参贷后视同为二手房贷款的共同借款人。</p><p style=\"text-align: left; text-indent: 2em;\">缴存职工家庭（仅指缴存职工夫妻双方，下同）属于下列情形之一的，购买二手房时不得申请二手房贷款：</p><p style=\"text-align: left; text-indent: 2em;\">（一）职工家庭名下拥有二套及以上住房，或缴存职工夫妻任一方有未结清的公积金贷款余额。</p><p style=\"text-align: left; text-indent: 2em;\">（二）已有两次公积金贷款记录，且不能证明已不再拥有贷款所购住房。</p><p style=\"text-align: left; text-indent: 2em;\">（三）有两笔及以上未结清的个人住房商业贷款余额。</p><p style=\"text-align: left; text-indent: 2em;\">第六条 贷款条件</p><p style=\"text-align: left; text-indent: 2em;\">申请二手房贷款的借款人应具备以下条件：</p><p style=\"text-align: left; text-indent: 2em;\">（一）有在本市城镇购买二手房的真实交易行为；</p><p style=\"text-align: left; text-indent: 2em;\">（二）交易的二手房具有合法有效的《房地产权证》（或《房屋所有权证》和《国有土地使用证》）或《不动产权证书》；</p><p style=\"text-align: left; text-indent: 2em;\">（三）同意用所购住房作为贷款抵押担保；</p><p style=\"text-align: left; text-indent: 2em;\">（四）已与售房人签订《房地产买卖合同》，并有不低于所购房屋价格20%( \n" +
                "含)以上的自筹购房资金；</p><p style=\"text-align: left; text-indent: 2em;\">（五）公积金中心规定的其他条件。</p><p style=\"text-align: center; text-indent: 2em;\">第三章 \n" +
                "贷款额度、期限和利率</p><p style=\"text-align: left; text-indent: 2em;\">第七条 贷款成数及额度</p><p style=\"text-align: left; text-indent: 2em;\">（一）贷款成数</p><p style=\"text-align: left; text-indent: 2em;\">二手房贷款最高可贷成数为所购二手房评估价值和实际成交金额二者中较低者的80%。</p><p style=\"text-align: left; text-indent: 2em;\">（二）贷款额度</p><p style=\"text-align: left; text-indent: 2em;\">二手房贷款个人公积金最高可贷额度不得超过我市公积金管委会规定的最高可贷额度，参贷后缴存职工家庭最高可贷额度不超过我市公积金管委会规定的最高可贷额度的规定倍数。实际可贷额度不超过我市公积金管委会规定的借款人及配偶公积金账户余额之和的一定倍数，借款人家庭月负债之和(含本次申请的贷款)不得超过其家庭月收入的50%。</p><p style=\"text-align: left; text-indent: 2em;\">第八条 贷款期限</p><p style=\"text-align: left; text-indent: 2em;\">二手房贷款最长期限为30年，且贷款期限加借款人申请贷款时实际年龄不超过法定退休年龄后10年。</p><p style=\"text-align: left; text-indent: 2em;\">第九条 贷款利率</p><p style=\"text-align: left; text-indent: 2em;\">贷款利率按照二手房贷款发放当日人民银行公布的公积金贷款挂牌利率执行，并按借款合同约定的调整方式进行调整。</p><p style=\"text-align: center; text-indent: 2em;\">第四章 \n" +
                "贷款办理程序</p><p style=\"text-align: left; text-indent: 2em;\">第十条 房屋价值评估</p><p style=\"text-align: left; text-indent: 2em;\">办理二手房贷款的房屋价值，需经公积金中心认可的房地产价格评估机构（以下简称“评估机构”）进行评估，并取得《房地产价格评估报告》。</p><p style=\"text-align: left; text-indent: 2em;\">第十一条 贷款申请和受理</p><p style=\"text-align: left; text-indent: 2em;\">（一）借款人申请办理二手房贷款，可自行或委托他人携申请资料到经办网点办理，并如实、认真填写《公积金个人住房贷款申请审批表》。</p><p style=\"text-align: left; text-indent: 2em;\">（二）经办网点受理人员核验借款人提交的申请资料要件齐全有效的，应当场出具受理回执。</p><p style=\"text-align: left; text-indent: 2em;\">借款人需提供的申请资料：</p><p style=\"text-align: left; text-indent: 2em;\">1.借款人及配偶的有效身份证明（或其他有效居留证件）和婚姻证明；</p><p style=\"text-align: left; text-indent: 2em;\">2.售房人的有效身份证明（或其他有效居留证件）；</p><p style=\"text-align: left; text-indent: 2em;\">3.借款人家庭的收入证明（不提供的按住房公积金缴存基数认定其收入）；</p><p style=\"text-align: left; text-indent: 2em;\">4.所购二手房《房地产权证》（或《房屋所有权证》和《国有土地使用证》）或《不动产权证书》；</p><p style=\"text-align: left; text-indent: 2em;\">5.借款人与售房人签订的《房地产买卖合同》；</p><p style=\"text-align: left; text-indent: 2em;\">6.借款人已支付所购二手房除贷款金额以外的自筹资金凭据（可在贷款发放前补充提供）；</p><p style=\"text-align: left; text-indent: 2em;\">7.《贷款资金划款授权书》；</p><p style=\"text-align: left; text-indent: 2em;\">8.《房地产价格评估报告》。</p><p style=\"text-align: left; text-indent: 2em;\">第十二条 贷款初审</p><p style=\"text-align: left; text-indent: 2em;\">经办网点工作人员受理借款人二手房贷款申请后，应在5个工作日内按照公积金个人住房贷款相关规定完成贷款初审。经初审符合二手房贷款条件的，通知借款人及配偶、售房人及其他购房产权人（含其配偶）到承办银行办理贷款签约手续；经初审不符合二手房贷款条件的，应将初审未通过原因告知借款人。</p><p style=\"text-align: left; text-indent: 2em;\">第十三条 \n" +
                "资料核验及合同面签</p><p style=\"text-align: left; text-indent: 2em;\">借款人及配偶、售房人及其他购房产权人（含其配偶）到承办银行办理贷款签约手续，承办银行工作人员应核验相关资料原件，经核验提交申请的复印件资料与原件无异的，严格按照贷款面谈面签制度的规定要求，为其办理借款合同和抵押合同及相关文本签订手续；经核验提交申请的复印件资料与原件有异的，工作人员不得为借款人办理签约手续，终止贷款办理并将借款人申请资料退回经办网点。</p><p style=\"text-align: left; text-indent: 2em;\">第十四条 \n" +
                "贷款复审及合同签章</p><p style=\"text-align: left; text-indent: 2em;\">借款人完成二手房贷款合同面签审核后，承办银行应严格按照个人住房贷款的相关规定，在5个工作日内完成贷款复审及合同签章，并由经办网点工作人员通知借款人领取办理抵押登记所需相关资料。</p><p style=\"text-align: left; text-indent: 2em;\">第十五条 \n" +
                "办理房屋交易和抵押登记手续</p><p style=\"text-align: left; text-indent: 2em;\">借款人在经办网点领取办理抵押所需资料后，自行到房屋所在地不动产登记机构办理二手房交易和抵押登记手续。借款人向不动产登记机构提交的交易资料应与申请二手房贷款提交的资料保持一致，如出现差异，公积金中心或承办银行有权不予发放二手房贷款。</p><p style=\"text-align: left; text-indent: 2em;\">第十六条 贷款发放</p><p style=\"text-align: left; text-indent: 2em;\">（一）抵押核实</p><p style=\"text-align: left; text-indent: 2em;\">借款人将已办妥的二手房交易和抵押登记资料交回经办网点后，经办网点应在5个工作日内完成对交易及抵押登记真实性的核实。由公积金中心或承办银行工作人员在不动产登记机构直接或见证收取抵押登记资料的，视为已核实抵押登记真实性。</p><p style=\"text-align: left; text-indent: 2em;\">（二）贷款发放</p><p style=\"text-align: left; text-indent: 2em;\">公积金中心和承办银行应在二手房贷款交易及抵押登记真实性核实后5个工作日内完成贷款的发放。</p><p style=\"text-align: center; text-indent: 2em;\">第五章 \n" +
                "其他规定</p><p style=\"text-align: left; text-indent: 2em;\">第十七条 \n" +
                "二手房贷款档案、贷后等管理，按公积金贷款现行管理相关规定执行。</p><p style=\"text-align: left; text-indent: 2em;\">第十八条 \n" +
                "本管理办法涉及的二手房贷款相关政策规定，随国家和我市住房公积金贷款政策规定调整。</p><p style=\"text-align: left; text-indent: 2em;\">第十九条 本管理办法由重庆市住房公积金管理中心负责解释。</p><p style=\"text-align: right; text-indent: 2em;\">重庆市住房公积金管理中心</p><p style=\"line-height: 16px;\"><img style=\"vertical-align: middle; margin-right: 2px;\" src=\"/sys/ueditor/dialogs/attachment/fileTypeImages/icon_txt.gif\"/><a style=\"font-size:12px; color:#0066cc;\" href=\"/upload/CMScqgjj/201805/20180508wb0xqsv43l.xlsx\" title=\"20180508wb0xqsv43l.xlsx\">20180508wb0xqsv43l.xlsx</a></p><p style=\"text-align: right; text-indent: 2em;\">2016年8月23日印发<br/></p>";
        System.out.println(html);
        html = html.replaceAll("<img(.*?)src=\"((?!http|hppts).*?)", "<img$1src=\"" + domain);
        System.out.println("-------------------------------");
        System.out.println(html);

        //匹配img标签的正则表达式
        String regxpForImgTag = "href=\"([^\"]*)\"";
        Pattern pattern = Pattern.compile(regxpForImgTag);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group(1);
            String suffix = temp.substring(temp.lastIndexOf("."), temp.length());
            for (String file : files) {
                if (suffix.toLowerCase().equals(file)) {
                    //String uploadPath = UploadManager.getUploadFilePath("site_id") + "/";
                }
            }
            System.out.println(temp + "---------------" + suffix);
        }
    }
}
