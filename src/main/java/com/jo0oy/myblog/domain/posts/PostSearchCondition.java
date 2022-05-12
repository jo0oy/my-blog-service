package com.jo0oy.myblog.domain.posts;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class PostSearchCondition {

    private String title;
    private String author;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Builder
    public PostSearchCondition(String title, String author,
                               LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.author = author;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
