package com.example.postnews.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertPostRequest {

    @NotNull(message = "User ID not null")
    private Long userId;

    @NotBlank(message = "The post title is required")
    private String title;

    private String description;

    @NotBlank(message = "The post body cannot be empty")
    private String body;

    @NotBlank(message = "Post category name not blank")
    private String categoryName;
}