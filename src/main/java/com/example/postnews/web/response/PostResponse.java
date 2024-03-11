package com.example.postnews.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long id;

    private String title;

    private String description;

    private String body;

    private Instant createdAt;

    private Instant updatedAt;

    private Integer countComment;

    private List<CommentResponse> comments = new ArrayList<>();
}