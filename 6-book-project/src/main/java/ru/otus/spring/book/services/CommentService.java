package ru.otus.spring.book.services;

import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;

import java.util.List;

public interface CommentService {

    void insert(Comment comment);

    Comment getById(long id);

    void deleteById(long id);
}
