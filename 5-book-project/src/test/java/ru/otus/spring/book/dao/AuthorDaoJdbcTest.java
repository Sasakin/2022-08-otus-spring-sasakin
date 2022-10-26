package ru.otus.spring.book.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import ru.otus.spring.book.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Тест Dao для работы с авторами")
@JdbcTest
@Import(AuthorDaoJdbc.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoJdbcTest {

    private static final int EXPECTED_AUTHORS_COUNT = 1;
    private static final long EXISTING_AUTHOR_ID = 1l;
    private static final String EXISTING_AUTHOR_NAME = "Ivan";

    @Autowired
    private AuthorDaoJdbc authorDao;

    @BeforeTransaction
    void beforeTransaction(){
        System.out.println("beforeTransaction");
    }

    @AfterTransaction
    void afterTransaction(){
        System.out.println("afterTransaction");
    }

    @DisplayName("Возвращать ожидаемое количество автора в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        int actualAuthorsCount = authorDao.count();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }


    @DisplayName("Добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author("Igor");
        Long id = authorDao.insert(expectedAuthor);
        Author actualAuthor = authorDao.getById(id);
        assertThat(actualAuthor.getName()).isEqualTo("Igor");
    }

    @DisplayName("Возвращать ожидаемого автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        Author actualAuthor = authorDao.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("Удалять заданного автора по его id")
    @Test
    void shouldCorrectDeleteAuthorById() {
        assertThatCode(() -> authorDao.getById(EXISTING_AUTHOR_ID))
                .doesNotThrowAnyException();

        authorDao.deleteById(EXISTING_AUTHOR_ID);

        Assertions.assertNull(authorDao.getById(EXISTING_AUTHOR_ID));
    }

    @DisplayName("Возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorsList() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        List<Author> actualAuthorList = authorDao.getAll();
        assertThat(actualAuthorList)
                .containsExactlyInAnyOrder(expectedAuthor);
    }
}
