package ru.otus.spring.book.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.services.SequenceGeneratorService;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест Dao для работы с книгами")
@SpringBootTest
public class BookDaoTest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;


    @Test
    public void testInsert() {
        Author author = new Author("Ivan");
        Genre genre = new Genre("Mystic");

        Book book = new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME),"Silent hill", author, genre, new ArrayList<>());

        bookDao.save(book);

        Book fromBDBook = bookDao.findById(book.getId()).orElse(null);

        Assert.notNull(fromBDBook, "Book is inserted");
    }

    @Test
    public void testUpdate() {
        Author author = new Author("Vasily");
        Genre genre = new Genre("Comedy");

        Book book = new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME), "Silent holl2", author, genre, new ArrayList<>());

        bookDao.save(book);

        Book fromBDBook = bookDao.findById(book.getId()).orElse(null);

        Assert.notNull(fromBDBook, "Book is inserted");

        fromBDBook.setTitle("Silent hill");

        bookDao.save(fromBDBook);

        Book fromBDBook2 = bookDao.findById(book.getId()).orElse(null);

        Assertions.assertEquals("Silent hill", fromBDBook2.getTitle());
    }

    @Test
    public void testDelete() {
        Author author = new Author("Vasily Ivanov");
        Genre genre = new Genre("Comedy for students");

        Book book = new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME), "Once a time in Paris", author, genre, new ArrayList<>());

        bookDao.save(book);

        Book fromBDBook = bookDao.findById(book.getId()).orElse(null);

        Assert.notNull(fromBDBook, "Book is inserted");

        Long id = book.getId();
        bookDao.deleteById(id);

        Assert.isNull(bookDao.findById(id).orElse(null), "Book is deleted");

    }

    @Test
    public void testGetById() {
        Author author = new Author("Vasily Ivanov");
        Genre genre = new Genre("Comedy for students");

        Book book = new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME), "Once a time in Paris", author, genre, new ArrayList<>());

        bookDao.save(book);

        Assert.notNull(book.getId(), "Book is inserted");

        Long id = book.getId();

        Assert.notNull(bookDao.findById(id).orElse(null), "");
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectStudentsListWithAllInfo() {
        Author author = new Author("Vasily Ivanov");
        Genre genre = new Genre("Comedy for students");

        Book book1 = new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME), "New book. Tom 1", author, genre, new ArrayList<>());
        Book book2 = new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME), "New book. Tom 2", author, genre, new ArrayList<>());

        bookDao.save(book1);
        bookDao.save(book2);

        book1.setComments(new ArrayList<>());
        book2.setComments(new ArrayList<>());

        Stream.of(1,2,3,4,5).forEach(i -> {
            Comment c = new Comment("Comment for book1 " + i);
            book1.getComments().add(c);
        });

        Stream.of(1,2,3,4,5).forEach(i -> {
            Comment c = new Comment("Comment for book2 " + i);
            book2.getComments().add(c);
        });

        bookDao.save(book1);
        bookDao.save(book2);

        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        var books = bookDao.findAll();
        assertThat(books).isNotNull()
                .allMatch(b -> !b.getTitle().equals(""))
                .allMatch(b -> b.getGenre() != null)
                .allMatch(b -> b.getAuthor() != null)
                .allMatch(b -> b.getComments() != null);
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
    }
}
