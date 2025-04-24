package org.example.cs489project.mapper;


import org.example.cs489project.dto.request.ProfileRequestDto;
import org.example.cs489project.dto.response.ProfileResponseDto;
import org.example.cs489project.model.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileMapper {
   Profile profileRequestDtoToProfile(ProfileRequestDto profileRequestDto);
   ProfileResponseDto profileToProfileResponseDto(Profile profile);
   List<ProfileResponseDto> profilesToProfileResponseDtos(List<Profile> profiles);
}
