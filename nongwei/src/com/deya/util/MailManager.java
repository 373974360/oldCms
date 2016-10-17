package com.deya.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * 发送Mail邮件.
 * <p>Title: 字符串处理类</p>
 * <p>Description: 字符串处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @version 1.0
 */

public class MailManager {
    private String strTo = ""; //收件人
    private String strFrom = ""; //发送人
    private String strHost = ""; //邮件器
    private String strFilename = ""; //附件文件名
    //private String strMessagetext = ""; //内容
    private String strSubject = ""; //主题
    private String strEncoding = "utf-8"; //发邮件时,使用的编码
    private boolean sendHtmlFormat = false; //是否以HTML格式发送

    private boolean debug = false; //调试信息

    String strMessage = ""; //邮件正文。
    Vector<String> vfile = new Vector<String>(10, 10); //保存发送的文件名
    private static String mailAuthor = null;
    private static String mailAuthorPassword = null;

    static {
        try {
            URL url = MailManager.class.getClassLoader().getResource(
                    "service.properties");
            //没有属性文件
            if (url == null) {
                System.out.println(Encode.gbkToSystem("系统配置错误： 属性文件不存在。"));
            }
            File serviceFile = new File(url.getFile());
           
            Properties prop = new Properties();
            prop.load(new FileInputStream(serviceFile));
            mailAuthor = prop.getProperty("mail.sender");
            mailAuthorPassword = prop.getProperty("mail.auth.password");
            
        }
        catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     *
     * @param strTo          收件人
     * @param strFrom        发送人
     * @param smtpServer     邮件服务器
     * @param strSubject     主题
     * @param strEncoding    发邮件时,使用的编码
     */
    public MailManager(String strTo,
                       String strFrom,
                       String smtpServer,
                       String strSubject,
                       String strEncoding) {
        //初始化收件人、发送人、邮件服务器、主题
        this.strTo = strTo;
        this.strFrom = strFrom;
        this.strHost = smtpServer;
        this.strSubject = strSubject;
        this.strEncoding = strEncoding;
    }

    /**
     *
     * @param strTo          收件人
     * @param strFrom        发送人
     * @param smtpServer     邮件服务器
     * @param strSubject     主题
     * @param strEncoding    发邮件时,使用的编码
     * @param sendHtmlFormat 是否以HTML格式发送邮件
     */
    public MailManager(String strTo,
                       String strFrom,
                       String smtpServer,
                       String strSubject,
                       String strEncoding,
                       boolean sendHtmlFormat) {
        //初始化收件人、发送人、邮件服务器、主题
        this.strTo = strTo;
        this.strFrom = strFrom;
        this.strHost = smtpServer;
        this.strSubject = strSubject;
        this.strEncoding = strEncoding;
        this.sendHtmlFormat = sendHtmlFormat;
    }

    /**
     * 收集附加文件
     */
    public void attachfile(String strFileNameP) {
        vfile.addElement(strFileNameP);
    }

    /**
     * 设置邮件正文
     */
    public void setMessage(String strMsgP) {
        this.strMessage = strMsgP;
    }

    /**
     * 设置调试标志
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     *邮件发送函数
     *@return 返回发送是否成功标志。
     */
    @SuppressWarnings("unchecked")
	public boolean startSend() {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", strHost);
        /* History 2003-11-17  Created by yangft */
        /* 对组件发邮件增加认证 */
        props.put("mail.smtp.auth", "true");
        /* End History 2003-11-17  Created by yangft */

        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(debug);

        /* History 2003-11-14  Created by Sunyi */
        /* 对组件发邮件增加认证 */
        Transport transport = null;
        try {
            transport = session.getTransport("smtp");
            transport.connect(strHost, 25, mailAuthor, mailAuthorPassword);
            
        }
        catch (Exception ex) {
            ex.printStackTrace(System.out);
            return false;
        }
        /* End History 2003-11-14  Created by Sunyi */

        try {
            //创建一个消息,并初始化该消息的各项元素
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(strFrom));
            InternetAddress[] address = {
                    new InternetAddress(strTo)};
            msg.setRecipients(Message.RecipientType.TO, address);

            //Added by sman on 2003-9-13
            if (strSubject != null) {
      
                strSubject = "=?" + this.strEncoding + "?B?" +
                        com.deya.util.MailEncode.encode(strSubject) +
                        "?=";
                
            }
            else {
                strSubject = "";
            }
//      this.strSubject = "=?utf-8?B?5rWL6K+V?=";
            //Added by sman on 2003-9-13

            msg.setSubject(strSubject);

            Multipart mp = new MimeMultipart(); //把message part加入新创建的Multipart

            MimeBodyPart mbp1 = new MimeBodyPart(); //邮件内容的第一部分

            if (sendHtmlFormat) {
                mbp1.setContent(strMessage,
                                "text/html;charset=" + this.strEncoding);
            }
            else {
                mbp1.setText(strMessage, this.strEncoding);
            }

            mp.addBodyPart(mbp1);

            /**
             *以下处理邮件内容的第二部分
             */
            Enumeration efile = vfile.elements();
            while (efile.hasMoreElements()) {
                MimeBodyPart mbp2 = new MimeBodyPart();
                strFilename = efile.nextElement().toString();
                FileDataSource fds = new FileDataSource(strFilename);
                mbp2.setDataHandler(new DataHandler(fds));
//        Debug.debug("附件文件名为:fds.getName()" + fds.getName());
                String strTempFileName = fds.getName();
                try {
                    strTempFileName = new String(strTempFileName.getBytes(
                            "ISO8859-1"), this.strEncoding);
                }
                catch (Exception ex) {
                    ex.printStackTrace(System.out);
                }
//        Debug.debug("附件文件名为:strTempFileName:" + strTempFileName);
                mbp2.setFileName(strTempFileName);
                mp.addBodyPart(mbp2);
            }
            vfile.removeAllElements();
            msg.setContent(mp); // 把MultiPart加入邮件
            msg.setSentDate(new Date()); // 设置邮件头的发送日期
            /* History 2003-11-14  Modified by Sunyi */
            /* 发送邮件增加认证功能 */
            //Transport.send(msg); // 发送邮件      
            try {
                //transport.send(msg);
                transport.sendMessage(msg,
                                      msg.getRecipients(Message.RecipientType.
                        TO));
            }
            catch (MessagingException ex1) {
                ex1.printStackTrace(System.out);
                return false;
            }
            // 发送邮件
            /* End History 2003-11-14  Modified by Sunyi */

        }
        catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
        return true;
    } //end public void startSend()

