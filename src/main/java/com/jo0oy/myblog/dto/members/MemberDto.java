package com.jo0oy.myblog.dto.members;

import com.jo0oy.myblog.domain.members.Member;
import com.jo0oy.myblog.domain.members.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

public class MemberDto {

    @NoArgsConstructor
    @Getter
    public static class SaveReq {
        @NotBlank
        @Size(min = 4, max = 20)
        private String username;

        @NotBlank
        @Size(min = 8, max = 20)
        private String password;

        @NotEmpty
        private String name;

        @NotBlank
        @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
        private String email;

        @NotBlank
        @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
        private String phoneNumber;

        @Builder
        public SaveReq(String username, String password, String name,
                                 String email, String phoneNumber) {
            this.username = username;
            this.password = password;
            this.name = name;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }

        public Member toEntity() {
            return Member.builder()
                    .username(username)
                    .password(password)
                    .name(name)
                    .phoneNumber(phoneNumber)
                    .email(email)
                    .role(Role.USER)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateReq {
        @Size(min = 8, max = 20, message = "{Size.member.password}")
        private String password;

        @NotEmpty
        private String name;

        @NotBlank
        @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
                , message = "{Pattern.member.email}")
        private String email;

        @NotBlank
        @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "{Pattern.member.phoneNumber}")
        private String phoneNumber;

        @Builder
        public UpdateReq(String password, String name,
                                   String email, String phoneNumber) {
            this.password = password;
            this.name = name;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }
    }

    @NoArgsConstructor
    @Getter
    public static class MemberResponse {
        private Long memberId;
        private String username;
        private String name;
        private String email;
        private String phoneNumber;
        private String blogTitle;
        private String blogDescription;

        public MemberResponse(Member entity) {
            this.memberId = entity.getId();
            this.username = entity.getUsername();
            this.name = entity.getName();
            this.email = entity.getEmail();
            this.phoneNumber = entity.getPhoneNumber();
            this.blogTitle = entity.getBlogFragment().getBlogTitle();
            this.blogDescription = entity.getBlogFragment().getBlogDescription();
        }
    }
}
