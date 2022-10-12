package ru.otus.spring.exam.tools;

import com.opencsv.CSVReader;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.otus.spring.exam.controller.ConsolExamController;
import ru.otus.spring.exam.property.ExamProperty;

import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class ExamCsvReader {
    private CSVReader reader;

    private ExamProperty property;

    public ExamCsvReader(ExamProperty property) {
        InputStream testStream = ConsolExamController.class.getClassLoader().getResourceAsStream(property.getExamTestSrc());
        CSVReader reader = new CSVReader(new InputStreamReader(testStream));
        this.reader = reader;
    }


    public CSVReader getReader() {
        return reader;
    }
}
