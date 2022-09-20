package ru.otus.spring.controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.spring.Main;
import ru.otus.spring.domain.Test;
import ru.otus.spring.service.CsvTestService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ConsolTestController implements TestController {

    private Scanner scanner;

    private CsvTestService service;

    private TestResult result;

    public ConsolTestController(@Autowired CsvTestService service) {
        this.scanner = new Scanner(System.in);
        this.service = service;
        this.result = new TestResult();
    }

    @Override
    public void prepareTest() {
        System.out.println("Please write your name.");
        result.setName(scanner.next());
        System.out.println("Please write your surname.");
        result.setSurname(scanner.next());
    }

    @Override
    public void startTesting() throws IOException, CsvException {
        InputStream testStream = Main.class.getClassLoader().getResourceAsStream("ru/otus/spring/test.csv");

        AtomicInteger testResult = result.getTestResult();
        AtomicInteger questionsCount = result.getQuestionsCount();

        try (CSVReader reader = new CSVReader(new InputStreamReader(testStream))) {
            List<Test> tests = service.readTests(reader);
            tests.stream().forEach(test -> {
                System.out.println(test.getQuestion());
                test.getOptions().stream().forEach(option -> {
                    System.out.println(option);
                });
                System.out.println("Enter your answer number");
                int answer = scanner.nextInt();
                if(test.getAnswerNumberForCheck().intValue() == answer) {
                    testResult.incrementAndGet();
                }
                questionsCount.incrementAndGet();
            });
        }
    }

    @Override
    public void finishTesting() {
        System.out.printf("%s %s your result: %d / %d",
                result.getName(), result.getSurname(),
                result.getTestResult().get(), result.getQuestionsCount().get());
    }
}

@Data
class TestResult {
    private String name;
    private String surname;
    private AtomicInteger testResult = new AtomicInteger();
    private AtomicInteger questionsCount = new AtomicInteger();
}
