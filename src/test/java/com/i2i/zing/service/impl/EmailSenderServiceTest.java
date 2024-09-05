package com.i2i.zing.service.impl;

import jakarta.persistence.ManyToOne;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailSenderServiceTest {

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    EmailSenderService emailSenderService;

    @Test
    void sendEmailFailure() {
        javaMailSender.send(new SimpleMailMessage());
        emailSenderService.sendEmail("mujamil.official@gmail.com", "Check", "Checking Your Mail");
    }

}