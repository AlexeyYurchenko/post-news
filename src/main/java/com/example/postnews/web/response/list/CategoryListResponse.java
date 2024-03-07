package com.example.postnews.web.response.list;

import com.example.postnews.web.response.CategoryResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryListResponse {

    private List<CategoryResponse> categoryResponseList = new ArrayList<>();
}
