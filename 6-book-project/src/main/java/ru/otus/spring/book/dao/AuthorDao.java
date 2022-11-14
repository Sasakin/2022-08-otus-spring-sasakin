package ru.otus.spring.book.dao;

import ru.otus.spring.book.domain.Author;

import java.util.List;

public interface AuthorDao {

    void insert(Author author);

    Author getById(long id);

    Author getByName(String name);

    List<Author> getAll();

    void deleteById(long id);
}
