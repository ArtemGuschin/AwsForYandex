package com.artem.awsforyandex.mapper;

import com.artem.awsforyandex.dto.FileDto;
import com.artem.awsforyandex.entity.FileEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileDto map(FileEntity fileEntity);

    @InheritInverseConfiguration
    FileEntity map(FileDto fileDto);

}
