package com.jo0oy.myblog.domain.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {

    Optional<Post> findOneWithMember(Long postId);

    List<Post> findAllWithMember();

    Page<Post> findAllWithMember(Pageable pageable);

    Page<Post> findAllByAuthorId(Pageable pageable, Long authorId);

    Page<Post> findAllByUsername(Pageable pageable, String username);

    Page<Post> findAllByConditions(Pageable pageable, PostSearchCondition condition);
}
