package ru.otus.spring.book.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.book.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AuthorDaoJPA implements AuthorDao {

    @PersistenceContext
    private EntityManager entityManager;

    public AuthorDaoJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void insert(Author author) {
        entityManager.persist(author);
    }

    @Override
    public Author getByName(String name) {
        Author author = entityManager
                .createQuery("select a from Author a where a.name = ?1",
                Author.class)
                .setParameter(1, name)
                .getResultList()
                .stream().findFirst().orElse(null);;
        return author;
    }

    @Override
    public Author getById(long id) {
        Author author = entityManager.find(Author.class, id);
        return author;
    }

    @Override
    public List<Author> getAll() {
        return entityManager.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        Author author = getById(id);
        entityManager.remove(author);
    }
}
