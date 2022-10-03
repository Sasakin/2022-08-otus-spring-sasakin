package ru.otus.spring.exam.controller;

import org.springframework.stereotype.Controller;
import ru.otus.spring.exam.controller.preparation.ExamPreparation;
import ru.otus.spring.exam.controller.starter.ExamStarter;
import ru.otus.spring.exam.controller.termination.ExamTermination;

@Controller
public class ConsolExamController implements ExamController {

    private ExamPreparation preparation;

    private ExamStarter starter;

    private ExamTermination termination;

    public ConsolExamController(ExamPreparation preparation,
                                ExamStarter starter,
                                ExamTermination termination) {
        this.starter = starter;
        this.preparation = preparation;
        this.termination = termination;
    }

    @Override
    public void prepareExam() {
        preparation.prepareExam();
    }

    @Override
    public void startExam() throws Throwable {
        starter.start();
    }

    @Override
    public void finishExam() {
        termination.terminateExam();
    }
}

