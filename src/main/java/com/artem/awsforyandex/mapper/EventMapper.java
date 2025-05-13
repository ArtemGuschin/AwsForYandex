package com.artem.awsforyandex.mapper;

import com.artem.awsforyandex.dto.EventDto;
import com.artem.awsforyandex.entity.EventEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDto map(EventEntity event);

    @InheritInverseConfiguration
    EventEntity map(EventDto dto);


}
