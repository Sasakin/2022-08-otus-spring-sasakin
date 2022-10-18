package ru.otus.spring.book.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.dao.AuthorDao;
import ru.otus.spring.book.domain.Author;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private AuthorDao dao;

    @Override
    @Transactional
    public void insert(Author author) {
        dao.insert(author);
    }

    @Override
    public Author getById(long id) {
        return dao.getById(id);
    }

    @Override
    public Author getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public List<Author> getAll() {
        return dao.getAll();
    }

    @Override
    public void deleteById(long id) {
        dao.deleteById(id);
    }
}
