package com.jo0oy.myblog.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Getter
public class ValidationError {
    private String field;
    private String defaultMessage;

    public ValidationError(String field, String defaultMessage) {
        this.field = field;
        this.defaultMessage = defaultMessage;
    }
}
