package com.jo0oy.myblog.domain.comments;

import com.jo0oy.myblog.domain.BaseTimeEntity;
import com.jo0oy.myblog.domain.members.Member;
import com.jo0oy.myblog.domain.posts.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "comments")
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commentBody;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    @Builder
    public Comment(String commentBody, Post post, Member author) {
        this.commentBody = commentBody;
        this.post = post;
        this.author = author;
    }
}
