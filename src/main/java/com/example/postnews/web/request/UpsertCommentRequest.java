package com.example.postnews.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertCommentRequest {

    @NotNull(message = "User ID not specified")
    private Long userId;

    @NotNull(message = "Post ID not specified")
    private Long postId;

    @NotBlank(message = "Comment cannot be empty")
    private String comment;

}