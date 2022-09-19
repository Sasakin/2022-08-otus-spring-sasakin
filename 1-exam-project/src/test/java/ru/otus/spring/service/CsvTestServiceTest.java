package ru.otus.spring.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.Assert;
import ru.otus.spring.Main;
import ru.otus.spring.converter.StringArrTestConverterImpl;
import ru.otus.spring.domain.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class CsvTestServiceTest {

    @org.junit.Test
    public void readCsv() throws IOException, CsvException {
        InputStream testStream = Main.class.getClassLoader().getResourceAsStream("ru/otus/spring/test.csv");

        CsvTestService service = new CsvTestServiceImpl(new StringArrTestConverterImpl());
        try (CSVReader reader = new CSVReader(new InputStreamReader(testStream))) {
            List<Test> tests = service.readTests(reader);

            Assert.assertEquals(tests.get(0).getQuestion(),"Кем вы мечтали стать в детстве?");
            Assert.assertEquals(tests.get(0).getOptions().size(),4);
        }
    }

}
