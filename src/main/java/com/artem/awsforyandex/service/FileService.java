package com.artem.awsforyandex.service;

import com.artem.awsforyandex.entity.FileEntity;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface FileService extends GenericService<FileEntity, Long> {
    Mono<FileEntity> uploadFile(FilePart file, Long userId);

}
