package org.example.cs489project.mapper;

import org.example.cs489project.dto.request.PostRequestDto;
import org.example.cs489project.dto.response.PostResponseDto;
import org.example.cs489project.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {
    Post postRequestDtoToPost(PostRequestDto postRequestDto);
    PostResponseDto postToPostResponseDto(Post post);
    List<PostResponseDto> postsToPostResponseDtos(List<Post> posts);
}
