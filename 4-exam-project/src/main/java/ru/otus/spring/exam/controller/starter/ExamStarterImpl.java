package ru.otus.spring.exam.controller.starter;

import org.springframework.stereotype.Component;
import ru.otus.spring.exam.controller.ControllerStatus;
import ru.otus.spring.exam.controller.ExamResult;
import ru.otus.spring.exam.controller.Result;
import ru.otus.spring.exam.domain.Test;
import ru.otus.spring.exam.exception.NoRegisterUserException;
import ru.otus.spring.exam.localisation.MessageSourceHolder;
import ru.otus.spring.exam.service.CsvExamService;
import ru.otus.spring.exam.tools.ExamScanner;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ExamStarterImpl implements ExamStarter {

    private MessageSourceHolder msgHolder;

    private Result result;

    private CsvExamService service;

    private ExamScanner scanner;


    public ExamStarterImpl(CsvExamService service,
                           Result result,
                           MessageSourceHolder msgHolder,
                           ExamScanner scanner) {
        this.result = result;
        this.service = service;
        this.scanner = scanner;
        this.msgHolder = msgHolder;
    }

    @Override
    public ControllerStatus start() throws Throwable {
        if(result.getName() == null || result.getSurname() == null) {
            throw new NoRegisterUserException();
        }

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
            int answer = scanner.getNextInt();
            if (test.getAnswerNumberForCheck().intValue() == answer) {
                testResult.incrementAndGet();
            }
            questionsCount.incrementAndGet();
        });

        return ControllerStatus.STARTED;
    }
}
