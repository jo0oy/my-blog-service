package com.jo0oy.myblog.common.exception.handler;

import com.jo0oy.myblog.common.exception.ErrorCode;
import com.jo0oy.myblog.common.exception.ValidationError;
import com.jo0oy.myblog.common.response.ResultResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        System.out.println(ex.getBindingResult().getFieldErrors());
        List<ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> new ValidationError(x.getField(), x.getDefaultMessage()))
                .collect(Collectors.toList());

        return ResponseEntity.badRequest()
                .body(ResultResponse.fail(
                        ErrorCode.COMMON_INVALID_PARAM.getErrorMsg(),
                        ResultResponse.Error.builder().errorDetails(errors).build()));
    }
}

