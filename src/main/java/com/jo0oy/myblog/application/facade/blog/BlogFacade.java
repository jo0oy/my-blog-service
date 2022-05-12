package com.jo0oy.myblog.application.facade.blog;

import com.jo0oy.myblog.application.service.members.MemberService;
import com.jo0oy.myblog.application.service.posts.PostServiceImpl;
import com.jo0oy.myblog.dto.blog.BlogDto;
import com.jo0oy.myblog.dto.members.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class BlogFacade {

    private final PostServiceImpl postService;
    private final MemberService memberService;

    public BlogDto.MainInfo getBlog(Pageable pageable, String username) {
        MemberDto.MemberResponse memberInfo = memberService.memberDetail(username);

        Page<BlogDto.PostSimpleInfo> postList =
                postService.postList(pageable, memberInfo.getMemberId())
                        .map(post
                                -> BlogDto.PostSimpleInfo.builder()
                                .title(post.getTitle())
                                .createdAt(post.getCreatedDate())
                                .lastModifiedAt(post.getLastModifiedDate()).build());

        return BlogDto.MainInfo.builder()
                .blogTitle(memberInfo.getBlogTitle())
                .blogDescription(memberInfo.getBlogDescription())
                .postList(postList)
                .build();
    }
}
