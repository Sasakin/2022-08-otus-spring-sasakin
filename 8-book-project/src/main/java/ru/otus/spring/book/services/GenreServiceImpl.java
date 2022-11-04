package ru.otus.spring.book.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.book.dao.GenreDao;
import ru.otus.spring.book.domain.Genre;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private GenreDao dao;

    @Override
    @Transactional
    public void insert(Genre genre) {
        dao.save(genre);
    }

    @Override
    public Genre getByTitle(String title) {
        return null; //dao.getByTitle(title);
    }

    @Override
    public Genre getById(long id) {
        return null; //dao.getById(id);
    }

    @Override
    public List<Genre> getAll() {
        return dao.findAll();
    }

    @Override
    public void deleteById(long id) {
        dao.deleteById(id);
    }
}
