package com.artem.awsforyandex.repository;

import com.artem.awsforyandex.entity.EventEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface EventRepository extends R2dbcRepository<EventEntity, Long> {

}
