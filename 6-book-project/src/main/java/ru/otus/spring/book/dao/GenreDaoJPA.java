package ru.otus.spring.book.dao;

import org.springframework.stereotype.Service;
import ru.otus.spring.book.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class GenreDaoJPA implements GenreDao {

    @PersistenceContext
    private EntityManager entityManager;

    public GenreDaoJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void insert(Genre genre) {
        entityManager.persist(genre);
    }

    @Override
    public Genre getByTitle(String title) {
        Genre genre = entityManager
                        .createQuery("select g from Genre g where g.title = :title", Genre.class)
                        .setParameter("title", title)
                        .getResultList()
                        .stream()
                        .findFirst()
                        .orElse(null);;
        return genre;
    }

    @Override
    public Genre getById(long id) {
        Genre genre = entityManager.find(Genre.class, id);
        return genre;
    }

    @Override
    public List<Genre> getAll() {
        return entityManager.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Genre genre = getById(id);
        entityManager.remove(genre);
    }
}
