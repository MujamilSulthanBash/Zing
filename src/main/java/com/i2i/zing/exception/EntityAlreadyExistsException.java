package com.i2i.zing.exception;

/**
 * <p>
 *     Defines the blueprint for exception when fields which are already available,
 *     but given as a input to insert
 * </p>
 */
public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}