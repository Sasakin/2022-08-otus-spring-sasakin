package ru.otus.spring.book.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.spring.book.domain.Book;

import java.util.List;

@RepositoryRestResource(path = "book")
public interface BookDao extends PagingAndSortingRepository<Book, Long> {


    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();

}
