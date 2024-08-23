package com.i2i.zing.common;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class APIResponse {
    private Integer status;
    private Object data;
    private Object error;
}
