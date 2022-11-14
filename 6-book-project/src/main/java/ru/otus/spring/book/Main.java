package ru.otus.spring.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import ru.otus.spring.book.dao.AuthorDao;
import ru.otus.spring.book.domain.Author;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Throwable {
        ApplicationContext context = SpringApplication.run(Main.class);

       /* AuthorDao dao = context.getBean(AuthorDao.class);

        System.out.println("All count " + dao.count());

        dao.insert(new Author(2l, "ivan"));

        System.out.println("All count " + dao.count());

        Author ivan = dao.getById(2);

        System.out.println("Ivan id: " + ivan.getId() + " name: " + ivan.getName());*/

        //Console.main(args);
    }
}
