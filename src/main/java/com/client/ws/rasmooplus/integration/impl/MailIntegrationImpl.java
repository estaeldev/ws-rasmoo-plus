package com.client.ws.rasmooplus.integration.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.client.ws.rasmooplus.integration.MailIntegration;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailIntegrationImpl implements MailIntegration {

    private final JavaMailSender javaMailSender;
    
    @Override
    public void send(String mailTo, String message, String subject) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mailTo);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);
    }
    
}
