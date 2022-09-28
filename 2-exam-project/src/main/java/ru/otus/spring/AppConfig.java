package ru.otus.spring;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.controller.ConsolExamController;
import ru.otus.spring.property.ExamProperty;

import java.io.InputStream;
import java.io.InputStreamReader;

@Configuration
@ComponentScan()
public class AppConfig {

    @Bean
    public CSVReader getCsvReader(@Autowired ExamProperty property) {
        InputStream testStream = ConsolExamController.class.getClassLoader().getResourceAsStream(property.getExamTestSrc());
        CSVReader reader = new CSVReader(new InputStreamReader(testStream));
        return reader;
    }
}
