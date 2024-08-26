package com.i2i.zing.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${TWILIO_ACCOUNT_SID}")
    String accountSid;

    @Value("${TWILIO_AUTH_TOKEN}")
    String authToken;

    @Value("${TWILIO_OUTGOING_SMS_NUMBER}")
    String outGoingNumber;

    @PostConstruct
    private void setup() {
        Twilio.init(accountSid, authToken);
    }
    public String sendSms(String smsNumber, String smsMessage) {
        Message message = Message.creator(
                new PhoneNumber(smsNumber),
                new PhoneNumber(outGoingNumber),
                smsMessage).create();
        return message.getStatus().toString();
    }
}
