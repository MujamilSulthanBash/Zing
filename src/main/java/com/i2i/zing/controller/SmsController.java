package com.i2i.zing.controller;

import com.i2i.zing.model.SmsSendRequest;
import com.i2i.zing.service.SmsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class SmsController {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    SmsService smsService;

    @PostMapping("/sendotp")
    public String processSms(@RequestBody SmsSendRequest sendRequest) {
        logger.info("Process Started..{}", sendRequest.toString());
        return smsService.sendSms(sendRequest.getDestinationNumber(), sendRequest.getSmsMessage());
    }
}
