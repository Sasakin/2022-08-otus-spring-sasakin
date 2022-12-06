package ru.otus.spring.book.services;

import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;

import java.util.List;

public interface CommentsService {

    List<Comment> getCommentsByBook(Book book);
}
