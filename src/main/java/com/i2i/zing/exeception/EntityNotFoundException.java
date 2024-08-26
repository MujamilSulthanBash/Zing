package com.i2i.zing.exeception;

/**
 * <p>
 *     Defines the blueprint for exception when fields which are not available
 *     given as a input.
 * </p>
 */
public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }
}