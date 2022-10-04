package ru.otus.spring.exam.controller;

import java.util.concurrent.atomic.AtomicInteger;

public interface Result {

    String getName();

    String getSurname();

    void setName(String name);

    void setSurname(String surname);

    AtomicInteger getCntRightAnswers();

    AtomicInteger getQuestionsCount();

    int getCountRightAnswersInt();

    int getQuestionsCountInt();
}
