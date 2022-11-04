package ru.otus.spring.book.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.book.domain.Author;

public interface AuthorDao extends MongoRepository<Author, Long> {

   /* @Query("select a from Author a where a.name = :name")
    Author getByName(String name);*/
}
