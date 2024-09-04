package com.i2i.zing.exception;

/**
 * <p>
 *     Defines the blueprint for exception when invalid token is given
 *     for authorization.
 * </p>
 */
public class UnAuthorizedException extends RuntimeException{
    public UnAuthorizedException(String message) {
        super(message);
    }
}
