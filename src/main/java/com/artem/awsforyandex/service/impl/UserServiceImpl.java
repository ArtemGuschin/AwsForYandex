package com.artem.awsforyandex.service.impl;

import com.artem.awsforyandex.entity.Status;
import com.artem.awsforyandex.entity.UserEntity;
import com.artem.awsforyandex.entity.UserRole;
import com.artem.awsforyandex.repository.UserRepository;
import com.artem.awsforyandex.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserEntity> getById(Long id) {
        return userRepository
                .findById(id);

    }

    @Override
    public Mono<UserEntity> getByUsername(String username) {
        return userRepository
                .findByUsername(username);

    }

    @Override
    public Flux<UserEntity> getAll() {
        return userRepository
                .findAll();

    }

    @Override
    public Mono<UserEntity> save(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository
                .save(user);

    }

    @Override
    public Mono<UserEntity> update(UserEntity user) {
        return userRepository
                .findById(user.getId())
                .flatMap(
                        existingUser -> {

                            if (!existingUser.getPassword().equals(user.getPassword())) {
                                user.setPassword(passwordEncoder.encode(user.getPassword()));
                            }
                            return userRepository.save(user);
                        });

    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return userRepository
                .deleteById(id);

    }

    public Mono<UserEntity> registerUser(UserEntity user) {
        return userRepository.save(
                user.toBuilder()
                        .password(passwordEncoder.encode(user.getPassword()))
                        .role(UserRole.ADMIN)
                        .status(Status.ACTIVE)
                        .enabled(true)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        ).doOnSuccess(u -> {
            log.info("IN registerUser - user: {} created", u);
        });
    }
}