package ru.otus.spring.book.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.book.domain.Genre;

public interface GenreDao extends MongoRepository<Genre, Long> {

/*    @Query("select g from Genre g where g.title = :title")
    Genre getByTitle(String title);*/

}
