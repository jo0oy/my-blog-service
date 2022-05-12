package com.jo0oy.myblog.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResultResponse<T> {

    private LocalDateTime timestamp;
    private boolean success;
    private String message;
    private T data;
    private Error<T> error;

    public ResultResponse(final LocalDateTime timestamp,
                          final boolean success,
                          final String message) {
        this.timestamp = timestamp;
        this.success = success;
        this.message = message;
        this.data = null;
        this.error = null;
    }

    public static<T> ResultResponse<T> success(final String message) {
        return success(message, null);
    }

    public static<T> ResultResponse<T> success(final String message, final T t) {
        return ResultResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .message(message)
                .data(t)
                .error(null)
                .build();
    }

    public static <T> ResultResponse<T> fail(final String message) {
        return fail(message, null);
    }

    public static<T> ResultResponse<T> fail(final String message, final Error<T> error) {
        return ResultResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .success(false)
                .message(message)
                .data(null)
                .error(error)
                .build();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Error<T>{
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private int errorCode;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String errorMessage;

        private T errorDetails;

        public Error(final int errorCode, final String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
            this.errorDetails = null;
        }
    }
}
