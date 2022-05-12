package com.jo0oy.myblog.web.api;

import com.jo0oy.myblog.application.service.posts.PostService;
import com.jo0oy.myblog.common.response.ResultResponse;
import com.jo0oy.myblog.domain.posts.PostSearchCondition;
import com.jo0oy.myblog.dto.posts.PostDto;
import com.jo0oy.myblog.security.AuthMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    // 글 작성
    @PostMapping("/api/v1/posts")
    public ResponseEntity<?> save(@Valid @RequestBody PostDto.SaveReq requestDto, @AuthenticationPrincipal AuthMember authMember) {
        log.info("/api/v1/posts 호출");
        PostDto.SaveEditSuccessResponse data = postService.saveV2(requestDto.toEntity(), authMember.getUsername());

        return ResponseEntity.created(URI.create("/api/v1/posts"))
                .body(ResultResponse.success("글 등록이 정상적으로 완료되었습니다.", data));
    }

    // 전체 글 리스트 조회
    @GetMapping("/api/v1/posts")
    public ResponseEntity<?> postList(PostSearchCondition condition,
                                      @PageableDefault(page = 0, size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("/api/v1/posts 호출");
        log.info("conditions={}", condition);

        Page<PostDto.ListSimpleInfo> data = postService.postList(condition, pageable);

        return ResponseEntity.ok(ResultResponse.success("전체 글 조회 성공", data));
    }

    // 글 수정
    @PutMapping("/api/v1/posts/{postId}")
    private ResponseEntity<?> editPost(@PathVariable(name = "postId") Long postId,
                                       @Valid @RequestBody PostDto.UpdateReq requestDto,
                                       @AuthenticationPrincipal AuthMember authMember) {
        log.info("/api/v1/posts/{} 호출", postId);

        Long editedPostId = postService.edit(postId, requestDto);

        return ResponseEntity
                .ok(ResultResponse.success("글 수정이 정상적으로 완료되었습니다.", new PostDto.SaveEditSuccessResponse(authMember.getUsername(), editedPostId)));
    }

    // 검색 조회
    // spring security csrf.disable() 선처리 필요!
    @PostMapping("/api/v1/posts/search")
    public ResponseEntity<?> searchPosts(@RequestBody PostDto.SearchCondition request,
                                         @PageableDefault(page = 0, size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("/api/v1/posts/search 호출...");
        log.info("searchCondition = {}", request.toSearchCondition());

        Page<PostDto.ListSimpleInfo> resultData = postService.postList(request.toSearchCondition(), pageable);


        return ResponseEntity.ok(ResultResponse.success("글 검색이 정상적으로 완료되었습니다.", resultData));
    }
}
