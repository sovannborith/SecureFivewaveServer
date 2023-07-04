package com.securefivewave.util.email;

import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Component
@RequiredArgsConstructor
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String sendTo, String emailSubject, String emailBody) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setFrom("yun.sovannborith@gmail.com");
        mimeMessageHelper.setTo(sendTo);
        mimeMessageHelper.setSubject(emailSubject);
        mimeMessageHelper.setText(emailBody,true);

        javaMailSender.send(mimeMessage);
    }

}