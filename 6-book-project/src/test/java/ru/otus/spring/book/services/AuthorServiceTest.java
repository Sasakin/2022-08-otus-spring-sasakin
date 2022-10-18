package ru.otus.spring.book.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import ru.otus.spring.book.dao.AuthorDaoJPA;
import ru.otus.spring.book.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Тест Dao для работы с авторами")
@DataJpaTest
@Import(AuthorDaoJPA.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/data.sql")
public class AuthorServiceTest {

    private static final int EXPECTED_AUTHORS_COUNT = 1;
    private static final long EXISTING_AUTHOR_ID = 1l;

    @Autowired
    private AuthorDaoJPA authorDao;

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
        int actualAuthorsCount = authorDao.getAll().size();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }


    @DisplayName("Добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author(null, "Igor");
        authorDao.insert(expectedAuthor);
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
}
