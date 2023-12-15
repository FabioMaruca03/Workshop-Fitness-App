package com.example.fitness.mailing;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
class MailSender extends JavaMailSenderImpl {

}