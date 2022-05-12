package com.jo0oy.myblog.dto.posts;

import com.jo0oy.myblog.domain.posts.Post;
import com.jo0oy.myblog.domain.posts.PostSearchCondition;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PostDto {

    @NoArgsConstructor
    @Getter
    public static class SaveReq {

        @NotBlank(message = "{NotBlank.post.title}")
        private String title;

        @NotBlank(message = "{NotBlank.post.content}")
        private String content;

        @Builder
        public SaveReq(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public Post toEntity() {
            return Post.builder()
                    .title(title)
                    .content(content)
                    .build();
        }
    }

    @NoArgsConstructor
    @Getter
    public static class UpdateReq {

        @NotBlank(message = "{NotBlank.post.title}")
        private String title;

        @NotBlank(message = "{NotBlank.post.content}")
        private String content;

        @Builder
        public UpdateReq(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }

    @NoArgsConstructor
    @Getter
    public static class SearchCondition {
        private String startDate;
        private String endDate;
        private String author;
        private String title;

        @Builder
        public SearchCondition(String startDate,
                                      String endDate,
                                      String author,
                                      String title) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.author = author;
            this.title = title;
        }

        public PostSearchCondition toSearchCondition() {

            LocalDate startDateParam = null;
            if (StringUtils.hasText(startDate)) {
                String[] startDateList = startDate.split("/");
                startDateParam
                        = LocalDate.of(Integer.parseInt(startDateList[2]),
                        Integer.parseInt(startDateList[0]), Integer.parseInt(startDateList[1]));
            }

            LocalDate endDateParam = null;
            if (StringUtils.hasText(endDate)) {
                String[] endDateList = endDate.split("/");
                endDateParam
                        = LocalDate.of(Integer.parseInt(endDateList[2]),
                                Integer.parseInt(endDateList[0]), Integer.parseInt(endDateList[1]))
                        .plusDays(1);
            }

            return PostSearchCondition.builder()
                    .startDate(startDateParam)
                    .endDate(endDateParam)
                    .author(author)
                    .title(title)
                    .build();
        }
    }

    @NoArgsConstructor
    @Getter
    public static class DetailInfo {
        private Long postId;
        private String title;
        private String author;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
        private String content;

        public DetailInfo(Post entity) {
            this.postId = entity.getId();
            this.title = entity.getTitle();
            this.author = entity.getMember().getUsername();
            this.createdDate = entity.getCreatedDate();
            this.lastModifiedDate = entity.getLastModifiedDate();
            this.content = entity.getContent();
        }
    }

    @NoArgsConstructor
    @Getter
    public static class ListSimpleInfo {
        private Long postId;
        private String title;
        private String author;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public ListSimpleInfo(Post entity) {
            this.postId = entity.getId();
            this.title = entity.getTitle();
            this.author = entity.getMember().getUsername();
            this.createdDate = entity.getCreatedDate();
            this.lastModifiedDate = entity.getLastModifiedDate();
        }
    }

    @NoArgsConstructor
    @Getter
    public static class SaveEditSuccessResponse {
        private String author;
        private Long postId;

        @Builder
        public SaveEditSuccessResponse(String author, Long postId) {
            this.author = author;
            this.postId = postId;
        }
    }

}
