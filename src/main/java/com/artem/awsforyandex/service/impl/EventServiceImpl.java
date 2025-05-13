package com.artem.awsforyandex.service.impl;

import com.artem.awsforyandex.entity.EventEntity;
import com.artem.awsforyandex.repository.EventRepository;
import com.artem.awsforyandex.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public Mono<EventEntity> getById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public Flux<EventEntity> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public Mono<EventEntity> save(EventEntity entity) {
        return eventRepository.save(entity);
    }

    @Override
    public Mono<EventEntity> update(EventEntity entity) {
        return eventRepository.save(entity);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return eventRepository.deleteById(id);
    }
}