    /**
     *邮件发送函数（对subject另做编码）
     *@return 返回发送是否成功标志。
     */
    @SuppressWarnings("unchecked")
	public boolean startSend(boolean isEncodingSubject) {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", strHost);
        /* History 2003-11-17  Created by yangft */
        /* 对组件发邮件增加认证 */
        props.put("mail.smtp.auth", "true");
        /* End History 2003-11-17  Created by yangft */
//
        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(debug);

        /* History 2003-11-14  Created by Sunyi */
        /* 对组件发邮件增加认证 */
        Transport transport = null;
        try {
            transport = session.getTransport("smtp");
            transport.connect(strHost, 25, mailAuthor, mailAuthorPassword);
            
        }
        catch (Exception ex) {
            ex.printStackTrace(System.out);
            return false;
        }
        /* End History 2003-11-14  Created by Sunyi */

        try {
            //创建一个消息,并初始化该消息的各项元素
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(strFrom));
            InternetAddress[] address = {
                    new InternetAddress(strTo)};
            msg.setRecipients(Message.RecipientType.TO, address);

            /* History 2004-01-07  Modified by Sunyi */
            if (strSubject != null) {                
                strSubject = Encode.encode(strSubject, this.strEncoding,
                                           System.getProperty("file.encoding"));
                
            }
            else {
                strSubject = "";
            }
            /* End History 2004-01-07  Modified by Sunyi */
//      this.strSubject = "=?utf-8?B?5rWL6K+V?=";
            //Added by sman on 2003-9-13

            msg.setSubject(strSubject);

            Multipart mp = new MimeMultipart(); //把message part加入新创建的Multipart

            MimeBodyPart mbp1 = new MimeBodyPart(); //邮件内容的第一部分

            if (sendHtmlFormat) {
                mbp1.setContent(strMessage,
                                "text/html;charset=" + this.strEncoding);
            }
            else {
                mbp1.setText(strMessage, this.strEncoding);
            }

            mp.addBodyPart(mbp1);

            /**
             *以下处理邮件内容的第二部分
             */
            Enumeration efile = vfile.elements();
            while (efile.hasMoreElements()) {
                MimeBodyPart mbp2 = new MimeBodyPart();
                strFilename = efile.nextElement().toString();
                FileDataSource fds = new FileDataSource(strFilename);
                mbp2.setDataHandler(new DataHandler(fds));
//        Debug.debug("附件文件名为:fds.getName()" + fds.getName());
                String strTempFileName = fds.getName();
                try {
                    strTempFileName = new String(strTempFileName.getBytes(
                            "ISO8859-1"), this.strEncoding);
                }
                catch (Exception ex) {
                    ex.printStackTrace(System.out);
                }
//        Debug.debug("附件文件名为:strTempFileName:" + strTempFileName);
                mbp2.setFileName(strTempFileName);
                mp.addBodyPart(mbp2);
            }
            vfile.removeAllElements();
            msg.setContent(mp); // 把MultiPart加入邮件
            msg.setSentDate(new Date()); // 设置邮件头的发送日期
            /* History 2003-11-14  Modified by Sunyi */
            /* 发送邮件增加认证功能 */
            //Transport.send(msg); // 发送邮件
            try {
                //transport.send(msg);
                transport.sendMessage(msg,
                                      msg.getRecipients(Message.RecipientType.
                        TO));
            }
            catch (MessagingException ex1) {
                ex1.printStackTrace(System.out);
                return false;
            }
            // 发送邮件
            /* End History 2003-11-14  Modified by Sunyi */

        }
        catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
        return true;
    } //end public void startSend(boolean isEncodingSubject)

    public static void main(String args[]) {
        MailManager mail = new MailManager("anlensony@163.net", "yft@cdms.com",
                                           "192.168.60.14", "nihao",
                                           "utf-8");
        mail.setMessage("<font size=\"\" color=\"#FF66FF\">这里是邮件内容</font>");
        mail.setDebug(false);
        boolean isSuccess = mail.startSend();
        if (isSuccess) {
            //Debug.debug("发送成功");
        }
        else {
            //Debug.debug("发送失败");
        }
    }

}