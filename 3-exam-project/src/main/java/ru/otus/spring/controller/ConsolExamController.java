package ru.otus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.otus.spring.controller.preparation.ExamPreparation;
import ru.otus.spring.controller.starter.ExamStarter;
import ru.otus.spring.controller.termination.ExamTermination;

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

