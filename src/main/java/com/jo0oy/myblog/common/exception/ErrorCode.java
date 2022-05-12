package com.jo0oy.myblog.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    COMMON_INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR.value(), "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    COMMON_INVALID_PARAM(BAD_REQUEST.value(), "유효하지 않은 요청값 입니다. 조건에 맞는 값을 입력하세요."),
    COMMON_ENTITY_NOT_FOUND(BAD_REQUEST.value(), "존재하지 않는 %s 엔티티 입니다. %s = %s"),
    AUTH_LOGIN_FAIL(BAD_REQUEST.value(), "로그인 인증에 실패했습니다. 아이디, 비밀번호를 확인해주세요.");

    private final Integer errorCode;
    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
