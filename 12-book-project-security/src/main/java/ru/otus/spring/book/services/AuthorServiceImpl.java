package ru.otus.spring.book.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.repository.AuthorRepository;
import ru.otus.spring.book.domain.Author;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository dao;

    @Override
    @Transactional
    public void insert(Author author) {
        dao.save(author);
    }

    @Override
    public Author getById(long id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    public Author getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public List<Author> getAll() {
        return dao.findAll();
    }

    @Override
    public void deleteById(long id) {
        dao.deleteById(id);
    }
}
