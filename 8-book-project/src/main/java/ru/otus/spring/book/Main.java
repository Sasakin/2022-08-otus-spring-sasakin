package ru.otus.spring.book;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.book.dao.AuthorDao;
import ru.otus.spring.book.domain.Author;

@EnableMongock
@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Throwable {
        ApplicationContext context = SpringApplication.run(Main.class);
    }
}
