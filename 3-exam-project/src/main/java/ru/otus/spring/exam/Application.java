package ru.otus.spring.exam;

import org.springframework.stereotype.Component;
import ru.otus.spring.exam.controller.ExamController;

@Component
public class Application {

    private ExamController controller;

    public Application(ExamController controller) {
        this.controller = controller;
    }

    public void run() throws Throwable {
        controller.prepareExam();
        controller.startExam();
        controller.finishExam();
    }
}
