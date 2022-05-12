package com.jo0oy.myblog.domain.members;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@Embeddable
public class BlogFragment {

    private String blogTitle;
    private String blogDescription;

    @Builder
    public BlogFragment(String blogTitle, String blogDescription) {
        this.blogTitle = blogTitle;
        this.blogDescription = blogDescription;
    }
}
