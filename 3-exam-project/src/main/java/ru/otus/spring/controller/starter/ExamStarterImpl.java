package ru.otus.spring.controller.starter;

import org.springframework.stereotype.Component;
import ru.otus.spring.controller.ExamResult;
import ru.otus.spring.domain.Test;
import ru.otus.spring.localisation.MessageSourceHolder;
import ru.otus.spring.service.CsvExamService;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ExamStarterImpl implements ExamStarter {

    private MessageSourceHolder msgHolder;

    private ExamResult result;

    private CsvExamService service;

    private Scanner scanner;


    public ExamStarterImpl(CsvExamService service,
                           ExamResult examResult,
                           MessageSourceHolder msgHolder) {
        this.result = examResult;
        this.service = service;
        this.scanner = new Scanner(System.in);
        this.msgHolder = msgHolder;
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
            String enterAnswerMsg = msgHolder.getMessage("choice.answer.number","Enter your answer number");
            System.out.println(enterAnswerMsg);
            int answer = scanner.nextInt();
            if (test.getAnswerNumberForCheck().intValue() == answer) {
                testResult.incrementAndGet();
            }
            questionsCount.incrementAndGet();
        });
    }
}
