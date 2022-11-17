package ru.otus.spring.book.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.book.dao.AuthorDao;
import ru.otus.spring.book.domain.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест Services для работы с авторами")
@SpringBootTest
public class AuthorServiceTest {

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @MockBean
    private AuthorDao authorDao;

    @Autowired
    private AuthorServiceImpl authorService;

    @DisplayName("Возвращать ожидаемое количество автора в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        List<Author> authorList = new ArrayList<>();
        authorList.add(new Author(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME), "Genry"));
        authorList.add(new Author(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME), "S King"));

        Mockito.when(authorDao.findAll()).thenReturn(authorList);

        int actualAuthorsCount = authorService.getAll().size();
        assertThat(actualAuthorsCount).isEqualTo(authorList.size());
    }


    @DisplayName("Получить автора по ID")
    @Test
    void getAuthorById() {
        Author expectedAuthor = new Author(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME), "Igor");
        Mockito.when(authorDao.findById(expectedAuthor.getId())).thenReturn(Optional.of(expectedAuthor));

        Author actualAuthor = authorService.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}
