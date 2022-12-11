package ru.otus.spring.book.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.book.dao.AuthorDao;
import ru.otus.spring.book.dao.BookDao;
import ru.otus.spring.book.dao.GenreDao;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookDao dao;

    private AuthorDao authorDao;

    private GenreDao genreDao;

    @Override
    @Transactional
    public void save(Book book) {

        // Заполняем данные об авторе и жанре из БД
        Author bookAuthor = Optional.of(book.getAuthor()).map(author -> {
            if(author.getId() == null) {
                Author authorByName = authorDao.getByName(author.getName());
                if(authorByName != null) {
                    return authorByName;
                }
            }
            return author;
        }).get();

        book.setAuthor(bookAuthor);

        Genre bookGenre = Optional.of(book.getGenre()).map(genre -> {
            if(genre.getId() == null) {
                Genre genreByTitle = genreDao.getByTitle(genre.getTitle());
                if(genreByTitle != null) {
                    return genreByTitle;
                }
            }
            return genre;
        }).get();

        book.setGenre(bookGenre);

        dao.save(book);
    }

    @Override
    public Optional<Book> getById(long id) {
        return dao.findById(id);
    }

    @Override
    public List<Book> getAll() {
        return dao.findAll();
    }

    @Override
    public void deleteById(long id) {
        Optional<Book> bookOpt = getById(id);
        if(bookOpt.isPresent()) {
            // убрать ссылки на автора и жанр
            Book book = bookOpt.get();
            book.setAuthor(null);
            book.setGenre(null);
            dao.save(book);

            dao.deleteById(id);
        }
    }
}
