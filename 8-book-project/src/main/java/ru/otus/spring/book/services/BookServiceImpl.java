package ru.otus.spring.book.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.book.dao.BookDao;
import ru.otus.spring.book.domain.Book;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookDao dao;

    @Override
    @Transactional
    public void save(Book book) {
        dao.save(book);
    }

    @Override
    public Book getById(long id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    public List<Book> getAll() {
        return dao.findAll();
    }

    @Override
    public void deleteById(long id) {
        dao.deleteById(id);
    }
}
