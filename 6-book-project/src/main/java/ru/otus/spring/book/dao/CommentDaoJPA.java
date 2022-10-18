package ru.otus.spring.book.dao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
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
    public List<Comment> getCommentsByBook(Book bookId) {
        return entityManager.createQuery("select c from Comment c where  c.book = ?1", Comment.class)
                .setParameter(1, bookId)
                .getResultList();
    }

    @Override
    public void deleteById(long id) {
        Comment comment = getById(id);
        entityManager.remove(comment);
    }
}
