package ru.otus.spring.book.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.book.dao.AuthorDao;
import ru.otus.spring.book.dao.GenreDao;
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

    @ChangeSet(order = "001", id = "initAuthors", author = "userAdmin", runAlways = true)
    public void initAuthors(MongockTemplate template, SequenceGeneratorService sequenceGeneratorService){
        template.save(new Author(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME),"Джон"));
        template.save(new Author(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME), "Игорь"));
        template.save(new Author(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME), "Дмитрий"));
    }

    @ChangeSet(order = "001", id = "initGenres", author = "userAdmin", runAlways = true)
    public void initGenres(MongockTemplate template, SequenceGeneratorService sequenceGeneratorService){
        template.save(new Genre(sequenceGeneratorService.generateSequence(Genre.SEQUENCE_NAME),"Mystic"));
        template.save(new Genre(sequenceGeneratorService.generateSequence(Genre.SEQUENCE_NAME),"Horor"));
        template.save(new Genre(sequenceGeneratorService.generateSequence(Genre.SEQUENCE_NAME),"Serial"));
    }

    @ChangeSet(order = "002", id = "initBooks", author = "userAdmin", runAlways = true)
    public void initBooks(MongockTemplate template, SequenceGeneratorService sequenceGeneratorService,
                          AuthorDao authorDao, GenreDao genreDao){
        Author author = authorDao.findAll().get(0);
        Genre genre = genreDao.findAll().get(0);

        Book book1 = new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME),"Once a time", author, genre, new ArrayList<>());

        Stream.of(1,2,3,4,5).forEach(i -> {
            Comment c = new Comment("Comment for book " + i, book1);
            book1.getComments().add(c);
        });
        template.save(book1);


        Book book2 = new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME),"Evening in Paris", author, genre, new ArrayList<>());

        Stream.of(1,2,3,4,5).forEach(i -> {
            Comment c = new Comment("Comment for book2 " + i, book2);
            book2.getComments().add(c);
        });
        template.save(book2);

        Book book3 = new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME),"Blood and milk", author, genre, new ArrayList<>());

        Stream.of(1,2,3,4,5).forEach(i -> {
            Comment c = new Comment("Comment for book3 " + i, book3);
            book3.getComments().add(c);
        });
        template.save(book3);
    }

}
