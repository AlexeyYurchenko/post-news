package com.example.postnews.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long id;

    private String comment;

    private Instant createdAt;

}
