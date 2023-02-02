package ru.otus.spring.book.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.spring.book.domain.Author;

import java.util.List;

@RepositoryRestResource(path = "author")
public interface AuthorDao extends PagingAndSortingRepository<Author, Long> {

    @RestResource(path = "names", rel = "names")
    Author getByName(String name);

    List<Author> findAll();
}
