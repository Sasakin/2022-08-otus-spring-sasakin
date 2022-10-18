package ru.otus.spring.book.dao;

import ru.otus.spring.book.domain.Book;

import java.util.List;

public interface BookDao {

    void save(Book book);

    Book getById(long id);

    List<Book> getAll();

    void deleteById(long id);
}
