package ru.otus.spring.book.services;

import ru.otus.spring.book.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    void save(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);
}
