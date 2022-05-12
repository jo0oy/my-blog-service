package com.jo0oy.myblog.application.service.posts;

import com.jo0oy.myblog.domain.posts.Post;
import com.jo0oy.myblog.domain.posts.PostSearchCondition;
import com.jo0oy.myblog.dto.posts.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    PostDto.SaveEditSuccessResponse save(String title, String author, String content);

    PostDto.SaveEditSuccessResponse saveV2(Post post, String author);

    PostDto.DetailInfo postDetail(Long postId);

    Page<PostDto.ListSimpleInfo> postList(Pageable pageable);

    Page<PostDto.ListSimpleInfo> postList(Pageable pageable, Long authorId);

    Page<PostDto.ListSimpleInfo> postList(PostSearchCondition condition, Pageable pageable);

    List<PostDto.ListSimpleInfo> postList();

    Long edit(Long postId, PostDto.UpdateReq requestDto);
}
