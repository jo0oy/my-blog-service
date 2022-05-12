package com.jo0oy.myblog.application.service.posts;

import com.jo0oy.myblog.domain.members.Member;
import com.jo0oy.myblog.domain.members.MemberRepository;
import com.jo0oy.myblog.domain.posts.Post;
import com.jo0oy.myblog.domain.posts.PostRepository;
import com.jo0oy.myblog.domain.posts.PostSearchCondition;
import com.jo0oy.myblog.domain.uploadfile.UploadFileRepository;
import com.jo0oy.myblog.dto.posts.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final UploadFileRepository uploadFileRepository;

    /**
     * 글 작성
     * @param title : String
     * @param author : String
     * @param content : String
     * @return PostSaveSuccessResponse
     */
    @Override
    @Transactional
    public PostDto.SaveEditSuccessResponse save(String title, String author, String content) {
        log.info("save post");

        // 멤버 조회
        Member member = memberRepository.findByUsername(author)
                .orElseThrow(() -> {
                    log.error("존재하지 않는 회원입니다. username={}", author);
                    throw new IllegalArgumentException("존재하지 않는 회원입니다. username=" + author);
                });

        Post post = Post.builder()
                .title(title)
                .member(member)
                .content(content)
                .build();

        Long savedId = postRepository.save(post).getId();

        return PostDto.SaveEditSuccessResponse.builder()
                .author(member.getUsername())
                .postId(savedId)
                .build();
    }

    @Override
    @Transactional
    public PostDto.SaveEditSuccessResponse saveV2(Post post, String author) {
        log.info("save post v2");

        // 멤버 조회
        Member member = memberRepository.findByUsername(author)
                .orElseThrow(() -> {
                    log.error("존재하지 않는 회원입니다. username={}", author);
                    throw new IllegalArgumentException("존재하지 않는 회원입니다. username=" + author);
                });

        post.setMember(member);

        Long savedId = postRepository.save(post).getId();

        return PostDto.SaveEditSuccessResponse.builder()
                .author(member.getUsername())
                .postId(savedId)
                .build();
    }

    /**
     * 글 상세 조회 by id
     * @param postId : 글 포스트 아이디
     * @return PostDetailResponse
     */
    @Override
    @Transactional(readOnly = true)
    public PostDto.DetailInfo postDetail(Long postId) {
        Post post = postRepository.findOneWithMember(postId)
                .orElseThrow(() -> {
                    log.error("존재하지 않는 포스트입니다. id={}", postId);
                    throw new IllegalArgumentException("존재하지 않는 포스트입니다. id=" + postId);
                });

        return new PostDto.DetailInfo(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDto.ListSimpleInfo> postList(Pageable pageable) {
        log.info("postList paging");
        int page = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;

        log.info("sort = {}", pageable.getSort());

        return postRepository.findAllWithMember(PageRequest.of(page, pageable.getPageSize(), pageable.getSort()))
                .map(PostDto.ListSimpleInfo::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDto.ListSimpleInfo> postList(Pageable pageable, Long authorId) {
        log.info("postList paging by authorId");
        int page = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;

        log.info("sort = {}", pageable.getSort());

        return postRepository.findAllWithMember(PageRequest.of(page, pageable.getPageSize(), pageable.getSort()))
                .map(PostDto.ListSimpleInfo::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDto.ListSimpleInfo> postList(PostSearchCondition condition, Pageable pageable) {
        log.info("postList paging & conditions 로직 실행");
        log.info("conditions={}", condition);
        int page = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;

        log.info("sort = {}", pageable.getSort());

        return postRepository.findAllByConditions(PageRequest.of(page, pageable.getPageSize(), pageable.getSort()), condition)
                .map(PostDto.ListSimpleInfo::new);
    }

    @Override
    public List<PostDto.ListSimpleInfo> postList() {
        return postRepository.findAllWithMember()
                .stream()
                .map(PostDto.ListSimpleInfo::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long edit(Long postId, PostDto.UpdateReq requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                    log.error("존재하지 않는 포스트입니다. id={}", postId);
                    throw new IllegalArgumentException("존재하지 않는 포스트입니다. id=" + postId);
                });

//        List<Integer> originalContentImage = new ArrayList<>();
//        StringBuilder sb = new StringBuilder(post.getContent());
//        int index = sb.indexOf("/image/");
//        originalContentImage.add(Integer.parseInt(sb.substring(index + 7, index + 8)));

        post.update(requestDto.getTitle(), requestDto.getContent());

        return post.getId();
    }
}
