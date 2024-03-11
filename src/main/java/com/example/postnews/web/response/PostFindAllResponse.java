package com.example.postnews.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostFindAllResponse {

    private Long id;

    private String title;

    private String description;

    private String body;

    private Instant createdAt;

    private Instant updatedAt;

    private Long countComment;

}

