package ru.otus.spring.book.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;
import ru.otus.spring.book.domain.Genre;

import java.util.List;

public interface GenreDao extends JpaRepository<Genre, Long> {

    @Query("select g from Genre g where g.title = :title")
    Genre getByTitle(String title);

}
