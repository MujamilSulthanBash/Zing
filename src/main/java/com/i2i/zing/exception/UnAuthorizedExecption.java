package com.i2i.zing.exception;

/**
 * <p>
 *     Defines the blueprint for exception when invalid token is given
 *     for authorization.
 * </p>
 */
public class UnAuthorizedExecption extends RuntimeException{
    public UnAuthorizedExecption(String message) {
        super(message);
    }
}
