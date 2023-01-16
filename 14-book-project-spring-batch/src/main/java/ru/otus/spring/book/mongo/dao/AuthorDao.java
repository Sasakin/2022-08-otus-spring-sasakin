package ru.otus.spring.book.mongo.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.book.mongo.domain.Author;

public interface AuthorDao extends MongoRepository<Author, ObjectId> {

    Author getByName(String name);
}
