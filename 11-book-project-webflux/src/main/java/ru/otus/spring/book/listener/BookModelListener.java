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

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        Book book = event.getSource();
        if (book.getAuthor() != null) {
            book.setAuthorId(book.getAuthor().getId());
        }
        if (book.getGenre() != null) {
            book.setGenreId(book.getGenre().getId());
        }
    }
}
