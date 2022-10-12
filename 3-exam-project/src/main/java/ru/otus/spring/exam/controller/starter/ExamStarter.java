package ru.otus.spring.exam.controller.starter;

import ru.otus.spring.exam.controller.ControllerStatus;

public interface ExamStarter {

    ControllerStatus start() throws Throwable;
}
