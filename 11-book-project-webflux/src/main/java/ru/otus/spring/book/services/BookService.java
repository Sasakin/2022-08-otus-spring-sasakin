package ru.otus.spring.book.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.book.domain.Book;

public interface BookService {

    void save(Book book);

    Mono<Book> getById(long id);

    Flux<Book> getAll();

    void deleteById(long id);
}
