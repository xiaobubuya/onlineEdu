package com.xiaobubuya.msm.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.xiaobubuya.msm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @Author: xiaobubuya
 * @Description:
 * @Date Created in 2021-06-22 15:11
 * @Modified By:
 */
@Service
public class MsmServiceImpl implements MsmService {

    /**
     * 发送短信
     */
    public boolean send(String PhoneNumbers, String templateCode, Map<String,Object> param) {

        if(StringUtils.isEmpty(PhoneNumbers)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "", "");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        request.putQueryParameter("PhoneNumbers", PhoneNumbers);
        request.putQueryParameter("SignName", "我的谷粒在线教育网站");
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 发送邮箱
    @Override
    public boolean sendEmailCode(String email, String code) {
        try {
            //创建Properties 类用于记录邮箱的一些属性
            final Properties props = new Properties();
            //表示SMTP发送邮件，必须进行身份验证
            props.put("mail.smtp.auth", "true");
            //此处填写SMTP服务器
            props.put("mail.smtp.host", "smtp.qq.com");
            //端口号，QQ邮箱给出了两个端口，这里给出587
            props.put("mail.smtp.port", "587");
            //此处填写你的账号
            props.put("mail.user", "424102378@qq.com");
            //此处的密码就是前面说的16位STMP口令
            //获取口令
            props.put("mail.password", "pqxgazwltgfvbgia");
            //构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            //使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            //创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            //设置发件人
            InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
            message.setFrom(form);

            //设置收件人的邮箱
            InternetAddress to = new InternetAddress(email);
            message.setRecipient(Message.RecipientType.TO, to);

            //设置邮件主题
            message.setSubject("小不不学院登录注册验证");
            //设置消息日期
            message.setSentDate(new Date());

            //html文件
            StringBuilder sb = new StringBuilder();
            sb.append("<h1>小不不学院验证码</h1>"+"<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"你好：</p>"+"<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;你的验证码是"+ code +"你可凭此验证码登录。</p>"+"<h3>小不不学院发</h3>");
            //设置邮件的内容体
            message.setContent(sb.toString(), "text/html;charset=UTF-8");

            //最后当然就是发送邮件
            Transport.send(message);
            System.out.println("发送成功！");
            return true;

        } catch (MessagingException e) {
            System.out.println("发送失败！"+e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
