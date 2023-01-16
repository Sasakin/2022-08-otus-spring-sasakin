package ru.otus.spring.book.config;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.spring.book.jpa.domain.Book;
import ru.otus.spring.book.mongo.dao.AuthorDao;
import ru.otus.spring.book.mongo.dao.GenreDao;
import ru.otus.spring.book.mongo.domain.Comment;
import ru.otus.spring.book.mongo.services.SequenceGeneratorService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class BookItemProcessor implements ItemProcessor<Book, ru.otus.spring.book.mongo.domain.Book> {

    private AuthorDao authorDao;

    private GenreDao genreDao;

    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public ru.otus.spring.book.mongo.domain.Book process(Book book) throws Exception {
        ru.otus.spring.book.mongo.domain.Book resultBook = new ru.otus.spring.book.mongo.domain.Book();
        resultBook.setId(sequenceGeneratorService.generateSequence());
        resultBook.setTitle(book.getTitle());
        resultBook.setAuthor(authorDao.getByName(book.getAuthor().getName()));
        resultBook.setGenre(genreDao.getByTitle(book.getGenre().getTitle()));
        resultBook.setComments(Optional.ofNullable(book.getComments()).map(
                list -> list.stream().map(c -> new Comment(c.getText())).collect(Collectors.toList())
                ).orElse(new ArrayList<>()));
        return resultBook;
    }
}
