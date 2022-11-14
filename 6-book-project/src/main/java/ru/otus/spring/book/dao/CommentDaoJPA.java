package ru.otus.spring.book.dao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CommentDaoJPA implements CommentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insert(Comment comment) {
        entityManager.persist(comment);
    }


    @Override
    public Comment getById(long id) {
        Comment comment = entityManager.find(Comment.class, id);
        return comment;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Comment comment = getById(id);
        entityManager.remove(comment);
    }
}
