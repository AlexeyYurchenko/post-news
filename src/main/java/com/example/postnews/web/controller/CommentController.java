package com.example.postnews.web.controller;

import com.example.postnews.entity.Comment;
import com.example.postnews.exception.EntityNotFoundException;
import com.example.postnews.mapper.CommentMapper;
import com.example.postnews.service.CommentService;
import com.example.postnews.web.request.UpsertCommentRequest;
import com.example.postnews.web.response.comment.CommentResponse;
import com.example.postnews.web.response.comment.CommentListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping("/{postId}")
    public ResponseEntity<CommentListResponse> findByPostId(@PathVariable("postId") Long postId) {

        List<Comment> comments = commentService.findAllByPostId(postId);
        if (comments != null) {
            return ResponseEntity.ok(
                    CommentListResponse.builder()
                            .commentResponseList(comments.stream().map(commentMapper::commentToResponse)
                                    .toList()).build()
            );
        }
        throw new EntityNotFoundException(MessageFormat.format("No comments found for post with id= {0}", postId));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid UpsertCommentRequest request) {
        Comment newComment = commentService.save(commentMapper.requestToComment(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.commentToResponse(newComment));

    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@PathVariable("id") Long commentId, UpsertCommentRequest request) {
        Comment updateComment = commentService.update(commentMapper.requestToComment(commentId, request));
        if (updateComment != null) {
            return ResponseEntity.ok(commentMapper.commentToResponse(updateComment));
        }
        throw new EntityNotFoundException(MessageFormat.format("Comment with id= {0} not found", commentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @RequestParam("userId") Long userid) {
        commentService.deleteByIdAndUserId(id, userid);
        return ResponseEntity.noContent().build();
    }
}