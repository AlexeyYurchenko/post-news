package com.example.postnews.web.response.list;

import com.example.postnews.web.response.post.PostFindAllResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostListResponse {

    private Long totalCount;

    private List<PostFindAllResponse> postResponseList = new ArrayList<>();
}
