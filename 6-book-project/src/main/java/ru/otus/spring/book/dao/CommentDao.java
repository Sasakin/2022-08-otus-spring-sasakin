package ru.otus.spring.book.dao;

import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;

import java.util.List;

public interface CommentDao {

    void insert(Comment comment);

    Comment getById(long id);

    void deleteById(long id);
}
