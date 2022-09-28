package ru.otus.spring;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.Test;
import ru.otus.spring.service.CsvTestService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, CsvException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        CsvTestService service = context.getBean(CsvTestService.class);

        InputStream testStream = Main.class.getClassLoader().getResourceAsStream("ru/otus/spring/test.csv");

        try (CSVReader reader = new CSVReader(new InputStreamReader(testStream))) {
            List<Test> tests = service.readTests(reader);
            tests.stream().forEach(test -> System.out.println(test));
        }
    }
}
