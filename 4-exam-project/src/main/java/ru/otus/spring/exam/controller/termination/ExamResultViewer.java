package ru.otus.spring.exam.controller.termination;

import ru.otus.spring.exam.controller.ControllerStatus;
import ru.otus.spring.exam.exception.TestNotPassedException;

public interface ExamResultViewer {
    ControllerStatus showResult() throws TestNotPassedException;
}
