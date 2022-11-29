package ru.otus.spring.book.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.util.Assert;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;
import ru.otus.spring.book.domain.Genre;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест Dao для работы с книгами")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoJPATest {

    private static int EXPECTED_COUNT_BOOKS = 2;

    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private BookDao bookDaoJPA;

    @Autowired
    private TestEntityManager em;

    @Test
    public void testInsert() {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Mystic");

        Book book = new Book("Silent hill", author, genre);

        bookDaoJPA.save(book);

        Assert.notNull(book.getId(), "Book is inserted");
    }

    @Test
    public void testUpdate() {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Mystic");

        Book book = new Book("Silent holl2", author, genre);

        bookDaoJPA.save(book);

        Assert.notNull(book.getId(), "Book is inserted");

        book.setTitle("Silent hill");

        bookDaoJPA.save(book);

        Book fromBDBook = bookDaoJPA.findById(book.getId()).orElse(null);

        Assertions.assertEquals("Silent hill", fromBDBook.getTitle());
    }

    @Test
    public void testDelete() {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Mystic");

        Book book = new Book("Silent hill", author, genre);

        bookDaoJPA.save(book);

        Assert.notNull(book.getId(), "Book is inserted");

        Long id = book.getId();
        bookDaoJPA.deleteById(id);

        Assert.isNull(bookDaoJPA.findById(id).orElse(null), "Book is deleted");

    }

    @Test
    public void testGetById() {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Mystic");

        Book book = new Book("Silent hill", author, genre);

        bookDaoJPA.save(book);

        Assert.notNull(book.getId(), "Book is inserted");

        Long id = book.getId();

        Assert.notNull(bookDaoJPA.findById(id).orElse(null), "");
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectStudentsListWithAllInfo() {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Mystic");

        Book book1 = new Book("New book. Tom 1", author, genre);
        Book book2 = new Book("New book. Tom 2", author, genre);

        bookDaoJPA.save(book1);
        bookDaoJPA.save(book2);

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

        bookDaoJPA.save(book1);
        bookDaoJPA.save(book2);

        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);


        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        var books = bookDaoJPA.findAll();
        assertThat(books).isNotNull()//.hasSize(EXPECTED_COUNT_BOOKS)
                .allMatch(b -> !b.getTitle().equals(""))
                .allMatch(b -> b.getGenre() != null)
                .allMatch(b -> b.getAuthor() != null)
                .allMatch(b -> b.getComments() != null);
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);

    }
}
