package ru.otus.spring.book.services;

import ru.otus.spring.book.domain.Genre;

import java.util.List;

public interface GenreService {

    void insert(Genre genre);

    Genre getByTitle(String title);

    Genre getById(long id);

    List<Genre> getAll();

    void deleteById(long id);
}
