package com.jo0oy.myblog.common.exception.handler;

import com.jo0oy.myblog.common.exception.ErrorCode;
import com.jo0oy.myblog.common.exception.LoginFailException;
import com.jo0oy.myblog.common.exception.ValidationError;
import com.jo0oy.myblog.common.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<?> loginFailException(LoginFailException ex) {
        log.info("handling {}", ex.getClass().toString());
        log.error(ex.getMessage());

        List<ValidationError> errors = new ArrayList<>();
        errors.add(ValidationError.builder()
                .defaultMessage(ex.getMessage())
                .field("globalError").build());

        var error = ResultResponse.Error.builder()
                .errorCode(ErrorCode.AUTH_LOGIN_FAIL.getErrorCode())
                .errorMessage(ErrorCode.AUTH_LOGIN_FAIL.getErrorMsg())
                .errorDetails(errors)
                .build();

        return ResponseEntity.badRequest()
                .body(ResultResponse.fail(ErrorCode.AUTH_LOGIN_FAIL.getErrorMsg(), error));
    }
}
