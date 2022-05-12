package com.jo0oy.myblog.domain.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c left join fetch c.author where c.post.id = :postId")
    Optional<Comment> findByPostId(Long postId);

    @Query("select c from Comment c left join fetch c.post where c.author.username = :author")
    Optional<Comment> findByAuthorName(String author);
}
