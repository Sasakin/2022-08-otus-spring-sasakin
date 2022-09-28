package ru.otus.spring.controller.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.spring.controller.ExamResult;
import ru.otus.spring.domain.Test;
import ru.otus.spring.property.ExamProperty;
import ru.otus.spring.service.CsvExamService;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ExamStarterImpl implements ExamStarter {

    private ExamResult result;

    private CsvExamService service;

    private ExamProperty property;

    private Scanner scanner;


    public ExamStarterImpl(@Autowired CsvExamService service,
                           @Autowired ExamProperty property,
                           @Autowired ExamResult examResult) {
        this.result = examResult;
        this.service = service;
        this.property = property;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void start() throws Throwable {
        AtomicInteger testResult = result.getCntRightAnswers();
        AtomicInteger questionsCount = result.getQuestionsCount();

        List<Test> tests = service.readTests();
        tests.stream().forEach(test -> {
            System.out.println(test.getQuestion());
            test.getOptions().stream().forEach(option -> {
                System.out.println(option);
            });
            System.out.println("Enter your answer number");
            int answer = scanner.nextInt();
            if (test.getAnswerNumberForCheck().intValue() == answer) {
                testResult.incrementAndGet();
            }
            questionsCount.incrementAndGet();
        });
    }
}
