package com.i2i.zing.exeception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.i2i.zing.common.APIResponse;

/**
 * <p>
 *     Contains all handlers for the exception thrown across the application
 *     using exception handlers and returns respective http status.
 *     e.g., NOT_FOUND, CONFLICT.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger();

    @ExceptionHandler({EntityNotFoundException.class})
    public APIResponse handleEntityNotFoundException(EntityNotFoundException exception) {
        APIResponse apiResponse = new APIResponse();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("NOT_FOUND");
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setErrorMessage(exception.getMessage());
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        apiResponse.setError(exceptionResponse);
        logger.warn("Error: {}", exception.getMessage(), exception);
        return apiResponse;
    }

    @ExceptionHandler({EntityAlreadyExistsException.class})
    public APIResponse handleEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
        APIResponse apiResponse = new APIResponse();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("CONFLICT");
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setErrorMessage(exception.getMessage());
        apiResponse.setStatus(HttpStatus.CONFLICT.value());
        apiResponse.setError(exceptionResponse);
        logger.warn("Error: {}", exception.getMessage(), exception);
        return apiResponse;
    }

    @ExceptionHandler({RuntimeException.class})
    public APIResponse handleRuntimeException(RuntimeException exception) {
        APIResponse apiResponse = new APIResponse();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("INTERNAL_SERVER_ERROR");
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setErrorMessage(exception.getMessage());
        apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiResponse.setError(exceptionResponse);
        logger.warn("Error: {}", exception.getMessage(), exception);
        return apiResponse;
    }

    @ExceptionHandler({UnAuthorizedExecption.class})
    public APIResponse handleUnAuthorizedException(UnAuthorizedExecption exception) {
        APIResponse apiResponse = new APIResponse();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("UNAUTHORIZED");
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setErrorMessage(exception.getMessage());
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        apiResponse.setError(exceptionResponse);
        logger.warn("Error: {}", exception.getMessage(), exception);
        return apiResponse;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        errorMap.put("url", String.valueOf(req.getRequestURI()));
        errorMap.put("TimeStamp", String.valueOf(LocalDateTime.now()));
        return errorMap;
    }
}
