package ru.otus.spring.exam.controller.preparation;

import org.springframework.stereotype.Component;
import ru.otus.spring.exam.controller.ControllerStatus;
import ru.otus.spring.exam.controller.Result;
import ru.otus.spring.exam.localisation.MessageSourceHolder;
import ru.otus.spring.exam.tools.ExamScanner;

@Component
public class ExamPreparationImpl implements ExamPreparation {

    private MessageSourceHolder msgHolder;
    private ExamScanner scanner;
    private Result result;

    public ExamPreparationImpl(Result result,
                               MessageSourceHolder msgHolder,
                               ExamScanner scanner) {
        this.scanner = scanner;
        this.result = result;
        this.msgHolder = msgHolder;
    }

    @Override
    public ControllerStatus prepareExam() {
        String writeNameMsg = msgHolder.getMessage("write.name", "Please write your name.");
        String writeSurnameMsg = msgHolder.getMessage("write.surname", "Please write your surname.");
        System.out.println(writeNameMsg);
        result.setName(scanner.getInput());
        System.out.println(writeSurnameMsg);
        result.setSurname(scanner.getInput());
        return ControllerStatus.PREPARATION;
    }
}
