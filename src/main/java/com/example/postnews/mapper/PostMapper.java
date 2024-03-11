package com.example.postnews.mapper;

import com.example.postnews.entity.Post;
import com.example.postnews.web.request.UpsertPostRequest;
import com.example.postnews.web.response.PostFindAllResponse;
import com.example.postnews.web.response.PostResponse;
import com.example.postnews.web.response.list.PostListResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(PostMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapper.class})
public interface PostMapper {

    Post requestToPost (UpsertPostRequest request);
    @Mapping(source = "postId", target = "id")
    Post requestToPost (Long postId, UpsertPostRequest request);

    PostResponse postToResponse(Post post);

    PostFindAllResponse postFindAllToResponse(Post post);

    List<PostFindAllResponse> postListToResponseList(List<Post> postList);

    default PostListResponse postListToPostListResponse(List<Post> postList) {
        PostListResponse response = new PostListResponse();
        response.setPostResponseList(postListToResponseList(postList));
        return response;
    }
}
