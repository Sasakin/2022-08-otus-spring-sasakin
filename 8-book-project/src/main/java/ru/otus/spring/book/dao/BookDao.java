package ru.otus.spring.book.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.book.domain.Book;

public interface BookDao extends MongoRepository<Book, Long> {

}
