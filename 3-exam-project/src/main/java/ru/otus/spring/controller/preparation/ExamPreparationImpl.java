package ru.otus.spring.controller.preparation;

import org.springframework.stereotype.Component;
import ru.otus.spring.controller.ExamResult;
import ru.otus.spring.localisation.MessageSourceHolder;

import java.util.Scanner;

@Component
public class ExamPreparationImpl implements ExamPreparation {

    private MessageSourceHolder msgHolder;
    private Scanner scanner;
    private ExamResult result;

    public ExamPreparationImpl(ExamResult result,
                               MessageSourceHolder msgHolder) {
        this.scanner = new Scanner(System.in);
        this.result = result;
        this.msgHolder = msgHolder;
    }

    @Override
    public void prepareExam() {
        String writeNameMsg = msgHolder.getMessage("write.name", "Please write your name.");
        String writeSurnameMsg = msgHolder.getMessage("write.surname", "Please write your surname.");
        System.out.println(writeNameMsg);
        result.setName(scanner.next());
        System.out.println(writeSurnameMsg);
        result.setSurname(scanner.next());
    }
}
