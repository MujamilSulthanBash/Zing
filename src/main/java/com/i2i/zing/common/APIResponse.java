package com.i2i.zing.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * Represents blueprint for the APIResponse.
 * Contains status of the api, data of the api, error of the api.
 * </p>
 */
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
