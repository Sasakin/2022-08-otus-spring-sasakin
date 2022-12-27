package ru.otus.spring.book.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.book.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {


    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();

}
