package ru.otus.spring.book.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;
import ru.otus.spring.book.dao.BookDao;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;
import ru.otus.spring.book.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@DisplayName("Тест Dao для работы с книгами")
@SpringBootTest
public class BookServiceTest {

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @MockBean
    private BookDao bookDao;

    @Autowired
    private BookServiceImpl bookService;

    @Test
    public void testInsert() {
        Author author = new Author(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME), "Ivan");
        Genre genre = new Genre(sequenceGeneratorService.generateSequence(Genre.SEQUENCE_NAME), "Mystic");

        Book book = new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME), "Once a time in Paris", author, genre, new ArrayList<>());

        when(bookDao.save(any(Book.class))).thenReturn(book);

        bookService.save(book);
    }

    @Test
    public void testDelete() {
        doNothing().when(bookDao).deleteById(any(Long.class));

        Long id = 1l;
        bookService.deleteById(id);

    }

    @Test
    public void testGetById() {
        Author author = new Author(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME), "Ivan");
        Genre genre = new Genre(sequenceGeneratorService.generateSequence(Genre.SEQUENCE_NAME), "Mystic");

        Book book = new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME), "Once a time in Paris", author, genre, new ArrayList<>());

        when(bookDao.save(any(Book.class))).thenReturn(book);

        bookService.save(book);

        Assert.notNull(book.getId(), "Book is inserted");

        Long id = book.getId();
        when(bookDao.findById(id)).thenReturn(Optional.of(book));

        Assert.notNull(bookService.getById(id), "");
    }


    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectStudentsListWithAllInfo() {

        Author author = new Author(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME), "Vasily Ivanov");
        Genre genre = new Genre(sequenceGeneratorService.generateSequence(Genre.SEQUENCE_NAME), "Comedy for students");

        Book book1 = new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME), "New book. Tom 1", author, genre, new ArrayList<>());
        Book book2 = new Book(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME), "New book. Tom 2", author, genre, new ArrayList<>());

        book1.setComments(new ArrayList<>());
        book2.setComments(new ArrayList<>());

        Stream.of(1,2,3,4,5).forEach(i -> {
            Comment c = new Comment("Comment for book1 " + i, book1);
            book1.getComments().add(c);
        });

        Stream.of(1,2,3,4,5).forEach(i -> {
            Comment c = new Comment("Comment for book2 " + i, book2);
            book2.getComments().add(c);
        });

        when(bookDao.save(any(Book.class))).thenReturn(book1);

        bookService.save(book1);
        bookService.save(book2);

        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);

        when(bookDao.findAll()).thenReturn(bookList);

        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        var books = bookService.getAll();
        assertThat(books).isNotNull()
                .allMatch(b -> !b.getTitle().equals(""))
                .allMatch(b -> b.getGenre() != null)
                .allMatch(b -> b.getAuthor() != null)
                .allMatch(b -> b.getComments() != null);
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
    }

}
