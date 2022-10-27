package ru.otus.spring.book.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.book.domain.Book;

import java.util.List;

public interface BookDao extends JpaRepository<Book, Long> {


    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();

}
