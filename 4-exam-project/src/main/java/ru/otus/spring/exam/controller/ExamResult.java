package ru.otus.spring.exam.controller;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@Component
public class ExamResult implements Result {
    private String name;
    private String surname;
    private AtomicInteger cntRightAnswers = new AtomicInteger();
    private AtomicInteger questionsCount = new AtomicInteger();

    public ExamResult() {
    }

    public int getCountRightAnswersInt() {
        return cntRightAnswers.get();
    }

    public int getQuestionsCountInt() {
        return questionsCount.get();
    }

}
