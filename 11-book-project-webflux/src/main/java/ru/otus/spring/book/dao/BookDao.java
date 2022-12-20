package ru.otus.spring.book.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.book.domain.Book;

public interface BookDao extends ReactiveMongoRepository<Book, Long> {

}
