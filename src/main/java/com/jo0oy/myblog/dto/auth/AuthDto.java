package com.jo0oy.myblog.dto.auth;

import lombok.Builder;
import lombok.Getter;

public class AuthDto {

    @Getter
    public static class LoginReq {
        private String username;
        private String password;

        @Builder
        public LoginReq(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    @Getter
    public static class LoginResponse {
        private String username;

        @Builder
        public LoginResponse(String username) {
            this.username = username;
        }
    }
}
