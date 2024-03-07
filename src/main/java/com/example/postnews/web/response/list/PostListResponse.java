package com.example.postnews.web.response.list;

import com.example.postnews.web.response.PostResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostListResponse {

    private List<PostResponse> postResponseList = new ArrayList<>();
}
