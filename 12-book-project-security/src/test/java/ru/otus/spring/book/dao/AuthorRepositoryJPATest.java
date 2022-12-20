package ru.otus.spring.book.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.repository.AuthorRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест Dao для работы с авторами")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/data.sql")
public class AuthorRepositoryJPATest {

    private static final String EXISTING_AUTHOR_NAME = "Ivan";

    @Autowired
    private AuthorRepository authorRepository;

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
        int actualAuthorsCount = authorRepository.findAll().size();
        assertThat(actualAuthorsCount).isGreaterThan(0);
    }


    @DisplayName("Добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author(null, "Igor");
        authorRepository.save(expectedAuthor);
        Author actualAuthor = authorRepository.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("Удалять заданного автора по его id")
    @Test
    void shouldCorrectDeleteAuthorById() {
        Author author = authorRepository.getByName(EXISTING_AUTHOR_NAME);
        Long id = author.getId();
        authorRepository.deleteById(id);

        Assertions.assertNull(authorRepository.findById(id).orElse(null));
    }
}
