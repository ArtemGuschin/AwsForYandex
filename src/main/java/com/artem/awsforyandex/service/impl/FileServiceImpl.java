package com.artem.awsforyandex.service.impl;

import com.artem.awsforyandex.entity.EventEntity;
import com.artem.awsforyandex.entity.FileEntity;
import com.artem.awsforyandex.entity.Status;
import com.artem.awsforyandex.entity.UserEntity;
import com.artem.awsforyandex.repository.EventRepository;
import com.artem.awsforyandex.repository.FileRepository;
import com.artem.awsforyandex.repository.UserRepository;
import com.artem.awsforyandex.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final S3Client s3Client;

    @Value("${yandex.cloud.bucket}")
    private String bucket;


    @Override
    public Mono<FileEntity> getById(Long id) {
        return fileRepository.findById(id);
    }

    @Override
    public Flux<FileEntity> getAll() {
        return fileRepository.findAll();
    }

    @Override
    public Mono<FileEntity> save(FileEntity entity) {
        return fileRepository.save(entity);
    }

    @Override
    public Mono<FileEntity> update(FileEntity entity) {
        return fileRepository.save(entity);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return fileRepository.deleteById(id);
    }


    private Mono<UserEntity> validateUser(UserEntity user) {
        if (user.getStatus() != Status.ACTIVE) {
            return Mono.error(new RuntimeException("User is not active"));
        }
        return Mono.just(user);


    }

    @Override
    public Mono<FileEntity> uploadFile(FilePart filePart, Long userId) {
        String originalFilename = filePart.filename();
        String key = "files/" + userId + "/" + UUID.randomUUID() + "_" + originalFilename;

        return DataBufferUtils.join(filePart.content())
                .flatMap(dataBuffer -> {
                    try {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        DataBufferUtils.release(dataBuffer);

                        String contentType = filePart.headers().getContentType() != null
                                ? filePart.headers().getContentType().toString()
                                : "application/octet-stream";

                        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                                .bucket(bucket)
                                .key(key)
                                .contentType(contentType)
                                .build();

                        return Mono.fromCallable(() -> {
                                    s3Client.putObject(putObjectRequest, RequestBody.fromBytes(bytes));
                                    return true;
                                })
                                .subscribeOn(Schedulers.boundedElastic())
                                .then(fileRepository.save(
                                        FileEntity.builder()
                                                .status(Status.ACTIVE)
                                                .location(originalFilename)
                                                .build()
                                ))
                                .flatMap(savedFile ->
                                        eventRepository.save(
                                                        EventEntity.builder()
                                                                .userId(userId)
                                                                .fileId(savedFile.getId())
                                                                .status(Status.ACTIVE)
                                                                .build()
                                                )
                                                .thenReturn(savedFile)
                                );
                    } catch (Exception e) {
                        return Mono.error(new IOException("File processing failed", e));
                    }
                });
    }


}

