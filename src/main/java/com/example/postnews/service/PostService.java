package com.example.postnews.service;

import com.example.postnews.entity.Post;

import java.util.List;

public interface PostService {

    List<Post> findAll();
    List<Post> findAll(int pageNumber, int PageSize);
    Post findById(Long id);
    Post save(Post post);
    Post update(Post ost);
    Post deleteById(Long id);
    Post deleteByIdAndUserId(Long id, Long userId);

//    List<Post> filterBy(NewsFilter filter);
}
