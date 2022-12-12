package ru.otus.spring.book.services;

import lombok.AllArgsConstructor;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;
import reactor.util.context.ContextView;
import ru.otus.spring.book.dao.AuthorDao;
import ru.otus.spring.book.dao.BookDao;
import ru.otus.spring.book.dao.GenreDao;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Flow;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private SequenceGeneratorService sequenceGeneratorService;

    private BookDao dao;

    private AuthorDao authorDao;

    private GenreDao genreDao;

    @Override
    @Transactional
    public void save(Book book) {

        /*Mono.just(book)
                .doOnNext(book1 -> {
                    if (book1.getAuthor().getId() == null) {
                        Author ifEmpty = book.getAuthor();
                        bookAuthor = authorDao.getByName(book.getAuthor().getName())
                                .defaultIfEmpty(ifEmpty);
                    }
                })

        // Заполняем данные об авторе и жанре из БД
        Mono<Author> bookAuthor = Mono.just(book.getAuthor());

        if (book.getAuthor().getId() == null) {
            Author ifEmpty = book.getAuthor();
            ifEmpty.setId(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME));
            bookAuthor = authorDao.getByName(book.getAuthor().getName())
                    .defaultIfEmpty(ifEmpty);
        }

        book.setAuthor(bookAuthor.);

        Genre bookGenre = Optional.of(book.getGenre()).map(genre -> {
            if (genre.getId() == null) {
                Genre genreByTitle = genreDao.getByTitle(genre.getTitle());
                if (genreByTitle != null) {
                    return genreByTitle;
                }
            }
            return genre;
        }).get();

        book.setGenre(bookGenre);*/

        dao.save(book);
    }

    @Override
    public Mono<Book> getById(long id) {
        return dao.findById(id);
    }

    @Override
    public Flux<Book> getAll() {
        return dao.findAll();
    }

    @Override
    public void deleteById(long id) {
        Mono<Book> bookOpt = getById(id);
        bookOpt.hasElement().doOnNext(hasElement -> {
            if(hasElement) {
                dao.deleteById(id);
            }
        });
    }
}
