package org.blurbackend.mapper;


import org.blurbackend.dto.UserDto;
import org.blurbackend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );
    UserDto toUserDto(User user);
    User toUser(UserDto userDto);
}
