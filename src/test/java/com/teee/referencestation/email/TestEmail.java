package com.teee.referencestation.email;

import com.teee.referencestation.ReferenceStationApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReferenceStationApplication.class)
public class TestEmail {

    // 获取JavaMailSender bean
    @Autowired
    private JavaMailSender javaMailSender;

    // 获取配置文件的username
    @Value("${spring.mail.from}")
    private String from;

    /**
     * 一个简单的邮件发送
     */
    @Test
    public void sendSimpleMail1(){
        SimpleMailMessage message = new SimpleMailMessage();
        // 设定邮件参数
        message.setFrom(from); //发送者
        message.setTo("3325528806@qq.com"); //接受者
        message.setSubject("主题:邮件"); //主题
        message.setText("邮件内容"); //邮件内容

        // 发送邮件
        javaMailSender.send(message);
    }
}
