package ru.otus.spring.book.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import ru.otus.spring.book.dao.AuthorDaoJPA;
import ru.otus.spring.book.domain.Author;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест Services для работы с авторами")
@SpringBootTest
public class AuthorServiceTest {

    @MockBean
    private AuthorDaoJPA authorDao;

    @Autowired
    private AuthorServiceImpl authorService;

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
        List<Author> authorList = new ArrayList<>();
        authorList.add(new Author(1l, "Genry"));
        authorList.add(new Author(2l, "S King"));

        Mockito.when(authorDao.getAll()).thenReturn(authorList);

        int actualAuthorsCount = authorService.getAll().size();
        assertThat(actualAuthorsCount).isEqualTo(authorList.size());
    }


    @DisplayName("Добавлять автора в БД")
    @Test
    void getAuthorById() {
        Author expectedAuthor = new Author(1l, "Igor");
        Mockito.when(authorDao.getById(expectedAuthor.getId())).thenReturn(expectedAuthor);

        Author actualAuthor = authorService.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}
