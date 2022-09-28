package ru.otus.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.spring.controller.ExamController;

import javax.annotation.PostConstruct;

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


    @PostConstruct
    public void printCurrentTime() {
        try {
            run();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
