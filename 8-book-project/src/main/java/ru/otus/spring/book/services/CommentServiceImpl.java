package ru.otus.spring.book.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.book.dao.CommentDao;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentDao dao;

    @Override
    @Transactional
    public void insert(Comment comment) {
        dao.save(comment);
    }

    @Override
    public Comment getById(long id) {
        return null;//dao.getById(id);
    }

    @Override
    public void deleteById(long id) {
        dao.deleteById(id);
    }
}
