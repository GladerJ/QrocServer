package com.glader.qrocserver.util.mail;

import com.sun.mail.util.MailSSLSocketFactory;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

/**
 * 邮箱发送工具类
 */
public class MailUtils {
    /**
     * 发送验证码
     *
     * @param receiveMail
     * @throws Exception
     */
    public static String sendMail(String receiveMail,String verifyCode) {
        Properties prop = new Properties();
        // 开启debug调试，以便在控制台查看
        prop.setProperty("mail.debug", "true");
        // 设置邮件服务器主机名
        prop.setProperty("mail.host", "smtp.163.com");
        // 发送服务器需要身份验证
        prop.setProperty("mail.smtp.auth", "true");
        // 发送邮件协议名称
        prop.setProperty("mail.transport.protocol", "smtp");
        // 开启SSL加密，否则会失败
        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);
            // 创建session
            Session session = Session.getInstance(prop);
            // 通过session得到transport对象
            Transport ts = session.getTransport();
            // 连接邮件服务器：邮箱类型，帐号，POP3/SMTP协议授权码 163使用：smtp.163.com，qq使用：smtp.qq.com
            ts.connect("smtp.163.com", "glader_goodup@163.com", "NQNXUTJKUJNTNLJU");
            // 创建邮件
            Message message = createSimpleMail(session, receiveMail,verifyCode);
            // 发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        } catch (Exception e) {
            return "failed";
            //e.printStackTrace();
        }
        return "success";
    }

    /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     */
    public static MimeMessage createSimpleMail(Session session, String receiveMail,String verifyCode) throws Exception {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress("glader_goodup@163.com"));
        // 指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveMail));
        // 邮件的标题
        message.setSubject("【Qroc】注册验证");
        // 邮件的文本内容
        message.setContent("您的验证码：" + verifyCode + "，如非本人操作，请忽略！请勿回复此邮箱", "text/html;charset=UTF-8");

        // 返回创建好的邮件对象
        return message;
    }
}

