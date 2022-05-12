package com.jo0oy.myblog.web;

import com.jo0oy.myblog.application.facade.blog.BlogFacade;
import com.jo0oy.myblog.dto.blog.BlogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogFacade blogFacade;

    @GetMapping({"/", "/home"})
    public String home() {
        return "index";
    }

    @GetMapping("/blog/{username}")
    public String myBlog(@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                         @PathVariable("username") String username, Model model) {
        BlogDto.MainInfo blog = blogFacade.getBlog(pageable, username);

        model.addAttribute("blogInfo", blog);
        model.addAttribute("list", blog.getPostList());

        return "/blog/myBlog";
    }
}
