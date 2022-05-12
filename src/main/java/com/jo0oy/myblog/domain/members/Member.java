package com.jo0oy.myblog.domain.members;

import com.jo0oy.myblog.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "members")
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String password;

    private String name;

    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private BlogFragment blogFragment;

    @Builder
    public Member(String username, String password, String name,
                  String email, String phoneNumber, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.blogFragment = BlogFragment.builder()
                .blogTitle(username + "'s 블로그")
                .blogDescription(username + "의 블로그에 오신 걸 환영합니다!")
                .build();
    }

    public void setEncodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void update(String password, String name, String email, String phoneNumber) {
        if(StringUtils.hasText(password)) {
            this.password = password;
        }
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void updateBlogFragment(String blogTitle, String blogDescription) {
        this.blogFragment = BlogFragment.builder()
                .blogTitle(blogTitle)
                .blogDescription(blogDescription)
                .build();
    }
}
