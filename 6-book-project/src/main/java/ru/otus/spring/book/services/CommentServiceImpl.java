package ru.otus.spring.book.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.dao.CommentDao;
import ru.otus.spring.book.domain.Comment;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentDao dao;

    @Override
    @Transactional
    public void insert(Comment comment) {
        dao.insert(comment);
    }

    @Override
    public Comment getById(long id) {
        return dao.getById(id);
    }

    @Override
    public void deleteById(long id) {
        dao.deleteById(id);
    }
}
