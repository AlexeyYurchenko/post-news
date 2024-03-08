package com.example.postnews.mapper;

import com.example.postnews.entity.User;
import com.example.postnews.web.request.UpsertUserRequest;
import com.example.postnews.web.response.UserResponse;
import com.example.postnews.web.response.list.UserListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {PostMapper.class, CommentMapper.class})
public interface UserMapper {

    User requestToUser(UpsertUserRequest request);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId, UpsertUserRequest request);

    UserResponse userToResponse(User user);

    List<UserResponse> userListToResponseList(List<User> users);

    default UserListResponse userListToUserListResponse(List<User> users) {
        UserListResponse response = new UserListResponse();
        response.setUserResponseList(userListToResponseList(users));
        return response;
    }
}

