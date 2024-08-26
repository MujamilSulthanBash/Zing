package com.i2i.zing.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsSendRequest {
    private String destinationNumber;
    private String smsMessage;
}
