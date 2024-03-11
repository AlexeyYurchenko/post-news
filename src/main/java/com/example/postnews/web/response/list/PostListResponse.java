package com.example.postnews.web.response.list;

import com.example.postnews.web.response.PostFindAllResponse;
import com.example.postnews.web.response.PostResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostListResponse {

    private List<PostFindAllResponse> postResponseList = new ArrayList<>();
}
