package ru.otus.spring.book.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.book.domain.Genre;

public interface GenreDao extends ReactiveMongoRepository<Genre, Long> {

    Mono<Genre> getByTitle(String title);
}
