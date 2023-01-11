package ru.otus.spring.book.mongo.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.book.mongo.domain.Genre;

public interface GenreDao extends MongoRepository<Genre, ObjectId> {

    Genre getByTitle(String title);
}
