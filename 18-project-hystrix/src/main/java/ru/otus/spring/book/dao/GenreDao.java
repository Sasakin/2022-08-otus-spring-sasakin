package ru.otus.spring.book.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Genre;

import java.util.List;

@RepositoryRestResource(path = "genre")
public interface GenreDao extends PagingAndSortingRepository<Genre, Long> {

    @RestResource(path = "title", rel = "title")
    Genre getByTitle(String title);

    List<Genre> findAll();

}
