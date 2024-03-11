package com.example.postnews.web.controller;

import com.example.postnews.mapper.PostMapper;
import com.example.postnews.service.CommentService;
import com.example.postnews.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category/post")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final CommentService commentService;





}
