package ru.otus.spring.book.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.repository.AuthorRepository;
import ru.otus.spring.book.repository.BookRepository;
import ru.otus.spring.book.repository.GenreRepository;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository dao;

    private AuthorRepository authorRepository;

    private GenreRepository genreRepository;

    @Override
    @Transactional
    public void save(Book book) {

        // Заполняем данные об авторе и жанре из БД
        Author bookAuthor = Optional.of(book.getAuthor()).map(author -> {
            if(author.getId() == null) {
                Author authorByName = authorRepository.getByName(author.getName());
                if(authorByName != null) {
                    return authorByName;
                }
            }
            return author;
        }).get();

        book.setAuthor(bookAuthor);

        Genre bookGenre = Optional.of(book.getGenre()).map(genre -> {
            if(genre.getId() == null) {
                Genre genreByTitle = genreRepository.getByTitle(genre.getTitle());
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
