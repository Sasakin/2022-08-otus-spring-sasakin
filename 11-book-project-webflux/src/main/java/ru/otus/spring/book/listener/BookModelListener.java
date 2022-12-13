package ru.otus.spring.book.listener;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.otus.spring.book.dao.AuthorDao;
import ru.otus.spring.book.dao.GenreDao;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.services.SequenceGeneratorService;

@Component
@AllArgsConstructor
public class BookModelListener extends AbstractMongoEventListener<Book> {

    private ReactiveMongoOperations mongoOperations;

    private SequenceGeneratorService sequenceGeneratorService;

    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        if (event.getSource().getId() == null || event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME).block());
        }

       /* Book book = event.getSource();
        if (book.getAuthor() != null) {
            book.getAuthor().setId(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME).block());
            mongoOperations.save(book.getAuthor());
        }
        if (book.getGenre() != null) {
            book.getGenre().setId(sequenceGeneratorService.generateSequence(Genre.SEQUENCE_NAME).block());
            mongoOperations.save(book.getGenre());
        }*/


    }


    /**
     * Проверить наличие автора в БД
     * @param name - имя автора
     * @return
     */
    public Mono<Author> getAuthor(String name) {
        return mongoOperations
                .find(Query.query(Criteria.where("name").is(name)), Author.class)
                .single()
                .switchIfEmpty(Mono.zip(Mono.just(name),
                        sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME),
                        (authorName, id) -> new Author(id, name))
                        .doOnNext(author -> mongoOperations.save(author)));
    }

    /**
     * Проверить наличие жанра в БД
     * @param title - имя жанра
     * @return
     */
    public Mono<Genre> getGenre(String title) {
        return mongoOperations
                .find(Query.query(Criteria.where("title").is(title)), Genre.class)
                .single()
                .switchIfEmpty(Mono.zip(Mono.just(title),
                                sequenceGeneratorService.generateSequence(Genre.SEQUENCE_NAME),
                                (genreTitle, id) -> new Genre(id, genreTitle))
                        .doOnNext(genre -> mongoOperations.save(genre)));
    }


   /* private Mono<Book> genereateBookDataForSave(Author author, Genre genre, Book book) {
        return Mono.zip(
                (author1, genre1) -> {
                    book.setAuthor(author1);
                    book.setGenre(genre1);
                    return book;
                });
    }*/
}
