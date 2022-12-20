package ru.otus.spring.book.services;

import reactor.core.publisher.Mono;

public interface SequenceGeneratorService {
    Mono<Long> generateSequence(String seqName);
}
