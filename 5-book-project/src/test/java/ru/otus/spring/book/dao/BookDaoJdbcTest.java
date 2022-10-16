package ru.otus.spring.book.dao;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@DisplayName("Тест Dao для работы с книгами")
@JdbcTest
@Import(BookDaoJdbc.class)
public class BookDaoJdbcTest {
}
