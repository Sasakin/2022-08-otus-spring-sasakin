package ru.otus.spring.book.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.book.domain.Author;

import java.util.List;

public interface AuthorDao extends JpaRepository<Author, Long> {

    @Query("select a from Author a where a.name = :name")
    Author getByName(String name);

    List<Author> findAll();
}
