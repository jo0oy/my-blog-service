package com.jo0oy.myblog.domain.post;

import com.jo0oy.myblog.TestConfig;
import com.jo0oy.myblog.domain.member.Member;
import com.jo0oy.myblog.domain.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:/application-test.properties")
@Import(TestConfig.class)
class PostRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("post save 테스트")
    void post_save_test() {
        //given
        Member member = createMember("user1", "user1user1", "이주연",
                "user1@gmail.com", "010-1111-1111");

        Member savedMember = memberRepository.save(member);

        Post post = createPost("글 제목1", savedMember, "글 내용1입니다.");

        Post savedPost = postRepository.save(post);

        //when
        Post findPost = postRepository.findOneWithMember(savedPost.getId()).get();

        //then
        assertThat(findPost).isNotNull();
        assertThat(findPost.getTitle()).isEqualTo("글 제목1");
        assertThat(findPost.getMember().getUsername()).isEqualTo(member.getUsername());
    }

    private Post createPost(String title, Member author, String content) {
        return Post.builder()
                .title(title)
                .member(author)
                .content(content)
                .build();
    }

    private Member createMember(String username, String passwd, String name, String email, String phoneNumber) {
        return Member.builder()
                .username(username)
                .password(passwd)
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
    }

}
