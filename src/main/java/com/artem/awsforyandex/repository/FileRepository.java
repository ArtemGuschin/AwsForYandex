package com.artem.awsforyandex.repository;

import com.artem.awsforyandex.entity.FileEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface FileRepository extends R2dbcRepository<FileEntity, Long> {
}
