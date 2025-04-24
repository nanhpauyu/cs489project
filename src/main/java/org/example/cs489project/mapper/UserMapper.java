package org.example.cs489project.mapper;






import org.example.cs489project.dto.request.UserRequestDto;
import org.example.cs489project.dto.response.UserResponseDto;
import org.example.cs489project.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User userRequestDtoToUser(UserRequestDto user);
    UserResponseDto userToUserResponseDto(User user);
}
