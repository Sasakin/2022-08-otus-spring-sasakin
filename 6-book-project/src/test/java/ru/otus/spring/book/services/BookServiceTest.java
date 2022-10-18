package ru.otus.spring.book.services;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;
import ru.otus.spring.book.dao.BookDaoJPA;
import ru.otus.spring.book.dao.CommentDaoJPA;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;
import ru.otus.spring.book.domain.Genre;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест Dao для работы с книгами")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({BookServiceImpl.class, CommentServiceImpl.class, BookDaoJPA.class, CommentDaoJPA.class})
public class BookServiceTest {

    private static int EXPECTED_COUNT_BOOKS = 2;

    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private TestEntityManager em;

    @Test
    public void testInsert() {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Mystic");

        Book book = new Book("Silent hill", author, genre);

        bookService.save(book);

        Assert.notNull(book.getId(), "Book is inserted");
    }

    @Test
    public void testUpdate() {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Mystic");

        Book book = new Book("Silent hill", author, genre);

        bookService.save(book);

        Assert.notNull(book.getId(), "Book is inserted");

        bookService.save(book);

        Book fromBDBook = bookService.getById(book.getId());
    }

    @Test
    public void testDelete() {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Mystic");

        Book book = new Book("Silent hill", author, genre);

        bookService.save(book);

        Assert.notNull(book.getId(), "Book is inserted");

        Long id = book.getId();
        bookService.deleteById(id);

        Assert.isNull(bookService.getById(id), "Book is deleted");

    }

    @Test
    public void testGetById() {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Mystic");

        Book book = new Book("Silent hill", author, genre);

        bookService.save(book);

        Assert.notNull(book.getId(), "Book is inserted");

        Long id = book.getId();

        Assert.notNull(bookService.getById(id), "");
    }

    @Test
    public void testGetAll() {

        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Mystic");

        Book book1 = new Book("New book. Tom 1", author, genre);
        Book book2 = new Book("New book. Tom 2", author, genre);

        bookService.save(book1);
        bookService.save(book2);

        var books = bookService.getAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_COUNT_BOOKS)
                .allMatch(b -> !b.getTitle().equals(""))
                .allMatch(b -> b.getGenre() != null)
                .allMatch(b -> b.getAuthor() != null);
    }


    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectStudentsListWithAllInfo() {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Mystic");

        Book book1 = new Book("New book. Tom 1", author, genre);
        Book book2 = new Book("New book. Tom 2", author, genre);

        bookService.save(book1);
        bookService.save(book2);

        Stream.of(1,2,3,4,5).forEach(i -> {
            Comment c = new Comment("Comment for book1 " + i, book1);
            //commentDaoJPA.insert(c);
            book1.getComments().add(c);
        });

        Stream.of(1,2,3,4,5).forEach(i -> {
            Comment c = new Comment("Comment for book2 " + i, book2);
            //commentDaoJPA.insert(c);
            book2.getComments().add(c);
        });

        bookService.save(book1);
        bookService.save(book2);

        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);


        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        var books = bookService.getAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_COUNT_BOOKS)
                .allMatch(b -> !b.getTitle().equals(""))
                .allMatch(b -> b.getGenre() != null)
                .allMatch(b -> b.getAuthor() != null)
                .allMatch(b -> b.getComments() != null);
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);

        var comments1 = commentService.getCommentsByBook(book1);

        assertThat(comments1).isNotNull().hasSize(5)
                .allMatch(c -> c.getBook() != null)
                .allMatch(c -> c.getText() != null);

        var comments2 = commentService.getCommentsByBook(book1);

        assertThat(comments2).isNotNull().hasSize(5)
                .allMatch(c -> c.getBook() != null)
                .allMatch(c -> c.getText() != null);
    }


    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectStudentsListWithAllInfo2() {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Mystic");

        Book book1 = new Book("New book. Tom 1", author, genre);
        Book book2 = new Book("New book. Tom 2", author, genre);

        bookService.save(book1);
        bookService.save(book2);

        Stream.of(1,2,3,4,5).forEach(i -> {
            Comment c = new Comment("Comment for book1 " + i, book1);
            commentService.insert(c);
            //book1.getComments().add(c);
        });

        Stream.of(1,2,3,4,5).forEach(i -> {
            Comment c = new Comment("Comment for book2 " + i, book2);
            commentService.insert(c);
            //book2.getComments().add(c);
        });

        bookService.save(book1);
        bookService.save(book2);

        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);


        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        var books = bookService.getAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_COUNT_BOOKS)
                .allMatch(b -> !b.getTitle().equals(""))
                .allMatch(b -> b.getGenre() != null)
                .allMatch(b -> b.getAuthor() != null)
                .allMatch(b -> b.getComments() != null)
                .allMatch(b -> b.getComments().size() > 0);
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);

        var comments1 = commentService.getCommentsByBook(book1);

        assertThat(comments1).isNotNull().hasSize(5)
                .allMatch(c -> c.getBook() != null)
                .allMatch(c -> c.getText() != null);

        var comments2 = commentService.getCommentsByBook(book1);

        assertThat(comments2).isNotNull().hasSize(5)
                .allMatch(c -> c.getBook() != null)
                .allMatch(c -> c.getText() != null);

    }
}
