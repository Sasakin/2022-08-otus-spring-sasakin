package ru.otus.spring.book.dao;

import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;

import java.util.List;

public interface GenreDao {
    int count();

    Long insert(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    void deleteById(long id);
}
