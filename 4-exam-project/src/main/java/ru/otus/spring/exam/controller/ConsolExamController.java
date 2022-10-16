package ru.otus.spring.exam.controller;

import org.springframework.stereotype.Controller;
import ru.otus.spring.exam.controller.registration.ExamRegistration;
import ru.otus.spring.exam.controller.starter.ExamStarter;
import ru.otus.spring.exam.controller.termination.ExamResultViewer;

@Controller
public class ConsolExamController implements ExamController {

    private ExamRegistration preparation;

    private ExamStarter starter;

    private ExamResultViewer termination;

    public ConsolExamController(ExamRegistration preparation,
                                ExamStarter starter,
                                ExamResultViewer termination) {
        this.starter = starter;
        this.preparation = preparation;
        this.termination = termination;
    }

    @Override
    public void prepareExam() {
        preparation.registerUser();
    }

    @Override
    public void startExam() throws Throwable {
        starter.start();
    }

    @Override
    public void finishExam() {
        termination.showResult();
    }
}

