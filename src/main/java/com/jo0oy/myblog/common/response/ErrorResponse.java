package com.jo0oy.myblog.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Builder
public class ErrorResponse<T> {
    private LocalDateTime timestamp;
    private boolean success;
    private int status;
    private String message;
    private T error;

    public ErrorResponse(final LocalDateTime timestamp, final boolean success,
                         final int status, final String message, final T error) {
        this.timestamp = timestamp;
        this.success = success;
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public static<T> ErrorResponse<T> error(final LocalDateTime timestamp, final int status, final String message) {
        return error(timestamp, status, message, null);
    }

    public static<T> ErrorResponse<T> error(final LocalDateTime timestamp, final int status, final String message, final T t) {
        return ErrorResponse.<T>builder()
                .error(t)
                .timestamp(timestamp)
                .success(false)
                .status(status)
                .message(message)
                .build();
    }
}
