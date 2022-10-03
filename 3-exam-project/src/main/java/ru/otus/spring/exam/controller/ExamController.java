package ru.otus.spring.exam.controller;

public interface ExamController {
    void prepareExam();

    void startExam() throws Throwable;

    void finishExam();
}
