package com.jo0oy.myblog.dto.blog;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public class BlogDto {

    @ToString
    @Getter
    @Builder
    public static class PostSimpleInfo {
        private String title;
        private LocalDateTime createdAt;
        private LocalDateTime lastModifiedAt;
    }

    @ToString
    @Getter
    @Builder
    public static class MainInfo {
        private String blogTitle;
        private String blogDescription;
        private Page<PostSimpleInfo> postList;
    }
}
