package ru.otus.spring.controller.preparation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.spring.controller.ExamResult;

import java.util.Scanner;

@Component
public class ExamPreparationImpl implements ExamPreparation {
    private Scanner scanner;

    private ExamResult result;

    public ExamPreparationImpl(@Autowired ExamResult result) {
        this.scanner = new Scanner(System.in);
        this.result = result;
    }

    @Override
    public void prepareExam() {
        System.out.println("Please write your name.");
        result.setName(scanner.next());
        System.out.println("Please write your surname.");
        result.setSurname(scanner.next());
    }
}
