package ru.otus.spring.book.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.book.domain.Author;

public interface AuthorDao extends ReactiveMongoRepository<Author, Long> {

    Mono<Author> getByName(String name);
}
