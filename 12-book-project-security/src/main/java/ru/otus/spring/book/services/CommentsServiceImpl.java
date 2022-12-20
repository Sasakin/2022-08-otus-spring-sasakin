package ru.otus.spring.book.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;

import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Override
    @Transactional
    public List<Comment> getCommentsByBook(Book book) {
        return book.getComments();
    }
}
