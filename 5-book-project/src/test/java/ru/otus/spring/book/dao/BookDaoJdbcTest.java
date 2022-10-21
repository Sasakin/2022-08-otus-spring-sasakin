package ru.otus.spring.book.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;

@DisplayName("Тест Dao для работы с книгами")
@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoJdbcTest {
    private static int EXPECTED_COUNT_BOOKS = 2;

    @Autowired
    private BookDaoJdbc bookDaoJDBC;

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    public void testInsert() {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Mystic");

        Book book = new Book("Silent hill", author, genre);

        authorDaoJdbc.insert(author);
        genreDaoJdbc.insert(genre);

        bookDaoJDBC.insert(book);

        Assert.notNull(book.getId(), "Book is inserted");
    }

    @Test
    public void testUpdate() {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Mystic");

        Book book = new Book("Silent hill", author, genre);

        authorDaoJdbc.insert(author);
        genreDaoJdbc.insert(genre);

        bookDaoJDBC.insert(book);

        Assert.notNull(book.getId(), "Book is inserted");

    }

    @Test
    public void testDelete() {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Mystic");

        Book book = new Book("Silent hill", author, genre);

        authorDaoJdbc.insert(author);
        genreDaoJdbc.insert(genre);

        bookDaoJDBC.insert(book);

        Assert.notNull(book.getId(), "Book is inserted");

        Long id = book.getId();
        bookDaoJDBC.deleteById(id);

        Assert.isNull(bookDaoJDBC.getById(id), "Book is deleted");

    }

    @Test
    public void testGetById() {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Mystic");

        authorDaoJdbc.insert(author);
        genreDaoJdbc.insert(genre);

        Book book = new Book("Silent hill", author, genre);

        bookDaoJDBC.insert(book);

        Assert.notNull(book.getId(), "Book is inserted");

        Long id = book.getId();

        Assert.notNull(bookDaoJDBC.getById(id), "");
    }

    @Test
    public void testGetAll() {

        Author author = new Author(null, "Michail");
        Genre genre = new Genre(null, "Mystic");

        authorDaoJdbc.insert(author);
        genreDaoJdbc.insert(genre);

        Book book1 = new Book("New book. Tom 1", author, genre);
        Book book2 = new Book("New book. Tom 2", author, genre);

        bookDaoJDBC.insert(book1);
        bookDaoJDBC.insert(book2);

        var books = bookDaoJDBC.getAll();
        Assertions.assertThat(books).isNotNull().hasSize(EXPECTED_COUNT_BOOKS)
                .allMatch(b -> !b.getTitle().equals(""))
                .allMatch(b -> b.getGenre() != null)
                .allMatch(b -> b.getAuthor() != null);
    }
}
