package ru.otus.spring.book.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.dao.GenreDao;
import ru.otus.spring.book.domain.Genre;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private GenreDao dao;

    @Override
    @Transactional
    public void insert(Genre genre) {
        dao.insert(genre);
    }

    @Override
    public Genre getByTitle(String title) {
        return dao.getByTitle(title);
    }

    @Override
    public Genre getById(long id) {
        return dao.getById(id);
    }

    @Override
    public List<Genre> getAll() {
        return dao.getAll();
    }

    @Override
    public void deleteById(long id) {
        dao.deleteById(id);
    }
}
