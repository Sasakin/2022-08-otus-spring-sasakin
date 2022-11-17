package ru.otus.spring.book.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment, Long> {

}
