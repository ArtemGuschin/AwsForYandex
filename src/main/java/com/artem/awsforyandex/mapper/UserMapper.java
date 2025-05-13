package com.artem.awsforyandex.mapper;

import com.artem.awsforyandex.dto.UserDto;
import com.artem.awsforyandex.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto map(UserEntity user);

    @InheritInverseConfiguration
    UserEntity map(UserDto user);
}

