package ru.otus.spring.book.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.rest.controller.dto.BookDto;

public interface BookService {

    Mono<Book> save(BookDto book);

    Mono<BookDto> getById(long id);

    Flux<BookDto> getAll();

    Flux<BookDto> searchByKeyWord(String keyword);

    Mono<Void> deleteById(long id);
}
