package com.example.postnews.web.controller;

import com.example.postnews.entity.Post;
import com.example.postnews.exception.EntityNotFoundException;
import com.example.postnews.mapper.PostMapper;
import com.example.postnews.service.PostService;
import com.example.postnews.web.request.UpsertPostRequest;
import com.example.postnews.web.response.post.PostResponse;
import com.example.postnews.web.response.list.PostListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category/post")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping
    public ResponseEntity <PostListResponse> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                              @RequestParam(defaultValue = "10") int pageSize) {

        Page<Post> posts = postService.findAll(pageNumber, pageSize);
        return ResponseEntity.ok(
                        PostListResponse.builder()
                .totalCount(posts.getTotalElements())
                .postResponseList(posts.stream().map(postMapper::postFindAllToResponse).toList())
                .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable("id") Long id) {
        Post post = postService.findById(id);
        if (post != null) {
            return ResponseEntity.ok(postMapper.postToResponse(post));
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