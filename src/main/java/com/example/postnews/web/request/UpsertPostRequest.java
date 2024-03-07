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


    @NotNull(message = "User ID not specified")
    private Long userId;

    @NotBlank(message = "The news title is required")
    private String title;

    private String description;

    @NotBlank(message = "The news cannot be empty")
    private String body;

    @NotBlank(message = "News category name not specified")
    private String categoryName;

}
