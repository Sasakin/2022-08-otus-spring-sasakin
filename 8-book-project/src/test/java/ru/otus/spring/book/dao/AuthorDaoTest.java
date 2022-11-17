package ru.otus.spring.book.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.services.SequenceGeneratorService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест Dao для работы с авторами")
@SpringBootTest
public class AuthorDaoTest {

    private static final String EXISTING_AUTHOR_NAME = "Игорь";

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private AuthorDao authorDao;

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
        int actualAuthorsCount = authorDao.findAll().size();
        assertThat(actualAuthorsCount).isGreaterThan(0);
    }


    @DisplayName("Добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author(null, "Igor");
        expectedAuthor.setId(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME));
        authorDao.save(expectedAuthor);
        List<Author> actualAuthors = authorDao.findAll();
        Author actualAuthor = actualAuthors.get(actualAuthors.indexOf(expectedAuthor));
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("Удалять заданного автора по его id")
    @Test
    void shouldCorrectDeleteAuthorById() {
        Author author = authorDao.getByName(EXISTING_AUTHOR_NAME);
        Long id = author.getId();
        authorDao.deleteById(id);

        Assertions.assertNull(authorDao.findById(id).orElse(null));
    }
}
