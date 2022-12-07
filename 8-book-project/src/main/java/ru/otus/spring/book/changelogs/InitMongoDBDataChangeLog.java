package ru.otus.spring.book.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.services.SequenceGeneratorService;

import java.util.ArrayList;
import java.util.stream.Stream;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "dropDB", author = "userAdmin", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initBooks", author = "userAdmin", runAlways = true)
    public void initBooks(MongockTemplate template, SequenceGeneratorService sequenceGeneratorService){
        Author author1 = new Author("Джон");
        Genre genre1 = new Genre("Mystic");

        Book book1 = new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME),"Once a time", author1, genre1, new ArrayList<>());

        Stream.of(1,2,3,4,5).forEach(i -> {
            Comment c = new Comment("Comment for book " + i);
            book1.getComments().add(c);
        });
        template.save(book1);

        Author author2 = new Author("Pushkin");
        Genre genre2 = new Genre("History");
        Book book2 = new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME),"Evening in Paris", author2, genre2, new ArrayList<>());

        Stream.of(1,2,3,4,5).forEach(i -> {
            Comment c = new Comment("Comment for book2 " + i);
            book2.getComments().add(c);
        });
        template.save(book2);

        Author author3 = new Author("Kant");
        Genre genre3 = new Genre("Philosophy");
        Book book3 = new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME),"Blood and milk", author3, genre3, new ArrayList<>());

        Stream.of(1,2,3,4,5).forEach(i -> {
            Comment c = new Comment("Comment for book3 " + i);
            book3.getComments().add(c);
        });
        template.save(book3);
    }

}
