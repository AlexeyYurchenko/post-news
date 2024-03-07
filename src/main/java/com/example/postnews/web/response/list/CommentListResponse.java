package com.example.postnews.web.response.list;

import com.example.postnews.web.response.CommentResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentListResponse {

    private List<CommentResponse> commentResponseList = new ArrayList<>();
}
