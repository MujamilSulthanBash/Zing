package com.i2i.zing.exeception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.i2i.zing.common.APIResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        apiResponse.setError(exception.getMessage());
        logger.warn("Error: {}", exception.getMessage(), exception);
        return apiResponse;
    }

    @ExceptionHandler({EntityAlreadyExistsException.class})
    public APIResponse handleEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.CONFLICT.value());
        apiResponse.setError(exception.getMessage());
        logger.warn("Error: {}", exception.getMessage(), exception);
        return apiResponse;
    }

    @ExceptionHandler({RuntimeException.class})
    public APIResponse handleRuntimeException(RuntimeException exception) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiResponse.setError(exception.getMessage());
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
