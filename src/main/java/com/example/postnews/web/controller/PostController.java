package com.example.postnews.web.controller;

import com.example.postnews.entity.Comment;
import com.example.postnews.entity.Post;
import com.example.postnews.exception.EntityNotFoundException;
import com.example.postnews.mapper.PostMapper;
import com.example.postnews.service.CommentService;
import com.example.postnews.service.PostService;
import com.example.postnews.web.request.UpsertPostRequest;
import com.example.postnews.web.response.PostFindAllResponse;
import com.example.postnews.web.response.PostResponse;
import com.example.postnews.web.response.list.PostListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category/post")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<PostListResponse> findAll(@RequestParam(defaultValue = "0") int pageNumber,@RequestParam(defaultValue = "10") int pageSize) {
        List<PostFindAllResponse> listPostResponse = new ArrayList<>();
        List<Post> postList = postService.findAll(pageNumber,pageSize);
        List<Long> countCommentList = postList.stream().map(e->e.getComments().stream().count()).toList();

        List<PostFindAllResponse> postResponseList = postMapper.postListToResponseList(postList);
        for (int i=0; i< postResponseList.size(); i++) {
            postResponseList.get(i).setCountComment(countCommentList.get(i));
            listPostResponse.add(postResponseList.get(i));
        }
        PostListResponse newsListResponse = new PostListResponse();
        newsListResponse.setPostResponseList(listPostResponse);
        return ResponseEntity.ok(newsListResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable("id") Long id) {
        Post news = postService.findById(id);
        if (news != null) {
            List<Comment> comments = commentService.findAllByPostId(id);
            news.setComments(comments);
            PostResponse newsResponse = postMapper.postToResponse(news);
            newsResponse.setCountComment(Long.valueOf(comments.size()));
            return ResponseEntity.ok(newsResponse);
        }
        throw new EntityNotFoundException(MessageFormat.format("Post with id = {0} not found", id));
    }
    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody @Valid UpsertPostRequest request) {
        Post newPost = postService.save(postMapper.requestToPost(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(postMapper.postToResponse(newPost));
    }
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update (@PathVariable("id") Long postId, UpsertPostRequest request) {
        Post updatePost = postService.update(postMapper.requestToPost(postId,request));
        if (updatePost !=null) {
            return ResponseEntity.ok(postMapper.postToResponse(updatePost));
        }
        throw new EntityNotFoundException(MessageFormat.format("Post with id = {0} not found", postId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Post deletePost = postService.deleteById(id);
        if (deletePost !=null) {
            return ResponseEntity.noContent().build();
        }
        throw new EntityNotFoundException(MessageFormat.format("Post with id = {0} not found", id));
    }
}