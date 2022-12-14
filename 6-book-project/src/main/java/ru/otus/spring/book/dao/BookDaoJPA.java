package ru.otus.spring.book.dao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.domain.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;


@Service
@AllArgsConstructor
public class BookDaoJPA implements BookDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Book book) {
        if(book.getId() == null && book.getAuthor().getId()==null && book.getGenre().getId() == null) {
            entityManager.persist(book);
        } else {
            entityManager.merge(book);
        }
    }

    @Override
    public Book getById(long id) {
        Book book = entityManager.find(Book.class, id);
        return book;
    }

    @Override
    public List<Book> getAll() {
        EntityGraph graph = entityManager.getEntityGraph("author-genre-graph");
        Query query = entityManager.createQuery("select b from Book b", Book.class); //
        query.setHint(org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH.getKey(),graph);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Book book = getById(id);
        entityManager.remove(book);
    }
}
