package com.example.postnews.web.response.list;

import com.example.postnews.web.response.UserResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserListResponse {

    private List<UserResponse> userResponseList = new ArrayList<>();
}
