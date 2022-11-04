package ru.otus.spring.book.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.book.domain.Comment;

public interface CommentDao extends MongoRepository<Comment, Long> {

}
