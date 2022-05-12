package com.jo0oy.myblog.web;

import com.jo0oy.myblog.application.service.posts.PostServiceImpl;
import com.jo0oy.myblog.dto.posts.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostServiceImpl postService;

    @GetMapping("/posts/new")
    public String addPostForm() {
        log.info("get /posts/addPostForm");
        return "/posts/addPostForm";
    }

/*
    @PostMapping("/posts/new")
    public String addPost(@Valid @ModelAttribute("post") AddPostForm form, BindingResult result,
                          RedirectAttributes attributes) {
        log.info("post /post/new");

        if (result.hasErrors()) {
            log.info("errors={}", result.getAllErrors());
            return "/posts/addForm";
        }

        log.info("글 포스팅 성공 로직 실행");

        Long postId = postService.save(form.getTitle(), form.getAuthor(), form.getContent());
        attributes.addAttribute("postId", postId);
        attributes.addAttribute("status", true);

        return "redirect:/posts/{postId}";
    }
*/

    // 글 상세보기
    @GetMapping("/posts/{username}/{postId}")
    public String detailForm(@PathVariable("username") String username,
                             @PathVariable("postId") Long postId, Model model) {
        PostDto.DetailInfo data = postService.postDetail(postId);
        model.addAttribute("post", data);

        return "/posts/post";
    }

    // 게시글 전체보기1 - 테이블
    @GetMapping("/posts/simple-list")
    public String postSimpleList(@PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
            , Model model) {
        model.addAttribute("list", postService.postList(pageable));
        return "/posts/posts";
    }

    // 게시글 전체보기2 - 카드 리스트
    @GetMapping("/posts")
    public String postCardList(@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
            , Model model, @RequestParam(name = "startDate", required = false) String startDate,
                               @RequestParam(name = "endDate", required = false) String endDate,
                               @RequestParam(name = "author", required = false) String author,
                               @RequestParam(name = "title", required = false) String title) {

        PostDto.SearchCondition conditionDto = PostDto.SearchCondition.builder()
                .startDate(startDate)
                .endDate(endDate)
                .author(author)
                .title(title)
                .build();

        model.addAttribute("list", postService.postList(conditionDto.toSearchCondition(), pageable));
        return "/posts/postList";
    }

    // 글 수정하기
    @GetMapping("/posts/{postId}/edit")
    public String editForm(@PathVariable(name = "postId") Long postId, Model model) {
        PostDto.DetailInfo data = postService.postDetail(postId);
        model.addAttribute("post", data);

        return "/posts/editPost";
    }
}
