package ru.otus.spring.controller;

public interface ExamController {
    void prepareExam();

    void startExam() throws Throwable;

    void finishExam();
}
