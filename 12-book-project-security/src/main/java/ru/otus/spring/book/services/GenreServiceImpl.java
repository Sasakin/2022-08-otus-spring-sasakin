package ru.otus.spring.book.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.repository.GenreRepository;
import ru.otus.spring.book.domain.Genre;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private GenreRepository dao;

    @Override
    @Transactional
    public void insert(Genre genre) {
        dao.save(genre);
    }

    @Override
    public Genre getByTitle(String title) {
        return dao.getByTitle(title);
    }

    @Override
    public Genre getById(long id) {
        return dao.findById(id).orElse(null);
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
