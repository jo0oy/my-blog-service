package com.jo0oy.myblog.domain.posts;

import com.jo0oy.myblog.domain.BaseTimeEntity;
import com.jo0oy.myblog.domain.members.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "posts")
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Post(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    // 연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
    }

    // 비즈니스 로직 메서드
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
