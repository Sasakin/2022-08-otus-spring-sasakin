package ru.otus.spring.controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.otus.spring.domain.Test;
import ru.otus.spring.property.ExamProperty;
import ru.otus.spring.service.CsvTestService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ConsolTestController implements TestController {

    private Scanner scanner;

    private CsvTestService service;

    private TestResult result;

    private ExamProperty property;

    public ConsolTestController(@Autowired CsvTestService service, @Autowired ExamProperty property) {
        this.scanner = new Scanner(System.in);
        this.service = service;
        this.result = new TestResult();
        this.property = property;
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
        InputStream testStream = ConsolTestController.class.getClassLoader().getResourceAsStream(property.getExamTestSrc());

        AtomicInteger testResult = result.getCntRightAnswers();
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
        int countRightAnswers = result.getCntRightAnswers().get();
        System.out.printf("%s %s your result: %d / %d\n",
                result.getName(), result.getSurname(),
                countRightAnswers, result.getQuestionsCount().get());

        if(countRightAnswers >= property.getCountAnswersForPass()) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }
}

@Data
class TestResult {
    private String name;
    private String surname;
    private AtomicInteger cntRightAnswers = new AtomicInteger();
    private AtomicInteger questionsCount = new AtomicInteger();
}
