package com.artem.awsforyandex.service;

import com.artem.awsforyandex.entity.UserEntity;
import reactor.core.publisher.Mono;

public interface UserService extends GenericService<UserEntity, Long> {
    Mono<UserEntity> getByUsername(String username);

    Mono<UserEntity> registerUser(UserEntity user);
}

